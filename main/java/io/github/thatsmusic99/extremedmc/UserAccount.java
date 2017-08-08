package io.github.thatsmusic99.extremedmc;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.entity.Player;

public class UserAccount {

    private User u;
    private Player p;

    public UserAccount(User u, Player p) {
        this.u = u;
        this.p = p;
    }
    public User getDiscordUser() {
        return this.u;
    }
    public Member getDiscordMember() {
        return this.u.getMutualGuilds().get(0).getMember(getDiscordUser());
    }
    public Player getPlayer() {
        return this.p;
    }
    public boolean isOnlineMC() {
        return this.p.isOnline();
    }
    public String getDId() {
        return this.u.getId();
    }
    public String getUUID() {
        return this.p.getUniqueId().toString();
    }
    public boolean isInVoiceChannel() {
        return this.u.getMutualGuilds().get(0).getMember(getDiscordUser()).getVoiceState().inVoiceChannel();
    }
    public boolean isStaff() {
        return this.p.hasPermission("extremedmc.staff");
    }


}
