//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.skin;

import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.support.util.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.support.gui.*;
import java.io.*;
import org.lwjgl.input.*;

public class GuiSkinCustomization extends blk
{
    private blk lastScreen;
    private long screenCalled;
    private boolean enabledPreviewDragging;
    private boolean currentDragging;
    private boolean currentMoving;
    private boolean mouseOverPreview;
    private double mouseClickedX;
    private double mouseClickedY;
    private double dragPreviewX;
    private double dragPreviewY;
    private double clickedYaw;
    private double xRotationGoal;
    private double yRotationGoal;
    private long dragStartCalled;
    private long lastGoalTracking;
    private double startMoveClickX;
    private double startMoveClickY;
    private double moveX;
    private double moveY;
    private double zoomValue;
    private List<SkinCustomizationSettingElement> settingElements;
    
    public GuiSkinCustomization(final blk lastScreen) {
        this.screenCalled = 0L;
        this.enabledPreviewDragging = false;
        this.currentDragging = false;
        this.currentMoving = false;
        this.mouseOverPreview = false;
        this.dragPreviewX = 0.0;
        this.dragPreviewY = 0.0;
        this.clickedYaw = 0.0;
        this.xRotationGoal = 0.0;
        this.yRotationGoal = 0.0;
        this.dragStartCalled = 0L;
        this.lastGoalTracking = 0L;
        this.startMoveClickX = 0.0;
        this.startMoveClickY = 0.0;
        this.moveX = 0.0;
        this.moveY = 0.0;
        this.zoomValue = 50.0;
        this.settingElements = new ArrayList<SkinCustomizationSettingElement>();
        this.lastScreen = lastScreen;
        this.screenCalled = System.currentTimeMillis();
    }
    
    public void b() {
        super.b();
        this.settingElements.clear();
        this.settingElements.add(new SkinLayerSettingElement(this, null, "hat", new aee[] { aee.g }));
        final SkinLayerSettingElement jacketElement = new SkinLayerSettingElement(this, LanguageManager.translate("skinpart_jacket"), "jacket", new aee[] { aee.b, aee.d, aee.c });
        jacketElement.addSubSetting(new SkinLayerSettingElement(this, null, "right_sleeve", new aee[] { aee.d }));
        jacketElement.addSubSetting(new SkinLayerSettingElement(this, null, "jacket_base", new aee[] { aee.b }));
        jacketElement.addSubSetting(new SkinLayerSettingElement(this, null, "left_sleeve", new aee[] { aee.c }));
        this.settingElements.add(jacketElement);
        final SkinLayerSettingElement pantsElement = new SkinLayerSettingElement(this, LanguageManager.translate("skinpart_pants"), "pants", new aee[] { aee.f, aee.e });
        pantsElement.addSubSetting(new SkinLayerSettingElement(this, null, "right_pants", new aee[] { aee.f }));
        pantsElement.addSubSetting(new SkinLayerSettingElement(this, null, "left_pants", new aee[] { aee.e }));
        this.settingElements.add(pantsElement);
        this.settingElements.add(new SkinLayerSettingElement(this, null, "cape", new aee[] { aee.a }));
        if (!Source.ABOUT_MC_VERSION.startsWith("1.8")) {
            this.settingElements.add(new SkinHandSettingElement(LanguageManager.translate("skinpart_hand"), "hand"));
        }
        int listElementWidth = this.l / 3;
        if (listElementWidth < 130) {
            listElementWidth = 130;
        }
        if (listElementWidth > 200) {
            listElementWidth = 200;
        }
        this.n.add(new bja(5, (LabyMod.getInstance().isInGame() ? (listElementWidth / 2) : (this.l / 2 - 10)) - listElementWidth / 2 + 10, this.m - 30, listElementWidth, 20, cey.a("gui.done", new Object[0])));
        this.n.add(new bja(6, this.l - 100, 4, 97, 20, LanguageManager.translate("button_refresh_labymod")));
        if (this.getOptifineCapeScreen() != null) {
            this.n.add(new bja(7, this.l - 100, 26, 97, 20, LanguageManager.translate("optifine_cape")));
        }
        if (Debug.isActive()) {
            this.n.add(new bja(8, this.l - 100, this.m - 25, 97, 20, "Cosmetic Preview"));
        }
    }
    
