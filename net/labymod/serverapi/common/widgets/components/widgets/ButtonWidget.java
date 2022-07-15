//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.serverapi.common.widgets.components.widgets;

import net.labymod.serverapi.common.widgets.components.*;
import com.google.gson.annotations.*;
import net.labymod.serverapi.common.widgets.util.*;

public class ButtonWidget extends ValueContainerWidget
{
    @SerializedName("close_screen_on_click")
    private boolean closeScreenOnClick;
    
    public ButtonWidget(final int id, final Anchor anchor, final double offsetX, final double offsetY, final String value, final int width, final int height) {
        super(id, anchor, offsetX, offsetY, value, width, height);
        this.value = value;
    }
    
    public void setCloseScreenOnClick(final boolean closeScreenOnClick) {
        this.closeScreenOnClick = closeScreenOnClick;
    }
    
    public boolean isCloseScreenOnClick() {
        return this.closeScreenOnClick;
    }
}
