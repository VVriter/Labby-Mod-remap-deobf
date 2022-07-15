//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.moduletypes;

import net.labymod.ingamegui.enums.*;
import net.labymod.core.*;
import net.labymod.ingamegui.*;

public abstract class ResizeableModule extends Module
{
    private double defaultWidth;
    private double defaultHeight;
    public double minWidth;
    public double minHeight;
    public double maxWidth;
    public double maxHeight;
    public double[] width;
    public double[] height;
    private boolean moving;
    protected boolean mouseOverVertical;
    protected boolean mouseOverHorizontal;
    private boolean mouseOverFirst;
    private double clickX;
    private double clickY;
    private boolean widthIsEqualToHeight;
    private ModuleGui.CoordinatesConsumer mouseClickListener;
    private ModuleGui.CoordinatesConsumer mouseMoveListener;
    private ModuleGui.CoordinatesConsumer mouseReleaseListener;
    
    private void onMouseMove(final int mouseX, final int mouseY, final int mouseButton, final EnumDisplayType displayType) {
        if (mouseButton != 0) {
            return;
        }
        if (!this.moving) {
            return;
        }
        if (this.mouseOverHorizontal) {
            final short x = (short)mouseX;
            if (this.mouseOverFirst) {
                final short newWidth = (short)(this.clickX - x);
                if (newWidth >= this.minWidth && newWidth <= this.maxWidth) {
                    this.width[Module.getLastDrawnDisplayType().ordinal()] = newWidth;
                    if (!this.isRightBound(displayType)) {
                        this.updateCoordinate(true, this.mouseOverFirst, x, displayType, true);
                    }
                    if (this.widthIsEqualToHeight) {
                        this.height = this.width;
                    }
                }
            }
            else {
                final short newWidth = (short)(x - this.getLastX());
                if (newWidth >= this.minWidth && newWidth <= this.maxWidth) {
                    this.width[Module.getLastDrawnDisplayType().ordinal()] = newWidth;
                    if (this.isRightBound(displayType)) {
                        this.updateCoordinate(true, this.mouseOverFirst, x, displayType, true);
                    }
                    if (this.widthIsEqualToHeight) {
                        this.height = this.width;
                    }
                }
            }
        }
        else {
            final short y = (short)mouseY;
            if (this.mouseOverFirst) {
                final short newHeight = (short)(this.clickY - y);
                if (newHeight >= this.minHeight && newHeight <= this.maxHeight) {
                    this.height[Module.getLastDrawnDisplayType().ordinal()] = newHeight;
                    this.updateCoordinate(false, this.mouseOverFirst, y, displayType, false);
                    if (this.widthIsEqualToHeight) {
                        this.width = this.height;
                    }
                }
            }
            else {
                final short newHeight = (short)(y - this.getLastY());
                if (newHeight >= this.minHeight && newHeight <= this.maxHeight) {
                    this.height[Module.getLastDrawnDisplayType().ordinal()] = newHeight;
                    if (this.widthIsEqualToHeight) {
                        this.width = this.height;
                    }
                }
            }
        }
    }
    
    @Deprecated
    private void updateCoordinate(final boolean horizontal, final boolean mouseOverFirst, final short value, final EnumDisplayType displayType) {
        this.updateCoordinate(horizontal, mouseOverFirst, value, displayType, true);
    }
    
    private void updateCoordinate(final boolean horizontal, final boolean mouseOverFirst, final short value, final EnumDisplayType displayType, final boolean updateRegion) {
        EnumModuleRegion newRegion = this.getRegion(displayType.ordinal());
        for (final EnumModuleRegion regions : EnumModuleRegion.values()) {
            if (regions.isInArea(horizontal ? ((double)value) : this.getLastX(), horizontal ? this.getLastY() : ((double)value), Module.getLastLeft(), Module.getLastRight(), Module.getLastTop(), Module.getLastBottom())) {
                newRegion = regions;
                break;
            }
        }
        if (updateRegion && newRegion != this.getRegion(displayType.ordinal())) {
            this.setRegion(displayType.ordinal(), newRegion);
        }
        final double newValue = horizontal ? this.getRegion(displayType.ordinal()).getOffsetX((double)value, Module.getLastLeft(), Module.getLastRight()) : this.getRegion(displayType.ordinal()).getOffsetY((double)value, Module.getLastTop(), Module.getLastBottom());
        if (horizontal) {
            this.setX(displayType.ordinal(), newValue);
            if (mouseOverFirst && this.isRightBound(displayType)) {
                this.setX(displayType.ordinal(), this.getRegion(displayType.ordinal()).getOffsetX(value + this.getWidth(), Module.getLastLeft(), Module.getLastRight()));
            }
            if (!mouseOverFirst && !this.isRightBound(displayType)) {
                this.setX(displayType.ordinal(), this.getRegion(displayType.ordinal()).getOffsetX(value - this.getWidth(), Module.getLastLeft(), Module.getLastRight()));
            }
        }
        else {
            this.setY(displayType.ordinal(), newValue);
        }
    }
    
