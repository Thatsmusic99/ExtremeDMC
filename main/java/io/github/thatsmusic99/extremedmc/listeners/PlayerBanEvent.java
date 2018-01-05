package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerBanEvent {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (e.getPlayer().isBanned()) {
            if (Config.hasDiscord(e.getPlayer())) {
                if (ExtremeDMC.instance.mainGuild.getMember(Config.getDiscord(e.getPlayer())) != null) {
                    for (BanEntry b : Bukkit.getBanList(BanList.Type.NAME).getBanEntries()) {
                        if (b.getTarget().equalsIgnoreCase(e.getPlayer().getName())) {
                            ExtremeDMC.instance.mainGuild.getController().ban(Config.getDiscord(e.getPlayer()), 7, b.getReason()).queue();
                        }
                    }
                }
            }
        }
    }
}
