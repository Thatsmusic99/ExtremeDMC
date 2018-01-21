package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupLinkCommand {

    public static void linkGroup(String g, String n, Message m) {
        if (m.getMember().hasPermission(Permission.MANAGE_PERMISSIONS) || m.getMember().isOwner()) {
            if (Arrays.asList(ExtremeDMC.perms.getGroups()).contains(g)) {

                if (n.matches("^[0-9]+$")) {
                    if (ExtremeDMC.instance.mainGuild.getRoleById(n) != null) {
                        Role r = ExtremeDMC.instance.mainGuild.getRoleById(n);
                        if (!Config.isGroupAlreadyLinked(g, r)) {
                            Config.createGroup(r, g);
                            m.getTextChannel().sendMessage(ChatColor.DARK_AQUA + g + " and " + r.getName() + " have been linked!").queue();
                        }
                    }
                } else {
                    if (ExtremeDMC.instance.mainGuild.getRolesByName(n, true) != null) {
                        List<Role> rs = ExtremeDMC.instance.mainGuild.getRolesByName(n, true);
                        if (rs.size() > 1) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("More than one role was found, type in the corresponding number in a new message to link that role.");
                            HashMap<Integer, HashMap<Role, String>> keys = new HashMap<>();

                            for (int i = 0; i < rs.size(); i++) {
                                Role r = rs.get(i);
                                try {
                                    sb.append("\n").append(i + 1).append(". ").append(r.getName()).append(" (").append(r.getId()).append(") - Color (RGB): ").append(r.getColor().getRed()).append(", ").append(r.getColor().getGreen()).append(", ").append(r.getColor().getBlue());
                                } catch (NullPointerException ex) {
                                    sb.append("(None)");
                                }
                                HashMap<Role, String> keys2 = new HashMap<>();
                                keys2.put(r, g);
                                keys.put(i + 1, keys2);
                            }
                            m.getTextChannel().sendMessage(sb.toString()).queue();
                            HashMap<String, HashMap<Integer, HashMap<Role, String>>> h = new HashMap<>();
                            h.put("grouplink", keys);
                            AwaitSubcommand.sections.put(m.getAuthor(), h);
                        } else {
                            Role r = rs.get(0);
                            if (!Config.isGroupAlreadyLinked(g, r)) {
                                Config.createGroup(r, g);
                                m.getTextChannel().sendMessage(ChatColor.DARK_AQUA + g + " and " + r.getName() + " have been linked!").queue();
                            }
                        }
                    }
                }
            }
        }
    }
}
