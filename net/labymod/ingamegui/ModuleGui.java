//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui;

import org.lwjgl.input.*;
import net.labymod.ingamegui.modules.*;
import net.labymod.main.*;
import net.labymod.ingamegui.moduletypes.*;
import org.lwjgl.opengl.*;
import net.labymod.utils.*;
import java.io.*;
import java.util.*;
import net.labymod.ingamegui.enums.*;
import java.awt.*;

public class ModuleGui extends blk
{
    public static final List<ModuleGui> INSTANCES;
    private static final int ALIGNMENT_COLOR;
    private Module draggingModule;
    private Module selectedModule;
    private boolean hoveringCenterX;
    private boolean hoveringCenterY;
    private boolean canDrag;
    private double mouseMoveX;
    private double mouseMoveY;
    private boolean placeOver;
    private double mouseX;
    private double mouseY;
    private List<CoordinatesConsumer> mouseClickListeners;
    private List<CoordinatesConsumer> mouseMoveListeners;
    private List<CoordinatesConsumer> mouseScrollListeners;
    private List<CoordinatesConsumer> mouseReleaseListeners;
    private List<KeyConsumer> keyTypeListeners;
    private List<Consumer<Module>> doubleClickModuleListeners;
    private List<Consumer<Module>> clickModuleListeners;
    private boolean movableModules;
    private boolean showAllModules;
    private Set<EnumItemSlot> usedItemSlots;
    private EnumItemSlot nearestItemSlot;
    private int nearestScoreboardSlot;
    private Module focusedModule;
    private Module lastClickedModule;
    private long lastClicked;
    private EnumDisplayType displayType;
    private boolean clicking;
    
    public ModuleGui(final boolean movableModules, final boolean showAllModules, final EnumDisplayType displayType) {
        this.hoveringCenterX = false;
        this.hoveringCenterY = false;
        this.canDrag = false;
        this.mouseMoveX = 0.0;
        this.mouseMoveY = 0.0;
        this.mouseClickListeners = new ArrayList<CoordinatesConsumer>();
        this.mouseMoveListeners = new ArrayList<CoordinatesConsumer>();
        this.mouseScrollListeners = new ArrayList<CoordinatesConsumer>();
        this.mouseReleaseListeners = new ArrayList<CoordinatesConsumer>();
        this.keyTypeListeners = new ArrayList<KeyConsumer>();
        this.doubleClickModuleListeners = new ArrayList<Consumer<Module>>();
        this.clickModuleListeners = new ArrayList<Consumer<Module>>();
        this.usedItemSlots = new HashSet<EnumItemSlot>();
        this.nearestItemSlot = null;
        this.nearestScoreboardSlot = -1;
        this.clicking = false;
        this.movableModules = movableModules;
        this.showAllModules = showAllModules;
        this.displayType = displayType;
        for (final Module module : Module.getModules()) {
            module.initModuleGui(this);
        }
        ModuleGui.INSTANCES.add(this);
    }
    
    public void setDisplayType(final EnumDisplayType displayType) {
        this.displayType = displayType;
    }
    
    public void b() {
        super.b();
        this.j = bib.z();
        this.draggingModule = null;
    }
    
