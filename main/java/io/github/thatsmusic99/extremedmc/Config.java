package io.github.thatsmusic99.extremedmc;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Config {

    private static File dataF;
    private static File configF;
    private static double version = 0.1;

    public static boolean hasDiscord(OfflinePlayer p) {
        try {
            return ExtremeDMC.data.getConfigurationSection("data").getConfigurationSection(p.getUniqueId().toString()).getString("discordid") != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static UserAccount create(OfflinePlayer p, User u) {
        ExtremeDMC.data.addDefault("data." + p.getUniqueId().toString() + ".discordid", u.getId());
        ExtremeDMC.data.addDefault("data." + p.getUniqueId().toString() + ".discordname", u.getName() + "#" + u.getDiscriminator());
        ExtremeDMC.data.addDefault("data." + p.getUniqueId().toString() + ".playername", p.getName());
        ExtremeDMC.data.options().copyDefaults(true);
        saveData();
        return new UserAccount(u, p);
    }
    public static void remove(OfflinePlayer p) {
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
            try {
                ExtremeDMC.instance.createConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ExtremeDMC.config = YamlConfiguration.loadConfiguration(configF);
    }
    public static void reloadData() {
        if (dataF == null) {
            dataF = new File(ExtremeDMC.instance.getDataFolder(), "data.yml");
            try {
                dataF.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public static User getDiscord(OfflinePlayer p) {
        return ExtremeDMC.jda.getUserById(ExtremeDMC.data.getConfigurationSection("data." + p.getUniqueId().toString()).getString(".discordid"));
    }

    public static OfflinePlayer getPlayer(User u) {
        for (String s : ExtremeDMC.data.getConfigurationSection("data").getKeys(true)) {
            ConfigurationSection cs = ExtremeDMC.data.getConfigurationSection("data").getConfigurationSection(s);
            try {
                if (cs.getString("discordid").equals(u.getId())) {
                    return Bukkit.getOfflinePlayer(UUID.fromString(cs.getName()));
                }
            } catch (NullPointerException ex) {
                break;
            }
        }
        return null;
    }

    public static void createGroup(Role r, String s) {
        ExtremeDMC.data.addDefault("groups." + r.getId().trim() + ".staff", false);
        if (ExtremeDMC.data.getStringList("groups." + r.getId().trim() + ".links") == null) {
            ExtremeDMC.data.addDefault("groups." + r.getId().trim() + ".links", new ArrayList<>(Collections.singleton(s)));
        } else {
            List<String> l = ExtremeDMC.data.getStringList("groups." + r.getId().trim() + ".links");
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

    public static List<String> getLinkedGroups(Role r) {
        return ExtremeDMC.data.getStringList("groups." + r.getId() + ".links");
    }

    public static boolean isGroupLinked(String s) {
        for (String st : ExtremeDMC.data.getConfigurationSection("groups").getKeys(false)) {
            if (ExtremeDMC.data.getStringList("groups." + st + ".links").contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGroupAlreadyLinked(String s, Role r) {
        return ExtremeDMC.data.getStringList("groups." + r.getId() + ".links").contains(s);
    }

    public static void checkValuesForConfigs() {
        FileWriter fw = null;
        BufferedWriter bw = null;
        File configF = new File(ExtremeDMC.instance.getDataFolder(), "config.yml");
        try {
            if (ExtremeDMC.instance.getConfig().getDouble("version") == 0.0) {
                fw = new FileWriter(configF, true);
                bw = new BufferedWriter(fw);
                bw.write("\n\n# Banning players that have been banned on the Discord server/vice versa\n" +
                        "# If a player has been banned on the Discord server, then they can be banned automatically IG if this is enabled.\n" +
                        "# If a user has been banned on the main MC server, their Discord account will be banned automatically if enabled.\n" +
                        "# Recommended for security purposes.\n" +
                        "ban-linked-accounts: true\n" +
                        "\n" +
                        "# DO NOT CHANGE THIS VALUE UNLESS YOU WANT THE PLUGIN TO COMPLETELY FREAK OUT AND HAVE A HEART ATTACK, AS WELL AS MOST LIKELY DIE.\n" +
                        "# I'M NOT EVEN LAUGHING.\n" +
                        "version: 0.1");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        ExtremeDMC.config = YamlConfiguration.loadConfiguration(configF);

    }


}
