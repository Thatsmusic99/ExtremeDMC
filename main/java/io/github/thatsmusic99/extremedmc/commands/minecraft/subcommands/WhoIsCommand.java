package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.UserAccount;
import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

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
            } else {
                List<Member> us = ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(args[2], true);
                if (us.size() == 0) {
                    p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "No users were found by that name!");
                } else if (us.size() == 1) {
                    User u = us.get(0).getUser();
                    if (Config.isPlayer(u)) {
                        OfflinePlayer pl = Config.getPlayer(u);
                        StringBuilder sb = new StringBuilder();
                        sb.append(ChatColor.DARK_AQUA).append(ChatColor.BOLD).append(u.getName()).append("#").append(u.getDiscriminator());
                        if (us.get(0).getNickname() != null) {
                            sb.append(" (").append(us.get(0).getNickname()).append(")");
                        }
                        sb.append(": ").append(ChatColor.AQUA).append("Linked to: ").append(pl.getName());
                        sb.append("\n").append(ChatColor.AQUA).append("Minecraft status: " ).append(pl.isOnline() ? "Online" : "Offline");
                        sb.append("\n").append(ChatColor.AQUA).append("Discord status: ").append(us.get(0).getOnlineStatus().getKey());
                        p.sendMessage(sb.toString());
                    }

                } else if (us.size() > 8) {
                    p.sendMessage(ChatColor.DARK_AQUA
                            + ""
                            + ChatColor.BOLD
                            + "Multiple names were found when searching by name, please click one of the following (use /edmc page <#> to switch pages:");
                    PagedLists pl = new PagedLists(ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(args[2], true), 8);

                    for (Object o : pl.getContentsInPage(1)) {
                        Member u = (Member) o;
                        StringBuilder sb = new StringBuilder();
                        sb.append(u.getUser().getName()).append("#").append(u.getUser().getDiscriminator());
                        if (!Objects.equals(u.getEffectiveName(), u.getUser().getName())) {
                            sb.append("(").append(u.getNickname()).append(")");
                        }
                        FancyMessage fc = new FancyMessage()
                                .color(ChatColor.AQUA)
                                .text(sb.toString())
                                .command("/edmc whois discord " + u.getUser().getId());
                        fc.send(p);
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_AQUA
                            + ""
                            + ChatColor.BOLD
                            + "Multiple names were found when searching by name, please click one of the following:");
                    for (Member u : ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(args[2], true)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(u.getUser().getName()).append("#").append(u.getUser().getDiscriminator());
                        if (!Objects.equals(u.getEffectiveName(), u.getUser().getName())) {
                            sb.append("(").append(u.getNickname()).append(")");
                        }
                        FancyMessage fc = new FancyMessage()
                                .color(ChatColor.AQUA)
                                .text(sb.toString())
                                .command("/edmc whois discord " + u.getUser().getId());
                        fc.send(p);
                    }
                }
            }
        } else if (args[1].equalsIgnoreCase("mc")) {
            if (args[2].matches("^[A-Za-z0-9]+$")) {
                for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if (op.getName().equalsIgnoreCase(args[2])) {
                        if (Config.hasDiscord(op)) {
                            UserAccount ua = new UserAccount(Config.getDiscord(op), op);
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
