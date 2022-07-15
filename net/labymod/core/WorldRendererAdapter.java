//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

public interface WorldRendererAdapter
{
    void begin(final int p0, final cea p1);
    
    WorldRendererAdapter pos(final double p0, final double p1, final double p2);
    
    WorldRendererAdapter tex(final double p0, final double p1);
    
    WorldRendererAdapter color(final float p0, final float p1, final float p2, final float p3);
    
    WorldRendererAdapter color(final int p0, final int p1, final int p2, final int p3);
    
    WorldRendererAdapter normal(final float p0, final float p1, final float p2);
    
    void endVertex();
}
