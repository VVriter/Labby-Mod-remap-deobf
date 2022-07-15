//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.gui;

import java.lang.reflect.*;
import net.labymod.gui.elements.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import net.labymod.main.lang.*;
import net.labymod.user.sticker.*;
import net.labymod.user.sticker.data.*;
import org.lwjgl.opengl.*;
import net.labymod.user.*;
import net.labymod.utils.*;
import org.lwjgl.input.*;
import net.labymod.user.cosmetic.custom.handler.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.utils.manager.*;
import net.labymod.main.*;
import java.io.*;

public class StickerSelectorGui extends bir
{
    private static final nf POP_SOUND;
    private static final int ANIMATION_SPEED = 100;
    private static Field fieldPressTime;
    private static Field fieldLeftClickCounter;
    private boolean open;
    private long lastOpened;
    private int selectedItemIndex;
    private ModTextField fieldSearch;
    private boolean hoverSearchBar;
    private float lockedYaw;
    private float lockedPitch;
    private boolean prevCrosshairState;
    private short lastHoveredStickerId;
    private int hotkeySelectedSticker;
    private short lastAcceptedHoveredStickerId;
    private long lastHoveredStickerChanged;
    private int hoverStickerIndex;
    private double lastSelectorWidth;
    private double lastSelectorHeight;
    private List<Sticker> filteredStickers;
    private boolean searchOpened;
    private List<String> packTitles;
    private int searchMouseX;
    private int searchMouseY;
    private int page;
    private int acceptedPage;
    private int animationState;
    private long pageAnimation;
    
    public StickerSelectorGui() {
        this.open = false;
        this.lockedYaw = 0.0f;
        this.lockedPitch = 0.0f;
        this.lastHoveredStickerId = -1;
        this.hotkeySelectedSticker = -1;
        this.lastAcceptedHoveredStickerId = -1;
        this.hoverStickerIndex = -1;
        this.searchOpened = false;
        this.page = 0;
        this.acceptedPage = 0;
        this.animationState = 0;
        this.pageAnimation = 0L;
    }
    
