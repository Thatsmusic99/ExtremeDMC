package io.github.thatsmusic99.extremedmc.commands.minecraft;

import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.BotStatusCommand;
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
                } else {
                    switch (args[0].toLowerCase()) {
                        case "link":
                            if (args.length == 1) {

                            } else if (args.length == 2) {
                                if (args[1].matches("^[0-9]+$")) {
                                    LinkCommand.linkSelfID((Player) cs, args[1]);
                                } else {
                                    LinkCommand.linkSelfName((Player) cs, args[1]);
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
                                }
                            }
                        case "reload":
                            if (cs.hasPermission("edmc.command.reload")) {
                               ReloadCommand.reload(cs);
                            }
                        case "status":
                            if (cs.hasPermission("edmc.command.status")) {
                                BotStatusCommand.botStatus(cs);
                            }
                    }

                }
            }
        }
        return false;
    }
}
