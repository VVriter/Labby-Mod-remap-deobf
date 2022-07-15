//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.listeners;

import net.labymod.discordapp.api.*;
import net.labymod.discordapp.*;
import java.util.*;
import net.labymod.support.util.*;

public class SpectateGameListener implements DiscordEventHandlers.spectateGame_callback
{
    private DiscordApp discordApp;
    
    public SpectateGameListener(final DiscordApp discordApp) {
        this.discordApp = discordApp;
    }
    
    public void apply(final String secret) {
        if (secret.contains(":")) {
            try {
                final String[] parts = secret.split(":");
                final UUID secretSpectateKey = UUID.fromString(parts[0]);
                final String serverDomain = parts[1];
                this.discordApp.redeemSpectateKey(secretSpectateKey, serverDomain);
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
        else {
            Debug.log(Debug.EnumDebugMode.DISCORD, "Invalid spectate key: " + secret);
        }
    }
}
