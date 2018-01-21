package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.audit.ActionType;
import net.dv8tion.jda.core.audit.AuditLogEntry;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DiscordBanEvent extends ListenerAdapter {

    @Override
    public void onGuildBan(GuildBanEvent e) {
        if (ExtremeDMC.instance.getConfig().getBoolean("ban-linked-accounts")) {
            if (Config.isPlayer(e.getUser())) {
                if (!Config.getPlayer(e.getUser()).isBanned()) {
                    List<AuditLogEntry> ale = getBan(e.getGuild());
                    if (ale.size() > 0) {
                        AuditLogEntry al = ale.get(0);
                        OfflinePlayer op = Config.getPlayer(e.getUser());
                        Bukkit.getBanList(BanList.Type.NAME).addBan(op.getName(), ChatColor.AQUA + al.getReason(), null, "ExtremeDMCBAN").save();
                        ExtremeDMC.instance.getLogger().info(op.getName() + " has been banned on the main MC server as they were banned on the Discord server!");
                        Player p = op.getPlayer();
                        Bukkit.getScheduler().runTask(ExtremeDMC.instance, () -> {
                            try {
                                p.kickPlayer(ChatColor.AQUA + al.getReason());
                            } catch (NullPointerException ignored) {

                            }
                        });
                    }
                }
            }
        }
    }

    public static List<AuditLogEntry> getBan(Guild g) {
        return  g.getAuditLogs().cache(false).stream().filter(it -> it.getType().equals(ActionType.BAN)).collect(Collectors.toList());
    }
}
