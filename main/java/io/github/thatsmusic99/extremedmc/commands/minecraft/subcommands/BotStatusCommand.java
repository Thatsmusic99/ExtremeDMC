package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.entities.Game;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class BotStatusCommand {
    // TODO add onto it
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
        cs.sendMessage(ChatColor.DARK_AQUA + "Action: " + ChatColor.AQUA + (ExtremeDMC.jda.getPresence().getGame().getType() == Game.GameType.DEFAULT ? "Playing" : WordUtils.capitalize(ExtremeDMC.jda.getPresence().getGame().getType().name())));
        cs.sendMessage(ChatColor.DARK_AQUA + "Game: " + ChatColor.AQUA + ExtremeDMC.jda.getPresence().getGame().getName());
    }
}
