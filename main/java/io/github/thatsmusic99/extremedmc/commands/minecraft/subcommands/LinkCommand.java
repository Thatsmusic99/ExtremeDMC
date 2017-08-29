package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class LinkCommand {

    public static HashMap<User, Player> pu = new HashMap<>();

    public static void linkSelfName(Player p, String name) {
        if (p.hasPermission("edmc.command.link")) {
            if (!Config.hasDiscord(p)) {
                if (ExtremeDMC.jda.getUsersByName(name, true).size() > 0) {
                    if (!Config.isPlayer(ExtremeDMC.jda.getUsersByName(name, true).get(0))) {
                        if (ExtremeDMC.jda.getUsersByName(name, true).size() > 1) {
                            if (ExtremeDMC.jda.getUsersByName(name, true).size() > 8) {

                            } else {
                                p.sendMessage(ChatColor.DARK_AQUA
                                        + ""
                                        + ChatColor.BOLD
                                        + "Multiple names were found when searching by name, please click one of the following:");
                                for (User u : ExtremeDMC.jda.getUsersByName(name, true)) {
                                    FancyMessage fm = new FancyMessage()
                                            .color(ChatColor.AQUA)
                                            .text(u.getName() + "#" + u.getDiscriminator())
                                            .command("/edmc link " + u.getId());
                                    p.sendMessage(fm.toJSONString());
                                }
                            }
                        } else {
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
                    p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "No players were found by that name!" + ChatColor.AQUA + " Make sure you use your Discord name, not your nickname!");
                }
            } else {
                User u = Config.getDiscord(p);
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Your account is already linked!" + ChatColor.AQUA + " Currently linked to: " + u.getName() + "#" + u.getDiscriminator() + " (" + u.getId() + ")");
            }
        }
    }
}
