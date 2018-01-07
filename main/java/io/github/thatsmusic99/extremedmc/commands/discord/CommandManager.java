package io.github.thatsmusic99.extremedmc.commands.discord;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.discord.subcommands.LinkCommand;
import io.github.thatsmusic99.extremedmc.commands.discord.subcommands.ListCommand;
import io.github.thatsmusic99.extremedmc.commands.discord.subcommands.WhoisCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getChannel().getType().equals(ChannelType.TEXT)) {
            if (!e.getAuthor().isBot()) {
                String[] msg = e.getMessage().getContentDisplay().split(" ");
                if (msg[0].startsWith(ExtremeDMC.config.getString("prefix"))) {
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "list")) {
                        e.getChannel().sendMessage(ListCommand.list()).queue();
                    }
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "whois")) {
                        if (msg.length > 2) {
                            String[] args = new String[msg.length - 1];
                            System.arraycopy(msg, 1, args, 0, msg.length - 1);
                            WhoisCommand.whois(args, e.getTextChannel());
                        }
                    }
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "link")) {
                        if (msg.length > 1) {
                            LinkCommand.link(e.getAuthor(), msg[1], e.getTextChannel());
                        }
                    }
                }
            }
        }
    }

    public static boolean isItACommand(String content) {
        if (content.startsWith(ExtremeDMC.config.getString("prefix"))) {
            if (content.startsWith(ExtremeDMC.config.getString("prefix") + "list")) {
                return true;
            } else if (content.startsWith(ExtremeDMC.config.getString("prefix") + "whois")) {
                return true;
            } else if (content.equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "link")) {
                return true;
            }
            return false;

        } else {
            return false;
        }
    }

}
