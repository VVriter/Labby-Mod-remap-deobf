//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components.widgets;

import net.labymod.serverapi.common.widgets.components.*;
import com.google.gson.annotations.*;
import net.labymod.serverapi.common.widgets.util.*;

public class TextFieldWidget extends ValueContainerWidget
{
    protected String placeholder;
    @SerializedName("max_length")
    protected int maxLength;
    protected boolean focused;
    
    public TextFieldWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final String value, final int width, final int height, final String placeholder, final int maxLength, final boolean focused) {
        super(id, anchor, offsetX, offsetY, value, width, height);
        this.placeholder = placeholder;
        this.maxLength = maxLength;
        this.focused = focused;
    }
    
    public void setPlaceholder(final String placeholder) {
        this.placeholder = placeholder;
    }
    
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }
    
    public void setFocused(final boolean focused) {
        this.focused = focused;
    }
    
    public String getPlaceholder() {
        return this.placeholder;
    }
    
    public int getMaxLength() {
        return this.maxLength;
    }
    
    public boolean isFocused() {
        return this.focused;
    }
}
