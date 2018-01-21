package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

public enum PermissionEnums {

    // TODO add new commands
    VIEW_BOT_STATUS("edmc.command.status", "Views bot status.", "/edmc status", "status"),
    HELP("edmc.command.help", "Displays help for EDMC.", "/edmc help [Page no.]", "help"),
    APPROVE("edmc.command.approve", "Approves a link request.", "/edmc approve <User ID>", "approve"),
    RELOAD("edmc.command.reload", "Reloads the configuration for EDMC.", "/edmc reload", "reload"),
    WHOIS("edmc.command.whois", "Checks if a user/player has their Discord and MC accounts linked.", "/edmc whois <MC|Discord> <User ID|Username|Player name>", "whois"),
    LINK("edmc.command.link", "Links your MC account to your Discord account.", "/edmc link <Username|Nickname|User ID>", "link"),
    DENY("edmc.command.deny", "Denies a link request.", "/edmc deny <User ID>", "deny"),
    GLINK("edmc.command.linkgroup", "Links a group to a role.", "/edmc glink <Group Name> <Role Name|ID>", "glink"),
    PAGE("edmc.command.page", "When using linking or group linking, will turn the page if necessary.", "/edmc page <#>", "page"),
    STAFFCHAT("edmc.command.staffchat", "Switches you in and out of the staff chat.", "/edmc staffchat", "staffchat"),
    UNLINK("edmc.command.unlink", "Unlinks your account.", "/edmc unlink", "unlink" + "");

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
