package io.github.thatsmusic99.extremedmc;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.entity.Player;

public class Config {

    public static boolean hasDiscord(Player p) {
        return ExtremeDMC.data.getConfigurationSection("data").getConfigurationSection(p.getUniqueId().toString()).getString("Discord ID") != null;
    }

    public static UserAccount create(Player p, User u) {
        ExtremeDMC.data.getConfigurationSection("data").createSection(p.getUniqueId().toString()).addDefault("Discord ID", u.getId());
        return new UserAccount(u, p);
    }
}
