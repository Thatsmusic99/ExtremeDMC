package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvents implements Listener {

    @EventHandler
    public void onMinecraftJoin(PlayerJoinEvent e) {
        if (!ExtremeDMC.instance.getConfig().getString("text-channel").equals("INSERT-TEXT-CHANNEL")) {
            TextChannel tc = ExtremeDMC.instance.mainGuild.getTextChannelsByName(ExtremeDMC.instance.getConfig().getString("text-channel"), true).get(0);
            StringBuilder sb = new StringBuilder();
            for (char s : e.getPlayer().getName().toCharArray()) {
                if (s == '_') {
                    sb.append("\\");
                }
                sb.append(s);
            }
            tc.sendMessage("**" + sb.toString() + "** has joined the game!").queue();
        }
    }

    @EventHandler
    public void onMinecraftLeave(PlayerQuitEvent e) {
        if (!ExtremeDMC.instance.getConfig().getString("text-channel").equals("INSERT-TEXT-CHANNEL")) {
            TextChannel tc = ExtremeDMC.instance.mainGuild.getTextChannelsByName(ExtremeDMC.instance.getConfig().getString("text-channel"), true).get(0);
            StringBuilder sb = new StringBuilder();
            for (char s : e.getPlayer().getName().toCharArray()) {
                if (s == '_') {
                    sb.append("\\");
                }
                sb.append(s);
            }
            tc.sendMessage("**" + sb.toString() + "** has left the game!").queue();
        }
    }
}
