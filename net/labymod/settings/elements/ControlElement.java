//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.ingamegui.*;
import net.labymod.api.permissions.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.utils.manager.*;
import java.util.*;
import net.labymod.settings.*;
import net.labymod.core.*;
import net.labymod.utils.*;

public class ControlElement extends SettingsElement
{
    protected IconData iconData;
    private bja buttonAdvanced;
    private Module module;
    private boolean selected;
    private boolean hoverable;
    private boolean hideSubList;
    private boolean settingEnabled;
    private int lastMaxX;
    private Consumer<Boolean> advancedButtonCallback;
    private boolean blocked;
    
    public ControlElement(final String elementName, final String configEntryName, final IconData iconData) {
        super(elementName, configEntryName);
        this.blocked = false;
        this.iconData = iconData;
        if (this.iconData != null && this.iconData.isUseConfigName()) {
            this.iconData.apply((configEntryName == null) ? elementName : configEntryName);
        }
        this.createButton();
    }
    
    public ControlElement(final Module module, final IconData iconData, final String displayName) {
        super(displayName, null);
        this.blocked = false;
        this.iconData = iconData;
        this.module = module;
        this.createButton();
    }
    
    public ControlElement(final String displayName, final IconData iconData) {
        super(displayName, null);
        this.blocked = false;
        this.iconData = iconData;
        this.createButton();
    }
    
    private void createButton() {
        this.buttonAdvanced = new bja(-2, 0, 0, 23, 20, "");
    }
    
    public bja getButtonAdvanced() {
        return this.buttonAdvanced;
    }
    
    public void setAdvancedButtonCallback(final Consumer<Boolean> callback) {
        this.advancedButtonCallback = callback;
    }
    
