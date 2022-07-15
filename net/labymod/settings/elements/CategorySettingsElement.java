//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.settings.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import java.util.*;

public class CategorySettingsElement extends SettingsElement
{
    private SettingsCategory category;
    private ClickedCallback callback;
    
    public CategorySettingsElement(final SettingsCategory category, final ClickedCallback callback) {
        super(category.getTitle(), null);
        this.category = category;
        this.callback = callback;
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final double textureSize = 292.0;
        final double textureScale = 36.0;
        final int elementWidth = maxX - x;
        final int elementHeight = maxY - y;
        bib.z().N().a(this.isMouseOver() ? ModTextures.BUTTON_LARGE_PRESSED : ModTextures.BUTTON_LARGE);
        draw.drawTexture(x, y, textureSize / 200.0 * elementWidth / 2.0, textureSize / 200.0 * elementHeight / 2.0, elementWidth / 2, elementHeight / 2);
        draw.drawTexture(x, y + elementHeight / 2, 0.0, -textureScale + textureSize - textureSize / 200.0 * elementHeight / 2.0, textureSize / 200.0 * elementWidth / 2.0, textureSize / 200.0 * elementHeight / 2.0, elementWidth / 2, elementHeight / 2);
        draw.drawTexture(x + elementWidth / 2, y, -textureScale + textureSize - textureSize / 200.0 * elementWidth / 2.0, 0.0, textureSize / 200.0 * elementWidth / 2.0, textureSize / 200.0 * elementHeight / 2.0, elementWidth / 2, elementHeight / 2);
        draw.drawTexture(x + elementWidth / 2, y + elementHeight / 2, -textureScale + textureSize - textureSize / 200.0 * elementWidth / 2.0, -textureScale + textureSize - textureSize / 200.0 * elementHeight / 2.0, textureSize / 200.0 * elementWidth / 2.0, textureSize / 200.0 * elementHeight / 2.0, elementWidth / 2, elementHeight / 2);
        if (this.category.getResourceLocation() != null) {
            bus.e();
            bib.z().N().a(this.category.getResourceLocation());
            draw.drawTexture(x + 2, y + 1, 256.0, 256.0, maxY - y - 4, maxY - y - 4);
        }
        final List<String> list = draw.listFormattedStringToWidth(this.category.getTitle(), maxX - x - 64 + 25, 2);
        int posY = list.size() * -5 + 5;
        for (final String line : list) {
            draw.c(LabyModCore.getMinecraft().getFontRenderer(), line, x + maxY - y + 2, y + 7 + posY, this.isMouseOver() ? 16777120 : 14737632);
            posY += 10;
        }
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
        if (this.isMouseOver()) {
            this.callback.clicked(this.category);
        }
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
    
    public interface ClickedCallback
    {
        void clicked(final SettingsCategory p0);
    }
}
