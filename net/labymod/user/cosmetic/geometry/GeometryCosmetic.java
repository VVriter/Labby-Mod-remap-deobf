//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.*;
import net.labymod.support.util.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.geometry.blockbench.*;
import net.labymod.main.*;
import net.labymod.core.*;
import org.lwjgl.opengl.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.user.cosmetic.animation.model.*;

public abstract class GeometryCosmetic<T extends CosmeticData> extends CosmeticRenderer<T> implements IModelTransformer
{
    private final RemoteObject data;
    private IGeometryProviderCallback<T> callback;
    private BlockBenchLoader geometry;
    private AnimationLoader animation;
    
    public GeometryCosmetic(final RemoteObject data, final IGeometryProviderCallback<T> callback) {
        this.data = data;
        this.callback = callback;
    }
    
    public void addModels(final ModelCosmetics baseModel, final float modelSize) {
        try {
            this.geometry = new GeometryLoader(this.callback.getGeometryJson()).toBlockBenchLoader((bqf)baseModel);
            this.animation = new AnimationLoader(this.callback.getAnimationJson()).load();
        }
        catch (Exception e) {
            Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Can't load " + this.data.name + ": " + e.getMessage());
        }
    }
    
    public void setInvisible(final boolean invisible) {
        if (this.geometry != null && this.geometry.getModel() != null) {
            this.geometry.getModel().showModel = invisible;
        }
    }
    
    public void transform(final String boneName, final KeyframeVector rotation, final KeyframeVector position, final KeyframeVector scale) {
        final GeometryModelRenderer model = (this.geometry == null) ? null : this.geometry.getModel(boneName);
        if (model != null) {
            final Item item = this.geometry.getItem(boneName);
            final double fixedRotationX = (item.rotation == null) ? 0.0 : item.rotation.get(0);
            final double fixedRotationY = (item.rotation == null) ? 0.0 : item.rotation.get(1);
            final double fixedRotationZ = (item.rotation == null) ? 0.0 : item.rotation.get(2);
            model.rotateAngleX = (float)Math.toRadians(rotation.x - fixedRotationX);
            model.rotateAngleY = (float)Math.toRadians(rotation.y - fixedRotationY);
            model.rotateAngleZ = (float)Math.toRadians(rotation.z + fixedRotationZ);
            model.offsetX = (float)(position.x / 16.0);
            model.offsetY = (float)(-position.y / 16.0);
            model.offsetZ = (float)(position.z / 16.0);
            model.scaleX = (float)scale.x;
            model.scaleY = (float)scale.y;
            model.scaleZ = (float)scale.z;
        }
    }
    
    public void resetTransformation(final String boneName) {
        final GeometryModelRenderer model = (this.geometry == null) ? null : this.geometry.getModel(boneName);
        if (model != null) {
            final Item item = this.geometry.getItem(boneName);
            model.rotateAngleX = ((item.rotation == null) ? 0.0f : ((float)Math.toRadians(-item.rotation.get(0))));
            model.rotateAngleY = ((item.rotation == null) ? 0.0f : ((float)Math.toRadians(-item.rotation.get(1))));
            model.rotateAngleZ = ((item.rotation == null) ? 0.0f : ((float)Math.toRadians(item.rotation.get(2))));
            model.offsetX = 0.0f;
            model.offsetY = 0.0f;
            model.offsetZ = 0.0f;
            model.scaleX = 1.0f;
            model.scaleY = 1.0f;
            model.scaleZ = 1.0f;
        }
    }
    
    public BlockBenchLoader getGeometry() {
        return this.geometry;
    }
    
