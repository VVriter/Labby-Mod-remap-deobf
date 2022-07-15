//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render.components.widgets;

import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.gui.elements.*;
import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.api.protocol.screen.render.util.*;
import java.awt.*;
import com.google.gson.*;

public class RenderColorPickerWidget extends RenderWidget<ColorPickerWidget> implements ColorPicker.DefaultColorCallback
{
    private ColorPicker colorPicker;
    
    public RenderColorPickerWidget(final ColorPickerWidget widget, final IInteractionCallback callback) {
        super((Widget)widget, callback);
    }
    
    public void createScreen(final IScreenAccessor accessor) {
        (this.colorPicker = new ColorPicker(((ColorPickerWidget)this.widget).getTitle(), ((ColorPickerWidget)this.widget).getSelectedColor(), this, this.getX(accessor), this.getY(accessor), ((ColorPickerWidget)this.widget).getWidth(), ((ColorPickerWidget)this.widget).getHeight())).setHasAdvanced(((ColorPickerWidget)this.widget).isRgb());
    }
    
    public void initScreen(final IScreenAccessor accessor) {
        super.initScreen(accessor);
        this.colorPicker.setX(this.getX(accessor));
        this.colorPicker.setY(this.getY(accessor));
    }
    
    public void renderScreen(final IScreenAccessor accessor, final int mouseX, final int mouseY) {
        this.colorPicker.drawColorPicker(mouseX, mouseY);
    }
    
    public boolean mouseClicked(final IScreenAccessor accessor, final double mouseX, final double mouseY, final int button) {
        return this.colorPicker.mouseClicked((int)mouseX, (int)mouseY, button);
    }
    
    public Color getDefaultColor() {
        return Color.WHITE;
    }
    
    protected void fillResponsePayload(final JsonObject state) {
        super.fillResponsePayload(state);
        state.addProperty("color", "#" + Integer.toHexString(this.colorPicker.getSelectedColor().getRGB()).substring(2));
    }
}
