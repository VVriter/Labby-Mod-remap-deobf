//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui;

import java.io.*;
import net.labymod.utils.manager.*;
import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import com.google.common.base.*;
import net.labymod.settings.*;
import net.labymod.support.util.*;
import java.lang.reflect.*;
import net.labymod.ingamegui.enums.*;
import java.util.*;
import net.labymod.ingamegui.modules.*;

public class ModuleConfig
{
    private static final File MODULES_FILE;
    private static final File MODULES_PROFILE_FOLDER;
    public static final String SINGLEPLAYER_KEY = "singleplayer";
    private static ModuleConfig config;
    private static ConfigManager<ModuleConfig> configManager;
    public static String loadedProfile;
    private static List<Class<? extends Module>> defaultEnabledModules;
    private static Map<Class<? extends Module>, Class<? extends Module>> defaultListedAfterValues;
    private Map<String, ModuleConfigElement> modules;
    private Map<String, Boolean> moduleCategories;
    private boolean modulesEnabled;
    private boolean gridEnabled;
    private boolean itemSlotGravity;
    private int guiScale;
    private int prefixColor;
    private int bracketsColor;
    private int valuesColor;
    private int formattingBold;
    private int formattingItalic;
    private int formattingUnderline;
    private boolean keyVisible;
    private boolean backgroundVisible;
    private int backgroundColor;
    private int backgroundTransparency;
    private int padding;
    private EnumModuleAlignment defaultAlignment;
    private EnumModuleFormatting defaultFormatting;
    private ItemModule.DurabilityVisibility defaultDurabilityVisibility;
    
    public ModuleConfig() {
        this.modules = new HashMap<String, ModuleConfigElement>();
        this.moduleCategories = new HashMap<String, Boolean>();
        this.modulesEnabled = true;
        this.gridEnabled = false;
        this.itemSlotGravity = true;
        this.guiScale = 100;
        this.prefixColor = DefaultValues.PREFIX_COLOR;
        this.bracketsColor = DefaultValues.BRACKETS_COLOR;
        this.valuesColor = DefaultValues.VALUE_COLOR;
        this.formattingBold = 0;
        this.formattingItalic = 0;
        this.formattingUnderline = 0;
        this.keyVisible = true;
        this.backgroundVisible = false;
        this.backgroundColor = DefaultValues.BACKGROUND_COLOR;
        this.backgroundTransparency = 50;
        this.padding = 0;
        this.defaultAlignment = EnumModuleAlignment.AUTO;
        this.defaultFormatting = DefaultValues.DEFAULT_FORMATTING;
        this.defaultDurabilityVisibility = DefaultValues.DEFAULT_DURABILITY_VISIBILITY;
    }
    
    public static File[] listProfileFiles() {
        return ModuleConfig.MODULES_PROFILE_FOLDER.exists() ? ModuleConfig.MODULES_PROFILE_FOLDER.listFiles() : new File[0];
    }
    
    public static void switchProfile(final String serverIP, final boolean force) {
        final String profile = ModUtils.getProfileNameByIp(serverIP);
        if (Objects.equal((Object)profile, (Object)ModuleConfig.loadedProfile)) {
            return;
        }
        if (ModuleConfig.configManager != null) {
            ModuleConfig.configManager.save();
        }
        loadConfig(profile, force);
    }
    
    public static void deleteProfile(String profile) {
        if (profile != null && profile.equals(LabyModModuleEditorGui.SINGLEPLAYER_PROFILE)) {
            profile = "singleplayer";
        }
        ModuleConfig.configManager.getFile().delete();
        loadConfig(profile, false);
    }
    
    public static File getFile(String profile) {
        if (profile != null && profile.equals(LabyModModuleEditorGui.SINGLEPLAYER_PROFILE)) {
            profile = "singleplayer";
        }
        if (profile != null) {
            return new File(ModuleConfig.MODULES_PROFILE_FOLDER, profile + ".json");
        }
        return ModuleConfig.MODULES_FILE;
    }
    
