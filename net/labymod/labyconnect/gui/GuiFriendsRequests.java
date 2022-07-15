//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui;

import net.labymod.gui.elements.*;
import net.labymod.labyconnect.user.*;
import org.lwjgl.input.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.labyconnect.*;
import net.labymod.utils.*;
import java.util.*;

public class GuiFriendsRequests extends blk
{
    public static String response;
    private blk lastScreen;
    private Scrollbar scrollbar;
    private ChatRequest hoverAccept;
    private ChatRequest hoverDeny;
    
    public GuiFriendsRequests(final blk lastScreen) {
        this.scrollbar = new Scrollbar(10);
        this.hoverAccept = null;
        this.hoverDeny = null;
        this.lastScreen = lastScreen;
    }
    
    public void b() {
        super.b();
        Keyboard.enableRepeatEvents(true);
        this.n.add(new bja(1, this.l / 2 - 101, this.m - 37, 98, 20, LanguageManager.translate("button_done")));
        this.scrollbar.init();
        this.scrollbar.setPosition(this.l / 2 + 100, 32, this.l / 2 + 104, this.m - 40 - 2);
        this.scrollbar.setSpeed(10);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.j.a(this.lastScreen);
            return;
        }
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        final LabyConnect chatClient = LabyMod.getInstance().getLabyConnect();
        if (this.hoverAccept != null) {
            chatClient.getClientConnection().sendPacket((Packet)new PacketPlayRequestAddFriend(this.hoverAccept.getGameProfile().getName()));
            chatClient.getRequests().remove(this.hoverAccept);
        }
        else if (this.hoverDeny != null) {
            chatClient.getClientConnection().sendPacket((Packet)new PacketPlayDenyFriendRequest(this.hoverDeny));
            chatClient.getRequests().remove(this.hoverDeny);
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 1) {
            this.j.a(this.lastScreen);
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        this.c(0);
        draw.drawDimmedOverlayBackground(this.l / 2 - 100, 30, this.l / 2 + 100, this.m - 40);
        this.hoverAccept = null;
        this.hoverDeny = null;
        double listY = 32.0 + this.scrollbar.getScrollY() + 1.0;
        final int entryHeight = 10;
        final ArrayList<ChatRequest> list = new ArrayList<ChatRequest>(LabyMod.getInstance().getLabyConnect().getRequests());
        for (final ChatRequest requester : list) {
            final boolean hoverEntry = mouseX > this.l / 2 - 100 && mouseX < this.l / 2 + 100 && mouseY > listY && mouseY < listY + entryHeight + 1.0;
            final int hoverColor = hoverEntry ? 200 : 100;
            draw.drawRect(this.l / 2 - 100, listY, this.l / 2 + 100, listY + entryHeight, ModColor.toRGB(hoverColor, hoverColor, hoverColor, 30));
            bus.d(1.0f, 1.0f, 1.0f);
            draw.drawPlayerHead(requester.getGameProfile(), this.l / 2 - 97, (int)listY, entryHeight);
            draw.drawString(requester.getGameProfile().getName(), this.l / 2 - 85, listY + 1.0);
            if (hoverEntry) {
                final int buttonWidth = 10;
                final boolean hoverAccept = mouseX > this.l / 2 + 60 - buttonWidth && mouseX < this.l / 2 + 60 + buttonWidth;
                if (hoverAccept) {
                    draw.drawRect(this.l / 2 + 60 - buttonWidth, listY, this.l / 2 + 60 + buttonWidth, listY + entryHeight, ModColor.toRGB(255, 255, 255, 50));
                    this.hoverAccept = requester;
                }
                final boolean hoverDeny = mouseX > this.l / 2 + 80 - buttonWidth && mouseX < this.l / 2 + 80 + buttonWidth;
                if (hoverDeny) {
                    draw.drawRect(this.l / 2 + 80 - buttonWidth, listY, this.l / 2 + 80 + buttonWidth, listY + entryHeight, ModColor.toRGB(255, 255, 255, 50));
                    this.hoverDeny = requester;
                }
                draw.drawCenteredString(ModColor.GREEN + "\u2714", this.l / 2 + 60, listY + 1.0);
                draw.drawCenteredString(ModColor.RED + "\u2716", this.l / 2 + 80, listY + 1.0);
            }
            listY += entryHeight + 1;
        }
        draw.drawOverlayBackground(0, 30);
        draw.drawOverlayBackground(this.m - 40, this.m);
        draw.drawGradientShadowTop(30.0, this.l / 2 - 100, this.l / 2 + 100);
        draw.drawGradientShadowBottom(this.m - 40, this.l / 2 - 100, this.l / 2 + 100);
        draw.drawString(LanguageManager.translate("button_requests") + ":", this.l / 2 - 100, 20.0);
        final int size = LabyMod.getInstance().getLabyConnect().getRequests().size();
        draw.drawRightString(size + " " + LanguageManager.translate((size == 1) ? "button_request" : "button_requests"), this.l / 2 + 99, this.m - 35);
        this.scrollbar.update(list.size());
        this.scrollbar.setEntryHeight((double)(entryHeight + 1));
        this.scrollbar.draw();
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        super.b(mouseX, mouseY, state);
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
    
    static {
        GuiFriendsRequests.response = null;
    }
}
