package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;


import io.github.thatsmusic99.extremedmc.ExtremeDMC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCommand {

    public static String list() {
        HashMap<String, List<Player>> groups = new HashMap<>();
        List<Player> ps = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("**Currently, there are ").append(Bukkit.getOnlinePlayers().size()).append(" players online!**");
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (String g : ExtremeDMC.perms.getGroups()) {
                if (ExtremeDMC.perms.playerInGroup(p, g)) {
                    ps.add(p);
                    groups.put(g, ps);
                }
            }
        }
        for (int i = 0; i < groups.size(); i++) {
            sb.append("\n**").append(groups.keySet().toArray()[i]).append("**: ");
            for (Player p : groups.get(groups.keySet().toArray()[i])) { // At least it works.
                // Wait you can't suppress that warning?
                sb.append(p.getName()).append(", ");
            }
        }
        return sb.toString();
    }
}
