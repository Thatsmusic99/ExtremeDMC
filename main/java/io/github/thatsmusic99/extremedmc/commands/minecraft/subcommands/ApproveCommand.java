package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ApproveCommand {

    public static void approve(Player p, User u) {
        if (LinkCommand.pu.containsValue(p)) {
            if (!Config.hasDiscord(p)) {
                Config.create(p, u);
                p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Your accounts are now linked! " + ChatColor.AQUA + "Linked to: " + u.getName() + "#" + u.getDiscriminator());
                LinkCommand.pu.remove(u);
                if (ExtremeDMC.config.getBoolean("change-nickname")) {
                    try {
                        ExtremeDMC.instance.mainGuild.getController().setNickname(ExtremeDMC.instance.mainGuild.getMember(u), p.getName()).queue();
                    } catch (PermissionException ex) {
                        ExtremeDMC.instance.getLogger().log(java.util.logging.Level.SEVERE, "Failed to change the nickname of " + ExtremeDMC.instance.mainGuild.getMember(u) + " to " + p.getName() + "! Please provide the bot with the permission " + ex.getPermission().getName() + "!");
                    }
                }
                if (ExtremeDMC.instance.getConfig().getBoolean("verify")) {
                    if (ExtremeDMC.instance.mainGuild.getMember(u).getRoles().contains(ExtremeDMC.instance.getVerifyRole())) {
                        ExtremeDMC.instance.mainGuild.getController().removeRolesFromMember(ExtremeDMC.instance.mainGuild.getMember(u), ExtremeDMC.instance.getVerifyRole()).queue();
                    }
                }
            }
        }
    }
}
