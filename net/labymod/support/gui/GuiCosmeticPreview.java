//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.gui;

import net.labymod.gui.elements.*;
import net.labymod.user.cosmetic.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.main.*;
import net.labymod.user.cosmetic.remote.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;
import org.lwjgl.opengl.*;
import net.labymod.utils.*;
import org.lwjgl.input.*;
import javax.imageio.*;
import java.awt.image.*;
import net.labymod.support.util.*;

public class GuiCosmeticPreview extends blk implements Runnable
{
    public static final GuiCosmeticPreview INSTANCE;
    private blk lastScreen;
    private ModTextField fieldExternal;
    private ModTextField fieldInternal;
    private DropDownMenu<CosmeticRenderer<CosmeticData>> dropDownMenu;
    private File file;
    private int textureId;
    private long prevLastModified;
    private boolean animation;
    private boolean darkMode;
    private float pausedTickValue;
    private boolean currentDragging;
    private boolean currentMoving;
    private double mouseClickedX;
    private double mouseClickedY;
    private double dragPreviewX;
    private double dragPreviewY;
    private double clickedYaw;
    private double startMoveClickX;
    private double startMoveClickY;
    private double moveX;
    private double moveY;
    private double zoomValue;
    protected Map<Integer, CosmeticRenderer<CosmeticData>> cosmeticRenderers;
    private ModelCosmetics modelCosmetics;
    private CosmeticRenderer<CosmeticData> cosmeticRenderer;
    private CosmeticData cosmeticData;
    
