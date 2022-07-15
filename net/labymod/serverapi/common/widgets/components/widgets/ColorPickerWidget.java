//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components.widgets;

import net.labymod.serverapi.common.widgets.components.*;
import java.awt.*;
import com.google.gson.annotations.*;
import net.labymod.serverapi.common.widgets.util.*;

public class ColorPickerWidget extends ContainerWidget
{
    private String title;
    @SerializedName("selected_color")
    private Color selectedColor;
    private boolean rgb;
    
    public ColorPickerWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final int width, final int height, final String title, final Color selectedColor) {
        super(id, anchor, offsetX, offsetY, width, height);
        this.title = title;
        this.selectedColor = selectedColor;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public Color getSelectedColor() {
        return this.selectedColor;
    }
    
    public void setSelectedColor(final Color selectedColor) {
        this.selectedColor = selectedColor;
    }
    
    public boolean isRgb() {
        return this.rgb;
    }
    
    public void setRgb(final boolean rgb) {
        this.rgb = rgb;
    }
}
