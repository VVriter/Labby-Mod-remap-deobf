//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.labymod.settings.*;
import net.labymod.main.*;
import net.labymod.core.*;

public class TabbedGuiButton extends CustomGuiButton
{
    private nf icon;
    private IconRenderCallback iconRenderCallback;
    private boolean rightBound;
    
    public TabbedGuiButton(final nf icon, final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
        this.rightBound = false;
        this.icon = icon;
    }
    
    public TabbedGuiButton(final nf icon, final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.rightBound = false;
        this.icon = icon;
    }
    
    public TabbedGuiButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(null, buttonId, x, y, buttonText);
    }
    
    public TabbedGuiButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        this(null, buttonId, x, y, widthIn, heightIn, buttonText);
    }
    
    private boolean isHovered(int mouseX, int mouseY) {
        if (bib.z().m != null && bib.z().m instanceof LabyModModuleEditorGui) {
            final bit scaled = LabyMod.getInstance().getDrawUtils().getScaledResolution();
            final double rescale = scaled.e() / LabyMod.getInstance().getDrawUtils().getCustomScaling();
            mouseX /= (int)rescale;
            mouseY /= (int)rescale;
        }
        final int xPosition = LabyModCore.getMinecraft().getXPosition((bja)this);
        final int yPosition = LabyModCore.getMinecraft().getYPosition((bja)this);
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + this.f && mouseY < yPosition + this.g;
    }
    
    public void a(final bib mc, final int mouseX, final int mouseY, final float partialTicks) {
        this.drawButton(mc, mouseX, mouseY);
    }
    
    public void drawButton(final bib mc, final int mouseX, final int mouseY) {
        if (this.m) {
            final int xPosition = LabyModCore.getMinecraft().getXPosition((bja)this);
            final int yPosition = LabyModCore.getMinecraft().getYPosition((bja)this);
            final bip fontrenderer = LabyModCore.getMinecraft().getFontRenderer();
            mc.N().a(TabbedGuiButton.a);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            this.n = this.isHovered(mouseX, mouseY);
            final int i = this.a(this.n);
            bus.m();
            bus.a(770, 771, 1, 0);
            bus.b(770, 771);
            this.b(xPosition, yPosition, 0, 46 + i * 20, this.f / 2, this.g);
            this.b(xPosition + this.f / 2, yPosition, 200 - this.f / 2, 46 + i * 20, this.f / 2, this.g);
            this.a(mc, mouseX, mouseY);
            int j = 14737632;
            if (!this.l) {
                j = 10526880;
            }
            else if (this.n) {
                j = 16777120;
            }
            if (this.icon == null) {
                this.a(fontrenderer, this.j, xPosition + this.f / 2, yPosition + (this.g - 8) / 2, j);
            }
            else {
                final int padding = 3;
                final int iconSize = this.g - padding * 2;
                final int stringWidth = LabyMod.getInstance().getDrawUtils().getStringWidth(this.j) + iconSize + padding;
                bib.z().N().a(this.icon);
                if (this.iconRenderCallback == null) {
                    LabyMod.getInstance().getDrawUtils().drawTexture(xPosition + this.f / 2 - stringWidth / 2, yPosition + padding, 256.0, 256.0, iconSize, iconSize);
                }
                else {
                    this.iconRenderCallback.render(xPosition + this.f / 2 - stringWidth / 2, yPosition + padding, iconSize);
                }
                this.c(fontrenderer, this.j, xPosition - 1 + this.f / 2 - stringWidth / 2 + iconSize + padding, yPosition + (this.g - 8) / 2, j);
            }
        }
    }
    
    public void setIconRenderCallback(final IconRenderCallback iconRenderCallback) {
        this.iconRenderCallback = iconRenderCallback;
    }
    
    public void setRightBound(final boolean rightBound) {
        this.rightBound = rightBound;
    }
    
    public boolean isRightBound() {
        return this.rightBound;
    }
    
    public interface IconRenderCallback
    {
        void render(final int p0, final int p1, final int p2);
    }
}
