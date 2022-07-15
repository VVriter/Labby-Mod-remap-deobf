//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet;

import net.labymod.user.cosmetic.animation.model.*;

public abstract class PetCodeAnimator
{
    private final Animation animation;
    
    public PetCodeAnimator() {
        this.animation = new Animation("");
        this.create();
    }
    
    public abstract void create();
    
    public void keyframe(final long time, final int id, final KeyframeVector rotation, final KeyframeVector position) {
        final BoneAnimation boneAnimation = this.animation.getBoneAnimation(String.valueOf(id));
        if (position != null) {
            boneAnimation.position.add(time, position);
        }
        if (rotation != null) {
            boneAnimation.rotation.add(time, rotation);
        }
    }
    
    public void keyframe(final long time, final int id, final float x, final float y, final float z, final float posX, final float posY, final float posZ, final boolean interpolation) {
        this.keyframe(time, id, new KeyframeVector(time, (double)x, (double)y, (double)z, interpolation), new KeyframeVector(time, (double)posX, (double)posY, (double)posZ, interpolation));
    }
    
    public void keyframe(final long time, final int id, final float x, final float y, final float z, final float posX, final float posY, final float posZ) {
        this.keyframe(time, id, new KeyframeVector(time, (double)x, (double)y, (double)z, false), new KeyframeVector(time, (double)posX, (double)posY, (double)posZ, false));
    }
    
    public void keyframe(final long time, final int id, final float x, final float y, final float z) {
        this.keyframe(time, id, new KeyframeVector(time, (double)x, (double)y, (double)z, false), null);
    }
    
    public void keyframe(final long time, final int id, final float x, final float y, final float z, final boolean interpolate) {
        this.keyframe(time, id, new KeyframeVector(time, (double)x, (double)y, (double)z, false), null);
    }
    
    public Animation getAnimation() {
        return this.animation;
    }
}