    public void m() {
        super.m();
        bib.z().t.b();
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        double elementWidth = this.l / 3;
        double elementHeight = this.m / 70.0 * this.settingElements.size();
        if (elementWidth < 130.0) {
            elementWidth = 130.0;
        }
        if (elementHeight < 17.0) {
            elementHeight = 17.0;
        }
        if (elementWidth > 200.0) {
            elementWidth = 200.0;
        }
        if (LabyMod.getInstance().isInGame()) {
            draw.drawOverlayBackground(0, 0, (int)elementWidth + 20, this.m, 32);
            draw.drawGradientShadowRight(elementWidth + 20.0, 0.0, this.m);
        }
        int totalHeight = 0;
        for (final SkinCustomizationSettingElement element : this.settingElements) {
            totalHeight += (int)elementHeight;
            for (final SkinLayerSettingElement subElement : element.getSubSettingElements()) {
                totalHeight += (int)elementHeight;
            }
            totalHeight += 5;
        }
        final int posX = (int)(LabyMod.getInstance().isInGame() ? 10.0 : (this.l / 2 - elementWidth / 2.0));
        int posY = (this.m - totalHeight) / 2 - 10;
        for (final SkinCustomizationSettingElement element2 : this.settingElements) {
            element2.draw(false, posX, posY, elementWidth, elementHeight, mouseX, mouseY);
            posY += (int)elementHeight;
            posY += 2;
            int subIndex = 1;
            for (final SkinLayerSettingElement subElement2 : element2.getSubSettingElements()) {
                final int lineColor = ModColor.toRGB(150, 150, 150, 155);
                draw.drawRect(posX + 20 - 10, posY, posX + 20 - 8, posY + elementHeight / 2.0, lineColor);
                draw.drawRect(posX + 20 - 10, posY + elementHeight / 2.0, posX + 20, posY + elementHeight / 2.0 + 2.0, lineColor);
                if (element2.getSubSettingElements().size() != subIndex) {
                    draw.drawRect(posX + 20 - 10, posY + elementHeight / 2.0 + 2.0, posX + 20 - 8, posY + elementHeight + 2.0, lineColor);
                }
                subElement2.draw(true, posX + 20, posY, elementWidth - 50.0, elementHeight, mouseX, mouseY);
                posY += (int)(elementHeight + 2.0);
                ++subIndex;
            }
            posY += 2;
        }
        draw.drawCenteredString(cey.a("options.skinCustomisation.title", new Object[0]), LabyMod.getInstance().isInGame() ? (10.0 + elementWidth / 2.0) : ((double)(this.l / 2)), posY - totalHeight - 17);
        final double rightSideMiddle = elementWidth + 20.0 + (this.l - elementWidth + 20.0) / 2.0;
        if (LabyModCore.getMinecraft().getPlayer() != null) {
            final int entityPosX = (int)rightSideMiddle - 20;
            int entityPosY = this.m / 2 + 80;
            double rotationIntroAnimation = (double)(this.screenCalled + 200L - System.currentTimeMillis());
            if (rotationIntroAnimation <= 0.0) {
                rotationIntroAnimation = 0.0;
            }
            else {
                rotationIntroAnimation = rotationIntroAnimation / 50.0 * rotationIntroAnimation / 50.0;
            }
            double rotationIntroAnimationPointer = (this.screenCalled + 500L - System.currentTimeMillis() + 200L) / 200.0;
            if (this.enabledPreviewDragging) {
                rotationIntroAnimationPointer = (System.currentTimeMillis() - this.dragStartCalled) / 200.0;
            }
            if (rotationIntroAnimationPointer <= 1.0) {
                rotationIntroAnimationPointer = 1.0;
            }
            rotationIntroAnimationPointer *= rotationIntroAnimationPointer;
            double mousePointX = -mouseX + entityPosX;
            double mousePointY = -mouseY + entityPosY - 130;
            if (rotationIntroAnimationPointer > 0.0) {
                mousePointX /= rotationIntroAnimationPointer;
            }
            if (rotationIntroAnimationPointer > 0.0) {
                mousePointY /= rotationIntroAnimationPointer;
            }
            double dragRotationX = 0.0;
            double dragRotationY = 0.0;
            if (this.enabledPreviewDragging) {
                dragRotationX = -this.dragPreviewX;
                dragRotationY = -this.dragPreviewY;
            }
            if (dragRotationY == 0.0) {
                ++dragRotationY;
            }
            entityPosY -= 80;
            DrawUtils.drawEntityOnScreen((int)(entityPosX + this.moveX), (int)(entityPosY + this.moveY), (int)this.zoomValue, (float)(int)mousePointX, (float)(int)mousePointY, (int)(dragRotationX + rotationIntroAnimation), (int)dragRotationY, 0, (vp)LabyModCore.getMinecraft().getPlayer());
        }
        this.mouseOverPreview = (mouseX > rightSideMiddle + this.moveX - 60.0 && mouseX < rightSideMiddle + this.moveX + 60.0);
        super.a(mouseX, mouseY, partialTicks);
        if (this.currentMoving || this.currentDragging) {
            this.a(mouseX, mouseY, 0, 0L);
        }
        if ((this.xRotationGoal != 0.0 || this.yRotationGoal != 0.0) && this.lastGoalTracking < System.currentTimeMillis()) {
            this.lastGoalTracking = System.currentTimeMillis() + 15L;
            final int f = 10;
            if (this.xRotationGoal > this.dragPreviewX + f) {
                this.dragPreviewX += f;
            }
            if (this.xRotationGoal < this.dragPreviewX - f) {
                this.dragPreviewX -= f;
            }
            if (this.yRotationGoal > this.dragPreviewY + f) {
                this.dragPreviewY += f;
            }
            if (this.yRotationGoal < this.dragPreviewY - f) {
                this.dragPreviewY -= f;
            }
        }
    }
    
