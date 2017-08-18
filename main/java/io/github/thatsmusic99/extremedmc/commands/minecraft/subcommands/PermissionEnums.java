package io.github.thatsmusic99.extremedmc.commands.minecraft.subcommands;

public enum PermissionEnums {

    VIEW_BOT_STATUS("edmc.command.viewbot", "Views bot status.", "/edmc bot", "bot"),
    HELP("edmc.command.help", "Displays help for EDMC.", "/edmc help [Page no.]", "help");

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
