//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.api;

import java.io.*;
import net.labymod.support.util.*;
import net.labymod.discordapp.*;
import com.sun.jna.*;

public interface DiscordRPCLibrary extends Library
{
    public static final int DISCORD_REPLY_NO = 0;
    public static final int DISCORD_REPLY_YES = 1;
    public static final int DISCORD_REPLY_IGNORE = 2;
    public static final NativeLibrary nativeLibrary = loadLibrary(DiscordApp.libraryFile);
    public static final DiscordRPCLibrary instance = (DiscordRPCLibrary)Native.loadLibrary(DiscordLibraryProvider.JNA_LIBRARY_NAME, (Class)DiscordRPCLibrary.class);
    
    void Discord_Initialize(final String p0, final DiscordEventHandlers p1, final int p2, final String p3);
    
    void Discord_Shutdown();
    
    void Discord_RunCallbacks();
    
    void Discord_UpdatePresence(final DiscordRichPresence p0);
    
    void Discord_Respond(final String p0, final int p1);
    
    default void initialize(final String applicationId, final DiscordEventHandlers handlers, final int autoRegister, final String optionalSteamId) {
        DiscordRPCLibrary.instance.Discord_Initialize(applicationId, handlers, autoRegister, optionalSteamId);
    }
    
    default void shutdown() {
        DiscordRPCLibrary.instance.Discord_Shutdown();
    }
    
    default void runCallbacks() {
        DiscordRPCLibrary.instance.Discord_RunCallbacks();
    }
    
    default void updatePresence(final DiscordRichPresence presence) {
        DiscordRPCLibrary.instance.Discord_UpdatePresence(presence);
    }
    
    default void respond(final String userid, final int reply) {
        DiscordRPCLibrary.instance.Discord_Respond(userid, reply);
    }
    
    default NativeLibrary loadLibrary(final File file) {
        if (file != null) {
            final File path = file.getParentFile();
            Debug.log(Debug.EnumDebugMode.DISCORD, "Add search path: " + path.getAbsolutePath());
            NativeLibrary.addSearchPath(DiscordLibraryProvider.JNA_LIBRARY_NAME, path.getAbsolutePath());
        }
        return NativeLibrary.getInstance(DiscordLibraryProvider.JNA_LIBRARY_NAME);
    }
}
