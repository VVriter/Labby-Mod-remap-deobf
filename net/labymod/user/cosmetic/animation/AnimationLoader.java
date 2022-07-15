//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.animation;

import java.io.*;
import java.util.*;
import net.labymod.user.cosmetic.animation.model.*;
import com.google.gson.*;
import net.labymod.main.*;

public class AnimationLoader
{
    private static final Gson GSON;
    private final JsonObject tree;
    private final Map<String, Animation> animations;
    
    public AnimationLoader(final JsonObject tree) throws IOException {
        this.animations = new HashMap<String, Animation>();
        this.tree = tree;
    }
    
    public AnimationLoader(final InputStream inputStream) throws IOException {
        this((JsonObject)AnimationLoader.GSON.fromJson((Reader)new InputStreamReader(inputStream), (Class)JsonObject.class));
        inputStream.close();
    }
    
    public AnimationLoader(final String json) throws IOException {
        this((JsonObject)AnimationLoader.GSON.fromJson(json, (Class)JsonObject.class));
    }
    
    public AnimationLoader(final File file) throws IOException {
        this(new FileInputStream(file));
    }
    
    public AnimationLoader load() {
        if (!this.tree.has("animations") || !this.tree.get("animations").isJsonObject()) {
            return this;
        }
        final JsonObject animations = this.tree.get("animations").getAsJsonObject();
        for (final Map.Entry<String, JsonElement> animationEntry : animations.entrySet()) {
            final String animationName = animationEntry.getKey();
            final JsonObject animationObject = animationEntry.getValue().getAsJsonObject();
            if (animationObject.has("bones")) {
                final JsonObject bones = animationObject.get("bones").getAsJsonObject();
                final Animation animation = new Animation(animationName);
                if (animationObject.has("anim_time_update")) {
                    animation.parseMeta(animationObject.get("anim_time_update").getAsString());
                }
                else {
                    animation.parseMeta(animationObject);
                }
                for (final Map.Entry<String, JsonElement> boneEntry : bones.entrySet()) {
                    final String boneName = boneEntry.getKey();
                    final JsonObject bone = boneEntry.getValue().getAsJsonObject();
                    final BoneAnimation boneAnimation = animation.getBoneAnimation(boneName);
                    this.extractKeyframes(boneAnimation.rotation, bone, "rotation");
                    this.extractKeyframes(boneAnimation.position, bone, "position");
                    this.extractKeyframes(boneAnimation.scale, bone, "scale");
                }
                this.animations.put(animationName, animation);
            }
        }
        return this;
    }
    
    private void extractKeyframes(final Keyframes storage, final JsonObject bone, final String key) {
        if (bone.has(key)) {
            final JsonElement type = bone.get(key);
            if (type.isJsonArray()) {
                this.pushVector(storage, 0L, type.getAsJsonArray(), false);
            }
            else if (type.isJsonObject()) {
                final JsonObject object = type.getAsJsonObject();
                if (object.has("post")) {
                    final boolean smooth = object.has("lerp_mode") && object.get("lerp_mode").getAsString().equals("catmullrom");
                    this.pushVector(storage, 0L, object.get("post").getAsJsonArray(), smooth);
                }
                else {
                    for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        final long offset = (long)(Double.parseDouble(entry.getKey()) * 1000.0);
                        final JsonElement value = entry.getValue();
                        JsonArray array = null;
                        boolean smooth2 = false;
                        if (value.isJsonArray()) {
                            array = value.getAsJsonArray();
                        }
                        else {
                            final JsonObject entryObject = value.getAsJsonObject();
                            array = entryObject.get("post").getAsJsonArray();
                            smooth2 = (entryObject.has("lerp_mode") && entryObject.get("lerp_mode").getAsString().equals("catmullrom"));
                        }
                        this.pushVector(storage, offset, array, smooth2);
                    }
                }
            }
            else {
                final float value2 = type.getAsFloat();
                final JsonArray array2 = new JsonArray();
                array2.add((JsonElement)new JsonPrimitive((Number)value2));
                array2.add((JsonElement)new JsonPrimitive((Number)value2));
                array2.add((JsonElement)new JsonPrimitive((Number)value2));
                this.pushVector(storage, 0L, array2, false);
            }
        }
    }
    
    private void pushVector(final Keyframes storage, final long offset, final JsonArray arrayVector, final boolean smooth) {
        final float x = arrayVector.get(0).getAsFloat();
        final float y = arrayVector.get(1).getAsFloat();
        final float z = arrayVector.get(2).getAsFloat();
        storage.add(offset, x, y, z, smooth);
    }
    
    public Animation getAnimationByTrigger(final EnumTrigger triggerType, final vg entity) {
        Animation lastAnimation = null;
        int totalProbability = 0;
        int totalAnimations = 0;
        for (final Animation animation : this.animations.values()) {
            if (animation.hasTrigger(triggerType) && animation.meetsConditions(entity)) {
                lastAnimation = animation;
                final Integer probability = animation.getProbability();
                if (probability == null) {
                    continue;
                }
                totalProbability += probability;
                ++totalAnimations;
            }
        }
        if (totalAnimations >= 2) {
            final int targetIndex = LabyMod.getRandom().nextInt(totalProbability);
            int index = 0;
            for (final Animation animation2 : this.animations.values()) {
                if (animation2.hasTrigger(triggerType)) {
                    final Integer probability2 = animation2.getProbability();
                    if (probability2 == null) {
                        continue;
                    }
                    if (targetIndex >= index && targetIndex < index + probability2) {
                        return animation2;
                    }
                    index += probability2;
                }
            }
            return null;
        }
        return lastAnimation;
    }
    
    public Animation getAnimation(final String name) {
        return this.animations.get(name);
    }
    
    static {
        GSON = new Gson();
    }
}
