//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

import net.labymod.mojang.*;

public interface RenderPlayerAdapter
{
    String[] getSkinMapNames();
    
    ccg[] getLayerRenderers(final cct p0);
    
    void renderName(final RenderPlayerHook.RenderPlayerCustom p0, final bua p1, final double p2, final double p3, final double p4);
    
    RenderPlayerHook.RenderPlayerCustom getRenderPlayer(final bzf p0, final boolean p1);
}
