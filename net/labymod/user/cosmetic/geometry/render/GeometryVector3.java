//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.render;

public class GeometryVector3
{
    public final double xCoord;
    public final double yCoord;
    public final double zCoord;
    
    public GeometryVector3(double x, double y, double z) {
        if (x == -0.0) {
            x = 0.0;
        }
        if (y == -0.0) {
            y = 0.0;
        }
        if (z == -0.0) {
            z = 0.0;
        }
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }
    
    public GeometryVector3 subtractReverse(final GeometryVector3 vec) {
        return new GeometryVector3(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
    }
    
    public GeometryVector3 normalize() {
        final double d0 = Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return (d0 < 1.0E-4) ? new GeometryVector3(0.0, 0.0, 0.0) : new GeometryVector3(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
    }
    
    public double dotProduct(final GeometryVector3 vec) {
        return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
    }
    
    public GeometryVector3 crossProduct(final GeometryVector3 vec) {
        return new GeometryVector3(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
    }
    
    public GeometryVector3 subtract(final GeometryVector3 vec) {
        return this.subtract(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public GeometryVector3 subtract(final double x, final double y, final double z) {
        return this.addVector(-x, -y, -z);
    }
    
    public GeometryVector3 add(final GeometryVector3 vec) {
        return this.addVector(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public GeometryVector3 addVector(final double x, final double y, final double z) {
        return new GeometryVector3(this.xCoord + x, this.yCoord + y, this.zCoord + z);
    }
    
    public double distanceTo(final GeometryVector3 vec) {
        final double d0 = vec.xCoord - this.xCoord;
        final double d2 = vec.yCoord - this.yCoord;
        final double d3 = vec.zCoord - this.zCoord;
        return Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    public double squareDistanceTo(final GeometryVector3 vec) {
        final double d0 = vec.xCoord - this.xCoord;
        final double d2 = vec.yCoord - this.yCoord;
        final double d3 = vec.zCoord - this.zCoord;
        return d0 * d0 + d2 * d2 + d3 * d3;
    }
    
    public double lengthVector() {
        return Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }
    
    public GeometryVector3 getIntermediateWithXValue(final GeometryVector3 vec, final double x) {
        final double d0 = vec.xCoord - this.xCoord;
        final double d2 = vec.yCoord - this.yCoord;
        final double d3 = vec.zCoord - this.zCoord;
        if (d0 * d0 < 1.0000000116860974E-7) {
            return null;
        }
        final double d4 = (x - this.xCoord) / d0;
        return (d4 >= 0.0 && d4 <= 1.0) ? new GeometryVector3(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
    }
    
    public GeometryVector3 getIntermediateWithYValue(final GeometryVector3 vec, final double y) {
        final double d0 = vec.xCoord - this.xCoord;
        final double d2 = vec.yCoord - this.yCoord;
        final double d3 = vec.zCoord - this.zCoord;
        if (d2 * d2 < 1.0000000116860974E-7) {
            return null;
        }
        final double d4 = (y - this.yCoord) / d2;
        return (d4 >= 0.0 && d4 <= 1.0) ? new GeometryVector3(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
    }
    
    public GeometryVector3 getIntermediateWithZValue(final GeometryVector3 vec, final double z) {
        final double d0 = vec.xCoord - this.xCoord;
        final double d2 = vec.yCoord - this.yCoord;
        final double d3 = vec.zCoord - this.zCoord;
        if (d3 * d3 < 1.0000000116860974E-7) {
            return null;
        }
        final double d4 = (z - this.zCoord) / d3;
        return (d4 >= 0.0 && d4 <= 1.0) ? new GeometryVector3(this.xCoord + d0 * d4, this.yCoord + d2 * d4, this.zCoord + d3 * d4) : null;
    }
    
    @Override
    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }
    
    public GeometryVector3 rotatePitch(final float pitch) {
        final float f = (float)Math.cos(pitch);
        final float f2 = (float)Math.sin(pitch);
        final double d0 = this.xCoord;
        final double d2 = this.yCoord * f + this.zCoord * f2;
        final double d3 = this.zCoord * f - this.yCoord * f2;
        return new GeometryVector3(d0, d2, d3);
    }
    
    public GeometryVector3 rotateYaw(final float yaw) {
        final float f = (float)Math.cos(yaw);
        final float f2 = (float)Math.sin(yaw);
        final double d0 = this.xCoord * f + this.zCoord * f2;
        final double d2 = this.yCoord;
        final double d3 = this.zCoord * f - this.xCoord * f2;
        return new GeometryVector3(d0, d2, d3);
    }
}
