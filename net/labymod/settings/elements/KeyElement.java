//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.gui.elements.*;
import net.labymod.main.*;
import net.labymod.ingamegui.*;
import net.labymod.api.*;
import net.labymod.core.*;
import org.lwjgl.input.*;
import net.labymod.utils.*;

public class KeyElement extends ControlElement
{
    private Integer currentKey;
    private boolean allowMouseButtons;
    private Consumer<Integer> changeListener;
    private ModTextField textField;
    private Consumer<Integer> callback;
    
    public KeyElement(final String displayName, final String configEntryName, final ControlElement.IconData iconData) {
        super(displayName, configEntryName, iconData);
        this.allowMouseButtons = true;
        if (!configEntryName.isEmpty()) {
            try {
                this.currentKey = (Integer)ModSettings.class.getDeclaredField(configEntryName).get(LabyMod.getSettings());
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        if (this.currentKey == null) {
            this.currentKey = -1;
        }
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                try {
                    ModSettings.class.getDeclaredField(configEntryName).set(LabyMod.getSettings(), accepted);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (KeyElement.this.callback != null) {
                    KeyElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public KeyElement(final Module module, final ControlElement.IconData iconData, final String displayName, final String attribute) {
        this(module, iconData, displayName, attribute, false);
    }
    
    public KeyElement(final Module module, final ControlElement.IconData iconData, final String displayName, final String attribute, final boolean allowMouseButtons) {
        super(module, iconData, displayName);
        this.allowMouseButtons = true;
        this.allowMouseButtons = allowMouseButtons;
        this.currentKey = Integer.valueOf(module.getAttributes().get(attribute));
        if (this.currentKey == null) {
            this.currentKey = -1;
        }
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                module.getAttributes().put(attribute, String.valueOf(accepted));
                module.loadSettings();
                if (KeyElement.this.callback != null) {
                    KeyElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public KeyElement(final String displayName, final LabyModAddon labymodAddon, final ControlElement.IconData iconData, final String attributeName, final int currentKey, final boolean allowMouseButtons) {
        super(displayName, iconData);
        this.allowMouseButtons = true;
        this.allowMouseButtons = allowMouseButtons;
        this.currentKey = currentKey;
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                labymodAddon.getConfig().addProperty(attributeName, (Number)accepted);
                labymodAddon.loadConfig();
                if (KeyElement.this.callback != null) {
                    KeyElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public KeyElement(final String displayName, final LabyModAddon labymodAddon, final ControlElement.IconData iconData, final String attributeName, final int currentKey) {
        this(displayName, labymodAddon, iconData, attributeName, currentKey, false);
    }
    
    public KeyElement(final String displayName, final ControlElement.IconData iconData, final int currentKey, final Consumer<Integer> changeListener) {
        super(displayName, iconData);
        this.allowMouseButtons = true;
        this.currentKey = currentKey;
        this.changeListener = changeListener;
        this.createTextfield();
    }
    
    public KeyElement(final String configEntryName, final ControlElement.IconData iconData) {
        this(configEntryName, configEntryName, iconData);
    }
    
    public void createTextfield() {
        this.textField = new ModTextField(-2, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, this.getObjectWidth() - 5, 20);
        this.updateValue();
        this.textField.setCursorPositionEnd();
        this.textField.setFocused(false);
    }
    
    private void updateValue() {
        if (this.currentKey == -1) {
            this.textField.setText("NONE");
        }
        else {
            try {
                this.textField.setText((this.currentKey < 0) ? ("Mouse " + (this.currentKey + 100)) : Keyboard.getKeyName((int)this.currentKey));
            }
            catch (Exception error) {
                this.currentKey = -1;
                error.printStackTrace();
            }
        }
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
    }
    
    public void unfocus(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean prevFocus = this.textField.isFocused();
        super.unfocus(mouseX, mouseY, mouseButton);
        this.textField.setFocused(false);
        if (prevFocus && this.allowMouseButtons) {
            this.textField.setFocused(false);
            this.currentKey = mouseButton - 100;
            this.changeListener.accept(this.currentKey);
            this.updateValue();
            this.updateScreen();
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            this.textField.mouseClicked(mouseX, mouseY, 0);
        }
    }
    
    public void keyTyped(final char typedChar, int keyCode) {
        if (keyCode == 1) {
            keyCode = -1;
        }
        if (this.textField.isFocused()) {
            this.textField.setFocused(false);
            this.currentKey = keyCode;
            this.changeListener.accept(keyCode);
            this.updateValue();
        }
    }
    
    public void updateScreen() {
        super.updateScreen();
        this.textField.updateCursorCounter();
    }
    
    public KeyElement maxLength(final int maxLength) {
        this.textField.setMaxStringLength(maxLength);
        return this;
    }
    
    public KeyElement setAllowMouseButtons(final boolean allowMouseButtons) {
        this.allowMouseButtons = allowMouseButtons;
        return this;
    }
    
    public KeyElement addCallback(final Consumer<Integer> callback) {
        this.callback = callback;
        return this;
    }
    
    public int getObjectWidth() {
        return 53;
    }
    
    public ModTextField getTextField() {
        return this.textField;
    }
}