    public void open() {
        if (this.open) {
            return;
        }
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        this.open = true;
        this.page = 0;
        this.selectedItemIndex = LabyModCore.getMinecraft().getPlayer().bv.d;
        this.hotkeySelectedSticker = -1;
        this.lastAcceptedHoveredStickerId = -1;
        this.lockedYaw = player.v;
        this.lockedPitch = player.w;
        (this.fieldSearch = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 0, 20)).setBlackBox(false);
        this.fieldSearch.setEnableBackgroundDrawing(false);
        this.searchOpened = false;
        this.prevCrosshairState = LabyMod.getInstance().getLabyModAPI().isCrosshairHidden();
        LabyMod.getInstance().getLabyModAPI().setCrosshairHidden(true);
        if (StickerSelectorGui.fieldLeftClickCounter == null) {
            try {
                (StickerSelectorGui.fieldLeftClickCounter = ReflectionHelper.findField(bib.class, LabyModCore.getMappingAdapter().getLeftClickCounterMappings())).setAccessible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StickerSelectorGui.fieldPressTime == null) {
            try {
                (StickerSelectorGui.fieldPressTime = ReflectionHelper.findField(bhy.class, LabyModCore.getMappingAdapter().getPressTimeMappings())).setAccessible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.filter("");
        if (System.currentTimeMillis() - this.lastOpened < 300L) {
            bib.z().a((blk)new SearchGui(this, this.fieldSearch));
            this.searchOpened = true;
            this.animationState = 0;
        }
        else {
            this.searchOpened = false;
        }
        this.lastOpened = System.currentTimeMillis();
    }
    
    public void close() {
        if (!this.open) {
            return;
        }
        if (!this.searchOpened && this.hoverSearchBar) {
            this.searchOpened = true;
            bib.z().a((blk)new SearchGui(this, this.fieldSearch));
            return;
        }
        this.searchOpened = false;
        this.open = false;
        LabyMod.getInstance().getLabyModAPI().setCrosshairHidden(this.prevCrosshairState);
        this.updateScrollLock(false);
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        player.v = this.lockedYaw;
        player.w = this.lockedPitch;
        if (this.lastHoveredStickerId != -1) {
            LabyModCore.getMinecraft().playSound(StickerSelectorGui.POP_SOUND, 1.0f);
            LabyMod.getInstance().getStickerRegistry().playSticker(LabyMod.getInstance().getUserManager().getUser(player.bm()), this.lastHoveredStickerId);
            final ModSettings settings = LabyMod.getSettings();
            try {
                boolean swap = false;
                for (int i = 0; i < settings.stickerHistory.length; ++i) {
                    if (settings.stickerHistory[i] == this.lastHoveredStickerId) {
                        swap = true;
                    }
                }
                if (!swap) {
                    settings.stickerHistory[settings.stickerHistory.length - 1] = -1;
                    for (int i = settings.stickerHistory.length - 2; i >= 0; --i) {
                        if (settings.stickerHistory[i + 1] == -1) {
                            settings.stickerHistory[i + 1] = settings.stickerHistory[i];
                            settings.stickerHistory[i] = -1;
                        }
                    }
                    settings.stickerHistory[0] = this.lastHoveredStickerId;
                    LabyMod.getMainConfig().save();
                }
            }
            catch (Exception error) {
                error.printStackTrace();
                settings.stickerHistory = new short[] { -1, -1, -1, -1, -1 };
                LabyMod.getMainConfig().save();
            }
        }
    }
    
    private void updateScrollLock(final boolean locked) {
        final bid gameSettings = bib.z().t;
        if (gameSettings == null) {
            return;
        }
        final bhy keyBinding = gameSettings.ap[this.selectedItemIndex];
        if (keyBinding == null) {
            return;
        }
        final int keyCode = keyBinding.j();
        bhy.a(keyCode, locked);
        if (locked) {
            bhy.a(keyCode);
        }
        else {
            try {
                if (StickerSelectorGui.fieldPressTime != null) {
                    StickerSelectorGui.fieldPressTime.setInt(keyBinding, 0);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void filter(String searchString) {
        final bud player = LabyModCore.getMinecraft().getPlayer();
        final User user = LabyMod.getInstance().getUserManager().getUser(player.bm());
        final List<Short> stickerPacks = user.getStickerPacks();
        final List<Sticker> filteredStickers = new ArrayList<Sticker>();
        final StickerRegistry stickerRegistry = LabyMod.getInstance().getStickerRegistry();
        if (stickerRegistry.getStickerData() != null && stickerRegistry.getStickerData().getPacks() != null) {
            if (searchString.isEmpty()) {
                final List<String> packTitles = new ArrayList<String>();
                final ModSettings settings = LabyMod.getSettings();
                for (int i = 0; i < settings.stickerHistory.length; ++i) {
                    final short id = settings.stickerHistory[i];
                    if (id != -1 || !packTitles.isEmpty()) {
                        filteredStickers.add((id == -1) ? null : stickerRegistry.getSticker(id));
                        if (packTitles.isEmpty()) {
                            packTitles.add(LanguageManager.translate("sticker_history"));
                        }
                    }
                }
                for (final StickerPack stickerPack : stickerRegistry.getStickerData().getPacks()) {
                    if (stickerPacks.contains(stickerPack.getId())) {
                        for (final Sticker sticker : stickerPack.getStickers()) {
                            filteredStickers.add(sticker);
                        }
                        packTitles.add(stickerPack.getName());
                    }
                }
                this.packTitles = packTitles;
                this.filteredStickers = filteredStickers;
            }
            else {
                searchString = searchString.toLowerCase();
                for (final StickerPack stickerPack2 : stickerRegistry.getStickerData().getPacks()) {
                    if (stickerPacks.contains(stickerPack2.getId())) {
                        if (stickerPack2.getName().toLowerCase().contains(searchString)) {
                            for (final Sticker sticker2 : stickerPack2.getStickers()) {
                                filteredStickers.add(sticker2);
                            }
                        }
                        else {
                            for (final Sticker sticker2 : stickerPack2.getStickers()) {
                                if (sticker2.getName().toLowerCase().contains(searchString)) {
                                    filteredStickers.add(sticker2);
                                }
                                else {
                                    for (final String tag : sticker2.getTags()) {
                                        if (tag.toLowerCase().contains(searchString)) {
                                            filteredStickers.add(sticker2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.filteredStickers = filteredStickers;
    }
    
    public void render() {
        if (!this.open) {
            return;
        }
        final bud player = LabyModCore.getMinecraft().getPlayer();
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final User user = userManager.getUser(player.bm());
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        try {
            if (StickerSelectorGui.fieldLeftClickCounter != null) {
                StickerSelectorGui.fieldLeftClickCounter.setInt(bib.z(), 2);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        long timePassed = System.currentTimeMillis() - this.lastOpened;
        if (timePassed > 2000L) {
            timePassed = 2000L;
        }
        final double stickerSize = draw.getHeight() / 8.0 - Math.exp(-timePassed / 100.0) * 10.0;
        final double padding = 2.0;
        double midX = draw.getWidth() / 2.0;
        double midY = draw.getHeight() / 2.0 - (stickerSize / 2.0 + padding);
        final double lockedX = this.lockedYaw;
        double lockedY = this.lockedPitch;
        final double radiusY = this.lastSelectorHeight / 3.0;
        if (lockedY + radiusY > 90.0) {
            lockedY = (float)(90.0 - radiusY);
        }
        if (lockedY - radiusY < -90.0) {
            lockedY = (float)(-90.0 + radiusY);
        }
        double offsetX = lockedX - player.v;
        double offsetY = lockedY - player.w;
        if (bib.z().t.aw == 0) {
            midX += offsetX;
            midY += offsetY;
        }
        final int stickerPerPage = 5;
        final int totalSticker = this.filteredStickers.size();
        final int maxPages = (int)Math.ceil(totalSticker / (double)stickerPerPage);
        final int stickerIndex = this.page * stickerPerPage;
        final int acceptedStickerIndex = this.acceptedPage * stickerPerPage;
        final double selectorWidth = stickerPerPage * (stickerSize + 1.0) - 1.0;
        draw.drawRect(midX - selectorWidth / 2.0 - padding, midY - stickerSize / 2.0 - padding, midX + selectorWidth / 2.0 + padding, midY + stickerSize / 2.0 + padding, Integer.MIN_VALUE);
        double cursorX = midX - offsetX * 1.5;
        double cursorY = midY - offsetY * 1.5 + stickerSize / 2.0 + padding;
        if (this.searchOpened) {
            cursorX = this.searchMouseX;
            cursorY = this.searchMouseY;
            offsetX = this.searchMouseX - draw.getWidth() / 2;
            offsetY = this.searchMouseY - draw.getHeight() / 2;
        }
        final int searchFieldHeight = 10;
        final int searchAreaGap = 1;
        draw.drawRect(midX - selectorWidth / 2.0 - padding, midY + stickerSize / 2.0 + padding + searchAreaGap, midX + selectorWidth / 2.0 + padding, midY + stickerSize / 2.0 + padding + searchFieldHeight + padding * 2.0 + searchAreaGap + 2.0, Integer.MIN_VALUE);
        final double fieldX = midX - selectorWidth / 2.0 + 1.0;
        final double fieldY = midY + stickerSize / 2.0 + padding + searchAreaGap + padding + 1.0;
        this.fieldSearch.width = (int)selectorWidth - 2;
        this.fieldSearch.height = searchFieldHeight;
        this.fieldSearch.xPosition = (int)fieldX + 1;
        this.fieldSearch.yPosition = (int)fieldY + 2;
        this.hoverSearchBar = (cursorX > fieldX - 1.0 && cursorX < fieldX + selectorWidth - 1.0 && cursorY > fieldY - 1.0 && cursorY < fieldY + this.fieldSearch.height + 1.0);
        draw.drawRect(fieldX - 1.0, fieldY - 1.0, fieldX + selectorWidth - 1.0, fieldY + this.fieldSearch.height + 1.0, this.hoverSearchBar ? ModColor.toRGB(255, 255, 170, 100) : Integer.MAX_VALUE);
        if (!this.fieldSearch.isFocused()) {
            draw.drawString(LanguageManager.translate("search_textbox_placeholder"), fieldX + 1.0, fieldY + 1.0);
        }
        this.drawStickerRow(midX, midY, cursorX, cursorY, stickerPerPage, totalSticker, stickerIndex, stickerSize, selectorWidth, user, this.filteredStickers, 0);
        this.drawStickerRow(midX, midY, cursorX, cursorY, stickerPerPage, totalSticker, stickerIndex, stickerSize, selectorWidth, user, this.filteredStickers, -1);
        this.drawStickerRow(midX, midY, cursorX, cursorY, stickerPerPage, totalSticker, acceptedStickerIndex, stickerSize, selectorWidth, user, this.filteredStickers, 1);
        double pageDisplayX = midX + selectorWidth / 2.0 + padding + 1.0;
        double pageDisplayY = midY - stickerSize / 2.0 - padding;
        for (int i = 0; i < maxPages; ++i) {
            final boolean selected = this.page == i;
            bus.G();
            bus.e();
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
            bib.z().N().a(ModTextures.MISC_MENU_POINT);
            draw.drawTexture(pageDisplayX, pageDisplayY, 255.0, 255.0, 3.0, 3.0, selected ? 1.1f : 0.2f);
            bus.d(1.0f, 1.0f, 1.0f);
            bus.H();
            pageDisplayY += 4.0;
            if (pageDisplayY > midY + stickerSize / 2.0 + padding) {
                pageDisplayY = midY - stickerSize / 2.0 - padding;
                pageDisplayX += 4.0;
            }
        }
        final String title = (this.packTitles == null || this.packTitles.isEmpty()) ? LanguageManager.translate("sticker_title") : this.packTitles.get(this.page);
        final double titleWidth = draw.getStringWidth(title);
        final double titleOffsetY = -15.0;
        final double titlePadding = 3.0;
        draw.drawRect(midX - titleWidth / 2.0 - titlePadding, midY - stickerSize / 2.0 + titleOffsetY - titlePadding, midX + titleWidth / 2.0 + titlePadding, midY - stickerSize / 2.0 + titleOffsetY + 7.0 + titlePadding, Integer.MIN_VALUE);
        draw.drawCenteredString(title, midX, midY - stickerSize / 2.0 + titleOffsetY);
        if (this.filteredStickers.isEmpty() && !this.searchOpened) {
            final double statusY = midY + stickerSize / 2.0 + padding * 5.0 + searchAreaGap + searchFieldHeight;
            draw.drawCenteredString(LanguageManager.translate("sticker_status_no_stickers"), midX, statusY, 0.8);
        }
        else if (maxPages > 1 && this.page == 0) {
            final double statusY = midY + stickerSize / 2.0 + padding * 5.0 + searchAreaGap + searchFieldHeight;
            draw.drawCenteredString(ModColor.cl('7') + LanguageManager.translate("sticker_info_scroll"), midX, statusY, 0.8);
        }
        if (offsetX == 0.0 && offsetY == 0.0) {
            cursorX = (int)cursorX;
            cursorY = (int)cursorY;
        }
        if (!this.searchOpened) {
            draw.drawRect(cursorX, cursorY - 4.0, cursorX + 1.0, cursorY + 5.0, Integer.MAX_VALUE);
            draw.drawRect(cursorX - 4.0, cursorY, cursorX + 5.0, cursorY + 1.0, Integer.MAX_VALUE);
        }
        this.lastSelectorWidth = selectorWidth + padding * 2.0;
        this.lastSelectorHeight = padding + stickerSize + padding + searchAreaGap + padding + searchFieldHeight + padding;
        this.handleMouseInput(maxPages - 1);
    }
    
    private void handleMouseInput(final int maxPages) {
        final double scroll = Mouse.getDWheel();
        boolean moveUp = scroll > 0.0;
        boolean moveDown = scroll < 0.0;
        if (this.hoverStickerIndex == -1 && (moveUp || moveDown)) {
            if (!moveDown || this.acceptedPage != maxPages || this.hotkeySelectedSticker != 0) {
                if (!moveUp || this.acceptedPage != 0 || this.hotkeySelectedSticker != 4) {
                    final int value = this.hotkeySelectedSticker + (moveUp ? 1 : -1);
                    this.hotkeySelectedSticker = ((value < 0) ? 4 : (value % 5));
                    if ((moveUp || value != -1) && (!moveUp || value != 5)) {
                        moveDown = false;
                        moveUp = false;
                    }
                }
            }
        }
        try {
            for (int stickerPerPage = 5, i = 0; i < stickerPerPage; ++i) {
                final int code = bib.z().t.ap[i].j();
                if (code >= 0 && Keyboard.isKeyDown(code)) {
                    this.hotkeySelectedSticker = stickerPerPage - 1 - i;
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        if (Mouse.isButtonDown(0)) {
            moveUp = true;
        }
        if (Mouse.isButtonDown(1)) {
            moveDown = true;
        }
        if (this.acceptedPage == this.page && this.animationState == 0) {
            if (moveUp && this.page > 0) {
                --this.page;
                this.animationState = -1;
                this.pageAnimation = System.currentTimeMillis();
            }
            if (moveDown && this.page < maxPages) {
                ++this.page;
                this.animationState = 1;
                this.pageAnimation = System.currentTimeMillis();
            }
        }
        else if (!moveUp && !moveDown && this.animationState != -1 && this.animationState != 1) {
            this.acceptedPage = this.page;
        }
        if ((this.animationState == -1 || this.animationState == 1) && this.pageAnimation + 100L < System.currentTimeMillis()) {
            this.animationState *= 2;
        }
        if ((this.animationState == -2 || this.animationState == 2) && this.pageAnimation + 100L < System.currentTimeMillis()) {
            this.animationState = 0;
        }
    }
    
    private void drawStickerRow(final double midX, final double midY, final double cursorX, final double cursorY, final int stickerPerPage, final int totalSticker, int stickerIndex, final double stickerSize, final double selectorWidth, final User user, final List<Sticker> stickers, final int animationChannel) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final long timePassed = System.currentTimeMillis() - this.pageAnimation;
        final double percentage = 0.01 * timePassed;
        double animation = (this.animationState == 0) ? 1.0 : Math.min(percentage, 1.0);
        final boolean increased = this.page < this.acceptedPage;
        final boolean lowBound = animationChannel == (increased ? 1 : -1);
        this.lastHoveredStickerId = -1;
        this.hoverStickerIndex = -1;
        if (animationChannel == 1) {
            animation = 1.0 - animation;
        }
        final double lowOffset = (stickerSize - 2.0) * (1.0 - animation);
        for (int index = stickerPerPage - 1; index >= 0; --index) {
            final double x = midX - selectorWidth / 2.0 + (stickerPerPage - index - 1) * (stickerSize + 1.0);
            final double y = midY - stickerSize / 2.0;
            boolean hover = cursorX > x && cursorX < x + stickerSize && cursorY > y && cursorY < y + stickerSize;
            final Sticker sticker = (stickerIndex >= totalSticker) ? null : stickers.get(stickerIndex);
            final Short id = (sticker == null) ? null : Short.valueOf(sticker.getId());
            if (hover) {
                this.hoverStickerIndex = index;
            }
            if (hover && id != null) {
                this.hotkeySelectedSticker = -1;
            }
            if (this.hotkeySelectedSticker != -1 && this.hotkeySelectedSticker == index) {
                hover = true;
            }
            double brightness = 0.0;
            if (hover && id != null) {
                brightness = 185.0 + 185.0 * -Math.exp(-(System.currentTimeMillis() - this.lastHoveredStickerChanged) / 500.0);
            }
            if (animationChannel == 0) {
                draw.drawRect(x, y, x + stickerSize, y + stickerSize, ModColor.toRGB((int)brightness, (int)brightness, (int)brightness, 50));
            }
            else {
                double currentStickerSize = stickerSize - 2.0;
                double hoverOffset = 0.0;
                if (hover && id != null) {
                    this.lastHoveredStickerId = id;
                    if (this.lastAcceptedHoveredStickerId != id) {
                        this.lastAcceptedHoveredStickerId = id;
                        this.lastHoveredStickerChanged = System.currentTimeMillis();
                    }
                }
                if (hover) {
                    final double popUp = 10.0 + 10.0 * -Math.exp(-(System.currentTimeMillis() - this.lastHoveredStickerChanged) / 100.0);
                    hoverOffset = popUp / 2.0;
                    currentStickerSize += popUp;
                }
                bus.G();
                bus.a(1.0, animation, 1.0);
                this.drawSticker(x + 1.0 - hoverOffset, (y + 1.0 - hoverOffset + (lowBound ? lowOffset : 0.0)) / animation, currentStickerSize, id, user);
                bus.H();
                ++stickerIndex;
            }
        }
    }
    
    private void drawSticker(final double x, final double y, final double size, final Short stickerId, final User user) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final StickerImageHandler stickerHandler = userManager.getCosmeticImageManager().getStickerImageHandler();
        if (stickerHandler != null && stickerId != null && stickerId != -1) {
            final UserTextureContainer container = stickerHandler.getContainer(user, (short)stickerId);
            if (container != null) {
                container.validateTexture((CosmeticImageHandler)stickerHandler);
                final AnimatedResourceLocation resourceLocation = stickerHandler.getResourceLocations().get(container.getFileName());
                if (resourceLocation != null) {
                    final double scale = size / 20.0;
                    final double textureSizeX = 115.90909090909092;
                    final double textureSizeY = 231.81818181818184;
                    final double textureOffsetX = 11.590909090909092;
                    final double textureOffsetY = 23.181818181818183;
                    bus.G();
                    bus.c(1.0f, 1.1f, 1.0f, 1.0f);
                    bus.a(scale, scale, 1.0);
                    bib.z().N().a(resourceLocation.getDefault());
                    LabyMod.getInstance().getDrawUtils().drawTexture(x / scale, y / scale, textureOffsetX, textureOffsetY, textureSizeX, textureSizeY, 20.0, 20.0);
                    bus.H();
                }
            }
        }
    }
    
    public void pointSearchMouse(final int mouseX, final int mouseY) {
        this.searchMouseX = mouseX;
        this.searchMouseY = mouseY;
        if (this.lastHoveredStickerId != -1) {
            final Sticker sticker = LabyMod.getInstance().getStickerRegistry().getSticker(this.lastHoveredStickerId);
            if (sticker != null) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, sticker.getName());
            }
        }
    }
    
    public void lockMouseMovementInCircle() {
        if (!this.open) {
            return;
        }
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        final double radiusX = this.lastSelectorWidth / 3.0;
        final double radiusY = this.lastSelectorHeight / 3.0;
        float centerX = this.lockedYaw;
        float centerY = this.lockedPitch + 5.0f - 16.0f;
        if (centerY + radiusY > 90.0) {
            centerY = (float)(90.0 - radiusY);
        }
        if (centerY - radiusY < -90.0) {
            centerY = (float)(-90.0 + radiusY);
        }
        final float newX = player.v;
        final float newY = player.w;
        final double distanceX = Math.abs(centerX - newX);
        final double distanceY = Math.abs(centerY - newY);
        if (distanceX > radiusX) {
            double fromOriginToObjectX = newX - centerX;
            final double multiplier = radiusX / distanceX;
            fromOriginToObjectX *= multiplier;
            centerX += (float)fromOriginToObjectX;
            player.v = centerX;
            player.x = centerX;
        }
        if (distanceY > radiusY) {
            double fromOriginToObjectY = newY - centerY;
            final double multiplier = radiusY / distanceY;
            fromOriginToObjectY *= multiplier;
            centerY += (float)fromOriginToObjectY;
            player.w = centerY;
            player.y = centerY;
        }
        this.updateScrollLock(true);
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    static {
        POP_SOUND = new nf(Source.ABOUT_MC_VERSION.startsWith("1.8") ? "random.pop" : "entity.chicken.egg");
    }
    
    public static class SearchGui extends blk
    {
        private StickerSelectorGui stickerSelectorGui;
        private ModTextField textField;
        
        public SearchGui(final StickerSelectorGui stickerSelectorGui, final ModTextField textField) {
            this.stickerSelectorGui = stickerSelectorGui;
            this.textField = textField;
        }
        
        public void b() {
            super.b();
            this.textField.setFocused(true);
        }
        
        public void m() {
            super.m();
            this.stickerSelectorGui.close();
        }
        
        public void a(final int mouseX, final int mouseY, final float partialTicks) {
            super.a(mouseX, mouseY, partialTicks);
            this.stickerSelectorGui.pointSearchMouse(mouseX, mouseY);
            this.textField.drawTextBox();
        }
        
        public void e() {
            super.e();
            this.textField.updateCursorCounter();
        }
        
        protected void a(final char typedChar, final int keyCode) throws IOException {
            super.a(typedChar, keyCode);
            if (this.textField.textboxKeyTyped(typedChar, keyCode)) {
                this.stickerSelectorGui.filter(this.textField.getText());
            }
        }
        
        protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
            super.a(mouseX, mouseY, mouseButton);
            if (this.textField.mouseClicked(mouseX, mouseY, mouseButton)) {
                return;
            }
            this.stickerSelectorGui.pointSearchMouse(mouseX, mouseY);
            bib.z().a((blk)null);
            this.stickerSelectorGui.close();
        }
        
        protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
            super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            this.stickerSelectorGui.pointSearchMouse(mouseX, mouseY);
        }
    }
}
