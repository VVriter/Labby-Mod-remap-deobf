//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.gui;

import net.labymod.labyplay.party.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.gui.layout.*;
import net.labymod.gui.elements.*;
import net.labymod.labyplay.gui.elements.*;
import net.labymod.utils.*;
import java.io.*;

public class GuiPlayLayout extends WindowLayout
{
    private blk parentScreen;
    private PartySystem partySystem;
    
    public GuiPlayLayout(final blk parentScreen) {
        this.parentScreen = parentScreen;
        this.partySystem = LabyMod.getInstance().getLabyPlay().getPartySystem();
    }
    
    public void b() {
        super.b();
        Tabs.initGui((blk)this);
    }
    
    protected void initLayout(final List<WindowElement<?>> windowElements) {
        this.n.clear();
        MultiplayerTabs.initMultiplayerTabs(2);
        final int marginWindowX = this.l / 4;
        final int marginWindowY = 10;
        final int windowLeft = marginWindowX;
        final int windowRight = this.l - marginWindowX;
        final int windowBottom = this.m - marginWindowY;
        final boolean hasParty = this.partySystem.hasParty();
        final boolean hasInvites = !hasParty && this.partySystem.getPartyInvites().size() != 0;
        final int partyPlaySplit = this.m - 80 + marginWindowY;
        final int partyInvitesSplit = hasInvites ? (this.l / 2) : windowRight;
        if (hasParty) {
            windowElements.add((WindowElement<?>)new WinCurrentParty(this).construct((double)windowLeft, (double)partyPlaySplit, (double)partyInvitesSplit, (double)windowBottom));
        }
        else {
            windowElements.add((WindowElement<?>)new WinPartyCreator(this).construct((double)windowLeft, (double)partyPlaySplit, (double)partyInvitesSplit, (double)windowBottom));
        }
        if (hasInvites) {
            windowElements.add((WindowElement<?>)new WinPartyInvites(this).construct((double)partyInvitesSplit, (double)partyPlaySplit, (double)windowRight, (double)windowBottom));
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawAutoDimmedBackground(0, 41, this.l, this.m - 80);
        draw.drawOverlayBackground(0, 41);
        draw.drawOverlayBackground(0, this.m - 80, this.l, this.m);
        draw.drawGradientShadowTop(41.0, 0.0, this.l);
        draw.drawGradientShadowBottom(this.m - 80, 0.0, this.l);
        super.a(mouseX, mouseY, partialTicks);
        MultiplayerTabs.drawMultiplayerTabs(2, mouseX, mouseY, true, false);
        MultiplayerTabs.drawParty(mouseX, mouseY, this.l);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        MultiplayerTabs.mouseClickedMultiplayerTabs(2, mouseX, mouseY);
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
    }
    
    public blk getParentScreen() {
        return this.parentScreen;
    }
    
    public PartySystem getPartySystem() {
        return this.partySystem;
    }
}
