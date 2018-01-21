package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

public enum PermissionEnums {

    // TODO add new commands
    VIEW_BOT_STATUS("edmc.command.status", "Views bot status.", "/edmc status", "status", false),
    HELP("edmc.command.help", "Displays help for EDMC.", "/edmc help [Page no.]", "help", false),
    APPROVE("edmc.command.approve", "Approves a link request.", "/edmc approve <User ID>", "approve", true),
    RELOAD("edmc.command.reload", "Reloads the configuration for EDMC.", "/edmc reload", "reload", false),
    WHOIS("edmc.command.whois", "Checks if a user/player has their Discord and MC accounts linked.", "/edmc whois <MC|Discord> <User ID|Username|Player name>", "whois", false),
    LINK("edmc.command.link", "Links your MC account to your Discord account.", "/edmc link <Username|Nickname|User ID>", "link", true),
    DENY("edmc.command.deny", "Denies a link request.", "/edmc deny <User ID>", "deny", true),
    GLINK("edmc.command.linkgroup", "Links a group to a role.", "/edmc glink <Group Name> <Role Name|ID>", "glink", true),
    PAGE("edmc.command.page", "When using linking or group linking, will turn the page if necessary.", "/edmc page <#>", "page", false),
    STAFFCHAT("edmc.command.staffchat", "Switches you in and out of the staff chat.", "/edmc staffchat", "staffchat", false),
    UNLINK("edmc.command.unlink", "Unlinks your account.", "/edmc unlink", "unlink", true);

    String p;
    String d;
    String c;
    String s;
    boolean ds;

    PermissionEnums(String p, String d, String c, String s, boolean ds) {
        this.p = p;
        this.d = d;
        this.c = c;
        this.s = s;
        this.ds = ds;
    }
}
