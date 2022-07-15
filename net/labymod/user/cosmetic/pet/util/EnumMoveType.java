//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet.util;

public enum EnumMoveType
{
    BOTH(true, true), 
    MOVE_ONLY(true, false), 
    IDLE_ONLY(false, true);
    
    private boolean canMove;
    private boolean canIdle;
    
    public boolean canMove() {
        return this.canMove;
    }
    
    public boolean canIdle() {
        return this.canIdle;
    }
    
    private EnumMoveType(final boolean canMove, final boolean canIdle) {
        this.canMove = canMove;
        this.canIdle = canIdle;
    }
}
