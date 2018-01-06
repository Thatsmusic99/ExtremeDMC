package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

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
                }
            }
        }
    }
}
