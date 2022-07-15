//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui;

import net.minecraftforge.common.*;
import net.labymod.core.*;
import net.labymod.gui.elements.*;
import net.labymod.ingamegui.moduletypes.*;
import net.labymod.gui.*;
import net.labymod.api.permissions.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.settings.elements.*;
import net.labymod.settings.*;
import net.labymod.ingamegui.enums.*;
import java.util.*;
import com.google.common.reflect.*;
import net.labymod.ingamegui.modules.*;
import net.labymod.ingamegui.modules.item.*;
import com.google.common.collect.*;

public abstract class Module
{
    private static int lastRegisteredModuleId;
    private static double lastLeft;
    private static double lastRight;
    private static double lastTop;
    private static double lastBottom;
    private static double prevWidth;
    private static double prevHeight;
    private static ModuleGui currentModuleGui;
    private static EnumDisplayType lastDrawnDisplayType;
    private static List<Module> moduleList;
    private static List<Module> registerQueue;
    private static Map<String, Module> modulesByOverlistedModules;
    public static final bib mc;
    public static long lastTablistRendered;
    private ModuleConfigElement moduleConfigElement;
    private Set<EnumDisplayType> enabled;
    private EnumModuleRegion[] regions;
    private double[] x;
    private double[] y;
    private double lastX;
    private double lastY;
    private boolean lastCenter;
    private boolean lastRightBound;
    private String listedAfter;
    private Map<String, String> attributes;
    private int id;
    private String moduleName;
    protected boolean backgroundVisible;
    protected int backgroundColor;
    protected int backgroundTransparency;
    protected int padding;
    public BooleanElement rawBooleanElement;
    
    public Module() {
        this.regions = new EnumModuleRegion[] { EnumModuleRegion.TOP_LEFT, EnumModuleRegion.TOP_LEFT };
        this.x = new double[] { 0.0, 0.0 };
        this.y = new double[] { 0.0, 0.0 };
        this.moduleName = null;
        this.backgroundVisible = false;
        this.backgroundColor = Integer.MIN_VALUE;
        this.backgroundTransparency = 50;
        this.padding = 0;
    }
    
    public static void registerModule(final Module module, final boolean loadConfigValues) {
        module.setId(++Module.lastRegisteredModuleId);
        MinecraftForge.EVENT_BUS.register(module);
        Module.registerQueue.add(module);
        if (LabyModCore.getMinecraft().getWorld() == null) {
            handleRegisterQueue();
        }
        if (loadConfigValues) {
            ModuleConfig.loadModule(module);
        }
    }
    
    private static void handleRegisterQueue() {
        if (!Module.registerQueue.isEmpty()) {
            Module.moduleList.addAll(Module.registerQueue);
            Module.registerQueue.clear();
        }
    }
    