    public void a(final int mouseXInt, final int mouseYInt, final float partialTicks) {
        if (!ModuleConfig.getConfig().isModulesEnabled()) {
            return;
        }
        double mouseX = mouseXInt;
        double mouseY = mouseYInt;
        final double scale = this.displayType.isScaling() ? (2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0) : 1.0;
        if (this.displayType.isScaling() && scale != 1.0) {
            mouseX *= scale;
            mouseY *= scale;
        }
        final boolean mouseIsMoving = this.mouseX != mouseX || this.mouseY != mouseY;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        final double lastLeft = Module.getLastLeft() / scale;
        final double lastTop = Module.getLastTop() / scale;
        final double lastRight = Module.getLastRight() / scale;
        final double lastBottom = Module.getLastBottom() / scale;
        final boolean isCtrlDown = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        this.nearestScoreboardSlot = -1;
        if (this.draggingModule != null && this.canDrag && !isCtrlDown) {
            final EnumModuleRegion region = this.draggingModule.getRegion(this.displayType.ordinal());
            final double minX = region.getMinX(Module.getLastLeft(), Module.getLastRight()) / scale;
            final double maxX = region.getMaxX(Module.getLastLeft(), Module.getLastRight()) / scale;
            final double minY = region.getMinY(Module.getLastTop(), Module.getLastBottom()) / scale;
            final double maxY = region.getMaxY(Module.getLastTop(), Module.getLastBottom()) / scale;
            bir.a((int)(minX + 1.0), (int)(minY + 1.0), (int)(maxX - 1.0), (int)(maxY - 1.0), ModColor.toRGB(255, 255, 255, 26));
            if (this.draggingModule instanceof ItemModule && !Module.getModulesByOverlistedModules().containsKey(this.draggingModule.getName())) {
                final double width = lastRight - lastLeft;
                final double height = lastBottom - lastTop;
                EnumItemSlot nearestSlot = null;
                double nearestDistance = 2.147483647E9;
                for (final EnumItemSlot itemSlot : EnumItemSlot.values()) {
                    if (itemSlot != EnumItemSlot.NONE) {
                        if (!this.usedItemSlots.contains(itemSlot)) {
                            final double x = itemSlot.getX(width) + lastLeft;
                            final double y = itemSlot.getY(height) + lastTop;
                            final double distance = Math.min(Math.abs(mouseXInt - x) + Math.abs(mouseYInt - y), Math.abs(mouseXInt - (x + 15.0)) + Math.abs(mouseYInt - (y + 15.0)));
                            if (nearestSlot == null || distance < nearestDistance) {
                                nearestSlot = itemSlot;
                                nearestDistance = distance;
                            }
                            bir.a((int)x, (int)y, (int)(x + 15.0), (int)(y + 15.0), ModColor.toRGB(255, 255, 255, 100));
                        }
                    }
                }
                if (nearestDistance < 20.0 && nearestSlot != null) {
                    final double x2 = nearestSlot.getX(width) + lastLeft;
                    final double y2 = nearestSlot.getY(height) + lastTop;
                    bir.a((int)x2, (int)y2, (int)(x2 + 15.0), (int)(y2 + 15.0), ModColor.toRGB(255, 255, 0, 50));
                    this.nearestItemSlot = nearestSlot;
                }
                else {
                    this.nearestItemSlot = null;
                }
            }
            else {
                this.nearestItemSlot = null;
            }
            if (this.draggingModule instanceof ScoreboardModule && !Module.getModulesByOverlistedModules().containsKey(this.draggingModule.getName())) {
                final double width = lastRight - lastLeft;
                final double height = lastBottom - lastTop;
                double slotX = lastLeft + (width - this.draggingModule.getWidth() / scale - 1.0);
                final double slotY = lastTop + (height / 2.0 - this.draggingModule.getHeight() / scale / 2.0);
                LabyMod.getInstance().getDrawUtils().drawRect(slotX, slotY, slotX + this.draggingModule.getWidth() / scale, slotY + this.draggingModule.getHeight() / scale, ModColor.toRGB(255, 255, 255, 100));
                if (mouseX / scale > slotX && mouseX / scale < slotX + this.draggingModule.getWidth() / scale && mouseY / scale > slotY && mouseY / scale < slotY + this.draggingModule.getHeight() / scale) {
                    this.nearestScoreboardSlot = 0;
                }
                slotX = lastLeft + 1.0;
                LabyMod.getInstance().getDrawUtils().drawRect(slotX, slotY, slotX + this.draggingModule.getWidth() / scale, slotY + this.draggingModule.getHeight() / scale, ModColor.toRGB(255, 255, 255, 100));
                if (mouseX / scale > slotX && mouseX / scale < slotX + this.draggingModule.getWidth() / scale && mouseY / scale > slotY && mouseY / scale < slotY + this.draggingModule.getHeight() / scale) {
                    this.nearestScoreboardSlot = 1;
                }
            }
        }
        else {
            this.nearestItemSlot = null;
        }
        boolean skipDistanceLine = false;
        if (!isCtrlDown) {
            if (this.draggingModule != null && this.selectedModule != null) {
                double y3 = this.selectedModule.getLastY() + this.selectedModule.getHeight();
                if (this.draggingModule.getLastY() < this.selectedModule.getLastY()) {
                    y3 = this.selectedModule.getLastY();
                    this.placeOver = true;
                }
                else {
                    this.placeOver = false;
                }
                LabyMod.getInstance().getDrawUtils().drawRect(this.selectedModule.getLastX() / scale - 5.0, y3 / scale - 0.8, this.selectedModule.getLastX() / scale + this.selectedModule.getWidth() / scale + 5.0, y3 / scale, ModuleGui.ALIGNMENT_COLOR);
                skipDistanceLine = true;
            }
            else {
                if (this.hoveringCenterX && this.draggingModule != null) {
                    final double center = lastLeft + (lastRight - lastLeft) / 2.0;
                    LabyMod.getInstance().getDrawUtils().drawRect(center - 0.4, Module.getLastTop() / scale, center + 0.4, Module.getLastBottom() / scale, ModuleGui.ALIGNMENT_COLOR);
                    skipDistanceLine = true;
                }
                if (this.hoveringCenterY && this.draggingModule != null) {
                    final double center = lastTop + (lastBottom - lastTop) / 2.0;
                    LabyMod.getInstance().getDrawUtils().drawRect(Module.getLastLeft() / scale, center - 0.4, Module.getLastRight() / scale, center + 0.4, ModuleGui.ALIGNMENT_COLOR);
                    skipDistanceLine = true;
                }
            }
        }
        else {
            this.selectedModule = null;
        }
        if (this.draggingModule != null && !isCtrlDown) {
            final double tX = this.draggingModule.getX(this.displayType.ordinal());
            final double tY = this.draggingModule.getY(this.displayType.ordinal());
            final EnumModuleRegion region2 = this.draggingModule.getRegion(this.displayType.ordinal());
            if ((region2 == EnumModuleRegion.BOTTOM_LEFT || region2 == EnumModuleRegion.CENTER_LEFT || region2 == EnumModuleRegion.TOP_LEFT) && tX < 2.0) {
                LabyMod.getInstance().getDrawUtils().drawRect(Module.getLastLeft() / scale + 2.0, Module.getLastTop() / scale + 2.0, Module.getLastLeft() / scale + 2.0 + 1.0, Module.getLastBottom() / scale - 2.0, ModuleGui.ALIGNMENT_COLOR);
            }
            if ((region2 == EnumModuleRegion.TOP_LEFT || region2 == EnumModuleRegion.TOP_CENTER || region2 == EnumModuleRegion.TOP_RIGHT) && tY < 2.0) {
                LabyMod.getInstance().getDrawUtils().drawRect(Module.getLastLeft() / scale + 2.0, Module.getLastTop() / scale + 2.0, Module.getLastRight() / scale - 2.0, Module.getLastTop() / scale + 2.0 + 1.0, ModuleGui.ALIGNMENT_COLOR);
            }
            if ((region2 == EnumModuleRegion.BOTTOM_RIGHT || region2 == EnumModuleRegion.CENTER_RIGHT || region2 == EnumModuleRegion.TOP_RIGHT) && tX > -2.0) {
                LabyMod.getInstance().getDrawUtils().drawRect(Module.getLastRight() / scale - 2.0 - 1.0, Module.getLastTop() / scale + 2.0, Module.getLastRight() / scale - 2.0, Module.getLastBottom() / scale - 2.0, ModuleGui.ALIGNMENT_COLOR);
            }
        }
        if (this.focusedModule != null && this.focusedModule.isEnabled(this.displayType)) {
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            final Module focusedModule = this.focusedModule;
            final boolean hasUnderlistedModule = focusedModule instanceof ResizeableModule && Module.getModulesByOverlistedModules().containsKey(focusedModule.getName()) && Module.getLastDrawnDisplayType() != EnumDisplayType.ESCAPE;
            final double firstOffset = 0.0;
            final double secondOffset = hasUnderlistedModule ? 4.0 : 0.0;
            final double mX = focusedModule.getLastX() / scale - 1.0;
            final double mY = focusedModule.getLastY() / scale - 1.0 + firstOffset;
            final double mWidth = focusedModule.getWidth() / scale + 2.0;
            final double mHeight = focusedModule.getHeight() / scale + 2.0 - secondOffset;
            final boolean isInItemSlot = focusedModule instanceof ItemModule && ((ItemModule)focusedModule).getItemSlot() != null && ((ItemModule)focusedModule).getItemSlot() != EnumItemSlot.NONE;
            final boolean isInScoreboardSlot = focusedModule instanceof ScoreboardModule && ((ScoreboardModule)focusedModule).getSlot() != -1;
            if (!isInItemSlot && !isInScoreboardSlot) {
                draw.drawRectBorder(mX, mY, mX + mWidth, mY + mHeight, ModColor.toRGB(255, 255, 255, 56), 1.0);
            }
        }
        if (this.focusedModule != null && this.focusedModule.isEnabled(this.displayType)) {
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            final Module focusedModule = this.focusedModule;
            if (this.displayType == EnumDisplayType.ESCAPE && this.draggingModule == focusedModule) {
                final double middleX = (Module.getLastRight() - Module.getLastLeft()) / 2.0 + Module.getLastLeft();
                final double middleY = (Module.getLastBottom() - Module.getLastTop()) / 2.0 + Module.getLastTop();
                final double moduleMiddleX = focusedModule.getLastX() + focusedModule.getWidth() / 2.0;
                final double moduleMiddleY = focusedModule.getLastY() + focusedModule.getHeight() / 2.0;
                if (!skipDistanceLine) {
                    bus.m();
                    bus.z();
                    GL11.glLineWidth(1.5f);
                    GL11.glColor3f(1.0f, 1.0f, 0.0f);
                    GL11.glBegin(1);
                    GL11.glVertex3d(middleX, middleY, 0.0);
                    GL11.glVertex3d(moduleMiddleX, moduleMiddleY, 0.0);
                    GL11.glEnd();
                    bus.l();
                    bus.y();
                }
                final double distance2 = Math.sqrt(Math.pow(moduleMiddleX - middleX, 2.0) + Math.pow(moduleMiddleY - middleY, 2.0));
                draw.drawString(ModColor.cl('e') + (int)distance2 + " px", middleX + (moduleMiddleX - middleX) / 2.0 + 4.0, middleY + (moduleMiddleY - middleY) / 2.0);
            }
        }
        if (this.clicking && mouseIsMoving) {
            this.a(mouseXInt, mouseYInt, 0, 0L);
        }
    }
    
