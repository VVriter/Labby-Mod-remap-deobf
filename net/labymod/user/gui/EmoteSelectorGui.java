//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.gui;

import java.lang.reflect.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import net.labymod.user.emote.keys.provider.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.splash.*;
import net.labymod.splash.dailyemotes.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import org.lwjgl.input.*;
import net.labymod.user.emote.*;
import net.labymod.user.emote.keys.*;
import net.labymod.gui.elements.*;
import java.io.*;

public class EmoteSelectorGui extends bir
{
    private static final int ANIMATION_SPEED = 100;
    private static Field fieldPressTime;
    private static Field fieldLeftClickCounter;
    private static long emoteCooldownEnd;
    private long lastOpened;
    private int selectedItemIndex;
    private boolean open;
    private float lockedYaw;
    private float lockedPitch;
    private boolean prevCrosshairState;
    private short lastHoveredEmoteId;
    private boolean emotesLocked;
    private boolean emotesOnCooldown;
    private int page;
    private int acceptedPage;
    private int animationState;
    private long pageAnimation;
    private int scrollSelectedEmote;
    private List<Short> filteredEmotes;
    private boolean searchOpened;
    private boolean dailyEmotes;
    private int searchMouseX;
    private int searchMouseY;
    
    public EmoteSelectorGui() {
        this.open = false;
        this.lockedYaw = 0.0f;
        this.lockedPitch = 0.0f;
        this.lastHoveredEmoteId = -1;
        this.emotesLocked = false;
        this.emotesOnCooldown = false;
        this.page = 0;
        this.acceptedPage = 0;
        this.animationState = 0;
        this.pageAnimation = 0L;
        this.scrollSelectedEmote = -1;
        this.searchOpened = false;
        this.dailyEmotes = false;
    }
    
