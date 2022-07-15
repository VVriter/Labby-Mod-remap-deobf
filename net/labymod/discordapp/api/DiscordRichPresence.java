//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.api;

import java.util.*;
import com.sun.jna.*;

public class DiscordRichPresence extends Structure
{
    public String state;
    public String details;
    public long startTimestamp;
    public long endTimestamp;
    public String largeImageKey;
    public String largeImageText;
    public String smallImageKey;
    public String smallImageText;
    public String partyId;
    public int partySize;
    public int partyMax;
    public String matchSecret;
    public String joinSecret;
    public String spectateSecret;
    public byte instance;
    
    public DiscordRichPresence() {
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance");
    }
    
    public DiscordRichPresence(final Pointer peer) {
        super(peer);
    }
    
    public static class ByReference extends DiscordRichPresence implements Structure.ByReference
    {
    }
    
    public static class ByValue extends DiscordRichPresence implements Structure.ByValue
    {
    }
}
