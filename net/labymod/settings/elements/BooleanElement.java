//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.servermanager.*;
import net.labymod.api.*;
import net.labymod.ingamegui.*;
import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.*;
import net.labymod.ingamegui.enums.*;
import java.util.*;
import net.labymod.core.*;
import net.labymod.utils.*;

public class BooleanElement extends ControlElement
{
    private boolean currentValue;
    private Consumer<Boolean> toggleListener;
    private bja buttonToggle;
    private String stringEnabled;
    private String stringDisabled;
    private Consumer<Boolean> callback;
    
    public BooleanElement(final String displayName, final String configEntryName, final IconData iconData) {
        super(displayName, configEntryName, iconData);
        this.stringEnabled = "ON";
        this.stringDisabled = "OFF";
        if (configEntryName != null) {
            if (!configEntryName.isEmpty()) {
                try {
                    this.currentValue = ModSettings.class.getDeclaredField(configEntryName).getBoolean(LabyMod.getSettings());
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (NoSuchFieldException e2) {
                    e2.printStackTrace();
                }
            }
            this.toggleListener = new Consumer<Boolean>() {
                @Override
                public void accept(final Boolean accepted) {
                    try {
                        ModSettings.class.getDeclaredField(configEntryName).set(LabyMod.getSettings(), accepted);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    BooleanElement.this.setSettingEnabled(accepted);
                }
            };
        }
        this.createButton();
    }
    
    public BooleanElement(final String displayName, final Server server, final IconData iconData, final String attributeName) {
        super(displayName, null, iconData);
        this.stringEnabled = "ON";
        this.stringDisabled = "OFF";
        this.currentValue = server.getConfig().get(attributeName).getAsBoolean();
        this.toggleListener = new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                server.getConfig().addProperty(attributeName, accepted);
                server.saveConfig();
                server.loadConfig();
                BooleanElement.this.setSettingEnabled(accepted);
            }
        };
        this.createButton();
    }
    
