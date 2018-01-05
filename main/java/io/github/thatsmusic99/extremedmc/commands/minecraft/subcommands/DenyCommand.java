package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DenyCommand {

    public static void deny(Player p, User u) {
        if (LinkCommand.pu.containsValue(p)) {
            p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "The request has been denied! " + ChatColor.AQUA + "To try again, do this from your Minecraft account.");
            LinkCommand.pu.remove(u);
            LinkCommand.up.put(u, p);
        }
    }
}
