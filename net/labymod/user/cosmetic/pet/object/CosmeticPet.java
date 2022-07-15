//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.pet.object;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.pet.util.*;
import net.labymod.user.cosmetic.*;
import net.labymod.user.emote.*;
import net.labymod.main.*;
import net.labymod.user.cosmetic.pet.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.user.emote.keys.*;
import net.labymod.user.cosmetic.pet.ai.*;
import org.lwjgl.opengl.*;
import net.labymod.user.*;

public abstract class CosmeticPet<T extends CosmeticData> extends CosmeticRenderer<T> implements IModelTransformer
{
    private int id;
    public PetAI<T> ai;
    public EnumMoveType moveType;
    
    public CosmeticPet() {
        this.moveType = EnumMoveType.BOTH;
    }
    
    public void init(final int id, final PetAI<T> ai) {
        this.id = id;
        this.ai = ai;
    }
    
    public void render(final ModelCosmetics model, final vg entityIn, final T cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float currentTick, final float firstRotationX, final float secondRotationX, final float partialTicks, final boolean firstPersonContext) {
        final nf resourceLocation = this.getResourceLocation((bua)entityIn, cosmeticData);
        if (resourceLocation == null) {
            return;
        }
        bib.z().N().a(resourceLocation);
        final PetStorage<T> storage = this.getStorage(cosmeticData);
        if (storage == null) {
            return;
        }
        storage.ownerPosition.setX(entityIn.p).setY(entityIn.q).setZ(entityIn.r);
        final EmoteRenderer playerEmoteRenderer = LabyMod.getInstance().getEmoteRegistry().getPlayingEmotes().get(entityIn.bm());
        if (playerEmoteRenderer != null && !playerEmoteRenderer.isAborted() && playerEmoteRenderer.isVisible() && !playerEmoteRenderer.isStream() && !storage.isAttachedToOwner(currentTick)) {
            for (final EmoteBodyPart bodyPart : playerEmoteRenderer.getBodyParts()) {
                for (final PoseAtTime pose : playerEmoteRenderer.getEmotePosesAtTime()) {
                    if (pose != null) {
                        if (pose.getPose().getBodyPart() == bodyPart.getId()) {
                            switch (bodyPart.getId()) {
                                case 5: {
                                    bus.c(0.0f, -0.4f, 0.0f);
                                    final int modifier = 1;
                                    bus.b(-bodyPart.getX() * 57.295776f * modifier, 1.0f, 0.0f, 0.0f);
                                    bus.b(-bodyPart.getY() * 57.295776f, 0.0f, 1.0f, 0.0f);
                                    bus.b(-bodyPart.getZ() * 57.295776f, 0.0f, 0.0f, 1.0f);
                                    bus.c(0.0f, 0.4f, 0.0f);
                                    break;
                                }
                                case 6: {
                                    bus.b(-bodyPart.getX() / 10.0, -bodyPart.getY() / 10.0, -bodyPart.getZ() / 10.0);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (partialTicks != 1.0f || storage.isAttachedToOwner(currentTick)) {
            final float clientPartialTicks = LabyMod.getInstance().getPartialTicks();
            if (storage.lastTicks > clientPartialTicks) {
                this.ai.onTick(model, (aed)entityIn, (PetStorage)storage, movementFactor, walkingSpeed, currentTick, clientPartialTicks);
            }
            storage.lastTicks = clientPartialTicks;
            bus.G();
            this.ai.preRender(model, entityIn, (PetStorage)storage, currentTick, walkingSpeed, partialTicks);
            this.ai.firstPersonTransform(entityIn, (PetStorage)storage, firstPersonContext, partialTicks);
            if (!storage.animationController.isPlaying(currentTick)) {
                this.handleEvent(storage.isMoving() ? EnumTrigger.MOVING : EnumTrigger.IDLE, storage, currentTick, entityIn);
                if (storage.isMoving()) {
                    storage.standingUp = false;
                }
            }
            final double rotX = storage.prevRotation.x + (storage.rotation.x - storage.prevRotation.x) * partialTicks;
            final double rotY = storage.prevRotation.y + (storage.rotation.y - storage.prevRotation.y) * partialTicks;
            final double rotZ = storage.prevRotation.z + (storage.rotation.z - storage.prevRotation.z) * partialTicks;
            if (!storage.isAttachedToOwner(currentTick)) {
                bus.b((float)rotX, 1.0f, 0.0f, 0.0f);
                bus.b((float)rotY, 0.0f, 1.0f, 0.0f);
                bus.b((float)rotZ, 0.0f, 0.0f, 1.0f);
            }
            bus.m();
            bus.f();
            bus.I();
            final boolean playing = storage.animationController.isPlaying(currentTick);
            if (playing) {
                storage.animationController.transformAndRender((IModelTransformer)this, entityIn, (CosmeticData)cosmeticData, movementFactor, walkingSpeed, currentTick, scale, partialTicks, storage.rightShoulder);
            }
            else {
                this.renderModel(scale);
            }
            bus.H();
        }
    }
    
    public void onRenderWorld() {
        final vg entity = bib.z().aa();
        if (entity instanceof bua && bib.z().t.aw == 0) {
            final cct renderplayer = (cct)bib.z().ac().a((vg)entity);
            final bua player = (bua)entity;
            if (renderplayer.h() instanceof ModelCosmetics) {
                final UserManager userManager = LabyMod.getInstance().getUserManager();
                final User user = userManager.getUser(entity.bm());
                final CosmeticData data = user.getCosmetics().get(this.id);
                final User clientUser = userManager.getClientUser();
                final boolean canSeeDraftCosmetics = clientUser != null && clientUser.isCanSeeDraftCosmetics();
                final boolean allowedToSee = data != null && (!data.isDraft() || canSeeDraftCosmetics);
                if (data != null && data.isEnabled() && allowedToSee && this.ai instanceof WalkingPet) {
                    final float partialTicks = LabyMod.getInstance().getPartialTicks();
                    final float renderYaw = player.aO + (player.aN - player.aO) * partialTicks;
                    bus.G();
                    bus.f();
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    bus.l();
                    bus.f();
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    bus.f();
                    bus.a(0);
                    bus.a(1);
                    bus.h();
                    bus.a(1032, 5634);
                    bus.b(1.0f, -1.0f, -1.0f);
                    bus.b(0.0, -1.5, 0.0);
                    bus.b(renderYaw, 0.0f, 1.0f, 0.0f);
                    this.render((ModelCosmetics)renderplayer.h(), entity, data, 0.0625f, 0.0f, 0.0f, player.T + partialTicks, 0.0f, 0.0f, partialTicks, true);
                    bus.H();
                }
            }
        }
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    protected abstract nf getResourceLocation(final bua p0, final T p1);
    
    public abstract void handleEvent(final EnumTrigger p0, final PetStorage<T> p1, final float p2, final vg p3);
    
    protected abstract PetStorage<T> getStorage(final T p0);
}
