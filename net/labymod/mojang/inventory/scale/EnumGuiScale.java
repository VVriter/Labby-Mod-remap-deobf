//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.inventory.scale;

public enum EnumGuiScale
{
    DEFAULT("Default"), 
    SMALL("Small"), 
    NORMAL("Normal"), 
    LARGE("Large"), 
    AUTO("Auto");
    
    private String displayName;
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    private EnumGuiScale(final String displayName) {
        this.displayName = displayName;
    }
}
