package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class BotStatusCommand {

    public static void botStatus(CommandSender cs) {
        cs.sendMessage(ChatColor.DARK_AQUA + "==============="
                + ChatColor.WHITE + ChatColor.BOLD + " [ "
                + ChatColor.AQUA + "BOT STATUS"
                + ChatColor.WHITE + ChatColor.BOLD + " ] "
                + ChatColor.DARK_AQUA + "===============");
        cs.sendMessage(ChatColor.DARK_AQUA + "Bot name: " + ChatColor.AQUA + ExtremeDMC.jda.getSelfUser().getName());
        cs.sendMessage(ChatColor.DARK_AQUA + "Bot ID: " + ChatColor.AQUA + ExtremeDMC.jda.getSelfUser().getId());
        cs.sendMessage(ChatColor.DARK_AQUA + "Guild: " + ChatColor.AQUA + ExtremeDMC.jda.getGuilds().get(0).getName() + " (" + ExtremeDMC.jda.getGuilds().get(0).getId() + ")");
        cs.sendMessage(ChatColor.DARK_AQUA + "Status: " + ChatColor.AQUA + ExtremeDMC.jda.getPresence().getStatus().getKey());
    }
}
