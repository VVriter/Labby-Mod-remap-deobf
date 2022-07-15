//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import net.labymod.main.lang.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import java.io.*;
import java.lang.reflect.*;

public class GuiShaderSelection extends blk
{
    private blk lastScreen;
    private Scrollbar scrollbar;
    private int hoveredShader;
    private nf[] shaderResourceLocations;
    private static int shaderIndex;
    private CheckBox checkBox;
    private boolean enabled;
    
    public GuiShaderSelection(final blk lastScreen) {
        this.scrollbar = new Scrollbar(12);
        this.hoveredShader = -1;
        this.enabled = false;
        this.lastScreen = lastScreen;
        if (LabyMod.getInstance().isInGame()) {
            try {
                Field field = ReflectionHelper.findField(buq.class, LabyModCore.getMappingAdapter().getShaderResourceLocationsMappings());
                field.setAccessible(true);
                this.shaderResourceLocations = (nf[])field.get(bib.z().o);
                if (!bib.z().o.a()) {
                    GuiShaderSelection.shaderIndex = -1;
                }
                field = ReflectionHelper.findField(buq.class, LabyModCore.getMappingAdapter().getUseShaderMappings());
                field.setAccessible(true);
                this.enabled = (boolean)field.get(bib.z().o);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        (this.checkBox = new CheckBox("Enabled", this.enabled ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, 0, 0, 20, 20)).setUpdateListener((Consumer)new Consumer<CheckBox.EnumCheckBoxValue>() {
            @Override
            public void accept(final CheckBox.EnumCheckBoxValue accepted) {
                bib.z().o.c();
                if (accepted == CheckBox.EnumCheckBoxValue.DISABLED) {
                    LabyMod.getSettings().loadedShader = null;
                    LabyMod.getMainConfig().save();
                }
            }
        });
    }
    
    private void loadShader(final int index) {
        if (index == -1) {
            GuiShaderSelection.shaderIndex = -1;
            this.j.o.b();
            LabyMod.getSettings().loadedShader = null;
            LabyMod.getMainConfig().save();
            return;
        }
        try {
            final nf[] shaderResourceLocations = this.shaderResourceLocations;
            GuiShaderSelection.shaderIndex = index;
            final nf resourceLocation = shaderResourceLocations[index];
            LabyMod.getSettings().loadedShader = resourceLocation.a();
            LabyMod.getMainConfig().save();
            loadShader(resourceLocation);
            this.checkBox.setCurrentValue(CheckBox.EnumCheckBoxValue.ENABLED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void b() {
        super.b();
        this.n.add(new bja(3, this.l / 2 - 100 + 30, this.m - 26, 170, 20, LanguageManager.translate("button_done")));
        this.scrollbar.setPosition(this.l / 2 + 100 - 5, 48, this.l / 2 + 100, this.m - 45);
        this.checkBox.setX(this.l / 2 - 100);
        this.checkBox.setY(this.m - 26);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawAutoDimmedBackground(this.scrollbar.getScrollY());
        this.hoveredShader = -2;
        if (LabyMod.getInstance().isInGame() && this.shaderResourceLocations != null) {
            double y = 48.0 + this.scrollbar.getScrollY();
            int index = -1;
            this.drawEntry("None " + ModColor.cl('e') + "(Default)", index, y, mouseX, mouseY);
            y += 12.0;
            ++index;
            for (final nf resourceLocation : this.shaderResourceLocations) {
                String name = resourceLocation.a();
                if (name.contains("/")) {
                    final String[] path = name.split("/");
                    name = path[path.length - 1];
                    name = name.split("\\.")[0];
                    name = Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
                }
                this.drawEntry(name, index, y, mouseX, mouseY);
                y += 12.0;
                ++index;
            }
            this.scrollbar.update(this.shaderResourceLocations.length + 1);
            this.scrollbar.draw();
        }
        else {
            draw.drawCenteredString(ModColor.cl('c') + LanguageManager.translate("shader_selection_not_ingame"), this.l / 2, this.m / 2);
        }
        draw.drawOverlayBackground(0, 41);
        draw.drawOverlayBackground(this.m - 38, this.m);
        draw.drawGradientShadowTop(41.0, 0.0, this.l);
        draw.drawGradientShadowBottom(this.m - 38, 0.0, this.l);
        draw.drawCenteredString(LanguageManager.translate("title_shaders"), this.l / 2, 29.0);
        this.checkBox.drawCheckbox(mouseX, mouseY);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    private void drawEntry(final String name, final int index, final double y, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        if (GuiShaderSelection.shaderIndex == index) {
            draw.drawRect(this.l / 2 - 102, y - 2.0, this.l / 2 + 100 - 6, y + 10.0, Integer.MIN_VALUE);
            draw.drawRectBorder(this.l / 2 - 102, y - 2.0, this.l / 2 + 100 - 6, y + 10.0, Integer.MAX_VALUE, 1.0);
            if (index == -1) {
                bus.c(1.0f, 0.5f, 0.5f, 1.0f);
            }
            else {
                bus.c(0.5f, 1.0f, 0.5f, 1.0f);
            }
        }
        else {
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        }
        bib.z().N().a(ModTextures.MISC_MENU_POINT);
        draw.drawTexture(this.l / 2 - 100, y, 255.0, 255.0, 8.0, 8.0, 1.1f);
        draw.drawString(name, this.l / 2 - 100 + 10, y);
        if (mouseX > this.l / 2 - 100 && mouseX < this.l / 2 + 100 && mouseY > y && mouseY < y + 12.0 && mouseY > 41 && mouseY < this.m - 38) {
            this.hoveredShader = index;
        }
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 3) {
            bib.z().a(this.lastScreen);
        }
        super.a(button);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            bib.z().a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (this.hoveredShader != -2 && this.hoveredShader != GuiShaderSelection.shaderIndex) {
            this.loadShader(this.hoveredShader);
        }
        this.checkBox.mouseClicked(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
    
    public static void loadShader(final nf resourceLocation) throws Exception {
        final String[] methodNames = LabyModCore.getMappingAdapter().getLoadShaderMappings();
        Exception exception = null;
        final String[] array = methodNames;
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final String methodName = array[i];
            try {
                final Method method = buq.class.getDeclaredMethod(methodName, nf.class);
                method.setAccessible(true);
                method.invoke(bib.z().o, resourceLocation);
            }
            catch (Exception e) {
                exception = e;
                ++i;
                continue;
            }
            break;
        }
        if (exception != null) {
            exception.printStackTrace();
        }
    }
}
