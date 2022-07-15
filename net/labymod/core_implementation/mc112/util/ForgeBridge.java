//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.util;

import net.labymod.mojang.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ForgeBridge
{
    public static void preRenderLivingSpecials(final RenderPlayerHook.RenderPlayerCustom renderPlayer, final bua entity, final double x, final double y, final double z) {
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Specials.Pre((vp)entity, (caa)renderPlayer, x, y, z));
    }
    
    public static void postRenderLivingSpecials(final RenderPlayerHook.RenderPlayerCustom renderPlayer, final bua entity, final double x, final double y, final double z) {
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Specials.Post((vp)entity, (caa)renderPlayer, x, y, z));
    }
}
