//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.enums;

import net.labymod.ingamegui.*;

public enum EnumModuleRegion
{
    BOTTOM_LEFT(3, 1), 
    BOTTOM_CENTER(3, 2), 
    BOTTOM_RIGHT(3, 3), 
    TOP_LEFT(1, 1), 
    TOP_CENTER(1, 2), 
    TOP_RIGHT(1, 3), 
    CENTER_LEFT(2, 1), 
    CENTER(2, 2), 
    CENTER_RIGHT(2, 3);
    
    public static final double DISPLAY_PART = 100.0;
    private byte horizontalPosition;
    private byte verticalPosition;
    
    private EnumModuleRegion(final int verticalPosition, final int horizontalPosition) {
        this.horizontalPosition = (byte)horizontalPosition;
        this.verticalPosition = (byte)verticalPosition;
    }
    
    public byte getHorizontalPosition() {
        return this.horizontalPosition;
    }
    
    public byte getVerticalPosition() {
        return this.verticalPosition;
    }
    
    public boolean isInArea(final double x, final double y, final double left, final double right, final double top, final double bottom) {
        final double minX = this.getMinX(left, right);
        final double maxX = this.getMaxX(left, right);
        final double minY = this.getMinY(top, bottom);
        final double maxY = this.getMaxY(top, bottom);
        final boolean returned = x >= minX && x <= maxX && y >= minY && y <= maxY;
        return returned;
    }
    
    public double getPointX(final double left, final double right) {
        if (Module.getLastDrawnDisplayType().isEscapeRegions()) {
            double pos = -1.0;
            if (this.horizontalPosition == 1) {
                pos = (right - left) / 2.0 - 150.0 + left;
            }
            if (this.horizontalPosition == 3) {
                pos = (right - left) / 2.0 + 150.0 + left;
            }
            if (pos != -1.0) {
                return pos;
            }
        }
        final double width = right - left;
        final int pos2 = (this.horizontalPosition == 1) ? 1 : ((this.horizontalPosition == 2) ? 3 : 5);
        return (int)(left + width / 6.0 * pos2);
    }
    
    public double getPointY(final double top, final double bottom) {
        if (Module.getLastDrawnDisplayType().isEscapeRegions()) {
            return (bottom - top) / 4.0 + 48.0 + top;
        }
        final double height = bottom - top;
        final double pos = (this.verticalPosition == 1) ? 1.0 : ((this.verticalPosition == 2) ? 3.0 : 5.0);
        return top + height / 6.0 * pos;
    }
    
    public double getMinecraftX(final double offsetX, final double left, final double right) {
        return this.getBoundingPoint(true, left, right) + offsetX;
    }
    
    public double getMinecraftY(final double offsetY, final double top, final double bottom) {
        return this.getBoundingPoint(false, top, bottom) + offsetY;
    }
    
    public double getOffsetX(final double x, final double left, final double right) {
        return x - this.getBoundingPoint(true, left, right);
    }
    
    public double getOffsetY(final double y, final double top, final double bottom) {
        return y - this.getBoundingPoint(false, top, bottom);
    }
    
    private double getBoundingPoint(final boolean horizontal, final double first, final double second) {
        double point = 0.0;
        final int pos = horizontal ? this.horizontalPosition : this.verticalPosition;
        if (pos == 1) {
            point = first;
        }
        if (pos == 2) {
            point = (horizontal ? this.getPointX(first, second) : this.getPointY(first, second));
        }
        if (pos == 3) {
            point = second;
        }
        if (!horizontal && Module.getLastDrawnDisplayType().isEscapeRegions()) {
            point = EnumModuleRegion.CENTER.getPointY(first, second);
        }
        return point;
    }
    
    public double getMinX(final double left, final double right) {
        if (Module.getLastDrawnDisplayType().isEscapeRegions()) {
            if (this.horizontalPosition == 2) {
                return (right - left) / 2.0 - 150.0 + left;
            }
            if (this.horizontalPosition == 3) {
                return (right - left) / 2.0 + 150.0 + left;
            }
            return left;
        }
        else {
            final double protection = 10.0;
            final double middleSize = 150.0;
            final double middle = left + (right - left) / 2.0;
            final boolean flag = middle - middleSize < protection + Module.getLastLeft();
            if (this.horizontalPosition == 2) {
                return flag ? (left + protection) : (middle - middleSize);
            }
            if (this.horizontalPosition == 3) {
                return flag ? (right - protection) : (middle + middleSize);
            }
            return left;
        }
    }
    
    public double getMinY(final double top, final double bottom) {
        if (Module.getLastDrawnDisplayType().isEscapeRegions()) {
            return 0.0;
        }
        return top + (bottom - top) / 3.0 * (this.verticalPosition - 1.0);
    }
    
    public double getMaxX(final double left, final double right) {
        if (Module.getLastDrawnDisplayType().isEscapeRegions()) {
            if (this.horizontalPosition == 1) {
                return (right - left) / 2.0 - 150.0 + left;
            }
            if (this.horizontalPosition == 2) {
                return (right - left) / 2.0 + 150.0 + left;
            }
            return right;
        }
        else {
            final double protection = 10.0;
            final double middleSize = 150.0;
            final double middle = left + (right - left) / 2.0;
            final boolean flag = middle - middleSize < protection + Module.getLastLeft();
            if (this.horizontalPosition == 2) {
                return flag ? (right - protection) : (middle + middleSize);
            }
            if (this.horizontalPosition == 3) {
                return right;
            }
            return flag ? (left + protection) : (middle - middleSize);
        }
    }
    
    public double getMaxY(final double top, final double bottom) {
        if (Module.getLastDrawnDisplayType().isEscapeRegions()) {
            return bottom;
        }
        final double height = (bottom - top) / 3.0;
        return top + height * this.verticalPosition;
    }
}
