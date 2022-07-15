//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render;

import net.labymod.api.protocol.screen.render.util.*;
import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.*;
import java.util.*;
import net.labymod.serverapi.common.widgets.util.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.io.*;
import com.google.gson.*;

public class GuiScreenProtocol extends blk implements IScreenAccessor
{
    private final blk lastScreen;
    private final int id;
    private IInteractionCallback callback;
    private List<RenderWidget<? extends Widget>> widgets;
    private String lastExceptionMessage;
    private boolean screenCreated;
    
    public GuiScreenProtocol(final blk lastScreen, final int id, final IInteractionCallback callback, final List<RenderWidget<? extends Widget>> widgets) {
        this.lastExceptionMessage = null;
        this.screenCreated = false;
        this.lastScreen = lastScreen;
        this.id = id;
        this.callback = callback;
        this.widgets = widgets;
    }
    
    public void b() {
        super.b();
        try {
            this.n.clear();
            for (final RenderWidget<? extends Widget> widget : this.widgets) {
                if (!this.screenCreated) {
                    widget.createScreen((IScreenAccessor)this);
                }
                widget.initScreen((IScreenAccessor)this);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastExceptionMessage = e.getMessage();
        }
        this.screenCreated = true;
    }
    
    public void closeProperly() {
        try {
            for (final RenderWidget<? extends Widget> widget2 : this.widgets) {
                widget2.onClose((IScreenAccessor)this);
            }
            this.callback.sendResponse(this.id, EnumResponse.CLOSE, -1, states -> this.widgets.forEach(widget -> widget.putState(states)));
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastExceptionMessage = e.getMessage();
        }
        bib.z().a(this.lastScreen);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        if (this.lastExceptionMessage == null) {
            super.a(mouseX, mouseY, partialTicks);
            try {
                for (final RenderWidget<? extends Widget> widget : this.widgets) {
                    widget.renderScreen((IScreenAccessor)this, mouseX, mouseY);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                this.lastExceptionMessage = ((e instanceof NullPointerException) ? "NullPointerException" : e.getMessage());
            }
        }
        else {
            LabyMod.getInstance().getDrawUtils().drawCenteredString(ModColor.cl('c') + this.lastExceptionMessage, this.l / 2.0, this.m / 2.0);
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        try {
            for (final RenderWidget<? extends Widget> widget : this.widgets) {
                widget.keyTyped((IScreenAccessor)this, typedChar, keyCode);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastExceptionMessage = e.getMessage();
        }
        if (keyCode == 1) {
            this.closeProperly();
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int button) throws IOException {
        try {
            for (final RenderWidget<? extends Widget> widget : this.widgets) {
                if (widget.mouseClicked((IScreenAccessor)this, (double)mouseX, (double)mouseY, button)) {
                    return;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastExceptionMessage = e.getMessage();
        }
        super.a(mouseX, mouseY, button);
    }
    
    public void e() {
        try {
            for (final RenderWidget<? extends Widget> widget : this.widgets) {
                widget.tick((IScreenAccessor)this);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastExceptionMessage = e.getMessage();
        }
    }
    
    protected void a(final bja button) throws IOException {
        try {
            for (final RenderWidget<? extends Widget> widget : this.widgets) {
                if (widget.getWidget().getId() == button.k) {
                    widget.actionPerformed((IScreenAccessor)this);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.lastExceptionMessage = e.getMessage();
        }
        super.a(button);
    }
    
    public void addMinecraftButton(final bja button) {
        this.n.add(button);
    }
    
    public int getScreenId() {
        return this.id;
    }
    
    public int getWidth() {
        return this.l;
    }
    
    public int getHeight() {
        return this.m;
    }
    
    public blk getLastScreen() {
        return this.lastScreen;
    }
    
    public void setWidgets(final List<RenderWidget<? extends Widget>> widgets) {
        this.widgets = widgets;
    }
}
