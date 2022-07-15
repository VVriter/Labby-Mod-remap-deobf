//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.effects;

import net.labymod.user.cosmetic.geometry.effect.*;
import net.labymod.user.cosmetic.geometry.effect.util.*;
import net.labymod.user.cosmetic.geometry.render.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;

public class GeometryPhysic extends GeometryEffect
{
    private int version;
    private PhysicMapping mappingX;
    private PhysicMapping mappingY;
    private PhysicMapping mappingZ;
    private double strength;
    private boolean mirrorX;
    private boolean mirrorY;
    private boolean mirrorZ;
    
    public GeometryPhysic(final String name, final GeometryModelRenderer model) {
        super(name, model);
        this.mappingX = PhysicMapping.F;
        this.mappingY = PhysicMapping.G;
        this.mappingZ = PhysicMapping.S;
        this.strength = -1.0;
        this.mirrorX = false;
        this.mirrorY = false;
        this.mirrorZ = false;
    }
    
    @Override
    protected boolean parse() {
        this.strength = Integer.parseInt(this.getParameter(0)) / 50.0f;
        final String mappingV2 = this.getParameter(1, 3);
        if (mappingV2 != null) {
            this.version = 2;
            if (mappingV2.isEmpty()) {
                return false;
            }
            this.mappingX = PhysicMapping.get(mappingV2.charAt(0));
            this.mappingY = PhysicMapping.get(mappingV2.charAt(1));
            this.mappingZ = PhysicMapping.get(mappingV2.charAt(2));
            final String mirror = this.getParameter(2, 3);
            if (mirror == null || mirror.isEmpty()) {
                return false;
            }
            this.mirrorX = (mirror.charAt(0) == 'n');
            this.mirrorY = (mirror.charAt(1) == 'n');
            this.mirrorZ = (mirror.charAt(2) == 'n');
        }
        else {
            this.version = 1;
            final String mapping = this.getParameter(1, 2);
            final String mirror2 = this.getParameter(2, 2);
            if (mapping == null || mapping.isEmpty()) {
                return false;
            }
            this.mappingX = PhysicMapping.get(mapping.charAt(0));
            this.mappingZ = PhysicMapping.get(mapping.charAt(1));
            if (mirror2 == null || mirror2.isEmpty()) {
                return false;
            }
            this.mirrorX = (mirror2.charAt(0) == 'n');
            this.mirrorZ = (mirror2.charAt(1) == 'n');
        }
        return true;
    }
    
    @Override
    public void apply(final RemoteData remoteData, final MetaEffectFrameParameter meta) {
        final float strength = (float)this.strength;
        final float forward = meta.forward * (this.isMirrorX() ? -1 : 1) * strength;
        final float gravity = meta.gravity * (this.isMirrorY() ? -1 : 1) * strength;
        final float strafe = meta.strafe * (this.isMirrorZ() ? -1 : 1) * strength;
        if (this.version == 1) {
            switch (this.getMappingX()) {
                case X: {
                    this.model.rotateAngleX = forward;
                    break;
                }
                case Y: {
                    this.model.rotateAngleY = forward;
                    break;
                }
                case Z: {
                    this.model.rotateAngleZ = forward;
                    break;
                }
            }
            switch (this.getMappingZ()) {
                case X: {
                    this.model.rotateAngleX = strafe;
                    break;
                }
                case Y: {
                    this.model.rotateAngleY = strafe;
                    break;
                }
                case Z: {
                    this.model.rotateAngleZ = strafe;
                    break;
                }
            }
        }
        else if (this.version == 2) {
            this.model.rotateAngleX = ((this.mappingX == PhysicMapping.F) ? forward : ((this.mappingX == PhysicMapping.G) ? gravity : ((this.mappingX == PhysicMapping.S) ? strafe : 0.0f)));
            this.model.rotateAngleY = ((this.mappingY == PhysicMapping.F) ? forward : ((this.mappingY == PhysicMapping.G) ? gravity : ((this.mappingY == PhysicMapping.S) ? strafe : 0.0f)));
            this.model.rotateAngleZ = ((this.mappingZ == PhysicMapping.F) ? forward : ((this.mappingZ == PhysicMapping.G) ? gravity : ((this.mappingZ == PhysicMapping.S) ? strafe : 0.0f)));
        }
    }
    
    public double getStrength() {
        return this.strength;
    }
    
    @Override
    protected int getParametersAmount() {
        return 5;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public PhysicMapping getMappingX() {
        return this.mappingX;
    }
    
    public PhysicMapping getMappingY() {
        return this.mappingY;
    }
    
    public PhysicMapping getMappingZ() {
        return this.mappingZ;
    }
    
    public boolean isMirrorX() {
        return this.mirrorX;
    }
    
    public boolean isMirrorY() {
        return this.mirrorY;
    }
    
    public boolean isMirrorZ() {
        return this.mirrorZ;
    }
}