    public void renderModel(final float renderScale) {
        if (this.geometry != null && this.geometry.getModel() != null) {
            this.geometry.getModel().render(renderScale);
        }
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final T cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float currentTick, final float firstRotationX, final float secondRotationX, final float partialTicks, final boolean firstPersonContext) {
        boolean swap = LabyMod.getSettings().leftHand;
        final aip itemStack = LabyModCore.getMinecraft().getMainHandItem();
        final int itemId = (itemStack != null && itemStack.c() != null) ? ain.a(itemStack.c()) : 0;
        if (LabyMod.getSettings().swapBow && itemId == 261) {
            swap = !swap;
        }
        if ((swap && LabyModCore.getMinecraft().getItemInUseMaxCount() != 0 && itemId == 261) || (swap && LabyMod.getInstance().isHasLeftHand())) {
            swap = false;
        }
        bus.G();
        if (swap && this.data.attachedTo.canBeMirrored() && !firstPersonContext) {
            bus.b(-1.0f, 1.0f, 1.0f);
            bus.r();
        }
        final nf texture = this.callback.getTexture(entityIn, cosmeticData);
        if (texture != null) {
            final int tex = GL11.glGetInteger(32873);
            bib.z().N().a(texture);
            final boolean showBothHands = !firstPersonContext || (entityIn instanceof aed && LabyModCore.getMinecraft().isMapInMainHand((aed)entityIn));
            if (this.data.mirror && this.data.attachedTo.canBeMirrored()) {
                for (int i = 0; i < 2; ++i) {
                    final boolean mirrored = i == 1;
                    if (showBothHands || i != 0) {
                        this.renderGeometryWithSide(entityIn, modelCosmetics, cosmeticData, movementFactor, walkingSpeed, currentTick, partialTicks, scale, texture, mirrored);
                    }
                }
            }
            else {
                final boolean mirrored2 = cosmeticData instanceof RemoteData && ((RemoteData)cosmeticData).rightSide;
                if (showBothHands || (cosmeticData instanceof RemoteData && ((RemoteData)cosmeticData).rightSide)) {
                    this.renderGeometryWithSide(entityIn, modelCosmetics, cosmeticData, movementFactor, walkingSpeed, currentTick, partialTicks, scale, texture, mirrored2);
                }
            }
            bus.i(tex);
        }
        if (swap && this.data.attachedTo.canBeMirrored() && !firstPersonContext) {
            bus.b(-1.0f, 1.0f, 1.0f);
            bus.r();
        }
        bus.H();
    }
    
    private void renderGeometryWithSide(final vg entityIn, final ModelCosmetics modelCosmetics, final T cosmeticData, final float movementFactor, final float walkingSpeed, final float currentTick, final float partialTicks, final float scaleIn, final nf texture, final boolean mirrored) {
        bus.G();
        bus.r();
        bus.m();
        bus.f();
        bus.I();
        final float scale = (float)(0.0625 / this.data.scale);
        if (this.data.attachedTo == EnumAttachedTo.ARM || this.data.attachedTo == EnumAttachedTo.LEG) {
            final boolean isArm = this.data.attachedTo == EnumAttachedTo.ARM;
            final brs model = isArm ? (mirrored ? modelCosmetics.h : modelCosmetics.i) : (mirrored ? modelCosmetics.j : modelCosmetics.k);
            final boolean slim = modelCosmetics.isSlim();
            bus.c(model.c * scale, model.d * scale, model.e * scale);
            bus.b((float)Math.toDegrees(model.h), 0.0f, 0.0f, 1.0f);
            bus.b((float)Math.toDegrees(model.g), 0.0f, 1.0f, 0.0f);
            bus.b((float)Math.toDegrees(model.f), 1.0f, 0.0f, 0.0f);
            if (mirrored) {
                if (this.data.mirrorType == EnumMirrorType.DUPLICATE) {
                    if (isArm) {
                        bus.c(slim ? (-scale) : (-scale * 2.0f), 0.0f, 0.0f);
                    }
                }
                else if (this.data.mirrorType == EnumMirrorType.MIRROR) {
                    bus.b(-1.0f, 1.0f, 1.0f);
                }
                else {
                    bus.b(180.0f, 0.0f, 1.0f, 0.0f);
                }
            }
            if (isArm) {
                bus.c(slim ? (scale / 2.0f) : scale, -2.0f * scale, 0.0f);
            }
        }
        if (this.data.attachedTo == EnumAttachedTo.BODY) {
            bus.b((float)Math.toDegrees(modelCosmetics.g.g), 0.0f, 1.0f, 0.0f);
            bus.b((float)Math.toDegrees(modelCosmetics.g.f), 1.0f, 0.0f, 0.0f);
        }
        if (this.data.attachedTo == EnumAttachedTo.HEAD) {
            if (modelCosmetics.n) {
                bus.c(0.0f, 1.0f * scale, 0.0f);
            }
            bus.b((float)Math.toDegrees(modelCosmetics.e.g), 0.0f, 1.0f, 0.0f);
            bus.b((float)Math.toDegrees(modelCosmetics.e.f), 1.0f, 0.0f, 0.0f);
        }
        final AnimationStorage storage = this.getAnimationStorage(cosmeticData);
        if (storage != null) {
            final boolean sneaking = entityIn.aU();
            final boolean moving = walkingSpeed > 0.1;
            if (sneaking && !storage.lastSneaking) {
                this.handleTrigger(EnumTrigger.START_SNEAKING, storage.controller, currentTick, entityIn);
            }
            else if (!sneaking && storage.lastSneaking) {
                this.handleTrigger(EnumTrigger.STOP_SNEAKING, storage.controller, currentTick, entityIn);
            }
            storage.lastSneaking = sneaking;
            if (moving && !storage.lastMoving) {
                this.handleTrigger(EnumTrigger.START_MOVING, storage.controller, currentTick, entityIn);
            }
            else if (!moving && storage.lastMoving) {
                this.handleTrigger(EnumTrigger.STOP_MOVING, storage.controller, currentTick, entityIn);
            }
            storage.lastMoving = moving;
            if (System.currentTimeMillis() > storage.nextTriggerTime || !storage.controller.isPlaying(currentTick)) {
                storage.nextTriggerTime = System.currentTimeMillis() + 500L;
                final EnumTrigger trigger = moving ? (sneaking ? EnumTrigger.SNEAK_MOVING : EnumTrigger.MOVING) : (sneaking ? EnumTrigger.SNEAK_IDLE : EnumTrigger.IDLE);
                this.handleTrigger(trigger, storage.controller, currentTick, entityIn);
            }
            if (this.geometry != null && this.geometry.getModel() != null) {
                if (cosmeticData instanceof RemoteData) {
                    final RemoteData remoteData = (RemoteData)cosmeticData;
                    if (remoteData.offset != null) {
                        bus.b(remoteData.offset.x / 16.0 / this.data.scale, -remoteData.offset.y / 16.0 / this.data.scale, remoteData.offset.z / 16.0 / this.data.scale);
                    }
                }
                storage.controller.transformAndRender((IModelTransformer)this, entityIn, (CosmeticData)cosmeticData, movementFactor, walkingSpeed, currentTick, scaleIn, partialTicks, mirrored);
                if (cosmeticData instanceof RemoteData) {
                    ((RemoteData)cosmeticData).depthMapChangedInThisFrame = false;
                }
            }
        }
        bus.H();
    }
    
