package io.github.thatsmusic99.extremedmc;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Config {

    private static File dataF;
    private static File configF;

    public static boolean hasDiscord(Player p) {
        try {
            return ExtremeDMC.data.getConfigurationSection("data").getConfigurationSection(p.getUniqueId().toString()).getString("discordid") != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static UserAccount create(Player p, User u) {
        ExtremeDMC.data.addDefault("data." + p.getUniqueId().toString() + ".discordid", u.getId());
        ExtremeDMC.data.addDefault("data." + p.getUniqueId().toString() + ".discordname", u.getName() + "#" + u.getDiscriminator());
        ExtremeDMC.data.addDefault("data." + p.getUniqueId().toString() + ".playername", p.getName());
        ExtremeDMC.data.options().copyDefaults(true);
        saveData();
        return new UserAccount(u, p);
    }
    public static void remove(Player p) {
        ExtremeDMC.data.set("data." + p.getUniqueId().toString(), null);
        ExtremeDMC.data.options().copyDefaults(true);
        saveData();
    }
    public static boolean isPlayer(User u) {
        try {
            for (String s : ExtremeDMC.data.getConfigurationSection("data").getKeys(true)) {
                ConfigurationSection cs = ExtremeDMC.data.getConfigurationSection("data").getConfigurationSection(s);
                if (cs.getString("discordid").equals(u.getId())) {
                    return true;
                }
            }
        } catch (NullPointerException ex) {
            return false;
        }

        return false;
    }
    public static void reloadConfig() {
        if (configF == null) {
            configF = new File(ExtremeDMC.instance.getDataFolder(), "config.yml");
        }
        ExtremeDMC.config = YamlConfiguration.loadConfiguration(configF);
    }
    public static void reloadData() {
        if (dataF == null) {
            dataF = new File(ExtremeDMC.instance.getDataFolder(), "data.yml");
        }
        ExtremeDMC.data = YamlConfiguration.loadConfiguration(dataF);
    }
    public static void saveData() {
        dataF = new File(ExtremeDMC.instance.getDataFolder(), "data.yml");
        if (ExtremeDMC.data == null) {
                return;
        }
        try {
            ExtremeDMC.data.save(dataF);
        } catch (IOException e) {
            ExtremeDMC.instance.getLogger().severe("Couldn't save data.yml!");
            e.printStackTrace();
        }
    }
    public static User getDiscord(Player p) {
        return ExtremeDMC.jda.getUserById(ExtremeDMC.data.getConfigurationSection("data." + p.getUniqueId().toString()).getString(".discordid"));
    }

    public static Player getPlayer(User u) {
        for (String s : ExtremeDMC.data.getConfigurationSection("data").getKeys(true)) {
            ConfigurationSection cs = ExtremeDMC.data.getConfigurationSection("data").getConfigurationSection(s);
            if (cs.getString("discordid").equals(u.getId())) {
                return Bukkit.getPlayer(UUID.fromString(cs.getName()));
            }

        }
        return null;
    }

    public static void createGroup(Role r, String s) {
        ExtremeDMC.data.addDefault("groups." + r.getId() + ".staff", false);
        if (ExtremeDMC.data.getStringList("groups." + r.getId() + ".links") != null) {
            ExtremeDMC.data.addDefault("groups." + r.getId() + ".links", new ArrayList<>(Collections.singleton(s)));
        } else {
            List<String> l = ExtremeDMC.data.getStringList("groups." + r.getId() + ".links");
            l.add(s);
            ExtremeDMC.data.set("groups." + r.getId() + ".links", l);
        }
        ExtremeDMC.data.options().copyDefaults(true);
        saveData();
    }

    public static boolean isRoleLinked(Role r) {
        if (ExtremeDMC.data.getStringList("groups." + r.getId() + ".links") != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isGroupLinked(String s) {
        for (String st : ExtremeDMC.data.getConfigurationSection("groups").getKeys(false)) {
            if (ExtremeDMC.data.getStringList("groups." + st + ".links").contains(s)) {
                return true;
            }
        }
        return false;
    }


}
