package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class StaffChatCommand {

    public static List<CommandSender> staff = new ArrayList<>();

    public static void addToChatNoArgs(CommandSender cs) {
        if (cs.hasPermission("edmc.command.staffchat")) {
            if (staff.contains(cs)) {
                staff.remove(cs);
                cs.sendMessage(ChatColor.AQUA + "You've left the staff chat!");
            } else {
                staff.add(cs);
                cs.sendMessage(ChatColor.AQUA + "You've joined the staff chat!");
            }
        }
    }
}
