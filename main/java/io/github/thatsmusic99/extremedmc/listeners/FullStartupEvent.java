package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class FullStartupEvent extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent e) {
        ExtremeDMC.instance.setupMainGuild();
    }
}