    public void open() {
        if (this.open || bib.z().t.av) {
            return;
        }
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        if (EmoteSelectorGui.fieldLeftClickCounter == null) {
            try {
                (EmoteSelectorGui.fieldLeftClickCounter = ReflectionHelper.findField(bib.class, LabyModCore.getMappingAdapter().getLeftClickCounterMappings())).setAccessible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.page = 0;
        this.open = true;
        this.selectedItemIndex = LabyModCore.getMinecraft().getPlayer().bv.d;
        this.scrollSelectedEmote = -1;
        this.lockedYaw = player.v;
        this.lockedPitch = player.w;
        this.prevCrosshairState = LabyMod.getInstance().getLabyModAPI().isCrosshairHidden();
        LabyMod.getInstance().getLabyModAPI().setCrosshairHidden(true);
        if (EmoteSelectorGui.fieldPressTime == null) {
            try {
                (EmoteSelectorGui.fieldPressTime = ReflectionHelper.findField(bhy.class, LabyModCore.getMappingAdapter().getPressTimeMappings())).setAccessible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.dailyEmotes = this.hasDailyEmotes(player);
        if (this.isEmotePlaying(player)) {
            this.emotesLocked = true;
        }
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        if (System.currentTimeMillis() - this.lastOpened < 300L && userManager.getGroupManager().hasPermissionOf(userManager.getUser(player.bm()), (short)10)) {
            bib.z().a((blk)new SearchGui(this));
            this.searchOpened = true;
            this.animationState = 0;
        }
        else {
            this.searchOpened = false;
        }
        this.lastOpened = System.currentTimeMillis();
        this.filter("");
    }
    
    public void close() {
        if (!this.open) {
            return;
        }
        this.open = false;
        LabyMod.getInstance().getLabyModAPI().setCrosshairHidden(this.prevCrosshairState);
        this.updateScrollLock(false);
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        player.v = this.lockedYaw;
        player.w = this.lockedPitch;
        if (this.lastHoveredEmoteId != -1) {
            EmoteSelectorGui.emoteCooldownEnd = System.currentTimeMillis() + 5000L;
            LabyMod.getInstance().getEmoteRegistry().playEmote(this.lastHoveredEmoteId);
        }
    }
    
    public void pointSearchMouse(final int mouseX, final int mouseY) {
        this.searchMouseX = mouseX;
        this.searchMouseY = mouseY;
    }
    
    public void filter(String searchString) {
        final bud player = LabyModCore.getMinecraft().getPlayer();
        final User user = LabyMod.getInstance().getUserManager().getUser(player.bm());
        if (searchString.isEmpty()) {
            this.filteredEmotes = user.getEmotes();
        }
        else {
            searchString = searchString.toLowerCase();
            final Map<Short, KeyFrameStorage> sources = (Map<Short, KeyFrameStorage>)LabyMod.getInstance().getEmoteRegistry().getEmoteSources();
            final List<Short> filteredEmotes = new ArrayList<Short>();
            for (final Short id : user.getEmotes()) {
                final KeyFrameStorage storage = sources.get(id);
                if (storage != null && storage.getName().toLowerCase().contains(searchString)) {
                    filteredEmotes.add(id);
                }
            }
            this.filteredEmotes = filteredEmotes;
        }
    }
    
    private boolean hasDailyEmotes(final bud player) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final User user = userManager.getUser(player.bm());
        if (SplashLoader.getLoader() == null || SplashLoader.getLoader().getEntries() == null) {
            return false;
        }
        final DailyEmote[] dailyEmotes = SplashLoader.getLoader().getEntries().getDailyEmotes();
        return dailyEmotes != null && dailyEmotes.length != 0 && user.isDailyEmoteFlat();
    }
    
    private boolean isEmotePlaying(final bud player) {
        final EmoteRenderer renderer = LabyMod.getInstance().getEmoteRegistry().getEmoteRendererFor((bua)player);
        return renderer != null && renderer.isVisible() && !renderer.isStream();
    }
    
    public void render() {
        if (!this.open) {
            return;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null || player.bm() == null || player.ay != 0) {
            this.close();
            return;
        }
        try {
            if (EmoteSelectorGui.fieldLeftClickCounter != null) {
                EmoteSelectorGui.fieldLeftClickCounter.setInt(bib.z(), 2);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (this.emotesLocked && !this.isEmotePlaying(player)) {
            this.emotesLocked = false;
        }
        final double radiusMouseBorder = draw.getHeight() / 4.0 / 3.0;
        double midX = draw.getWidth() / 2.0;
        double midY = draw.getHeight() / 2.0;
        final double lockedX = this.lockedYaw;
        double lockedY = this.lockedPitch;
        if (lockedY + radiusMouseBorder > 90.0) {
            lockedY = (float)(90.0 - radiusMouseBorder);
        }
        if (lockedY - radiusMouseBorder < -90.0) {
            lockedY = (float)(-90.0 + radiusMouseBorder);
        }
        long timePassed = System.currentTimeMillis() - this.lastOpened;
        if (timePassed > 2000L) {
            timePassed = 2000L;
        }
        final double radius = draw.getHeight() / 4.0 - Math.exp(-timePassed / 100.0) * 10.0;
        double offsetX = lockedX - player.v;
        double offsetY = lockedY - player.w;
        double distance = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        double cursorX = midX - offsetX * 1.5;
        double cursorY = midY - offsetY * 1.5;
        if (this.searchOpened) {
            cursorX = this.searchMouseX;
            cursorY = this.searchMouseY;
            offsetX = this.searchMouseX - draw.getWidth() / 2;
            offsetY = this.searchMouseY - draw.getHeight() / 2;
            distance = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        }
        bus.G();
        if (bib.z().t.aw == 0 && !this.searchOpened) {
            midX += offsetX;
            midY += offsetY;
        }
        final int totalEmotes = this.filteredEmotes.size();
        final int amount = 6;
        int emoteIndex = this.page * amount;
        final int maxPages = (int)Math.ceil(totalEmotes / (double)amount);
        final boolean cooldown = this.emotesOnCooldown && EmoteSelectorGui.emoteCooldownEnd > System.currentTimeMillis();
        final boolean emotesEnabled = LabyMod.getSettings().emotes;
        final String localeKey = emotesEnabled ? (this.emotesLocked ? "emote_status_already_playing" : ((totalEmotes == 0) ? (this.searchOpened ? "emote_status_not_found" : "emote_status_no_emotes") : (cooldown ? "emote_status_cooldown" : "emote_status_select"))) : "emote_status_disabled";
        final String title = ((this.emotesLocked || !emotesEnabled) ? ModColor.cl('c') : "") + LabyMod.getMessage(localeKey, new Object[0]);
        draw.drawCenteredString(title, midX, midY - radius - 5.0);
        if (this.page == -1) {
            draw.drawCenteredString(ModColor.cl('b') + ModColor.cl('o') + "labymod.net/shop", midX, midY + radius + 6.0);
            draw.drawCenteredString(ModColor.cl('6') + LabyMod.getMessage("emote_daily", new Object[0]), midX, midY + radius - 5.0, 0.7);
        }
        else {
            if (totalEmotes == 0) {
                draw.drawCenteredString(ModColor.cl('b') + ModColor.cl('o') + "labymod.net/shop", midX, midY + radius - 5.0);
            }
            else if (maxPages > 1 && !this.searchOpened) {
                String keyName = "?";
                try {
                    keyName = Keyboard.getKeyName(LabyMod.getSettings().keyEmote).toLowerCase();
                }
                catch (Exception ex) {}
                draw.drawCenteredString(LabyMod.getMessage("emote_selector_page", new Object[] { this.page + 1, maxPages }), midX, midY + radius - 5.0, 0.7);
                draw.drawCenteredString(ModColor.cl('7') + LabyMod.getMessage("emote_doubletap", new Object[] { keyName }), midX, midY + radius + 6.0, 0.7);
            }
            if (maxPages == 1 && !this.searchOpened && this.dailyEmotes) {
                draw.drawCenteredString(LabyMod.getMessage("emote_own", new Object[0]), midX, midY + radius - 5.0, 0.7);
            }
        }
        if (!this.searchOpened) {
            final double arrowWidth = 9.0;
            final double arrowHeight = 6.0;
            final double arrowAnimation = Math.abs(Math.sin((System.currentTimeMillis() - this.pageAnimation) / 28.274333882308138) / 2.0);
            if (this.acceptedPage > (this.dailyEmotes ? -1 : 0)) {
                if (this.acceptedPage <= 0) {
                    GL11.glColor4f(1.0f, 0.7f, 0.0f, 1.0f);
                }
                else {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
                final double scale = (this.animationState == -1) ? (arrowAnimation + 1.0) : 1.0;
                bib.z().N().a(ModTextures.MISC_ARROW);
                draw.drawTexture(midX - arrowWidth * scale / 2.0 - radius / 2.0, midY + radius - 2.0 - arrowHeight * scale / 2.0, 0.0, 0.0, 127.5, 255.0, arrowWidth * scale, arrowHeight * scale, 1.1f);
                bib.z().N().a(ModTextures.MISC_MOUSE);
                draw.drawTexture(midX - arrowWidth * scale / 2.0 - radius / 2.0 + 10.0 + 2.0, midY + radius - 4.0 - arrowHeight * scale / 2.0, 127.0, 0.0, 127.0, 255.0, 7.0 * scale, 10.0 * scale, 1.1f);
            }
            if (this.acceptedPage < maxPages - 1) {
                final double scale = (this.animationState == 1) ? (arrowAnimation + 1.0) : 1.0;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                bib.z().N().a(ModTextures.MISC_ARROW);
                draw.drawTexture(midX - arrowWidth * scale / 2.0 + radius / 2.0, midY + radius - 2.0 - arrowHeight * scale / 2.0, 127.5, 0.0, 127.5, 255.0, arrowWidth * scale, arrowHeight * scale, 1.1f);
                bib.z().N().a(ModTextures.MISC_MOUSE);
                draw.drawTexture(midX - arrowWidth * scale / 2.0 + radius / 2.0 - 10.0, midY + radius - 4.0 - arrowHeight * scale / 2.0, 0.0, 0.0, 127.0, 255.0, 7.0 * scale, 10.0 * scale, 1.1f);
            }
        }
        bus.e();
        bus.m();
        this.lastHoveredEmoteId = -1;
        if (this.animationState != 0) {
            final double animation = (System.currentTimeMillis() - this.pageAnimation) * 0.01 * radius;
            final double speed = 1.0 * radius;
            if (this.animationState == 1 || this.animationState == -1) {
                if (this.animationState == -1) {
                    midX += animation;
                }
                else {
                    midX -= animation;
                }
            }
            else if (this.animationState == 2) {
                midX += speed - animation;
            }
            else {
                midX -= speed - animation;
            }
        }
        final Map<Short, KeyFrameStorage> sources = (Map<Short, KeyFrameStorage>)LabyMod.getInstance().getEmoteRegistry().getEmoteSources();
        if (this.page == -1) {
            final DailyEmote[] dailyEmotes = SplashLoader.getLoader().getEntries().getDailyEmotes();
            emoteIndex = 0;
            for (int index = amount; index >= 1; --index) {
                final DailyEmote dailyEmote = (emoteIndex >= dailyEmotes.length) ? null : dailyEmotes[emoteIndex];
                final KeyFrameStorage emote = (dailyEmote == null) ? null : sources.get(dailyEmote.getId());
                this.drawUnit(midX, midY, radius, amount, index, cursorX, cursorY, distance, emote, player);
                ++emoteIndex;
            }
        }
        else {
            for (int index2 = amount - 1; index2 >= 0; --index2) {
                final Short emoteId = (emoteIndex >= totalEmotes) ? null : this.filteredEmotes.get(emoteIndex);
                final KeyFrameStorage emote2 = (emoteId == null) ? null : sources.get(emoteId);
                this.drawUnit(midX, midY, radius, amount, index2, cursorX, cursorY, distance, emote2, player);
                ++emoteIndex;
            }
        }
        bus.d();
        bus.l();
        bus.H();
        if (offsetX == 0.0 && offsetY == 0.0) {
            cursorX = (int)cursorX;
            cursorY = (int)cursorY;
        }
        if (!this.searchOpened) {
            draw.drawRect(cursorX, cursorY - 4.0, cursorX + 1.0, cursorY + 5.0, Integer.MAX_VALUE);
            draw.drawRect(cursorX - 4.0, cursorY, cursorX + 5.0, cursorY + 1.0, Integer.MAX_VALUE);
            this.handleMouseInput(maxPages - 1);
        }
    }
    
    private void handleMouseInput(final int maxPages) {
        final double scroll = Mouse.getDWheel();
        final boolean moveUp = scroll > 0.0;
        final boolean moveDown = scroll < 0.0;
        if (moveUp || moveDown) {
            final int value = this.scrollSelectedEmote + (moveUp ? 1 : -1);
            this.scrollSelectedEmote = ((value < 0) ? 5 : (value % 6));
        }
        try {
            for (int amount = 6, i = 0; i < amount; ++i) {
                final int code = bib.z().t.ap[i].j();
                if (code >= 0 && Keyboard.isKeyDown(code)) {
                    this.scrollSelectedEmote = amount - 1 - i;
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        if (this.acceptedPage == this.page && this.animationState == 0) {
            if (Mouse.isButtonDown(0) && this.page > (this.dailyEmotes ? -1 : 0)) {
                --this.page;
                this.animationState = -1;
                this.pageAnimation = System.currentTimeMillis();
            }
            if (Mouse.isButtonDown(1) && this.page < maxPages) {
                ++this.page;
                this.animationState = 1;
                this.pageAnimation = System.currentTimeMillis();
            }
        }
        else if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && this.animationState != -1 && this.animationState != 1) {
            this.acceptedPage = this.page;
        }
        if ((this.animationState == -1 || this.animationState == 1) && this.pageAnimation + 100L < System.currentTimeMillis()) {
            this.animationState *= 2;
            this.pageAnimation = System.currentTimeMillis();
        }
        if ((this.animationState == -2 || this.animationState == 2) && this.pageAnimation + 100L < System.currentTimeMillis()) {
            this.animationState = 0;
            this.pageAnimation = System.currentTimeMillis();
        }
    }
    
    private void drawUnit(final double midX, final double midY, final double radius, final int amount, final int index, final double cursorX, final double cursorY, final double distance, final KeyFrameStorage emote, final bud player) {
        final double tau = 6.283185307179586;
        final double unitGap = 0.02;
        final double idleGap = 1.0;
        final double shift = tau / 3.0 + 3.141592653589793;
        final double outsideX = midX + radius * Math.cos(index * tau / amount + unitGap + shift);
        final double outsideY = midY + radius * Math.sin(index * tau / amount + unitGap + shift);
        final double outsideXNext = midX + radius * Math.cos((index + 1) * tau / amount - unitGap + shift);
        final double outsideYNext = midY + radius * Math.sin((index + 1) * tau / amount - unitGap + shift);
        final double radiusInside = radius / 5.0;
        final double insideX = midX + radiusInside * Math.cos(index * tau / amount + unitGap + shift);
        final double insideY = midY + radiusInside * Math.sin(index * tau / amount + unitGap + shift);
        final double insideXNext = midX + radiusInside * Math.cos((index + 1) * tau / amount - unitGap + shift);
        final double insideYNext = midY + radiusInside * Math.sin((index + 1) * tau / amount - unitGap + shift);
        final double idleRadius = radius / 5.0 - idleGap;
        final double idleX = midX + idleRadius * Math.cos(index * tau / amount + shift);
        final double idleY = midY + idleRadius * Math.sin(index * tau / amount + shift);
        final double idleXNext = midX + idleRadius * Math.cos((index + 1) * tau / amount + shift);
        final double idleYNext = midY + idleRadius * Math.sin((index + 1) * tau / amount + shift);
        final double staticMidX = LabyMod.getInstance().getDrawUtils().getWidth() / 2;
        final double switchPageBrightness = (this.animationState == 0) ? 1.0 : (1.0 - 1.0 / radius * Math.abs(staticMidX - midX));
        final boolean validEmote = LabyMod.getSettings().emotes && !this.emotesLocked && emote != null;
        final boolean scrollSelected = this.scrollSelectedEmote != -1 && this.scrollSelectedEmote == index - ((this.page == -1) ? 1 : 0);
        boolean hoverOutside = validEmote && ((this.isInside(cursorX, cursorY, outsideX, outsideY, midX, midY, outsideXNext, outsideYNext) && Math.abs(distance) > 6.0) || (scrollSelected && Math.abs(distance) < 6.0));
        final double outsideBrightness = (hoverOutside ? 0.5 : 0.1) * switchPageBrightness;
        final double outsideAlpha = (hoverOutside ? 0.6 : 0.5) * switchPageBrightness;
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(2.0f);
        bib.z().N().a(ModTextures.VOID);
        bus.e();
        bus.m();
        GL11.glBegin(7);
        GL11.glColor4d(outsideBrightness, outsideBrightness, outsideBrightness, outsideAlpha);
        GL11.glVertex3d(insideX, insideY, 0.0);
        GL11.glVertex3d(insideXNext, insideYNext, 0.0);
        GL11.glVertex3d(outsideXNext, outsideYNext, 0.0);
        GL11.glVertex3d(outsideX, outsideY, 0.0);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5 * switchPageBrightness);
        GL11.glVertex3d(insideX, insideY, 0.0);
        GL11.glVertex3d(insideXNext, insideYNext, 0.0);
        GL11.glVertex3d(outsideXNext, outsideYNext, 0.0);
        GL11.glVertex3d(outsideX, outsideY, 0.0);
        GL11.glEnd();
        GL11.glBegin(4);
        GL11.glColor4d(0.3, 0.3, 0.3, 0.5 * switchPageBrightness);
        GL11.glVertex3d(idleXNext, idleYNext, 0.0);
        GL11.glVertex3d(idleX, idleY, 0.0);
        GL11.glVertex3d(midX, midY, 0.0);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glColor4d(0.2, 0.2, 0.2, 0.9 * switchPageBrightness);
        GL11.glVertex3d(idleXNext, idleYNext, 0.0);
        GL11.glVertex3d(idleX, idleY, 0.0);
        GL11.glEnd();
        final boolean cooldown = this.emotesOnCooldown && EmoteSelectorGui.emoteCooldownEnd > System.currentTimeMillis();
        if (emote != null && !this.emotesLocked && !cooldown) {
            final EmoteRegistry registry = LabyMod.getInstance().getEmoteRegistry();
            EmoteRenderer emoteRenderer = registry.getEmoteRendererFor((bua)player);
            if (hoverOutside) {
                final boolean moving = player.m != player.p || player.n != player.q || player.o != player.r;
                if (moving) {
                    for (final EmoteKeyFrame keyframe : emote.getKeyframes()) {
                        if (keyframe != null) {
                            for (final EmotePose emotePose : keyframe.getEmotePoses()) {
                                if (emotePose != null && emotePose.isBlockMovement()) {
                                    hoverOutside = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (hoverOutside) {
                if (emoteRenderer == null || emoteRenderer.isStream()) {
                    emoteRenderer = registry.handleEmote(player.bm(), emote.getId());
                    if (emoteRenderer != null) {
                        emoteRenderer.setVisible(false);
                    }
                }
                this.lastHoveredEmoteId = emote.getId();
            }
            else if (emoteRenderer != null && emoteRenderer.getEmoteId() == emote.getId()) {
                registry.handleEmote(player.bm(), (short)(-1));
                emoteRenderer = null;
            }
            final double emoteRadius = radius / 1.7;
            final double middleOutsideX = midX + emoteRadius * Math.cos((index + 0.5) * tau / amount + shift);
            final double middleOutsideY = midY + emoteRadius * Math.sin((index + 0.5) * tau / amount + shift);
            if (emoteRenderer != null && emoteRenderer.getEmoteId() == emote.getId()) {
                emoteRenderer.setVisible(true);
            }
            double size = radius / 70.0;
            if (switchPageBrightness < 0.5) {
                size = 0.0;
            }
            bus.G();
            bus.a(size, size, size);
            drawEntityOnScreen(player, middleOutsideX / size, middleOutsideY / size + 8.0, 13.0, middleOutsideX - cursorX, middleOutsideY - cursorY, hoverOutside);
            bus.H();
            if (emoteRenderer != null && emoteRenderer.getEmoteId() == emote.getId()) {
                emoteRenderer.setVisible(false);
            }
        }
        if (emote != null && (this.emotesLocked || cooldown)) {
            final double emoteRadius2 = radius / 1.7;
            final double middleOutsideX2 = midX + emoteRadius2 * Math.cos((index + 0.5) * tau / amount + shift);
            final double middleOutsideY2 = midY + emoteRadius2 * Math.sin((index + 0.5) * tau / amount + shift);
            final double size2 = radius / 4.0;
            bus.d(1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bib.z().N().a(ModTextures.MISC_BLOCKED);
            LabyMod.getInstance().getDrawUtils().drawTexture(middleOutsideX2 - size2 / 2.0, middleOutsideY2 - size2 / 2.0, 255.0, 255.0, size2, size2);
        }
        if (emote != null) {
            final double emoteRadius2 = radius / 1.7;
            final double middleOutsideX2 = midX + emoteRadius2 * Math.cos((index + 0.5) * tau / amount + shift);
            final double middleOutsideY2 = midY + emoteRadius2 * Math.sin((index + 0.5) * tau / amount + shift);
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            final double fontSize = 0.5;
            final String prefix = (this.page == -1) ? ModColor.cl('6') : "";
            final List<String> lines = draw.listFormattedStringToWidth(prefix + emote.getName(), (int)(idleRadius * 2.0 / fontSize), 2);
            int lineY = 0;
            for (final String line : lines) {
                draw.drawCenteredString(line, middleOutsideX2, middleOutsideY2 + lineY + emoteRadius2 / 4.0, radius / 70.0 * fontSize * switchPageBrightness);
                lineY += 6;
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
                if (EmoteSelectorGui.fieldPressTime != null) {
                    EmoteSelectorGui.fieldPressTime.setInt(keyBinding, 0);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
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
        final double radius = LabyMod.getInstance().getDrawUtils().getHeight() / 4.0 / 3.0;
        float centerX = this.lockedYaw;
        float centerY = this.lockedPitch;
        if (centerY + radius > 90.0) {
            centerY = (float)(90.0 - radius);
        }
        if (centerY - radius < -90.0) {
            centerY = (float)(-90.0 + radius);
        }
        final float newX = player.v;
        final float newY = player.w;
        final double distanceX = centerX - newX;
        final double distanceY = centerY - newY;
        final double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (distance > radius) {
            double fromOriginToObjectX = newX - centerX;
            double fromOriginToObjectY = newY - centerY;
            final double multiplier = radius / distance;
            fromOriginToObjectX *= multiplier;
            fromOriginToObjectY *= multiplier;
            centerX += (float)fromOriginToObjectX;
            centerY += (float)fromOriginToObjectY;
            player.v = centerX;
            player.x = centerX;
            player.w = centerY;
            player.y = centerY;
        }
        this.updateScrollLock(true);
    }
    
    public static void drawEntityOnScreen(final bud entity, final double x, final double y, final double size, final double mouseX, final double mouseY, final boolean hover) {
        bus.G();
        bus.h();
        bus.b(x, y, 0.0);
        bus.a(-size, size, size);
        bus.b(180.0f, 0.0f, 0.0f, 1.0f);
        final float var6 = entity.aN;
        final float var7 = entity.v;
        final float var8 = entity.w;
        final float var9 = entity.aQ;
        final float var10 = entity.aP;
        bhz.b();
        bus.B();
        bus.g();
        if (hover) {
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        }
        else {
            GL11.glColor4d(0.5, 0.5, 0.5, 1.0);
        }
        entity.aN = (float)Math.atan(mouseX / 40.0) * 20.0f;
        entity.v = (float)Math.atan(mouseX / 40.0) * 40.0f;
        entity.w = -(float)Math.atan(mouseY / 40.0) * 20.0f;
        entity.aP = entity.v;
        entity.aQ = entity.v;
        final double lastTickPosX = entity.M;
        final double lastTickPosY = entity.N;
        final double lastTickPosZ = entity.O;
        final double posX = entity.p;
        final double posY = entity.q;
        final double posZ = entity.r;
        final double prevPosX = entity.m;
        final double prevPosY = entity.n;
        final double prevPosZ = entity.o;
        final double chasingPosX = entity.bH;
        final double chasingPosY = entity.bI;
        final double chasingPosZ = entity.bJ;
        final double prevChasingPosX = entity.bE;
        final double prevChasingPosY = entity.bF;
        final double prevChasingPosZ = entity.bG;
        entity.M = 0.0;
        entity.N = 0.0;
        entity.O = 0.0;
        entity.p = 0.0;
        entity.q = 0.0;
        entity.r = 0.0;
        entity.m = 0.0;
        entity.n = 0.0;
        entity.o = 0.0;
        entity.bH = 0.0;
        entity.bI = 0.0;
        entity.bJ = 0.0;
        entity.bE = 0.0;
        entity.bF = 0.0;
        entity.bG = 0.0;
        bus.c(0.0f, 0.0f, 10.0f);
        final bzf renderManager = bib.z().ac();
        renderManager.a(90.0f);
        LabyModCore.getRenderImplementation().renderEntity(renderManager, (vg)entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        entity.M = lastTickPosX;
        entity.N = lastTickPosY;
        entity.O = lastTickPosZ;
        entity.p = posX;
        entity.q = posY;
        entity.r = posZ;
        entity.m = prevPosX;
        entity.n = prevPosY;
        entity.o = prevPosZ;
        entity.bH = chasingPosX;
        entity.bI = chasingPosY;
        entity.bJ = chasingPosZ;
        entity.bE = prevChasingPosX;
        entity.bF = prevChasingPosY;
        entity.bG = prevChasingPosZ;
        entity.aN = var6;
        entity.v = var7;
        entity.w = var8;
        entity.aQ = var9;
        entity.aP = var10;
        bhz.a();
        bus.E();
        bus.g(cii.r);
        bus.z();
        bus.g(cii.q);
        bus.H();
    }
    
    private double sign(final double px1, final double py1, final double px2, final double py2, final double px3, final double py3) {
        return (px1 - px3) * (py2 - py3) - (px2 - px3) * (py1 - py3);
    }
    
    private boolean isInside(final double pointX, final double pointY, final double px1, final double py1, final double px2, final double py2, final double px3, final double py3) {
        final boolean b1 = this.sign(pointX, pointY, px1, py1, px2, py2) < 0.0;
        final boolean b2 = this.sign(pointX, pointY, px2, py2, px3, py3) < 0.0;
        final boolean b3 = this.sign(pointX, pointY, px3, py3, px1, py1) < 0.0;
        return b1 == b2 && b2 == b3;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    static {
        EmoteSelectorGui.emoteCooldownEnd = 0L;
    }
    
    public static class SearchGui extends blk
    {
        private EmoteSelectorGui emoteSelectorGui;
        private ModTextField textField;
        
        public SearchGui(final EmoteSelectorGui emoteSelectorGui) {
            this.emoteSelectorGui = emoteSelectorGui;
        }
        
        public void b() {
            super.b();
            (this.textField = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 50, this.m / 4 - 30, 100, 20)).setFocused(true);
            this.textField.setBlackBox(false);
        }
        
        public void a(final int mouseX, final int mouseY, final float partialTicks) {
            super.a(mouseX, mouseY, partialTicks);
            this.textField.drawTextBox();
            this.emoteSelectorGui.pointSearchMouse(mouseX, mouseY);
        }
        
        public void e() {
            super.e();
            this.textField.updateCursorCounter();
        }
        
        protected void a(final char typedChar, final int keyCode) throws IOException {
            super.a(typedChar, keyCode);
            if (this.textField.textboxKeyTyped(typedChar, keyCode)) {
                this.emoteSelectorGui.filter(this.textField.getText());
            }
        }
        
        protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
            super.a(mouseX, mouseY, mouseButton);
            if (this.textField.mouseClicked(mouseX, mouseY, mouseButton)) {
                return;
            }
            this.emoteSelectorGui.pointSearchMouse(mouseX, mouseY);
            bib.z().a((blk)null);
            this.emoteSelectorGui.close();
        }
        
        protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
            super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            this.emoteSelectorGui.pointSearchMouse(mouseX, mouseY);
        }
    }
}
