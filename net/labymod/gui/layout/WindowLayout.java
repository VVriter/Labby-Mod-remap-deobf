//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.layout;

import org.lwjgl.input.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;
import java.util.*;
import java.io.*;

public abstract class WindowLayout extends blk
{
    protected List<WindowElement<?>> windowElements;
    
    public WindowLayout() {
        this.windowElements = new ArrayList<WindowElement<?>>();
    }
    
    public void b() {
        this.n.clear();
        super.b();
        Keyboard.enableRepeatEvents(true);
        this.n.add(new bja(20, this.l - 55, 5, 50, 20, LanguageManager.translate("account")));
        this.initLayout();
        LabyMod.getInstance().getLabyConnect().setViaServerList(false);
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void initLayout() {
        final List<WindowElement<?>> windowElements = new ArrayList<WindowElement<?>>();
        this.initLayout(windowElements);
        this.windowElements = windowElements;
    }
    
    protected abstract void initLayout(final List<WindowElement<?>> p0);
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        for (final WindowElement<?> element : this.windowElements) {
            element.draw(mouseX, mouseY);
        }
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final WindowElement<?> element : this.windowElements) {
            element.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.a(mouseX, mouseY, mouseButton);
    }
    
    protected void a(final bja button) throws IOException {
        for (final WindowElement<?> element : this.windowElements) {
            element.actionPerformed(button);
        }
        super.a(button);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        for (final WindowElement<?> element : this.windowElements) {
            element.keyTyped(typedChar, keyCode);
        }
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        for (final WindowElement<?> element : this.windowElements) {
            element.mouseClickMove(mouseX, mouseY);
        }
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        for (final WindowElement<?> element : this.windowElements) {
            element.mouseReleased(mouseX, mouseY, state);
        }
        super.b(mouseX, mouseY, state);
    }
    
    public void k() throws IOException {
        for (final WindowElement<?> element : this.windowElements) {
            element.mouseInput();
        }
        super.k();
    }
    
    public void e() {
        for (final WindowElement<?> element : this.windowElements) {
            element.updateScreen();
        }
        super.e();
    }
    
    public List<bja> getButtonList() {
        return (List<bja>)this.n;
    }
}
