//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.main.lang.*;
import java.util.*;
import net.labymod.api.permissions.*;
import net.labymod.settings.elements.*;

public class SettingsCategory
{
    private String title;
    private Settings settings;
    private ArrayList<SettingsCategory> subList;
    private bja guiButton;
    private SettingsCategory parentCategory;
    private nf resourceLocation;
    private int iconSize;
    
    public SettingsCategory(final String title) {
        this(title, null);
    }
    
    public SettingsCategory(final String title, final SettingsCategory parentCategory) {
        this.settings = new Settings();
        this.iconSize = 10;
        this.title = LanguageManager.translate(title);
        this.subList = new ArrayList<SettingsCategory>();
        this.parentCategory = parentCategory;
    }
    
    public SettingsCategory setParent(final SettingsCategory parentCategory) {
        this.parentCategory = parentCategory;
        return this;
    }
    
    public Settings getSettings() {
        return this.settings;
    }
    
    public SettingsCategory setSettings(final Settings openSettings) {
        this.settings = openSettings;
        return this;
    }
    
    public SettingsCategory setSettings(final ArrayList<SettingsElement> elements) {
        this.settings = new Settings((SettingsElement[])elements.toArray(new SettingsElement[elements.size()]));
        return this;
    }
    
    public SettingsCategory addSetting(final SettingsElement settingsElement) {
        this.settings.add(settingsElement);
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public ArrayList<SettingsCategory> getSubList() {
        return this.subList;
    }
    
    public void setGuiButton(final bja guiButton) {
        this.guiButton = guiButton;
    }
    
    public bja getGuiButton() {
        return this.guiButton;
    }
    
    public SettingsCategory getParentCategory() {
        return this.parentCategory;
    }
    
    public boolean isCategoryElementsOnly() {
        for (final SettingsElement element : this.settings.getElements()) {
            if (!(element instanceof CategorySettingsElement)) {
                return false;
            }
        }
        return true;
    }
    
    public SettingsCategory setIcon(final String imageName) {
        this.resourceLocation = new nf("labymod/textures/settings/category/" + imageName);
        return this;
    }
    
    public SettingsCategory setIcon(final nf resourceLocation) {
        this.resourceLocation = resourceLocation;
        return this;
    }
    
    public SettingsCategory setIconSize(final int iconSize) {
        this.iconSize = iconSize;
        return this;
    }
    
    public void bindPermissionToAll(final Permissions.Permission... permission) {
        for (final SettingsElement element : this.settings.getElements()) {
            element.bindPermission(permission);
        }
    }
    
    public void bindCustomBooleanToAll(final String... args) {
        for (final SettingsElement element : this.settings.getElements()) {
            if (element instanceof BooleanElement) {
                ((BooleanElement)element).custom(args);
            }
        }
    }
    
    public nf getResourceLocation() {
        return this.resourceLocation;
    }
    
    public int getIconSize() {
        return this.iconSize;
    }
}
