//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry;

import net.labymod.user.cosmetic.animation.*;

public class AnimationStorage
{
    public AnimationController controller;
    public boolean lastSneaking;
    public boolean lastMoving;
    public long nextTriggerTime;
    
    public AnimationStorage() {
        this.controller = new AnimationController();
        this.lastSneaking = false;
        this.lastMoving = false;
    }
}
