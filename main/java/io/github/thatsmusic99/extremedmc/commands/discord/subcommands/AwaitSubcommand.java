package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;

public class AwaitSubcommand extends ListenerAdapter {

    public static HashMap<User, HashMap<Integer, Role>> sections = new HashMap<>();
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

    }

}
