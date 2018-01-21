package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands.LinkCommand;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Listener;

public class AccountLinkedEvent implements Listener {

    public void onLink(github.scarsz.discordsrv.api.events.AccountLinkedEvent e) {
        Config.create(e.getPlayer(), (User) e.getUser());
        if (ExtremeDMC.config.getBoolean("change-nickname")) {
            try {
                ExtremeDMC.instance.mainGuild.getController().setNickname(ExtremeDMC.instance.mainGuild.getMember((User) e.getUser()), Config.getPlayer((User) e.getUser()).getName()).queue();
            } catch (PermissionException ex) {
                ExtremeDMC.instance.getLogger().log(java.util.logging.Level.SEVERE, "Failed to change the nickname of " + ExtremeDMC.instance.mainGuild.getMember((User) e.getUser()) + " to " + Config.getPlayer((User) e.getUser()).getName() + "! Please provide the bot with the permission " + ex.getPermission().getName() + "!");
            }
        }
        LinkCommand.pu.remove(e.getUser());
        if (ExtremeDMC.instance.getConfig().getBoolean("verify")) {
            if (ExtremeDMC.instance.mainGuild.getMember((User) e.getUser()).getRoles().contains(ExtremeDMC.instance.getVerifyRole())) {
                ExtremeDMC.instance.mainGuild.getController().removeRolesFromMember(ExtremeDMC.instance.mainGuild.getMember((User) e.getUser()), ExtremeDMC.instance.getVerifyRole()).queue();
            }
        }
    }

}
