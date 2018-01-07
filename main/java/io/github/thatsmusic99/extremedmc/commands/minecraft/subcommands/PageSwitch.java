package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PageSwitch {

    public static HashMap<CommandSender, HashMap<String, PagedLists>> sections = new HashMap<>();

    public static void switchPage(int page, CommandSender cs) {
        if (sections.containsKey(cs)) {
            String s = String.valueOf(getKeyInHashmap(sections.get(cs)));
            if (s.equalsIgnoreCase("playerlink")) {
                PagedLists pl = sections.get(cs).get("playerlink");
                StringBuilder sb = new StringBuilder();
                for (Object o : pl.getContentsInPage(page)) {

                }
            }
        }
    }

    public static Object getKeyInHashmap(HashMap<?, ?> m) {

        for (Object k : m.keySet()) {
            for (Object v : m.values()) {
                if (m.get(k).equals(v)) {
                    return k;
                }
            }
        }
        return "";
    }
}
