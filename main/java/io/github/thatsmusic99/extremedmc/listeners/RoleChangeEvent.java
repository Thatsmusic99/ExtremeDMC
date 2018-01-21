package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.List;

public class RoleChangeEvent extends ListenerAdapter {

    // TODO test

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e) {
        if (ExtremeDMC.config.getBoolean("roles-groups-link")) {
            if (Config.isPlayer(e.getUser())) {
                OfflinePlayer p = Config.getPlayer(e.getUser());
                for (Role r : e.getRoles()) {
                    if (Config.isRoleLinked(r))  {
                        List<String> str = Config.getLinkedGroups(r);
                        for (String s : str) {
                            if (p.isOnline()) {
                                Player pl = p.getPlayer();
                                if (ExtremeDMC.perms.playerInGroup(pl, s)) {
                                    ExtremeDMC.perms.playerAddGroup(pl, s);
                                    ExtremeDMC.instance.getLogger().info("Player " + pl.getName() + " has been added to group " + s + " since it is linked to the Discord role added, " + r.getName() + "!");
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
