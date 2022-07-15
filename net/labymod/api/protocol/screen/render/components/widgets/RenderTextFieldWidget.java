//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render.components.widgets;

import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.gui.elements.*;
import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.api.protocol.screen.render.util.*;
import net.labymod.core.*;
import com.google.gson.*;

public class RenderTextFieldWidget extends RenderWidget<TextFieldWidget>
{
    private ModTextField textField;
    
    public RenderTextFieldWidget(final TextFieldWidget widget, final IInteractionCallback callback) {
        super((Widget)widget, callback);
    }
    
    public void createScreen(final IScreenAccessor accessor) {
        (this.textField = new ModTextField(((TextFieldWidget)this.widget).getId(), LabyModCore.getMinecraft().getFontRenderer(), this.getX(accessor), this.getY(accessor), ((TextFieldWidget)this.widget).getWidth(), ((TextFieldWidget)this.widget).getHeight())).setText(((TextFieldWidget)this.widget).getValue());
        this.textField.setMaxStringLength(((TextFieldWidget)this.widget).getMaxLength());
        this.textField.setFocused(((TextFieldWidget)this.widget).isFocused());
        this.textField.setCursorPositionEnd();
        this.textField.setPlaceHolder(((TextFieldWidget)this.widget).getPlaceholder());
    }
    
    public void initScreen(final IScreenAccessor accessor) {
        super.initScreen(accessor);
        this.textField.xPosition = this.getX(accessor);
        this.textField.yPosition = this.getY(accessor);
        this.textField.setFocused(((TextFieldWidget)this.widget).isFocused());
    }
    
    public void renderScreen(final IScreenAccessor accessor, final int mouseX, final int mouseY) {
        this.textField.drawTextBox();
    }
    
    public void keyTyped(final IScreenAccessor accessor, final char typedChar, final int keyCode) {
        this.textField.textboxKeyTyped(typedChar, keyCode);
    }
    
    public boolean mouseClicked(final IScreenAccessor accessor, final double mouseX, final double mouseY, final int button) {
        final boolean prevFocused = this.textField.isFocused();
        final boolean result = this.textField.mouseClicked((int)mouseX, (int)mouseY, button);
        if (prevFocused && !this.textField.isFocused()) {
            this.sendResponse(accessor);
        }
        return result;
    }
    
    public void tick(final IScreenAccessor accessor) {
        this.textField.updateCursorCounter();
    }
    
    protected void fillResponsePayload(final JsonObject payload) {
        payload.addProperty("value", this.textField.getText());
    }
}
