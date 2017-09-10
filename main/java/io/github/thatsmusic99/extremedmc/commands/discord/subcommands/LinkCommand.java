package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class LinkCommand {

    public static void link(User u, String name, TextChannel tc) {
        if (!Config.isPlayer(u)) {

        } else {
            tc.sendMessage("**Your Discord account is already linked to " + Config.getPlayer(u).getName() + "!**").queue();
        }
    }
}
