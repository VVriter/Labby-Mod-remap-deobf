//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.labymod.core.*;

public class CustomGuiButton extends bja
{
    protected static final nf BUTTON_TEXTURES;
    
    public CustomGuiButton(final int buttonId, final int x, final int y, final String buttonText) {
        super(buttonId, x, y, buttonText);
    }
    
    public CustomGuiButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
    
    public void setPosition(final int minX, final int minY, final int maxX, final int maxY) {
        LabyModCore.getMinecraft().setButtonXPosition((bja)this, minX);
        LabyModCore.getMinecraft().setButtonYPosition((bja)this, minY);
        this.f = maxX - minX;
        this.g = maxY - minY;
    }
    
    public void setXPosition(final int x) {
        LabyModCore.getMinecraft().setButtonXPosition((bja)this, x);
    }
    
    public void setYPosition(final int y) {
        LabyModCore.getMinecraft().setButtonYPosition((bja)this, y);
    }
    
    public int getXPosition() {
        return LabyModCore.getMinecraft().getXPosition((bja)this);
    }
    
    public int getYPosition() {
        return LabyModCore.getMinecraft().getYPosition((bja)this);
    }
    
    public void setEnabled(final boolean enabled) {
        this.l = enabled;
    }
    
    public String getText() {
        return this.j;
    }
    
    static {
        BUTTON_TEXTURES = new nf("textures/gui/widgets.png");
    }
}
