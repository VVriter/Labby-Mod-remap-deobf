//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang;

import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import java.lang.reflect.*;
import net.labymod.user.cosmetic.*;
import net.minecraftforge.common.*;

public class RenderPlayerHook
{
    public RenderPlayerHook() {
        try {
            final Field skinMapField = ReflectionHelper.findField(bzf.class, LabyModCore.getRenderPlayerImplementation().getSkinMapNames());
            final Map<String, cct> skinMap = (Map<String, cct>)skinMapField.get(bib.z().ac());
            skinMap.put("default", LabyModCore.getRenderPlayerImplementation().getRenderPlayer(bib.z().ac(), false));
            skinMap.put("slim", LabyModCore.getRenderPlayerImplementation().getRenderPlayer(bib.z().ac(), true));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public abstract static class RenderPlayerCustom extends cct
    {
        public RenderPlayerCustom(final bzf renderManager, final boolean slim) {
            super(renderManager, slim);
            this.h.clear();
            this.f = (bqf)new ModelCosmetics(0.0f, slim);
            MinecraftForge.EVENT_BUS.register(this.f);
            for (final ccg layerRenderer : LabyModCore.getRenderPlayerImplementation().getLayerRenderers((cct)this)) {
                this.a(layerRenderer);
            }
        }
        
        public void b(final bua clientPlayer) {
            super.b(clientPlayer);
            if (this.f instanceof ModelCosmetics) {
                ((ModelCosmetics)this.f).renderFirstPersonArm(clientPlayer, true);
            }
        }
        
        public void c(final bua clientPlayer) {
            super.c(clientPlayer);
            if (this.f instanceof ModelCosmetics) {
                ((ModelCosmetics)this.f).renderFirstPersonArm(clientPlayer, false);
            }
        }
        
        public abstract boolean canRenderTheName(final bua p0);
        
        public abstract void renderLabel(final bua p0, final double p1, final double p2, final double p3, final String p4, final float p5, final double p6);
    }
}
