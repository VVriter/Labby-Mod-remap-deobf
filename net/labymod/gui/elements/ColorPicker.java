//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import java.awt.*;
import net.labymod.main.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;
import net.labymod.settings.*;
import net.labymod.core.*;
import java.io.*;

public class ColorPicker extends bir
{
    private static final int[] ADVANCED_COLORS;
    private String title;
    private int x;
    private int y;
    private int width;
    private int height;
    private Color selectedColor;
    private Color colorForPreview;
    private boolean openedSelector;
    private boolean hoverSlider;
    private boolean hasAdvanced;
    private boolean hoverAdvancedButton;
    private boolean hoverDefaultButton;
    private boolean hasDefault;
    private boolean isDefault;
    private DefaultColorCallback defaultColor;
    private Consumer<Color> updateListener;
    
    public ColorPicker(final String title, final Color selectedColor, final DefaultColorCallback defaultColorCallback, final int x, final int y, final int width, final int height) {
        this.hoverSlider = false;
        this.hasAdvanced = false;
        this.hoverAdvancedButton = false;
        this.hasDefault = false;
        this.isDefault = true;
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.selectedColor = selectedColor;
        this.colorForPreview = selectedColor;
        this.defaultColor = defaultColorCallback;
    }
    
    public void onGuiClosed() {
        if (this.colorForPreview != this.selectedColor && this.updateListener != null) {
            this.updateListener.accept(this.selectedColor);
        }
    }
    
