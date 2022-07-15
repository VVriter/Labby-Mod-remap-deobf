//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui;

import net.labymod.main.lang.*;
import net.labymod.settings.elements.*;
import java.util.*;

public class ModuleCategory
{
    private String name;
    private boolean enabled;
    private ControlElement.IconData iconData;
    public CategoryModuleEditorElement rawCategoryElement;
    
    public ModuleCategory(final String name, final boolean enabled) {
        final String key = "modulecategory_" + name;
        this.name = LanguageManager.translate(key);
        if (this.name.equals(key)) {
            this.name = name;
        }
        this.enabled = enabled;
        this.iconData = new ControlElement.IconData(new nf("labymod/textures/settings/guicategory/" + name + ".png"));
    }
    
    public ModuleCategory(final String name, final boolean enabled, final ControlElement.IconData iconData) {
        final String key = "modulecategory_" + name;
        this.name = LanguageManager.translate(key);
        if (this.name.equals(key)) {
            this.name = name;
        }
        this.enabled = enabled;
        this.iconData = iconData;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof ModuleCategory && ((ModuleCategory)obj).getName().equals(this.name);
    }
    
    public void createCategoryElement() {
        this.rawCategoryElement = new CategoryModuleEditorElement(this.name, this.iconData);
        final ArrayList<SettingsElement> subList = new ArrayList<SettingsElement>();
        for (final Module module : Module.getModules()) {
            if (module.getCategory() == null) {
                continue;
            }
            if (!module.getCategory().equals(this)) {
                continue;
            }
            subList.add(module.getBooleanElement());
        }
        this.rawCategoryElement.getSubSettings().addAll(subList);
    }
    
    public CategoryModuleEditorElement getCategoryElement() {
        if (this.rawCategoryElement == null) {
            this.createCategoryElement();
        }
        return this.rawCategoryElement;
    }
    
    public void setCategoryElement(final CategoryModuleEditorElement categoryModuleEditorElement) {
        this.rawCategoryElement = categoryModuleEditorElement;
    }
    
    public void setRawCategoryElement(final CategoryModuleEditorElement rawCategoryElement) {
        this.rawCategoryElement = rawCategoryElement;
    }
}
