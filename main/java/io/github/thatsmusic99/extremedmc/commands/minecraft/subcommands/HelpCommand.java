package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand {

    public static void helpNoArgs(CommandSender cs) {
        List<PermissionEnums> perms = new ArrayList<>();
        for (PermissionEnums key : PermissionEnums.values()) {
            if (cs.hasPermission(key.p)) {
                perms.add(key);
            }
        }
        PagedLists pl = new PagedLists(perms, 8);
        cs.sendMessage(ChatColor.DARK_AQUA + "===============" + ChatColor.WHITE + ChatColor.BOLD + " [ " + ChatColor.AQUA + ChatColor.BOLD + "EXTREMEDMC" + ChatColor.WHITE + ChatColor.BOLD + " ] " + ChatColor.AQUA + "1/" + String.valueOf(pl.getTotalPages()) + " " + ChatColor.DARK_AQUA + "===============");
        int TimesSent = 0;
        for (Object key : pl.getContentsInPage(1)) {
            if (TimesSent <= 7) {
                PermissionEnums key2 = (PermissionEnums) key;
                cs.sendMessage(ChatColor.AQUA + key2.c + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + key2.d);
                TimesSent++;
            }
        }
    }

    public static void help(CommandSender cs, int page) {
        List<PermissionEnums> perms = new ArrayList<>();
        for (PermissionEnums key : PermissionEnums.values()) {
            if (cs.hasPermission(key.p)) {
                perms.add(key);
            }
        }
        PagedLists pl = new PagedLists(perms, 8);
        cs.sendMessage(ChatColor.DARK_AQUA + "===============" + ChatColor.WHITE + ChatColor.BOLD + " [ " + ChatColor.AQUA + ChatColor.BOLD + "EXTREMEDMC" + ChatColor.WHITE + ChatColor.BOLD + " ] " + ChatColor.AQUA + page + "/" + String.valueOf(pl.getTotalPages()) + " " + ChatColor.DARK_AQUA + "===============");
        int TimesSent = 0;
        for (Object key : pl.getContentsInPage(page)) {
            if (TimesSent <= 7) {
                PermissionEnums key2 = (PermissionEnums) key;
                cs.sendMessage(ChatColor.AQUA + key2.c + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + key2.d);
                TimesSent++;
            }
        }
    }
}
