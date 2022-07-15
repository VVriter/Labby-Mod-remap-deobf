//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.moduletypes;

import net.labymod.ingamegui.enums.*;
import net.labymod.settings.elements.*;
import net.labymod.settings.*;
import net.labymod.ingamegui.*;
import java.util.*;

public abstract class TextModule extends ColoredTextModule
{
    protected int prefixColor;
    protected int bracketsColor;
    protected int valueColor;
    protected boolean bold;
    protected boolean italic;
    protected boolean underline;
    protected boolean keyVisible;
    private EnumModuleFormatting formatting;
    
    public TextModule() {
        this.prefixColor = -1;
        this.bracketsColor = -1;
        this.valueColor = -1;
        this.keyVisible = true;
        this.formatting = EnumModuleFormatting.DEFAULT;
    }
    
    public List<List<ColoredTextModule.Text>> getCustomTextLines() {
        return null;
    }
    
    public abstract String[] getKeys();
    
    public abstract List<List<ColoredTextModule.Text>> getTextValues();
    
    public abstract String[] getDefaultKeys();
    
    public abstract List<List<ColoredTextModule.Text>> getDefaultTextValues();
    
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings((List)settingsElements);
        DefaultElementsCreator.createFormatting(this, false, settingsElements);
        DefaultElementsCreator.createColorPicker(this, false, settingsElements);
        DefaultElementsCreator.createKeyVisible(this, false, settingsElements);
        DefaultElementsCreator.createBackgroundVisible((Module)this, false, settingsElements);
        DefaultElementsCreator.createPadding((Module)this, false, settingsElements);
    }
    
    public void init() {
        super.init();
        if (this.getModuleConfigElement().isUsingExtendedSettings()) {
            this.keyVisible = (!this.getAttributes().containsKey("keyVisible") || Boolean.parseBoolean(this.getAttributes().get("keyVisible")));
            if (this.getAttributes().containsKey("formatting")) {
                this.formatting = EnumModuleFormatting.values()[Integer.parseInt(this.getAttributes().get("formatting"))];
            }
        }
    }
    
    public List<List<ColoredTextModule.Text>> getTexts() {
        final List<List<ColoredTextModule.Text>> customLines = this.getCustomTextLines();
        if (customLines != null) {
            return customLines;
        }
        this.setColors();
        if (!this.getModuleConfigElement().isUsingExtendedSettings()) {
            this.keyVisible = ModuleConfig.getConfig().isKeyVisible();
        }
        this.setFormattings();
        final String[] keys = this.isShown() ? this.getKeys() : this.getDefaultKeys();
        final List<List<ColoredTextModule.Text>> values = this.isShown() ? this.getTextValues() : this.getDefaultTextValues();
        final EnumModuleFormatting displayFormatting = this.getDisplayFormatting();
        final List<List<ColoredTextModule.Text>> texts = new ArrayList<List<ColoredTextModule.Text>>();
        try {
            for (int i = 0; i < keys.length; ++i) {
                texts.add(displayFormatting.getTexts(keys[i], (List)values.get(i), this.bracketsColor, this.prefixColor, this.valueColor, this.keyVisible, this.bold, this.italic, this.underline));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return texts;
    }
    
    public int getLines() {
        final List<List<ColoredTextModule.Text>> customLines = this.getCustomTextLines();
        return (customLines != null) ? customLines.size() : (this.isShown() ? this.getKeys() : this.getDefaultKeys()).length;
    }
    
    public void setColors() {
        int dumpColor = 0;
        this.prefixColor = ((!this.getAttributes().containsKey("prefixColor") || !this.getModuleConfigElement().isUsingExtendedSettings() || (dumpColor = Integer.parseInt(this.getAttributes().get("prefixColor"))) == -1) ? ModuleConfig.getConfig().getPrefixColor() : dumpColor);
        this.bracketsColor = ((!this.getAttributes().containsKey("bracketsColor") || !this.getModuleConfigElement().isUsingExtendedSettings() || (dumpColor = Integer.parseInt(this.getAttributes().get("bracketsColor"))) == -1) ? ModuleConfig.getConfig().getBracketsColor() : dumpColor);
        this.valueColor = ((!this.getAttributes().containsKey("valueColor") || !this.getModuleConfigElement().isUsingExtendedSettings() || (dumpColor = Integer.parseInt(this.getAttributes().get("valueColor"))) == -1) ? ModuleConfig.getConfig().getValuesColor() : dumpColor);
    }
    
    public void setFormattings() {
        int dumpFormatting = 0;
        final int boldMode = (!this.getAttributes().containsKey("boldFormatting") || !this.getModuleConfigElement().isUsingExtendedSettings() || (dumpFormatting = Integer.parseInt(this.getAttributes().get("boldFormatting"))) == -1) ? ModuleConfig.getConfig().getFormattingBold() : dumpFormatting;
        final int italicMode = (!this.getAttributes().containsKey("italicFormatting") || !this.getModuleConfigElement().isUsingExtendedSettings() || (dumpFormatting = Integer.parseInt(this.getAttributes().get("italicFormatting"))) == -1) ? ModuleConfig.getConfig().getFormattingItalic() : dumpFormatting;
        final int underlineMode = (!this.getAttributes().containsKey("underlineFormatting") || !this.getModuleConfigElement().isUsingExtendedSettings() || (dumpFormatting = Integer.parseInt(this.getAttributes().get("underlineFormatting"))) == -1) ? ModuleConfig.getConfig().getFormattingUnderline() : dumpFormatting;
        this.bold = (boldMode == 1);
        this.italic = (italicMode == 1);
        this.underline = (underlineMode == 1);
    }
    
    public void drawLine(final EnumModuleFormatting formatting, final int x, final int y, final String key, final List<ColoredTextModule.Text> value) {
        drawTextLine(formatting, x, y, key, value, this.bracketsColor, this.prefixColor, this.valueColor, this.bold, this.italic, this.underline);
    }
    
    protected EnumModuleFormatting getDisplayFormatting() {
        return (this.formatting == EnumModuleFormatting.DEFAULT || !this.getModuleConfigElement().isUsingExtendedSettings() || this.formatting == null) ? ModuleConfig.getConfig().getDefaultFormatting() : this.formatting;
    }
    
    public static void drawTextLine(final EnumModuleFormatting formatting, final int x, final int y, final String key, final List<ColoredTextModule.Text> value, final int bracketsColor, final int prefixColor, final int valueColor, final boolean bold, final boolean italic, final boolean underline) {
        formatting.draw(x, y, key, (List)value, bracketsColor, prefixColor, valueColor, bold, italic, underline);
    }
    
    public boolean isKeyVisible() {
        return this.keyVisible;
    }
    
    public void setKeyVisible(final boolean keyVisible) {
        this.keyVisible = keyVisible;
    }
}
