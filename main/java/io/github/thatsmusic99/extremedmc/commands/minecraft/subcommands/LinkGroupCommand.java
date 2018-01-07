package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class LinkGroupCommand {

    public static void linkGroup(String g, String n, CommandSender p) {
        if (p.hasPermission("edmc.command.linkgroup")) {
            if (Arrays.asList(ExtremeDMC.perms.getGroups()).contains(g)) {
                if (n.matches("^[0-9]+$")) {
                    if (ExtremeDMC.instance.mainGuild.getRoleById(n) != null) {
                        Role r = ExtremeDMC.instance.mainGuild.getRoleById(n);
                        if (!Config.isGroupAlreadyLinked(g, r)) {
                            Config.createGroup(r, g);
                            p.sendMessage(ChatColor.DARK_AQUA + g + " and " + r.getName() + " have been linked!");
                        }
                    }
                } else {
                    if (ExtremeDMC.instance.mainGuild.getRolesByName(n, true) != null) {
                        List<Role> rs = ExtremeDMC.instance.mainGuild.getRolesByName(n, true);
                        if (rs.size() > 8) {
                            p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "More than one role was found, ");
                            PagedLists pl = new PagedLists(rs, 8);
                            for (Object key : pl.getContentsInPage(1)) {
                                Role r = (Role) key;
                                FancyMessage fc = new FancyMessage()
                                        .color(ChatColor.AQUA)
                                        .text((ChatColor.AQUA + r.getName() + "-  Color (RGB): " + r.getColor().getRGB()))
                                        .command("/edmc glink " + r.getId());
                                fc.send(p);
                            }
                        } else if (rs.size() > 1) {
                            for (Role r : rs) {
                                FancyMessage fc = new FancyMessage()
                                        .color(ChatColor.AQUA)
                                        .text((ChatColor.AQUA + r.getName() + "-  Color (RGB): " + r.getColor().getRGB()))
                                        .command("/edmc glink " + r.getId());
                                fc.send(p);
                            }
                        } else {
                            Role r = rs.get(0);
                            if (!Config.isGroupAlreadyLinked(g, r)) {
                                Config.createGroup(r, g);
                                p.sendMessage(ChatColor.DARK_AQUA + g + " and " + r.getName() + " have been linked!");
                            }
                        }
                    }
                }
            }
        }
    }
}
