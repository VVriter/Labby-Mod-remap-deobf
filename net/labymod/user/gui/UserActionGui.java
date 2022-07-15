//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.gui;

import net.labymod.api.events.*;
import java.lang.reflect.*;
import net.labymod.user.util.*;
import net.labymod.user.*;
import net.labymod.main.lang.*;
import net.labymod.labyconnect.user.*;
import net.labymod.labyconnect.*;
import java.util.*;
import net.labymod.labyconnect.gui.*;
import net.labymod.ingamechat.*;
import net.labymod.ingamechat.tabs.*;
import net.labymod.main.listeners.*;
import net.labymod.api.*;
import net.labymod.support.util.*;
import com.google.gson.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import net.labymod.ingamechat.tools.playermenu.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.utils.*;
import net.labymod.api.permissions.*;
import org.lwjgl.input.*;

public class UserActionGui extends bir implements ServerMessageEvent
{
    private static Field fieldLeftClickCounter;
    private List<UserActionEntry> defaultEntries;
    private List<UserActionEntry> serverEntries;
    private aed selectedPlayer;
    private User selectedUser;
    private bsc networkPlayerInfo;
    private List<UserActionEntry> actionEntries;
    private float lockedYaw;
    private float lockedPitch;
    private boolean prevCrosshairState;
    private boolean middleMousePressed;
    private UserActionEntry actionToExecute;
    private long selectionStarted;
    
