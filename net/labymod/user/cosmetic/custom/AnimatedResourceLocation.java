//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom;

public class AnimatedResourceLocation
{
    private final String path;
    private final long delay;
    private final nf[] resourceLocations;
    private DepthMap depthMap;
    
    public AnimatedResourceLocation(final String path) {
        this(path, 1, 50L);
    }
    
    public AnimatedResourceLocation(final String path, final int frames, final long delay) {
        this.depthMap = null;
        this.path = path;
        this.delay = Math.max(1L, Math.min(5000L, delay));
        this.resourceLocations = new nf[frames];
        for (int i = 0; i < frames; ++i) {
            this.resourceLocations[i] = new nf(path + "_" + i);
        }
    }
    
    public nf getDefault() {
        return this.get(0);
    }
    
    public nf get(final int frame) {
        return (frame < 0 || frame > this.resourceLocations.length - 1) ? ((this.resourceLocations.length == 0) ? null : this.resourceLocations[0]) : this.resourceLocations[frame];
    }
    
    public String getPath() {
        return this.getDefault().a();
    }
    
    public nf[] getAllFrames() {
        return this.resourceLocations;
    }
    
    public int getFrameAmount() {
        return this.resourceLocations.length;
    }
    
    public nf getFrameAtCurrentTime() {
        return this.getFrameAtTime(System.currentTimeMillis());
    }
    
    public DepthMap getDepthMap() {
        return this.depthMap;
    }
    
    public void setDepthMap(final DepthMap depthMap) {
        this.depthMap = depthMap;
    }
    
    public nf getFrameAtTime(final long timestamp) {
        final long ticks = timestamp / this.delay;
        final int frame = (int)(ticks % this.getFrameAmount());
        return this.get(frame);
    }
}
