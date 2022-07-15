//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation.model;

public class BoneAnimation
{
    public Keyframes rotation;
    public Keyframes position;
    public Keyframes scale;
    
    public BoneAnimation() {
        this.rotation = new Keyframes(0.0f, 0.0f, 0.0f);
        this.position = new Keyframes(0.0f, 0.0f, 0.0f);
        this.scale = new Keyframes(1.0f, 1.0f, 1.0f);
    }
    
    public long getLength() {
        return Math.max(Math.max(this.position.getLength(), this.rotation.getLength()), this.scale.getLength());
    }
}
