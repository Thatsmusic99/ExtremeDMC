package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.UserAccount;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoIsCommand {

    public static void whois(String[] args, CommandSender p) {
        if (args[1].equalsIgnoreCase("discord")) {
            if (args[2].matches("^[0-9]+$")) {
                User u = ExtremeDMC.jda.getUserById(args[2]);
                if (Config.getPlayer(u) != null) {
                    UserAccount ua = new UserAccount(u, Config.getPlayer(u));
                    StringBuilder sb = new StringBuilder();
                    sb.append(ChatColor.DARK_AQUA).append(ChatColor.BOLD).append(ua.getDiscordUser().getName()).append("#").append(ua.getDiscordUser().getDiscriminator());
                    if (ua.getDiscordMember().getNickname() != null) {
                        sb.append(" (").append(ua.getDiscordMember().getNickname()).append(")");
                    }
                    sb.append(": ").append(ChatColor.AQUA).append("Linked to: ").append(ua.getPlayer().getName());
                    sb.append("\n").append(ChatColor.AQUA).append("Minecraft status: " ).append(ua.getPlayer().isOnline() ? "Online" : "Offline");
                    sb.append("\n").append(ChatColor.AQUA).append("Discord status: ").append(ua.getDiscordMember().getOnlineStatus().getKey());
                    p.sendMessage(sb.toString());
                } else {
                    p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "This user does not have a Minecraft account linked!");
                }
            }
        } else if (args[1].equalsIgnoreCase("mc")) {
            if (args[2].matches("^[A-Za-z0-9]+$")) {
                for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if (op.getName().equalsIgnoreCase(args[2])) {
                        if (Config.hasDiscord((Player) op)) {
                            UserAccount ua = new UserAccount(Config.getDiscord((Player) op), (Player) op);
                            StringBuilder sb = new StringBuilder();
                            sb.append(ChatColor.DARK_AQUA).append(ChatColor.BOLD).append(ua.getPlayer().getName());
                            sb.append(": ").append(ChatColor.AQUA).append("Linked to: ").append(ua.getDiscordUser().getName()).append("#").append(ua.getDiscordUser().getDiscriminator());
                            if (ua.getDiscordMember().getNickname() != null) {
                                sb.append(" (").append(ua.getDiscordMember().getNickname()).append(")");
                            }
                            sb.append("\n").append(ChatColor.AQUA).append("Minecraft status: " ).append(ua.getPlayer().isOnline() ? "Online" : "Offline");
                            sb.append("\n").append(ChatColor.AQUA).append("Discord status: ").append(ua.getDiscordMember().getOnlineStatus().getKey());
                            p.sendMessage(sb.toString());
                            return;
                        } else {
                            p.sendMessage(ChatColor.RED + "This player does not have their Discord account linked!");
                            return;
                        }
                    }
                }
                p.sendMessage(ChatColor.RED + "Player not found!");
            } else {
                p.sendMessage(ChatColor.RED + "Invalid username! Use alphanumeric usernames.");
            }
        }
    }
}
