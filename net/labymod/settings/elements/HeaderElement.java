//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.main.*;

public class HeaderElement extends SettingsElement
{
    private double textSize;
    
    public HeaderElement(final String displayName, final double textSize) {
        super(displayName, null);
        this.textSize = textSize;
    }
    
    public HeaderElement(final String displayName) {
        this(displayName, 1.0);
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final int absoluteY = y + 7;
        LabyMod.getInstance().getDrawUtils().drawCenteredString(this.getDisplayName(), x + (maxX - x) / 2, absoluteY, this.textSize);
    }
    
    @Override
    public int getEntryHeight() {
        return 22;
    }
    
    @Override
    public void drawDescription(final int x, final int y, final int screenWidth) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void mouseRelease(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public void mouseClickMove(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public void unfocus(final int mouseX, final int mouseY, final int mouseButton) {
    }
}