    public void drawColorPicker(final int mouseX, final int mouseY) {
        LabyMod.getInstance().getDrawUtils().drawCenteredString(this.title, this.x + this.width / 2, this.y - 5, 0.5);
        a(this.x, this.y, this.x + this.width, this.y + this.height, this.openedSelector ? -1 : Integer.MAX_VALUE);
        final int bgColor = (this.colorForPreview == null) ? ((this.defaultColor == null) ? (this.openedSelector ? Integer.MIN_VALUE : Integer.MAX_VALUE) : this.defaultColor.getDefaultColor().getRGB()) : this.colorForPreview.getRGB();
        a(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, bgColor);
        if (this.hasDefault && this.selectedColor == null && this.isDefault) {
            bib.z().N().a(ModTextures.BUTTON_HOVER_DEFAULT);
            final Color color = new Color(bgColor);
            int luma = (int)(0.2126f * color.getRed() + 0.7152f * color.getGreen() + 0.0722f * color.getBlue());
            luma = 255 - luma;
            if (luma < 80) {
                bus.c(luma / 255.0f, luma / 255.0f, luma / 255.0f, 1.0f);
                LabyMod.getInstance().getDrawUtils().drawTexture(this.x + 2, this.y + 2, 256.0, 256.0, this.width - 4, this.height - 4, 1.1f);
            }
            else {
                LabyMod.getInstance().getDrawUtils().drawTexture(this.x + 2, this.y + 2, 256.0, 256.0, this.width - 4, this.height - 4);
            }
            if (this.isMouseOver(mouseX, mouseY)) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 200L, "Default");
            }
        }
        if (this.openedSelector) {
            this.drawColorsAndButtons(mouseX, mouseY);
        }
    }
    
    private Color getContrastColor(final int r, final int g, final int b) {
        final double y = (299 * r + 587 * g + 114 * b) / 1000;
        return (y >= 128.0) ? Color.black : Color.white;
    }
    
    private void drawColorsAndButtons(final int mouseX, final int mouseY) {
        final int widthPerColor = 13;
        final int sliderHeight = 13;
        int sliderWidth = 0;
        for (final ModColor color : ModColor.values()) {
            if (color.getColor() != null) {
                sliderWidth += widthPerColor;
            }
        }
        int sliderX = this.x - sliderWidth / 2 + this.width / 2;
        int sliderY = this.y + this.height + 4;
        if (this.hasAdvanced) {
            sliderX -= 20;
        }
        final int minX = (this.hasDefault && this.selectedColor != null) ? 20 : 5;
        final int maxX = LabyMod.getInstance().getDrawUtils().getWidth() - 5;
        final int maxY = LabyMod.getInstance().getDrawUtils().getHeight() - 5;
        if (sliderX + sliderWidth > maxX) {
            sliderX -= sliderX + sliderWidth - maxX;
        }
        if (sliderX < minX) {
            sliderX = minX;
        }
        if (sliderY > maxY) {
            sliderY = maxY - sliderHeight - this.height;
        }
        else {
            a(this.x + this.width / 2 - 1, sliderY - 3, this.x + this.width / 2 + 1, sliderY - 2, Integer.MAX_VALUE);
            a(this.x + this.width / 2 - 2, sliderY - 2, this.x + this.width / 2 + 2, sliderY - 1, Integer.MAX_VALUE);
        }
        if (!(bib.z().m instanceof AdvancedColorSelectorGui)) {
            this.drawSlider(mouseX, mouseY, sliderX, sliderY, sliderWidth, sliderHeight, widthPerColor);
            this.drawButtons(mouseX, mouseY, sliderX, sliderY, sliderWidth, sliderHeight, widthPerColor);
        }
        this.hoverAdvancedButton = (mouseX > sliderX + sliderWidth + 3 - 1 && mouseX < sliderX + sliderWidth + 3 + widthPerColor + 1 && mouseY > sliderY - 1 && mouseY < sliderY + sliderHeight + 1);
        this.hoverSlider = (mouseX > sliderX && mouseX < sliderX + sliderWidth + widthPerColor && mouseY > sliderY && mouseY < sliderY + sliderHeight);
        this.hoverDefaultButton = (mouseX > sliderX - 3 - widthPerColor - 1 && mouseX < sliderX - 3 + 1 && mouseY > sliderY - 1 && mouseY < sliderY + sliderHeight + 1);
    }
    
    private void drawSlider(final int mouseX, final int mouseY, final int sliderX, final int sliderY, final int sliderWidth, final int sliderHeight, final int widthPerColor) {
        a(sliderX - 1, sliderY - 1, sliderX + sliderWidth + 1, sliderY + sliderHeight + 1, Integer.MAX_VALUE);
        int pos = 0;
        int hoverPos = -1;
        int selectedPos = -1;
        ModColor hoverColorType = null;
        ModColor selectedColorType = null;
        for (final ModColor color : ModColor.values()) {
            if (color.getColor() != null) {
                a(sliderX + pos, sliderY, sliderX + pos + widthPerColor, sliderY + sliderHeight, color.getColor().getRGB());
                final boolean hoverColor = mouseX > sliderX + pos && mouseX < sliderX + pos + widthPerColor + 1 && mouseY > sliderY && mouseY < sliderY + sliderHeight;
                if (hoverPos == -1 && hoverColorType == null && hoverColor) {
                    hoverPos = pos;
                    hoverColorType = color;
                }
                if (color.getColor() == this.selectedColor) {
                    selectedPos = pos;
                    selectedColorType = color;
                }
                pos += widthPerColor;
            }
        }
        if (hoverColorType != null) {
            a(sliderX + hoverPos - 1, sliderY - 1, sliderX + hoverPos + widthPerColor + 1, sliderY + sliderHeight + 1, hoverColorType.getColor().getRGB());
            this.colorForPreview = hoverColorType.getColor();
            if (this.updateListener != null) {
                this.updateListener.accept(this.colorForPreview);
            }
        }
        else {
            this.colorForPreview = this.selectedColor;
            if (this.updateListener != null) {
                this.updateListener.accept(this.selectedColor);
            }
        }
        if (selectedColorType != null) {
            a(sliderX + selectedPos - 1, sliderY - 1, sliderX + selectedPos + widthPerColor + 1, sliderY + sliderHeight + 1, -1);
            a(sliderX + selectedPos, sliderY, sliderX + selectedPos + widthPerColor, sliderY + sliderHeight, selectedColorType.getColor().getRGB());
        }
    }
    
    private void drawAdvanced(final int mouseX, final int mouseY, final int advancedX, final int advancedY, final int advancedWidth) {
        final int alphaCount = 12;
        final double heightPerColor;
        final double widthPerColor = heightPerColor = advancedWidth / (double)ColorPicker.ADVANCED_COLORS.length;
        a(advancedX - 1, advancedY - 1, (int)(advancedX + widthPerColor * ColorPicker.ADVANCED_COLORS.length + 1.0), (int)(advancedY + heightPerColor * alphaCount + 1.0), Integer.MAX_VALUE);
        double hoverPosX = -1.0;
        double hoverPosY = -1.0;
        Color hoveredColorType = null;
        double selectedPosX = -1.0;
        double selectedPosY = -1.0;
        Color selectedColorType = null;
        double posX = 0.0;
        for (final int color : ColorPicker.ADVANCED_COLORS) {
            for (int posY = 0; posY < alphaCount; ++posY) {
                final int rgb = ModColor.changeBrightness(new Color(color), 0.07f * posY).getRGB();
                a((int)(advancedX + posX), (int)(advancedY + posY * heightPerColor), (int)(advancedX + posX + widthPerColor), (int)(advancedY + posY * heightPerColor + heightPerColor), rgb);
                final boolean hoverColor = mouseX > advancedX + posX && mouseX < advancedX + posX + widthPerColor + 1.0 && mouseY > advancedY + posY * heightPerColor && mouseY < advancedY + posY * heightPerColor + heightPerColor + 1.0;
                if (hoverColor) {
                    hoverPosX = posX;
                    hoverPosY = posY;
                    hoveredColorType = new Color(rgb);
                }
                if (this.selectedColor != null && rgb == this.selectedColor.getRGB()) {
                    selectedPosX = posX;
                    selectedPosY = posY;
                    selectedColorType = this.selectedColor;
                }
            }
            posX += widthPerColor;
        }
        if (hoveredColorType != null) {
            a((int)(advancedX + hoverPosX - 1.0), (int)(advancedY + hoverPosY * heightPerColor - 1.0), (int)(advancedX + hoverPosX + widthPerColor + 1.0), (int)(advancedY + hoverPosY * heightPerColor + heightPerColor + 1.0), hoveredColorType.getRGB());
            this.colorForPreview = hoveredColorType;
            if (this.updateListener != null) {
                this.updateListener.accept(this.colorForPreview);
            }
        }
        else {
            this.colorForPreview = this.selectedColor;
            if (this.updateListener != null) {
                this.updateListener.accept(this.selectedColor);
            }
        }
        if (selectedColorType != null) {
            a((int)(advancedX + selectedPosX - 1.0), (int)(advancedY + selectedPosY * heightPerColor - 1.0), (int)(advancedX + selectedPosX + widthPerColor + 1.0), (int)(advancedY + selectedPosY * heightPerColor + heightPerColor + 1.0), -1);
            a((int)(advancedX + selectedPosX), (int)(advancedY + selectedPosY * heightPerColor), (int)(advancedX + selectedPosX + widthPerColor), (int)(advancedY + selectedPosY * heightPerColor + heightPerColor), selectedColorType.getRGB());
        }
    }
    
    private void drawButtons(final int mouseX, final int mouseY, final int sliderX, final int sliderY, final int sliderWidth, final int sliderHeight, final int widthPerColor) {
        if (this.hasDefault && this.selectedColor != null) {
            a(sliderX - 3 - widthPerColor - 1, sliderY - 1, sliderX - 3 + 1, sliderY + sliderHeight + 1, this.hoverDefaultButton ? -1 : Integer.MAX_VALUE);
            a(sliderX - 3 - widthPerColor, sliderY, sliderX - 3, sliderY + sliderHeight, Integer.MIN_VALUE);
            LabyMod.getInstance().getDrawUtils().fontRenderer.a("D", (float)(sliderX - 3 - widthPerColor / 2 - 3), (float)(sliderY + sliderHeight / 2 - 3), -1, false);
            if (this.hoverDefaultButton) {
                this.updateListener.accept(null);
                this.colorForPreview = this.defaultColor.getDefaultColor();
            }
        }
        if (this.hasAdvanced) {
            a(sliderX + sliderWidth + 3 - 1, sliderY - 1, sliderX + sliderWidth + 3 + widthPerColor + 1, sliderY + sliderHeight + 1, this.hoverAdvancedButton ? -1 : Integer.MAX_VALUE);
            a(sliderX + sliderWidth + 3, sliderY, sliderX + sliderWidth + 3 + widthPerColor, sliderY + sliderHeight, -1);
            final int iconX = sliderX + sliderWidth + 3;
            final int iconY = sliderY;
            double pxlX = iconX;
            for (final int color : ColorPicker.ADVANCED_COLORS) {
                int pxlY = iconY;
                for (int i = 0; i < 13; ++i) {
                    final Color theColor = new Color(color + i * 2000);
                    final int rgb = ModColor.toRGB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), 255 - i * 18);
                    a((int)pxlX, pxlY, (int)pxlX + 1, pxlY + 1, rgb);
                    ++pxlY;
                }
                pxlX += 0.7;
            }
            if (this.hoverAdvancedButton) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, "More colors");
            }
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            this.openedSelector = !this.openedSelector;
            return true;
        }
        if (this.openedSelector) {
            if (this.hoverSlider) {
                this.selectedColor = this.colorForPreview;
                if (this.updateListener != null) {
                    this.updateListener.accept(this.selectedColor);
                }
            }
            if (this.hasDefault && this.selectedColor != null && this.hoverDefaultButton) {
                this.selectedColor = null;
                this.colorForPreview = this.defaultColor.getDefaultColor();
                if (this.updateListener != null) {
                    this.updateListener.accept(this.selectedColor);
                }
                return true;
            }
            if (this.hasAdvanced && this.hoverAdvancedButton) {
                bib.z().a((blk)new AdvancedColorSelectorGui(this, bib.z().m, new Consumer<blk>() {
                    @Override
                    public void accept(final blk lastScreen) {
                        bib.z().a(lastScreen);
                    }
                }));
                return true;
            }
        }
        final boolean flag = this.openedSelector;
        this.openedSelector = false;
        return flag != this.openedSelector;
    }
    
    public boolean mouseDragging(final int mouseX, final int mouseY, final int mouseButton) {
        return false;
    }
    
    public boolean mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        return false;
    }
    
    public void setSelectedColor(final Color selectedColor) {
        this.selectedColor = selectedColor;
        this.colorForPreview = selectedColor;
    }
    
    public boolean isHoverAdvancedButton() {
        return this.hoverAdvancedButton;
    }
    
    public boolean isHoverDefaultButton() {
        return this.hoverDefaultButton;
    }
    
    public boolean isHoverSlider() {
        return this.hoverSlider;
    }
    
    public Color getSelectedColor() {
        return this.selectedColor;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public boolean isMouseOver(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public Color getColorForPreview() {
        return this.colorForPreview;
    }
    
    public void setHasDefault(final boolean hasDefault) {
        this.hasDefault = hasDefault;
    }
    
    public void setDefault(final boolean aDefault) {
        this.isDefault = aDefault;
    }
    
    public void setHasAdvanced(final boolean hasAdvanced) {
        this.hasAdvanced = hasAdvanced;
    }
    
    public void setUpdateListener(final Consumer<Color> updateListener) {
        this.updateListener = updateListener;
    }
    
    static {
        ADVANCED_COLORS = new int[] { -4842468, -7795121, -11922292, -13624430, -15064194, -15841375, -16754788, -16687004, -16757697, -14918112, -13407970, -8292586, -753898, -37120, -1683200, -4246004, -12704222, -14606047, -14208456 };
    }
    
    public class AdvancedColorSelectorGui extends blk
    {
        private blk backgroundScreen;
        private Consumer<blk> callback;
        private ColorPicker colorPicker;
        private bje fieldHexColor;
        private Color lastColor;
        private boolean validHex;
        
        public AdvancedColorSelectorGui(final ColorPicker colorPicker, final blk backgroundScreen, final Consumer<blk> callback) {
            this.lastColor = null;
            this.validHex = true;
            this.backgroundScreen = backgroundScreen;
            this.callback = callback;
            this.colorPicker = colorPicker;
        }
        
        public void b() {
            super.b();
            this.backgroundScreen.l = this.l;
            this.backgroundScreen.m = this.m;
            if (this.backgroundScreen instanceof LabyModModuleEditorGui) {
                PreviewRenderer.getInstance().init(AdvancedColorSelectorGui.class);
            }
            (this.fieldHexColor = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 70, this.m / 4 + 115, 100, 16)).f(7);
            this.lastColor = null;
            this.n.add(new bja(1, this.l / 2 + 40, this.m / 4 + 113, 60, 20, "Done"));
        }
        
        public void m() {
            this.backgroundScreen.m();
            super.m();
        }
        
        public void a(final int mouseX, final int mouseY, final float partialTicks) {
            this.backgroundScreen.a(mouseX, mouseY, partialTicks);
            a(0, 0, this.l, this.m, Integer.MIN_VALUE);
            this.colorPicker.drawColorPicker(mouseX, mouseY);
            a(this.l / 2 - 105, this.m / 4 - 25, this.l / 2 + 105, this.m / 4 + 140, Integer.MIN_VALUE);
            this.colorPicker.drawAdvanced(mouseX, mouseY, this.l / 2 - 100, this.m / 4 - 20, 200);
            a(LabyModCore.getMinecraft().getXPosition(this.fieldHexColor) - 2, LabyModCore.getMinecraft().getYPosition(this.fieldHexColor) - 2, LabyModCore.getMinecraft().getXPosition(this.fieldHexColor) + 100 + 2, LabyModCore.getMinecraft().getYPosition(this.fieldHexColor) + 16 + 2, this.validHex ? ModColor.toRGB(85, 255, 85, 100) : ModColor.toRGB(255, 85, 85, 100));
            this.fieldHexColor.g();
            if (this.colorPicker.colorForPreview == null) {
                this.colorPicker.colorForPreview = this.colorPicker.defaultColor.getDefaultColor();
            }
            a(this.l / 2 - 100, this.m / 4 + 113, this.l / 2 - 100 + 20, this.m / 4 + 113 + 20, Integer.MAX_VALUE);
            a(this.l / 2 - 100 + 1, this.m / 4 + 113 + 1, this.l / 2 - 100 + 20 - 1, this.m / 4 + 113 + 20 - 1, this.colorPicker.colorForPreview.getRGB());
            if (this.lastColor == null || !this.lastColor.equals(this.colorPicker.colorForPreview)) {
                this.lastColor = this.colorPicker.colorForPreview;
                final String hex = String.format("#%02x%02x%02x", this.lastColor.getRed(), this.lastColor.getGreen(), this.lastColor.getBlue());
                this.fieldHexColor.a(hex);
                this.validHex = true;
            }
            this.a(LabyModCore.getMinecraft().getFontRenderer(), "Advanced colors", this.l / 2, this.m / 4 - 35, -1);
            super.a(mouseX, mouseY, partialTicks);
        }
        
        protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
            super.a(mouseX, mouseY, mouseButton);
            this.colorPicker.selectedColor = this.colorPicker.colorForPreview;
            if (this.colorPicker.updateListener != null) {
                this.colorPicker.updateListener.accept(this.colorPicker.selectedColor);
            }
            this.fieldHexColor.a(mouseX, mouseY, mouseButton);
        }
        
        protected void a(final char typedChar, final int keyCode) throws IOException {
            if (keyCode == 1) {
                bib.z().a(this.backgroundScreen);
            }
            if (this.fieldHexColor.a(typedChar, keyCode)) {
                final String hex = this.fieldHexColor.b();
                if (hex.length() == 7) {
                    try {
                        this.colorPicker.selectedColor = new Color(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16));
                        this.colorPicker.colorForPreview = this.colorPicker.selectedColor;
                        this.validHex = true;
                    }
                    catch (Exception error) {
                        this.validHex = false;
                    }
                }
                else {
                    this.validHex = false;
                }
            }
        }
        
        public void e() {
            this.backgroundScreen.e();
            this.fieldHexColor.a();
        }
        
        protected void a(final bja button) throws IOException {
            super.a(button);
            if (button.k == 1) {
                this.colorPicker.openedSelector = false;
                bib.z().a(this.backgroundScreen);
            }
        }
        
        public blk getBackgroundScreen() {
            return this.backgroundScreen;
        }
    }
    
    public interface DefaultColorCallback
    {
        Color getDefaultColor();
    }
}
