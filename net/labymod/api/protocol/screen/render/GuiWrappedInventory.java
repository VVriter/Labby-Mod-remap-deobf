//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen.render;

import net.labymod.api.protocol.screen.render.util.*;
import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.*;
import net.labymod.serverapi.common.widgets.*;
import net.labymod.core.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.utils.manager.*;
import java.util.*;

public class GuiWrappedInventory extends GuiScreenProtocol
{
    protected final bmg screen;
    protected final afr container;
    protected final int id;
    protected final IInteractionCallback callback;
    protected final List<RenderWidget<? extends Widget>> widgets;
    private final int slotWidth;
    private final int slotHeight;
    private final int slotMarginX;
    private final int slotMarginY;
    private final int borderPaddingX;
    private final int borderPaddingY;
    private final double fontSize;
    private int lastHoveredSlotIndex;
    private int lastLabelShiftY;
    private boolean canRender;
    
    public GuiWrappedInventory(final bmg containerScreen, final int id, final IInteractionCallback callback, final List<RenderWidget<? extends Widget>> widgets, final WidgetLayout layout) {
        super((blk)null, id, callback, (List)widgets);
        this.lastHoveredSlotIndex = -1;
        this.canRender = false;
        this.screen = containerScreen;
        this.container = containerScreen.h;
        this.id = id;
        this.callback = callback;
        this.widgets = widgets;
        this.slotWidth = layout.getSlotWidth();
        this.slotHeight = layout.getSlotHeight();
        this.slotMarginX = layout.getSlotMarginX();
        this.slotMarginY = layout.getSlotMarginY();
        this.borderPaddingX = layout.getBorderPaddingX();
        this.borderPaddingY = layout.getBorderPaddingY();
        this.fontSize = layout.getFontSize();
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        final List<agr> slots = (List<agr>)this.container.c;
        final int amount = slots.size() - 36;
        final int columns = 9;
        final int rows = amount / columns;
        final double totalWidth = columns * (this.slotWidth + this.slotMarginX) - this.slotMarginX;
        final double totalHeight = rows * (this.slotHeight + this.slotMarginY) - this.slotMarginY + this.lastLabelShiftY;
        final double left = this.l / 2.0 - totalWidth / 2.0 - this.borderPaddingX;
        final double top = this.m / 2.0 - totalHeight / 2.0 - this.borderPaddingY;
        final double right = this.l / 2.0 + totalWidth / 2.0 + this.borderPaddingX;
        final double bottom = this.m / 2.0 + totalHeight / 2.0 + this.borderPaddingY;
        if (this.canRender) {
            this.renderBackground(left, top, right, bottom);
        }
        this.lastHoveredSlotIndex = -1;
        int labelShiftY = 0;
        int highestSlotInRow = 0;
        boolean emptyRow = true;
        try {
            for (int i = 0; i < amount; ++i) {
                final agr slot = slots.get(i);
                if (i % columns == 0) {
                    if (emptyRow) {
                        labelShiftY += highestSlotInRow;
                    }
                    else {
                        emptyRow = true;
                        labelShiftY -= this.slotHeight - highestSlotInRow;
                    }
                    highestSlotInRow = 0;
                }
                final boolean stackEmpty = LabyModCore.getMinecraft().isItemStackEmpty(slot.d());
                if (!stackEmpty) {
                    emptyRow = false;
                }
                final double offsetX = this.l / 2.0 - totalWidth / 2.0;
                final double offsetY = this.m / 2.0 - totalHeight / 2.0;
                final double x = i % columns * (this.slotWidth + this.slotMarginX) + offsetX;
                final double y = i / columns * (this.slotHeight + this.slotMarginY) + offsetY + labelShiftY;
                final boolean mouseOver = mouseX >= x && mouseX <= x + this.slotWidth && mouseY >= y && mouseY <= y + this.slotHeight;
                if (mouseOver && !stackEmpty) {
                    this.lastHoveredSlotIndex = i;
                }
                final int slotHeight = this.renderWrappedWidget(slot, x, y, mouseOver, mouseX, mouseY);
                if (slotHeight > highestSlotInRow) {
                    highestSlotInRow = slotHeight;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (this.canRender) {}
        this.lastLabelShiftY = labelShiftY + highestSlotInRow;
        this.canRender = true;
    }
    
    protected void a(final int mouseX, final int mouseY, final int button) throws IOException {
        super.a(mouseX, mouseY, button);
        if (this.lastHoveredSlotIndex != -1) {
            final bib minecraft = bib.z();
            if (minecraft.c != null && LabyModCore.getMinecraft().getPlayer() != null) {
                LabyModCore.getMinecraft().windowClick(this.container.d, this.lastHoveredSlotIndex, button, 0, LabyModCore.getMinecraft().getPlayer());
            }
        }
        super.a(mouseX, mouseY, button);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        if (bib.z().t.aa.j() == keyCode) {
            this.closeProperly();
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    private void renderBackground(final double left, final double top, final double right, final double bottom) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawRect(left + 5.0, top + 5.0, right - 5.0, bottom - 5.0, ModColor.toRGB(0, 0, 0, 200));
        bib.z().N().a(ModTextures.GUI_WRAPPER_INVENTORY);
        final double texWidth = 32.0;
        final double texHeight = 1.3689839572192513 * texWidth;
        draw.drawTexture(left, top, texWidth, texHeight, texWidth, texWidth);
        draw.drawTexture(right - texWidth, top, 256.0 - texWidth, 0.0, texWidth, texHeight, texWidth, texWidth);
        draw.drawTexture(left, bottom - texWidth, 0.0, 256.0 - texHeight, texWidth, texHeight, texWidth, texWidth);
        draw.drawTexture(right - texWidth, bottom - texWidth, 256.0 - texWidth, 256.0 - texHeight, texWidth, texHeight, texWidth, texWidth);
        draw.drawTexture(left + texWidth, top, texWidth, 0.0, 1.0, texHeight, right - left - texWidth * 2.0, texWidth);
        draw.drawTexture(left + texWidth, bottom - texWidth, texWidth, 256.0 - texHeight, 1.0, texHeight, right - left - texWidth * 2.0, texWidth);
        draw.drawTexture(left, top + texWidth, 0.0, texHeight, texWidth, texHeight, texWidth, bottom - top - texWidth * 2.0);
        draw.drawTexture(right - texWidth, top + texWidth, 256.0 - texWidth, texHeight, texWidth, texHeight, texWidth, bottom - top - texWidth * 2.0);
    }
    
    private int renderWrappedWidget(final agr slot, final double x, final double y, final boolean mouseOver, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final double hover = mouseOver ? 3.0 : 0.0;
        final aip itemStack = (slot == null) ? null : slot.d();
        String url = null;
        final fy tag = (itemStack == null) ? null : itemStack.p();
        if (tag != null) {
            if (tag.e("CustomImageUrl")) {
                url = tag.l("CustomImageUrl");
            }
            if (tag.e("PublicBukkitValues")) {
                final fy publicBukkitValues = tag.p("PublicBukkitValues");
                if (publicBukkitValues.e("labymod3:customimageurl")) {
                    url = publicBukkitValues.l("labymod3:customimageurl");
                }
            }
        }
        if (this.canRender) {
            if (url == null) {
                this.renderItem(slot.d(), x - hover, y - hover, this.slotWidth + hover * 2.0, this.slotHeight + hover * 2.0);
            }
            else {
                final nf resourceLocation = LabyMod.getInstance().getDynamicTextureManager().getTexture("screen_image_widget_" + url.hashCode(), url);
                bib.z().N().a(resourceLocation);
                draw.drawTexture(x - hover, y - hover, 256.0, 256.0, this.slotWidth + hover * 2.0, this.slotHeight + hover * 2.0);
            }
        }
        if (!LabyModCore.getMinecraft().isItemStackEmpty(slot.d())) {
            final aip stack = slot.d();
            final String title = stack.r();
            final List<String> list = draw.listFormattedStringToWidth(title, (int)((this.slotWidth + this.slotMarginX) / this.fontSize));
            int listY = 0;
            for (final String line : list) {
                if (this.canRender) {
                    draw.drawCenteredString(line, (float)(x + this.slotWidth / 2.0f), (float)(y + this.slotHeight + 3.0) + listY, (float)this.fontSize);
                }
                listY += (int)(this.fontSize * 10.0);
            }
            if (this.canRender && mouseOver) {
                final List<String> tooltip = LabyModCore.getMinecraft().getTooltip(stack, LabyModCore.getMinecraft().getPlayer());
                final String[] processors = new String[tooltip.size()];
                tooltip.toArray(processors);
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, processors);
            }
            return listY + 2;
        }
        return 0;
    }
    
    private void renderItem(final aip itemStack, final double x, final double y, final double width, final double height) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final double scale = 0.05 * width;
        bus.G();
        bus.k();
        bus.a(scale, scale, scale);
        if (itemStack != null) {
            draw.drawItem(itemStack, (x - 3.0 + width / 2.0 - scale * 6.0) / scale, (y - 3.0 + height / 2.0 - scale * 6.0) / scale, "");
        }
        bus.H();
    }
}
