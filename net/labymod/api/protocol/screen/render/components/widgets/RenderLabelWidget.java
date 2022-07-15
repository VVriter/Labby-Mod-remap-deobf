//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render.components.widgets;

import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.api.protocol.screen.render.util.*;
import net.labymod.core.*;
import net.labymod.main.*;
import net.labymod.utils.*;

public class RenderLabelWidget extends RenderWidget<LabelWidget>
{
    private String string;
    
    public RenderLabelWidget(final LabelWidget widget, final IInteractionCallback callback) {
        super((Widget)widget, callback);
        this.string = "";
    }
    
    public void initScreen(final IScreenAccessor accessor) {
        super.initScreen(accessor);
        if (((LabelWidget)this.widget).getValue().isJsonPrimitive()) {
            this.string = ((LabelWidget)this.widget).getValue().getAsString();
        }
        else {
            try {
                this.string = LabyModCore.getMinecraft().rawTextToString(((LabelWidget)this.widget).getValue());
            }
            catch (Exception e) {
                e.printStackTrace();
                this.string = "";
            }
        }
        if (this.string == null) {
            this.string = "";
        }
    }
    
    public void renderScreen(final IScreenAccessor accessor, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        switch (((LabelWidget)this.widget).getAlignment()) {
            case 0: {
                draw.drawString(this.string, this.getX(accessor), this.getY(accessor), (float)((LabelWidget)this.widget).getScale());
                break;
            }
            case 1: {
                draw.drawCenteredString(this.string, this.getX(accessor), this.getY(accessor), (float)((LabelWidget)this.widget).getScale());
                break;
            }
            case 2: {
                draw.drawRightString(this.string, this.getX(accessor), this.getY(accessor), (float)((LabelWidget)this.widget).getScale());
                break;
            }
        }
    }
}
