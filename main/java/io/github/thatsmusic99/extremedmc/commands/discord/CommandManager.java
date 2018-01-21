package io.github.thatsmusic99.extremedmc.commands.discord;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.discord.subcommands.*;
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
                    if (AwaitSubcommand.sections.containsKey(e.getAuthor())) {
                        AwaitSubcommand.sections.remove(e.getAuthor());
                        e.getTextChannel().sendMessage("**Operation cancelled.**").queue();
                    }
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "list")) {
                        e.getChannel().sendMessage(ListCommand.list()).queue();
                    }

                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "whois")) {
                        if (msg.length > 2) {
                            WhoisCommand.whois(msg, e.getTextChannel(), e.getAuthor());
                        }
                    }
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "link")) {
                        if (msg.length > 1) {
                            LinkCommand.link(e.getAuthor(), msg[1], e.getTextChannel());
                        }
                    }
                    if (msg[0].equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "glink")) {
                        if (msg.length > 2) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 2; i < msg.length; i++) {
                                sb.append(msg[i]);
                            }
                            GroupLinkCommand.linkGroup(msg[1], sb.toString(), e.getMessage());
                        }
                    }
                }
            }
        }
    }
    // TODO
    public static boolean isItACommand(String content) {
        if (content.startsWith(ExtremeDMC.config.getString("prefix"))) {
            if (content.startsWith(ExtremeDMC.config.getString("prefix") + "list")) {
                return true;
            } else if (content.startsWith(ExtremeDMC.config.getString("prefix") + "whois")) {
                return true;
            } else if (content.equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "link")) {
                return true;
            } else if (content.equalsIgnoreCase(ExtremeDMC.config.getString("prefix") + "glink")) {
                return true;
            }
            return false;

        } else {
            return false;
        }
    }

}
