//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.gui.elements.*;
import net.labymod.ingamegui.*;
import net.labymod.api.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import net.labymod.settings.*;
import net.labymod.main.lang.*;
import java.io.*;

public class StringElement extends ControlElement
{
    private String currentValue;
    private Consumer<String> changeListener;
    private ModTextField textField;
    private Consumer<String> callback;
    private boolean hoverExpandButton;
    
    public StringElement(final String displayName, final String configEntryName, final ControlElement.IconData iconData) {
        super(displayName, configEntryName, iconData);
        this.hoverExpandButton = false;
        if (!configEntryName.isEmpty()) {
            try {
                this.currentValue = (String)ModSettings.class.getDeclaredField(configEntryName).get(LabyMod.getSettings());
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        if (this.currentValue == null) {
            this.currentValue = "";
        }
        this.changeListener = new Consumer<String>() {
            @Override
            public void accept(final String accepted) {
                try {
                    ModSettings.class.getDeclaredField(configEntryName).set(LabyMod.getSettings(), accepted);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringElement.this.callback != null) {
                    StringElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public StringElement(final Module module, final ControlElement.IconData iconData, final String displayName, final String attribute) {
        super(module, iconData, displayName);
        this.hoverExpandButton = false;
        this.currentValue = module.getAttributes().get(attribute);
        if (this.currentValue == null) {
            this.currentValue = "";
        }
        this.changeListener = new Consumer<String>() {
            @Override
            public void accept(final String accepted) {
                module.getAttributes().put(attribute, accepted);
                module.loadSettings();
                if (StringElement.this.callback != null) {
                    StringElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public StringElement(final String displayName, final LabyModAddon addon, final ControlElement.IconData iconData, final String attribute, String currentValue) {
        super(displayName, iconData);
        this.hoverExpandButton = false;
        if (currentValue == null) {
            currentValue = "";
        }
        this.currentValue = currentValue;
        this.changeListener = new Consumer<String>() {
            @Override
            public void accept(final String accepted) {
                addon.getConfig().addProperty(attribute, accepted);
                addon.loadConfig();
                if (StringElement.this.callback != null) {
                    StringElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public StringElement(final String displayName, final ControlElement.IconData iconData, final String currentValue, final Consumer<String> changeListener) {
        super(displayName, iconData);
        this.hoverExpandButton = false;
        this.currentValue = currentValue;
        this.changeListener = changeListener;
        this.createTextfield();
    }
    
    public StringElement(final String configEntryName, final ControlElement.IconData iconData) {
        this(configEntryName, configEntryName, iconData);
    }
    
    public void createTextfield() {
        (this.textField = new ModTextField(-2, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, this.getObjectWidth() - 5, 20)).setMaxStringLength(500);
        this.updateValue();
        this.textField.setCursorPositionEnd();
        this.textField.setFocused(false);
    }
    
    private void updateValue() {
        this.textField.setText((this.currentValue == null) ? "" : this.currentValue);
    }
    
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final int width = this.getObjectWidth() - 5;
        if (this.textField == null) {
            return;
        }
        this.textField.xPosition = maxX - width - 2;
        this.textField.yPosition = y + 1;
        this.textField.drawTextBox();
        LabyMod.getInstance().getDrawUtils().drawRectangle(x - 1, y, x, maxY, ModColor.toRGB(120, 120, 120, 120));
        bus.d(1.0f, 1.0f, 1.0f);
        bib.z().N().a(ModTextures.BUTTON_EXPAND);
        this.hoverExpandButton = (mouseX > maxX - this.getObjectWidth() - 12 && mouseX < maxX - this.getObjectWidth() - 7 + 8 && mouseY > y + 1 && mouseY < y + 1 + 8);
        LabyMod.getInstance().getDrawUtils().drawTexture(maxX - this.getObjectWidth() - 7, y + 1, 0.0, this.hoverExpandButton ? 130.0 : 0.0, 256.0, 128.0, 8.0, 8.0);
    }
    
    public void unfocus(final int mouseX, final int mouseY, final int mouseButton) {
        super.unfocus(mouseX, mouseY, mouseButton);
        if (this.hoverExpandButton) {
            this.hoverExpandButton = false;
            bib.z().a((blk)new ExpandedStringElementGui(this.textField, bib.z().m, new Consumer<ModTextField>() {
                @Override
                public void accept(final ModTextField accepted) {
                    StringElement.this.textField.setText(accepted.getText());
                    StringElement.this.textField.setFocused(true);
                    StringElement.this.textField.setCursorPosition(accepted.getCursorPosition());
                    StringElement.this.textField.setSelectionPos(accepted.getSelectionEnd());
                    StringElement.this.changeListener.accept(StringElement.this.textField.getText());
                }
            }));
        }
        this.textField.setFocused(false);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, 0);
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.textField.textboxKeyTyped(typedChar, keyCode)) {
            this.changeListener.accept(this.textField.getText());
        }
    }
    
    public void updateScreen() {
        super.updateScreen();
        this.textField.updateCursorCounter();
    }
    
    public StringElement maxLength(final int maxLength) {
        this.textField.setMaxStringLength(maxLength);
        return this;
    }
    
    public StringElement addCallback(final Consumer<String> callback) {
        this.callback = callback;
        return this;
    }
    
    public int getObjectWidth() {
        return 85;
    }
    
    public class ExpandedStringElementGui extends blk
    {
        private blk backgroundScreen;
        private Consumer<ModTextField> callback;
        private ModTextField preField;
        private ModTextField expandedField;
        
        public ExpandedStringElementGui(final ModTextField preField, final blk backgroundScreen, final Consumer<ModTextField> callback) {
            this.backgroundScreen = backgroundScreen;
            this.callback = callback;
            this.preField = preField;
        }
        
        public void b() {
            super.b();
            this.backgroundScreen.l = this.l;
            this.backgroundScreen.m = this.m;
            if (this.backgroundScreen instanceof LabyModModuleEditorGui) {
                PreviewRenderer.getInstance().init(ExpandedStringElementGui.class);
            }
            (this.expandedField = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 150, this.m / 4 + 45, 300, 20)).setMaxStringLength(this.preField.getMaxStringLength());
            this.expandedField.setFocused(true);
            this.expandedField.setText(this.preField.getText());
            this.expandedField.setCursorPosition(this.preField.getCursorPosition());
            this.expandedField.setSelectionPos(this.preField.getSelectionEnd());
            this.n.add(new bja(1, this.l / 2 - 50, this.m / 4 + 85, 100, 20, LanguageManager.translate("button_done")));
        }
        
        public void a(final int mouseX, final int mouseY, final float partialTicks) {
            this.backgroundScreen.a(mouseX, mouseY, partialTicks);
            a(0, 0, this.l, this.m, Integer.MIN_VALUE);
            a(this.l / 2 - 165, this.m / 4 + 35, this.l / 2 + 165, this.m / 4 + 120, Integer.MIN_VALUE);
            this.expandedField.drawTextBox();
            super.a(mouseX, mouseY, partialTicks);
        }
        
        protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
            super.a(mouseX, mouseY, mouseButton);
            this.expandedField.mouseClicked(mouseX, mouseY, mouseButton);
            this.callback.accept(this.expandedField);
        }
        
        protected void a(final char typedChar, final int keyCode) throws IOException {
            if (keyCode == 1) {
                bib.z().a(this.backgroundScreen);
            }
            if (this.expandedField.textboxKeyTyped(typedChar, keyCode)) {
                this.callback.accept(this.expandedField);
            }
        }
        
        public void e() {
            this.backgroundScreen.e();
            this.expandedField.updateCursorCounter();
        }
        
        protected void a(final bja button) throws IOException {
            super.a(button);
            if (button.k == 1) {
                bib.z().a(this.backgroundScreen);
            }
        }
        
        public blk getBackgroundScreen() {
            return this.backgroundScreen;
        }
    }
}
