//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import net.labymod.core.*;
import net.labymod.main.*;
import net.minecraftforge.fml.relauncher.*;
import java.io.*;
import net.labymod.utils.*;
import java.util.*;
import java.lang.reflect.*;

public class ModGuiMultiplayer extends bnf
{
    private long lastRefreshed;
    private blk parentScreen;
    private bnj serverSelector;
    private boolean inited;
    private Object serverListSelectorField;
    private static final String[] serverListSelectorMappings;
    private static final String[] savedServerListMappings;
    
    public ModGuiMultiplayer(final blk parentScreen) {
        super(parentScreen);
        this.parentScreen = parentScreen;
        this.lastRefreshed = System.currentTimeMillis();
    }
    
    public void b() {
        super.b();
        MultiplayerTabs.initMultiplayerTabs(0);
        Tabs.initGui((blk)this);
        this.lastRefreshed = System.currentTimeMillis();
        this.inited = true;
        this.serverSelector = this.getServerSelectionList();
        if (LabyModCore.getMinecraft().getPlayer() != null) {
            for (final bja button : this.n) {
                if (button.k == 0) {
                    button.l = false;
                }
            }
        }
        if (LabyMod.getSettings().publicServerList) {
            final int addSpace = 9;
            this.serverSelector.a(this.l, this.m, 32 + addSpace, this.m - 64);
        }
        try {
            this.serverListSelectorField = ReflectionHelper.findField(bnf.class, ModGuiMultiplayer.serverListSelectorMappings).get(this);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            this.serverListSelectorField = null;
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.serverListSelectorField == null) {
            return;
        }
        super.a(mouseX, mouseY, partialTicks);
        final boolean isScrolled = !LabyMod.getInstance().isInGame() && this.serverSelector != null && this.serverSelector.n() == 0;
        final boolean isIndex0Selected = !LabyMod.getInstance().isInGame() && this.serverSelector != null && LabyModCore.getMinecraft().isSelected(this.serverSelector, 0);
        MultiplayerTabs.drawMultiplayerTabs(0, mouseX, mouseY, isScrolled, isIndex0Selected);
        MultiplayerTabs.drawParty(mouseX, mouseY, this.l);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
    }
    
    public void e() {
        if (this.inited) {
            try {
                super.e();
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
        if (LabyMod.getSettings().serverlistLiveView && System.currentTimeMillis() - this.lastRefreshed >= LabyMod.getSettings().serverlistLiveViewInterval * 1000) {
            this.refreshServerListSilently();
            this.lastRefreshed = System.currentTimeMillis();
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        MultiplayerTabs.mouseClickedMultiplayerTabs(0, mouseX, mouseY);
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
        super.a(mouseX, mouseY, mouseButton);
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 4) {
            LabyMod.getInstance().getLabyConnect().setViaServerList(false);
        }
        if (button.k != 8) {
            super.a(button);
        }
        else {
            this.refreshServerList();
        }
    }
    
    public void k() throws IOException {
        if (this.inited) {
            super.k();
        }
    }
    
    public void f() {
        if (LabyMod.getInstance().isInGame() && !bib.z().E() && LabyMod.getSettings().confirmDisconnect) {
            String ip = "";
            final bjm.a guilistextended$iguilistentry = (LabyModCore.getMinecraft().getSelectedServerInSelectionList(this.getServerSelector()) < 0) ? null : this.getServerSelector().b(LabyModCore.getMinecraft().getSelectedServerInSelectionList(this.getServerSelector()));
            if (guilistextended$iguilistentry instanceof bni) {
                ip = ((bni)guilistextended$iguilistentry).a().b;
            }
            final blk lastScreen = bib.z().m;
            bib.z().a((blk)new bkq((bkp)new bkp() {
                public void a(final boolean result, final int id) {
                    if (result) {
                        ModGuiMultiplayer.this.joinToServer();
                    }
                    else {
                        bib.z().a(lastScreen);
                    }
                }
            }, "Are you sure you want to leave the current server and join this one?", ModColor.cl("c") + ip, 0));
        }
        else {
            this.joinToServer();
        }
    }
    
    private void joinToServer() {
        LabyMod.getInstance().getLabyConnect().setViaServerList(false);
        if (LabyModCore.getMinecraft().getWorld() != null) {
            LabyModCore.getMinecraft().getWorld().O();
            this.j.a((bsb)null);
        }
        if (LabyMod.getInstance().isInGame()) {
            LabyMod.getInstance().onQuit();
        }
        super.f();
    }
    
    public void a(final boolean result, final int id) {
        if (id == 5 && result && LabyModCore.getMinecraft().getWorld() != null) {
            LabyModCore.getMinecraft().getWorld().O();
            this.j.a((bsb)null);
        }
        super.a(result, id);
    }
    
    public blk getParentScreen() {
        return this.parentScreen;
    }
    
    private void refreshServerList() {
        try {
            bsf serverList = null;
            ReflectionHelper.findField(bnf.class, ModGuiMultiplayer.savedServerListMappings).set(this, serverList = new bsf(this.j));
            LabyModCore.getMinecraft().updateOnlineServers(this.serverSelector, serverList);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void refreshServerListSilently() {
        try {
            Field serverDataPingedField = null;
            for (final bni serverListEntryNormal : (List)ReflectionHelper.findField(bnj.class, LabyModCore.getMappingAdapter().getServerListEntryListMappings()).get(this.serverSelector)) {
                if (serverDataPingedField == null) {
                    serverDataPingedField = ReflectionHelper.findField(serverListEntryNormal.a().getClass(), LabyModCore.getMappingAdapter().getServerDataPingedMappings());
                }
                serverDataPingedField.set(serverListEntryNormal.a(), false);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private bnj getServerSelectionList() {
        try {
            return (bnj)ReflectionHelper.findField(bnf.class, ModGuiMultiplayer.serverListSelectorMappings).get(this);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public bnj getServerSelector() {
        return this.serverSelector;
    }
    
    static {
        serverListSelectorMappings = LabyModCore.getMappingAdapter().getServerListSelectorMappings();
        savedServerListMappings = LabyModCore.getMappingAdapter().getSavedServerListMappings();
    }
}
