//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render.components;

import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.api.protocol.screen.render.util.*;
import com.google.gson.*;
import net.labymod.serverapi.common.widgets.util.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.api.protocol.screen.render.components.widgets.*;
import net.labymod.utils.*;

public abstract class RenderWidget<T extends Widget> extends WidgetGui
{
    protected T widget;
    protected IInteractionCallback callback;
    
    public RenderWidget(final T widget, final IInteractionCallback callback) {
        this.widget = widget;
        this.callback = callback;
    }
    
    protected void fillResponsePayload(final JsonObject state) {
    }
    
    protected void sendResponse(final IScreenAccessor accessor) {
        this.callback.sendResponse(accessor.getScreenId(), EnumResponse.INTERACT, this.widget.getId(), this::putState);
    }
    
    public void putState(final JsonObject states) {
        final String key = String.valueOf(this.widget.getId());
        final JsonObject state = new JsonObject();
        this.fillResponsePayload(state);
        if (!state.entrySet().isEmpty()) {
            states.add(key, (JsonElement)state);
        }
    }
    
    protected int getX(final IScreenAccessor accessor) {
        return (int)(accessor.getWidth() / 100.0 * this.widget.getAnchor().getX() + this.widget.getOffsetX());
    }
    
    protected int getY(final IScreenAccessor accessor) {
        return (int)(accessor.getHeight() / 100.0 * this.widget.getAnchor().getY() + this.widget.getOffsetY());
    }
    
    public T getWidget() {
        return this.widget;
    }
    
    public static RenderWidget<? extends Widget> from(final Widget widget, final IInteractionCallback callback) {
        final EnumWidget type = EnumWidget.getTypeOf(widget.getClass());
        if (type == null) {
            return null;
        }
        switch (type) {
            case BUTTON: {
                return new RenderButtonWidget((ButtonWidget)widget, callback);
            }
            case TEXT_FIELD: {
                return new RenderTextFieldWidget((TextFieldWidget)widget, callback);
            }
            case LABEL: {
                return new RenderLabelWidget((LabelWidget)widget, callback);
            }
            case COLOR_PICKER: {
                return new RenderColorPickerWidget((ColorPickerWidget)widget, callback);
            }
            case IMAGE: {
                return new RenderImageWidget((ImageWidget)widget, callback);
            }
            default: {
                final LabelWidget label = new LabelWidget(widget, ModColor.cl('c') + "Widget not implemented: " + type.name(), 1, 1.0);
                return new RenderLabelWidget(label, callback);
            }
        }
    }
}
