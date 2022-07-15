//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.enums;

import net.labymod.main.lang.*;

public enum EnumDisplayType
{
    INGAME(true, true, false, true), 
    ESCAPE(false, true, true, false);
    
    private String displayName;
    private boolean attachableModules;
    private boolean attachableCenter;
    private boolean escapeRegions;
    private boolean scaling;
    
    private EnumDisplayType(final boolean attachableModules, final boolean attachableCenter, final boolean escapeRegions, final boolean scaling) {
        this.displayName = LanguageManager.translate("module_editor_display_type_" + this.name());
        this.attachableModules = attachableModules;
        this.attachableCenter = attachableCenter;
        this.escapeRegions = escapeRegions;
        this.scaling = scaling;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public boolean isAttachableModules() {
        return this.attachableModules;
    }
    
    public boolean isAttachableCenter() {
        return this.attachableCenter;
    }
    
    public boolean isEscapeRegions() {
        return this.escapeRegions;
    }
    
    public boolean isScaling() {
        return this.scaling;
    }
}
