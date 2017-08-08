package io.github.thatsmusic99.extremedmc.commands.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
        if (c.getName().equalsIgnoreCase("edmc")) {
            if (cs.hasPermission("edmc.command")) {
                if (args.length == 0) {
                    if (cs.hasPermission("edmc.command.help")) {

                    }
                }
            }
        }
        return false;
    }
}
