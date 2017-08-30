package io.github.thatsmusic99.extremedmc.commands.discord;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.discord.subcommands.ListCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getChannel().getType().equals(ChannelType.TEXT)) {
            if (!e.getAuthor().isBot()) {
                String[] msg = e.getMessage().getContent().split(" ");
                if (msg[0].startsWith(ExtremeDMC.config.getString("prefix"))) {
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "list")) {
                        e.getChannel().sendMessage(ListCommand.list()).queue();
                    }
                }
            }
        }
    }
}
