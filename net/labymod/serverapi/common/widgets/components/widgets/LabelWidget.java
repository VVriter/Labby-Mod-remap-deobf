//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components.widgets;

import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.serverapi.common.widgets.util.*;
import com.google.gson.*;

public class LabelWidget extends Widget
{
    private JsonElement value;
    private int alignment;
    private double scale;
    
    public LabelWidget(final Widget sourceWidget, final String value, final int alignment, final double scale) {
        this(sourceWidget.getId(), sourceWidget.getAnchor(), sourceWidget.getOffsetX(), sourceWidget.getOffsetY(), value, alignment, scale);
    }
    
    public LabelWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final String value, final int alignment, final double scale) {
        this(id, anchor, offsetX, offsetY, (JsonElement)new JsonPrimitive(value), alignment, scale);
    }
    
    public LabelWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final JsonElement value, final int alignment, final double scale) {
        super(id, anchor, offsetX, offsetY);
        this.value = value;
        this.alignment = alignment;
        this.scale = scale;
    }
    
    public JsonElement getValue() {
        return this.value;
    }
    
    public void setValue(final JsonElement value) {
        this.value = value;
    }
    
    public void setValue(final String value) {
        this.value = (JsonElement)new JsonPrimitive(value);
    }
    
    public void setAlignment(final int alignment) {
        this.alignment = alignment;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
    
    public int getAlignment() {
        return this.alignment;
    }
    
    public double getScale() {
        return this.scale;
    }
}
