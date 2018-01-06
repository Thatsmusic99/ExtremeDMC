package io.github.thatsmusic99.extremedmc.listeners;

import io.github.thatsmusic99.extremedmc.Config;
import io.github.thatsmusic99.extremedmc.ExtremeDMC;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DiscordJoinLeaveEvents extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        if (ExtremeDMC.instance.getConfig().getBoolean("join-leave")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("[" + ChatColor.DARK_AQUA + "Discord" + ChatColor.RESET + "] " + ChatColor.AQUA + e.getMember().getEffectiveName() + " has joined the Discord server!");
            }
        }
        if (ExtremeDMC.instance.getConfig().getBoolean("verify")) {
            if (!Config.isPlayer(e.getUser())) {
                ExtremeDMC.instance.mainGuild.getController().addRolesToMember(e.getMember(), ExtremeDMC.instance.getVerifyRole()).queue();
                ExtremeDMC.instance.setUnverifiedPerms();
                e.getUser().openPrivateChannel().complete().sendMessage("**Hi there!** Currently you need to verify you're a part of **" + e.getGuild().getName() + "**'s MC server to participate in the Discord server. Please ask a member of staff about joining in game to do this.").queue();
            }
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent e) {
        if (ExtremeDMC.instance.getConfig().getBoolean("join-leave")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("[" + ChatColor.DARK_AQUA + "Discord" + ChatColor.RESET + "] " + ChatColor.AQUA + e.getUser().getName() + " has left the Discord server!");
            }
        }
    }
}
