//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core.*;

public class WorldRendererImplementation implements WorldRendererAdapter
{
    private static WorldRendererImplementation instance;
    private buk worldRenderer;
    
    public WorldRendererImplementation() {
        this.worldRenderer = bve.a().c();
    }
    
    public static WorldRendererImplementation getInstance() {
        return WorldRendererImplementation.instance;
    }
    
    public void begin(final int glMode, final cea vertexFormat) {
        this.worldRenderer.a(glMode, vertexFormat);
    }
    
    public WorldRendererAdapter pos(final double x, final double y, final double z) {
        this.worldRenderer.b(x, y, z);
        return (WorldRendererAdapter)this;
    }
    
    public WorldRendererAdapter tex(final double u, final double v) {
        this.worldRenderer.a(u, v);
        return (WorldRendererAdapter)this;
    }
    
    public WorldRendererAdapter color(final float red, final float green, final float blue, final float alpha) {
        this.worldRenderer.a(red, green, blue, alpha);
        return (WorldRendererAdapter)this;
    }
    
    public WorldRendererAdapter color(final int red, final int green, final int blue, final int alpha) {
        this.worldRenderer.b(red, green, blue, alpha);
        return (WorldRendererAdapter)this;
    }
    
    public WorldRendererAdapter normal(final float x, final float y, final float z) {
        this.worldRenderer.c(x, y, z);
        return (WorldRendererAdapter)this;
    }
    
    public void endVertex() {
        this.worldRenderer.d();
    }
    
    static {
        WorldRendererImplementation.instance = new WorldRendererImplementation();
    }
}
