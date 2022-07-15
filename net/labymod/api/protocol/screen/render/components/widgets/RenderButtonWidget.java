//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render.components.widgets;

import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.api.protocol.screen.render.util.*;

public class RenderButtonWidget extends RenderWidget<ButtonWidget>
{
    public RenderButtonWidget(final ButtonWidget widget, final IInteractionCallback callback) {
        super((Widget)widget, callback);
    }
    
    public void initScreen(final IScreenAccessor accessor) {
        final bja button = new bja(((ButtonWidget)this.widget).getId(), this.getX(accessor), this.getY(accessor), ((ButtonWidget)this.widget).getWidth(), ((ButtonWidget)this.widget).getHeight(), ((ButtonWidget)this.widget).getValue());
        accessor.addMinecraftButton(button);
    }
    
    public void actionPerformed(final IScreenAccessor accessor) {
        this.sendResponse(accessor);
        if (((ButtonWidget)this.widget).isCloseScreenOnClick()) {
            accessor.closeProperly();
        }
    }
}
