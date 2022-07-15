//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom;

import java.awt.image.*;
import net.labymod.user.cosmetic.geometry.effect.effects.*;
import java.util.*;

public class DepthMap
{
    private final boolean[][] map;
    private final long creationTime;
    private final long hashCode;
    
    public DepthMap(final BufferedImage bufferedImage) {
        this.creationTime = System.currentTimeMillis();
        this.map = new boolean[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int x = 0; x < bufferedImage.getWidth(); ++x) {
            for (int y = 0; y < bufferedImage.getHeight(); ++y) {
                this.map[x][y] = this.hasDepth(bufferedImage.getRGB(x, y));
            }
        }
        this.hashCode = super.hashCode();
    }
    
    public boolean shouldRenderFace(final GeometryExtrude bounds, final int x, final int y, final int face) {
        return this.hasDepthAt(bounds, x, y) && !this.hasDepthInFacing(bounds, x, y, face);
    }
    
    public boolean hasDepthInFacing(final GeometryExtrude bounds, final int x, final int y, final int face) {
        switch (face) {
            case 0: {
                return this.hasDepthAt(bounds, x + 1, y);
            }
            case 1: {
                return this.hasDepthAt(bounds, x - 1, y);
            }
            case 2: {
                return this.hasDepthAt(bounds, x, y - 1);
            }
            case 3: {
                return this.hasDepthAt(bounds, x, y + 1);
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean hasDepthAt(final GeometryExtrude bounds, final int x, final int y) {
        return x >= bounds.getX() && y >= bounds.getY() && x < bounds.getX() + bounds.getWidth() && y < bounds.getY() + bounds.getHeight() && this.map[x][y];
    }
    
    public int getWidth() {
        return this.map.length;
    }
    
    public int getHeight() {
        return this.map[0].length;
    }
    
    private boolean hasDepth(final int color) {
        return (color >> 24 & 0xFF) > 0;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DepthMap depthMap = (DepthMap)o;
        return this.creationTime == depthMap.creationTime && this.hashCode == depthMap.hashCode;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.creationTime) + Objects.hash(this.hashCode);
    }
}
