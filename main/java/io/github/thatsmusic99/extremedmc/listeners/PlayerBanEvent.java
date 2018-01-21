package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerBanEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (e.getPlayer().isBanned()) {
            if (ExtremeDMC.instance.getConfig().getBoolean("ban-linked-accounts")) {
                if (Config.hasDiscord(e.getPlayer())) {
                    try {
                        if (ExtremeDMC.instance.mainGuild.getMember(Config.getDiscord(e.getPlayer())) != null) {
                            for (BanEntry b : Bukkit.getBanList(BanList.Type.NAME).getBanEntries()) {
                                if (b.getTarget().equalsIgnoreCase(e.getPlayer().getName())) {
                                    ExtremeDMC.instance.getLogger().info(Config.getDiscord(e.getPlayer()).getName() + ", aka " + e.getPlayer().getName() + ", has been banned on the Discord server!");
                                    ExtremeDMC.instance.mainGuild.getController().ban(Config.getDiscord(e.getPlayer()), 7, b.getReason()).queue();
                                }
                            }
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }
}
