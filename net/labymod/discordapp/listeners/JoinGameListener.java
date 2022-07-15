//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.listeners;

import net.labymod.discordapp.api.*;
import net.labymod.discordapp.*;
import java.util.*;
import net.labymod.support.util.*;

public class JoinGameListener implements DiscordEventHandlers.joinGame_callback
{
    private DiscordApp discordApp;
    
    public JoinGameListener(final DiscordApp discordApp) {
        this.discordApp = discordApp;
    }
    
    public void apply(final String secret) {
        if (secret.contains(":")) {
            try {
                final String[] parts = secret.split(":");
                final UUID secretJoinKey = UUID.fromString(parts[0]);
                final String serverDomain = parts[1];
                this.discordApp.redeemJoinKey(secretJoinKey, serverDomain);
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
        else {
            Debug.log(Debug.EnumDebugMode.DISCORD, "Invalid join key: " + secret);
        }
    }
}
