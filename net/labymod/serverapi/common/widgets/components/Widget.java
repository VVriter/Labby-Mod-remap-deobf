//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components;

import net.labymod.serverapi.common.widgets.util.*;

public abstract class Widget
{
    protected int id;
    private Anchor anchor;
    private double offsetX;
    private double offsetY;
    
    public Widget(final int id, final Anchor anchor, final double offsetX, final double offsetY) {
        this.id = id;
        this.anchor = anchor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public void setAnchor(final Anchor anchor) {
        this.anchor = anchor;
    }
    
    public void setOffsetX(final double offsetX) {
        this.offsetX = offsetX;
    }
    
    public void setOffsetY(final double offsetY) {
        this.offsetY = offsetY;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Anchor getAnchor() {
        return this.anchor;
    }
    
    public double getOffsetX() {
        return this.offsetX;
    }
    
    public double getOffsetY() {
        return this.offsetY;
    }
}
