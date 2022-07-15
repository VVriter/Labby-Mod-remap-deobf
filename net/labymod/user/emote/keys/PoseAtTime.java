//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote.keys;

import java.beans.*;

public class PoseAtTime
{
    private EmotePose pose;
    private long offset;
    private boolean animate;
    
    public void setPose(final EmotePose pose) {
        this.pose = pose;
    }
    
    public void setOffset(final long offset) {
        this.offset = offset;
    }
    
    public void setAnimate(final boolean animate) {
        this.animate = animate;
    }
    
    public EmotePose getPose() {
        return this.pose;
    }
    
    public long getOffset() {
        return this.offset;
    }
    
    public boolean isAnimate() {
        return this.animate;
    }
    
    @ConstructorProperties({ "pose", "offset", "animate" })
    public PoseAtTime(final EmotePose pose, final long offset, final boolean animate) {
        this.pose = pose;
        this.offset = offset;
        this.animate = animate;
    }
}
