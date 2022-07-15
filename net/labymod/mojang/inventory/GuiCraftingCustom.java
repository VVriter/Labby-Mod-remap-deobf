//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.inventory;

import net.labymod.mojang.inventory.scale.*;

public class GuiCraftingCustom extends bmn
{
    private InventoryScaleChanger inventoryScaleChanger;
    
    public GuiCraftingCustom(final aec playerInv, final amu worldIn) {
        super(playerInv, worldIn);
        this.inventoryScaleChanger = new InventoryScaleChanger();
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
    }
}