    public void a(final int mouseXInt, final int mouseYInt, final int mouseButton) throws IOException {
        super.a(mouseXInt, mouseYInt, mouseButton);
        this.clicking = true;
        if (!ModuleConfig.getConfig().isModulesEnabled()) {
            return;
        }
        if (this.draggingModule != null) {
            return;
        }
        if (this.movableModules) {
            this.usedItemSlots.clear();
            for (final Module module : Module.getModules()) {
                if (module.isEnabled(this.displayType) && module instanceof ItemModule && ((ItemModule)module).getItemSlot() != null && ((ItemModule)module).getItemSlot() != EnumItemSlot.NONE) {
                    this.usedItemSlots.add(((ItemModule)module).getItemSlot());
                }
            }
        }
        for (final Module module : Module.getModules()) {
            final boolean isShown = (module.isShown() || (Module.getCurrentModuleGui() != null && Module.getCurrentModuleGui().isShowAllModules())) && module.getCategory().isEnabled();
            if (module.isEnabled(this.displayType)) {
                if (!isShown) {
                    continue;
                }
                double mouseX = mouseXInt;
                double mouseY = mouseYInt;
                final boolean isItemSlot = module instanceof ItemModule && ((ItemModule)module).getItemSlot() != EnumItemSlot.NONE;
                final boolean isScoreboardSlot = module instanceof ScoreboardModule && ((ScoreboardModule)module).getSlot() != -1;
                double x = module.getLastX();
                double y = module.getLastY();
                final double scale = this.displayType.isScaling() ? (2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0) : 1.0;
                if (isItemSlot) {
                    final ItemModule itemModule = (ItemModule)module;
                    final EnumItemSlot itemSlot = itemModule.getItemSlot();
                    x = Module.getLastLeft() / scale + itemSlot.getX(Module.getLastRight() / scale - Module.getLastLeft() / scale);
                    y = Module.getLastTop() / scale + itemSlot.getY(Module.getLastBottom() / scale - Module.getLastTop() / scale);
                    if (mouseX < x) {
                        continue;
                    }
                    if (mouseX > x + module.getWidth()) {
                        continue;
                    }
                    if (mouseY < y) {
                        continue;
                    }
                    if (mouseY > y + module.getHeight()) {
                        continue;
                    }
                    if (!module.isMovable((int)mouseX, (int)mouseY)) {
                        module.onMouseClick((int)mouseX, (int)mouseY, mouseButton);
                        continue;
                    }
                }
                else if (isScoreboardSlot) {
                    final ScoreboardModule scoreboardModule = (ScoreboardModule)module;
                    final int slot = scoreboardModule.getSlot();
                    switch (slot) {
                        case 0: {
                            x = Module.getLastRight() / scale - module.getWidth() - 1.0;
                            y = Module.getLastTop() / scale + (Module.getLastBottom() / scale - Module.getLastTop() / scale) / 2.0 - module.getHeight() / 2.0;
                            break;
                        }
                        case 1: {
                            x = Module.getLastLeft() / scale + 1.0;
                            y = Module.getLastTop() / scale + (Module.getLastBottom() / scale - Module.getLastTop() / scale) / 2.0 - module.getHeight() / 2.0;
                            break;
                        }
                    }
                    if (mouseX < x) {
                        continue;
                    }
                    if (mouseX > x + module.getWidth()) {
                        continue;
                    }
                    if (mouseY < y) {
                        continue;
                    }
                    if (mouseY > y + module.getHeight()) {
                        continue;
                    }
                    if (!module.isMovable((int)mouseX, (int)mouseY)) {
                        module.onMouseClick((int)mouseX, (int)mouseY, mouseButton);
                        continue;
                    }
                }
                else {
                    mouseX *= scale;
                    mouseY *= scale;
                    if (mouseX < x) {
                        continue;
                    }
                    if (mouseX > x + module.getWidth()) {
                        continue;
                    }
                    if (mouseY < y) {
                        continue;
                    }
                    if (mouseY > y + module.getHeight()) {
                        continue;
                    }
                    if (!module.isMovable((int)mouseX, (int)mouseY)) {
                        module.onMouseClick((int)mouseX, (int)mouseY, mouseButton);
                        continue;
                    }
                }
                if (this.movableModules) {
                    final long curr = System.currentTimeMillis();
                    if (this.lastClickedModule != null && this.lastClickedModule.equals(module) && curr - this.lastClicked < 200L) {
                        for (final Consumer<Module> doubleClickListener : this.doubleClickModuleListeners) {
                            doubleClickListener.accept(this.lastClickedModule);
                        }
                        this.lastClickedModule = null;
                        this.lastClicked = curr;
                    }
                    else {
                        this.lastClickedModule = module;
                        this.lastClicked = curr;
                    }
                    this.draggingModule = module;
                    this.canDrag = false;
                    this.mouseMoveX = (module.isRightBound(this.displayType) ? (mouseX - (x + module.getWidth())) : (mouseX - x));
                    this.mouseMoveY = mouseY - y;
                    for (final Consumer<Module> listener : this.clickModuleListeners) {
                        listener.accept(module);
                    }
                }
                return;
            }
        }
        for (final CoordinatesConsumer listener2 : this.mouseClickListeners) {
            listener2.accept((int)this.mouseX, (int)this.mouseY, mouseButton, this.displayType);
        }
    }
    
