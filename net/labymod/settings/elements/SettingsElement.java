//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.settings.*;
import net.labymod.ingamegui.*;
import net.labymod.api.permissions.*;
import java.util.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;

public abstract class SettingsElement
{
    public static final nf buttonTextures;
    public static final nf BUTTON_PRESS_SOUND;
    protected bib mc;
    protected Settings subSettings;
    protected String displayName;
    private String descriptionText;
    protected boolean mouseOver;
    private int sortingId;
    private boolean visible;
    private Module moduleCopyVisible;
    private String configEntryName;
    protected List<Permissions.Permission> permissions;
    
    public SettingsElement(final String displayName, final String description, final String configEntryName) {
        this.mc = bib.z();
        this.subSettings = new Settings();
        this.sortingId = 0;
        this.visible = true;
        this.moduleCopyVisible = null;
        this.permissions = new ArrayList<Permissions.Permission>();
        this.displayName = displayName;
        this.descriptionText = description;
        this.configEntryName = configEntryName;
        this.preInit();
    }
    
    public SettingsElement(final String displayName, final String configEntryName) {
        this.mc = bib.z();
        this.subSettings = new Settings();
        this.sortingId = 0;
        this.visible = true;
        this.moduleCopyVisible = null;
        this.permissions = new ArrayList<Permissions.Permission>();
        this.displayName = displayName;
        this.initTranslation(this.configEntryName = configEntryName);
        this.preInit();
    }
    
    protected SettingsElement initTranslation(final String key) {
        if (key == null) {
            return this;
        }
        final String descriptionKey = "description_" + key.toLowerCase();
        this.descriptionText = LanguageManager.translate(descriptionKey);
        if (this.descriptionText.equals(descriptionKey)) {
            this.descriptionText = null;
        }
        final String titleKey = "setting_" + key.toLowerCase();
        final String displayName = LanguageManager.translate(titleKey);
        if (!displayName.equals(titleKey)) {
            this.displayName = displayName;
        }
        return this;
    }
    
    public SettingsElement bindPermission(final Permissions.Permission... permissions) {
        for (final Permissions.Permission permission : permissions) {
            this.permissions.add(permission);
        }
        return this;
    }
    
    public SettingsElement bindDescription(final String customDescription) {
        this.descriptionText = customDescription;
        return this;
    }
    
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        this.mouseOver = (mouseX > x && mouseX < maxX && mouseY > y && mouseY < maxY);
    }
    
    public abstract void drawDescription(final int p0, final int p1, final int p2);
    
    public abstract void mouseClicked(final int p0, final int p1, final int p2);
    
    public abstract void mouseRelease(final int p0, final int p1, final int p2);
    
    public abstract void mouseClickMove(final int p0, final int p1, final int p2);
    
    public abstract void keyTyped(final char p0, final int p1);
    
    public abstract void unfocus(final int p0, final int p1, final int p2);
    
    public abstract int getEntryHeight();
    
    public void preInit() {
    }
    
    public void init() {
    }
    
    public void updateScreen() {
    }
    
    public boolean isVisible() {
        return (this.moduleCopyVisible == null) ? this.visible : this.moduleCopyVisible.getModuleConfigElement().isUsingExtendedSettings();
    }
    
    public bib getMc() {
        return this.mc;
    }
    
    public Settings getSubSettings() {
        return this.subSettings;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getDescriptionText() {
        return this.descriptionText;
    }
    
    public boolean isMouseOver() {
        return this.mouseOver;
    }
    
    public int getSortingId() {
        return this.sortingId;
    }
    
    public Module getModuleCopyVisible() {
        return this.moduleCopyVisible;
    }
    
    public String getConfigEntryName() {
        return this.configEntryName;
    }
    
    public List<Permissions.Permission> getPermissions() {
        return this.permissions;
    }
    
    public void setMc(final bib mc) {
        this.mc = mc;
    }
    
    public void setSubSettings(final Settings subSettings) {
        this.subSettings = subSettings;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public void setDescriptionText(final String descriptionText) {
        this.descriptionText = descriptionText;
    }
    
    public void setMouseOver(final boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    
    public void setSortingId(final int sortingId) {
        this.sortingId = sortingId;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public void setModuleCopyVisible(final Module moduleCopyVisible) {
        this.moduleCopyVisible = moduleCopyVisible;
    }
    
    public void setConfigEntryName(final String configEntryName) {
        this.configEntryName = configEntryName;
    }
    
    public void setPermissions(final List<Permissions.Permission> permissions) {
        this.permissions = permissions;
    }
    
    static {
        buttonTextures = new nf("textures/gui/widgets.png");
        BUTTON_PRESS_SOUND = new nf(Source.ABOUT_MC_VERSION.startsWith("1.8") ? "gui.button.press" : "ui.button.click");
    }
}
