package io.github.thatsmusic99.extremedmc;

import com.google.common.io.ByteStreams;

import io.github.thatsmusic99.extremedmc.commands.discord.CommandManager;
import io.github.thatsmusic99.extremedmc.commands.minecraft.DiscordCommand;
import io.github.thatsmusic99.extremedmc.commands.minecraft.MainCommand;
import io.github.thatsmusic99.extremedmc.listeners.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

import java.io.*;
import java.util.logging.Logger;

public class ExtremeDMC extends JavaPlugin {

    public static JDA jda = null;
    public static YamlConfiguration config;
    public static YamlConfiguration data;
    public static YamlConfiguration messages;
    public Logger log = getLogger();
    public static Chat chat = null;
    private static Economy econ = null;
    public static Permission perms = null;
    public static ExtremeDMC instance;
    public Guild mainGuild = null;
    public static Metrics m;

    @Override
    public void onEnable() {
        try {
            instance = this;
            setupEconomy();
            setupPermissions();
            setupChat();
            createConfig();
            this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatEvent(), this);
            this.getServer().getPluginManager().registerEvents(new JoinLeaveEvents(), this);
            getCommand("edmc").setExecutor(new MainCommand());
            getCommand("discord").setExecutor(new DiscordCommand());
            m = new Metrics(this);


            log.info("ExtremeDMC has successfully set itself up, logging into bot account once the server is set up!");
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        jda = new JDABuilder(AccountType.BOT).setToken(config.getString("bot-token"))
                                .addEventListener(new DiscordMessageEvent())
                                .addEventListener(new ReactionListener())
                                .addEventListener(new CommandManager())
                                .addEventListener(new FullStartupEvent())
                                .addEventListener(new JoinLeaveEvents())
                                .buildAsync();
                    } catch (LoginException e) {
                        log.severe("ExtremeDMC couldn't login into a bot account. Make sure the token you've inserted is correct!");
                        return;
                    } catch (RateLimitedException e) {
                        e.printStackTrace();
                        return;
                    }
                    log.info("ExtremeDMC has successfully logged into a bot account!");

                    jda.getPresence().setStatus(OnlineStatus.valueOf(config.getString("online-status")));



                    if (System.getProperty("java.version").contains("1.8")) {
                        try {
                            jda.getPresence().setGame(Game.of(Game.GameType.valueOf(config.getString("gametype")), config.getString("playing-status")));
                        } catch (Exception e) {
                            // Nothing, just in case of mismatch
                        }
                    } else {
                        log.warning("Java 8 not detected, cannot set playing status!");

                    }

                }
            }.runTaskAsynchronously(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    private void createConfig() throws IOException {
        config = YamlConfiguration.loadConfiguration(loadResource(this, "config.yml"));
        data = YamlConfiguration.loadConfiguration(loadResource(this, "data.yml"));
        messages = YamlConfiguration.loadConfiguration(loadResource(this, "messages.yml"));
    }
    static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }
    @Override
    public void onDisable() {
        try {
            jda.shutdownNow();
        } catch (NullPointerException ex) {
            //
        }
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return chat != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        perms = rsp.getProvider();
        return perms != null;
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Player getPlayer(String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return (Player) p;
            }
        }
        return null;
    }

    public void setupMainGuild() {
        try {
            this.mainGuild = jda.getGuilds().get(0);
            m.addCustomChart(new Metrics.SingleLineChart("users_managed", () -> jda.getUsers().size()));
            m.addCustomChart(new Metrics.SingleLineChart("tcs_managed", () -> jda.getTextChannels().size()));
            m.addCustomChart(new Metrics.SingleLineChart("roles_managed", () -> jda.getRoles().size()));
            m.addCustomChart(new Metrics.SingleLineChart("accounts_linked", () -> data.getConfigurationSection("data").getKeys(false).size()));
        } catch (IndexOutOfBoundsException ex) {
            log.warning("No main guild able to be detected! Please make sure your bot is in a guild!");
        }
    }
}
