package io.github.thatsmusic99.extremedmc.commands.minecraft;

import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.*;
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
                        HelpCommand.helpNoArgs(cs);
                        return true;
                    }
                } else {
                    switch (args[0].toLowerCase()) {
                        case "link":
                            if (args.length == 1) {

                            } else if (args.length == 2) {
                                if (args[1].matches("^[0-9]+$")) {
                                    LinkCommand.linkSelfID((Player) cs, args[1]);
                                    return true;
                                } else {
                                    LinkCommand.linkSelfName((Player) cs, args[1]);
                                    return true;
                                }
                            }
                        case "whois":
                            if (cs.hasPermission("edmc.command.whois")) {
                                if (args.length == 1) {

                                }
                                if (args.length == 2) {

                                }
                                if (args.length == 3) {
                                    WhoIsCommand.whois(args, cs);
                                    return true;
                                }
                            }
                        case "reload":
                            if (cs.hasPermission("edmc.command.reload")) {
                               ReloadCommand.reload(cs);
                               return true;
                            }
                        case "status":
                            if (cs.hasPermission("edmc.command.status")) {
                                BotStatusCommand.botStatus(cs);
                                return true;
                            }
                        case "help":
                            if (cs.hasPermission("edmc.command.help")) {
                                if (args.length > 1) {

                                } else {
                                    HelpCommand.helpNoArgs(cs);
                                    return true;
                                }
                            }
                        case "approve":
                            if (cs.hasPermission("edmc.command.approve")) {
                                if (args.length > 1) {
                                    if (args[1].matches("^[0-9]+$")) {
                                        if (ExtremeDMC.jda.getUserById(args[1]) != null) {
                                            if (cs instanceof Player) {
                                                ApproveCommand.approve((Player) cs, ExtremeDMC.jda.getUserById(args[1]));
                                            }
                                        }
                                    }
                                }
                            }

                    }
                }
            }
        }
        return false;
    }
}
