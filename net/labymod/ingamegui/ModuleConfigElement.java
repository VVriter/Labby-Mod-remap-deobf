//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui;

import net.labymod.ingamegui.enums.*;
import java.util.*;

public class ModuleConfigElement
{
    private Set<EnumDisplayType> enabled;
    private EnumModuleRegion[] regions;
    private EnumModuleAlignment[] alignment;
    private double[] x;
    private double[] y;
    private String listedAfter;
    private String lastListedAfter;
    private boolean useExtendedSettings;
    private int scale;
    private Map<String, String> attributes;
    
    public ModuleConfigElement(final boolean enabled, final String listedAfter) {
        this.enabled = new HashSet<EnumDisplayType>();
        this.regions = new EnumModuleRegion[] { EnumModuleRegion.TOP_LEFT, EnumModuleRegion.TOP_LEFT };
        this.alignment = new EnumModuleAlignment[] { EnumModuleAlignment.DEFAULT, EnumModuleAlignment.DEFAULT };
        this.x = new double[] { 2.0, 0.0 };
        this.y = new double[] { 4.0, 0.0 };
        this.scale = 100;
        this.attributes = new HashMap<String, String>();
        if (enabled) {
            this.enabled.add(EnumDisplayType.INGAME);
        }
        this.listedAfter = listedAfter;
    }
    
    public ModuleConfigElement() {
        this.enabled = new HashSet<EnumDisplayType>();
        this.regions = new EnumModuleRegion[] { EnumModuleRegion.TOP_LEFT, EnumModuleRegion.TOP_LEFT };
        this.alignment = new EnumModuleAlignment[] { EnumModuleAlignment.DEFAULT, EnumModuleAlignment.DEFAULT };
        this.x = new double[] { 2.0, 0.0 };
        this.y = new double[] { 4.0, 0.0 };
        this.scale = 100;
        this.attributes = new HashMap<String, String>();
    }
    
    public Set<EnumDisplayType> getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final Set<EnumDisplayType> enabled) {
        this.enabled = enabled;
    }
    
    public Map<String, String> getAttributes() {
        return this.attributes;
    }
    
    public void setAttributes(final Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public String getListedAfter() {
        return this.listedAfter;
    }
    
    public void setListedAfter(final String listedAfter) {
        this.listedAfter = listedAfter;
    }
    
    public double getX(final int index) {
        return this.x[index];
    }
    
    public double[] getX() {
        return this.x;
    }
    
    public void setX(final int index, final double x) {
        this.x[index] = x;
    }
    
    public double getY(final int index) {
        return this.y[index];
    }
    
    public double[] getY() {
        return this.y;
    }
    
    public void setY(final int index, final double y) {
        this.y[index] = y;
    }
    
    public EnumModuleRegion[] getRegions() {
        return this.regions;
    }
    
    public void setRegion(final int index, final EnumModuleRegion region) {
        this.regions[index] = region;
    }
    
    public EnumModuleAlignment getAlignment(final int index) {
        return this.alignment[index];
    }
    
    public void setAlignment(final int index, final EnumModuleAlignment alignment) {
        this.alignment[index] = alignment;
    }
    
    public boolean isUsingExtendedSettings() {
        return this.useExtendedSettings;
    }
    
    public void setUseExtendedSettings(final boolean useExtendedSettings) {
        this.useExtendedSettings = useExtendedSettings;
    }
    
    public String getLastListedAfter() {
        return this.lastListedAfter;
    }
    
    public void setLastListedAfter(final String lastListedAfter) {
        this.lastListedAfter = lastListedAfter;
    }
    
    public int getScale() {
        return this.scale;
    }
    
    public void setScale(final int scale) {
        this.scale = scale;
    }
}