    public static void draw(double left, double top, double right, double bottom, final EnumDisplayType displayType, final boolean scaling) {
        bus.G();
        bus.b(0.0, 0.0, (Module.lastTablistRendered + 20L >= System.currentTimeMillis()) ? -0.1 : 1.0);
        if (scaling) {
            final double scale = 2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0;
            if (scale != 1.0) {
                bus.a(1.0 / scale, 1.0 / scale, 1.0);
                left *= scale;
                right *= scale;
                top *= scale;
                bottom *= scale;
            }
        }
        Module.lastDrawnDisplayType = displayType;
        Module.lastLeft = left;
        Module.lastRight = right;
        Module.lastTop = top;
        Module.lastBottom = bottom;
        final double screenCenter = getLastLeft() + (getLastRight() - getLastLeft()) / 2.0;
        checkResizeAllModules();
        if (!ModuleConfig.getConfig().isModulesEnabled()) {
            bus.H();
            return;
        }
        handleRegisterQueue();
        final boolean itemSlotGravity = ModuleConfig.getConfig().isItemSlotGravity() && (getCurrentModuleGui() == null || !getCurrentModuleGui().isShowAllModules());
        HashSet<Byte> takenItemSlots = null;
        if (itemSlotGravity) {
            Collections.sort(Module.moduleList, new Comparator<Module>() {
                @Override
                public int compare(final Module o1, final Module o2) {
                    return (o1 instanceof ItemModule && o2 instanceof ItemModule) ? Integer.valueOf(((ItemModule)o2).getItemSlot().ordinal()).compareTo(((ItemModule)o1).getItemSlot().ordinal()) : Integer.valueOf(o1.getId()).compareTo(o2.getId());
                }
            });
            takenItemSlots = new HashSet<Byte>(EnumItemSlot.values().length);
            for (final Module module : Module.moduleList) {
                if (!(module instanceof ItemModule)) {
                    continue;
                }
                if (module.getListedAfter() != null && displayType != EnumDisplayType.ESCAPE) {
                    continue;
                }
                final boolean isShown = (module.isShown() || (getCurrentModuleGui() != null && getCurrentModuleGui().isShowAllModules())) && module.getCategory().isEnabled();
                if (!module.isEnabled(displayType) || !isShown) {
                    continue;
                }
                final EnumItemSlot itemSlot = ((ItemModule)module).getItemSlot();
                if (itemSlot == null || itemSlot == EnumItemSlot.NONE) {
                    continue;
                }
                takenItemSlots.add((byte)itemSlot.ordinal());
            }
        }
        for (final Module module : Module.moduleList) {
            if (module.getListedAfter() != null && displayType != EnumDisplayType.ESCAPE) {
                continue;
            }
            boolean isShown = (module.isShown() || (getCurrentModuleGui() != null && getCurrentModuleGui().isShowAllModules())) && module.getCategory().isEnabled();
            if (bib.z().m != null && bib.z().m instanceof ColorPicker.AdvancedColorSelectorGui) {
                isShown = true;
            }
            double x = module.getRegion(displayType.ordinal()).getMinecraftX(module.getX(displayType.ordinal()), left, right);
            double y = module.getRegion(displayType.ordinal()).getMinecraftY(module.getY(displayType.ordinal()), top, bottom);
            if (x > getLastRight()) {
                x = getLastRight();
            }
            if (y < getLastTop()) {
                y = getLastTop();
            }
            final boolean rightBound = module.isRightBound(displayType);
            boolean inItemSlot = false;
            boolean inScoreboardSlot = false;
            if (module.isEnabled(displayType) && isShown) {
                final double width = module.getWidth();
                double moduleX = Math.max(getLastLeft(), Math.min(rightBound ? (x - width) : x, getLastRight()));
                boolean isItemModule = false;
                EnumItemSlot itemSlot2 = null;
                int scoreboardSlot = -1;
                if (isItemModule = (module instanceof ItemModule)) {
                    itemSlot2 = ((ItemModule)module).getItemSlot();
                    if (itemSlot2 != null && itemSlot2 != EnumItemSlot.NONE) {
                        if (itemSlotGravity) {
                            final EnumItemSlot prevSlot = itemSlot2;
                            final EnumItemSlot[] alternativeSlots = itemSlot2.getAlternativeSlots();
                            if (alternativeSlots != null) {
                                for (final EnumItemSlot alternativeSlot : alternativeSlots) {
                                    if (!takenItemSlots.contains((byte)alternativeSlot.ordinal())) {
                                        itemSlot2 = alternativeSlot;
                                    }
                                }
                            }
                            if (itemSlot2 != prevSlot) {
                                takenItemSlots.remove((byte)prevSlot.ordinal());
                            }
                            takenItemSlots.add((byte)itemSlot2.ordinal());
                        }
                        moduleX = itemSlot2.getX(getLastRight() - getLastLeft()) + getLastLeft();
                        y = itemSlot2.getY(getLastBottom() - getLastTop()) + getLastTop();
                        inItemSlot = true;
                    }
                    else {
                        itemSlot2 = null;
                    }
                }
                if (module instanceof ScoreboardModule) {
                    scoreboardSlot = ((ScoreboardModule)module).getSlot();
                    if (scoreboardSlot != -1) {
                        inScoreboardSlot = true;
                    }
                }
                boolean inCenter = Math.abs(screenCenter - (moduleX + width / 2.0)) < 3.0;
                if (!(module instanceof ResizeableModule) && !inCenter && module.isLastCenter() && (getCurrentModuleGui() == null || !module.equals(getCurrentModuleGui().getDraggingModule()))) {
                    inCenter = true;
                    moduleX = screenCenter - width / 2.0;
                    module.setX(displayType.ordinal(), module.getRegion(displayType.ordinal()).getOffsetX(moduleX, getLastLeft(), getLastRight()));
                    module.getModuleConfigElement().setX(displayType.ordinal(), module.getX(displayType.ordinal()));
                    ModuleConfig.getConfigManager().save();
                }
                module.setLastX(moduleX);
                module.setLastY(y);
                module.setLastCenter(inCenter);
                module.setLastRightBound(rightBound);
                double tX = module.getLastX();
                double tY = y;
                double tRight = rightBound ? (moduleX + width) : -1.0;
                bus.G();
                if (inItemSlot) {
                    final double scale2 = scaling ? (2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0) : 1.0;
                    bus.a(1.0 * scale2, 1.0 * scale2, 1.0);
                    tX = itemSlot2.getX(getLastRight() / scale2 - getLastLeft() / scale2) + getLastLeft() / scale2;
                    tY = itemSlot2.getY(getLastBottom() / scale2 - getLastTop() / scale2) + getLastTop() / scale2;
                }
                if (inScoreboardSlot) {
                    switch (scoreboardSlot) {
                        case 0: {
                            tX = getLastRight() - module.getWidth() - 1.0;
                            tY = getLastTop() + (getLastBottom() - getLastTop()) / 2.0 - module.getHeight() / 2.0;
                            break;
                        }
                        case 1: {
                            tX = getLastLeft() + 1.0;
                            tY = getLastTop() + (getLastBottom() - getLastTop()) / 2.0 - module.getHeight() / 2.0;
                            break;
                        }
                    }
                }
                if (displayType == EnumDisplayType.ESCAPE && tY + module.getHeight() > Module.lastBottom) {
                    tY += Module.lastBottom - (tY + module.getHeight());
                    module.setY(displayType.ordinal(), module.getRegion(displayType.ordinal()).getOffsetY(tY, getLastTop(), getLastBottom()));
                }
                bus.G();
                if (module.supportsRescale()) {
                    module.applyScale(true);
                    tX = module.scaleModuleSize(tX, true);
                    tY = module.scaleModuleSize(tY, true);
                    if (rightBound) {
                        tRight = module.scaleModuleSize(tRight, true);
                    }
                }
                if (!isItemModule) {
                    module.draw(tX, tY, tRight);
                }
                else {
                    ((ItemModule)module).draw(tX, tY, tRight, itemSlot2);
                }
                if (inItemSlot || inScoreboardSlot) {
                    final double scale2 = scaling ? (2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0) : 1.0;
                    bus.a(1.0 / scale2, 1.0 / scale2, 1.0);
                }
                bus.H();
                bus.H();
                y += module.getHeight();
            }
            String currentModule = module.getName();
            if (displayType == EnumDisplayType.ESCAPE || !Module.modulesByOverlistedModules.containsKey(currentModule)) {
                continue;
            }
            double width2 = module.getWidth();
            double moduleX2 = Math.max(getLastLeft(), Math.min(rightBound ? (x - width2) : x, getLastRight()));
            while (displayType != EnumDisplayType.ESCAPE && Module.modulesByOverlistedModules.containsKey(currentModule)) {
                final Module listedAfter = Module.modulesByOverlistedModules.get(currentModule);
                isShown = ((listedAfter.isShown() || (getCurrentModuleGui() != null && getCurrentModuleGui().isShowAllModules())) && listedAfter.getCategory().isEnabled());
                if (listedAfter.isEnabled(displayType) && isShown) {
                    width2 = listedAfter.getWidth();
                    moduleX2 = Math.max(getLastLeft(), Math.min(rightBound ? (x - width2) : x, getLastRight()));
                    listedAfter.setLastX(moduleX2);
                    listedAfter.setLastY(y);
                    listedAfter.setLastRightBound(rightBound);
                    double tRight2 = rightBound ? (moduleX2 + width2) : -1.0;
                    bus.G();
                    double mY = y;
                    if (listedAfter.supportsRescale()) {
                        listedAfter.applyScale(true);
                        moduleX2 = listedAfter.scaleModuleSize(moduleX2, true);
                        mY = listedAfter.scaleModuleSize(y, true);
                        if (rightBound) {
                            tRight2 = listedAfter.scaleModuleSize(tRight2, true);
                        }
                    }
                    listedAfter.draw(moduleX2, mY, tRight2);
                    bus.H();
                    y += listedAfter.getHeight() + listedAfter.getSpacing();
                }
                listedAfter.setLastCenter(false);
                currentModule = listedAfter.getName();
            }
        }
        bus.H();
    }
    
