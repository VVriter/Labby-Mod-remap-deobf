//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import org.lwjgl.input.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.utils.manager.*;
import java.io.*;
import net.labymod.utils.*;

public class GuiTagsAdd extends blk
{
    private blk lastScreen;
    private String editName;
    private String storedName;
    private String storedTag;
    private String prevName;
    private ModTextField fieldMinecraftName;
    private ModTextField fieldTagName;
    private bja buttonDone;
    private String renderHeadName;
    
    public GuiTagsAdd(final blk lastScreen, final String editName) {
        this.storedName = "";
        this.storedTag = "";
        this.lastScreen = lastScreen;
        this.editName = editName;
        this.prevName = editName;
    }
    
    public void b() {
        super.b();
        Keyboard.enableRepeatEvents(true);
        (this.fieldMinecraftName = new ModTextField(-1, LabyMod.getInstance().getDrawUtils().fontRenderer, this.l / 2 - 100, this.m / 2 - 50, 200, 20)).setMaxStringLength(32);
        (this.fieldTagName = new ModTextField(-1, LabyMod.getInstance().getDrawUtils().fontRenderer, this.l / 2 - 100, this.m / 2 - 5, 200, 20)).setColorBarEnabled(true);
        this.fieldTagName.setMaxStringLength(32);
        this.n.add(this.buttonDone = new bja(0, this.l / 2 + 3, this.m / 2 + 35, 98, 20, LanguageManager.translate("button_add")));
        this.n.add(new bja(1, this.l / 2 - 101, this.m / 2 + 35, 98, 20, LanguageManager.translate("button_cancel")));
        this.buttonDone.l = false;
        if (this.editName != null) {
            this.storedName = this.editName;
            this.renderHeadName = this.editName;
            final String foundTag = TagManager.getConfigManager().getSettings().getTags().get(this.editName);
            if (foundTag != null) {
                this.storedTag = foundTag;
                this.buttonDone.l = true;
            }
            this.editName = null;
        }
        this.fieldMinecraftName.setText(this.storedName);
        this.fieldTagName.setText(this.storedTag);
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 15) {
            if (this.fieldMinecraftName.isFocused()) {
                this.fieldTagName.setFocused(true);
                this.fieldMinecraftName.setFocused(false);
                this.renderHeadName = this.storedName;
            }
            else if (this.fieldTagName.isFocused()) {
                this.fieldMinecraftName.setFocused(true);
                this.fieldTagName.setFocused(false);
            }
        }
        if (this.fieldMinecraftName.textboxKeyTyped(typedChar, keyCode)) {
            this.storedName = this.fieldMinecraftName.getText();
        }
        if (this.fieldTagName.textboxKeyTyped(typedChar, keyCode)) {
            this.storedTag = this.fieldTagName.getText();
        }
        this.buttonDone.l = (!this.storedName.isEmpty() && !this.storedTag.isEmpty());
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        final boolean flag = this.fieldMinecraftName.isFocused();
        this.fieldMinecraftName.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.fieldTagName.mouseClicked(mouseX, mouseY, mouseButton)) {
            this.storedTag = this.fieldTagName.getText();
            if (flag && !this.fieldMinecraftName.isFocused()) {
                this.renderHeadName = this.storedName;
            }
        }
        super.a(mouseX, mouseY, mouseButton);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 0: {
                if (this.prevName != null) {
                    TagManager.getConfigManager().getSettings().getTags().remove(this.prevName);
                }
                TagManager.getConfigManager().getSettings().getTags().put(this.storedName, this.storedTag);
                bib.z().a(this.lastScreen);
                break;
            }
            case 1: {
                bib.z().a(this.lastScreen);
                break;
            }
        }
    }
    
    public void e() {
        super.e();
        this.fieldMinecraftName.updateCursorCounter();
        this.fieldTagName.updateCursorCounter();
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        this.fieldMinecraftName.drawTextBox();
        this.fieldTagName.drawTextBox();
        this.fieldTagName.drawColorBar(mouseX, mouseY);
        if (this.renderHeadName != null && !this.renderHeadName.contains(" ") && this.renderHeadName.length() <= 16) {
            LabyMod.getInstance().getDrawUtils().drawPlayerHead(this.renderHeadName, this.fieldMinecraftName.xPosition - this.fieldMinecraftName.height - 4, this.fieldMinecraftName.yPosition, this.fieldMinecraftName.height);
        }
        LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate("minecraft_name") + ":", this.l / 2 - 100, this.m / 2 - 65);
        LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate("custom_tag_for_player") + ":", this.l / 2 - 100, this.m / 2 - 20);
        final String displayString = this.storedTag.replaceAll("&", ModColor.getCharAsString());
        final int strl = LabyMod.getInstance().getDrawUtils().getStringWidth(ModColor.removeColor(displayString));
        if (strl > 1) {
            a(this.l / 2 - strl / 2 - 2, this.m / 2 - 92, this.l / 2 + strl / 2 + 2, this.m / 2 - 80, Integer.MIN_VALUE);
            LabyMod.getInstance().getDrawUtils().drawCenteredString(displayString, this.l / 2, this.m / 2 - 90);
        }
        super.a(mouseX, mouseY, partialTicks);
    }
}
