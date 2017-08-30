package io.github.thatsmusic99.extremedmc.commands.minecraft;

import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand;
import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.ReloadCommand;
import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.WhoIsCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
        if (c.getName().equalsIgnoreCase("edmc")) {
            if (cs.hasPermission("edmc.command")) {
                if (args.length == 0) {
                    if (cs.hasPermission("edmc.command.help")) {

                    }
                }
                if (args[0].equalsIgnoreCase("link")) {
                    if (args.length == 1) {

                    } else if (args.length == 2) {
                        LinkCommand.linkSelfName((Player) cs, args[1]);
                    }
                }
                if (args[0].equalsIgnoreCase("whois")) {
                    if (cs.hasPermission("edmc.command.whois")) {
                        if (args.length == 1) {

                        }
                        if (args.length == 2) {

                        }
                        if (args.length == 3) {
                            WhoIsCommand.whois(args, cs);
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (cs.hasPermission("edmc.command.reload")) {
                        ReloadCommand.reload((Player) cs);
                    }
                }
            }
        }
        return false;
    }
}