    public ResizeableModule(final short defaultWidth, final short defaultHeight, final short minWidth, final short minHeight, final short maxWidth, final short maxHeight) {
        this(defaultWidth, defaultHeight, minWidth, minHeight, maxWidth, maxHeight, false);
    }
    
    public ResizeableModule(final short defaultWidth, final short defaultHeight, final short minWidth, final short minHeight, final short maxWidth, final short maxHeight, final boolean widthIsEqualToHeight) {
        this.clickX = -1.0;
        this.clickY = -1.0;
        this.mouseClickListener = (ModuleGui.CoordinatesConsumer)new ModuleGui.CoordinatesConsumer() {
            public void accept(final int mouseX, final int mouseY, final int mouseButton, final EnumDisplayType displayType) {
                if (mouseButton != 0) {
                    return;
                }
                if (!ResizeableModule.this.isEnabled(displayType) || ResizeableModule.this.moving) {
                    return;
                }
                if (!ResizeableModule.this.mouseOverHorizontal && !ResizeableModule.this.mouseOverVertical) {
                    return;
                }
                ResizeableModule.this.clickX = mouseX + ResizeableModule.this.width[Module.getLastDrawnDisplayType().ordinal()];
                ResizeableModule.this.clickY = mouseY + ResizeableModule.this.height[Module.getLastDrawnDisplayType().ordinal()];
                ResizeableModule.this.moving = true;
            }
        };
        this.mouseMoveListener = (ModuleGui.CoordinatesConsumer)new ModuleGui.CoordinatesConsumer() {
            public void accept(final int mouseX, final int mouseY, final int mouseButton, final EnumDisplayType displayType) {
                ResizeableModule.this.onMouseMove(mouseX, mouseY, mouseButton, displayType);
            }
        };
        this.mouseReleaseListener = (ModuleGui.CoordinatesConsumer)new ModuleGui.CoordinatesConsumer() {
            public void accept(final int x, final int y, final int mouseButton, final EnumDisplayType displayType) {
                if (mouseButton != 0) {
                    return;
                }
                if (!ResizeableModule.this.moving) {
                    return;
                }
                ResizeableModule.this.setAttribute("width", ResizeableModule.this.getStringByArray(ResizeableModule.this.width));
                ResizeableModule.this.setAttribute("height", ResizeableModule.this.getStringByArray(ResizeableModule.this.height));
                ResizeableModule.this.getModuleConfigElement().setX(displayType.ordinal(), ResizeableModule.this.getX(displayType.ordinal()));
                ResizeableModule.this.getModuleConfigElement().setY(displayType.ordinal(), ResizeableModule.this.getY(displayType.ordinal()));
                ResizeableModule.this.getModuleConfigElement().setRegion(displayType.ordinal(), ResizeableModule.this.getRegion(displayType.ordinal()));
                ResizeableModule.this.getModuleConfigElement().setAttributes(ResizeableModule.this.getAttributes());
                ResizeableModule.this.moving = false;
            }
        };
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.widthIsEqualToHeight = widthIsEqualToHeight;
    }
    
    public void init() {
        final double[] width = new double[EnumDisplayType.values().length];
        final double[] height = new double[EnumDisplayType.values().length];
        String defaultWidthValue = "";
        String defaultHeightValue = "";
        for (int i = 0; i < EnumDisplayType.values().length; ++i) {
            defaultWidthValue = defaultWidthValue + (defaultWidthValue.equals("") ? "" : ",") + this.defaultWidth;
            defaultHeightValue = defaultHeightValue + (defaultHeightValue.equals("") ? "" : ",") + this.defaultHeight;
        }
        final String widthAttribute = this.getAttribute("width", defaultWidthValue);
        final String heightAttribute = this.getAttribute("height", defaultHeightValue);
        int j = 0;
        for (final String widthSplit : widthAttribute.split(",")) {
            width[j] = Math.min(this.maxWidth, Math.max(this.minWidth, Double.parseDouble(widthSplit)));
            ++j;
        }
        j = 0;
        for (final String heightSplit : heightAttribute.split(",")) {
            height[j] = Math.min(this.maxHeight, Math.max(this.minHeight, Double.parseDouble(heightSplit)));
            ++j;
        }
        this.width = width;
        this.height = height;
        super.init();
    }
    
    public boolean isMovable(final int mouseX, final int mouseY) {
        return !this.mouseOverHorizontal && !this.mouseOverVertical;
    }
    
