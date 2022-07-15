//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.gui.*;
import net.labymod.gui.elements.*;
import net.labymod.gui.layout.*;
import java.util.*;
import net.labymod.core.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;

public class WinSearchField extends WindowElement<GuiFriendsLayout>
{
    private ModTextField fieldSearch;
    private SmallDropDownMenu buttonSortOptions;
    
    public WinSearchField(final GuiFriendsLayout chatLayout) {
        super((WindowLayout)chatLayout);
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        final int paddingHeight = 5;
        final int paddingWidth = 0;
        final int dragLineWidth = 2;
        final int spaceBetween = 5;
        final int buttonWidth = 30;
        (this.fieldSearch = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), left + buttonWidth + paddingWidth * 2 + spaceBetween, top + paddingHeight, right - left - (buttonWidth + paddingWidth) - paddingWidth * 2 - dragLineWidth - spaceBetween, bottom - top - paddingHeight * 2)).setBlackBox(false);
        (this.buttonSortOptions = new SmallDropDownMenu(left + paddingWidth, top + (bottom - top - 20) / 2, buttonWidth, 20)).addDropDownEntry(LanguageManager.translate("chat_sort_all"));
        this.buttonSortOptions.addDropDownEntry(LanguageManager.translate("online"));
        this.buttonSortOptions.addDropDownEntry(LanguageManager.translate("chat_sort_latest"));
        this.buttonSortOptions.setSelectedOptionIndex(LabyMod.getSettings().friendSortType);
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        this.fieldSearch.drawTextBox();
        this.buttonSortOptions.renderButton(mouseX, mouseY);
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.fieldSearch.mouseClicked(mouseX, mouseY, mouseButton);
        final int entryCode = this.buttonSortOptions.onClick(mouseX, mouseY);
        if (entryCode >= 0) {
            LabyMod.getSettings().friendSortType = entryCode;
            LabyMod.getInstance().getLabyConnect().sortFriendList(entryCode);
            return true;
        }
        return false;
    }
    
    public void actionPerformed(final bja button) {
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        this.fieldSearch.textboxKeyTyped(typedChar, keyCode);
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public void updateScreen() {
        this.fieldSearch.updateCursorCounter();
    }
    
    public ModTextField getFieldSearch() {
        return this.fieldSearch;
    }
    
    public SmallDropDownMenu getButtonSortOptions() {
        return this.buttonSortOptions;
    }
}
