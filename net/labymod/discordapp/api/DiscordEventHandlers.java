//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.discordapp.api;

import java.util.*;
import com.sun.jna.*;

public class DiscordEventHandlers extends Structure
{
    public ready_callback ready;
    public disconnected_callback disconnected;
    public errored_callback errored;
    public joinGame_callback joinGame;
    public spectateGame_callback spectateGame;
    public joinRequest_callback joinRequest;
    
    public DiscordEventHandlers() {
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
    }
    
    public DiscordEventHandlers(final ready_callback ready, final disconnected_callback disconnected, final errored_callback errored, final joinGame_callback joinGame, final spectateGame_callback spectateGame, final joinRequest_callback joinRequest) {
        this.ready = ready;
        this.disconnected = disconnected;
        this.errored = errored;
        this.joinGame = joinGame;
        this.spectateGame = spectateGame;
        this.joinRequest = joinRequest;
    }
    
    public DiscordEventHandlers(final Pointer peer) {
        super(peer);
    }
    
    public static class ByReference extends DiscordEventHandlers implements Structure.ByReference
    {
    }
    
    public static class ByValue extends DiscordEventHandlers implements Structure.ByValue
    {
    }
    
    public interface joinRequest_callback extends Callback
    {
        void apply(final DiscordJoinRequest p0);
    }
    
    public interface spectateGame_callback extends Callback
    {
        void apply(final String p0);
    }
    
    public interface joinGame_callback extends Callback
    {
        void apply(final String p0);
    }
    
    public interface errored_callback extends Callback
    {
        void apply(final int p0, final String p1);
    }
    
    public interface disconnected_callback extends Callback
    {
        void apply(final int p0, final String p1);
    }
    
    public interface ready_callback extends Callback
    {
        void apply(final DiscordUser p0);
    }
}
