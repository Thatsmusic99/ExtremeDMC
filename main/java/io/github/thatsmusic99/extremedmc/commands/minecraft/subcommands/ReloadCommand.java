package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.listeners.DiscordMessageEvent;
import io.github.thatsmusic99.extremedmc.listeners.ReactionListener;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;

public class ReloadCommand {

    public static void reload(Player p) {
        try {
            Config.reloadConfig();
            if (ExtremeDMC.jda == null || !ExtremeDMC.jda.getToken().equals(ExtremeDMC.config.getString("bot-token"))) {
                try {
                    ExtremeDMC.jda = null;
                    ExtremeDMC.jda = new JDABuilder(AccountType.BOT).setToken(ExtremeDMC.config.getString("bot-token"))
                            .addEventListener(new DiscordMessageEvent())
                            .addEventListener(new ReactionListener())
                            .buildAsync();
                } catch (LoginException ex) {
                    ExtremeDMC.instance.log.severe("ExtremeDMC couldn't login into a bot account. Make sure the token you've inserted is correct!");
                    p.sendMessage(ChatColor.RED + "The config was reloaded, but ExtremeDMC couldn't log into a bot account. Make sure the token you've inserted is correct!");
                    return;
                } catch (RateLimitedException ex) {
                    ex.printStackTrace();
                    return;
                }
                ExtremeDMC.instance.log.info("ExtremeDMC has successfully logged into a bot account!");
                ExtremeDMC.jda.getPresence().setStatus(OnlineStatus.valueOf(ExtremeDMC.config.getString("online-status")));
                if (System.getProperty("java.version").contains("1.8")) {
                    try {
                        ExtremeDMC.jda.getPresence().setGame(Game.of(ExtremeDMC.config.getString("playing-status")));
                    } catch (Exception e) {
                        // Nothing, just in case of mismatch
                    }
                } else {
                    ExtremeDMC.instance.log.warning("Java 8 not detected, cannot set playing status!");

                }
            }
            p.sendMessage(ChatColor.AQUA + "Config has been reloaded!");
        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "Failed to reload config!");
            e.printStackTrace();
        }
    }
}