    @Override
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        this.lastMaxX = maxX;
        if (this.displayName != null) {
            LabyMod.getInstance().getDrawUtils().drawRectangle(x, y, maxX, maxY, ModColor.toRGB(80, 80, 80, this.selected ? 130 : ((this.hoverable && this.mouseOver) ? 80 : 60)));
            final int iconWidth = (this.iconData != null) ? 25 : 2;
            if (this.iconData != null) {
                if (this.iconData.hasTextureIcon()) {
                    bib.z().N().a(this.iconData.getTextureIcon());
                    LabyMod.getInstance().getDrawUtils().drawTexture(x + 3, y + 3, 256.0, 256.0, 16.0, 16.0);
                }
                else if (this.iconData.hasMaterialIcon()) {
                    LabyMod.getInstance().getDrawUtils().drawItem(this.iconData.getMaterialIcon().createItemStack(), x + 3, y + 2, null);
                }
            }
            final List<String> list = LabyMod.getInstance().getDrawUtils().listFormattedStringToWidth(this.getDisplayName().isEmpty() ? (ModColor.cl("4") + "Unknown") : this.getDisplayName(), maxX - (x + iconWidth) - this.getObjectWidth() - 5 - (this.hasSubList() ? iconWidth : 0));
            int listY = y + 7 - (((list.size() > 2) ? 2 : list.size()) - 1) * 5;
            int i = 0;
            for (final String line : list) {
                LabyMod.getInstance().getDrawUtils().drawString(line, x + iconWidth, listY);
                listY += 10;
                if (++i > 1) {
                    break;
                }
            }
            this.renderAdvancedButton(x, y, maxX - this.getObjectWidth(), maxY, this.mouseOver, mouseX, mouseY);
            this.hideSubList = false;
            boolean blocked = this.blocked;
            if (!this.permissions.isEmpty()) {
                boolean defaultDisabled = false;
                boolean isEnabledByServer = true;
                String allowedPermissions = "";
                for (final Permissions.Permission permission : this.permissions) {
                    if (!permission.isDefaultEnabled()) {
                        defaultDisabled = true;
                    }
                    if (!allowedPermissions.isEmpty()) {
                        allowedPermissions = allowedPermissions + ModColor.cl("7") + ", ";
                    }
                    allowedPermissions = allowedPermissions + ModColor.cl("e") + permission.getDisplayName();
                    if (!Permissions.isAllowed(permission)) {
                        isEnabledByServer = false;
                    }
                }
                if (defaultDisabled || !isEnabledByServer) {
                    final boolean hover = mouseX > x - 13 && mouseX < x - 13 + 7 && mouseY > y + 3 && mouseY < y + 3 + 16;
                    final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
                    if (isEnabledByServer) {
                        bib.z().N().a(ModTextures.BUTTON_CHECKBOX);
                        draw.drawTexture(x - 13, y + 7, 0.0, 0.0, 255.0, 255.0, 10.0, 10.0);
                    }
                    else {
                        bib.z().N().a(ModTextures.BUTTON_EXCLAMATION);
                        draw.drawTexture(x - 13, y + 3, hover ? 127.0 : 0.0, 0.0, 127.0, 255.0, 7.0, 16.0);
                        if (!bib.z().E() && LabyMod.getInstance().isInGame()) {
                            blocked = true;
                        }
                    }
                    if (hover) {
                        final String text = LanguageManager.translate("permission_information_" + (isEnabledByServer ? "enabled" : "disabled")) + (isEnabledByServer ? "" : allowedPermissions);
                        TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, (String[])draw.listFormattedStringToWidth(text, draw.getWidth() / 3).toArray());
                    }
                }
            }
            if (blocked) {
                LabyMod.getInstance().getDrawUtils().drawRectangle(x, y, maxX, maxY, ModColor.toRGB(0, 0, 0, 200));
                LabyMod.getInstance().getDrawUtils().drawString(ModColor.RED + "\u2716 " + LabyMod.getMessage("not_allowed_here", new Object[0]) + " \u2716", x + 5, y + (maxY - y) / 2 - 4);
            }
        }
    }
    
    private void renderAdvancedButton(final int x, final int y, final int maxX, final int maxY, final boolean mouseOver, final int mouseX, final int mouseY) {
        if (!this.hasSubList()) {
            return;
        }
        if (this.buttonAdvanced == null) {
            return;
        }
        if (this.hideSubList) {
            return;
        }
        boolean enabled;
        if (this.isModule()) {
            enabled = this.module.isEnabled(LabyModModuleEditorGui.getSelectedTab());
        }
        else {
            enabled = this.settingEnabled;
        }
        LabyModCore.getMinecraft().setButtonXPosition(this.buttonAdvanced, maxX - this.getSubListButtonWidth() - 2);
        LabyModCore.getMinecraft().setButtonYPosition(this.buttonAdvanced, y + 1);
        this.buttonAdvanced.l = enabled;
        LabyModCore.getMinecraft().drawButton(this.buttonAdvanced, mouseX, mouseY);
        this.mc.N().a(ModTextures.BUTTON_ADVANCED);
        bus.m();
        bus.c(1.0f, 1.0f, 1.0f, enabled ? 1.0f : 0.2f);
        LabyMod.getInstance().getDrawUtils().drawTexture(LabyModCore.getMinecraft().getXPosition(this.buttonAdvanced) + 4, LabyModCore.getMinecraft().getYPosition(this.buttonAdvanced) + 3, 0.0, 0.0, 256.0, 256.0, 14.0, 14.0, 2.0f);
    }
    
    public int getObjectWidth() {
        return this.isModule() ? 37 : 50;
    }
    
    public int getSubListButtonWidth() {
        return this.hasSubList() ? 23 : 0;
    }
    
    @Override
    public void drawDescription(final int x, final int y, final int screenWidth) {
        final String description = this.getDescriptionText();
        if (description == null) {
            return;
        }
        if (this.buttonAdvanced != null && this.buttonAdvanced.a()) {
            return;
        }
        if (x > this.lastMaxX - this.getObjectWidth() - 2) {
            return;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final List<String> list = draw.listFormattedStringToWidth(description, screenWidth / 3);
        TooltipHelper.getHelper().pointTooltip(x, y, 500L, (String[])list.toArray());
    }
    
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public int getEntryHeight() {
        return 23;
    }
    
    public boolean isModule() {
        return this.module != null;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public boolean hasSubList() {
        return this.advancedButtonCallback != null || (!this.subSettings.getElements().isEmpty() && !this.hideSubList);
    }
    
    public ControlElement setSelected(final boolean selected) {
        this.selected = selected;
        return this;
    }
    
    public ControlElement setHoverable(final boolean hoverable) {
        this.hoverable = hoverable;
        return this;
    }
    
    public ControlElement hideSubListButton() {
        this.hideSubList = true;
        return this;
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
    
    public IconData getIconData() {
        return this.iconData;
    }
    
    public void setSettingEnabled(final boolean settingEnabled) {
        this.settingEnabled = settingEnabled;
    }
    
    public Consumer<Boolean> getAdvancedButtonCallback() {
        return this.advancedButtonCallback;
    }
    
    public void setBlocked(final boolean blocked) {
        this.blocked = blocked;
    }
    
    public boolean isBlocked() {
        return this.blocked;
    }
    
    public static class IconData
    {
        private boolean useConfigName;
        private Material materialIcon;
        private nf textureIcon;
        
        public IconData(final Material materialIcon) {
            this.useConfigName = false;
            this.materialIcon = materialIcon;
        }
        
        public IconData(final nf textureIcon) {
            this.useConfigName = false;
            this.textureIcon = textureIcon;
        }
        
        public IconData(final String resourceLocationPath) {
            this.useConfigName = false;
            this.textureIcon = new nf(resourceLocationPath);
        }
        
        public IconData() {
            this.useConfigName = false;
            this.useConfigName = true;
        }
        
        public Material getMaterialIcon() {
            return this.materialIcon;
        }
        
        public nf getTextureIcon() {
            return this.textureIcon;
        }
        
        public boolean hasTextureIcon() {
            return this.textureIcon != null;
        }
        
        public boolean hasMaterialIcon() {
            return this.materialIcon != null;
        }
        
        public boolean isUseConfigName() {
            return this.useConfigName;
        }
        
        public void apply(final String configEntryName) {
            this.textureIcon = new nf("labymod/textures/settings/settings/" + configEntryName.toLowerCase() + ".png");
        }
    }
}
