package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.UserAccount;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class WhoisCommand {

    public static void whois(String[] args, TextChannel tc) {
        if (args[1].equalsIgnoreCase("discord")) {
            if (args[2].matches("^[0-9]+$")) {
                User u = ExtremeDMC.jda.getUserById(args[2]);
                if (Config.getPlayer(u) != null) {
                    UserAccount ua = new UserAccount(u, Config.getPlayer(u));
                    StringBuilder sb = new StringBuilder();
                    sb.append("**").append(ua.getDiscordUser().getName()).append("#").append(ua.getDiscordUser().getDiscriminator());
                    if (ua.getDiscordMember().getNickname() != null) {
                        sb.append("** (").append(ua.getDiscordMember().getNickname()).append(")");
                    }
                    sb.append(": ").append("**Linked to:** ").append(ua.getPlayer().getName());
                    sb.append("\n").append("**Minecraft status:** " ).append(ua.getPlayer().isOnline() ? "Online" : "Offline");
                    sb.append("\n").append("**Discord status:** ").append(ua.getDiscordMember().getOnlineStatus().getKey());
                    tc.sendMessage(sb.toString()).queue();
                } else {
                    tc.sendMessage("**This user does not have a Minecraft account linked!**").queue();
                }
            }
        } else if (args[1].equalsIgnoreCase("mc")) {
            if (args[2].matches("^[A-Za-z0-9]+$")) {
                for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if (op.getName().equalsIgnoreCase(args[2])) {
                        if (Config.hasDiscord((Player) op)) {
                            UserAccount ua = new UserAccount(Config.getDiscord((Player) op), (Player) op);
                            StringBuilder sb = new StringBuilder();
                            sb.append("**").append(ua.getPlayer().getName());
                            sb.append("**: ").append("**Linked to:** ").append(ua.getDiscordUser().getName()).append("#").append(ua.getDiscordUser().getDiscriminator());
                            if (ua.getDiscordMember().getNickname() != null) {
                                sb.append(" (").append(ua.getDiscordMember().getNickname()).append(")");
                            }
                            sb.append("\n").append("**Minecraft status:** " ).append(ua.getPlayer().isOnline() ? "Online" : "Offline");
                            sb.append("\n").append("**Discord status:** ").append(ua.getDiscordMember().getOnlineStatus().getKey());
                            tc.sendMessage(sb.toString()).queue();
                            return;
                        } else {
                            tc.sendMessage("**This player does not have their Discord account linked!**").queue();
                            return;
                        }
                    }
                }
                tc.sendMessage("**Player not found!**").queue();
            } else {
                tc.sendMessage("**Invalid username! Use alphanumeric usernames.**").queue();
            }
        }
    }
}
