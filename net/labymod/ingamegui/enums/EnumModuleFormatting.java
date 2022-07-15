//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.enums;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.ingamegui.*;
import java.util.*;

public enum EnumModuleFormatting
{
    DEFAULT("DEFAULT", "", "", "", ""), 
    COLON("K: V", "", ": ", " :", ""), 
    BRACKETS("K> V", "", "> ", "<", ""), 
    SQUARE_BRACKETS("[K] V", "[", "] ", " [", "]"), 
    HYPHEN("K - V", "", " - ", " - ", "");
    
    private String format;
    private String beforeKey;
    private String afterKey;
    private String rightBeforeKey;
    private String rightAfterKey;
    
    private EnumModuleFormatting(final String format, final String beforeKey, final String afterKey, final String rightBeforeKey, final String rightAfterKey) {
        this.format = format;
        this.beforeKey = beforeKey;
        this.afterKey = afterKey;
        this.rightBeforeKey = rightBeforeKey;
        this.rightAfterKey = rightAfterKey;
    }
    
    @Override
    public String toString() {
        return this.format;
    }
    
    public void draw(final int x, final int y, final String key, final List<ColoredTextModule.Text> value, final int bracketsColor, final int prefixColor, final int valueColor, final boolean bold, final boolean italic, final boolean underline) {
        if (this == EnumModuleFormatting.DEFAULT) {
            return;
        }
        ColoredTextModule.drawTexts(Arrays.asList(this.getTexts(key, value, bracketsColor, prefixColor, valueColor, true, bold, italic, underline)), x, y, -1.0);
    }
    
    public List<ColoredTextModule.Text> getTexts(final String key, final List<ColoredTextModule.Text> value, int bracketsColor, int prefixColor, int valueColor, final boolean showKey, final boolean bold, final boolean italic, final boolean underline) {
        final List<ColoredTextModule.Text> texts = new ArrayList<ColoredTextModule.Text>();
        if (bracketsColor == -1) {
            bracketsColor = ModuleConfig.getConfig().getBracketsColor();
        }
        if (prefixColor == -1) {
            prefixColor = ModuleConfig.getConfig().getPrefixColor();
        }
        if (valueColor == -1) {
            valueColor = ModuleConfig.getConfig().getValuesColor();
        }
        if (!this.beforeKey.isEmpty() && showKey) {
            texts.add(new ColoredTextModule.Text(this.beforeKey, bracketsColor, bold, italic, underline));
        }
        if (showKey) {
            texts.add(new ColoredTextModule.Text(key, prefixColor, bold, italic, underline));
        }
        if (!this.afterKey.isEmpty() && showKey) {
            texts.add(new ColoredTextModule.Text(this.afterKey, bracketsColor, bold, italic, underline));
        }
        for (final ColoredTextModule.Text valueElement : value) {
            texts.add(new ColoredTextModule.Text(valueElement.getText(), (valueElement.getColor() == -1) ? valueColor : valueElement.getColor(), bold, italic, underline));
        }
        return texts;
    }
    
    public String getRawText(final String key, final String value) {
        return this.beforeKey + key + this.afterKey + value;
    }
}
