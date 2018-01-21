package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class PageSwitch {

    public static HashMap<CommandSender, HashMap<String, PagedLists>> sections = new HashMap<>();

    // TODO test
    public static void switchPage(int page, CommandSender cs) {
        if (sections.containsKey(cs)) {
            String s = String.valueOf(getFirstKeyInHashmap(sections.get(cs)));
            if (s.equalsIgnoreCase("playerlink")) {
                PagedLists pl = sections.get(cs).get("playerlink");
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.DARK_AQUA + "===============" + ChatColor.WHITE + ChatColor.BOLD + " [ " + ChatColor.AQUA + ChatColor.BOLD + "EXTREMEDMC" + ChatColor.WHITE + ChatColor.BOLD + " ] " + ChatColor.AQUA + "1/").append(String.valueOf(pl.getTotalPages())).append(" ").append(ChatColor.DARK_AQUA).append("===============\n");
                for (Object o : pl.getContentsInPage(page)) {
                    sb.append(ChatColor.AQUA).append("").append(o);
                }
                cs.sendMessage(sb.toString());
            }
        }
    }

    public static Object getFirstKeyInHashmap(HashMap<?, ?> m) {
        return m.keySet().toArray()[0];
    }

    public static Object getKeyInHashmapWithVal(HashMap<?, ?> m, String val) {
        for (Object k : m.keySet()) {
            if (m.get(k).equals(val)) {
                return k;
            }
        }
        return "";
    }
}
