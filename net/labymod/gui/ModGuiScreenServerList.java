//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.minecraftforge.fml.relauncher.*;
import net.labymod.utils.manager.*;
import net.labymod.main.*;
import net.labymod.core.*;
import java.util.concurrent.*;
import org.lwjgl.input.*;
import java.io.*;
import net.labymod.utils.*;

@SideOnly(Side.CLIENT)
public class ModGuiScreenServerList extends blk
{
    private final blk guiScreen;
    private final bse serverData;
    private bje textField;
    private long lastUpdate;
    private long updateCooldown;
    private ServerInfoRenderer serverInfoRenderer;
    
    public ModGuiScreenServerList(final blk guiScreen, final bse serverData) {
        this.lastUpdate = 0L;
        this.updateCooldown = 2000L;
        this.guiScreen = guiScreen;
        this.serverData = serverData;
    }
    
    public void e() {
        this.textField.a();
        if (LabyMod.getSettings().directConnectInfo && !this.textField.b().replace(" ", "").isEmpty()) {
            if (this.lastUpdate + this.updateCooldown < System.currentTimeMillis()) {
                this.lastUpdate = System.currentTimeMillis();
                LabyModCore.getServerPinger().pingServer((ExecutorService)null, this.lastUpdate, this.textField.b(), (Consumer)new Consumer<ServerPingerData>() {
                    @Override
                    public void accept(final ServerPingerData accepted) {
                        if (accepted != null && accepted.getTimePinged() != ModGuiScreenServerList.this.lastUpdate) {
                            return;
                        }
                        ModGuiScreenServerList.this.serverInfoRenderer = new ServerInfoRenderer(ModGuiScreenServerList.this.textField.b(), accepted);
                    }
                });
            }
        }
        else {
            this.serverInfoRenderer = new ServerInfoRenderer(this.textField.b(), null);
            this.lastUpdate = -1L;
        }
    }
    
    public void b() {
        Keyboard.enableRepeatEvents(true);
        this.n.clear();
        this.n.add(new bja(0, this.l / 2 - 100, this.m / 4 + 96 + 12, cey.a("selectServer.select", new Object[0])));
        this.n.add(new bja(1, this.l / 2 - 100, this.m / 4 + 120 + 12, cey.a("gui.cancel", new Object[0])));
        (this.textField = new bje(2, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 100, 116, 200, 20)).f(128);
        this.textField.b(true);
        this.textField.a(this.j.t.aA);
        this.n.get(0).l = (this.textField.b().length() > 0 && this.textField.b().split(":").length > 0);
    }
    
    public void m() {
        Keyboard.enableRepeatEvents(false);
        this.j.t.aA = this.textField.b();
        this.j.t.b();
        LabyModCore.getServerPinger().closePendingConnections();
    }
    
    protected void a(final bja button) throws IOException {
        if (button.l) {
            if (button.k == 1) {
                this.guiScreen.a(false, 0);
            }
            else if (button.k == 0) {
                this.serverData.b = this.textField.b();
                this.guiScreen.a(true, 5);
            }
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (this.textField.a(typedChar, keyCode)) {
            this.n.get(0).l = (this.textField.b().length() > 0 && this.textField.b().split(":").length > 0);
        }
        else if (keyCode == 28 || keyCode == 156) {
            this.a(this.n.get(0));
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.textField.a(mouseX, mouseY, mouseButton);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        this.a(LabyModCore.getMinecraft().getFontRenderer(), cey.a("selectServer.direct", new Object[0]), this.l / 2, 20, 16777215);
        this.c(LabyModCore.getMinecraft().getFontRenderer(), cey.a("addServer.enterIp", new Object[0]), this.l / 2 - 100, 100, 10526880);
        this.textField.g();
        super.a(mouseX, mouseY, partialTicks);
        if (this.serverInfoRenderer == null || this.lastUpdate == -1L) {
            return;
        }
        final DrawUtils drawUtils = LabyMod.getInstance().getDrawUtils();
        final int leftBound = this.l / 2 - 150;
        final int rightBound = this.l / 2 + 150;
        final int posY = 44;
        final int height = 30;
        drawUtils.drawRectangle(leftBound, posY - 4, rightBound, posY + 6 + height, Integer.MIN_VALUE);
        this.serverInfoRenderer.drawEntry(leftBound + 3, posY, rightBound - leftBound, mouseX, mouseY);
        final int stateColorR = this.serverInfoRenderer.canReachServer() ? 105 : 240;
        final int stateColorG = this.serverInfoRenderer.canReachServer() ? 240 : 105;
        final int stateColorB = 105;
        final double total = rightBound - leftBound;
        double barPercent = total / this.updateCooldown * (System.currentTimeMillis() - this.lastUpdate);
        if (barPercent > total) {
            barPercent = total;
        }
        final int colorPercent = (int)Math.round(155.0 / this.updateCooldown * (System.currentTimeMillis() - this.lastUpdate - 100L));
        drawUtils.drawRectangle(leftBound, posY - 6, rightBound, posY - 5, Integer.MIN_VALUE);
        drawUtils.drawRectangle(leftBound, posY - 6, rightBound, posY - 5, ModColor.toRGB(stateColorR, stateColorG, stateColorB, 155 - colorPercent));
        drawUtils.drawRect(leftBound, posY - 6, leftBound + barPercent, posY - 5, ModColor.toRGB(stateColorR, stateColorG, stateColorB, 150));
        drawUtils.drawGradientShadowTop(posY - 4, leftBound, rightBound);
        drawUtils.drawGradientShadowBottom(posY + 6 + height, leftBound, rightBound);
    }
}
