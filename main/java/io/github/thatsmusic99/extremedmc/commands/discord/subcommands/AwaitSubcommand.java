package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.PageSwitch;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;

public class AwaitSubcommand extends ListenerAdapter {

    public static HashMap<User, HashMap<String, HashMap<Integer, HashMap<Role, String>>>> sections = new HashMap<>();
    public static HashMap<User, HashMap<Integer, String>> users = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (sections.containsKey(e.getAuthor())) {
            if (e.getMessage().getContentDisplay().trim().matches("^[0-9]+$")) {
                HashMap<String, HashMap<Integer, HashMap<Role, String>>> r = sections.get(e.getAuthor());
                if (PageSwitch.getFirstKeyInHashmap(r).equals("grouplink")) {
                    HashMap<Integer, HashMap<Role, String>> s = r.get("grouplink");
                    if (s.containsKey(Integer.parseInt(e.getMessage().getContentDisplay().trim()))) {
                        HashMap<Role, String> p = s.get(Integer.parseInt(e.getMessage().getContentDisplay().trim()));
                        Role ro = (Role) PageSwitch.getFirstKeyInHashmap(p);
                        String g = p.get(ro);
                        if (!Config.isGroupAlreadyLinked(g, ro)) {
                            Config.createGroup(ro, g);
                            e.getTextChannel().sendMessage(g + " and " + ro.getName() + " have been linked!").queue();
                            sections.remove(e.getAuthor());
                        } else {
                            e.getTextChannel().sendMessage(g + " and " + ro.getName() + " have already been linked!").queue();
                        }
                    }
                }
            } else if (e.getMessage().getContentDisplay().equalsIgnoreCase("cancel")) {
                sections.remove(e.getAuthor());
                e.getTextChannel().sendMessage("**Operation cancelled.**").queue();
            }
        } else if (users.containsKey(e.getAuthor())) {
            if (e.getMessage().getContentDisplay().trim().matches("^[0-9]+$")) {
                HashMap<Integer, String> s = users.get(e.getAuthor());
                if (s.containsKey(Integer.parseInt(e.getMessage().getContentDisplay().trim()))) {
                    User u = ExtremeDMC.instance.mainGuild.getMemberById(s.get(Integer.parseInt(e.getMessage().getContentDisplay().trim()))).getUser();
                    String[] a = new String[3];
                    a[0] = "";
                    a[1] = "discord";
                    a[2] = u.getId();
                    WhoisCommand.whois(a, e.getTextChannel(), e.getAuthor());
                    users.remove(e.getAuthor());
                }
            } else if (e.getMessage().getContentDisplay().equalsIgnoreCase("cancel")) {
                users.remove(e.getAuthor());
                e.getTextChannel().sendMessage("**Operation cancelled.**").queue();
            }
        }
     }
}
