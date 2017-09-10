package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import org.bukkit.entity.Player;

public class ApproveCommand {

    public static void approve(Player p) {
        if (LinkCommand.pu.containsValue(p)) {
            if (!Config.hasDiscord(p)) {

            }
        }
    }
}
