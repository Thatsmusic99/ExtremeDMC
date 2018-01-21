package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.UserAccount;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.List;

public class WhoisCommand {

    public static void whois(String[] args, TextChannel tc, User user) {
        if (args[1].equalsIgnoreCase("discord")) {
            if (args[2].matches("^[0-9]+$")) {
                User u = ExtremeDMC.jda.getUserById(args[2]);
                if (Config.getPlayer(u) != null) {
                    UserAccount ua = new UserAccount(u, Config.getPlayer(u));
                    StringBuilder sb = new StringBuilder();
                    sb.append("**").append(ua.getDiscordUser().getName()).append("#").append(ua.getDiscordUser().getDiscriminator());
                    if (ua.getDiscordMember().getNickname() != null) {
                        sb.append("** (").append(ua.getDiscordMember().getNickname()).append(")");
                    } else {
                        sb.append("**");
                    }
                    sb.append(": ").append("**Linked to:** ").append(ua.getPlayer().getName());
                    sb.append("\n").append("**Minecraft status:** " ).append(ua.getPlayer().isOnline() ? "Online" : "Offline");
                    sb.append("\n").append("**Discord status:** ").append(ua.getDiscordMember().getOnlineStatus().getKey());
                    tc.sendMessage(sb.toString()).queue();
                } else {
                    tc.sendMessage("**This user does not have a Minecraft account linked!**").queue();
                }
            } else {
                List<Member> us = ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(args[2], true);
                if (us.size() == 0) {
                    tc.sendMessage("**No users were found by that name!**").queue();
                } else if (us.size() == 1) {
                    User u = us.get(0).getUser();
                    if (Config.isPlayer(u)) {
                        OfflinePlayer pl = Config.getPlayer(u);
                        StringBuilder sb = new StringBuilder();
                        sb.append("**").append(u.getName()).append("#").append(u.getDiscriminator());
                        if (us.get(0).getNickname() != null) {
                            sb.append("** (").append(us.get(0).getNickname()).append(")");
                        } else {
                            sb.append("**");
                        }
                        sb.append(": ").append("**Linked to:** ").append(pl.getName());
                        sb.append("\n").append("**Minecraft status:** " ).append(pl.isOnline() ? "Online" : "Offline");
                        sb.append("\n").append("**Discord status:** ").append(us.get(0).getOnlineStatus().getKey());
                        tc.sendMessage(sb.toString()).queue();
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("More than one user was found, type in the corresponding number in a new message to link that role.");
                    HashMap<Integer, String> keys2 = new HashMap<>();
                    for (int i = 0; i < us.size(); i++) {
                        Member r = us.get(i);
                        try {
                            sb.append("\n").append(i + 1).append(". ").append(r.getEffectiveName()).append(" (").append(r.getUser().getName()).append("#").append(r.getUser().getDiscriminator()).append(")");
                        } catch (NullPointerException ex) {
                            sb.append("(None)");
                        }

                        keys2.put(i + 1, r.getUser().getId());
                    }
                    tc.sendMessage(sb.toString()).queue();
                    AwaitSubcommand.users.put(user, keys2);

                }
            }
        } else if (args[1].equalsIgnoreCase("mc")) {
            if (args[2].matches("^[A-Za-z0-9]+$")) {
                for (OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if (op.getName().equalsIgnoreCase(args[2])) {
                        if (Config.hasDiscord(op)) {
                            UserAccount ua = new UserAccount(Config.getDiscord(op), op);
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
