//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote.model;

public enum EnumAttachedTo
{
    NONE(false), 
    BODY(false), 
    HEAD(false), 
    ARM(true), 
    LEG(true);
    
    private final boolean canBeMirrored;
    
    private EnumAttachedTo(final boolean canBeMirrored) {
        this.canBeMirrored = canBeMirrored;
    }
    
    public boolean canBeMirrored() {
        return this.canBeMirrored;
    }
}
