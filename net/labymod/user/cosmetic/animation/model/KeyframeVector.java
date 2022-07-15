//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation.model;

public class KeyframeVector
{
    public long offset;
    public double x;
    public double y;
    public double z;
    public boolean smooth;
    
    public KeyframeVector(final long offset, final double x, final double y, final double z, final boolean smooth) {
        this.offset = offset;
        this.x = x;
        this.y = y;
        this.z = z;
        this.smooth = smooth;
    }
    
    public KeyframeVector(final long offset) {
        this(offset, 0.0, 0.0, 0.0, false);
    }
    
    public KeyframeVector invert() {
        return new KeyframeVector(this.offset, -this.x, -this.y, -this.z, this.smooth);
    }
}
