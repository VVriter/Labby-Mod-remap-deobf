//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.main.*;
import net.labymod.ingamegui.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.ingamegui.enums.*;

public class CategoryModuleEditorElement extends SettingsElement
{
    private ControlElement.IconData iconData;
    
    public CategoryModuleEditorElement(final String displayName, final ControlElement.IconData iconData) {
        super(displayName, null);
        this.iconData = iconData;
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final int absoluteY = y + 7;
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawRectangle(x, y, maxX, maxY, ModColor.toRGB(200, 200, 200, this.mouseOver ? 50 : 30));
        final int imageSize = maxY - y;
        if (this.iconData.hasTextureIcon()) {
            bib.z().N().a(this.iconData.getTextureIcon());
            LabyMod.getInstance().getDrawUtils().drawTexture(x + 2, y + 2, 256.0, 256.0, 18.0, 18.0);
        }
        else if (this.iconData.hasMaterialIcon()) {
            LabyMod.getInstance().getDrawUtils().drawItem(this.iconData.getMaterialIcon().createItemStack(), x + 3, y + 3, null);
        }
        draw.drawString(this.getDisplayName(), x + imageSize + 5, absoluteY);
        int totalSubCount = 0;
        int enabledCount = 0;
        for (final SettingsElement element : this.getSubSettings().getElements()) {
            boolean isCurrentTab = false;
            if (element instanceof ControlElement) {
                final Module module = ((ControlElement)element).getModule();
                if (module != null) {
                    for (final EnumDisplayType type : module.getDisplayTypes()) {
                        if (type == Module.getLastDrawnDisplayType()) {
                            isCurrentTab = true;
                        }
                    }
                }
            }
            if (element instanceof BooleanElement && ((BooleanElement)element).getCurrentValue()) {
                ++enabledCount;
            }
            if (isCurrentTab) {
                ++totalSubCount;
            }
        }
        draw.drawRightString(enabledCount + ModColor.cl("7") + "/" + ModColor.cl("f") + totalSubCount, maxX - 5, absoluteY);
    }
    
    public ControlElement.IconData getIconData() {
        return this.iconData;
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