    public void e() {
        super.e();
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 5: {
                bib.z().a(this.lastScreen);
                break;
            }
            case 6: {
                button.l = false;
                LabyMod.getInstance().getUserManager().refresh();
                break;
            }
            case 7: {
                final blk screen = this.getOptifineCapeScreen();
                if (screen != null) {
                    bib.z().a(screen);
                    break;
                }
                break;
            }
            case 8: {
                bib.z().a((blk)GuiCosmeticPreview.create(this));
                break;
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (!this.enabledPreviewDragging && this.mouseOverPreview && mouseButton == 0) {
            this.enabledPreviewDragging = true;
            this.dragStartCalled = System.currentTimeMillis();
        }
        if (this.enabledPreviewDragging && this.mouseOverPreview && mouseButton == 0) {
            this.currentDragging = true;
            this.mouseClickedX = mouseX + this.dragPreviewX;
            this.mouseClickedY = ((this.clickedYaw > 180.0) ? (-mouseY) : mouseY) + this.dragPreviewY;
            this.clickedYaw = (this.dragPreviewX + 90.0) % 360.0;
            this.xRotationGoal = 0.0;
            this.yRotationGoal = 0.0;
        }
        if (mouseButton == 2) {
            this.startMoveClickX = -this.moveX + mouseX;
            this.startMoveClickY = -this.moveY + mouseY;
            this.currentMoving = true;
        }
        for (final SkinCustomizationSettingElement element : this.settingElements) {
            element.getCheckBox().mouseClicked(mouseX, mouseY, mouseButton);
            for (final SkinLayerSettingElement subElement : element.getSubSettingElements()) {
                subElement.getCheckBox().mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        if (state == 0) {
            this.currentDragging = false;
            this.clickedYaw = (this.dragPreviewX + 90.0) % 360.0;
        }
        if (state == 2) {
            this.currentMoving = false;
        }
    }
    
    public void k() throws IOException {
        super.k();
        final int mouseScroll = Mouse.getDWheel();
        if (mouseScroll > 0) {
            this.zoomValue += 10.0;
        }
        if (mouseScroll < 0) {
            this.zoomValue -= 10.0;
        }
        if (!Debug.isActive()) {
            if (this.zoomValue < 50.0) {
                this.zoomValue = 50.0;
            }
            final int maxZoom = 150;
            if (this.zoomValue > maxZoom) {
                this.zoomValue = maxZoom;
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (this.currentDragging) {
            this.dragPreviewX = (-mouseX + this.mouseClickedX) % 360.0;
            this.dragPreviewY = ((this.clickedYaw > 180.0) ? mouseY : (-mouseY)) + this.mouseClickedY;
            if (!Debug.isActive()) {
                if (this.dragPreviewY > 45.0) {
                    this.dragPreviewY = 45.0;
                }
                if (this.dragPreviewY < -45.0) {
                    this.dragPreviewY = -45.0;
                }
            }
        }
        if (this.currentMoving) {
            this.moveX = -this.startMoveClickX + mouseX;
            this.moveY = -this.startMoveClickY + mouseY;
            if (!Debug.isActive()) {
                if (this.moveX < -150.0) {
                    this.moveX = -150.0;
                }
                if (this.moveX > 150.0) {
                    this.moveX = 150.0;
                }
                if (this.moveY < -150.0) {
                    this.moveY = -150.0;
                }
                if (this.moveY > 150.0) {
                    this.moveY = 150.0;
                }
            }
        }
    }
    
    public void updatePart(final aee part, final boolean value) {
        bib.z().t.a(part, value);
        if (this.enabledPreviewDragging) {
            if (part == aee.a) {
                this.xRotationGoal = 180.0;
                this.yRotationGoal = 0.0;
            }
            else if (part == aee.g) {
                this.xRotationGoal = 20.0;
                this.yRotationGoal = -20.0;
            }
            else if (part == aee.f || part == aee.e) {
                this.xRotationGoal = 20.0;
                this.yRotationGoal = 10.0;
            }
            else {
                this.xRotationGoal = 20.0;
                this.yRotationGoal = -5.0;
            }
        }
    }
    
    private blk getOptifineCapeScreen() {
        try {
            final Class<?> optifineClass = Class.forName("net.optifine.gui.GuiScreenCapeOF");
            if (optifineClass != null) {
                return (blk)optifineClass.getConstructor(blk.class).newInstance(this);
            }
        }
        catch (Exception ex) {}
        return null;
    }
}
