//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet.ai;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.pet.object.*;
import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.pet.*;
import net.labymod.support.gui.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.pet.util.*;
import net.labymod.user.cosmetic.animation.*;

public class FlyingAI<T extends CosmeticData> implements PetAI<T>
{
    private final CosmeticPet<T> pet;
    
    public FlyingAI(final CosmeticPet<T> pet) {
        this.pet = pet;
    }
    
    @Override
    public void preRender(final ModelCosmetics model, final vg entityIn, final PetStorage<T> data, final float currentTick, final float walkingSpeed, final float partialTicks) {
        final Vector offset = data.offset;
        if (data.prevPosition.distanceSquared(data.position) > 10.0) {
            this.onTick(model, (aed)entityIn, data, 0.0f, 0.0f, 0.0f, partialTicks);
        }
        final bua player = (bua)entityIn;
        final float renderYaw = player.aO + (player.aN - player.aO) * partialTicks;
        final double playerX = entityIn.m + (entityIn.p - entityIn.m) * partialTicks;
        final double playerY = entityIn.n + (entityIn.q - entityIn.n) * partialTicks;
        final double playerZ = entityIn.o + (entityIn.r - entityIn.o) * partialTicks;
        final double x = data.prevPosition.x + (data.position.x - data.prevPosition.x) * partialTicks;
        final double y = data.prevPosition.y + (data.position.y - data.prevPosition.y) * partialTicks;
        final double z = data.prevPosition.z + (data.position.z - data.prevPosition.z) * partialTicks;
        bus.b(offset.x / 16.0, offset.y / 16.0, offset.z / 16.0);
        final brs armSource = data.rightShoulder ? model.h : model.i;
        final float shoulderMirror = data.rightShoulder ? -1.0f : 1.0f;
        if (!(bib.z().m instanceof GuiCosmeticPreview)) {
            bus.c(0.3125f * shoulderMirror, 0.15625f, 0.0f);
            if (data.isAttachedToOwner(currentTick)) {
                final float transition = Math.min(1.0f, 1.0f - walkingSpeed);
                bus.b((float)Math.toDegrees(armSource.f) * transition, 1.0f, 0.0f, 0.0f);
                bus.b((float)Math.toDegrees(armSource.g) * transition, 0.0f, 1.0f, 0.0f);
                bus.b((float)Math.toDegrees(armSource.h) * transition, 0.0f, 0.0f, 1.0f);
            }
            bus.c(0.0625f * shoulderMirror, -0.14f, -0.0625f);
        }
        bus.c(0.0f, 0.015f, 0.0f);
        if (LabyModCore.getMinecraft().hasChestplate((aed)entityIn)) {
            bus.c(0.0f, -0.08f, 0.0f);
        }
        if (!data.isAttachedToOwner(currentTick) && this.pet.moveType.canMove()) {
            bus.b(renderYaw, 0.0f, -1.0f, 0.0f);
            final double f = 1.0658999681472778;
            bus.a(f, f, f);
            bus.b(x - playerX, -y + playerY, -z + playerZ);
        }
        if (!entityIn.aU() && data.isAttachedToOwner(currentTick)) {
            final double uniqueTick = currentTick + this.pet.getCosmeticId() * 3.141592653589793;
            bus.b(0.0, -Math.abs(Math.cos(uniqueTick / 2.0) / 3.0 * walkingSpeed / 2.0), 0.0);
            bus.b((float)Math.cos(uniqueTick / 2.0) * 10.0f * walkingSpeed, 0.0f, 0.0f, 1.0f);
            final double chasingY = player.bF + (player.bI - player.bF) * partialTicks;
            final double velocityY = (playerY > chasingY) ? 0.0 : (playerY - chasingY);
            final double bounce = Math.abs(Math.cos(uniqueTick / 2.0 + velocityY) / 20.0);
            bus.b(0.0, bounce * velocityY, 0.0);
        }
    }
    
