//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

import java.beans.*;

public class BoundingBox
{
    private final double epsilon = 0.0;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    
    public BoundingBox expand(final double x, final double y, final double z) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;
        if (x < 0.0) {
            minX += x;
        }
        else {
            maxX += x;
        }
        if (y < 0.0) {
            minY += y;
        }
        else {
            maxY += y;
        }
        if (z < 0.0) {
            minZ += z;
        }
        else {
            maxZ += z;
        }
        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public void move(final double x, final double y, final double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }
    
    public double clipXCollide(final BoundingBox otherBoundingBox, double x) {
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return x;
        }
        if (otherBoundingBox.maxZ <= this.minZ || otherBoundingBox.minZ >= this.maxZ) {
            return x;
        }
        if (x > 0.0 && otherBoundingBox.maxX <= this.minX) {
            final double n = this.minX - otherBoundingBox.maxX;
            this.getClass();
            final double max = n - 0.0;
            if (max < x) {
                x = max;
            }
        }
        if (x < 0.0 && otherBoundingBox.minX >= this.maxX) {
            final double n2 = this.maxX - otherBoundingBox.minX;
            this.getClass();
            final double max = n2 + 0.0;
            if (max > x) {
                x = max;
            }
        }
        return x;
    }
    
    public double clipYCollide(final BoundingBox otherBoundingBox, double y) {
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return y;
        }
        if (otherBoundingBox.maxZ <= this.minZ || otherBoundingBox.minZ >= this.maxZ) {
            return y;
        }
        if (y > 0.0 && otherBoundingBox.maxY <= this.minY) {
            final double n = this.minY - otherBoundingBox.maxY;
            this.getClass();
            final double max = n - 0.0;
            if (max < y) {
                y = max;
            }
        }
        if (y < 0.0 && otherBoundingBox.minY >= this.maxY) {
            final double n2 = this.maxY - otherBoundingBox.minY;
            this.getClass();
            final double max = n2 + 0.0;
            if (max > y) {
                y = max;
            }
        }
        return y;
    }
    
    public double clipZCollide(final BoundingBox otherBoundingBox, double z) {
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return z;
        }
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return z;
        }
        if (z > 0.0 && otherBoundingBox.maxZ <= this.minZ) {
            final double n = this.minZ - otherBoundingBox.maxZ;
            this.getClass();
            final double max = n - 0.0;
            if (max < z) {
                z = max;
            }
        }
        if (z < 0.0 && otherBoundingBox.minZ >= this.maxZ) {
            final double n2 = this.maxZ - otherBoundingBox.minZ;
            this.getClass();
            final double max = n2 + 0.0;
            if (max > z) {
                z = max;
            }
        }
        return z;
    }
    
    @ConstructorProperties({ "minX", "minY", "minZ", "maxX", "maxY", "maxZ" })
    public BoundingBox(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
}
