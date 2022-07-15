//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main;

import java.io.*;
import net.minecraft.realms.*;

public class Source
{
    public static final String ABOUT_VERSION_TYPE = "";
    public static final String ABOUT_MODID = "labymod";
    public static final String ABOUT_VERSION = "3.9.41";
    public static final String ABOUT_MC_VERSION;
    public static final int ABOUT_MC_PROTOCOL_VERSION;
    public static final String PROFILE_VERSION_NAME;
    public static File RUNNING_JAR;
    public static final String FILE_STEPS_TITLES = "assets/minecraft/labymod/data/steps.titles";
    public static final String FILE_SYMBOLS = "assets/minecraft/labymod/data/symbols.txt";
    public static final String FILE_ICON_16 = "assets/minecraft/labymod/data/icons/icon_16x16.png";
    public static final String FILE_ICON_32 = "assets/minecraft/labymod/data/icons/icon_32x32.png";
    public static final String FILE_ICON_NOTIFY_16 = "assets/minecraft/labymod/data/icons/icon_notify_16x16.png";
    public static final String FILE_ICON_NOTIFY_32 = "assets/minecraft/labymod/data/icons/icon_notify_32x32.png";
    public static final String FILE_LABYMOD_FOLDER = "LabyMod/";
    public static final String FILE_MODS_FOLDER = "mods/";
    public static final String FILE_LANGUAGE_FOLDER = "/assets/minecraft/labymod/lang/";
    public static final String FILE_CCP_FOLDER = "LabyMod/ccp/";
    public static final String FILE_NATIVES = "LabyMod/natives/";
    public static final String FILE_CONFIG = "LabyMod/LabyMod-3.json";
    public static final String FILE_GEO_CROWN = "assets/minecraft/labymod/models/crown.geo.json";
    public static final String URL_USERDATA = "https://dl.labymod.net/userdata/%s.json";
    public static final String URL_USER_WHITELIST = "https://dl.labymod.net/whitelist.bin";
    public static final String URL_EMOTEDATA = "https://dl.labymod.net/emotes/emotedata";
    public static final String URL_GROUPS = "https://dl.labymod.net/groups.json";
    public static final String URL_STICKERS = "https://dl.labymod.net/stickers.json";
    public static final String URL_CUSTOM_TEXTURES = "https://dl.labymod.net/textures/%s";
    public static final String URL_CAPE_REPORT = "https://next.api.labymod.net/cloak/report/v3";
    public static final String URL_HASTEBIN = "https://paste.labymod.net/";
    public static final String URL_HASTEBIN_API = "https://paste.labymod.net/documents";
    public static final String URL_SERVER_LIST = "https://dl.labymod.net/public_servers.json";
    public static final String URL_SERVER_GROUPS = "https://dl.labymod.net/server_groups.json";
    public static final String URL_ADVERTISEMENT_ENTRIES = "https://dl.labymod.net/advertisement/entries.json";
    public static final String URL_ADVERTISEMENT_ICONS = "https://dl.labymod.net/advertisement/icons/%s.png";
    public static final String URL_VERSIONS = "https://dl.labymod.net/versions.json";
    public static final String URL_UPDATER = "https://dl.labymod.net/latest/install/updater/updater.jar";
    public static final String URL_MC_JSON = "https://dl.labymod.net/latest/install/json/%s.json";
    public static final String URL_UPDATER_HASH = "https://dl.labymod.net/latest/install/updater/hash.json";
    public static final String URL_OFHANDLER = "https://dl.labymod.net/latest/install/ofhandler.jar";
    public static final String FILE_UPDATER = "LabyMod/Updater.jar";
    public static final String FILE_OFHANDLER_FOLDER = "LabyMod/ofhandler/";
    public static final String FILE_ACCOUNTS = "LabyMod/accounts.json";
    public static final String FILE_PINS = "LabyMod/pins.json";
    public static final String FILE_OAUTH = "LabyMod/oauth.jar";
    public static final String URL_OAUTH = "https://dl.labymod.net/latest/install/oauth/oauth.jar";
    public static final String URL_ADDONS = "https://dl.labymod.net/addons.json";
    public static final String URL_ADDON_DOWNLOAD = "https://dl.labymod.net/latest/?file=%s&a=1";
    public static final String URL_ADDON_TEXTURE = "https://dl.labymod.net/latest/addons/%s/icon.png";
    public static final String URL_DISABLED_ADDONS = "https://dl.labymod.net/disabled_addons.json";
    public static final String URL_REMOTE_COSMETICS = "https://dl.labymod.net/cosmetics/";
    public static final String URL_REMOTE_COSMETICS_INDEX = "https://dl.labymod.net/cosmetics/index.json";
    public static final String URL_REMOTE_COSMETICS_GEOMETRY = "https://dl.labymod.net/cosmetics/%s/geo.json";
    public static final String URL_REMOTE_COSMETICS_ANIMATION = "https://dl.labymod.net/cosmetics/%s/animation.json";
    public static final String URL_REMOTE_COSMETICS_TEXTURES = "https://dl.labymod.net/cosmetics/%s/textures/%s.png";
    @Deprecated
    public static final String URL_MINOTAR = "https://minotar.net/helm/%s/16.png";
    public static final String URL_DASHBOARD_LOGIN = "https://www.labymod.net/key/?id=%s&pin=%s";
    public static final String URL_DASHBOARD = "https://www.labymod.net/dashboard";
    public static final String FILE_CHATLOG = "LabyMod/chatlog/%s.log";
    public static final String CHATSERVER_IP = "chat.labymod.net";
    public static final String CHATSERVER_IP_2 = "chat2.labymod.net";
    public static final int CHATSERVER_PORT = 30336;
    public static final int CHATSERVER_MAX_MESSAGES = 300;
    public static final short CHATSERVER_PACKET_VERSION = 26;
    public static final String LABYCONNECT_NEO_HOST = "connect.labymod.net";
    public static final int LABYCONNECT_NEO_PORT = 23374;
    public static final String URL_LABYCONNECT_NATIVES = "https://dist.labymod.net/api/v1/maven/release/com/labymedia/lc-client-java/%s/%s";
    public static final String URL_LABYCONNECT_STATE = "https://dl.labymod.net/labyconnect/state.json";
    public static final String URL_SUBSCRIBER_API = "https://dl.labymod.net/subcounter.json";
    public static final String SOCIALBLADE_API_CHANNEL_PAGE = "https://socialblade.com/youtube/";
    public static final String URL_DISCORD_LIBRARY = "https://dl.labymod.net/latest/install/discord/%s.%s";
    public static final String URL_DISCORD_IMAGE = "https://cdn.discordapp.com/avatars/%s/%s.png";
    public static final String URL_LABY_NET = "https://laby.net/@%s";
    
    public static String getUserAgent() {
        return "LabyMod v3.9.41" + ("".isEmpty() ? "" : " ") + " on mc" + Source.ABOUT_MC_VERSION;
    }
    
    public static String getMajorVersion() {
        final String versionSplit = Source.ABOUT_MC_VERSION.replaceFirst("\\.", "");
        return versionSplit.contains(".") ? versionSplit.split("\\.")[0] : versionSplit;
    }
    
    static {
        ABOUT_MC_VERSION = RealmsSharedConstants.VERSION_STRING;
        ABOUT_MC_PROTOCOL_VERSION = RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
        PROFILE_VERSION_NAME = "LabyMod-3-" + Source.ABOUT_MC_VERSION;
        Source.RUNNING_JAR = null;
    }
}