    @Override
    public void firstPersonTransform(final vg entityIn, final PetStorage<T> data, final boolean firstPersonContext, final float partialTicks) {
    }
    
    @Override
    public void onTick(final ModelCosmetics model, final aed player, final PetStorage<T> data, final float movementFactor, final float walkingSpeed, final float tickValue, final float partialTicks) {
        final boolean isMoving = walkingSpeed > 0.3f || player.au;
        if (!data.isMoving() && this.pet.moveType == EnumMoveType.MOVE_ONLY) {
            data.setMovingState(true);
        }
        if (isMoving && data.isAttachedToOwner(tickValue) && this.pet.moveType.canMove()) {
            data.setMovingState(true);
            this.pet.handleEvent(EnumTrigger.START_MOVING, data, tickValue, (vg)player);
        }
        if (data.isAttachedToOwner(tickValue) || isMoving) {
            data.lastTimeMoved = System.currentTimeMillis();
        }
        final long timePassedSinceChange = System.currentTimeMillis() - data.lastTimeFlyingChanged;
        final float sigmoidInput = 0.006f * Math.min(1000L, timePassedSinceChange);
        final float increasing = (float)((1.0 - Math.exp(-sigmoidInput)) / (1.0 + Math.exp(-sigmoidInput)) * 1.0);
        final float decreasing = 1.0f - increasing;
        final double chasingX = player.bH;
        final double chasingY = player.bI + this.getFlightAltitude();
        final double chasingZ = player.bJ;
        final double posX = player.p;
        final double posY = player.q;
        final double posZ = player.r;
        final double positionFactor = data.isMoving() ? decreasing : ((double)increasing);
        final double chasingFactor = data.isMoving() ? increasing : ((double)decreasing);
        final double x = posX * positionFactor + chasingX * chasingFactor;
        final double y = posY * positionFactor + chasingY * chasingFactor;
        final double z = posZ * positionFactor + chasingZ * chasingFactor;
        final float renderYaw = player.aN;
        final float lookYaw = (float)(-Math.toDegrees(Math.atan2(-x + posX, -z + posZ)));
        final float sigmoidInputTransition = Math.min(2000L, System.currentTimeMillis() - data.lastTimeAboveShoulderChanged) / 2000.0f * 6.0f;
        final float transition = (float)((1.0 - Math.exp(-sigmoidInputTransition)) / (1.0 + Math.exp(-sigmoidInputTransition)) * 1.0);
        final float factor = data.isAboveShoulder ? (1.0f - transition) : transition;
        final float modDiff = (renderYaw - lookYaw) % 360.0f;
        final float shortestDistance = 180.0f - Math.abs(Math.abs(modDiff) - 180.0f);
        final float distance = ((modDiff + 360.0f) % 360.0f < 180.0f) ? shortestDistance : (shortestDistance * -1.0f);
        final float rotationY = renderYaw - distance * factor;
        data.teleport(x, y, z, 0.0, rotationY, 0.0);
        if (data.isMoving()) {
            final boolean aboveShoulder = walkingSpeed < 0.1f || chasingFactor < 0.8999999761581421;
            if (aboveShoulder != data.isAboveShoulder) {
                data.isAboveShoulder = aboveShoulder;
                data.lastTimeAboveShoulderChanged = System.currentTimeMillis();
            }
            if (data.isAboveShoulder && data.lastTimeMoved + 7000L < System.currentTimeMillis() && this.pet.moveType.canIdle()) {
                data.setMovingState(false);
                this.pet.handleEvent(EnumTrigger.STOP_MOVING, data, tickValue, (vg)player);
            }
        }
        else {
            data.isAboveShoulder = true;
        }
    }
    
    @Override
    public void renderFirstPerson(final aed player, final float partialTicks) {
    }
    
    public float getFlightAltitude() {
        return 0.5f;
    }
    
    @Override
    public boolean canAttach() {
        return true;
    }
}
