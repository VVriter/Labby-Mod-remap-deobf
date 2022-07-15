//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.effects;

import net.labymod.user.cosmetic.geometry.effect.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.user.cosmetic.geometry.render.*;

public class GeometryExtrude extends GeometryEffect
{
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean hasCube;
    
    public GeometryExtrude(final String name, final GeometryModelRenderer model) {
        super(name, model);
        this.x = 0;
        this.y = 0;
        this.width = 1;
        this.height = 1;
        this.hasCube = false;
    }
    
    @Override
    protected boolean parse() {
        return true;
    }
    
    @Override
    protected int getParametersAmount() {
        return 0;
    }
    
    @Override
    public boolean onCubeAdd(final GeometryModelRenderer target, final float x, final float y, final float z, final int sizeX, final int sizeY, final int sizeZ, final float inflate, final boolean mirror) {
        final int originOffsetX = target.textureOffsetX;
        final int originOffsetY = target.textureOffsetY;
        for (int relY = 0; relY < sizeY; ++relY) {
            for (int relX = 0; relX < sizeX; ++relX) {
                target.setTextureOffset(originOffsetX + relX, originOffsetY + relY);
                target.addBox(x + relX + 0.5f, y + relY + 0.5f, z, 0.01f, 0.01f, 0.01f, 0.5f, mirror);
            }
        }
        this.x = originOffsetX;
        this.y = originOffsetY;
        this.width = sizeX;
        this.height = sizeY;
        this.hasCube = true;
        return false;
    }
    
    @Override
    public void apply(final RemoteData remoteData, final MetaEffectFrameParameter meta) {
        if (!this.hasCube) {
            this.model.extruded = null;
            return;
        }
        this.model.extruded = new Extruded(this, remoteData.depthMap);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
}
