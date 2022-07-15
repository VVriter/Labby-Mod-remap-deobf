//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.inventory.scale;

import net.labymod.main.*;
import net.labymod.core.*;
import org.lwjgl.input.*;

public class InventoryScaleChanger
{
    private boolean initialized;
    private double scaledWidthD;
    private double scaledHeightD;
    private int scaledWidth;
    private int scaledHeight;
    private int scaleFactor;
    private int mouseX;
    private int mouseY;
    
    public InventoryScaleChanger() {
        this.initialized = false;
    }
    
    public boolean initGui() {
        final EnumGuiScale enumScale = LabyMod.getSettings().customInventoryScale;
        if (enumScale != EnumGuiScale.DEFAULT) {
            final bib minecraft = bib.z();
            this.scaledWidth = minecraft.d;
            this.scaledHeight = minecraft.e;
            this.scaleFactor = 1;
            final boolean flag = minecraft.e();
            int i = enumScale.ordinal();
            if (i == 0) {
                i = 1000;
            }
            while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
                ++this.scaleFactor;
            }
            if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
                --this.scaleFactor;
            }
            this.scaledWidthD = this.scaledWidth / (double)this.scaleFactor;
            this.scaledHeightD = this.scaledHeight / (double)this.scaleFactor;
            this.scaledWidth = LabyModCore.getMath().ceiling_double_int(this.scaledWidthD);
            this.scaledHeight = LabyModCore.getMath().ceiling_double_int(this.scaledHeightD);
            return this.initialized = true;
        }
        return false;
    }
    
    public boolean drawScreen(final int mouseX, final int mouseY) {
        if (this.initialized) {
            bus.m(256);
            bus.n(5889);
            bus.F();
            bus.a(0.0, this.scaledWidthD, this.scaledHeightD, 0.0, 1000.0, 3000.0);
            bus.n(5888);
            bus.F();
            bus.c(0.0f, 0.0f, -2000.0f);
            this.mouseX = Mouse.getX() * this.scaledWidth / bib.z().d;
            this.mouseY = this.scaledHeight - Mouse.getY() * this.scaledHeight / bib.z().e - 1;
            return true;
        }
        return false;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
    
    public double getScaledWidthD() {
        return this.scaledWidthD;
    }
    
    public double getScaledHeightD() {
        return this.scaledHeightD;
    }
    
    public int getScaledWidth() {
        return this.scaledWidth;
    }
    
    public int getScaledHeight() {
        return this.scaledHeight;
    }
    
    public int getScaleFactor() {
        return this.scaleFactor;
    }
    
    public int getMouseX() {
        return this.mouseX;
    }
    
    public int getMouseY() {
        return this.mouseY;
    }
}
