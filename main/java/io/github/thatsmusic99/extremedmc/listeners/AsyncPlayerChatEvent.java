package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static io.github.thatsmusic99.extremedmc.ExtremeDMC.config;

public class AsyncPlayerChatEvent implements Listener {

    private static boolean bold = false;
    private static boolean strike = false;

    @EventHandler
    public void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent e) {
        String m = e.getMessage();
        m = format(m);
        ExtremeDMC.jda.getGuilds().get(0)
                .getTextChannelsByName(config.getString("text-channel"), true)
                .get(0)
                .sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', config.getString("message-format").replaceAll("%w", e.getPlayer().getWorld().getName())
                        .replaceAll("%p", ExtremeDMC.chat.getPlayerPrefix(e.getPlayer()))
                        .replaceAll("%u", e.getPlayer().getName())
                        .replaceAll("%m", m)
                        .replaceAll("§", ""))))
        .queue();
    }
    private static String bold(String msg) {
        boolean t = false;
        boolean r = false;
        if (msg.contains("§l")) t = true;
        int s = msg.indexOf("§l");

        msg = msg.replaceFirst("§l", "**"); // **&nHi
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "**";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }
            } else if (c.name().equalsIgnoreCase("RESET")) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "**";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }

            }
        }
        if (t && !r) {
            msg += "**"; // **&nHi**
        }

        return msg;
    }
    private static String italic(String msg) {
        boolean t = false;
        boolean r = false;
        if (msg.contains("§o")) t = true;
        int s = msg.indexOf("§o");
        msg = msg.replaceFirst("§o", "*");
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "o";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }
            } else if (c.name().equalsIgnoreCase("RESET")) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "*";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }

            }
        }
        if (t && !r) {
            msg += "*";
        }
        return msg;
    }
    private static String underline(String msg) {
        boolean t = false;
        boolean r = false;
        if (msg.contains("§n")) t = true;
        int s = msg.indexOf("§n");
        if (bold) {
            String sub;
            if (msg.indexOf("**") == 0) { // **&nHi**
                sub = "";
            } else {
                sub = msg.substring(0, msg.indexOf("**") - 1); // Hi **there** (0, 2) (Hi )
            }
            String sub2 = msg.substring(msg.indexOf("**") + 2, msg.length()); // &nHi**

            sub = "__**" + sub; // __**
            sub2 += "__"; // &nHi**__
            msg = sub + sub2; // __**&nHi**__
            msg = msg.replaceFirst("§n", "");
            bold = false;
            return msg;
        }
        msg = msg.replaceFirst("§n", "__");
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "n";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }
            } else if (c.name().equalsIgnoreCase("RESET")) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "__";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }

            }
        }
        if (t && !r) {
            msg += "__";
        }
        return msg;
    }
    private static String strike(String msg) {
        boolean t = false;
        boolean r = false;
        if (msg.contains("§m")) t = true;
        int s = msg.indexOf("§m");

        msg = msg.replaceFirst("§m", "~~");
        for (ChatColor c : ChatColor.values()) {
            if (c.isColor()) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "m";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }
            } else if (c.name().equalsIgnoreCase("RESET")) {
                while (msg.contains("§" + c.getChar())) {
                    if (msg.indexOf("§" + c.getChar()) > s) {
                        String str3 = msg.substring(0, s);
                        String str = msg.substring(s, msg.indexOf("§" + c.getChar()));
                        String str2 = msg.substring(msg.indexOf("§" + c.getChar()), msg.length());
                        str += "~~";
                        msg = str3 + str + str2;
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                        r = true;
                    } else {
                        msg = msg.replaceFirst("§" + c.getChar(), "");
                    }
                }

            }
        }
        if (t && !r) {
            msg += "~~";
        }
        return msg;
    }
    private static String format(String msg) {
        while (msg.contains("§m")) {
            msg = strike(msg);
            strike = true;
        }
        if (strike) {
            strike = false;
            msg = msg.replaceAll("§l", "").replaceAll("§o", "").replaceAll("§n", "");
            return msg;
        }
        while (msg.contains("§l")) {
            msg = bold(msg);
            bold = true;
        }
        while (msg.contains("§o")) {
            msg = italic(msg);
        }
        while (msg.contains("§n")) {
            msg = underline(msg);
        }
        return msg;
    }
}

