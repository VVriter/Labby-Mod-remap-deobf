//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets;

import com.google.gson.annotations.*;

public class WidgetLayout
{
    @SerializedName("slot_width")
    private int slotWidth;
    @SerializedName("slot_height")
    private int slotHeight;
    @SerializedName("slot_margin_x")
    private int slotMarginX;
    @SerializedName("slot_margin_y")
    private int slotMarginY;
    @SerializedName("border_padding_x")
    private int borderPaddingX;
    @SerializedName("border_padding_y")
    private int borderPaddingY;
    @SerializedName("font_size")
    private double fontSize;
    
    public WidgetLayout() {
        this.slotWidth = 10;
        this.slotHeight = 10;
        this.slotMarginX = 5;
        this.slotMarginY = 5;
        this.borderPaddingX = 10;
        this.borderPaddingY = 10;
        this.fontSize = 1.0;
    }
    
    public WidgetLayout(final int slotWidth, final int slotHeight, final int slotMarginX, final int slotMarginY, final int borderPaddingX, final int borderPaddingY) {
        this.slotWidth = 10;
        this.slotHeight = 10;
        this.slotMarginX = 5;
        this.slotMarginY = 5;
        this.borderPaddingX = 10;
        this.borderPaddingY = 10;
        this.fontSize = 1.0;
        this.slotWidth = slotWidth;
        this.slotHeight = slotHeight;
        this.slotMarginX = slotMarginX;
        this.slotMarginY = slotMarginY;
        this.borderPaddingX = borderPaddingX;
        this.borderPaddingY = borderPaddingY;
    }
    
    public int getSlotWidth() {
        return this.slotWidth;
    }
    
    public void setSlotWidth(final int slotWidth) {
        this.slotWidth = slotWidth;
    }
    
    public int getSlotHeight() {
        return this.slotHeight;
    }
    
    public void setSlotHeight(final int slotHeight) {
        this.slotHeight = slotHeight;
    }
    
    public int getSlotMarginX() {
        return this.slotMarginX;
    }
    
    public void setSlotMarginX(final int slotMarginX) {
        this.slotMarginX = slotMarginX;
    }
    
    public int getSlotMarginY() {
        return this.slotMarginY;
    }
    
    public void setSlotMarginY(final int slotMarginY) {
        this.slotMarginY = slotMarginY;
    }
    
    public int getBorderPaddingX() {
        return this.borderPaddingX;
    }
    
    public void setBorderPaddingX(final int borderPaddingX) {
        this.borderPaddingX = borderPaddingX;
    }
    
    public int getBorderPaddingY() {
        return this.borderPaddingY;
    }
    
    public void setBorderPaddingY(final int borderPaddingY) {
        this.borderPaddingY = borderPaddingY;
    }
    
    public double getFontSize() {
        return this.fontSize;
    }
    
    public void setFontSize(final double fontSize) {
        this.fontSize = fontSize;
    }
}
