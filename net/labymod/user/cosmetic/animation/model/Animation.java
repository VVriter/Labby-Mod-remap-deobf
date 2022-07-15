//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation.model;

import net.labymod.user.cosmetic.animation.*;
import com.google.gson.*;
import net.labymod.support.util.*;
import java.util.*;

public class Animation
{
    private final String name;
    private final Map<String, BoneAnimation> boneAnimation;
    private final Map<EnumAnimationMetaType, String> meta;
    private final List<EnumTrigger> triggers;
    private Integer probability;
    private final List<EnumCondition> conditions;
    
    public Animation(final String name) {
        this.boneAnimation = new HashMap<String, BoneAnimation>();
        this.meta = new HashMap<EnumAnimationMetaType, String>();
        this.triggers = new ArrayList<EnumTrigger>();
        this.probability = null;
        this.conditions = new ArrayList<EnumCondition>();
        this.name = name;
    }
    
    public void parseMeta(final JsonObject object) {
        for (final EnumAnimationMetaType type : EnumAnimationMetaType.values()) {
            if (object.has(type.getKey())) {
                this.meta.put(type, object.get(type.getKey()).getAsString());
            }
        }
    }
    
    public void parseMeta(final String command) {
        if (command.isEmpty()) {
            return;
        }
        final String[] args = command.split(" ");
        EnumAnimationMetaType lastMetaType = null;
        for (final String argument : args) {
            if (lastMetaType != null) {
                this.meta.put(lastMetaType, argument);
                lastMetaType = null;
            }
            else if ((lastMetaType = EnumAnimationMetaType.get(argument.replace("-", ""))) == null) {
                Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Invalid animation meta: " + command);
                return;
            }
        }
        this.parseMetaTrigger();
        this.parseMetaProbability();
        this.parseMetaConditions();
    }
    
    private void parseMetaTrigger() {
        final String value = this.getMetaValue(EnumAnimationMetaType.TRIGGER);
        if (value != null) {
            if (value.contains(",")) {
                for (final String triggerEntry : value.split(",")) {
                    final EnumTrigger trigger = EnumTrigger.getById(triggerEntry.toUpperCase());
                    if (trigger != null) {
                        this.triggers.add(trigger);
                    }
                }
            }
            else if (value.equals("*")) {
                Collections.addAll(this.triggers, EnumTrigger.values());
            }
            else {
                final EnumTrigger trigger2 = EnumTrigger.getById(value.toUpperCase());
                if (trigger2 != null) {
                    this.triggers.add(trigger2);
                }
            }
        }
    }
    
    private void parseMetaProbability() {
        final String probability = this.getMetaValue(EnumAnimationMetaType.PROBABILITY);
        if (probability != null) {
            try {
                this.probability = Integer.parseInt(probability);
            }
            catch (Exception e) {
                Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Invalid probability of " + this.name + ": " + probability);
            }
        }
    }
    
    private void parseMetaConditions() {
        final String conditions = this.getMetaValue(EnumAnimationMetaType.CONDITION);
        if (conditions != null) {
            try {
                for (final String condition : conditions.split(",")) {
                    this.conditions.add(EnumCondition.valueOf(condition.toUpperCase(Locale.ROOT)));
                }
            }
            catch (Exception e) {
                Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Invalid conditions of " + this.name + ": " + conditions);
            }
        }
    }
    
    public BoneAnimation getBoneAnimation(final String boneName) {
        BoneAnimation boneAnimation = this.boneAnimation.get(boneName);
        if (boneAnimation == null) {
            this.boneAnimation.put(boneName, boneAnimation = new BoneAnimation());
        }
        return boneAnimation;
    }
    
    public long getLength() {
        long maxLength = 0L;
        for (final BoneAnimation boneAnimation : this.boneAnimation.values()) {
            maxLength = Math.max(boneAnimation.getLength(), maxLength);
        }
        return maxLength;
    }
    
    public String getMetaValue(final EnumAnimationMetaType type) {
        return this.meta.get(type);
    }
    
    public List<EnumCondition> getConditions() {
        return this.conditions;
    }
    
    public boolean hasTrigger(final EnumTrigger trigger) {
        return this.triggers.contains(trigger);
    }
    
    public Integer getProbability() {
        return this.probability;
    }
    
    public Map<String, BoneAnimation> getBoneAnimations() {
        return this.boneAnimation;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean meetsConditions(final vg entity) {
        if (this.conditions.isEmpty()) {
            return true;
        }
        final double motionX = entity.m - entity.p;
        final double motionZ = entity.o - entity.r;
        final double yawSin = Math.sin(Math.toRadians(entity.v));
        final double yawCos = -Math.cos(Math.toRadians(entity.v));
        final double forward = motionX * yawSin + motionZ * yawCos;
        for (final EnumCondition condition : this.conditions) {
            switch (condition) {
                case MOTION_BACKWARDS: {
                    if (forward >= 0.0) {
                        return false;
                    }
                }
                case NO_MOTION: {
                    if (forward != 0.0) {
                        return false;
                    }
                }
                case MOTION_FORWARD: {
                    if (forward <= 0.0) {
                        return false;
                    }
                    continue;
                }
            }
        }
        return true;
    }
}
