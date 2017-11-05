package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class LinkCommand {

    io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand l = io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.class.newInstance();

    public LinkCommand() throws IllegalAccessException, InstantiationException {
    }

    public static void link(User u, String name, TextChannel tc) {
        if (!Config.isPlayer(u)) {
            if (io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.up.containsKey(u)) {
                if (io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.up.get(u).getName().equalsIgnoreCase(name)) {
                    tc.sendMessage("**You've already sent this player a link request for this period!**").queue();
                    return;
                }
            }
            if (ExtremeDMC.getPlayer(name) == null) {
                tc.sendMessage("**That player couldn't be found!** Make sure you include the whole name!").queue();
                return;
            }
            if (!ExtremeDMC.getPlayer(name).isOnline()) {
                tc.sendMessage("**That player isn't online!** You can only link players that are online.").queue();
                return;
            }
            if (ExtremeDMC.getPlayer(name).hasPermission("edmc.command.link")) {
                tc.sendMessage("**That player can't have their account linked!** This is due to the lack of permissions!").queue();
                return;
            }
            if (io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.containsKey(u)) {
                tc.sendMessage("**You already have a link request pending!** Request linked to " + io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.get(u).getName()).queue();
                return;
            }



        } else {
            tc.sendMessage("**Your Discord account is already linked to " + Config.getPlayer(u).getName() + "!**").queue();
        }
    }
}
