//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.minecraftforge.client.event.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FOVUpdateListener
{
    @SubscribeEvent
    public void onFovUpdate(final FOVUpdateEvent event) {
        if (LabyMod.getSettings().speedFov) {
            return;
        }
        final bud entityPlayerSP = LabyModCore.getMinecraft().getPlayer();
        float f = 1.0f;
        if (entityPlayerSP.bO.b) {
            f *= 1.1f;
        }
        double speed = 0.10000000149011612;
        if (entityPlayerSP.aV()) {
            speed = 0.13000000312924387;
        }
        f *= (float)((speed / entityPlayerSP.bO.b() + 1.0) / 2.0);
        if (entityPlayerSP.bO.b() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        if (LabyModCore.getMinecraft().isHandActive() && LabyModCore.getMinecraft().getItemInUse().c() == LabyModCore.getMinecraft().getItemBow()) {
            final int i = LabyModCore.getMinecraft().getItemInUseMaxCount();
            float f2 = i / 20.0f;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            else {
                f2 *= f2;
            }
            f *= 1.0f - f2 * 0.15f;
        }
        LabyModCore.getForge().setNewFov(event, f);
    }
}
