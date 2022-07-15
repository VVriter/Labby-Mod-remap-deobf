//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.listeners;

import net.labymod.discordapp.api.*;
import net.labymod.discordapp.*;
import net.labymod.support.util.*;

public class ErroredListener implements DiscordEventHandlers.errored_callback
{
    public ErroredListener(final DiscordApp discordApp) {
    }
    
    public void apply(final int errcode, final String message) {
        Debug.log(Debug.EnumDebugMode.DISCORD, "Error: " + message + " [" + errcode + "]");
    }
}