    private void handleTrigger(final EnumTrigger trigger, final AnimationController animationController, final float currentTick, final vg entity) {
        if (this.animation != null) {
            final Animation animation = this.animation.getAnimationByTrigger(trigger, entity);
            if (animation != null) {
                final String forceString = animation.getMetaValue(EnumAnimationMetaType.FORCE);
                final String queueString = animation.getMetaValue(EnumAnimationMetaType.QUEUE);
                final boolean force = Boolean.parseBoolean(forceString);
                final boolean queue = Boolean.parseBoolean(queueString);
                final boolean isPlaying = animationController.isPlaying(currentTick);
                final boolean sameAnimation = animationController.getCurrentAnimation() == animation || animationController.hasAnimationInQueue(animation) || (animationController.getCurrentAnimation() != null && animationController.getCurrentAnimation().hasTrigger(trigger));
                if (force) {
                    animationController.play(animation, currentTick);
                }
                else if (isPlaying) {
                    if (!sameAnimation) {
                        if (queue) {
                            animationController.addToPlayQueue(animation);
                            final Animation currentAnimation = animationController.getCurrentAnimation();
                            final String currentSpeedString = currentAnimation.getMetaValue(EnumAnimationMetaType.SPEED);
                            if (currentSpeedString != null && animationController.getSpeed() == 1.0f) {
                                final float currentSpeed = Float.parseFloat(currentSpeedString);
                                animationController.setSpeed(currentSpeed, currentTick);
                            }
                        }
                        else {
                            animationController.play(animation, currentTick);
                        }
                    }
                }
                else {
                    animationController.play(animation, currentTick);
                }
            }
        }
    }
    
    protected abstract AnimationStorage getAnimationStorage(final T p0);
    
    public float getNameTagHeight() {
        return this.data.nametagOffset;
    }
    
    public int getCosmeticId() {
        return this.data.id;
    }
    
    public String getCosmeticName() {
        return this.data.name;
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public boolean isVisibleInFirstPerson(final CosmeticData data, final boolean rightHand) {
        return this.data != null && this.data.attachedTo == EnumAttachedTo.ARM;
    }
}
