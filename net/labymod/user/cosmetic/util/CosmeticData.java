//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.util;

import net.labymod.user.*;

public abstract class CosmeticData
{
    public static final int MAX_PRIORITY_LEVEL = 5;
    public int priorityLevel;
    
    public CosmeticData() {
        this.priorityLevel = 0;
    }
    
    public abstract boolean isEnabled();
    
    public abstract void loadData(final String[] p0) throws Exception;
    
    public void init(final User user) {
    }
    
    public void completed(final User user) {
    }
    
    public void init(final int id, final User user) {
        this.init(user);
    }
    
    public boolean isDraft() {
        return false;
    }
    
    public EnumLegacyCosmeticType getType() {
        return EnumLegacyCosmeticType.NONE;
    }
}
