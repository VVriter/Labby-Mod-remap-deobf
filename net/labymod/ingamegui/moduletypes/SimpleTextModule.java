//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.moduletypes;

import java.util.*;

public abstract class SimpleTextModule extends TextModule
{
    public abstract String[] getValues();
    
    public abstract String[] getDefaultValues();
    
    @Override
    public List<List<ColoredTextModule.Text>> getCustomTextLines() {
        if (this.getCustomLines() == null) {
            return null;
        }
        final List<List<ColoredTextModule.Text>> textValues = new ArrayList<List<ColoredTextModule.Text>>();
        for (final String value : this.getCustomLines()) {
            textValues.add(Collections.singletonList(ColoredTextModule.Text.getText(value)));
        }
        return textValues;
    }
    
    public String[] getCustomLines() {
        return null;
    }
    
    @Override
    public List<List<ColoredTextModule.Text>> getTextValues() {
        final List<List<ColoredTextModule.Text>> textValues = new ArrayList<List<ColoredTextModule.Text>>();
        for (final String value : this.getValues()) {
            textValues.add(Collections.singletonList(ColoredTextModule.Text.getText(value, -1)));
        }
        return textValues;
    }
    
    @Override
    public List<List<ColoredTextModule.Text>> getDefaultTextValues() {
        final List<List<ColoredTextModule.Text>> textValues = new ArrayList<List<ColoredTextModule.Text>>();
        for (final String value : this.getDefaultValues()) {
            textValues.add(Collections.singletonList(ColoredTextModule.Text.getText(value, -1)));
        }
        return textValues;
    }
}
