package io.github.thatsmusic99.extremedmc.commands.discord.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.UserAccount;
import mkremins.fanciful.FancyMessage;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.ChatColor;

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
            if (!ExtremeDMC.getPlayer(name).hasPermission("edmc.command.link"))  {
                tc.sendMessage("**That player can't have their account linked!** This is due to the lack of permissions!").queue();
                return;
            }
            if (io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.containsKey(u)) {
                tc.sendMessage("**You already have a link request pending!** Request linked to " + io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.get(u).getName()).queue();
                return;
            }
            if (Config.hasDiscord(ExtremeDMC.getPlayer(name))) {
                UserAccount ua = Config.create(ExtremeDMC.getPlayer(name), Config.getDiscord(ExtremeDMC.getPlayer(name)));
                tc.sendMessage("**This player already has their account linked!** Linked to: " + ua.getDiscordUser().getName() + "#" + ua.getDiscordUser().getDiscriminator()).queue();
            }
            io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.put(u, ExtremeDMC.getPlayer(name));
            tc.sendMessage("**Found " + name + "!** We've sent the player a message - use it to link your accounts!").queue();
            ExtremeDMC.getPlayer(name).sendMessage(ChatColor.DARK_AQUA + "===============" + ChatColor.WHITE + ChatColor.BOLD + " [ " + ChatColor.AQUA + ChatColor.BOLD + "EXTREMEDMC" + ChatColor.WHITE + ChatColor.BOLD + " ] " + ChatColor.DARK_AQUA + "===============");
            ExtremeDMC.getPlayer(name).sendMessage(ChatColor.AQUA + "You've received an account link request from " + u.getName() + "#" + u.getDiscriminator() + "!");
            new FancyMessage().text("If this is an account you want to link, click here.").command("/edmc approve " + u.getId()).color(ChatColor.AQUA).send(ExtremeDMC.getPlayer(name));
            new FancyMessage().text("If not, then click here.").command("/edmc deny " + u.getId()).color(ChatColor.AQUA).send(ExtremeDMC.getPlayer(name));
            ExtremeDMC.getPlayer(name).sendMessage(ChatColor.AQUA + "The request will expire automatically after 2 minutes.");
            javax.swing.Timer timer = new javax.swing.Timer(120000, arg0 -> {
                if (io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.containsKey(u)) {
                    io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.up.put(u, ExtremeDMC.getPlayer(name));
                    io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand.pu.remove(u);
                }
            });
            timer.setRepeats(false);
            timer.start();



        } else {
            tc.sendMessage("**Your Discord account is already linked to " + Config.getPlayer(u).getName() + "!**").queue();
        }
    }
}