    public void k() throws IOException {
        if (this.displayType.isScaling()) {
            final double scale = 2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0;
            if (scale != 1.0) {
                this.mouseX *= scale;
                this.mouseY *= scale;
            }
        }
        for (final CoordinatesConsumer listener : this.mouseScrollListeners) {
            listener.accept((int)this.mouseX, (int)this.mouseY, 0, this.displayType);
        }
        for (final Module module : Module.getModules()) {
            module.onMouseScroll((int)this.mouseX, (int)this.mouseY);
        }
    }
    
    public void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        for (final KeyConsumer listener : this.keyTypeListeners) {
            listener.accept(typedChar, keyCode);
        }
        for (final Module module : Module.getModules()) {
            final boolean isShown = (module.isShown() || (Module.getCurrentModuleGui() != null && Module.getCurrentModuleGui().isShowAllModules())) && module.getCategory().isEnabled();
            if (module.isEnabled(this.displayType)) {
                if (!isShown) {
                    continue;
                }
                module.onKeyType(typedChar, keyCode);
            }
        }
    }
    
    public void a(int mouseX, int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (this.displayType.isScaling()) {
            final double scale = 2.0 - ModuleConfig.getConfig().getGuiScale() / 100.0;
            if (scale != 1.0) {
                mouseX *= (int)scale;
                mouseY *= (int)scale;
            }
        }
        if (!ModuleConfig.getConfig().isModulesEnabled()) {
            return;
        }
        if (this.draggingModule == null) {
            for (final CoordinatesConsumer listener : this.mouseMoveListeners) {
                listener.accept(mouseX, mouseY, clickedMouseButton, this.displayType);
            }
            for (final Module module : Module.getModules()) {
                final boolean isShown = (module.isShown() || (Module.getCurrentModuleGui() != null && Module.getCurrentModuleGui().isShowAllModules())) && module.getCategory().isEnabled();
                if (module.isEnabled(this.displayType)) {
                    if (!isShown) {
                        continue;
                    }
                    final double x = module.getLastX();
                    final double y = module.getLastY();
                    if (mouseX < x) {
                        continue;
                    }
                    if (mouseX > x + module.getWidth()) {
                        continue;
                    }
                    if (mouseY < y) {
                        continue;
                    }
                    if (mouseY > y + module.getHeight()) {
                        continue;
                    }
                    if (module.isMovable(mouseX, mouseY)) {
                        continue;
                    }
                    module.onMouseMove(mouseX, mouseY, clickedMouseButton);
                }
            }
            return;
        }
        if (!this.canDrag) {
            this.canDrag = true;
            if (this.draggingModule instanceof ItemModule) {
                final EnumItemSlot itemSlot = ((ItemModule)this.draggingModule).getItemSlot();
                if (itemSlot != null && itemSlot != EnumItemSlot.NONE) {
                    this.usedItemSlots.remove(itemSlot);
                    ((ItemModule)this.draggingModule).setItemSlot(EnumItemSlot.NONE);
                    this.draggingModule.setAttribute("itemSlot", String.valueOf("0"));
                }
            }
            if (this.draggingModule instanceof ScoreboardModule) {
                final int slot = ((ScoreboardModule)this.draggingModule).getSlot();
                if (slot != -1) {
                    ((ScoreboardModule)this.draggingModule).setSlot(-1);
                    this.draggingModule.setAttribute("slot", String.valueOf("-1"));
                }
            }
        }
        final boolean gridEnabled = ModuleConfig.getConfig().isGridEnabled();
        boolean shiftDown = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
        boolean altDown = Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
        final boolean crtlDown = Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157);
        if (shiftDown && altDown) {
            shiftDown = false;
            altDown = false;
        }
        double newX = (!gridEnabled && !altDown) ? (mouseX - this.mouseMoveX) : (gridEnabled ? ((double)(Math.round((mouseX - this.mouseMoveX) / 5.0) * 5L)) : this.draggingModule.getX(this.displayType.ordinal()));
        double newY = (!gridEnabled && !shiftDown) ? (mouseY - this.mouseMoveY) : (gridEnabled ? ((double)(Math.round((mouseY - this.mouseMoveY) / 5.0) * 5L)) : this.draggingModule.getY(this.displayType.ordinal()));
        Module bottomModule = this.draggingModule;
        double currentY = newY + bottomModule.getHeight();
        double stackHeight = currentY - newY;
        double maxWidth = bottomModule.getWidth();
        if (this.displayType == EnumDisplayType.INGAME) {
            while (Module.getModulesByOverlistedModules().containsKey(bottomModule.getName())) {
                bottomModule = Module.getModulesByOverlistedModules().get(bottomModule.getName());
                final double width = bottomModule.getWidth();
                if (width > maxWidth) {
                    maxWidth = width;
                }
                final double height = bottomModule.getHeight();
                currentY += height;
                stackHeight += height;
            }
        }
        if (newY < Module.getLastTop()) {
            newY = Module.getLastTop();
        }
        if (currentY > Module.getLastBottom()) {
            currentY = Module.getLastBottom();
            newY = Module.getLastBottom() - stackHeight;
        }
        if (newX < Module.getLastLeft()) {
            newX = Module.getLastLeft();
        }
        final boolean isRightBound = this.draggingModule.isRightBound(this.displayType);
        if (newX + (isRightBound ? 0.0 : maxWidth) > Module.getLastRight()) {
            newX = Module.getLastRight() - (isRightBound ? 0.0 : maxWidth);
        }
        if (isRightBound && newX - maxWidth < Module.getLastLeft()) {
            newX = Module.getLastLeft() + maxWidth;
        }
        final EnumModuleRegion newRegion = this.getRegion(mouseX, mouseY);
        if (newRegion != null && newRegion != this.draggingModule.getRegion(this.displayType.ordinal())) {
            this.draggingModule.setRegion(this.displayType.ordinal(), newRegion);
        }
        final double x2 = this.draggingModule.getRegion(this.displayType.ordinal()).getOffsetX(newX, Module.getLastLeft(), Module.getLastRight());
        final double y2 = this.draggingModule.getRegion(this.displayType.ordinal()).getOffsetY(newY, Module.getLastTop(), Module.getLastBottom());
        if (this.draggingModule != null) {
            if (Module.getLastDrawnDisplayType() != EnumDisplayType.ESCAPE && this.draggingModule.getListedAfter() != null && this.draggingModule.getModuleConfigElement() != null) {
                final Module module2 = Module.getModuleByName(this.draggingModule.getListedAfter());
                if (module2 != null) {
                    final ModuleConfigElement moduleConfigElement = module2.getModuleConfigElement();
                    if (moduleConfigElement != null) {
                        final EnumModuleAlignment enumModuleAlignment = moduleConfigElement.getAlignment(this.displayType.ordinal());
                        if (enumModuleAlignment != null) {
                            this.draggingModule.getModuleConfigElement().setAlignment(this.displayType.ordinal(), enumModuleAlignment);
                        }
                    }
                }
                Module.getModulesByOverlistedModules().remove(this.draggingModule.getListedAfter());
                this.draggingModule.setListedAfter((String)null);
            }
            else if (this.displayType.isAttachableModules()) {
                final List<Module> possibleModules = new ArrayList<Module>();
                final Set<Module> impossibleModules = new HashSet<Module>();
                Module theModule = this.draggingModule;
                while ((theModule = Module.getModulesByOverlistedModules().get(theModule.getName())) != null) {
                    impossibleModules.add(theModule);
                }
                for (final Module module3 : Module.getModules()) {
                    if (module3 != this.draggingModule && !impossibleModules.contains(module3)) {
                        if (module3 instanceof ItemModule && ((ItemModule)module3).getItemSlot() != null && ((ItemModule)module3).getItemSlot() != EnumItemSlot.NONE) {
                            continue;
                        }
                        final boolean isShown2 = (module3.isShown() || (Module.getCurrentModuleGui() != null && Module.getCurrentModuleGui().isShowAllModules())) && module3.getCategory() != null;
                        if (!module3.isEnabled(this.displayType)) {
                            continue;
                        }
                        if (!isShown2) {
                            continue;
                        }
                        if (module3.getListedAfter() != null && module3.getListedAfter().equals(this.draggingModule.getName())) {
                            continue;
                        }
                        if (module3.getListedAfter() == null && newY > module3.getLastY() - 15.0 && newY < module3.getLastY() && newX > module3.getLastX() - 10.0 && newX < module3.getLastX() + module3.getWidth() + 10.0) {
                            possibleModules.add(module3);
                        }
                        else {
                            final boolean possible = newY > module3.getLastY() && newY < module3.getLastY() + module3.getHeight() + 10.0 && mouseX > module3.getLastX() && mouseX - module3.getWidth() / 2.0 < module3.getLastX() + module3.getWidth() / 2.0;
                            if (!possible) {
                                continue;
                            }
                            possibleModules.add(module3);
                        }
                    }
                }
                final double finalNewY = newY;
                Collections.sort(possibleModules, new Comparator<Module>() {
                    @Override
                    public int compare(final Module o1, final Module o2) {
                        return Integer.valueOf((int)Math.abs(o2.getLastY() - finalNewY)).compareTo((int)Math.abs(o1.getLastY() - finalNewY));
                    }
                });
                if (possibleModules.size() == 0) {
                    this.selectedModule = null;
                }
                else if (!crtlDown) {
                    this.selectedModule = possibleModules.get(0);
                }
            }
        }
        this.hoveringCenterX = (this.draggingModule != null && this.selectedModule == null && this.displayType.isAttachableCenter() && Math.abs(this.draggingModule.getLastX() + this.draggingModule.getWidth() / 2.0 - (Module.getLastLeft() + (Module.getLastRight() - Module.getLastLeft()) / 2.0)) < 5.0);
        this.hoveringCenterY = (this.draggingModule != null && this.selectedModule == null && this.displayType.isAttachableCenter() && Math.abs(this.draggingModule.getLastY() + this.draggingModule.getHeight() / 2.0 - (Module.getLastTop() + (Module.getLastBottom() - Module.getLastTop()) / 2.0)) < 5.0);
        if (!altDown) {
            this.draggingModule.setX(this.displayType.ordinal(), x2);
        }
        else {
            this.mouseMoveX = (this.draggingModule.isRightBound(this.displayType) ? (mouseX - (this.draggingModule.getLastX() + this.draggingModule.getWidth())) : (mouseX - this.draggingModule.getLastX()));
        }
        if (!shiftDown) {
            this.draggingModule.setY(this.displayType.ordinal(), y2);
        }
        else {
            this.mouseMoveY = mouseY - this.draggingModule.getLastY();
        }
        final boolean newRightBoundState = this.draggingModule.isRightBound(this.displayType);
        if (isRightBound && !newRightBoundState) {
            this.mouseMoveX = mouseX - newX + this.draggingModule.getWidth();
            this.draggingModule.setX(this.displayType.ordinal(), this.draggingModule.getRegion(this.displayType.ordinal()).getOffsetX(mouseX - this.mouseMoveX, Module.getLastLeft(), Module.getLastRight()));
        }
        if (!isRightBound && newRightBoundState) {
            this.mouseMoveX = mouseX - newX - this.draggingModule.getWidth();
            this.draggingModule.setX(this.displayType.ordinal(), this.draggingModule.getRegion(this.displayType.ordinal()).getOffsetX(mouseX - this.mouseMoveX, Module.getLastLeft(), Module.getLastRight()));
        }
    }
    
    public void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.clicking = false;
        if (!ModuleConfig.getConfig().isModulesEnabled()) {
            return;
        }
        if (this.draggingModule == null) {
            for (final CoordinatesConsumer listener : this.mouseReleaseListeners) {
                listener.accept(mouseX, mouseY, state, this.displayType);
            }
            for (final Module module : Module.getModules()) {
                module.onMouseRelease(mouseX, mouseY, state);
            }
            return;
        }
        if (!this.canDrag) {
            this.selectedModule = null;
            this.draggingModule = null;
            return;
        }
        boolean itemAttached = false;
        if (this.draggingModule instanceof ItemModule && this.nearestItemSlot != null && !this.usedItemSlots.contains(this.nearestItemSlot)) {
            this.draggingModule.setAttribute("itemSlot", String.valueOf(this.nearestItemSlot.ordinal()));
            ((ItemModule)this.draggingModule).setItemSlot(this.nearestItemSlot);
            itemAttached = true;
        }
        if (this.draggingModule instanceof ScoreboardModule && this.nearestScoreboardSlot != -1) {
            this.draggingModule.setAttribute("slot", String.valueOf(this.nearestScoreboardSlot));
            ((ScoreboardModule)this.draggingModule).setSlot(this.nearestScoreboardSlot);
            itemAttached = true;
        }
        if (!itemAttached) {
            if (this.selectedModule != null) {
                if (!this.placeOver) {
                    this.draggingModule.setListedAfter(this.selectedModule.getName());
                    this.draggingModule.getModuleConfigElement().setAlignment(this.displayType.ordinal(), this.selectedModule.getModuleConfigElement().getAlignment(this.displayType.ordinal()));
                    if (Module.getModulesByOverlistedModules().containsKey(this.selectedModule.getName())) {
                        Module currentModule;
                        for (currentModule = this.draggingModule; Module.getModulesByOverlistedModules().containsKey(currentModule.getName()); currentModule = Module.getModulesByOverlistedModules().get(currentModule.getName())) {}
                        Module.getModulesByOverlistedModules().put(currentModule.getName(), Module.getModulesByOverlistedModules().get(this.selectedModule.getName()));
                        final Module module2 = Module.getModulesByOverlistedModules().get(this.selectedModule.getName());
                        module2.setListedAfter(currentModule.getName());
                        module2.getModuleConfigElement().setListedAfter(module2.getListedAfter());
                    }
                    Module.getModulesByOverlistedModules().put(this.selectedModule.getName(), this.draggingModule);
                    this.getNewCoordinates(this.selectedModule);
                }
                else {
                    Module currentModule;
                    for (currentModule = this.draggingModule; Module.getModulesByOverlistedModules().containsKey(currentModule.getName()); currentModule = Module.getModulesByOverlistedModules().get(currentModule.getName())) {}
                    this.selectedModule.getModuleConfigElement().setAlignment(this.displayType.ordinal(), this.draggingModule.getModuleConfigElement().getAlignment(this.displayType.ordinal()));
                    this.draggingModule.setX(this.displayType.ordinal(), this.selectedModule.getX(this.displayType.ordinal()));
                    this.draggingModule.setRegion(this.displayType.ordinal(), this.selectedModule.getRegion(this.displayType.ordinal()));
                    this.draggingModule.setY(this.displayType.ordinal(), this.selectedModule.getRegion(this.displayType.ordinal()).getOffsetY(this.draggingModule.getLastY(), Module.getLastTop(), Module.getLastBottom()));
                    this.draggingModule.setListedAfter((String)null);
                    this.selectedModule.setListedAfter(currentModule.getName());
                    this.selectedModule.getModuleConfigElement().setListedAfter(currentModule.getListedAfter());
                    Module.getModulesByOverlistedModules().put(currentModule.getName(), this.selectedModule);
                    this.getNewCoordinates(this.draggingModule);
                }
            }
            else {
                if (this.hoveringCenterX) {
                    final double center = Module.getLastLeft() + (Module.getLastRight() - Module.getLastLeft()) / 2.0;
                    this.draggingModule.setRegion(this.displayType.ordinal(), (Module.getLastDrawnDisplayType() == EnumDisplayType.ESCAPE) ? EnumModuleRegion.CENTER : this.draggingModule.getRegion(Module.getLastDrawnDisplayType().ordinal()));
                    this.draggingModule.setX(this.displayType.ordinal(), this.draggingModule.getRegion(this.displayType.ordinal()).getOffsetX(center - this.draggingModule.getWidth() / 2.0, Module.getLastLeft(), Module.getLastRight()));
                }
                if (this.hoveringCenterY) {
                    final double center = Module.getLastTop() + (Module.getLastBottom() - Module.getLastTop()) / 2.0;
                    this.draggingModule.setRegion(this.displayType.ordinal(), this.draggingModule.getRegion(Module.getLastDrawnDisplayType().ordinal()));
                    this.draggingModule.setY(this.displayType.ordinal(), this.draggingModule.getRegion(this.displayType.ordinal()).getOffsetY(center - this.draggingModule.getHeight() / 2.0, Module.getLastTop(), Module.getLastBottom()));
                }
            }
        }
        double tX = this.draggingModule.getX(this.displayType.ordinal());
        double tY = this.draggingModule.getY(this.displayType.ordinal());
        final boolean isCtrlDown = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
        if (!isCtrlDown) {
            final EnumModuleRegion region = this.draggingModule.getRegion(this.displayType.ordinal());
            if ((region == EnumModuleRegion.BOTTOM_LEFT || region == EnumModuleRegion.CENTER_LEFT || region == EnumModuleRegion.TOP_LEFT) && tX < 2.0) {
                tX = 2.0;
            }
            if ((region == EnumModuleRegion.TOP_LEFT || region == EnumModuleRegion.TOP_CENTER || region == EnumModuleRegion.TOP_RIGHT) && tY < 2.0) {
                tY = 2.0;
            }
            if ((region == EnumModuleRegion.BOTTOM_RIGHT || region == EnumModuleRegion.CENTER_RIGHT || region == EnumModuleRegion.TOP_RIGHT) && tX > -2.0) {
                tX = -2.0;
            }
        }
        this.draggingModule.getModuleConfigElement().setX(this.displayType.ordinal(), tX);
        this.draggingModule.getModuleConfigElement().setY(this.displayType.ordinal(), tY);
        this.draggingModule.getModuleConfigElement().setRegion(this.displayType.ordinal(), this.draggingModule.getRegion(this.displayType.ordinal()));
        this.draggingModule.getModuleConfigElement().setListedAfter(this.draggingModule.getListedAfter());
        ModuleConfig.getConfigManager().save();
        this.selectedModule = null;
        this.draggingModule = null;
    }
    
    public void m() {
        this.b((int)this.mouseX, (int)this.mouseY, 0);
        this.b((int)this.mouseX, (int)this.mouseY, 1);
    }
    
    public void getNewCoordinates(final Module module) {
        Module topModule;
        for (topModule = module; topModule != null && topModule.getListedAfter() != null; topModule = Module.getModuleByName(topModule.getListedAfter())) {}
        if (topModule == null) {
            return;
        }
        final double currentY = topModule.getLastY();
        final boolean rightbound = topModule.isRightBound(this.displayType);
        double maxY = currentY + topModule.getHeight();
        double maxX = topModule.getLastX() + (rightbound ? topModule.getWidth() : 0.0);
        double stackWidth = topModule.getWidth();
        double stackHeight = maxY - currentY;
        Module bottomModule = topModule;
        while (Module.getModulesByOverlistedModules().containsKey(bottomModule.getName())) {
            bottomModule = Module.getModulesByOverlistedModules().get(bottomModule.getName());
            final double width = bottomModule.getWidth();
            final double x = rightbound ? (topModule.getLastX() - width) : (topModule.getLastX() + width);
            if ((!rightbound && x > maxX) || (rightbound && x < maxX)) {
                maxX = x;
                stackWidth = width;
            }
            final double height = bottomModule.getHeight();
            maxY += height;
            stackHeight += height;
        }
        final boolean shouldHaveNewX = (!rightbound && maxX > Module.getLastRight()) || (rightbound && maxX < Module.getLastLeft());
        if (maxY > Module.getLastBottom() || shouldHaveNewX) {
            final double newY = (maxY > Module.getLastBottom()) ? (Module.getLastBottom() - stackHeight) : topModule.getLastY();
            final double newX = shouldHaveNewX ? (rightbound ? (Module.getLastLeft() + stackWidth) : (Module.getLastRight() - stackWidth)) : topModule.getLastX();
            EnumModuleRegion newRegion = this.getRegion(newX + topModule.getWidth() / 2.0, newY + topModule.getHeight() / 2.0);
            final EnumModuleRegion oldRegion = topModule.getRegion(this.displayType.ordinal());
            if (newRegion != null && newRegion != oldRegion) {
                topModule.setRegion(this.displayType.ordinal(), newRegion);
            }
            else if (newRegion == null) {
                newRegion = oldRegion;
            }
            topModule.setX(this.displayType.ordinal(), newRegion.getOffsetX(newX, Module.getLastLeft(), Module.getLastRight()));
            topModule.setY(this.displayType.ordinal(), newRegion.getOffsetY(newY, Module.getLastTop(), Module.getLastBottom()));
            topModule.getModuleConfigElement().setX(this.displayType.ordinal(), topModule.getX(this.displayType.ordinal()));
            topModule.getModuleConfigElement().setY(this.displayType.ordinal(), topModule.getY(this.displayType.ordinal()));
            topModule.getModuleConfigElement().setRegion(this.displayType.ordinal(), topModule.getRegion(this.displayType.ordinal()));
        }
    }
    
    private EnumModuleRegion getRegion(double middleX, double middleY) {
        if (middleX < Module.getLastLeft()) {
            middleX = Module.getLastLeft();
        }
        if (middleX > Module.getLastRight()) {
            middleX = Module.getLastRight();
        }
        if (middleY < Module.getLastTop()) {
            middleY = Module.getLastTop();
        }
        if (middleY > Module.getLastBottom()) {
            middleY = Module.getLastBottom();
        }
        for (final EnumModuleRegion regions : EnumModuleRegion.values()) {
            if (regions.getVerticalPosition() == 3 && regions.isInArea(middleX, middleY, Module.getLastLeft(), Module.getLastRight(), Module.getLastTop(), Module.getLastBottom())) {
                return regions;
            }
            if (regions.isInArea(middleX, middleY, Module.getLastLeft(), Module.getLastRight(), Module.getLastTop(), Module.getLastBottom())) {
                return regions;
            }
        }
        return null;
    }
    
    public double getMouseX() {
        return this.mouseX;
    }
    
    public double getMouseY() {
        return this.mouseY;
    }
    
    public List<CoordinatesConsumer> getMouseClickListeners() {
        return this.mouseClickListeners;
    }
    
    public List<CoordinatesConsumer> getMouseMoveListeners() {
        return this.mouseMoveListeners;
    }
    
    public List<CoordinatesConsumer> getMouseScrollListeners() {
        return this.mouseScrollListeners;
    }
    
    public List<CoordinatesConsumer> getMouseReleaseListeners() {
        return this.mouseReleaseListeners;
    }
    
    public List<KeyConsumer> getKeyTypeListeners() {
        return this.keyTypeListeners;
    }
    
    public List<Consumer<Module>> getDoubleClickModuleListeners() {
        return this.doubleClickModuleListeners;
    }
    
    public List<Consumer<Module>> getClickModuleListeners() {
        return this.clickModuleListeners;
    }
    
    public boolean isShowAllModules() {
        return this.showAllModules;
    }
    
    public boolean isMovableModules() {
        return this.movableModules;
    }
    
    public void setFocusedModule(final Module focusedModule) {
        this.focusedModule = focusedModule;
    }
    
    public Module getDraggingModule() {
        return this.draggingModule;
    }
    
    static {
        INSTANCES = new ArrayList<ModuleGui>();
        ALIGNMENT_COLOR = new Color(246, 255, 0).getRGB();
    }
    
    public interface ScalingEnabledCallback
    {
        boolean get();
    }
    
    public interface KeyConsumer
    {
        void accept(final char p0, final int p1);
    }
    
    public interface CoordinatesConsumer
    {
        void accept(final int p0, final int p1, final int p2, final EnumDisplayType p3);
    }
}
