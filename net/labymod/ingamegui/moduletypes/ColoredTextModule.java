//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.moduletypes;

import net.labymod.ingamegui.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.util.*;
import net.labymod.utils.*;

public abstract class ColoredTextModule extends Module
{
    private static final byte TEXT_HEIGHT = 10;
    
    public abstract List<List<Text>> getTexts();
    
    public abstract int getLines();
    
    public void init() {
        super.init();
        if (this.getModuleConfigElement().isUsingExtendedSettings()) {
            this.padding = (this.getAttributes().containsKey("padding") ? Integer.parseInt(this.getAttributes().get("padding")) : 0);
            this.backgroundVisible = (this.getAttributes().containsKey("backgroundVisible") && Boolean.parseBoolean(this.getAttributes().get("backgroundVisible")));
            this.backgroundTransparency = (this.getAttributes().containsKey("backgroundTransparency") ? Integer.parseInt(this.getAttributes().get("backgroundTransparency")) : 50);
            this.backgroundColor = (this.getAttributes().containsKey("backgroundColor") ? Integer.parseInt(this.getAttributes().get("backgroundColor")) : Integer.MIN_VALUE);
        }
    }
    
    public void draw(final double x, final double y, final double rightX) {
        final List<List<Text>> allLines = this.getTexts();
        if (allLines == null) {
            return;
        }
        if (!this.getModuleConfigElement().isUsingExtendedSettings()) {
            this.padding = ModuleConfig.getConfig().getPadding();
            this.backgroundVisible = ModuleConfig.getConfig().isBackgroundVisible();
            if (this.backgroundVisible) {
                this.backgroundTransparency = ModuleConfig.getConfig().getBackgroundTransparency();
            }
        }
        if (this.backgroundVisible) {
            int color = this.backgroundColor;
            if (!this.getModuleConfigElement().isUsingExtendedSettings()) {
                color = ModuleConfig.getConfig().getBackgroundColor();
            }
            final int red = 0xFF & color >> 16;
            final int blue = 0xFF & color >> 0;
            final int green = 0xFF & color >> 8;
            color = (this.backgroundTransparency << 24 | red << 16 | green << 8 | blue);
            final double width = this.getRawWidth() + this.scaleModuleSize((double)(this.padding * 2.0f), true);
            final double height = this.getRawHeight() + this.scaleModuleSize((double)(this.padding * 2.0f), true);
            LabyMod.getInstance().getDrawUtils().drawRect(x - 1.0, y - 1.0, x + width + 1.0, y + height - 1.0, color);
        }
        final double paddingSize = this.scaleModuleSize((double)this.padding, true);
        drawTexts(allLines, x + paddingSize, y + paddingSize, rightX);
    }
    
    public double getHeight() {
        return this.scaleModuleSize(this.getRawHeight(), false) + this.padding * 2.0f;
    }
    
    public double getRawHeight() {
        return this.getLines() * 10;
    }
    
    protected int getSpacing() {
        return 0;
    }
    
    public double getWidth() {
        return this.scaleModuleSize(this.getRawWidth(), false) + this.padding * 2.0f;
    }
    
    public double getRawWidth() {
        double highestWidth = 0.0;
        final List<List<Text>> allLines = this.getTexts();
        if (allLines == null) {
            return 0.0;
        }
        for (final List<Text> texts : allLines) {
            double lineWidth = 0.0;
            for (final Text text : texts) {
                final double stringWidth = LabyModCore.getMinecraft().getFontRenderer().a(text.getText());
                lineWidth += stringWidth;
            }
            if (lineWidth > highestWidth) {
                highestWidth = lineWidth;
            }
        }
        return highestWidth;
    }
    
    public boolean supportsRescale() {
        return true;
    }
    
    public static void drawTexts(final List<List<Text>> allLines, double x, double y, final double rightX) {
        final boolean rightBound = rightX != -1.0;
        final double finalX = x;
        final bip fontRenderer = LabyModCore.getMinecraft().getFontRenderer();
        for (final List<Text> texts : allLines) {
            if (rightBound) {
                x = rightX;
            }
            else {
                x = finalX;
            }
            if (rightBound) {
                Collections.reverse(texts);
            }
            for (final Text text : texts) {
                final int stringWidth = fontRenderer.a(text.getText());
                if (rightBound) {
                    x -= stringWidth;
                }
                LabyMod.getInstance().getDrawUtils().drawStringWithShadow(text.getText(), x, y, text.getColor());
                if (!rightBound) {
                    x += stringWidth;
                }
            }
            y += 10.0;
        }
    }
    
    public static class Text
    {
        private String text;
        private int color;
        
        public Text(final String text, final int color) {
            this.text = text;
            this.color = color;
        }
        
        public Text(String text, final int color, final boolean bold, final boolean italic, final boolean underline) {
            if (bold) {
                text = ModColor.cl('l') + text;
            }
            if (italic) {
                text = ModColor.cl('o') + text;
            }
            if (underline) {
                text = ModColor.cl('n') + text;
            }
            this.text = text;
            this.color = color;
        }
        
        public static Text getText(final String text) {
            return new Text(text, 16777215);
        }
        
        public static Text getText(final String text, final int color) {
            return new Text(text, color);
        }
        
        public static Text getText(final String text, final int r, final int g, final int b) {
            return getText(text, ModColor.toRGB(r, g, b, 255));
        }
        
        public static Text getText(final String text, final int r, final int g, final int b, final int a) {
            return getText(text, ModColor.toRGB(r, g, b, a));
        }
        
        public boolean isDefaultColor() {
            return this.color == 16777215;
        }
        
        public String getText() {
            return this.text;
        }
        
        public int getColor() {
            return this.color;
        }
        
        public void setText(final String text) {
            this.text = text;
        }
        
        public void setColor(final int color) {
            this.color = color;
        }
    }
}
