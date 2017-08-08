package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static io.github.thatsmusic99.extremedmc.ExtremeDMC.config;

public class DiscordMessageEvent extends ListenerAdapter {

    private static boolean singlecharb;
    private static boolean singlechari;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        // if not valid command
            if (e.getChannel().getName().equalsIgnoreCase(config.getString("text-channel"))) {
                if (e.getAuthor() != ExtremeDMC.jda.getSelfUser()) {
                    for (Player p : ExtremeDMC.instance.getServer().getOnlinePlayers()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', ExtremeDMC.config.getString("d-message-format")
                                .replaceAll("%u", e.getAuthor().getName())
                                .replaceAll("%m", format(e.getMessage().getContent()))));
                    }
                }
            }
    }

    private static String format(String m) {
        while (m.contains("**") && !singlecharb) {
            m = bold(m);
        }
        while (m.contains("*") && !singlechari) {
            m = italic(m);
        }
        return m;
    }
    private static String bold(String m) {
        String ms = m;
        if (ms.contains("**")) {
            ms = ms.replaceFirst("\\*\\*", "§l");
            if (ms.contains("**")) {
                ms = ms.replaceFirst("\\*\\*", "§r");
                return ms;
            } else {
                singlecharb = true;
                return m;
            }
        } else {
            return m;
        }
    }
    private static String italic(String m) {
        String ms = m;
        if (ms.contains("*")) {
            ms = ms.replaceFirst("\\*", "§o");
            if (ms.contains("*")) {
                ms = ms.replaceFirst("\\*", "§r");
                return ms;
            } else {
                singlechari = true;
                return m;
            }
        } else {
            return m;
        }
    }

}
