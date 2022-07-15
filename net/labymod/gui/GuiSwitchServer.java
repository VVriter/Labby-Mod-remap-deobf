//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.utils.manager.*;
import net.labymod.gui.elements.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import java.util.concurrent.*;
import java.util.*;

public class GuiSwitchServer extends blk
{
    private String title;
    private String address;
    private Result result;
    private boolean connectingScreen;
    private ServerInfoRenderer serverInfoRenderer;
    private long lastUpdate;
    private long updateCooldown;
    private String currentServer;
    private CheckBox checkBox;
    
    public GuiSwitchServer(final String address) {
        this.lastUpdate = 0L;
        this.updateCooldown = 2000L;
        this.address = address;
        this.connectingScreen = true;
    }
    
    public GuiSwitchServer(final String title, final String address, final boolean preview, final Result result) {
        this.lastUpdate = 0L;
        this.updateCooldown = 2000L;
        this.title = ModColor.createColors(title);
        this.address = address;
        this.result = result;
        this.connectingScreen = false;
        this.currentServer = ((LabyMod.getInstance().getCurrentServerData() == null) ? null : ModUtils.getProfileNameByIp(LabyMod.getInstance().getCurrentServerData().getIp()));
        if (preview) {
            this.serverInfoRenderer = new ServerInfoRenderer(address, null);
        }
    }
    
    public void b() {
        super.b();
        if (!this.connectingScreen) {
            this.n.add(new bja(0, this.l / 2 + 20, this.m / 2 + 20, 100, 20, LanguageManager.translate("server_switch_no")));
            this.n.add(new bja(1, this.l / 2 - 120, this.m / 2 + 20, 130, 20, LanguageManager.translate("server_switch_yes")));
            this.checkBox = new CheckBox("", CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, this.l / 2 - 120, this.m / 2 + 50, 20, 20);
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        if (this.connectingScreen) {
            draw.drawCenteredString(cey.a("connect.connecting", new Object[0]), this.l / 2, this.m / 2 - 5);
            draw.drawCenteredString(this.address, this.l / 2, this.m / 2 + 5);
        }
        else {
            draw.drawCenteredString(this.title, this.l / 2, this.m / 2 - 55 + ((this.serverInfoRenderer == null) ? 0 : -15));
            final List<String> list = draw.listFormattedStringToWidth(LanguageManager.translate("server_switch_confirm", this.address), 250);
            int i = 0;
            for (final String line : list) {
                draw.drawCenteredString(line, this.l / 2, this.m / 2 - 40 + i * 10 + ((this.serverInfoRenderer == null) ? 10 : -10));
                ++i;
            }
            if (this.serverInfoRenderer != null) {
                this.serverInfoRenderer.drawEntry(this.l / 2 - 125, this.m / 2 - 20, 250, mouseX, mouseY);
            }
            if (this.currentServer != null && !LabyMod.isForge()) {
                this.checkBox.drawCheckbox(mouseX, mouseY);
                draw.drawString(ModColor.cl('a') + LanguageManager.translate("server_switch_trust", this.currentServer), this.l / 2 - 95, this.m / 2 + 56);
            }
        }
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (!this.connectingScreen) {
            super.a(typedChar, keyCode);
        }
    }
    
    public void e() {
        super.e();
        if (!this.connectingScreen && this.serverInfoRenderer != null && this.lastUpdate + this.updateCooldown < System.currentTimeMillis()) {
            this.lastUpdate = System.currentTimeMillis();
            LabyModCore.getServerPinger().pingServer((ExecutorService)null, this.lastUpdate, this.address, (Consumer)new Consumer<ServerPingerData>() {
                @Override
                public void accept(final ServerPingerData accepted) {
                    if (accepted != null && accepted.getTimePinged() != GuiSwitchServer.this.lastUpdate) {
                        return;
                    }
                    if (GuiSwitchServer.this.serverInfoRenderer != null) {
                        GuiSwitchServer.this.serverInfoRenderer.init(GuiSwitchServer.this.address, GuiSwitchServer.this.address, accepted);
                    }
                }
            });
        }
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 0) {
            this.result.notifyServer(this.address, false, false);
            bib.z().a((blk)null);
        }
        if (button.k == 1) {
            final boolean trusted = this.currentServer != null && this.checkBox.getValue() == CheckBox.EnumCheckBoxValue.ENABLED;
            if (trusted && !LabyMod.isForge()) {
                final List<String> list = new ArrayList<String>(Arrays.asList(LabyMod.getSettings().trustedServers));
                final String profileAddress = ModUtils.getProfileNameByIp(this.currentServer);
                if (trusted && !list.contains(profileAddress)) {
                    list.add(profileAddress);
                    final String[] array = new String[list.size()];
                    list.toArray(array);
                    LabyMod.getSettings().trustedServers = array;
                    LabyMod.getMainConfig().save();
                }
            }
            this.result.notifyServer(this.address, true, trusted);
            LabyMod.getInstance().switchServer(this.address, true);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        if (!this.connectingScreen) {
            this.checkBox.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public interface Result
    {
        void notifyServer(final String p0, final boolean p1, final boolean p2);
    }
}
