//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.api;

import java.util.*;
import com.sun.jna.*;

public class DiscordJoinRequest extends Structure
{
    public String userId;
    public String username;
    public String discriminator;
    public String avatar;
    
    public DiscordJoinRequest() {
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("userId", "username", "discriminator", "avatar");
    }
    
    public DiscordJoinRequest(final String userId, final String username, final String discriminator, final String avatar) {
        this.userId = userId;
        this.username = username;
        this.discriminator = discriminator;
        this.avatar = avatar;
    }
    
    public DiscordJoinRequest(final Pointer peer) {
        super(peer);
    }
    
    public static class ByReference extends DiscordJoinRequest implements Structure.ByReference
    {
    }
    
    public static class ByValue extends DiscordJoinRequest implements Structure.ByValue
    {
    }
}