    @Deprecated
    public void draw(final int x, final int y, final int rightX) {
        this.draw(x, y, (double)rightX);
    }
    
    public void draw(final double x, final double y, final double rightX) {
        final boolean hasOverlistedModule = this.getListedAfter() != null && Module.getLastDrawnDisplayType() != EnumDisplayType.ESCAPE;
        final boolean hasUnderlistedModule = Module.getModulesByOverlistedModules().containsKey(this.getName()) && Module.getLastDrawnDisplayType() != EnumDisplayType.ESCAPE;
        final double firstOffset = 0.0;
        final double secondOffset = hasUnderlistedModule ? 4.0 : 0.0;
        final double moduleWidth = this.getWidth();
        final double moduleHeight = this.getHeight();
        double mouseX = (Module.getCurrentModuleGui() == null) ? 0.0 : Module.getCurrentModuleGui().getMouseX();
        double mouseY = (Module.getCurrentModuleGui() == null) ? 0.0 : Module.getCurrentModuleGui().getMouseY();
        final double mouseXRelative = mouseX - x;
        final double mouseYRelative = mouseY - y;
        this.drawModule(x, y + firstOffset, rightX, moduleWidth, moduleHeight - secondOffset, mouseXRelative, mouseYRelative);
        final ModuleGui moduleGui = Module.getCurrentModuleGui();
        if (moduleGui == null) {
            return;
        }
        mouseX = moduleGui.getMouseX();
        mouseY = moduleGui.getMouseY();
        final boolean isMovable = !hasOverlistedModule && !hasUnderlistedModule;
        if (Module.getCurrentModuleGui() != null && Module.getCurrentModuleGui().isMovableModules() && mouseY > y && mouseY < y + this.getHeight() && ((!this.moving && mouseX > x - 5.0 && mouseX < x) || (!this.moving && mouseX > x + this.getWidth() && mouseX < x + this.getWidth() + 5.0) || (this.moving && this.mouseOverHorizontal))) {
            if ((this.minWidth != this.maxWidth || this.minWidth != this.defaultWidth) && Module.getCurrentModuleGui().isMovableModules()) {
                LabyModCore.getMinecraft().getFontRenderer().a("|||", (int)(mouseX - 2.0), (int)(mouseY - 2.0), 16777215);
                this.mouseOverHorizontal = true;
                this.mouseOverVertical = false;
            }
            if (!this.moving) {
                this.mouseOverFirst = (mouseX < x && !hasOverlistedModule);
            }
            return;
        }
        if (isMovable && mouseX > x && mouseX < x + this.getWidth() && ((!this.moving && mouseY > y - 5.0 && mouseY < y) || (!this.moving && mouseY > y + this.getHeight() && mouseY < y + this.getHeight() + 5.0) || (this.moving && !this.mouseOverHorizontal))) {
            if ((this.minHeight != this.maxHeight || this.minHeight != this.defaultHeight) && Module.getCurrentModuleGui().isMovableModules()) {
                LabyModCore.getMinecraft().getFontRenderer().a("==", (int)(mouseX - 5.0), (int)(mouseY - 3.0), 16777215);
                this.mouseOverHorizontal = false;
                this.mouseOverVertical = true;
            }
            if (!this.moving) {
                this.mouseOverFirst = (mouseY < y && !hasOverlistedModule);
            }
            return;
        }
        if (!this.moving) {
            this.mouseOverHorizontal = false;
            this.mouseOverVertical = false;
            this.mouseOverFirst = false;
        }
    }
    
    @Deprecated
    public void drawModule(final int x, final int y, final int rightX, final int width, final int height, final int mouseX, final int mouseY) {
    }
    
    public void drawModule(final double x, final double y, final double rightX, final double width, final double height, final double mouseX, final double mouseY) {
        this.drawModule((int)Math.round(x), (int)Math.round(y), (int)Math.round(rightX), (int)Math.round(width), (int)Math.round(height), (int)Math.round(mouseX), (int)Math.round(mouseY));
    }
    
    public void initModuleGui(final ModuleGui moduleGui) {
        if (!moduleGui.isMovableModules()) {
            return;
        }
        if (!moduleGui.getMouseClickListeners().contains(this.mouseClickListener)) {
            moduleGui.getMouseClickListeners().add(this.mouseClickListener);
            moduleGui.getMouseMoveListeners().add(this.mouseMoveListener);
            moduleGui.getMouseReleaseListeners().add(this.mouseReleaseListener);
        }
    }
    
    public double getHeight() {
        final boolean hasUnderlistedModule = Module.getModulesByOverlistedModules().containsKey(this.getName()) && Module.getLastDrawnDisplayType() != EnumDisplayType.ESCAPE;
        return (short)this.height[Module.getLastDrawnDisplayType().ordinal()] + (hasUnderlistedModule ? 4 : 0);
    }
    
