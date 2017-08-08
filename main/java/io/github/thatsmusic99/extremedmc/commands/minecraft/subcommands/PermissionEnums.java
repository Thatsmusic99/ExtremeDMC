package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

public enum PermissionEnums {

    VIEW_BOT_STATUS("edmc.command.viewbot", "Views bot status.", "/edmc bot"),
    HELP("edmc.command.help", "Displays help for EDMC.", "/edmc help [Page no.]");

    String p;
    String d;
    String c;

    PermissionEnums(String p, String d, String c) {
        this.p = p;
        this.d = d;
        this.c = c;
    }
}
