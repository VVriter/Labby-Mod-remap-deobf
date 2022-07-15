//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote.keys.provider;

import net.labymod.user.emote.keys.*;
import java.beans.*;

public class KeyFrameStorage
{
    private short id;
    private String name;
    private long timeout;
    private EmoteKeyFrame[] keyframes;
    
    public short getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getTimeout() {
        return this.timeout;
    }
    
    public EmoteKeyFrame[] getKeyframes() {
        return this.keyframes;
    }
    
    public void setId(final short id) {
        this.id = id;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setTimeout(final long timeout) {
        this.timeout = timeout;
    }
    
    public void setKeyframes(final EmoteKeyFrame[] keyframes) {
        this.keyframes = keyframes;
    }
    
    @ConstructorProperties({ "id", "name", "timeout", "keyframes" })
    public KeyFrameStorage(final short id, final String name, final long timeout, final EmoteKeyFrame[] keyframes) {
        this.id = id;
        this.name = name;
        this.timeout = timeout;
        this.keyframes = keyframes;
    }
}
