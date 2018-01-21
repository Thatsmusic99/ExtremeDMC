package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import net.dv8tion.jda.core.entities.Message;

public class InfoCommand {

    // TODO

    public static void info(Message m) {
        if (Config.isPlayer(m.getAuthor())) {

        } else {
            m.getTextChannel().sendMessage("**Your account isn't linked!**").queue();
        }
    }
}
