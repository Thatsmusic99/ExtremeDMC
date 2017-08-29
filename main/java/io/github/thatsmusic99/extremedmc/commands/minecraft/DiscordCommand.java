package io.github.thatsmusic99.extremedmc.commands.minecraft;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
        if (cs.hasPermission("edmc.discord")) {
            if (cs instanceof Player) {
                if (!ExtremeDMC.config.getString("discord-invite").equals("INSERT-VALID-INVITE")) {
                    cs.sendMessage(ExtremeDMC.messages.getString("invite").replaceAll("%url%", ExtremeDMC.config.getString("discord-invite")));
                } else {
                    cs.sendMessage(ChatColor.AQUA + "There is no invite set up for the Discord server!");
                }
            } else {
                cs.sendMessage("[ExtremeDMC] This command is for players only.");
            }
        }
        return false;
    }

}
