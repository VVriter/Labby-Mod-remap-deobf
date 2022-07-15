//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.util;

import java.io.*;
import net.labymod.support.gui.*;
import net.labymod.utils.*;
import java.awt.*;
import org.apache.logging.log4j.*;

public class Debug
{
    public static final File DEBUG_FILE;
    private static boolean active;
    private static final Logger logger;
    private static GuiDebugConsole debugConsoleGui;
    
    public static void init() {
        if (Debug.DEBUG_FILE.exists()) {
            Debug.DEBUG_FILE.delete();
            openDebugConsole();
        }
        else {
            final String debugMode = System.getProperty("debugMode");
            if (debugMode != null) {
                if (debugMode.equals("true")) {
                    Debug.active = true;
                    Debug.logger.info("[Debug] Started debug logging");
                }
                else if (debugMode.equals("GUI")) {
                    openDebugConsole();
                }
                else if (!debugMode.equals("false")) {
                    Debug.logger.info("[Debug] Invalid debug mode: " + debugMode);
                }
            }
        }
    }
    
    public static boolean isActive() {
        return Debug.active;
    }
    
    public static void openDebugConsole() {
        if (Debug.debugConsoleGui != null) {
            Debug.debugConsoleGui.toFront();
            return;
        }
        try {
            Debug.debugConsoleGui = new GuiDebugConsole((Consumer)new Consumer<Boolean>() {
                @Override
                public void accept(final Boolean accepted) {
                    Debug.debugConsoleGui = null;
                }
            });
        }
        catch (HeadlessException e) {
            e.printStackTrace();
        }
        Debug.active = true;
        Debug.logger.info("[Debug] Started debug GUI");
    }
    
    public static void log(final EnumDebugMode debugMode, final String message) {
        if (!Debug.active) {
            return;
        }
        Debug.logger.info("{}", new Object[] { "[Debug] [" + debugMode.name() + "] " + message });
    }
    
    public static GuiDebugConsole getDebugConsoleGui() {
        return Debug.debugConsoleGui;
    }
    
    static {
        DEBUG_FILE = new File("LabyMod/", ".debug");
        Debug.active = false;
        logger = LogManager.getLogger();
    }
    
    public enum EnumDebugMode
    {
        ADDON, 
        API, 
        UPDATER, 
        COSMETIC_IMAGE_MANAGER, 
        USER_MANAGER, 
        MINECRAFT, 
        TEAMSPEAK, 
        LABYMOD_CHAT, 
        LABYCONNECT_NEO, 
        ACCOUNT_MANAGER, 
        CONFIG_MANAGER, 
        ASM, 
        PLUGINMESSAGE, 
        GENERAL, 
        LANGUAGE, 
        CCP, 
        EMOTE, 
        STICKER, 
        DISCORD, 
        LABY_PLAY, 
        REQUEST, 
        MICROSOFT, 
        REMOTE_COSMETIC;
    }
}