    private GuiCosmeticPreview() {
        this.animation = true;
        this.darkMode = true;
        this.pausedTickValue = 0.0f;
        this.currentDragging = false;
        this.currentMoving = false;
        this.dragPreviewX = 0.0;
        this.dragPreviewY = 0.0;
        this.clickedYaw = 0.0;
        this.startMoveClickX = 0.0;
        this.startMoveClickY = 0.0;
        this.moveX = 0.0;
        this.moveY = 0.0;
        this.zoomValue = 200.0;
        this.cosmeticRenderers = new HashMap<Integer, CosmeticRenderer<CosmeticData>>();
        this.modelCosmetics = new ModelCosmetics(0.0625f, true);
        this.fieldExternal = new ModTextField(10, LabyModCore.getMinecraft().getFontRenderer(), 130, 5, 100, 20);
        (this.fieldInternal = new ModTextField(11, LabyModCore.getMinecraft().getFontRenderer(), 235, 5, 100, 20)).setText("labymod/textures/cosmetics/");
        try {
            final CosmeticClassLoader cosmeticClassLoader = new CosmeticClassLoader();
            for (final Class<?> loadedClassInfo : cosmeticClassLoader.getCosmeticClasses()) {
                final CosmeticRenderer<CosmeticData> cosmeticRenderer = (CosmeticRenderer<CosmeticData>)loadedClassInfo.newInstance();
                cosmeticRenderer.addModels(this.modelCosmetics, 0.0625f);
                this.cosmeticRenderers.put(cosmeticRenderer.getCosmeticId(), cosmeticRenderer);
            }
            LabyMod.getInstance().getUserManager().getRemoteCosmeticLoader().getAsync(new IRemoteCallback() {
                @Override
                public void load(final CosmeticRenderer<?> renderer) {
                    renderer.addModels(GuiCosmeticPreview.this.modelCosmetics, 0.0625f);
                    GuiCosmeticPreview.this.cosmeticRenderers.put(renderer.getCosmeticId(), (CosmeticRenderer<CosmeticData>)renderer);
                    GuiCosmeticPreview.this.b();
                }
                
                @Override
                public void unload(final CosmeticRenderer<?> renderer) {
                    GuiCosmeticPreview.this.cosmeticRenderers.remove(renderer.getCosmeticId());
                    GuiCosmeticPreview.this.b();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 0L, 1L, TimeUnit.SECONDS);
    }
    
    public void b() {
        super.b();
        final CosmeticRenderer<CosmeticData> selected = (CosmeticRenderer<CosmeticData>)((this.dropDownMenu != null) ? ((CosmeticRenderer)this.dropDownMenu.getSelected()) : null);
        (this.dropDownMenu = (DropDownMenu<CosmeticRenderer<CosmeticData>>)new DropDownMenu("", 5, 5, 120, 20)).setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                final CosmeticRenderer<CosmeticData> cosmetic = (CosmeticRenderer<CosmeticData>)object;
                LabyMod.getInstance().getDrawUtils().drawString(ModColor.cl('c') + cosmetic.getCosmeticId() + ModColor.cl('7') + ": " + ModColor.cl((cosmetic == GuiCosmeticPreview.this.cosmeticRenderer) ? 'e' : 'f') + cosmetic.getCosmeticName(), x, y);
            }
        });
        for (final Map.Entry<Integer, CosmeticRenderer<CosmeticData>> entry : this.cosmeticRenderers.entrySet()) {
            this.dropDownMenu.addOption((Object)entry.getValue());
        }
        this.dropDownMenu.setSelected((Object)selected);
        this.fieldExternal.setMaxStringLength(700);
        this.fieldInternal.setMaxStringLength(700);
        this.fieldExternal.setPlaceHolder("External png file");
        this.fieldInternal.setPlaceHolder("Internal resource");
        this.fieldExternal.setCursorPositionEnd();
        this.fieldInternal.setCursorPositionEnd();
        Keyboard.enableRepeatEvents(true);
        this.n.clear();
        this.n.add(new bja(1, this.l - 85, 5, 80, 20, "Animation: " + (this.animation ? (ModColor.cl('a') + "ON") : (ModColor.cl('c') + "OFF"))));
        this.n.add(new bja(2, this.l - 85 - 85, 5, 80, 20, "Dark mode: " + (this.darkMode ? (ModColor.cl('a') + "ON") : (ModColor.cl('c') + "OFF"))));
        this.n.add(new bja(3, this.l - 55, 30, 50, 20, "Refresh"));
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 1: {
                this.animation = !this.animation;
                button.j = "Animation: " + (this.animation ? (ModColor.cl('a') + "ON") : (ModColor.cl('c') + "OFF"));
                this.pausedTickValue = this.getCurrentTickValue();
                break;
            }
            case 2: {
                this.darkMode = !this.darkMode;
                button.j = "Dark Mode: " + (this.darkMode ? (ModColor.cl('a') + "ON") : (ModColor.cl('c') + "OFF"));
                break;
            }
            case 3: {
                button.l = false;
                LabyMod.getInstance().getUserManager().refresh();
                this.cosmeticRenderer = null;
                try {
                    this.select((CosmeticRenderer<CosmeticData>)this.dropDownMenu.getSelected());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        if (LabyMod.getInstance().isInGame()) {
            bir.a(0, 0, this.l, this.m, this.darkMode ? ModColor.toRGB(54, 57, 63, 255) : ModColor.toRGB(180, 180, 180, 255));
            final bud player = LabyModCore.getMinecraft().getPlayer();
            if (this.cosmeticRenderer != null) {
                bus.G();
                bus.b(this.l / 2 + this.moveX, this.m / 2 + this.moveY, 500.0);
                bus.a(this.zoomValue, this.zoomValue, this.zoomValue);
                bus.b((float)(-this.dragPreviewX) + 180.0f, 0.0f, 1.0f, 0.0f);
                bus.b((float)this.dragPreviewY, 1.0f, 0.0f, 0.0f);
                bus.l();
                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                bus.a(0);
                bus.a(1);
                bus.h();
                bus.a(1032, 5634);
                LabyModCore.getRenderImplementation().cullFaceBack();
                final float pitch = (float)((this.dragPreviewY - 180.0) % 360.0) - 180.0f;
                final float prevPitch = player.w;
                final bud bud = player;
                final bud bud2 = player;
                final float n = (pitch < 0.0f) ? (pitch + 360.0f) : pitch;
                bud2.w = n;
                bud.y = n;
                bus.b(-1.0f, 1.0f, 1.0f);
                this.cosmeticRenderer.render(this.modelCosmetics, (vg)player, this.cosmeticData, 0.0625f, 0.0f, 0.0f, this.animation ? this.getCurrentTickValue() : this.pausedTickValue, 0.0f, 0.0f, partialTicks, false);
                final bud bud3 = player;
                final bud bud4 = player;
                final float n2 = prevPitch;
                bud4.w = n2;
                bud3.y = n2;
                bus.a(770, 771, 1, 0);
                bus.y();
                bus.j(7424);
                bus.e();
                bus.l();
                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                bus.l();
                bus.I();
                bus.H();
            }
            bus.c(0.0f, 0.0f, 800.0f);
            this.dropDownMenu.draw(mouseX, mouseY);
            this.fieldExternal.drawTextBox();
            this.fieldInternal.drawTextBox();
        }
        else {
            draw.drawCenteredString("Not ingame", this.l / 2, this.m / 2);
        }
        this.a(mouseX, mouseY, 0, 0L);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.fieldExternal.mouseClicked(mouseX, mouseY, mouseButton);
        this.fieldInternal.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.dropDownMenu.onClick(mouseX, mouseY, mouseButton)) {
            try {
                final CosmeticRenderer<CosmeticData> selected = (CosmeticRenderer<CosmeticData>)this.dropDownMenu.getSelected();
                if (selected != null) {
                    this.select(selected);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mouseButton == 0 && !this.dropDownMenu.isOpen()) {
            this.currentDragging = true;
            this.mouseClickedX = mouseX + this.dragPreviewX;
            this.mouseClickedY = ((this.clickedYaw > 180.0) ? (-mouseY) : mouseY) + this.dragPreviewY;
            this.clickedYaw = (this.dragPreviewX + 90.0) % 360.0;
        }
        if (mouseButton == 1 || mouseButton == 2) {
            this.startMoveClickX = -this.moveX + mouseX;
            this.startMoveClickY = -this.moveY + mouseY;
            this.currentMoving = true;
        }
    }
    
    private void select(final CosmeticRenderer<CosmeticData> selected) throws Exception {
        final int id = selected.getCosmeticId();
        final CosmeticRenderer<CosmeticData> cosmeticRenderer = this.cosmeticRenderers.get(id);
        if (cosmeticRenderer != null && this.cosmeticRenderer != cosmeticRenderer) {
            this.cosmeticRenderer = cosmeticRenderer;
            final Class<?> dataClass = LabyMod.getInstance().getUserManager().getCosmeticIdToClassData().get(id);
            this.cosmeticData = (CosmeticData)dataClass.newInstance();
            this.zoomValue = 200.0;
            this.dragPreviewX = 0.0;
            this.dragPreviewY = 0.0;
            this.moveX = 0.0;
            this.moveY = 0.0;
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.dropDownMenu.onDrag(mouseX, mouseY, clickedMouseButton);
        if (this.currentDragging) {
            this.dragPreviewX = (-mouseX + this.mouseClickedX) % 360.0;
            this.dragPreviewY = ((this.clickedYaw > 180.0) ? mouseY : (-mouseY)) + this.mouseClickedY;
        }
        if (this.currentMoving) {
            this.moveX = -this.startMoveClickX + mouseX;
            this.moveY = -this.startMoveClickY + mouseY;
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.dropDownMenu.onRelease(mouseX, mouseY, state);
        if (state == 0) {
            this.currentDragging = false;
            this.clickedYaw = (this.dragPreviewX + 90.0) % 360.0;
        }
        if (state == 1 || state == 2) {
            this.currentMoving = false;
        }
    }
    
    public void k() throws IOException {
        super.k();
        this.dropDownMenu.onScroll();
        if (!this.dropDownMenu.isOpen()) {
            final int mouseScroll = Mouse.getDWheel();
            if (mouseScroll > 0) {
                this.zoomValue += 30.0;
            }
            if (mouseScroll < 0) {
                this.zoomValue -= 30.0;
            }
            if (this.zoomValue < 100.0) {
                this.zoomValue = 100.0;
            }
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1 && this.lastScreen != null) {
            bib.z().a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
        if (this.fieldExternal.textboxKeyTyped(typedChar, keyCode)) {
            final String path = this.fieldExternal.getText();
            final File file = new File(path);
            final boolean available = file.exists() && path.endsWith(".png");
            this.fieldExternal.setTextColor(available ? ModColor.GREEN.getColor().getRGB() : ModColor.RED.getColor().getRGB());
            this.file = (available ? file : null);
            this.prevLastModified = (available ? file.lastModified() : 0L);
        }
        if (this.fieldInternal.textboxKeyTyped(typedChar, keyCode)) {
            final String resourceName = this.fieldInternal.getText();
            final cds texture = bib.z().N().b(new nf(resourceName));
            final boolean available = texture != null;
            this.fieldInternal.setTextColor(available ? ModColor.GREEN.getColor().getRGB() : ModColor.RED.getColor().getRGB());
            this.textureId = (available ? texture.b() : -1);
        }
    }
    
    public void e() {
        super.e();
        this.fieldExternal.updateCursorCounter();
        this.fieldInternal.updateCursorCounter();
    }
    
    private float getCurrentTickValue() {
        return System.currentTimeMillis() % 10000000L / 50.0f;
    }
    
    public void run() {
        if (this.file == null || this.textureId == -1 || !this.file.exists()) {
            return;
        }
        final long lastModified = this.file.lastModified();
        if (lastModified == this.prevLastModified) {
            return;
        }
        this.prevLastModified = lastModified;
        try {
            final BufferedImage bufferedImage = ImageIO.read(this.file);
            final int[] dynamicTextureData = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
            bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), dynamicTextureData, 0, bufferedImage.getWidth());
            bib.z().a((Runnable)new Runnable() {
                @Override
                public void run() {
                    cdt.a(GuiCosmeticPreview.this.textureId, bufferedImage.getWidth(), bufferedImage.getHeight());
                    cdt.a(GuiCosmeticPreview.this.textureId, dynamicTextureData, bufferedImage.getWidth(), bufferedImage.getHeight());
                    Debug.log(Debug.EnumDebugMode.COSMETIC_IMAGE_MANAGER, "Source " + GuiCosmeticPreview.this.file.getAbsolutePath() + " changed. Updated texture object id " + GuiCosmeticPreview.this.textureId);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static GuiCosmeticPreview create(final blk lastScreen) {
        GuiCosmeticPreview.INSTANCE.lastScreen = lastScreen;
        return GuiCosmeticPreview.INSTANCE;
    }
    
    static {
        INSTANCE = new GuiCosmeticPreview();
    }
}
