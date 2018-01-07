package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import com.iwebpp.crypto.TweetNaclFast;
import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.utils.PagedLists;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GroupLinkCommand {

    public static void linkGroup(String g, String n, Message m) {
        if (m.getMember().hasPermission(Permission.MANAGE_PERMISSIONS)) {
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
                            HashMap<Integer, Role> keys = new HashMap<>();
                            for (int i = 0; i < rs.size(); i++) {
                                Role r = rs.get(i);
                                sb.append("\n" + i + ". " + r.getName());
                                keys.put(i, r);
                            }
                            AwaitSubcommand.sections.put(m.getAuthor(), keys);
                        } else {
                            Role r = rs.get(0);
                            if (!Config.isGroupAlreadyLinked(g, r)) {
                                Config.createGroup(r, g);
                                //p.sendMessage(ChatColor.DARK_AQUA + g + " and " + r.getName() + " have been linked!");
                            }
                        }
                    }
                }
            }
        }
    }
}
