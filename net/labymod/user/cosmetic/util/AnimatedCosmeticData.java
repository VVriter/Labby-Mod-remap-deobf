//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.util;

public abstract class AnimatedCosmeticData extends CosmeticData
{
    private float fadeAnimation;
    
    public void updateFadeAnimation(final boolean onGround) {
        final float fps = (float)Math.max(1, bib.af());
        if (onGround && this.fadeAnimation > -1.0f) {
            this.fadeAnimation -= 0.8f / fps;
        }
        else if (!onGround && this.fadeAnimation < 1.0f) {
            this.fadeAnimation += 0.8f / fps;
        }
        this.fadeAnimation = Math.min(this.fadeAnimation, 1.0f);
        this.fadeAnimation = Math.max(this.fadeAnimation, -1.0f);
    }
    
    public float getOnGroundStrength() {
        return (this.fadeAnimation - 1.0f) / -2.0f;
    }
    
    public float getAirStrength() {
        return (this.fadeAnimation + 1.0f) / 2.0f;
    }
    
    public float getFadeAnimation() {
        return this.fadeAnimation;
    }
}
