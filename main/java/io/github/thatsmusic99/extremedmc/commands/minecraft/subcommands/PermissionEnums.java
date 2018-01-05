package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

public enum PermissionEnums {

    VIEW_BOT_STATUS("edmc.command.status", "Views bot status.", "/edmc status", "status"),
    HELP("edmc.command.help", "Displays help for EDMC.", "/edmc help [Page no.]", "help"),
    APPROVE("edmc.command.approve", "Approves a link request.", "/edmc approve <User ID>", "approve"),
    RELOAD("edmc.command.reload", "Reloads the configuration for EDMC.", "/edmc reload", "reload"),
    WHOIS("edmc.command.whois", "Checks if a user/player has their Discord and MC accounts linked.", "/edmc whois <MC|Discord> <User ID|Username|Player name>", "whois"),
    LINK("edmc.command.link", "Links your MC account to your Discord account.", "/edmc link <Username|Nickname|User ID>", "link");

    String p;
    String d;
    String c;
    String s;

    PermissionEnums(String p, String d, String c, String s) {
        this.p = p;
        this.d = d;
        this.c = c;
        this.s = s;
    }
}
