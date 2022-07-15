//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.cinematic;

import java.beans.*;

public class Position
{
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
    private double tilt;
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public double getYaw() {
        return this.yaw;
    }
    
    public double getPitch() {
        return this.pitch;
    }
    
    public double getTilt() {
        return this.tilt;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public void setYaw(final double yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final double pitch) {
        this.pitch = pitch;
    }
    
    public void setTilt(final double tilt) {
        this.tilt = tilt;
    }
    
    public Position() {
    }
    
    @ConstructorProperties({ "x", "y", "z", "yaw", "pitch", "tilt" })
    public Position(final double x, final double y, final double z, final double yaw, final double pitch, final double tilt) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.tilt = tilt;
    }
}
