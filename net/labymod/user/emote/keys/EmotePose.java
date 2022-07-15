//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote.keys;

import java.beans.*;

public class EmotePose
{
    public static final int POSE_COUNT = 7;
    public static final int[] BLOCKING_MOVEMENT_IDS;
    private int bodyPart;
    private float x;
    private float y;
    private float z;
    private boolean interpolate;
    
    public String getName() {
        switch (this.bodyPart) {
            case 0: {
                return "Head";
            }
            case 1: {
                return "Right arm";
            }
            case 2: {
                return "Left arm";
            }
            case 3: {
                return "Left leg";
            }
            case 4: {
                return "Right leg";
            }
            case 5: {
                return "Chest";
            }
            case 6: {
                return "Position";
            }
            default: {
                return "Unknown";
            }
        }
    }
    
    public boolean isBlockMovement() {
        for (final int id : EmotePose.BLOCKING_MOVEMENT_IDS) {
            if (id == this.bodyPart) {
                return true;
            }
        }
        return false;
    }
    
    public int getBodyPart() {
        return this.bodyPart;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getZ() {
        return this.z;
    }
    
    public boolean isInterpolate() {
        return this.interpolate;
    }
    
    public void setBodyPart(final int bodyPart) {
        this.bodyPart = bodyPart;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public void setZ(final float z) {
        this.z = z;
    }
    
    public void setInterpolate(final boolean interpolate) {
        this.interpolate = interpolate;
    }
    
    @ConstructorProperties({ "bodyPart", "x", "y", "z", "interpolate" })
    public EmotePose(final int bodyPart, final float x, final float y, final float z, final boolean interpolate) {
        this.bodyPart = bodyPart;
        this.x = x;
        this.y = y;
        this.z = z;
        this.interpolate = interpolate;
    }
    
    static {
        BLOCKING_MOVEMENT_IDS = new int[] { 3, 4, 5, 6 };
    }
}
