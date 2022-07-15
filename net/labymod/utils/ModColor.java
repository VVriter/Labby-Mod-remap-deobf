//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.awt.*;

public enum ModColor
{
    BLACK('0', 0, 0, 0), 
    DARK_BLUE('1', 0, 0, 170), 
    DARK_GREEN('2', 0, 170, 0), 
    DARK_AQUA('3', 0, 170, 170), 
    DARK_RED('4', 170, 0, 0), 
    DARK_PURPLE('5', 170, 0, 170), 
    GOLD('6', 255, 170, 0), 
    GRAY('7', 170, 170, 170), 
    DARK_GRAY('8', 85, 85, 85), 
    BLUE('9', 85, 85, 255), 
    GREEN('a', 85, 255, 85), 
    AQUA('b', 85, 255, 255), 
    RED('c', 255, 85, 85), 
    PINK('d', 255, 85, 255), 
    YELLOW('e', 255, 255, 85), 
    WHITE('f', 255, 255, 255), 
    RESET('r'), 
    BOLD('l'), 
    ITALIC('o'), 
    UNDERLINE('n'), 
    MAGIC('k'), 
    STRIKETHROUGH('m');
    
    public static final String[] COLOR_CODES;
    public static final char COLOR_CHAR_PREFIX = '§';
    private char colorChar;
    private Color color;
    
    private ModColor(final char colorChar, final int r, final int g, final int b) {
        this.colorChar = colorChar;
        this.color = new Color(r, g, b);
    }
    
    private ModColor(final char colorChar) {
        this.colorChar = colorChar;
    }
    
    @Override
    public String toString() {
        return String.valueOf('§') + this.colorChar;
    }
    
    public static String cl(final String colorChar) {
        return '§' + colorChar;
    }
    
    public static String cl(final char colorChar) {
        return String.valueOf(new char[] { '§', colorChar });
    }
    
    private static String getColorCharPrefix() {
        return String.valueOf('§');
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public char getColorChar() {
        return this.colorChar;
    }
    
    public static String removeColor(final String string) {
        return string.replaceAll(getColorCharPrefix() + "[a-z0-9]", "");
    }
    
    public static String createColors(final String string) {
        return string.replaceAll("(?i)&([a-z0-9])", "§$1");
    }
    
    public static String booleanToColor(final boolean value) {
        return value ? ModColor.GREEN.toString() : ModColor.RED.toString();
    }
    
    public static int toRGB(final int r, final int g, final int b, final int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF) << 0;
    }
    
    public static String getCharAsString() {
        return String.valueOf('§');
    }
    
    public static Color changeBrightness(final Color color, final float fraction) {
        float red = color.getRed() + 255.0f * fraction;
        if (red > 255.0f) {
            red = 255.0f;
        }
        if (red < 0.0f) {
            red = 0.0f;
        }
        float green = color.getGreen() + 255.0f * fraction;
        if (green > 255.0f) {
            green = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        }
        float blue = color.getBlue() + 255.0f * fraction;
        if (blue > 255.0f) {
            blue = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        }
        return new Color((int)red, (int)green, (int)blue);
    }
    
    public static Color getColorByString(final String color) {
        if (color == null) {
            return null;
        }
        if (color.equals("-1")) {
            return null;
        }
        return new Color(Integer.parseInt(color));
    }
    
    static {
        COLOR_CODES = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "k", "m", "n", "l", "o", "r" };
    }
}
