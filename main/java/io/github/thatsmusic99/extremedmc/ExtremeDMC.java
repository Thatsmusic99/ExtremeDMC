package io.github.thatsmusic99.extremedmc;

import com.google.common.io.ByteStreams;

import io.github.thatsmusic99.extremedmc.commands.discord.CommandManager;
import io.github.thatsmusic99.extremedmc.commands.minecraft.MainCommand;
import io.github.thatsmusic99.extremedmc.listeners.AsyncPlayerChatEvent;
import io.github.thatsmusic99.extremedmc.listeners.DiscordMessageEvent;

import io.github.thatsmusic99.extremedmc.listeners.ReactionListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
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

    @Override
    public void onEnable() {
        try {
            instance = this;
            setupEconomy();
            setupPermissions();
            setupChat();
            createConfig();
            this.getServer().getPluginManager().registerEvents(new AsyncPlayerChatEvent(), this);
            getCommand("edmc").setExecutor(new MainCommand());
            log.info("ExtremeDMC has successfully set itself up, logging into bot account once the server is set up!");
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        jda = new JDABuilder(AccountType.BOT).setToken(config.getString("bot-token"))
                                .addEventListener(new DiscordMessageEvent())
                                .addEventListener(new ReactionListener())
                                .addEventListener(new CommandManager())
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
                            jda.getPresence().setGame(Game.of(config.getString("playing-status")));
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
        jda.shutdownNow();
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
}