    public UserActionGui(final UserManager userManager) {
        this.defaultEntries = new ArrayList<UserActionEntry>();
        this.serverEntries = new ArrayList<UserActionEntry>();
        this.selectedPlayer = null;
        this.selectedUser = null;
        this.lockedYaw = 0.0f;
        this.lockedPitch = 0.0f;
        this.middleMousePressed = false;
        this.actionToExecute = null;
        this.defaultEntries.add(new UserActionEntry(LanguageManager.translate("labynet_profile"), UserActionEntry.EnumActionType.OPEN_BROWSER, String.format("https://laby.net/@%s", "{name}"), null));
        this.defaultEntries.add(new UserActionEntry(LanguageManager.translate("playermenu_entry_copyname"), UserActionEntry.EnumActionType.CLIPBOARD, "{name}", null));
        this.defaultEntries.add(new UserActionEntry(LanguageManager.translate("playermenu_entry_addfriend"), UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public boolean canAppear(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
                final LabyConnect labyConnect = LabyMod.getInstance().getLabyConnect();
                if (!labyConnect.isOnline()) {
                    return false;
                }
                final ArrayList<ChatUser> list = new ArrayList<ChatUser>(LabyMod.getInstance().getLabyConnect().getFriends());
                for (final ChatUser p : list) {
                    if (p.getGameProfile().getId().equals(user.getUuid())) {
                        return false;
                    }
                }
                return true;
            }
            
            @Override
            public void execute(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
                bib.z().a((blk)new GuiFriendsAddFriend((blk)null, entityPlayer.h_()));
            }
        }));
        this.defaultEntries.add(new UserActionEntry(LanguageManager.translate("ingame_chat_tab_namehistory"), UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public boolean canAppear(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
                return true;
            }
            
            @Override
            public void execute(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
                GuiChatCustom.activeTab = 5;
                bib.z().a((blk)new GuiChatNameHistory("", entityPlayer.h_()));
            }
        }));
        this.defaultEntries.add(new UserActionEntry(LanguageManager.translate("playermenu_entry_capereport"), UserActionEntry.EnumActionType.NONE, null, new UserActionEntry.ActionExecutor() {
            @Override
            public boolean canAppear(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
                return true;
            }
            
            @Override
            public void execute(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
                if (entityPlayer != null && entityPlayer.h_() != null) {
                    CapeReportCommand.report(entityPlayer.h_());
                }
            }
        }));
        final EventManager eventManager = LabyMod.getInstance().getEventManager();
        eventManager.register((ServerMessageEvent)this);
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!messageKey.equals("user_menu_actions")) {
            return;
        }
        final List<UserActionEntry> serverEntries = new ArrayList<UserActionEntry>();
        final JsonArray array = serverMessage.getAsJsonArray();
        for (int i = 0; i < array.size(); ++i) {
            try {
                final JsonObject obj = array.get(i).getAsJsonObject();
                final String displayName = obj.get("displayName").getAsString();
                final UserActionEntry.EnumActionType type = UserActionEntry.EnumActionType.valueOf(obj.get("type").getAsString());
                final String value = obj.get("value").getAsString();
                final UserActionEntry entry = new UserActionEntry(displayName, type, value, null);
                serverEntries.add(entry);
            }
            catch (Exception error) {
                Debug.log(Debug.EnumDebugMode.API, "Could not parse user menu action entry: " + error.getMessage());
            }
        }
        this.serverEntries = serverEntries;
    }
    
    public void open(final aed entityPlayer) {
        if (this.selectedPlayer != null || entityPlayer == null) {
            return;
        }
        final bsc networkPlayerInfo = LabyModCore.getMinecraft().getConnection().a(entityPlayer.bm());
        if (networkPlayerInfo == null) {
            return;
        }
        if (UserActionGui.fieldLeftClickCounter == null) {
            try {
                (UserActionGui.fieldLeftClickCounter = ReflectionHelper.findField(bib.class, LabyModCore.getMappingAdapter().getLeftClickCounterMappings())).setAccessible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.networkPlayerInfo = networkPlayerInfo;
        this.selectedPlayer = entityPlayer;
        this.selectedUser = LabyMod.getInstance().getUserManager().getUser(entityPlayer.bm());
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        final List<UserActionEntry> entries = new ArrayList<UserActionEntry>();
        entries.addAll(this.defaultEntries);
        for (final PlayerMenu.PlayerMenuEntry entry : LabyMod.getInstance().getChatToolManager().getPlayerMenu()) {
            entries.add(new UserActionEntry(entry.getDisplayName(), entry.isSendInstantly() ? UserActionEntry.EnumActionType.RUN_COMMAND : UserActionEntry.EnumActionType.SUGGEST_COMMAND, entry.getCommand(), null));
        }
        entries.addAll(this.serverEntries);
        LabyMod.getInstance().getEventManager().callCreateUserMenuActions(this.selectedUser, entityPlayer, networkPlayerInfo, (List)entries);
        final Iterator<UserActionEntry> iterator = entries.iterator();
        while (iterator.hasNext()) {
            final UserActionEntry next = iterator.next();
            if (next.getExecutor() != null && !next.getExecutor().canAppear(this.selectedUser, (aed)player, this.networkPlayerInfo)) {
                iterator.remove();
            }
        }
        this.actionEntries = entries;
        this.lockedYaw = player.v;
        this.lockedPitch = player.w;
        this.selectionStarted = System.currentTimeMillis();
        this.prevCrosshairState = LabyMod.getInstance().getLabyModAPI().isCrosshairHidden();
        LabyMod.getInstance().getLabyModAPI().setCrosshairHidden(true);
    }
    
    public void close() {
        if (this.selectedPlayer == null) {
            return;
        }
        this.selectedPlayer = null;
        this.selectedUser = null;
        LabyMod.getInstance().getLabyModAPI().setCrosshairHidden(this.prevCrosshairState);
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null) {
            return;
        }
        player.v = this.lockedYaw;
        player.w = this.lockedPitch;
    }
    
    public void render() {
        if (this.selectedPlayer == null) {
            return;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player == null || player.bm() == null || player.ay != 0) {
            this.close();
            return;
        }
        try {
            if (UserActionGui.fieldLeftClickCounter != null) {
                UserActionGui.fieldLeftClickCounter.setInt(bib.z(), 2);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final double radiusMouseBorder = LabyMod.getInstance().getDrawUtils().getHeight() / 4.0 / 3.0;
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
        final double radius = draw.getHeight() / 4.0;
        final double offsetX = lockedX - player.v;
        final double offsetY = lockedY - player.w;
        final double distance = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        double cursorX = midX - offsetX * 1.5;
        double cursorY = midY - offsetY * 1.5;
        midX += offsetX;
        midY += offsetY;
        final String playerDisplayName = (this.networkPlayerInfo == null || this.networkPlayerInfo.l() == null) ? this.selectedPlayer.h_() : this.networkPlayerInfo.l().d();
        draw.drawCenteredString(playerDisplayName, midX, midY - radius / 2.5, radius / 70.0);
        bus.G();
        bus.e();
        bus.m();
        final long timePassed = System.currentTimeMillis() - this.selectionStarted;
        float animation = (float)((timePassed > 270.0f) ? 1.0 : Math.sin(timePassed * 10.0f / 2000.0f));
        if (!LabyMod.getSettings().playerMenuAnimation) {
            animation = 1.0f;
        }
        final float skullScale = (20.0f + (float)radius / 2.0f) * animation;
        bus.c((float)midX, (float)midY, 0.0f);
        bus.b(skullScale, skullScale, skullScale);
        bus.b((float)(cursorY - midY), -1.0f, 0.0f, 0.0f);
        bus.b((float)(cursorX - midX), 0.0f, 1.0f, 0.0f);
        bus.g();
        draw.renderSkull(this.selectedPlayer.da());
        bus.d();
        bus.l();
        bus.H();
        bus.G();
        bus.e();
        bus.m();
        int index = 0;
        final int amount = this.actionEntries.size();
        this.actionToExecute = null;
        if (amount != 0) {
            for (final UserActionEntry entry : this.actionEntries) {
                this.drawUnit(entry, midX, midY, radius, amount, index, cursorX, cursorY, distance);
                ++index;
            }
        }
        bus.d();
        bus.l();
        bus.H();
        if (offsetX == 0.0 && offsetY == 0.0) {
            cursorX = (int)cursorX;
            cursorY = (int)cursorY;
        }
        draw.drawRect(cursorX, cursorY - 4.0, cursorX + 1.0, cursorY + 5.0, Integer.MAX_VALUE);
        draw.drawRect(cursorX - 4.0, cursorY, cursorX + 5.0, cursorY + 1.0, Integer.MAX_VALUE);
    }
    
    private void drawUnit(final UserActionEntry entry, final double midX, final double midY, final double radius, final int amount, final int index, final double cursorX, final double cursorY, final double distance) {
        final long timePassed = System.currentTimeMillis() - (this.selectionStarted - 1072L);
        float animation = (float)((timePassed < 1572L) ? Math.sin(timePassed / 1000.0f) : 1.0);
        if (!LabyMod.getSettings().playerMenuAnimation) {
            animation = 1.0f;
        }
        final double tau = 6.283185307179586;
        final double destinationShift = 3.0707963267948966;
        final double shift = destinationShift / (animation * animation);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final double xNext = midX + radius * 2.0 * Math.cos((index + 1) * tau / amount + shift);
        final double yNext = midY + radius * 2.0 * Math.sin((index + 1) * tau / amount + shift);
        final double xAfterNext = midX + radius * 2.0 * Math.cos((index + 2) * tau / amount + shift);
        final double yAfterNext = midY + radius * 2.0 * Math.sin((index + 2) * tau / amount + shift);
        final double x = midX + radius * Math.cos((index + 1.5) * tau / amount + shift);
        final double y = midY + radius * Math.sin((index + 1.5) * tau / amount + shift);
        final double finalDestX = midX + radius * Math.cos((index + 1.5) * tau / amount + destinationShift);
        final double finalDestY = midY + radius * Math.sin((index + 1.5) * tau / amount + destinationShift);
        final boolean insideOfTwo = (cursorY > midY && index == 0) || (cursorY < midY && index != 0);
        final boolean inside = (amount > 2) ? this.isInside(cursorX, cursorY, xAfterNext, yAfterNext, midX, midY, xNext, yNext) : (amount == 1 || insideOfTwo);
        final boolean hover = distance > 10.0 && inside && timePassed > 1500L;
        final double buttonHeight;
        final double buttonWidth = buttonHeight = 256.0;
        double textureWidth = 9.0;
        double textureHeight = 9.0;
        double size = radius / 70.0;
        if (size < 3.0) {
            size = 1.0;
        }
        textureWidth *= size;
        textureHeight *= size;
        bib.z().N().a(ModTextures.MISC_MENU_POINT);
        if (hover) {
            GL11.glColor4f(0.2f, 1.0f, 0.2f, 1.0f);
        }
        else {
            GL11.glColor4f(0.23f, 0.7f, 1.0f, 1.0f);
        }
        if (finalDestX - midX > -10.0 && finalDestX - midX < 10.0) {
            if (finalDestY > midY) {
                draw.drawTexture(x - (int)(textureWidth / 2.0), y - 13.0, buttonWidth, buttonHeight, textureWidth, textureHeight);
            }
            else {
                draw.drawTexture(x - (int)(textureWidth / 2.0), y + 13.0, buttonWidth, buttonHeight, textureWidth, textureHeight);
            }
            this.drawOptionTag(entry, x, y, hover, radius, 0);
        }
        else if (finalDestX > midX) {
            if (finalDestY > midY) {
                draw.drawTexture(x - 13.0 * size, y, buttonWidth, buttonHeight, textureWidth, textureHeight);
            }
            else {
                draw.drawTexture(x - 13.0 * size, y, buttonWidth, buttonHeight, textureWidth, textureHeight);
            }
            this.drawOptionTag(entry, x, y, hover, radius, 1);
        }
        else {
            if (finalDestY > midY) {
                draw.drawTexture(x + 4.0, y, buttonWidth, buttonHeight, textureWidth, textureHeight);
            }
            else {
                draw.drawTexture(x + 4.0, y, buttonWidth, buttonHeight, textureWidth, textureHeight);
            }
            this.drawOptionTag(entry, x, y, hover, radius, -1);
        }
        GL11.glLineWidth(2.0f);
        bib.z().N().a(ModTextures.VOID);
        bus.e();
        bus.m();
    }
    
    private void drawOptionTag(final UserActionEntry entry, final double x, final double y, final boolean hover, final double radius, final int alignment) {
        final String displayName = entry.getDisplayName();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        int stringWidth = draw.getStringWidth(displayName);
        final int tagPadding = hover ? 3 : 2;
        int tagHeight = 9;
        final int backgroundColor = ModColor.toRGB(20, 20, 20, 100);
        final int outlineColor = hover ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        double size = radius / 70.0;
        if (size < 3.0) {
            size = 1.0;
        }
        tagHeight *= (int)size;
        stringWidth *= (int)size;
        double tagX = x;
        final double tagY = y + tagHeight;
        if (alignment < 0) {
            tagX = x - stringWidth;
        }
        else if (alignment == 0) {
            tagX = x - stringWidth / 2.0;
        }
        draw.drawRect(tagX - tagPadding, tagY - tagHeight - tagPadding, tagX + stringWidth + tagPadding, tagY + tagPadding, backgroundColor);
        draw.drawRectBorder(tagX - tagPadding - 1.0, tagY - tagHeight - tagPadding - 1.0, tagX + stringWidth + tagPadding + 1.0, tagY + tagPadding + 1.0, outlineColor, 1.0);
        switch (alignment) {
            case -1: {
                draw.drawRightString(displayName, x, y, size);
                break;
            }
            case 0: {
                draw.drawCenteredString(displayName, x, y, size);
                break;
            }
            case 1: {
                draw.drawString(displayName, x, y, size);
                break;
            }
        }
        if (hover) {
            this.actionToExecute = entry;
        }
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
    
    public void tick() {
        if (!LabyMod.getSettings().playerMenu) {
            return;
        }
        final boolean inGame = LabyMod.getInstance().isInGame();
        if (!inGame) {
            if (this.serverEntries.size() != 0) {
                this.serverEntries.clear();
            }
            return;
        }
        final boolean alternativeKeyPressed = this.isPressed(LabyMod.getSettings().keyPlayerMenu);
        final boolean middleMouseButtonpressed = LabyMod.getSettings().keyPlayerMenu == -1 && Mouse.isButtonDown(2);
        final boolean pressed = (middleMouseButtonpressed || alternativeKeyPressed) && bib.z().t.aw == 0;
        if (pressed && bib.z().m == null) {
            if (!this.middleMousePressed) {
                this.middleMousePressed = true;
                final vg entity = LabyModCore.getMinecraft().getEntityMouseOver();
                if (entity != null && entity instanceof aed && LabyMod.getInstance().getServerManager().isAllowed(Permissions.Permission.CHAT)) {
                    this.open((aed)entity);
                }
            }
        }
        else if (this.middleMousePressed) {
            this.middleMousePressed = false;
            if (this.actionToExecute != null && this.selectedUser != null && this.selectedPlayer != null && this.networkPlayerInfo != null) {
                this.actionToExecute.execute(this.selectedUser, this.selectedPlayer, this.networkPlayerInfo);
            }
            this.close();
        }
    }
    
    private boolean isPressed(final int key) {
        return key != -1 && ((key >= 0) ? Keyboard.isKeyDown(key) : Mouse.isButtonDown(key + 100));
    }
    
    public void lockMouseMovementInCircle() {
        if (this.selectedPlayer == null) {
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
    }
    
    public boolean isOpen() {
        return this.selectedPlayer != null;
    }
}
