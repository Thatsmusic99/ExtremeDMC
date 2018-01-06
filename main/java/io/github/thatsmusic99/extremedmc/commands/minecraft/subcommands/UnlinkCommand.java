package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UnlinkCommand {

    public static void unlink(Player p) {
        if (p.hasPermission("edmc.command.unlink")) {
            if (Config.hasDiscord(p)) {
                Config.remove(p);
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Your accounts have been unlinked!");
            }
        }
    }
}
