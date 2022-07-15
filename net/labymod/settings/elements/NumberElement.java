//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.main.*;
import java.lang.reflect.*;
import net.labymod.ingamegui.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import org.apache.commons.lang3.*;

public class NumberElement extends ControlElement
{
    private static final nf SERVER_SELECTION_BUTTONS;
    private Integer currentValue;
    private Consumer<Integer> changeListener;
    private bje textField;
    private Consumer<Integer> callback;
    private int minValue;
    private int maxValue;
    private boolean hoverUp;
    private boolean hoverDown;
    private int steps;
    private long fastTickerCounterValue;
    
    public NumberElement(final String displayName, final String configEntryName, final ControlElement.IconData iconData) {
        super(displayName, configEntryName, iconData);
        this.minValue = 0;
        this.maxValue = Integer.MAX_VALUE;
        this.steps = 1;
        this.fastTickerCounterValue = 0L;
        if (!configEntryName.isEmpty()) {
            try {
                this.currentValue = (Integer)ModSettings.class.getDeclaredField(configEntryName).get(LabyMod.getSettings());
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
        if (this.currentValue == null) {
            this.currentValue = this.minValue;
        }
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                try {
                    final Field f = ModSettings.class.getDeclaredField(configEntryName);
                    if (f.getType().equals(Integer.TYPE)) {
                        f.set(LabyMod.getSettings(), accepted);
                    }
                    else {
                        f.set(LabyMod.getSettings(), String.valueOf(accepted));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (NumberElement.this.callback != null) {
                    NumberElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public NumberElement(final String displayName, final ControlElement.IconData iconData, final int currentValue) {
        super(displayName, (String)null, iconData);
        this.minValue = 0;
        this.maxValue = Integer.MAX_VALUE;
        this.steps = 1;
        this.fastTickerCounterValue = 0L;
        this.currentValue = currentValue;
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                if (NumberElement.this.callback != null) {
                    NumberElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public NumberElement(final Module module, final ControlElement.IconData iconData, final String displayName, final String attribute) {
        super(module, iconData, displayName);
        this.minValue = 0;
        this.maxValue = Integer.MAX_VALUE;
        this.steps = 1;
        this.fastTickerCounterValue = 0L;
        try {
            final String attr = module.getAttributes().get(attribute);
            this.currentValue = ((attr == null) ? this.minValue : Integer.valueOf(attr));
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        if (this.currentValue == null) {
            this.currentValue = this.minValue;
        }
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                module.getAttributes().put(attribute, String.valueOf(accepted));
                module.loadSettings();
                if (NumberElement.this.callback != null) {
                    NumberElement.this.callback.accept(accepted);
                }
            }
        };
        this.createTextfield();
    }
    
    public NumberElement(final String configEntryName, final ControlElement.IconData iconData) {
        this(configEntryName, configEntryName, iconData);
    }
    
    public NumberElement setMinValue(final int minValue) {
        this.minValue = minValue;
        if (this.currentValue < this.minValue) {
            this.currentValue = this.minValue;
        }
        return this;
    }
    
    public NumberElement setMaxValue(final int maxValue) {
        this.maxValue = maxValue;
        if (this.currentValue > this.maxValue) {
            this.currentValue = this.maxValue;
        }
        return this;
    }
    
    public NumberElement setRange(final int min, final int max) {
        this.setMinValue(min);
        this.setMaxValue(max);
        return this;
    }
    
    public NumberElement setSteps(final int steps) {
        this.steps = steps;
        return this;
    }
    
    public void createTextfield() {
        this.textField = new bje(-2, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, this.getObjectWidth(), 20);
        this.updateValue();
        this.textField.b(false);
    }
    
    private void updateValue() {
        this.textField.a(String.valueOf(this.currentValue));
    }
    
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final int width = this.getObjectWidth();
        if (this.textField == null) {
            return;
        }
        LabyModCore.getMinecraft().setTextFieldXPosition(this.textField, maxX - width - 2);
        LabyModCore.getMinecraft().setTextFieldYPosition(this.textField, y + 1);
        this.textField.g();
        LabyMod.getInstance().getDrawUtils().drawRectangle(x - 1, y, x, maxY, ModColor.toRGB(120, 120, 120, 120));
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bib.z().N().a(NumberElement.SERVER_SELECTION_BUTTONS);
        bus.d(1.0f, 1.0f, 1.0f);
        this.hoverUp = (mouseX > maxX - 15 && mouseX < maxX - 15 + 11 && mouseY > y + 2 && mouseY < y + 2 + 7);
        this.hoverDown = (mouseX > maxX - 15 && mouseX < maxX - 15 + 11 && mouseY > y + 12 && mouseY < y + 12 + 7);
        draw.drawTexture(maxX - 15, y + 2, 99.0, this.hoverUp ? 37.0 : 5.0, 11.0, 7.0, 11.0, 7.0);
        draw.drawTexture(maxX - 15, y + 12, 67.0, this.hoverDown ? 52.0 : 20.0, 11.0, 7.0, 11.0, 7.0);
        if (this.isMouseOver() && this.fastTickerCounterValue != 0L) {
            if (this.fastTickerCounterValue > 0L && this.fastTickerCounterValue + 80L < System.currentTimeMillis()) {
                this.fastTickerCounterValue = System.currentTimeMillis();
                if (this.currentValue < this.maxValue) {
                    this.currentValue += this.steps;
                    this.updateValue();
                }
            }
            if (this.fastTickerCounterValue < 0L && this.fastTickerCounterValue - 80L > System.currentTimeMillis() * -1L) {
                this.fastTickerCounterValue = System.currentTimeMillis() * -1L;
                if (this.currentValue > this.minValue) {
                    this.currentValue -= this.steps;
                    this.updateValue();
                }
            }
        }
        else {
            this.mouseRelease(mouseX, mouseY, 0);
        }
    }
    
    public void unfocus(final int mouseX, final int mouseY, final int mouseButton) {
        super.unfocus(mouseX, mouseY, mouseButton);
        this.textField.b(false);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.hoverUp && this.currentValue < this.maxValue) {
            this.currentValue += this.steps;
            this.updateValue();
            this.fastTickerCounterValue = System.currentTimeMillis() + 500L;
        }
        if (this.hoverDown && this.currentValue > this.minValue) {
            this.currentValue -= this.steps;
            this.updateValue();
            this.fastTickerCounterValue = System.currentTimeMillis() * -1L - 500L;
        }
        if (this.currentValue > this.maxValue) {
            this.currentValue = this.maxValue;
            this.updateValue();
        }
        if (this.currentValue < this.minValue) {
            this.currentValue = this.minValue;
            this.updateValue();
        }
        this.textField.a(mouseX, mouseY, 0);
    }
    
    public void mouseRelease(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseRelease(mouseX, mouseY, mouseButton);
        if (this.fastTickerCounterValue != 0L) {
            this.fastTickerCounterValue = 0L;
            this.changeListener.accept(this.currentValue);
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        final int preNumber = this.textField.b().isEmpty() ? this.minValue : Integer.valueOf(this.textField.b());
        if (this.textField.a(typedChar, keyCode)) {
            String numericCheck;
            final String currentText = numericCheck = this.textField.b();
            if (numericCheck.startsWith("-")) {
                numericCheck = numericCheck.replaceFirst("-", "");
            }
            final boolean numeric = currentText.isEmpty() || StringUtils.isNumeric((CharSequence)numericCheck);
            int newNumber = 0;
            try {
                newNumber = ((currentText.isEmpty() || !numeric) ? this.minValue : Integer.valueOf(currentText.isEmpty() ? String.valueOf(this.minValue) : currentText));
                if (!numeric) {
                    newNumber = preNumber;
                }
                if (newNumber > this.maxValue) {
                    newNumber = this.maxValue;
                }
                if (newNumber < this.minValue) {
                    newNumber = this.minValue;
                }
            }
            catch (NumberFormatException e) {
                newNumber = this.maxValue;
            }
            final String newText = String.valueOf(newNumber);
            if (!currentText.equals(newText)) {
                this.textField.a(String.valueOf(newNumber));
            }
            this.changeListener.accept(newNumber);
            this.currentValue = newNumber;
        }
    }
    
    public void updateScreen() {
        super.updateScreen();
        this.textField.a();
    }
    
    public bje getTextField() {
        return this.textField;
    }
    
    public NumberElement addCallback(final Consumer<Integer> callback) {
        this.callback = callback;
        return this;
    }
    
    public int getObjectWidth() {
        return 50;
    }
    
    public Integer getCurrentValue() {
        return this.currentValue;
    }
    
    static {
        SERVER_SELECTION_BUTTONS = new nf("textures/gui/server_selection.png");
    }
}
