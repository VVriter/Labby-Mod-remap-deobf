//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.main.*;
import net.labymod.api.*;
import net.labymod.ingamegui.*;
import net.labymod.utils.*;

public class SliderElement extends ControlElement
{
    public static final nf buttonTextures;
    private Integer currentValue;
    private Consumer<Integer> changeListener;
    private Consumer<Integer> callback;
    private int minValue;
    private int maxValue;
    private boolean dragging;
    private boolean hover;
    private int dragValue;
    private int steps;
    
    public SliderElement(final String displayName, final String configEntryName, final ControlElement.IconData iconData) {
        super(displayName, configEntryName, iconData);
        this.minValue = 0;
        this.maxValue = 10;
        this.steps = 1;
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
                    ModSettings.class.getDeclaredField(configEntryName).set(LabyMod.getSettings(), accepted);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (SliderElement.this.callback != null) {
                    SliderElement.this.callback.accept(accepted);
                }
            }
        };
    }
    
    public SliderElement(final String displayName, final ControlElement.IconData iconData, final int currentValue) {
        super(displayName, (String)null, iconData);
        this.minValue = 0;
        this.maxValue = 10;
        this.steps = 1;
        this.currentValue = currentValue;
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                if (SliderElement.this.callback != null) {
                    SliderElement.this.callback.accept(accepted);
                }
            }
        };
    }
    
    public SliderElement(final String displayName, final LabyModAddon addon, final ControlElement.IconData iconData, final String attribute, final int currentValue) {
        super(displayName, iconData);
        this.minValue = 0;
        this.maxValue = 10;
        this.steps = 1;
        this.currentValue = currentValue;
        this.changeListener = new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                addon.getConfig().addProperty(attribute, (Number)accepted);
                addon.loadConfig();
                if (SliderElement.this.callback != null) {
                    SliderElement.this.callback.accept(accepted);
                }
            }
        };
    }
    
    public SliderElement(final Module module, final ControlElement.IconData iconData, final String displayName, final String attribute) {
        super(module, iconData, displayName);
        this.minValue = 0;
        this.maxValue = 10;
        this.steps = 1;
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
                if (SliderElement.this.callback != null) {
                    SliderElement.this.callback.accept(accepted);
                }
            }
        };
    }
    
    public SliderElement(final String configEntryName, final ControlElement.IconData iconData) {
        this(configEntryName, configEntryName, iconData);
    }
    
    public SliderElement setMinValue(final int minValue) {
        this.minValue = minValue;
        if (this.currentValue < this.minValue) {
            this.currentValue = this.minValue;
        }
        return this;
    }
    
    public SliderElement setMaxValue(final int maxValue) {
        this.maxValue = maxValue;
        if (this.currentValue > this.maxValue) {
            this.currentValue = this.maxValue;
        }
        return this;
    }
    
    public SliderElement setRange(final int min, final int max) {
        this.setMinValue(min);
        this.setMaxValue(max);
        return this;
    }
    
    public SliderElement setSteps(final int steps) {
        this.steps = steps;
        return this;
    }
    
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int width = this.getObjectWidth();
        if (this.displayName != null) {
            draw.drawRectangle(x - 1, y, x, maxY, ModColor.toRGB(120, 120, 120, 120));
        }
        bib.z().N().a(SliderElement.buttonTextures);
        bus.d(1.0f, 1.0f, 1.0f);
        final double maxSliderPos = maxX;
        final double sliderWidth = width - 8;
        final double sliderWidthBackground = width;
        final double minSliderPos = maxSliderPos - width;
        final double totalValueDiff = this.maxValue - this.minValue;
        final double currentValue = this.currentValue;
        final double pos = minSliderPos + sliderWidth / totalValueDiff * (currentValue - this.minValue);
        draw.drawTexturedModalRect(minSliderPos, y + 1, 0.0, 46.0, sliderWidthBackground / 2.0, 20.0);
        draw.drawTexturedModalRect(minSliderPos + sliderWidthBackground / 2.0, y + 1, 200.0 - sliderWidthBackground / 2.0, 46.0, sliderWidthBackground / 2.0, 20.0);
        this.hover = (mouseX > x && mouseX < maxX && mouseY > y + 1 && mouseY < maxY);
        draw.drawTexturedModalRect(pos, y + 1, 0.0, 66.0, 4.0, 20.0);
        draw.drawTexturedModalRect(pos + 4.0, y + 1, 196.0, 66.0, 4.0, 20.0);
        if (!this.isMouseOver()) {
            this.mouseRelease(mouseX, mouseY, 0);
        }
        else {
            final double mouseToMinSlider = mouseX - minSliderPos;
            final double finalValue = this.minValue + totalValueDiff / sliderWidth * (mouseToMinSlider - 1.0);
            if (this.dragging) {
                this.dragValue = (int)finalValue;
                this.mouseClickMove(mouseX, mouseY, 0);
            }
        }
        draw.drawCenteredString("" + this.currentValue, minSliderPos + sliderWidthBackground / 2.0, y + 7);
    }
    
    public void unfocus(final int mouseX, final int mouseY, final int mouseButton) {
        super.unfocus(mouseX, mouseY, mouseButton);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.hover) {
            this.dragging = true;
        }
    }
    
    public void mouseRelease(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseRelease(mouseX, mouseY, mouseButton);
        if (this.dragging) {
            this.dragging = false;
            this.currentValue = (int)(this.dragValue / (double)this.steps) * this.steps;
            if (this.currentValue > this.maxValue) {
                this.currentValue = this.maxValue;
            }
            if (this.currentValue < this.minValue) {
                this.currentValue = this.minValue;
            }
            this.changeListener.accept(this.currentValue);
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClickMove(mouseX, mouseY, mouseButton);
        if (this.dragging) {
            this.currentValue = (int)Math.round(this.dragValue / (double)this.steps * this.steps);
            if (this.currentValue > this.maxValue) {
                this.currentValue = this.maxValue;
            }
            if (this.currentValue < this.minValue) {
                this.currentValue = this.minValue;
            }
            this.changeListener.accept(this.currentValue);
        }
    }
    
    public SliderElement addCallback(final Consumer<Integer> callback) {
        this.callback = callback;
        return this;
    }
    
    public int getObjectWidth() {
        return 50;
    }
    
    public void setCurrentValue(final Integer currentValue) {
        this.currentValue = currentValue;
    }
    
    static {
        buttonTextures = new nf("textures/gui/widgets.png");
    }
}
