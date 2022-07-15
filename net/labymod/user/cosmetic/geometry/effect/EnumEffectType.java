//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect;

import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.geometry.effect.effects.*;

public enum EnumEffectType
{
    PHYSICS("physics", (Class<? extends GeometryEffect>)GeometryPhysic.class), 
    HEAD_GRAVITY("headgravity", (Class<? extends GeometryEffect>)GeometryHeadGravity.class), 
    COLOR("color", (Class<? extends GeometryEffect>)GeometryColor.class), 
    LAYER("layer", (Class<? extends GeometryEffect>)GeometryLayer.class), 
    EXTRUDE("extrude", (Class<? extends GeometryEffect>)GeometryExtrude.class), 
    GLOW("glow", (Class<? extends GeometryEffect>)GeometryGlow.class);
    
    private final String prefix;
    private final Class<? extends GeometryEffect> clazz;
    
    private EnumEffectType(final String prefix, final Class<? extends GeometryEffect> clazz) {
        this.prefix = prefix;
        this.clazz = clazz;
    }
    
    public static EnumEffectType getEffectType(final String name) {
        for (final EnumEffectType type : values()) {
            if (name.startsWith(type.prefix + "_")) {
                return type;
            }
        }
        return null;
    }
    
    public static GeometryEffect createEffect(final String name, final GeometryModelRenderer model) throws Exception {
        final EnumEffectType type = getEffectType(name);
        return (type == null) ? null : type.create(name, model);
    }
    
    public GeometryEffect create(final String name, final GeometryModelRenderer model) throws Exception {
        return ((GeometryEffect)this.clazz.getConstructor(String.class, GeometryModelRenderer.class).newInstance(name, model)).load();
    }
}
