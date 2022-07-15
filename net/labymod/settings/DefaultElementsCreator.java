//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.main.lang.*;
import net.labymod.ingamegui.*;
import net.labymod.ingamegui.enums.*;
import java.util.*;
import java.awt.*;
import net.labymod.gui.elements.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.ingamegui.moduletypes.*;
import net.labymod.settings.elements.*;

public class DefaultElementsCreator
{
    public static void createAlignment(final Module module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final String displayNameAlignment = LanguageManager.translate((isDefaultSetting ? "default" : "custom") + "_alignment");
        final DropDownMenu alignmentDropDownMenu = new DropDownMenu(displayNameAlignment, 0, 0, 0, 0).fill((Object[])EnumModuleAlignment.values());
        if (isDefaultSetting) {
            alignmentDropDownMenu.remove((Object)EnumModuleAlignment.DEFAULT);
        }
        final DropDownElement alignmentDropDown = new DropDownElement(displayNameAlignment, alignmentDropDownMenu);
        alignmentDropDownMenu.setSelected((Object)(isDefaultSetting ? ModuleConfig.getConfig().getDefaultAlignment() : module.getModuleConfigElement().getAlignment(Module.getLastDrawnDisplayType().ordinal())));
        if (!isDefaultSetting) {
            alignmentDropDown.setModuleCopyVisible(module);
        }
        alignmentDropDown.setChangeListener(new Consumer<EnumModuleAlignment>() {
            @Override
            public void accept(final EnumModuleAlignment alignment) {
                if (alignment != null) {
                    if (isDefaultSetting) {
                        ModuleConfig.getConfig().setDefaultAlignment(alignment);
                    }
                    else {
                        module.getModuleConfigElement().setAlignment(Module.getLastDrawnDisplayType().ordinal(), alignment);
                    }
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        alignmentDropDownMenu.setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                final String entry = object.toString().toLowerCase();
                LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate(entry), x, y);
            }
        });
        settingsElements.add(alignmentDropDown);
    }
    
