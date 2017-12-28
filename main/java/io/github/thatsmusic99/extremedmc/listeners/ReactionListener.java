package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getChannelType().equals(ChannelType.PRIVATE)) {
            if (e.getChannel().getMessageById(e.getMessageId()).complete().getAuthor().getId().equals(ExtremeDMC.jda.getSelfUser().getId())) {
                if (e.getReaction().getReactionEmote().getName().equals("\u2705")) {
                    if (LinkCommand.pu.containsKey(e.getUser())) {
                        e.getUser().openPrivateChannel().complete().sendMessage("**Great!** We're linking your accounts now. Thank you!").queue();
                        Config.create(LinkCommand.pu.get(e.getUser()), e.getUser());
                        LinkCommand.pu.remove(e.getUser());
                    }
                } else if (e.getReaction().getReactionEmote().getName().equals("\u274E")) {
                    if (LinkCommand.pu.containsKey(e.getUser())) {
                        e.getUser().openPrivateChannel().complete().sendMessage("**Oops, may have been the wrong account.** Apologies for bothering!").queue();
                        LinkCommand.up.putIfAbsent(e.getUser(), LinkCommand.pu.get(e.getUser()));
                        LinkCommand.pu.remove(e.getUser());
                    }
                }
            }
        }
    }
}
