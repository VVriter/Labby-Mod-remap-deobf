//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.effects;

import net.labymod.user.cosmetic.geometry.effect.*;
import net.labymod.user.cosmetic.geometry.effect.util.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;

public class GeometryHeadGravity extends GeometryEffect
{
    private PhysicMapping mapping;
    private double strength;
    private boolean mirror;
    
    public GeometryHeadGravity(final String name, final GeometryModelRenderer model) {
        super(name, model);
        this.mapping = PhysicMapping.X;
        this.strength = 1.0;
        this.mirror = false;
    }
    
    @Override
    protected boolean parse() {
        this.strength = Integer.parseInt(this.getParameter(0)) / 100.0;
        final String mappingString = this.getParameter(1, 1);
        final String mirrorString = this.getParameter(2, 1);
        this.mapping = PhysicMapping.get(mappingString.charAt(0));
        this.mirror = (mirrorString.charAt(0) == 'n');
        return true;
    }
    
    @Override
    protected int getParametersAmount() {
        return 5;
    }
    
    @Override
    public void apply(final RemoteData remoteData, final MetaEffectFrameParameter meta) {
        final float rotation = (float)(-Math.toRadians(meta.pitch * (this.mirror ? -1 : 1) * this.strength));
        switch (this.mapping) {
            case X: {
                this.model.rotateAngleX = rotation;
                break;
            }
            case Y: {
                this.model.rotateAngleY = rotation;
                break;
            }
            case Z: {
                this.model.rotateAngleZ = rotation;
                break;
            }
        }
    }
}
