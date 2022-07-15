//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import org.lwjgl.input.*;
import net.labymod.utils.manager.*;
import net.labymod.core.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.io.*;

public class GuiSignSearch extends blk
{
    private blk lastScreen;
    private ModTextField fieldSearch;
    private ModTextField fieldBlacklist;
    private CheckBox checkBoxAdvanced;
    private CheckBox checkBoxFilterFullServer;
    private CheckBox checkBoxNightmode;
    
    public GuiSignSearch(final blk lastScreen) {
        this.lastScreen = lastScreen;
    }
    
    public void b() {
        Keyboard.enableRepeatEvents(true);
        this.n.clear();
        this.n.add(new bja(0, this.l / 2 - 100, this.m / 4 + (SignManager.getSignSearchSettings().isUseAdvancedOptions() ? 90 : 50), "Done"));
        (this.fieldSearch = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 100, this.m / 4 + 20, 200, 20)).setBlackBox(false);
        this.fieldSearch.setText(SignManager.getSignSearchSettings().getSearchString());
        this.fieldSearch.setPlaceHolder(LanguageManager.translate("search_on_signs") + "..");
        this.fieldSearch.setFocused(false);
        this.fieldSearch.setMaxStringLength(256);
        (this.fieldBlacklist = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 20, this.m / 4 + 50, 120, 20)).setBlackBox(false);
        this.fieldBlacklist.setText(SignManager.getSignSearchSettings().getBlacklistString());
        this.fieldBlacklist.setPlaceHolder(LanguageManager.translate("blacklist") + "..");
        this.fieldBlacklist.setVisible(SignManager.getSignSearchSettings().isUseAdvancedOptions());
        this.fieldBlacklist.setMaxStringLength(256);
        (this.checkBoxAdvanced = new CheckBox(LanguageManager.translate("button_advanced"), SignManager.getSignSearchSettings().isUseAdvancedOptions() ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, this.l / 2 + 100 + 5, this.m / 4 + 20, 20, 20)).setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                SignManager.getSignSearchSettings().setUseAdvancedOptions(accepted == CheckBox.EnumCheckBoxValue.ENABLED);
                GuiSignSearch.this.b();
            }
        });
        (this.checkBoxFilterFullServer = new CheckBox(LanguageManager.translate("filter_full_servers"), SignManager.getSignSearchSettings().isFilterFullServer() ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, this.l / 2 - 100 + 5, this.m / 4 + 50, 20, 20)).setVisible(SignManager.getSignSearchSettings().isUseAdvancedOptions());
        this.checkBoxFilterFullServer.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                SignManager.getSignSearchSettings().setFilterFullServer(accepted == CheckBox.EnumCheckBoxValue.ENABLED);
                GuiSignSearch.this.b();
            }
        });
        this.checkBoxFilterFullServer.setDescription(LanguageManager.translate("filter_full_servers_description"));
        (this.checkBoxNightmode = new CheckBox(LanguageManager.translate("filter_empty_servers"), SignManager.getSignSearchSettings().isFilterEmptyServer() ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, this.l / 2 - 100 + 20 + 15 + 5, this.m / 4 + 50, 20, 20)).setVisible(SignManager.getSignSearchSettings().isUseAdvancedOptions());
        this.checkBoxNightmode.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                SignManager.getSignSearchSettings().setFilterEmptyServer(accepted == CheckBox.EnumCheckBoxValue.ENABLED);
                GuiSignSearch.this.b();
            }
        });
        this.checkBoxNightmode.setDescription(LanguageManager.translate("filter_empty_servers_description"));
        SignManager.getSignSearchSettings().update();
        super.b();
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        this.fieldSearch.drawTextBox();
        this.fieldBlacklist.drawTextBox();
        this.checkBoxAdvanced.drawCheckbox(mouseX, mouseY);
        this.checkBoxFilterFullServer.drawCheckbox(mouseX, mouseY);
        this.checkBoxNightmode.drawCheckbox(mouseX, mouseY);
        draw.drawCenteredString(LanguageManager.translate("title_sign_search"), this.l / 2, this.m / 4);
        bib.z().N().a(ModTextures.BUTTON_SIGNSEARCH);
        LabyMod.getInstance().getDrawUtils().drawTexture(this.fieldSearch.xPosition - 22, this.fieldSearch.yPosition, 255.0, 255.0, 20.0, 20.0);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 0) {
            bib.z().a(this.lastScreen);
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        if (!this.fieldSearch.isFocused() && !this.fieldBlacklist.isFocused()) {
            this.fieldSearch.setFocused(true);
        }
        if (this.fieldSearch.textboxKeyTyped(typedChar, keyCode)) {
            SignManager.getSignSearchSettings().setSearchString(this.fieldSearch.getText());
        }
        if (this.fieldBlacklist.textboxKeyTyped(typedChar, keyCode)) {
            SignManager.getSignSearchSettings().setBlacklistString(this.fieldBlacklist.getText());
        }
        SignManager.getSignSearchSettings().update();
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.fieldSearch.mouseClicked(mouseX, mouseY, mouseButton);
        this.fieldBlacklist.mouseClicked(mouseX, mouseY, mouseButton);
        this.checkBoxAdvanced.mouseClicked(mouseX, mouseY, mouseButton);
        this.checkBoxFilterFullServer.mouseClicked(mouseX, mouseY, mouseButton);
        this.checkBoxNightmode.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void e() {
        super.e();
        this.fieldSearch.updateCursorCounter();
        this.fieldBlacklist.updateCursorCounter();
    }
}
