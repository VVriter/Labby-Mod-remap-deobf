//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.minecraftforge.fml.common.gameevent.*;
import net.labymod.main.*;
import net.labymod.settings.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class RenderTickListener
{
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        LabyMod.getInstance().setPartialTicks(event.renderTickTime);
        LabyMod.getInstance().getDrawUtils().setScaledResolution(new bit(bib.z()));
        if (bib.z().m != null) {
            PreviewRenderer.getInstance().createFrame();
        }
    }
    
    public void drawMenuOverlay(final int mouseX, final int mouseY, final float partialTicks) {
        if (!LabyMod.getInstance().isInGame()) {
            LabyMod.getInstance().getGuiCustomAchievement().updateAchievementWindow();
        }
    }
}