    protected boolean supportsRescale() {
        return false;
    }
    
    public static boolean isDrawn() {
        return bib.z().m instanceof ModGuiIngameMenu || bib.z().m instanceof LabyModModuleEditorGui || (!bib.z().t.ax && Permissions.isAllowed(Permissions.Permission.GUI_ALL));
    }
    
    public static Module getModuleByName(final String name) {
        for (final Module module : getModules()) {
            if (!module.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public static Module getModuleByClass(final Class<?> clazz) {
        for (final Module module : getModules()) {
            if (!module.getClass().equals(clazz)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public static Map<String, Module> getModulesByOverlistedModules() {
        return Module.modulesByOverlistedModules;
    }
    
    public static ModuleGui getCurrentModuleGui() {
        return Module.currentModuleGui;
    }
    
    public static void setCurrentModuleGui(final ModuleGui currentModuleGui) {
        Module.currentModuleGui = currentModuleGui;
    }
    
    public static void checkResizeAllModules() {
        final double w = getLastRight() - getLastLeft();
        final double h = getLastTop() - getLastBottom();
        if (Module.prevWidth != -1.0 && Module.prevHeight != -1.0 && (Module.prevWidth != w || Module.prevHeight != h)) {
            for (final Module module : getModules()) {
                module.onResize(w, h, Module.prevWidth, Module.prevHeight);
            }
            Module.prevWidth = w;
            Module.prevHeight = h;
        }
        else {
            Module.prevWidth = w;
            Module.prevHeight = h;
        }
    }
    
    public static List<Module> getModules() {
        return Module.moduleList;
    }
    
    public static double getLastLeft() {
        return Module.lastLeft;
    }
    
    public static double getLastRight() {
        return Module.lastRight;
    }
    
    public static double getLastTop() {
        return Module.lastTop;
    }
    
    public static double getLastBottom() {
        return Module.lastBottom;
    }
    
    @Deprecated
    public void draw(final int x, final int y, final int rightX) {
    }
    
    public void draw(final double x, final double y, final double rightX) {
        this.draw((int)Math.round(x), (int)Math.round(y), (int)Math.round(rightX));
    }
    
    public String getName() {
        return (this.moduleName == null) ? (this.moduleName = this.getClass().getSimpleName()) : this.moduleName;
    }
    
    public void init() {
        try {
            this.loadSettings();
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public EnumDisplayType[] getDisplayTypes() {
        return new EnumDisplayType[] { EnumDisplayType.INGAME };
    }
    
    public void createSettingElement() {
        final String displayName = this.getControlName();
        this.rawBooleanElement = new BooleanElement(this, this.getIconData(), displayName, "enabled");
        if (this.rawBooleanElement.getDisplayName() == null || !this.rawBooleanElement.getDisplayName().equals(displayName)) {
            this.rawBooleanElement.setDisplayName(displayName);
        }
        this.rawBooleanElement.setSortingId(this.getSortingId());
        final ArrayList<SettingsElement> subList = new ArrayList<SettingsElement>();
        this.fillSubSettings(subList);
        this.rawBooleanElement.getSubSettings().addAll(subList);
    }
    
    public String getControlName() {
        final String settingName = this.getSettingName();
        final String key = "module_" + settingName;
        final String translated = LanguageManager.translateOrReturnKey(key, new Object[0]);
        return translated.equals(key) ? key : translated;
    }
    
    public void settingUpdated(final boolean enabled) {
    }
    
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        final String displayUseDefault = LanguageManager.translate("use_default_option");
        final BooleanElement useDefaultElement = new BooleanElement(displayUseDefault, new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_USE_DEFAULT_SETTINGS), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean value) {
                Module.this.getModuleConfigElement().setUseExtendedSettings(!value);
                Module.this.init();
            }
        }, !this.getModuleConfigElement().isUsingExtendedSettings());
        settingsElements.add(useDefaultElement);
        if (this.supportsRescale()) {
            final String displayScale = LanguageManager.translate("module_scale");
            final SliderElement scaleElement = new SliderElement(displayScale, new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_GUI_SCALING), this.getModuleConfigElement().getScale());
            scaleElement.addCallback(new Consumer<Integer>() {
                @Override
                public void accept(final Integer value) {
                    Module.this.getModuleConfigElement().setScale(value);
                    Module.this.init();
                }
            });
            scaleElement.setRange(50, 150).setSteps(5);
            settingsElements.add(scaleElement);
        }
        DefaultElementsCreator.createAlignment(this, false, settingsElements);
    }
    
    public String getAttribute(final String attribute, final String defaultValue) {
        final String attr = this.getAttributes().get(attribute);
        String value = defaultValue;
        if (attr == null) {
            this.getAttributes().put(attribute, defaultValue);
        }
        else {
            value = attr;
        }
        return value;
    }
    
    public void setAttribute(final String attribute, final String value) {
        this.getAttributes().put(attribute, value);
    }
    
    public BooleanElement getBooleanElement() {
        if (this.rawBooleanElement == null) {
            this.createSettingElement();
        }
        return this.rawBooleanElement;
    }
    
    public void setBooleanElement(final BooleanElement booleanElement) {
        this.rawBooleanElement = booleanElement;
    }
    
    protected void applyScale(final boolean enabled) {
        final double scale = this.moduleConfigElement.getScale() / 100.0;
        final double value = enabled ? scale : (1.0 / scale);
        bus.a(value, value, 1.0);
    }
    
    protected double scaleModuleSize(final double value, final boolean divide) {
        final double scale = this.moduleConfigElement.getScale() / 100.0;
        return divide ? (value / scale) : (value * scale);
    }
    
    public abstract ControlElement.IconData getIconData();
    
    public boolean isShown() {
        return true;
    }
    
    public boolean isMovable(final int mouseX, final int mouseY) {
        return true;
    }
    
    public void initModuleGui(final ModuleGui moduleGui) {
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_OTHER;
    }
    
    public abstract double getHeight();
    
    public abstract double getWidth();
    
    public ModuleConfigElement getModuleConfigElement() {
        return this.moduleConfigElement;
    }
    
    public ModuleConfigElement setModuleConfigElement(final ModuleConfigElement moduleConfigElement) {
        this.moduleConfigElement = moduleConfigElement;
        if (moduleConfigElement.getListedAfter() != null) {
            Module.modulesByOverlistedModules.put(moduleConfigElement.getListedAfter(), this);
        }
        return moduleConfigElement;
    }
    
    public Set<EnumDisplayType> getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final Set<EnumDisplayType> enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnabled(final EnumDisplayType displayType) {
        return this.getEnabled() != null && this.getEnabled().contains(displayType);
    }
    
    public boolean isRightBound(final EnumDisplayType displayType) {
        final EnumModuleAlignment alignment = (this.getModuleConfigElement().getAlignment(displayType.ordinal()) == EnumModuleAlignment.DEFAULT || !this.getModuleConfigElement().isUsingExtendedSettings()) ? ModuleConfig.getConfig().getDefaultAlignment() : this.getModuleConfigElement().getAlignment(displayType.ordinal());
        final EnumModuleRegion region = this.getRegion(displayType.ordinal());
        if (this.getListedAfter() == null && alignment == EnumModuleAlignment.AUTO) {
            if (region.getHorizontalPosition() == 2 && this.getX(displayType.ordinal()) > 0.0) {
                return true;
            }
            if (region.getHorizontalPosition() == 3) {
                return true;
            }
        }
        else if (alignment == EnumModuleAlignment.RIGHT) {
            return true;
        }
        if (this.getListedAfter() == null || !this.lastRightBound) {
            return false;
        }
        return true;
    }
    
    public void onMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseMove(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseRelease(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseScroll(final int mouseX, final int mouseY) {
    }
    
    public void onKeyType(final char charType, final int keyCode) {
    }
    
    public String getListedAfter() {
        return this.listedAfter;
    }
    
    public Map<String, String> getAttributes() {
        return this.attributes;
    }
    
    public EnumModuleRegion getRegion(final int index) {
        return this.regions[index];
    }
    
    public double getX(final int index) {
        return this.x[index];
    }
    
    public double getY(final int index) {
        return this.y[index];
    }
    
    public void setListedAfter(final String listedAfter) {
        this.listedAfter = listedAfter;
    }
    
    public void setAttributes(final Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public void setRegion(final int index, final EnumModuleRegion region) {
        this.regions[index] = region;
    }
    
    public void setRegions(final EnumModuleRegion[] regions) {
        this.regions = regions;
    }
    
    public void setX(final int index, final double x) {
        this.x[index] = x;
    }
    
    public void setX(final double[] x) {
        this.x = x;
    }
    
    public void setY(final int index, final double y) {
        this.y[index] = y;
    }
    
    public void setY(final double[] y) {
        this.y = y;
    }
    
    public void onResize(final double width, final double height, final double prevWidth, final double prevHeight) {
    }
    
    public abstract void loadSettings();
    
    protected ControlElement.IconData getModuleIcon(final String name) {
        return new ControlElement.IconData(new nf("labymod/textures/settings/modules/" + name + ".png"));
    }
    
    protected ControlElement.IconData getModuleIcon(final String name, final String subSetting) {
        return new ControlElement.IconData(new nf("labymod/textures/settings/modules/" + name + "_" + subSetting + ".png"));
    }
    
    protected int getSpacing() {
        return 2;
    }
    
    public abstract String getSettingName();
    
    public abstract String getDescription();
    
    public abstract int getSortingId();
    
    public static EnumDisplayType getLastDrawnDisplayType() {
        return Module.lastDrawnDisplayType;
    }
    
    public static void setLastDrawnDisplayType(final EnumDisplayType lastDrawnDisplayType) {
        Module.lastDrawnDisplayType = lastDrawnDisplayType;
    }
    
    public double getLastX() {
        return this.lastX;
    }
    
    public void setLastX(final double lastX) {
        this.lastX = lastX;
    }
    
    public double getLastY() {
        return this.lastY;
    }
    
    public void setLastY(final double lastY) {
        this.lastY = lastY;
    }
    
    public boolean isLastCenter() {
        return this.lastCenter;
    }
    
    public void setLastCenter(final boolean lastCenter) {
        this.lastCenter = lastCenter;
    }
    
    public boolean isLastRightBound() {
        return this.lastRightBound;
    }
    
    public void setLastRightBound(final boolean lastRightBound) {
        this.lastRightBound = lastRightBound;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public void setRawBooleanElement(final BooleanElement rawBooleanElement) {
        this.rawBooleanElement = rawBooleanElement;
    }
    
    static {
        Module.lastRegisteredModuleId = -1;
        Module.prevWidth = -1.0;
        Module.prevHeight = -1.0;
        Module.lastDrawnDisplayType = EnumDisplayType.INGAME;
        Module.moduleList = new ArrayList<Module>();
        Module.registerQueue = new ArrayList<Module>();
        Module.modulesByOverlistedModules = new HashMap<String, Module>();
        mc = bib.z();
        Module.lastTablistRendered = 0L;
        boolean noModulesFound = true;
        try {
            for (final ClassPath.ClassInfo classInfo : ClassPath.from(Module.class.getClassLoader()).getTopLevelClassesRecursive("net.labymod.ingamegui.modules")) {
                final Module module = classInfo.load().newInstance();
                registerModule(module, false);
                noModulesFound = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            noModulesFound = true;
        }
        try {
            if (noModulesFound) {
                for (final Class<? extends Module> moduleClass : new Class[] { AfkTimerModule.class, BiomeModule.class, ClickTestModule.class, ClockModule.class, ComboModule.class, CoordinatesModule.class, DateModule.class, EntityCountModule.class, FModule.class, FPSModule.class, LavaTimerModule.class, MemoryModule.class, OnlinePlayersModule.class, PingModule.class, PotionEffectsModule.class, RangeModule.class, ScoreboardModule.class, ServerAddressModule.class, ServerInfoModule.class, ServerSupportModule.class, SpeedModule.class, YouTubeRealTimeModule.class, ArrowAmountModule.class, BootsModule.class, ChestplateModule.class, HeldItemModule.class, HelmetModule.class, LeggingsModule.class }) {
                    final Module module2 = (Module)moduleClass.newInstance();
                    registerModule(module2, false);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