    protected int getSpacing() {
        return 0;
    }
    
    public double getWidth() {
        return (short)this.width[Module.getLastDrawnDisplayType().ordinal()];
    }
    
    public void onResize(final double width, final double height, final double prevWidth, final double prevHeight) {
        if (!this.isEnabled(EnumDisplayType.ESCAPE)) {
            return;
        }
        boolean changedConfig = false;
        final EnumModuleRegion region = this.getRegion(EnumDisplayType.ESCAPE.ordinal());
        double x = region.getMinecraftX(this.getX(EnumDisplayType.ESCAPE.ordinal()), 0.0, prevWidth);
        final double y = region.getMinecraftY(this.getY(EnumDisplayType.ESCAPE.ordinal()), 0.0, prevHeight);
        x = (this.isRightBound(EnumDisplayType.ESCAPE) ? (x - this.width[EnumDisplayType.ESCAPE.ordinal()]) : x);
        final double rightX = x + this.width[EnumDisplayType.ESCAPE.ordinal()];
        final double prevMaxY = y + this.height[EnumDisplayType.ESCAPE.ordinal()];
        final double leftButton = prevWidth / 2.0 - 100.0;
        final double rightButton = prevWidth / 2.0 + 100.0;
        if (x >= leftButton && x <= rightButton) {
            return;
        }
        double newPart = (width / 2.0 - 100.0) / 100.0;
        final boolean updateHeight = prevWidth != width && ((x < leftButton && rightX < leftButton) || x > rightButton);
        if (prevWidth != width && x < leftButton && rightX < leftButton) {
            final double marginLeft = 100.0 / leftButton * x;
            final double marginRight = 100.0 / leftButton * (leftButton - rightX);
            final double newX = newPart * marginLeft;
            this.setX(EnumDisplayType.ESCAPE.ordinal(), region.getOffsetX(newX, 0.0, width));
            final double newRightX = width / 2.0 - 100.0 - newPart * marginRight;
            this.width[EnumDisplayType.ESCAPE.ordinal()] = Math.max(this.minWidth, newRightX - newX);
            this.setAttribute("width", this.getStringByArray(this.width));
            this.getModuleConfigElement().setAttributes(this.getAttributes());
            this.getModuleConfigElement().setX(EnumDisplayType.ESCAPE.ordinal(), newX);
            changedConfig = true;
        }
        else if (prevWidth != width && x > rightButton) {
            final double marginLeft = 100.0 / leftButton * (x - rightButton);
            final double marginRight = 100.0 / leftButton * (prevWidth - rightX);
            final double newX = width / 2.0 + 100.0 + newPart * marginLeft;
            final double newRightX = width - newPart * marginRight;
            final double newWidth = Math.max(this.minWidth, newRightX - newX);
            this.setX(EnumDisplayType.ESCAPE.ordinal(), region.getOffsetX(this.isRightBound(EnumDisplayType.ESCAPE) ? newRightX : newX, 0.0, width));
            this.width[EnumDisplayType.ESCAPE.ordinal()] = newWidth;
            this.setAttribute("width", this.getStringByArray(this.width));
            this.getModuleConfigElement().setAttributes(this.getAttributes());
            this.getModuleConfigElement().setX(EnumDisplayType.ESCAPE.ordinal(), this.getX(EnumDisplayType.ESCAPE.ordinal()));
            changedConfig = true;
        }
        if (prevHeight != height && updateHeight) {
            newPart = height / 100.0;
            final double marginTop = 100.0 / prevHeight * y;
            final double marginBottom = 100.0 / prevHeight * (prevHeight - prevMaxY);
            final double newY = newPart * marginTop;
            final double maxY = height - newPart * marginBottom;
            this.setY(EnumDisplayType.ESCAPE.ordinal(), region.getOffsetY(newY, 0.0, height));
            this.height[EnumDisplayType.ESCAPE.ordinal()] = Math.min(this.maxHeight, Math.max(this.minWidth, maxY - newY));
            this.setAttribute("height", this.getStringByArray(this.height));
            this.getModuleConfigElement().setAttributes(this.getAttributes());
            this.getModuleConfigElement().setY(EnumDisplayType.ESCAPE.ordinal(), this.getY(EnumDisplayType.ESCAPE.ordinal()));
            changedConfig = true;
        }
        if (changedConfig) {
            ModuleConfig.getConfigManager().save();
        }
    }
    
    public void debugModuleSize() {
        System.out.println("[Module Debug] " + this.width + " " + this.height);
    }
    
    public String getStringByArray(final double[] array) {
        String returned = "";
        for (final double element : array) {
            returned = returned + (returned.equals("") ? "" : ",") + element;
        }
        return returned;
    }
    
    public boolean isMoving() {
        return this.moving;
    }
}
