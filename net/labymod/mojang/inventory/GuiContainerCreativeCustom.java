//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.inventory;

import net.labymod.mojang.inventory.scale.*;
import net.labymod.core.*;
import net.labymod.main.*;

public class GuiContainerCreativeCustom extends bmp
{
    private InventoryScaleChanger inventoryScaleChanger;
    private boolean hasActivePotionEffects;
    
    public GuiContainerCreativeCustom(final aed player) {
        super(player);
        this.inventoryScaleChanger = new InventoryScaleChanger();
    }
    
    protected void a() {
        if (!LabyModCore.getMinecraft().getPlayer().ca().isEmpty()) {
            if (!LabyMod.getSettings().oldInventory) {
                this.i = 160 + (this.l - this.f - 200) / 2;
            }
            this.hasActivePotionEffects = true;
        }
        else {
            this.i = (this.l - this.f) / 2;
            this.hasActivePotionEffects = false;
        }
    }
    
    public void b() {
        if (this.inventoryScaleChanger.initGui()) {
            this.l = this.inventoryScaleChanger.getScaledWidth();
            this.m = this.inventoryScaleChanger.getScaledHeight();
        }
        super.b();
    }
    
    public void a(int mouseX, int mouseY, final float partialTicks) {
        if (this.inventoryScaleChanger.drawScreen(mouseX, mouseY)) {
            mouseX = this.inventoryScaleChanger.getMouseX();
            mouseY = this.inventoryScaleChanger.getMouseY();
        }
        super.a(mouseX, mouseY, partialTicks);
        if (this.hasActivePotionEffects) {
            LabyModCore.getRenderImplementation().drawActivePotionEffects((double)this.i, (double)this.s, LabyModCore.getRenderImplementation().getInventoryBackground());
        }
    }
}
