//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

@Deprecated
public class Debug
{
    @Deprecated
    public static void log(final EnumDebugMode debugMode, final String message) {
        net.labymod.support.util.Debug.log(net.labymod.support.util.Debug.EnumDebugMode.ADDON, message);
    }
    
    @Deprecated
    public enum EnumDebugMode
    {
        @Deprecated
        ADDON;
    }
}
