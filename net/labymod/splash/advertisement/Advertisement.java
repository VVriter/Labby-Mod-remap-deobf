//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.splash.advertisement;

import java.awt.*;
import java.beans.*;

public class Advertisement
{
    private String title;
    private boolean isNew;
    private boolean visible;
    private String url;
    private Color color;
    private Color colorHover;
    private String iconName;
    
    public String getTitle() {
        return this.title;
    }
    
    public boolean isNew() {
        return this.isNew;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public Color getColorHover() {
        return this.colorHover;
    }
    
    public String getIconName() {
        return this.iconName;
    }
    
    @ConstructorProperties({ "title", "isNew", "visible", "url", "color", "colorHover", "iconName" })
    public Advertisement(final String title, final boolean isNew, final boolean visible, final String url, final Color color, final Color colorHover, final String iconName) {
        this.title = title;
        this.isNew = isNew;
        this.visible = visible;
        this.url = url;
        this.color = color;
        this.colorHover = colorHover;
        this.iconName = iconName;
    }
}
