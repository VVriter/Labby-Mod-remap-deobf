//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import java.awt.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.core.*;
import java.util.*;

public class DropDownMenu<T> extends bir
{
    private static final DropDownEntryDrawer defaultDrawer;
    private String title;
    private T selected;
    private boolean enabled;
    private boolean open;
    private T hoverSelected;
    private int x;
    private int y;
    private int width;
    private int height;
    private int maxY;
    private ArrayList<T> list;
    private DropDownEntryDrawer entryDrawer;
    private Scrollbar scrollbar;
    private Consumer<T> hoverCallback;
    
    public DropDownMenu(final String title, final int x, final int y, final int width, final int height) {
        this.selected = null;
        this.enabled = true;
        this.hoverSelected = null;
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.maxY = Integer.MAX_VALUE;
        this.list = new ArrayList<T>();
        this.entryDrawer = null;
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public DropDownMenu<T> fill(final T[] values) {
        for (final T value : values) {
            this.list.add(value);
        }
        return this;
    }
    
    public void onScroll() {
        if (this.scrollbar != null) {
            this.scrollbar.mouseInput();
            this.scrollbar.setScrollY((int)(this.scrollbar.getScrollY() / this.scrollbar.getSpeed()) * this.scrollbar.getSpeed());
        }
    }
    
    public void onDrag(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.scrollbar != null) {
            this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        }
    }
    
    public void onRelease(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.scrollbar != null) {
            this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        }
    }
    
    public boolean onClick(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.scrollbar != null && this.scrollbar.isHoverTotalScrollbar(mouseX, mouseY)) {
            this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
            return true;
        }
        if (!this.enabled || this.list.isEmpty()) {
            return false;
        }
        if (mouseX > this.x - 1 && mouseX < this.x + this.width + 1 && mouseY > this.y - 1 && mouseY < this.y + this.height + 1) {
            this.open = !this.open;
            if (this.open && this.list.size() > 10) {
                if (this.scrollbar == null) {
                    this.scrollbar = new Scrollbar(13);
                }
                this.scrollbar.setSpeed(13);
                this.scrollbar.setListSize(this.list.size());
                this.scrollbar.setPosition(this.x + this.width - 5, this.y + this.height + 1, this.x + this.width, (this.maxY == Integer.MAX_VALUE) ? (this.y + this.height + 1 + 130 - 1) : this.maxY);
            }
            return true;
        }
        if (this.hoverSelected != null) {
            this.selected = this.hoverSelected;
            this.open = false;
            return true;
        }
        this.open = false;
        if (!this.open && this.hoverCallback != null) {
            this.hoverCallback.accept(null);
        }
        return false;
    }
    
    public boolean isMouseOver(final int mouseX, final int mouseY) {
        return mouseX > this.x - 1 && mouseX < this.x + this.width + 1 && mouseY > this.y - 1 && mouseY < this.y + this.height + 1;
    }
    
    public void draw(final int mouseX, final int mouseY) {
        final T prevHover = this.hoverSelected;
        this.hoverSelected = null;
        a(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, Color.GRAY.getRGB());
        a(this.x, this.y, this.x + this.width, this.y + this.height, Color.BLACK.getRGB());
        if (this.selected != null) {
            final String trimmedEntry = LabyMod.getInstance().getDrawUtils().trimStringToWidth(ModColor.cl("f") + this.selected, this.width - 5);
            ((this.entryDrawer == null) ? DropDownMenu.defaultDrawer : this.entryDrawer).draw(this.selected, this.x + 5, this.y + this.height / 2 - 4, trimmedEntry);
        }
        if (this.enabled && !this.list.isEmpty()) {
            for (int i = 0; i <= 5; ++i) {
                a(this.x + this.width - 16 + i, this.y + this.height / 2 - 2 + i, this.x + this.width - 5 - i, this.y + this.height / 2 + 1 - 2 + i, Color.LIGHT_GRAY.getRGB());
            }
        }
        if (this.title != null) {
            LabyMod.getInstance().getDrawUtils().drawString(LabyModCore.getMinecraft().getFontRenderer().a(this.title, this.width), this.x, this.y - 13);
        }
        if (this.open) {
            this.drawMenuDirect(this.x, this.y, mouseX, mouseY);
        }
        if (((this.hoverSelected != null && prevHover == null) || (prevHover != null && !prevHover.equals(this.hoverSelected))) && this.hoverCallback != null) {
            this.hoverCallback.accept(this.hoverSelected);
        }
    }
    
    public void drawMenuDirect(final int x, final int y, final int mouseX, final int mouseY) {
        final int entryHeight = 13;
        final int maxPointY = y + this.height + 2 + 13 * this.list.size();
        final boolean buildUp = maxPointY > this.maxY;
        int yPositionList = buildUp ? (y - 13 - 1) : (y + this.height + 1);
        if (this.scrollbar != null) {
            yPositionList += (int)this.scrollbar.getScrollY();
        }
        for (final T option : this.list) {
            if (this.scrollbar == null || (yPositionList > y + 8 && yPositionList + entryHeight < this.scrollbar.getPosBottom() + 2)) {
                final boolean hover = mouseX > x && mouseX < x + this.width && mouseY > yPositionList && mouseY < yPositionList + entryHeight;
                if (hover) {
                    this.hoverSelected = option;
                }
                a(x - 1, yPositionList, x + this.width + 1, yPositionList + entryHeight, ModColor.toRGB(0, 30, 70, 250));
                a(x, yPositionList + (buildUp ? 1 : 0), x + this.width, yPositionList + entryHeight - 1 + (buildUp ? 1 : 0), hover ? ModColor.toRGB(55, 55, 155, 215) : ModColor.toRGB(0, 10, 10, 250));
                final String trimmedEntry = LabyMod.getInstance().getDrawUtils().trimStringToWidth(ModColor.cl("f") + option, this.width - 5);
                ((this.entryDrawer == null) ? DropDownMenu.defaultDrawer : this.entryDrawer).draw(option, x + 5, yPositionList + 3, trimmedEntry);
            }
            yPositionList += (buildUp ? (-entryHeight) : entryHeight);
        }
        if (this.scrollbar != null) {
            this.scrollbar.draw();
        }
    }
    
    public void clear() {
        this.open = false;
        this.selected = null;
        this.list.clear();
        this.setSelected(null);
    }
    
    public void remove(final T type) {
        this.list.remove(type);
    }
    
    public void addOption(final T option) {
        this.list.add(option);
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public T getSelected() {
        return this.selected;
    }
    
    public void setSelected(final T selected) {
        this.selected = selected;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public T getHoverSelected() {
        return this.hoverSelected;
    }
    
    public void setHoverSelected(final T hoverSelected) {
        this.hoverSelected = hoverSelected;
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
    
    public void setMaxY(final int maxY) {
        this.maxY = maxY;
    }
    
    public DropDownEntryDrawer getEntryDrawer() {
        return this.entryDrawer;
    }
    
    public void setEntryDrawer(final DropDownEntryDrawer entryDrawer) {
        this.entryDrawer = entryDrawer;
    }
    
    public void setHoverCallback(final Consumer<T> hoverCallback) {
        this.hoverCallback = hoverCallback;
    }
    
    static {
        defaultDrawer = new DropDownEntryDrawer() {
            @Override
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                LabyMod.getInstance().getDrawUtils().drawString(trimmedEntry, x, y);
            }
        };
    }
    
    public interface DropDownEntryDrawer
    {
        void draw(final Object p0, final int p1, final int p2, final String p3);
    }
}
