//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.minecraftforge.fml.relauncher.*;
import net.labymod.ingamegui.*;
import net.labymod.utils.*;
import java.io.*;
import net.labymod.ingamegui.enums.*;
import net.labymod.main.*;
import net.labymod.utils.manager.*;
import net.labymod.main.lang.*;
import net.labymod.gui.elements.*;

@SideOnly(Side.CLIENT)
public class ModGuiIngameMenu extends blg
{
    private static final ModuleGui moduleGui;
    private boolean confirmDisconnect;
    
    public void b() {
        super.b();
        this.confirmDisconnect = false;
        ModGuiIngameMenu.moduleGui.b();
        Module.setCurrentModuleGui(ModGuiIngameMenu.moduleGui);
        Tabs.initGui((blk)this);
    }
    
    public void m() {
        super.m();
        ModGuiIngameMenu.moduleGui.m();
        Module.setCurrentModuleGui(null);
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 1 && !bib.z().E() && !this.confirmDisconnect && LabyMod.getSettings().confirmDisconnect) {
            button.j = ModColor.RED + "Press again to confirm disconnect";
            this.confirmDisconnect = true;
            return;
        }
        super.a(button);
    }
    
    public void e() {
        super.e();
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        Module.draw(0.0, 0.0, LabyMod.getInstance().getDrawUtils().getWidth(), LabyMod.getInstance().getDrawUtils().getHeight(), EnumDisplayType.ESCAPE, false);
        ModGuiIngameMenu.moduleGui.a(mouseX, mouseY, partialTicks);
        if (LabyMod.getSettings().signSearch) {
            final boolean hover = mouseX > 2 && mouseX < 22 && mouseY > this.m - 22 && mouseY < this.m - 2;
            bib.z().N().a(ModTextures.BUTTON_SIGNSEARCH);
            LabyMod.getInstance().getDrawUtils().drawTexture(hover ? 1.0 : 2.0, this.m - (hover ? 23 : 22), 255.0, 255.0, hover ? 22.0 : 20.0, hover ? 22.0 : 20.0);
            bus.m();
            if (hover) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 9L, LanguageManager.translate("title_sign_search"));
            }
        }
        MultiplayerTabs.drawParty(mouseX, mouseY, this.l);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        ModGuiIngameMenu.moduleGui.a(mouseX, mouseY, mouseButton);
        final boolean hover = mouseX > 2 && mouseX < 22 && mouseY > this.m - 22 && mouseY < this.m - 2;
        if (hover && LabyMod.getSettings().signSearch) {
            this.j.a((blk)new GuiSignSearch((blk)null));
        }
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        ModGuiIngameMenu.moduleGui.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void k() throws IOException {
        super.k();
        ModGuiIngameMenu.moduleGui.k();
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        ModGuiIngameMenu.moduleGui.a(typedChar, keyCode);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        ModGuiIngameMenu.moduleGui.b(mouseX, mouseY, state);
    }
    
    static {
        moduleGui = new ModuleGui(false, false, EnumDisplayType.ESCAPE);
    }
}
