//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.listeners;

import net.labymod.discordapp.*;
import net.labymod.discordapp.api.*;
import net.labymod.support.util.*;

public class ReadyListener implements DiscordEventHandlers.ready_callback
{
    private DiscordApp discordApp;
    
    public ReadyListener(final DiscordApp discordApp) {
        this.discordApp = discordApp;
    }
    
    public void apply(final DiscordUser user) {
        this.discordApp.setConnected(true);
        final String imageURL = String.format("https://cdn.discordapp.com/avatars/%s/%s.png", user.userId, user.discriminator);
        Debug.log(Debug.EnumDebugMode.DISCORD, "Discord ready: " + user.username + "#" + user.discriminator);
        Debug.log(Debug.EnumDebugMode.DISCORD, imageURL);
    }
}
