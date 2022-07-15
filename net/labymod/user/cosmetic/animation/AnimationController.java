//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation;

import net.labymod.user.cosmetic.util.*;
import java.util.*;
import net.labymod.user.cosmetic.animation.model.*;

public class AnimationController
{
    private Animation animation;
    private float startTick;
    private float tickLength;
    private float speed;
    private float changedSpeedPointer;
    private final List<Animation> queue;
    
    public AnimationController() {
        this.speed = 1.0f;
        this.queue = new ArrayList<Animation>();
    }
    
    public void play(Animation animation, final float currentTick) {
        final boolean playFromQueue = !this.queue.isEmpty();
        if (playFromQueue) {
            animation = this.queue.remove(0);
        }
        this.animation = animation;
        this.startTick = currentTick;
        this.tickLength = animation.getLength() / 50.0f;
        this.changedSpeedPointer = 0.0f;
        float speed = 1.0f;
        final boolean hasToReachNextInQueue = !this.queue.isEmpty();
        if (hasToReachNextInQueue) {
            final String animationSpeedString = animation.getMetaValue(EnumAnimationMetaType.SPEED);
            if (animationSpeedString != null) {
                speed = Float.parseFloat(animationSpeedString);
            }
        }
        this.setSpeed(speed, currentTick);
    }
    
    public void addToPlayQueue(final Animation animation) {
        if (this.queue.size() < 10) {
            this.queue.add(animation);
        }
    }
    
    public void transformAndRender(final IModelTransformer transformer, final vg entity, final CosmeticData cosmeticData, final float movementFactor, final float walkingSpeed, final float currentTick, final float renderScale, final float partialTicks, final boolean rightSide) {
        final long offset = (long)(this.getAnimationTickTime(currentTick) * 50.0f);
        if (this.animation != null) {
            for (final Map.Entry<String, BoneAnimation> entry : this.animation.getBoneAnimations().entrySet()) {
                final BoneAnimation animation = entry.getValue();
                final KeyframeVector rotation = animation.rotation.get(offset);
                final KeyframeVector position = animation.position.get(offset);
                final KeyframeVector scale = animation.scale.get(offset);
                transformer.transform(entry.getKey(), rotation, position, scale);
            }
        }
        transformer.applyEffects(entity, cosmeticData, movementFactor, walkingSpeed, currentTick, partialTicks, rightSide);
        transformer.renderModel(renderScale);
        if (this.animation != null) {
            for (final Map.Entry<String, BoneAnimation> entry : this.animation.getBoneAnimations().entrySet()) {
                transformer.resetTransformation(entry.getKey());
            }
        }
    }
    
    private float getAnimationTickTime(final float currentTick) {
        final float tick = currentTick - this.startTick;
        final float speedTick = tick - this.changedSpeedPointer;
        if (this.speed == 1.0f) {
            return tick;
        }
        return this.changedSpeedPointer + speedTick * this.speed;
    }
    
    public boolean isPlaying(final float currentTick) {
        return this.animation != null && this.startTick <= currentTick && this.getAnimationTickTime(currentTick) < this.tickLength;
    }
    
    public void stop() {
        this.startTick = 0.0f;
    }
    
    public Animation getCurrentAnimation() {
        return this.animation;
    }
    
    public void setSpeed(final float speed, final float currentTick) {
        this.changedSpeedPointer = this.getAnimationTickTime(currentTick);
        this.speed = speed;
    }
    
    public boolean hasAnimationInQueue(final Animation animation) {
        for (final Animation animationInQueue : this.queue) {
            if (animationInQueue == animation) {
                return true;
            }
        }
        return false;
    }
    
    public float getSpeed() {
        return this.speed;
    }
}