    public static void createFormatting(final TextModule module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final String displayNameFormatting = LanguageManager.translate((isDefaultSetting ? "default" : "custom") + "_formatting");
        final DropDownMenu formattingDropDownMenu = new DropDownMenu(displayNameFormatting, 0, 0, 0, 0).fill((Object[])EnumModuleFormatting.values());
        if (isDefaultSetting) {
            formattingDropDownMenu.remove((Object)EnumModuleFormatting.DEFAULT);
        }
        final DropDownElement formattingDropDown = new DropDownElement(displayNameFormatting, formattingDropDownMenu);
        final String formattingString = isDefaultSetting ? "" : module.getAttributes().get("formatting");
        try {
            formattingDropDownMenu.setSelected((Object)(isDefaultSetting ? ModuleConfig.getConfig().getDefaultFormatting() : ((formattingString == null) ? EnumModuleFormatting.DEFAULT : EnumModuleFormatting.values()[Integer.parseInt(formattingString)])));
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (!isDefaultSetting) {
            formattingDropDown.setModuleCopyVisible((Module)module);
        }
        formattingDropDown.setChangeListener(new Consumer<EnumModuleFormatting>() {
            @Override
            public void accept(final EnumModuleFormatting formatting) {
                if (formatting != null) {
                    if (isDefaultSetting) {
                        ModuleConfig.getConfig().setDefaultFormatting((EnumModuleFormatting)formattingDropDownMenu.getSelected());
                    }
                    else {
                        module.getAttributes().put("formatting", String.valueOf(formatting.ordinal()));
                    }
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        formattingDropDownMenu.setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                final EnumModuleFormatting enumModuleFormatting = (EnumModuleFormatting)object;
                if (enumModuleFormatting == EnumModuleFormatting.DEFAULT) {
                    LabyMod.getInstance().getDrawUtils().drawString(LanguageManager.translate("default"), x, y);
                    return;
                }
                if (!isDefaultSetting) {
                    module.setColors();
                    module.setFormattings();
                    module.drawLine(enumModuleFormatting, x, y, "K", (List)Arrays.asList(new ColoredTextModule.Text("V", -1)));
                }
                final ModuleConfig config = ModuleConfig.getConfig();
                TextModule.drawTextLine(enumModuleFormatting, x, y, "K", (List)Collections.singletonList(new ColoredTextModule.Text("V", -1)), config.getBracketsColor(), config.getPrefixColor(), config.getValuesColor(), config.getFormattingBold() == 1, config.getFormattingItalic() == 1, config.getFormattingUnderline() == 1);
            }
        });
        settingsElements.add(formattingDropDown);
    }
    
    public static void createColorPicker(final TextModule module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final ColorPickerCheckBoxBulkElement colorPickerBulkElement = new ColorPickerCheckBoxBulkElement("Colors");
        colorPickerBulkElement.setCheckBoxRightBound(true);
        if (!isDefaultSetting) {
            colorPickerBulkElement.setModuleCopyVisible((Module)module);
        }
        final Color colorDefaultPrefix = new Color(ModuleConfig.getConfig().getPrefixColor());
        final Color colorPrefix = isDefaultSetting ? colorDefaultPrefix : ModColor.getColorByString(module.getAttributes().get("prefixColor"));
        final ColorPicker colorPickerPrefix = new ColorPicker("Prefix", colorPrefix, (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(ModuleConfig.getConfig().getPrefixColor());
            }
        }, 0, 0, 0, 0);
        colorPickerPrefix.setHasAdvanced(true);
        colorPickerPrefix.setHasDefault(!isDefaultSetting);
        colorPickerPrefix.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(Color color) {
                if (color != null && color.getRGB() == -1) {
                    color = new Color(254, 254, 254);
                }
                colorPickerPrefix.setDefault(color == null);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setPrefixColor(color.getRGB());
                }
                else {
                    module.getAttributes().put("prefixColor", String.valueOf((color == null) ? -1 : color.getRGB()));
                    module.getModuleConfigElement().setAttributes(module.getAttributes());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        colorPickerBulkElement.addColorPicker(colorPickerPrefix);
        final Color colorDefaultBrackets = new Color(ModuleConfig.getConfig().getBracketsColor());
        final Color colorBrackets = isDefaultSetting ? colorDefaultBrackets : ModColor.getColorByString(module.getAttributes().get("bracketsColor"));
        final ColorPicker colorPickerBrackets = new ColorPicker("Brackets", colorBrackets, (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(ModuleConfig.getConfig().getBracketsColor());
            }
        }, 0, 0, 0, 0);
        colorPickerBrackets.setHasAdvanced(true);
        colorPickerBrackets.setHasDefault(!isDefaultSetting);
        colorPickerBrackets.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(Color color) {
                if (color != null && color.getRGB() == -1) {
                    color = new Color(254, 254, 254);
                }
                colorPickerBrackets.setDefault(color == null);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setBracketsColor(color.getRGB());
                }
                else {
                    module.getAttributes().put("bracketsColor", String.valueOf((color == null) ? -1 : color.getRGB()));
                    module.getModuleConfigElement().setAttributes(module.getAttributes());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        colorPickerBulkElement.addColorPicker(colorPickerBrackets);
        final Color colorDefaultValue = new Color(ModuleConfig.getConfig().getValuesColor());
        final Color colorValue = isDefaultSetting ? colorDefaultValue : ModColor.getColorByString(module.getAttributes().get("valueColor"));
        final ColorPicker colorPickerValue = new ColorPicker("Value", colorValue, (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(ModuleConfig.getConfig().getValuesColor());
            }
        }, 0, 0, 0, 0);
        colorPickerValue.setHasAdvanced(true);
        colorPickerValue.setHasDefault(!isDefaultSetting);
        colorPickerValue.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(Color color) {
                if (color != null && color.getRGB() == -1) {
                    color = new Color(254, 254, 254);
                }
                colorPickerValue.setDefault(color == null);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setValuesColor(color.getRGB());
                }
                else {
                    module.getAttributes().put("valueColor", String.valueOf((color == null) ? -1 : color.getRGB()));
                    module.getModuleConfigElement().setAttributes(module.getAttributes());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        colorPickerBulkElement.addColorPicker(colorPickerValue);
        final CheckBox.EnumCheckBoxValue checkedDefaultBold = (ModuleConfig.getConfig().getFormattingBold() == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : ((ModuleConfig.getConfig().getFormattingBold() == -1) ? CheckBox.EnumCheckBoxValue.DEFAULT : CheckBox.EnumCheckBoxValue.DISABLED);
        final String boldAttr = isDefaultSetting ? null : module.getAttributes().get("boldFormatting");
        final CheckBox.EnumCheckBoxValue checkedBold = isDefaultSetting ? checkedDefaultBold : ((boldAttr == null) ? CheckBox.EnumCheckBoxValue.DEFAULT : ((Integer.parseInt(boldAttr) == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED));
        final CheckBox checkBoxBold = new CheckBox("Bold", checkedBold, (CheckBox.DefaultCheckBoxValueCallback)new CheckBox.DefaultCheckBoxValueCallback() {
            public CheckBox.EnumCheckBoxValue getDefaultValue() {
                return (ModuleConfig.getConfig().getFormattingBold() == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : ((ModuleConfig.getConfig().getFormattingBold() == -1) ? CheckBox.EnumCheckBoxValue.DEFAULT : CheckBox.EnumCheckBoxValue.DISABLED);
            }
        }, 0, 0, 0, 0);
        checkBoxBold.setHasDefault(!isDefaultSetting);
        checkBoxBold.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                final int mode = (accepted == null || accepted == CheckBox.EnumCheckBoxValue.DEFAULT) ? -1 : ((accepted == CheckBox.EnumCheckBoxValue.ENABLED) ? 1 : 0);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setFormattingBold(mode);
                }
                else {
                    module.getAttributes().put("boldFormatting", String.valueOf(mode));
                    module.getModuleConfigElement().setAttributes(module.getAttributes());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        colorPickerBulkElement.addCheckbox(checkBoxBold);
        final CheckBox.EnumCheckBoxValue checkedDefaultItalic = (ModuleConfig.getConfig().getFormattingItalic() == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : ((ModuleConfig.getConfig().getFormattingItalic() == -1) ? CheckBox.EnumCheckBoxValue.DEFAULT : CheckBox.EnumCheckBoxValue.DISABLED);
        final String italicAttr = isDefaultSetting ? null : module.getAttributes().get("italicFormatting");
        final CheckBox.EnumCheckBoxValue checkedItalic = isDefaultSetting ? checkedDefaultItalic : ((italicAttr == null) ? CheckBox.EnumCheckBoxValue.DEFAULT : ((Integer.parseInt(italicAttr) == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED));
        final CheckBox checkBoxItalic = new CheckBox("Italic", checkedItalic, (CheckBox.DefaultCheckBoxValueCallback)new CheckBox.DefaultCheckBoxValueCallback() {
            public CheckBox.EnumCheckBoxValue getDefaultValue() {
                return (ModuleConfig.getConfig().getFormattingItalic() == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : ((ModuleConfig.getConfig().getFormattingItalic() == -1) ? CheckBox.EnumCheckBoxValue.DEFAULT : CheckBox.EnumCheckBoxValue.DISABLED);
            }
        }, 0, 0, 0, 0);
        checkBoxItalic.setHasDefault(!isDefaultSetting);
        checkBoxItalic.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                final int mode = (accepted == null || accepted == CheckBox.EnumCheckBoxValue.DEFAULT) ? -1 : ((accepted == CheckBox.EnumCheckBoxValue.ENABLED) ? 1 : 0);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setFormattingItalic(mode);
                }
                else {
                    module.getAttributes().put("italicFormatting", String.valueOf(mode));
                    module.getModuleConfigElement().setAttributes(module.getAttributes());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        colorPickerBulkElement.addCheckbox(checkBoxItalic);
        final CheckBox.EnumCheckBoxValue checkedDefaultUnderline = (ModuleConfig.getConfig().getFormattingUnderline() == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : ((ModuleConfig.getConfig().getFormattingUnderline() == -1) ? CheckBox.EnumCheckBoxValue.DEFAULT : CheckBox.EnumCheckBoxValue.DISABLED);
        final String underlineAttr = isDefaultSetting ? null : module.getAttributes().get("underlineFormatting");
        final CheckBox.EnumCheckBoxValue checkedUnderline = isDefaultSetting ? checkedDefaultUnderline : ((underlineAttr == null) ? CheckBox.EnumCheckBoxValue.DEFAULT : ((Integer.parseInt(underlineAttr) == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED));
        final CheckBox checkBoxUnderline = new CheckBox("Underline", checkedUnderline, (CheckBox.DefaultCheckBoxValueCallback)new CheckBox.DefaultCheckBoxValueCallback() {
            public CheckBox.EnumCheckBoxValue getDefaultValue() {
                return (ModuleConfig.getConfig().getFormattingUnderline() == 1) ? CheckBox.EnumCheckBoxValue.ENABLED : ((ModuleConfig.getConfig().getFormattingUnderline() == -1) ? CheckBox.EnumCheckBoxValue.DEFAULT : CheckBox.EnumCheckBoxValue.DISABLED);
            }
        }, 0, 0, 0, 0);
        checkBoxUnderline.setHasDefault(!isDefaultSetting);
        checkBoxUnderline.setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                final int mode = (accepted == null || accepted == CheckBox.EnumCheckBoxValue.DEFAULT) ? -1 : ((accepted == CheckBox.EnumCheckBoxValue.ENABLED) ? 1 : 0);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setFormattingUnderline(mode);
                }
                else {
                    module.getAttributes().put("underlineFormatting", String.valueOf(mode));
                    module.getModuleConfigElement().setAttributes(module.getAttributes());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        colorPickerBulkElement.addCheckbox(checkBoxUnderline);
        settingsElements.add(colorPickerBulkElement);
    }
    
    public static void createDurability(final ItemModule module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final String displayNameItemDurability = LanguageManager.translate("item_durability_option");
        final DropDownMenu itemDurabilityDropDownMenu = new DropDownMenu(displayNameItemDurability, 0, 0, 0, 0).fill((Object[])ItemModule.DurabilityVisibility.values());
        if (isDefaultSetting) {
            itemDurabilityDropDownMenu.remove((Object)ItemModule.DurabilityVisibility.DEFAULT);
        }
        final DropDownElement itemDurabilityDropDown = new DropDownElement(displayNameItemDurability, itemDurabilityDropDownMenu);
        if (!isDefaultSetting) {
            itemDurabilityDropDown.setModuleCopyVisible((Module)module);
        }
        itemDurabilityDropDownMenu.setSelected((Object)(isDefaultSetting ? ModuleConfig.getConfig().getDefaultDurabilityVisibility() : module.visibility));
        itemDurabilityDropDown.setChangeListener(new Consumer<ItemModule.DurabilityVisibility>() {
            @Override
            public void accept(final ItemModule.DurabilityVisibility durabilityVisibility) {
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setDefaultDurabilityVisibility(durabilityVisibility);
                }
                else {
                    module.visibility = durabilityVisibility;
                    module.setAttribute("visibility", module.visibility.name());
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        itemDurabilityDropDownMenu.setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                final ItemModule.DurabilityVisibility durabilityVisibility = (ItemModule.DurabilityVisibility)object;
                final aip item = isDefaultSetting ? new aip(ain.c(1), 1) : module.getItem();
                LabyMod.getInstance().getDrawUtils().drawString((durabilityVisibility.getDurabilityBuilder() == null) ? durabilityVisibility.getDisplayName() : durabilityVisibility.getDurabilityBuilder().build(item), x, y);
            }
        });
        settingsElements.add(itemDurabilityDropDown);
    }
    
    public static void createKeyVisible(final TextModule module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final BooleanElement booleanElement = new BooleanElement(LanguageManager.translate("key_visible_switch"), new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_DISPLAY_KEY), new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setKeyVisible((boolean)accepted);
                }
                else {
                    module.setKeyVisible((boolean)accepted);
                    module.setAttribute("keyVisible", String.valueOf(accepted));
                }
                if (module != null) {
                    module.init();
                }
            }
        }, isDefaultSetting ? ModuleConfig.getConfig().isKeyVisible() : module.isKeyVisible());
        if (!isDefaultSetting) {
            booleanElement.setModuleCopyVisible((Module)module);
        }
        settingsElements.add(booleanElement);
    }
    
    public static void createPadding(final Module module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final SliderElement element = new SliderElement(LanguageManager.translate("padding"), new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_PADDING), (isDefaultSetting || !module.getAttributes().containsKey("padding")) ? ModuleConfig.getConfig().getPadding() : Integer.parseInt(module.getAttributes().get("padding")));
        element.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setPadding((int)accepted);
                }
                else {
                    module.setAttribute("padding", String.valueOf(accepted));
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        element.setRange(0, 5);
        if (!isDefaultSetting) {
            element.setModuleCopyVisible(module);
        }
        settingsElements.add(element);
    }
    
    public static void createBackgroundVisible(final Module module, final boolean isDefaultSetting, final List<SettingsElement> settingsElements) {
        final BooleanElement booleanElement = new BooleanElement(LanguageManager.translate("background_visible_switch"), new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_BACKGROUND), null, isDefaultSetting ? ModuleConfig.getConfig().isBackgroundVisible() : (module.getAttributes().containsKey("backgroundVisible") && Boolean.parseBoolean(module.getAttributes().get("backgroundVisible"))));
        booleanElement.addCallback(new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setBackgroundVisible((boolean)accepted);
                }
                else {
                    module.setAttribute("backgroundVisible", String.valueOf(accepted));
                }
                booleanElement.setSettingEnabled(accepted);
                if (module != null) {
                    module.init();
                }
            }
        });
        final SliderElement transparencyElement = new SliderElement(LanguageManager.translate("transparency_slider"), new ControlElement.IconData(Material.THIN_GLASS), isDefaultSetting ? ModuleConfig.getConfig().getBackgroundTransparency() : (module.getAttributes().containsKey("backgroundTransparency") ? Integer.parseInt(module.getAttributes().get("backgroundTransparency")) : 50));
        transparencyElement.setRange(0, 255);
        transparencyElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setBackgroundTransparency((int)accepted);
                }
                else {
                    module.setAttribute("backgroundTransparency", String.valueOf(accepted));
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        booleanElement.getSubSettings().add(transparencyElement);
        final ColorPickerCheckBoxBulkElement checkBoxBulkElement = new ColorPickerCheckBoxBulkElement("Background color");
        final Color color = new Color(isDefaultSetting ? ModuleConfig.getConfig().getBackgroundColor() : (module.getAttributes().containsKey("backgroundColor") ? Integer.parseInt(module.getAttributes().get("backgroundColor")) : Integer.MIN_VALUE));
        final ColorPicker colorPicker = new ColorPicker(LanguageManager.translate("color"), color, (ColorPicker.DefaultColorCallback)new ColorPicker.DefaultColorCallback() {
            public Color getDefaultColor() {
                return new Color(ModuleConfig.getConfig().getBackgroundColor());
            }
        }, 0, 0, 0, 0);
        colorPicker.setHasAdvanced(true);
        colorPicker.setHasDefault(!isDefaultSetting);
        colorPicker.setUpdateListener((Consumer)new Consumer<Color>() {
            @Override
            public void accept(final Color color) {
                colorPicker.setDefault(color == null);
                if (isDefaultSetting) {
                    ModuleConfig.getConfig().setBackgroundColor((color == null) ? Integer.MIN_VALUE : color.getRGB());
                }
                else {
                    module.setAttribute("backgroundColor", String.valueOf((color == null) ? Integer.MIN_VALUE : color.getRGB()));
                }
                if (module != null) {
                    module.init();
                }
            }
        });
        checkBoxBulkElement.addColorPicker(colorPicker);
        booleanElement.getSubSettings().add(checkBoxBulkElement);
        booleanElement.setSettingEnabled(booleanElement.getCurrentValue());
        if (!isDefaultSetting) {
            booleanElement.setModuleCopyVisible(module);
        }
        settingsElements.add(booleanElement);
    }
    
    public static void createKeyCustom(final SimpleModule module, final List<SettingsElement> settingsElements) {
        final StringElement stringElement = new StringElement("Custom Key", new ControlElement.IconData(Material.SIGN), module.getDisplayName(), new Consumer<String>() {
            @Override
            public void accept(final String accepted) {
                module.setAttribute("customKey", String.valueOf(accepted));
                if (module != null) {
                    module.init();
                }
            }
        });
        settingsElements.add(stringElement);
    }
}