    public static void loadConfig(String profile, final boolean force) {
        if (profile != null && profile.equals(LabyModModuleEditorGui.SINGLEPLAYER_PROFILE)) {
            profile = "singleplayer";
        }
        File file = ModuleConfig.MODULES_FILE;
        if (profile != null) {
            final File profileDir = ModuleConfig.MODULES_PROFILE_FOLDER;
            if (!profileDir.exists()) {
                if (force) {
                    profileDir.mkdir();
                }
                else {
                    profile = null;
                }
            }
            if (profile != null) {
                final File profileFile = new File(profileDir, profile + ".json");
                if (profileFile.exists() || force) {
                    file = profileFile;
                }
                else {
                    profile = null;
                }
            }
        }
        Debug.log(Debug.EnumDebugMode.CONFIG_MANAGER, "Switched profile to " + ((profile == null) ? "default" : profile));
        LabyModModuleEditorGui.selectedProfile = profile;
        ModuleConfig.loadedProfile = profile;
        ModuleConfig.configManager = new ConfigManager<ModuleConfig>(file, ModuleConfig.class);
        ModuleConfig.config = ModuleConfig.configManager.getSettings();
        Module.getModulesByOverlistedModules().clear();
        for (final Module module : Module.getModules()) {
            loadModule(module);
            if (module.rawBooleanElement != null) {
                module.createSettingElement();
            }
        }
        ModuleCategoryRegistry.getCategories().clear();
        for (final Field moduleCategoryField : ModuleCategoryRegistry.class.getDeclaredFields()) {
            if (ModuleCategory.class.isAssignableFrom(moduleCategoryField.getType())) {
                if (Modifier.isStatic(moduleCategoryField.getModifiers())) {
                    try {
                        final ModuleCategory moduleCategory = (ModuleCategory)moduleCategoryField.get(null);
                        if (moduleCategory.rawCategoryElement != null) {
                            moduleCategory.createCategoryElement();
                        }
                        ModuleCategoryRegistry.getCategories().add(moduleCategory);
                        if (!ModuleConfig.config.getModuleCategories().containsKey(moduleCategory.getName())) {
                            ModuleConfig.config.getModuleCategories().put(moduleCategory.getName(), moduleCategory.isEnabled());
                        }
                        else {
                            moduleCategory.setEnabled((boolean)ModuleConfig.config.getModuleCategories().get(moduleCategory.getName()));
                        }
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (final ModuleCategory moduleCategory2 : ModuleCategoryRegistry.ADDON_CATEGORY_LIST) {
            if (moduleCategory2.rawCategoryElement != null) {
                moduleCategory2.createCategoryElement();
            }
            ModuleCategoryRegistry.getCategories().add(moduleCategory2);
            if (!ModuleConfig.config.getModuleCategories().containsKey(moduleCategory2.getName())) {
                ModuleConfig.config.getModuleCategories().put(moduleCategory2.getName(), moduleCategory2.isEnabled());
            }
            else {
                moduleCategory2.setEnabled((boolean)ModuleConfig.config.getModuleCategories().get(moduleCategory2.getName()));
            }
        }
    }
    
    public static void loadModule(final Module module) {
        if (ModuleConfig.config.getModules().containsKey(module.getName())) {
            loadExistingModule(module, ModuleConfig.config.getModules().get(module.getName()));
            return;
        }
        final boolean contains = ModuleConfig.defaultEnabledModules.contains(module.getClass());
        final ModuleConfigElement moduleConfigElement = new ModuleConfigElement(contains, ModuleConfig.defaultListedAfterValues.containsKey(module.getClass()) ? ModuleConfig.defaultListedAfterValues.get(module.getClass()).getSimpleName() : null);
        ModuleConfig.config.getModules().put(module.getName(), module.setModuleConfigElement(moduleConfigElement));
        loadExistingModule(module, module.getModuleConfigElement());
    }
    
    private static void loadExistingModule(final Module module, final ModuleConfigElement element) {
        final ModuleConfigElement configElement = ModuleConfig.config.getModules().get(module.getName());
        final Set<EnumDisplayType> enabled = new HashSet<EnumDisplayType>();
        for (final EnumDisplayType type : configElement.getEnabled()) {
            boolean isValid = false;
            for (final EnumDisplayType validType : module.getDisplayTypes()) {
                if (validType.equals((Object)type)) {
                    isValid = true;
                }
            }
            if (isValid) {
                enabled.add(type);
            }
        }
        module.setModuleConfigElement(configElement);
        module.setRegions(configElement.getRegions());
        module.setX(configElement.getX());
        module.setY(configElement.getY());
        module.setEnabled((Set)enabled);
        module.setListedAfter(configElement.getListedAfter());
        module.setAttributes((Map)configElement.getAttributes());
        module.init();
        for (final ModuleGui moduleGui : ModuleGui.INSTANCES) {
            module.initModuleGui(moduleGui);
        }
    }
    
    public static ModuleConfig getConfig() {
        return ModuleConfig.config;
    }
    
    public static ConfigManager<ModuleConfig> getConfigManager() {
        return ModuleConfig.configManager;
    }
    
    public Map<String, ModuleConfigElement> getModules() {
        return this.modules;
    }
    
    public boolean isModulesEnabled() {
        return this.modulesEnabled;
    }
    
    public void setModulesEnabled(final boolean modulesEnabled) {
        this.modulesEnabled = modulesEnabled;
    }
    
    public void setModules(final Map<String, ModuleConfigElement> modules) {
        this.modules = modules;
    }
    
    public Map<String, Boolean> getModuleCategories() {
        return this.moduleCategories;
    }
    
    public void setModuleCategories(final Map<String, Boolean> moduleCategories) {
        this.moduleCategories = moduleCategories;
    }
    
    public boolean isGridEnabled() {
        return this.gridEnabled;
    }
    
    public void setGridEnabled(final boolean gridEnabled) {
        this.gridEnabled = gridEnabled;
    }
    
    public boolean isItemSlotGravity() {
        return this.itemSlotGravity;
    }
    
    public void setItemSlotGravity(final boolean itemSlotGravity) {
        this.itemSlotGravity = itemSlotGravity;
    }
    
    public int getGuiScale() {
        return this.guiScale;
    }
    
    public void setGuiScale(final int guiScale) {
        this.guiScale = guiScale;
    }
    
    public int getPrefixColor() {
        return this.prefixColor;
    }
    
    public void setPrefixColor(final int prefixColor) {
        this.prefixColor = prefixColor;
    }
    
    public int getBracketsColor() {
        return this.bracketsColor;
    }
    
    public void setBracketsColor(final int bracketsColor) {
        this.bracketsColor = bracketsColor;
    }
    
    public int getValuesColor() {
        return this.valuesColor;
    }
    
    public void setValuesColor(final int valuesColor) {
        this.valuesColor = valuesColor;
    }
    
    public int getFormattingBold() {
        return this.formattingBold;
    }
    
    public void setFormattingBold(final int formattingBold) {
        this.formattingBold = formattingBold;
    }
    
    public int getFormattingItalic() {
        return this.formattingItalic;
    }
    
    public void setFormattingItalic(final int formattingItalic) {
        this.formattingItalic = formattingItalic;
    }
    
    public int getFormattingUnderline() {
        return this.formattingUnderline;
    }
    
    public void setFormattingUnderline(final int formattingUnderline) {
        this.formattingUnderline = formattingUnderline;
    }
    
    public boolean isKeyVisible() {
        return this.keyVisible;
    }
    
    public void setKeyVisible(final boolean keyVisible) {
        this.keyVisible = keyVisible;
    }
    
    public boolean isBackgroundVisible() {
        return this.backgroundVisible;
    }
    
    public void setBackgroundVisible(final boolean backgroundVisible) {
        this.backgroundVisible = backgroundVisible;
    }
    
    public int getBackgroundColor() {
        return this.backgroundColor;
    }
    
    public void setBackgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public int getBackgroundTransparency() {
        return this.backgroundTransparency;
    }
    
    public void setBackgroundTransparency(final int backgroundTransparency) {
        this.backgroundTransparency = backgroundTransparency;
    }
    
    public int getPadding() {
        return this.padding;
    }
    
    public void setPadding(final int padding) {
        this.padding = padding;
    }
    
    public EnumModuleAlignment getDefaultAlignment() {
        return this.defaultAlignment;
    }
    
    public void setDefaultAlignment(final EnumModuleAlignment defaultAlignment) {
        this.defaultAlignment = defaultAlignment;
    }
    
    public EnumModuleFormatting getDefaultFormatting() {
        return this.defaultFormatting;
    }
    
    public void setDefaultFormatting(final EnumModuleFormatting defaultFormatting) {
        this.defaultFormatting = defaultFormatting;
    }
    
    public ItemModule.DurabilityVisibility getDefaultDurabilityVisibility() {
        return this.defaultDurabilityVisibility;
    }
    
    public void setDefaultDurabilityVisibility(final ItemModule.DurabilityVisibility defaultDurabilityVisibility) {
        this.defaultDurabilityVisibility = defaultDurabilityVisibility;
    }
    
    static {
        MODULES_FILE = new File("LabyMod/", "modules.json");
        MODULES_PROFILE_FOLDER = new File("LabyMod/", "modules_profiles");
        ModuleConfig.defaultEnabledModules = new ArrayList<Class<? extends Module>>() {
            {
                ((ArrayList<Class<FPSModule>>)this).add(FPSModule.class);
                ((ArrayList<Class<CoordinatesModule>>)this).add(CoordinatesModule.class);
                ((ArrayList<Class<FModule>>)this).add(FModule.class);
            }
        };
        ModuleConfig.defaultListedAfterValues = new HashMap<Class<? extends Module>, Class<? extends Module>>() {
            {
                ((HashMap<Class<CoordinatesModule>, Class<FPSModule>>)this).put(CoordinatesModule.class, FPSModule.class);
                ((HashMap<Class<FModule>, Class<CoordinatesModule>>)this).put(FModule.class, CoordinatesModule.class);
            }
        };
    }
}