    public BooleanElement(final String displayName, final LabyModAddon addon, final IconData iconData, final String attributeName, final boolean defaultValue) {
        super(displayName, null, iconData);
        this.stringEnabled = "ON";
        this.stringDisabled = "OFF";
        this.currentValue = (addon.getConfig().has(attributeName) ? addon.getConfig().get(attributeName).getAsBoolean() : defaultValue);
        this.toggleListener = new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                addon.getConfig().addProperty(attributeName, accepted);
                addon.saveConfig();
                addon.loadConfig();
                BooleanElement.this.setSettingEnabled(accepted);
            }
        };
        this.createButton();
    }
    
    public BooleanElement(final String displayName, final IconData iconData, final Consumer<Boolean> toggleListener, final boolean currentValue) {
        super(displayName, null, iconData);
        this.stringEnabled = "ON";
        this.stringDisabled = "OFF";
        this.currentValue = currentValue;
        this.toggleListener = toggleListener;
        this.createButton();
    }
    
    public BooleanElement(final Module module, final IconData iconData, final String displayName, final String attribute) {
        super(module, iconData, displayName);
        this.stringEnabled = "ON";
        this.stringDisabled = "OFF";
        if (attribute.equals("enabled")) {
            this.currentValue = module.isEnabled(Module.getLastDrawnDisplayType());
        }
        else {
            this.currentValue = Boolean.valueOf(module.getAttributes().get(attribute));
        }
        this.toggleListener = new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                if (attribute.equals("enabled")) {
                    module.init();
                    if (module.getListedAfter() != null) {
                        Module.getModulesByOverlistedModules().remove(module.getListedAfter());
                    }
                    if (Module.getModulesByOverlistedModules().containsKey(module.getName())) {
                        final String newListedAfter = module.getListedAfter();
                        final Module underlistedModule = Module.getModulesByOverlistedModules().get(module.getName());
                        underlistedModule.setListedAfter(newListedAfter);
                        underlistedModule.getModuleConfigElement().setListedAfter(newListedAfter);
                        Module.getModulesByOverlistedModules().remove(module.getName());
                        if (newListedAfter != null) {
                            Module.getModulesByOverlistedModules().put(newListedAfter, underlistedModule);
                        }
                    }
                    if (module.getListedAfter() != null) {
                        module.setListedAfter((String)null);
                        module.getModuleConfigElement().setListedAfter((String)null);
                    }
                    if (accepted) {
                        if (module instanceof ResizeableModule) {
                            ((ResizeableModule)module).height[Module.getLastDrawnDisplayType().ordinal()] = Math.min(((ResizeableModule)module).height[Module.getLastDrawnDisplayType().ordinal()], Module.getLastBottom() - Module.getLastTop() - 10.0);
                            module.setAttribute("height", ((ResizeableModule)module).getStringByArray(((ResizeableModule)module).height));
                            module.getModuleConfigElement().setAttributes(module.getAttributes());
                        }
                        module.getEnabled().add(Module.getLastDrawnDisplayType());
                        if (Module.getLastDrawnDisplayType() == EnumDisplayType.INGAME) {
                            boolean attach = true;
                            if (module.getModuleConfigElement().getLastListedAfter() != null) {
                                final Module lastListedAfter = Module.getModuleByName(module.getModuleConfigElement().getLastListedAfter());
                                if (lastListedAfter != null && lastListedAfter.isEnabled(Module.getLastDrawnDisplayType())) {
                                    attach = false;
                                    if (Module.getModulesByOverlistedModules().containsKey(lastListedAfter.getName())) {
                                        final Module attachedModule = Module.getModulesByOverlistedModules().get(lastListedAfter.getName());
                                        attachedModule.setListedAfter(module.getName());
                                        attachedModule.getModuleConfigElement().setListedAfter(attachedModule.getName());
                                        Module.getModulesByOverlistedModules().put(module.getName(), attachedModule);
                                    }
                                    Module.getModulesByOverlistedModules().put(lastListedAfter.getName(), module);
                                    module.setListedAfter(lastListedAfter.getName());
                                    module.getModuleConfigElement().setListedAfter(module.getListedAfter());
                                }
                            }
                            if (attach) {
                                final boolean attachToTheRight = DefaultValues.ATTACH_MODULES_RIGHT.contains(module.getClass());
                                final int displayTypeOrdinal = Module.getLastDrawnDisplayType().ordinal();
                                final List<Module> possibleModules = new ArrayList<Module>();
                                for (final Module modules : Module.getModules()) {
                                    if (modules.equals(module)) {
                                        continue;
                                    }
                                    if (!modules.isEnabled(Module.getLastDrawnDisplayType())) {
                                        continue;
                                    }
                                    if (module.getListedAfter() != null) {
                                        continue;
                                    }
                                    if ((attachToTheRight || modules.getRegion(displayTypeOrdinal) != EnumModuleRegion.TOP_LEFT) && (!attachToTheRight || modules.getRegion(displayTypeOrdinal) != EnumModuleRegion.TOP_RIGHT)) {
                                        continue;
                                    }
                                    possibleModules.add(modules);
                                }
                                Collections.sort(possibleModules, new Comparator<Module>() {
                                    @Override
                                    public int compare(final Module o1, final Module o2) {
                                        return Double.valueOf(o2.getY(displayTypeOrdinal)).compareTo(o1.getY(displayTypeOrdinal));
                                    }
                                });
                                boolean createNewPosition = true;
                                if (possibleModules.size() != 0) {
                                    Module bottomModule;
                                    Module target;
                                    for (bottomModule = possibleModules.get(0); Module.getModulesByOverlistedModules().containsKey(bottomModule.getName()); bottomModule = target) {
                                        target = Module.getModulesByOverlistedModules().get(bottomModule.getName());
                                        if (target != null && target.isEnabled(Module.getLastDrawnDisplayType())) {}
                                    }
                                    if (bottomModule != null && bottomModule.isEnabled(Module.getLastDrawnDisplayType())) {
                                        Module.getModulesByOverlistedModules().put(bottomModule.getName(), module);
                                        module.setListedAfter(bottomModule.getName());
                                        module.getModuleConfigElement().setListedAfter(module.getListedAfter());
                                        createNewPosition = false;
                                    }
                                }
                                if (createNewPosition) {
                                    module.setRegion(displayTypeOrdinal, attachToTheRight ? EnumModuleRegion.TOP_RIGHT : EnumModuleRegion.TOP_LEFT);
                                    module.getModuleConfigElement().setAlignment(displayTypeOrdinal, EnumModuleAlignment.AUTO);
                                    module.setX(displayTypeOrdinal, module.getRegion(displayTypeOrdinal).getOffsetX((double)(attachToTheRight ? (LabyMod.getInstance().getDrawUtils().getWidth() - 3) : 3), 0.0, (double)LabyMod.getInstance().getDrawUtils().getWidth()));
                                    module.setY(displayTypeOrdinal, module.getRegion(displayTypeOrdinal).getOffsetY(3.0, 0.0, (double)LabyMod.getInstance().getDrawUtils().getHeight()));
                                    module.getModuleConfigElement().setX(displayTypeOrdinal, module.getX(displayTypeOrdinal));
                                    module.getModuleConfigElement().setY(displayTypeOrdinal, module.getY(displayTypeOrdinal));
                                }
                            }
                        }
                    }
                    else {
                        module.getModuleConfigElement().setLastListedAfter(module.getListedAfter());
                        module.getEnabled().remove(Module.getLastDrawnDisplayType());
                    }
                    module.getModuleConfigElement().setEnabled(module.getEnabled());
                }
                else {
                    module.getAttributes().put(attribute, String.valueOf(accepted));
                    module.loadSettings();
                }
                if (BooleanElement.this.callback != null) {
                    BooleanElement.this.callback.accept(accepted);
                }
                BooleanElement.this.setSettingEnabled(accepted);
                module.settingUpdated((boolean)accepted);
            }
        };
        this.createButton();
    }
    
    public BooleanElement(final String configEntryName, final IconData iconData) {
        this(configEntryName, configEntryName, iconData);
    }
    
    public void createButton() {
        this.buttonToggle = new bja(-2, 0, 0, 0, 20, "");
        this.setSettingEnabled(this.currentValue);
    }
    
    @Override
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final int width = this.getObjectWidth();
        if (this.buttonToggle == null) {
            return;
        }
        this.buttonToggle.l = false;
        LabyModCore.getMinecraft().setButtonXPosition(this.buttonToggle, maxX - width - 2);
        LabyModCore.getMinecraft().setButtonYPosition(this.buttonToggle, y + 1);
        this.buttonToggle.a(width);
        LabyModCore.getMinecraft().drawButton(this.buttonToggle, mouseX, mouseY);
        this.buttonToggle.l = true;
        final int buttonWidth = this.buttonToggle.b();
        final int valueXPos = this.currentValue ? ((buttonWidth - 4) / 2) : ((buttonWidth - 4) / 2 + 6);
        final String displayString = (this.buttonToggle.a() ? ModColor.YELLOW : (this.currentValue ? ModColor.WHITE : ModColor.GRAY)) + (this.currentValue ? this.stringEnabled : this.stringDisabled);
        LabyMod.getInstance().getDrawUtils().drawCenteredString(displayString, LabyModCore.getMinecraft().getXPosition(this.buttonToggle) + valueXPos, LabyModCore.getMinecraft().getYPosition(this.buttonToggle) + 6);
        LabyMod.getInstance().getDrawUtils().drawString(this.currentValue ? ModColor.GREEN.toString() : ModColor.RED.toString(), 0.0, 0.0);
        this.mc.N().a(BooleanElement.buttonTextures);
        final int pos = (this.currentValue ? (maxX - 8) : (maxX - width)) - 2;
        LabyMod.getInstance().getDrawUtils().b(pos, y + 1, 0, 66, 4, 20);
        LabyMod.getInstance().getDrawUtils().b(pos + 4, y + 1, 196, 66, 4, 20);
        LabyMod.getInstance().getDrawUtils().drawRectangle(x - 1, y, x, maxY, this.currentValue ? ModColor.toRGB(20, 120, 20, 120) : ModColor.toRGB(120, 20, 20, 120));
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.buttonToggle.b(bib.z(), mouseX, mouseY)) {
            this.currentValue = !this.currentValue;
            if (this.toggleListener != null) {
                this.toggleListener.accept(this.currentValue);
            }
            if (this.callback != null) {
                this.callback.accept(this.currentValue);
            }
            this.buttonToggle.a(this.mc.U());
        }
    }
    
    public BooleanElement custom(final String... args) {
        if (args.length >= 1) {
            this.stringEnabled = args[0];
        }
        if (args.length >= 2) {
            this.stringDisabled = args[1];
        }
        return this;
    }
    
    public BooleanElement addCallback(final Consumer<Boolean> callback) {
        this.callback = callback;
        return this;
    }
    
    public boolean getCurrentValue() {
        return this.currentValue;
    }
}
