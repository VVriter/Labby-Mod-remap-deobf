//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote.model;

public enum EnumCosmeticType
{
    COSMETIC(false), 
    FLYING_PET(true), 
    WALKING_PET(true);
    
    private boolean pet;
    
    private EnumCosmeticType(final boolean pet) {
        this.pet = pet;
    }
    
    public boolean isPet() {
        return this.pet;
    }
}
