//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui;

import java.util.*;

public class ModuleCategoryRegistry
{
    private static List<ModuleCategory> categories;
    public static ModuleCategory CATEGORY_INFO;
    public static ModuleCategory CATEGORY_ITEMS;
    public static ModuleCategory CATEGORY_EXTERNAL_SERVICES;
    public static ModuleCategory CATEGORY_OTHER;
    public static List<ModuleCategory> ADDON_CATEGORY_LIST;
    
    public static void loadCategory(final ModuleCategory moduleCategory) {
        ModuleCategoryRegistry.categories.add(moduleCategory);
        if (!ModuleConfig.getConfig().getModuleCategories().containsKey(moduleCategory.getName())) {
            ModuleConfig.getConfig().getModuleCategories().put(moduleCategory.getName(), moduleCategory.isEnabled());
            return;
        }
        moduleCategory.setEnabled((boolean)ModuleConfig.getConfig().getModuleCategories().get(moduleCategory.getName()));
        ModuleCategoryRegistry.ADDON_CATEGORY_LIST.add(moduleCategory);
    }
    
    public static List<ModuleCategory> getCategories() {
        return ModuleCategoryRegistry.categories;
    }
    
    static {
        ModuleCategoryRegistry.categories = new ArrayList<ModuleCategory>();
        ModuleCategoryRegistry.CATEGORY_INFO = new ModuleCategory("info", true);
        ModuleCategoryRegistry.CATEGORY_ITEMS = new ModuleCategory("items", true);
        ModuleCategoryRegistry.CATEGORY_EXTERNAL_SERVICES = new ModuleCategory("external_service", true);
        ModuleCategoryRegistry.CATEGORY_OTHER = new ModuleCategory("other", true);
        ModuleCategoryRegistry.ADDON_CATEGORY_LIST = new ArrayList<ModuleCategory>();
    }
}
