package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LinkCommand {

    public static HashMap<User, Player> pu = new HashMap<>();
    public static HashMap<User, Player> up = new HashMap<>();

    public static void linkSelfName(Player p, String name) {
        if (p.hasPermission("edmc.command.link")) {
            if (!Config.hasDiscord(p)) {
                if (ExtremeDMC.jda.getUsersByName(name, true).size() > 0) {
                    if (!Config.isPlayer(ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(name, true).get(0).getUser())) {
                        if (ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(name, true).size() > 1) {
                            if (ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(name, true).size() > 8) {
                                PagedLists pl = new PagedLists(ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(name, true), 8);
                                List<Member> u = (List<Member>) pl.getContentsInPage(1);

                            } else {
                                p.sendMessage(ChatColor.DARK_AQUA
                                        + ""
                                        + ChatColor.BOLD
                                        + "Multiple names were found when searching by name, please click one of the following:");
                                for (Member u : ExtremeDMC.instance.mainGuild.getMembersByEffectiveName(name, true)) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(u.getUser().getName()).append("#").append(u.getUser().getDiscriminator());
                                    if (!Objects.equals(u.getEffectiveName(), u.getUser().getName())) {
                                        sb.append("(").append(u.getNickname()).append(")");
                                    }
                                    FancyMessage fc = new FancyMessage()
                                            .color(ChatColor.AQUA)
                                            .text(sb.toString())
                                            .command("/edmc link " + u.getUser().getId());
                                    fc.send(p);
                                }
                            }
                        } else {
                            if (LinkCommand.up.containsValue(p)) {
                                for (User u : LinkCommand.up.keySet()) {
                                    if (LinkCommand.up.get(u).equals(p)) {
                                        p.sendMessage(ChatColor.DARK_AQUA + "You've already attempted to link your account! Try linking your account via your Discord account.");
                                        return;
                                    }
                                }
                            }
                            p.sendMessage(ChatColor.AQUA + "We've found your account! " + ChatColor.DARK_AQUA + "Click the reaction on the private message we sent.");
                            User u = ExtremeDMC.jda.getUsersByName(name, true).get(0);
                            p.sendMessage(ChatColor.AQUA + "Wrong account? Do /edmc cancel. Message sent to: " + u.getName() + "#" + u.getDiscriminator() + " (" + u.getId() + ")");
                            pu.put(u, p);
                            PrivateChannel pc = u.openPrivateChannel().complete();
                            Message m = pc.sendMessage("**Hi there!** If you were linking this account to your MC player account (" + p.getName() + "), we've made it!" +
                                    "\nTo continue, please click one of the reactions added: the tick means you say that this is your account, the cross means it's not." +
                                    "\nRemember if you misclick, you won't be able to send a second request to your account; you can only send your player account a request.").complete();
                            m.addReaction("\u2705").queue();
                            m.addReaction("\u274E").queue();

                        }
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "No players were found by that name!");
                }
            } else {
                User u = Config.getDiscord(p);
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Your account is already linked!" + ChatColor.AQUA + " Currently linked to: " + u.getName() + "#" + u.getDiscriminator() + " (" + u.getId() + ")");
            }
        }
    }
    public static void linkSelfID(Player p, String id) {
        if (p.hasPermission("edmc.command.link")) {
            if (!Config.hasDiscord(p)) {
                if (ExtremeDMC.jda.getUserById(id) != null) {
                    if (!Config.isPlayer(ExtremeDMC.jda.getUserById(id))) {
                        if (LinkCommand.up.containsValue(p)) {
                            for (User u : LinkCommand.up.keySet()) {
                                if (LinkCommand.up.get(u).equals(p)) {
                                    p.sendMessage(ChatColor.DARK_AQUA + "You've already attempted to link your account! Try linking your account via your Discord account.");
                                    return;
                                }
                            }
                        }
                        User u = ExtremeDMC.jda.getUserById(id);
                        p.sendMessage(ChatColor.AQUA + "We've sent you a private message! " + ChatColor.DARK_AQUA + "Click the reaction on the private message we sent.");
                        p.sendMessage(ChatColor.AQUA + "Wrong account? Do /edmc cancel. Message sent to: " + u.getName() + "#" + u.getDiscriminator() + " (" + u.getId() + ")");
                        pu.put(u, p);
                        PrivateChannel pc = u.openPrivateChannel().complete();
                        Message m = pc.sendMessage("**Hi there!** If you were linking this account to your MC player account (" + p.getName() + "), we've made it!" +
                                "\nTo continue, please click one of the reactions added: the tick means you say that this is your account, the cross means it's not." +
                                "\nRemember if you misclick, you won't be able to send a second request to your account; you can only send your player account a request.").complete();
                        m.addReaction("\u2705").queue();
                        m.addReaction("\u274E").queue();
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "No players were found by that ID!" + ChatColor.AQUA + " Make sure you've copied the right ID!");
                }
            } else {
                User u = Config.getDiscord(p);
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Your account is already linked!" + ChatColor.AQUA + " Currently linked to: " + u.getName() + "#" + u.getDiscriminator() + " (" + u.getId() + ")");
            }
        }
    }
}
