//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render.components.widgets;

import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.api.protocol.screen.render.util.*;
import net.labymod.main.*;
import net.labymod.utils.*;

public class RenderImageWidget extends RenderWidget<ImageWidget>
{
    public RenderImageWidget(final ImageWidget widget, final IInteractionCallback callback) {
        super((Widget)widget, callback);
    }
    
    public void renderScreen(final IScreenAccessor accessor, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final String url = ((ImageWidget)this.widget).getUrl();
        final nf resourceLocation = LabyMod.getInstance().getDynamicTextureManager().getTexture("screen_image_widget_" + url.hashCode(), url);
        bib.z().N().a(resourceLocation);
        draw.drawTexture(this.getX(accessor), this.getY(accessor), ((ImageWidget)this.widget).getCutX(), ((ImageWidget)this.widget).getCutY(), ((ImageWidget)this.widget).getCutWidth(), ((ImageWidget)this.widget).getCutHeight(), ((ImageWidget)this.widget).getWidth(), ((ImageWidget)this.widget).getHeight());
    }
    
    public boolean mouseClicked(final IScreenAccessor accessor, final double mouseX, final double mouseY, final int button) {
        if (mouseX >= this.getX(accessor) && mouseX <= this.getX(accessor) + ((ImageWidget)this.widget).getWidth() && mouseY >= this.getY(accessor) && mouseY <= this.getY(accessor) + ((ImageWidget)this.widget).getHeight()) {
            this.sendResponse(accessor);
        }
        return super.mouseClicked(accessor, mouseX, mouseY, button);
    }
}
