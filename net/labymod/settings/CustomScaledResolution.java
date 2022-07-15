//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.core.*;

public class CustomScaledResolution
{
    private final double scaledWidthD;
    private final double scaledHeightD;
    private double scaledWidth;
    private double scaledHeight;
    private double scaleFactor;
    
    public CustomScaledResolution(final bib mc, final double scale) {
        this.scaledWidth = mc.d;
        this.scaledHeight = mc.e;
        this.scaleFactor = 1.0;
        final boolean flag = mc.e();
        double i = scale;
        if (i == 0.0) {
            i = 1000.0;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1.0) >= 320.0 && this.scaledHeight / (this.scaleFactor + 1.0) >= 240.0) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2.0 != 0.0 && this.scaleFactor != 1.0) {
            --this.scaleFactor;
        }
        this.scaledWidthD = this.scaledWidth / this.scaleFactor;
        this.scaledHeightD = this.scaledHeight / this.scaleFactor;
        this.scaledWidth = LabyModCore.getMath().ceiling_double_int(this.scaledWidthD);
        this.scaledHeight = LabyModCore.getMath().ceiling_double_int(this.scaledHeightD);
    }
    
    public int getScaledWidth() {
        return (int)this.scaledWidth;
    }
    
    public int getScaledHeight() {
        return (int)this.scaledHeight;
    }
    
    public double getScaledWidth_double() {
        return this.scaledWidthD;
    }
    
    public double getScaledHeight_double() {
        return this.scaledHeightD;
    }
    
    public double getScaleFactor() {
        return this.scaleFactor;
    }
}
