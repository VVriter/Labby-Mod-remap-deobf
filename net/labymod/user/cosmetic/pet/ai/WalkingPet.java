//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet.ai;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.pet.object.*;
import net.labymod.user.cosmetic.*;
import net.labymod.user.cosmetic.pet.*;
import net.labymod.user.cosmetic.pet.util.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.core.*;

public class WalkingPet<T extends CosmeticData> implements PetAI<T>
{
    private final CosmeticPet<T> pet;
    
    public WalkingPet(final CosmeticPet<T> pet) {
        this.pet = pet;
    }
    
    public void preRender(final ModelCosmetics model, final vg entityIn, final PetStorage<T> data, final float currentTick, final float walkingSpeed, final float partialTicks) {
        final bua player = (bua)entityIn;
        final double playerX = entityIn.m + (entityIn.p - entityIn.m) * partialTicks;
        final double playerY = entityIn.n + (entityIn.q - entityIn.n) * partialTicks;
        final double playerZ = entityIn.o + (entityIn.r - entityIn.o) * partialTicks;
        final double x = data.prevPosition.x + (data.position.x - data.prevPosition.x) * partialTicks;
        final double y = data.prevPosition.y + (data.position.y - data.prevPosition.y) * partialTicks;
        final double z = data.prevPosition.z + (data.position.z - data.prevPosition.z) * partialTicks;
        final float renderYaw = player.aO + (player.aN - player.aO) * partialTicks;
        bus.b(renderYaw, 0.0f, -1.0f, 0.0f);
        bus.b(-playerX, playerY, playerZ);
        bus.b(x, -y, -z);
        bus.b(0.0, 1.5, 0.0);
    }
    
    public void firstPersonTransform(final vg entityIn, final PetStorage<T> data, final boolean firstPersonContext, final float partialTicks) {
        final double playerX = entityIn.m + (entityIn.p - entityIn.m) * partialTicks;
        final double playerY = entityIn.n + (entityIn.q - entityIn.n) * partialTicks;
        final double playerZ = entityIn.o + (entityIn.r - entityIn.o) * partialTicks;
        final double x = data.prevPosition.x + (data.position.x - data.prevPosition.x) * partialTicks;
        final double y = data.prevPosition.y + (data.position.y - data.prevPosition.y) * partialTicks;
        final double z = data.prevPosition.z + (data.position.z - data.prevPosition.z) * partialTicks;
        if (!firstPersonContext) {
            final double diffX = playerX - x;
            final double diffY = playerY - y;
            final double diffZ = playerZ - z;
            bus.b(-diffX / 15.0, diffY / 15.0, diffZ / 15.0);
            if (entityIn.aU()) {
                bus.c(0.0f, -0.33f, 0.0f);
            }
        }
    }
    
    public void onTick(final ModelCosmetics model, final aed player, final PetStorage<T> data, final float movementFactor, final float walkingSpeed, final float tickValue, final float partialTicks) {
        data.prevPosition.setX(data.position.x).setY(data.position.y).setZ(data.position.z);
        data.prevRotation.setX(data.rotation.x).setY(data.rotation.y).setZ(data.rotation.z);
        if (Math.abs(data.motion.x) < 0.003) {
            data.motion.x = 0.0;
        }
        if (Math.abs(data.motion.y) < 0.003) {
            data.motion.y = 0.0;
        }
        if (Math.abs(data.motion.z) < 0.003) {
            data.motion.z = 0.0;
        }
        final double distance3D = data.ownerPosition.distanceSquared(data.position);
        final double distance2D = Vector.square(data.ownerPosition.x - data.position.x) + Vector.square(data.ownerPosition.z - data.position.z);
        boolean moving = false;
        final float rotation = (float)Math.toDegrees(Math.atan2(-player.p + data.position.x, -player.r + data.position.z));
        data.setRotation(0.0, -rotation + 180.0f, 0.0);
        if (distance2D > 5.0 && this.pet.moveType.canMove()) {
            if (!data.isMoving()) {
                data.setMovingState(true);
                data.standingUp = true;
                this.pet.handleEvent(EnumTrigger.START_MOVING, data, tickValue, (vg)player);
            }
            if (!data.standingUp || !data.isPlayingAnimation(tickValue)) {
                moving = true;
                data.standingUp = false;
            }
        }
        else if (data.isMoving() && this.pet.moveType.canIdle()) {
            data.setMovingState(false);
            data.attached = false;
            this.pet.handleEvent(EnumTrigger.STOP_MOVING, data, tickValue, (vg)player);
        }
        if (this.pet.moveType.canMove()) {
            data.travel(player.e(), moving ? -1.0f : 0.0f, 0.0f, 0.0f);
        }
        if (LabyModCore.getMinecraft().isJumpPredicted(data.position.x, data.position.y, data.position.z, data.prevPosition.x, data.prevPosition.y, data.prevPosition.z, (float)data.rotation.y, (float)data.rotation.x, data.boundingBox.minY, data.boundingBox.maxY, 1.0f, 0.0f, (double)player.G, (double)player.H, player.cy(), false, data.onGround, false)) {
            data.motion.y = 0.41999998688697815;
        }
        if (distance3D > 500.0 && player.z && this.pet.moveType.canMove()) {
            data.teleport(player.m, player.n + 1.0, player.o, 0.0, 0.0, 0.0);
        }
    }
    
    public void renderFirstPerson(final aed entity, final float partialTicks) {
    }
    
    public boolean canAttach() {
        return false;
    }
}
