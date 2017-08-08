package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand {

    public static void helpNoArgs(CommandSender cs) {
        List<PermissionEnums> headPerms = new ArrayList<>();
        for (PermissionEnums key : PermissionEnums.values()) {
            if (cs.hasPermission(key.p)) {
                headPerms.add(key);
            }
        }
        int pageNo = 1;
        int hpp = headPerms.size();
        while (hpp > 8) {
            pageNo++;
            hpp = hpp - 8;
        }
        cs.sendMessage(ChatColor.DARK_AQUA + "===============" + ChatColor.WHITE + ChatColor.BOLD + " [ " + ChatColor.AQUA + ChatColor.BOLD + "EXTREMEDMC" + ChatColor.WHITE + ChatColor.BOLD + " ] " + ChatColor.AQUA + "1/" + String.valueOf(pageNo) + " " + ChatColor.DARK_AQUA + "===============");
        int TimesSent = 0;
        for (PermissionEnums key2 : headPerms) {
            if (TimesSent <= 7) {
                cs.sendMessage(ChatColor.AQUA + key2.c + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + key2.d);
                TimesSent++;
            }
        }
    }
}
