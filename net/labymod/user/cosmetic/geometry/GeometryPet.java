//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry;

import net.labymod.user.cosmetic.util.*;
import net.labymod.user.cosmetic.pet.object.*;
import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.pet.ai.*;
import net.labymod.user.cosmetic.*;
import net.labymod.support.util.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.geometry.blockbench.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.user.cosmetic.pet.*;
import net.labymod.user.cosmetic.animation.model.*;

public abstract class GeometryPet<T extends CosmeticData> extends CosmeticPet<T>
{
    private final RemoteObject data;
    private final IGeometryProviderCallback<T> callback;
    private BlockBenchLoader geometry;
    private AnimationLoader animation;
    
    public GeometryPet(final RemoteObject data, final IGeometryProviderCallback<T> callback) {
        this.init(data.id, (PetAI<T>)((data.type == EnumCosmeticType.FLYING_PET) ? new FlyingAI<T>((CosmeticPet<CosmeticData>)this) : new WalkingPet<T>((CosmeticPet<CosmeticData>)this)));
        this.moveType = data.moveType;
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
        bus.G();
        bus.r();
        if (this.geometry != null && this.geometry.getModel() != null) {
            this.geometry.getModel().render(renderScale);
        }
        bus.H();
    }
    
    @Override
    protected nf getResourceLocation(final bua player, final T data) {
        return this.callback.getTexture((vg)player, data);
    }
    
    @Override
    public void handleEvent(final EnumTrigger trigger, final PetStorage<T> data, final float tickValue, final vg player) {
        final Animation animation = (this.animation == null) ? null : this.animation.getAnimationByTrigger(trigger, player);
        if (animation != null) {
            data.animationController.play(animation, tickValue);
            data.attached = (this.ai.canAttach() && (trigger == EnumTrigger.IDLE || trigger == EnumTrigger.SNEAK_IDLE));
        }
    }
    
    public int getCosmeticId() {
        return this.data.id;
    }
    
    public String getCosmeticName() {
        return this.data.name;
    }
    
    @Override
    public boolean isOfflineAvailable() {
        return false;
    }
}
