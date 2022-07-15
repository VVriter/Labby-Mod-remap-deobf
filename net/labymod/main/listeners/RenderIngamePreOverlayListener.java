//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.api.permissions.*;
import net.labymod.gui.*;
import net.labymod.settings.*;
import net.labymod.ingamegui.enums.*;
import net.labymod.ingamegui.*;
import net.labymod.support.gui.*;
import net.labymod.utils.*;

public class RenderIngamePreOverlayListener implements RenderIngameOverlayEvent
{
    public void onRender(final float partialTicks) {
        if (LabyMod.getInstance().getPriorityOverlayRenderer().isCineScopeActive()) {
            return;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bus.G();
        LabyMod.getInstance().getServerManager().draw();
        if (!bib.z().t.ax && Permissions.isAllowed(Permissions.Permission.GUI_ALL) && !(bib.z().m instanceof ModGuiIngameMenu) && !(bib.z().m instanceof LabyModModuleEditorGui)) {
            Module.draw(0.0, 0.0, (double)draw.getWidth(), (double)draw.getHeight(), EnumDisplayType.INGAME, true);
        }
        bus.k();
        bus.e();
        LabyMod.getInstance().getEmoteRegistry().getEmoteSelectorGui().render();
        LabyMod.getInstance().getStickerRegistry().getStickerSelectorGui().render();
        LabyMod.getInstance().getUserManager().getUserActionGui().render();
        if (LabyMod.getSettings().outOfMemoryWarning && bib.z().m == null) {
            GuiMemoryUpgrade.renderTickOutOfMemoryDetector();
        }
        bus.q();
        bus.H();
    }
}
