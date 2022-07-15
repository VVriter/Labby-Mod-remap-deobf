//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet;

import net.labymod.user.cosmetic.pet.util.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.core.*;
import java.util.*;

public class PetStorage<T>
{
    public T cosmetic;
    private boolean moving;
    public boolean attached;
    public boolean standingUp;
    public boolean onGround;
    public boolean collision;
    public Vector position;
    public Vector offset;
    public Vector rotation;
    public Vector motion;
    public Vector prevPosition;
    public Vector prevRotation;
    public Vector ownerPosition;
    public BoundingBox boundingBox;
    public AnimationController animationController;
    public long lastTimeMoved;
    public long lastTimeFlyingChanged;
    public long lastTimeAboveShoulderChanged;
    public boolean isAboveShoulder;
    public float lastTicks;
    public boolean lastSneaking;
    public boolean rightShoulder;
    
    public PetStorage() {
        this.moving = false;
        this.attached = true;
        this.standingUp = false;
        this.onGround = false;
        this.collision = false;
        this.position = new Vector();
        this.offset = new Vector();
        this.rotation = new Vector();
        this.motion = new Vector();
        this.prevPosition = new Vector();
        this.prevRotation = new Vector();
        this.ownerPosition = new Vector();
        this.boundingBox = new BoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.animationController = new AnimationController();
    }
    
    public void teleport(final double x, final double y, final double z, final double rotationX, final double rotationY, final double rotationZ) {
        this.prevPosition.setX(this.position.x).setY(this.position.y).setZ(this.position.z);
        this.prevRotation.setX(this.rotation.x).setY(this.rotation.y).setZ(this.rotation.z);
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
        this.rotation.x = rotationX;
        this.rotation.y = rotationY;
        this.rotation.z = rotationZ;
        this.motion.x = 0.0;
        this.motion.y = 0.0;
        this.motion.z = 0.0;
        final float width = 0.1f;
        final float height = 0.25f;
        this.boundingBox = new BoundingBox(x - width, y - height, z - width, x + width, y + height, z + width);
    }
    
    public boolean moveCollide(final amu world, double x, double y, double z) {
        final double prevX = x;
        final double prevY = y;
        final double prevZ = z;
        final List<BoundingBox> aABBs = (List<BoundingBox>)LabyModCore.getMinecraft().getCollisionBoxes(world, this.boundingBox.expand(x, y, z));
        for (final BoundingBox abb : aABBs) {
            y = abb.clipYCollide(this.boundingBox, y);
        }
        this.boundingBox.move(0.0, y, 0.0);
        for (final BoundingBox aABB : aABBs) {
            x = aABB.clipXCollide(this.boundingBox, x);
        }
        this.boundingBox.move(x, 0.0, 0.0);
        for (final BoundingBox aABB : aABBs) {
            z = aABB.clipZCollide(this.boundingBox, z);
        }
        this.boundingBox.move(0.0, 0.0, z);
        this.onGround = (prevY != y && prevY < 0.0);
        if (prevX != x) {
            this.motion.x = 0.0;
        }
        if (prevY != y) {
            this.motion.y = 0.0;
        }
        if (prevZ != z) {
            this.motion.z = 0.0;
        }
        this.position.x = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
        this.position.y = this.boundingBox.minY;
        this.position.z = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        return prevX != x || prevZ != z;
    }
    
    public void setRotation(final double x, final double y, final double z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public void moveRelative(double forward, double up, double strafe, final double friction) {
        double distance = strafe * strafe + up * up + forward * forward;
        if (distance >= 9.999999747378752E-5) {
            distance = Math.sqrt(distance);
            if (distance < 1.0) {
                distance = 1.0;
            }
            distance = friction / distance;
            strafe *= distance;
            up *= distance;
            forward *= distance;
            final double yawRadians = Math.toRadians(this.rotation.y);
            final double sin = Math.sin(yawRadians);
            final double cos = Math.cos(yawRadians);
            final Vector motion = this.motion;
            motion.x += strafe * cos - forward * sin;
            final Vector motion2 = this.motion;
            motion2.y += up;
            final Vector motion3 = this.motion;
            motion3.z += forward * cos + strafe * sin;
        }
    }
    
    public void travel(final amu world, final float forward, final float vertical, final float strafe) {
        final float prevSlipperiness = (this.onGround ? 0.6f : 1.0f) * 0.91f;
        final float value = 0.16277136f / (prevSlipperiness * prevSlipperiness * prevSlipperiness);
        float friction;
        if (this.onGround) {
            friction = 0.1f * value;
        }
        else {
            friction = 0.02f;
        }
        this.moveRelative(forward, vertical, strafe, friction);
        final float slipperiness = (this.onGround ? 0.6f : 1.0f) * 0.91f;
        this.collision = this.moveCollide(world, -this.motion.x, this.motion.y, -this.motion.z);
        final Vector motion = this.motion;
        motion.y -= 0.07999999821186066;
        final Vector motion2 = this.motion;
        motion2.x *= slipperiness;
        final Vector motion3 = this.motion;
        motion3.y *= 0.9800000190734863;
        final Vector motion4 = this.motion;
        motion4.z *= slipperiness;
    }
    
    public boolean isAttachedToOwner(final float currentTick) {
        return (!this.moving && !this.animationController.isPlaying(currentTick)) || (this.attached && this.animationController.isPlaying(currentTick));
    }
    
    public boolean isPlayingAnimation(final float currentTick) {
        return this.animationController.isPlaying(currentTick);
    }
    
    public void setMovingState(final boolean moving) {
        this.moving = moving;
        this.lastTimeFlyingChanged = System.currentTimeMillis();
    }
    
    public void stopAnimation() {
        this.animationController.stop();
    }
    
    public boolean isMoving() {
        return this.moving;
    }
}
