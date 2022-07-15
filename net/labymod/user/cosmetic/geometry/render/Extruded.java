//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.render;

import net.labymod.user.cosmetic.geometry.effect.effects.*;
import net.labymod.user.cosmetic.custom.*;

public class Extruded
{
    private final GeometryExtrude geometryExtrude;
    private final DepthMap depthMap;
    
    public Extruded(final GeometryExtrude geometryExtrude, final DepthMap depthMap) {
        this.geometryExtrude = geometryExtrude;
        this.depthMap = depthMap;
    }
    
    public boolean isVisible(final int cubeIndex, final int quadIndex) {
        final int x = cubeIndex % this.geometryExtrude.getWidth() + this.geometryExtrude.getX();
        final int y = cubeIndex / this.geometryExtrude.getWidth() + this.geometryExtrude.getY();
        return this.depthMap == null || this.depthMap.shouldRenderFace(this.geometryExtrude, x, y, quadIndex);
    }
    
    public boolean hasChanged(final DepthMap depthMap) {
        return this.depthMap != null && !this.depthMap.equals((Object)depthMap);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Extruded extruded = (Extruded)o;
        return this.depthMap.equals((Object)extruded.depthMap);
    }
    
    @Override
    public int hashCode() {
        return this.depthMap.hashCode();
    }
}
