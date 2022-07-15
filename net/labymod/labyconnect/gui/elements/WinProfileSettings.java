//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.gui.*;
import net.labymod.gui.elements.*;
import net.labymod.gui.layout.*;
import net.labymod.settings.elements.*;
import net.labymod.main.*;
import java.util.*;

public class WinProfileSettings extends WindowElement<GuiFriendsLayout>
{
    private Scrollbar scrollbar;
    
    public WinProfileSettings(final GuiFriendsLayout chatLayout) {
        super((WindowLayout)chatLayout);
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        (this.scrollbar = new Scrollbar(0)).init();
        this.scrollbar.setSpeed(15);
        this.scrollbar.setPosition(this.right - 4, this.top + 2, this.right, this.bottom - 2);
        this.updateGuiElements();
    }
    
    private void updateGuiElements() {
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        final List<SettingsElement> allElements = DefinedSettings.getChatSetingsCategory().getSettings().getElements();
        double totalEntryHeight = 0.0;
        for (int zLevel = 0; zLevel < 2; ++zLevel) {
            double posY = this.top + 3 + this.scrollbar.getScrollY();
            final int maxX = this.scrollbar.isHidden() ? (this.right - 1) : (this.right - 6);
            totalEntryHeight = 0.0;
            for (final SettingsElement element : allElements) {
                if (((!(element instanceof DropDownElement) || element instanceof ColorPickerCheckBoxBulkElement) && zLevel == 0) || ((element instanceof DropDownElement || element instanceof ColorPickerCheckBoxBulkElement) && zLevel == 1)) {
                    if (element instanceof DropDownElement) {
                        ((DropDownElement)element).getDropDownMenu().setMaxY(this.bottom);
                    }
                    element.draw(this.left + 1, (int)posY, maxX, (int)(posY + element.getEntryHeight()), mouseX, mouseY);
                }
                posY += element.getEntryHeight() + 1;
                totalEntryHeight += element.getEntryHeight() + 1;
                if (zLevel == 1 && element.isMouseOver() && mouseY > this.top && mouseY < this.bottom) {
                    element.drawDescription(mouseX, mouseY, LabyMod.getInstance().getDrawUtils().getWidth());
                }
            }
        }
        this.scrollbar.setEntryHeight(totalEntryHeight / allElements.size());
        this.scrollbar.update(allElements.size());
        this.scrollbar.draw();
    }
    
    public void actionPerformed(final bja button) {
    }
    
    public void handleMouseInput() {
        if (this.isMouseOver()) {
            this.scrollbar.mouseInput();
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final List<SettingsElement> allElements = DefinedSettings.getChatSetingsCategory().getSettings().getElements();
        for (final SettingsElement element : allElements) {
            if (element instanceof DropDownElement && ((DropDownElement)element).onClickDropDown(mouseX, mouseY, mouseButton)) {
                return false;
            }
        }
        for (final SettingsElement element : allElements) {
            element.unfocus(mouseX, mouseY, mouseButton);
            if (element.isMouseOver()) {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        return false;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        for (final SettingsElement settingElement : DefinedSettings.getChatSetingsCategory().getSettings().getElements()) {
            settingElement.keyTyped(typedChar, keyCode);
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public boolean isScrolledToTop() {
        return this.scrollbar.getScrollY() == 0.0;
    }
    
    public void updateScreen() {
    }
}
