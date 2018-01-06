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

import java.util.List;
import java.util.stream.Collectors;

public class DiscordBanEvent extends ListenerAdapter {

    @Override
    public void onGuildBan(GuildBanEvent e) {
        if (ExtremeDMC.instance.getConfig().getBoolean("ban-linked-accounts")) {
            if (Config.isPlayer(e.getUser())) {
                List<AuditLogEntry> ale = getBan(e.getGuild());
                if (ale.size() > 0) {
                    AuditLogEntry al = ale.get(0);
                    Bukkit.getBanList(BanList.Type.NAME).addBan(Config.getPlayer(e.getUser()).getName(), al.getReason(), null, "ExtremeDMCBAN").save();
                    ExtremeDMC.instance.getLogger().info(Config.getPlayer(e.getUser()) + " has been banned on the main MC server as they were banned on the Discord server");
                }
            }
        }
    }

    public static List<AuditLogEntry> getBan(Guild g) {
        return  g.getAuditLogs().cache(false).stream().filter(it -> it.getType().equals(ActionType.BAN)).collect(Collectors.toList());
    }
}
