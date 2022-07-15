//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import net.labymod.utils.*;
import net.labymod.core.*;
import net.labymod.main.*;
import java.util.*;
import com.google.common.base.*;
import io.netty.handler.codec.base64.*;
import java.io.*;
import org.apache.commons.lang3.*;
import io.netty.buffer.*;
import java.awt.image.*;

public class ServerInfoRenderer
{
    private static final nf UNKNOWN_SERVER;
    private static final nf SERVER_SELECTION_BUTTONS;
    private final bib mc;
    private ServerPingerData serverData;
    private nf serverIcon;
    private String base64;
    private cdg dynamicTexture;
    private boolean canReachServer;
    private boolean hidden;
    private ServerData labymodServerData;
    private int index;
    
    public ServerInfoRenderer(final String rawIp, final ServerPingerData serverData) {
        this(rawIp, rawIp, serverData);
    }
    
    public ServerInfoRenderer(final String serverName, final String rawIp, final ServerPingerData serverData) {
        this.canReachServer = false;
        this.hidden = false;
        this.index = 0;
        this.mc = bib.z();
        this.init(serverName, rawIp, serverData);
    }
    
    public void init(final String serverName, final String rawIp, ServerPingerData serverData) {
        if (serverData == null) {
            serverData = new ServerPingerData((rawIp == null || rawIp.isEmpty()) ? "localhost" : rawIp, 0L);
            serverData.setPingToServer(-1L);
            serverData.setMotd("§4Can't connect to server.");
            serverData.setVersion(Source.ABOUT_MC_PROTOCOL_VERSION);
            this.canReachServer = false;
        }
        else {
            this.serverIcon = new nf("servers/" + serverData.getIpAddress() + "/icon");
            this.dynamicTexture = (cdg)this.mc.N().b(this.serverIcon);
            this.canReachServer = true;
        }
        (this.serverData = serverData).setServerName(serverName);
    }
    
    public void drawEntry(final int x, final int y, final int listWidth, final int mouseX, final int mouseY) {
        final boolean flag = this.isClientOutOfDate();
        final boolean flag2 = this.isServerOutOfDate();
        final boolean flag3 = flag || flag2;
        LabyModCore.getMinecraft().getFontRenderer().a(this.serverData.getServerName(), x + 32 + 3, y + 1, 16777215);
        final List<String> list = (List<String>)LabyMod.getInstance().getDrawUtils().listFormattedStringToWidth(this.serverData.getMotd(), listWidth - 32 - 2);
        for (int i = 0; i < Math.min(list.size(), 2); ++i) {
            LabyModCore.getMinecraft().getFontRenderer().a((String)list.get(i), x + 32 + 3, y + 12 + LabyModCore.getMinecraft().getFontRenderer().a * i, 8421504);
        }
        String populationInfo = "§7" + this.serverData.getCurrentPlayers() + "§8/§7" + this.serverData.getMaxPlayers();
        if (!this.canReachServer) {
            populationInfo = "§7???";
        }
        final String s2 = flag3 ? ("§4" + this.serverData.getGameVersion()) : populationInfo;
        final int j = LabyModCore.getMinecraft().getFontRenderer().a(s2);
        LabyModCore.getMinecraft().getFontRenderer().a(s2, x + listWidth - j - 15 - 2, y + 1, 8421504);
        int k = 0;
        String s3 = null;
        int l;
        String s4;
        if (flag3) {
            l = 5;
            s4 = (flag ? "Client out of date!" : "Server out of date!");
            s3 = this.serverData.getPlayerList();
        }
        else if (this.serverData.getPingToServer() != -2L) {
            if (this.serverData.getPingToServer() < 0L) {
                l = 5;
            }
            else if (this.serverData.getPingToServer() < 150L) {
                l = 0;
            }
            else if (this.serverData.getPingToServer() < 300L) {
                l = 1;
            }
            else if (this.serverData.getPingToServer() < 600L) {
                l = 2;
            }
            else if (this.serverData.getPingToServer() < 1000L) {
                l = 3;
            }
            else {
                l = 4;
            }
            if (this.serverData.getPingToServer() < 0L) {
                s4 = "(no connection)";
            }
            else {
                s4 = this.serverData.getPingToServer() + "ms";
                s3 = this.serverData.getPlayerList();
            }
        }
        else {
            k = 1;
            l = (int)(bib.I() / 100L + 2L & 0x7L);
            if (l > 4) {
                l = 8 - l;
            }
            s4 = "Pinging...";
        }
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.N().a(LabyModCore.getRenderImplementation().getIcons());
        bir.a(x + listWidth - 15, y, (float)(k * 10), (float)(176 + l * 8), 10, 8, 256.0f, 256.0f);
        if (this.serverData != null && this.serverData.getBase64EncodedIconData() != null && !Objects.equal((Object)this.serverData.getBase64EncodedIconData(), (Object)this.base64)) {
            this.base64 = this.serverData.getBase64EncodedIconData();
            this.prepareServerIcon();
        }
        if (this.dynamicTexture != null) {
            this.drawServerIcon(x, y, this.serverIcon);
        }
        else {
            this.drawServerIcon(x, y, ServerInfoRenderer.UNKNOWN_SERVER);
        }
        final int i2 = mouseX - x;
        final int j2 = mouseY - y;
        if (s3 != null) {
            if (i2 >= listWidth - 15 && i2 <= listWidth - 5 && j2 >= 0 && j2 <= 8 && !s4.isEmpty()) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, s4.split("\n"));
            }
            else if (i2 >= listWidth - j - 15 - 2 && i2 <= listWidth - 15 - 2 && j2 >= 0 && j2 <= 8 && !s3.isEmpty()) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, s3.split("\n"));
            }
        }
    }
    
    public boolean isClientOutOfDate() {
        return this.serverData.getVersion() > Source.ABOUT_MC_PROTOCOL_VERSION;
    }
    
    public boolean isServerOutOfDate() {
        return this.serverData.getVersion() < Source.ABOUT_MC_PROTOCOL_VERSION;
    }
    
    public boolean drawJoinServerButton(final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY) {
        if (mouseX > x && mouseX < x + listWidth && mouseY > y && mouseY < y + slotHeight) {
            this.mc.N().a(ServerInfoRenderer.SERVER_SELECTION_BUTTONS);
            bir.a(x, y, x + 32, y + 32, -1601138544);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            final int k1 = mouseX - x;
            if (k1 < 32 && k1 > 16) {
                bir.a(x, y, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                return true;
            }
            bir.a(x, y, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
        }
        return false;
    }
    
    public boolean drawSaveServerButton(final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY) {
        if (mouseX > x && mouseX < x + listWidth && mouseY > y && mouseY < y + slotHeight) {
            this.mc.N().a(ServerInfoRenderer.SERVER_SELECTION_BUTTONS);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            final int k1 = mouseX - x;
            final int l1 = mouseY - y;
            if (k1 < 16 && l1 > 16) {
                bir.a(x, y, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                return true;
            }
            bir.a(x, y, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
        }
        return false;
    }
    
    protected void drawServerIcon(final int posX, final int posY, final nf resourceLocation) {
        this.mc.N().a(resourceLocation);
        bus.m();
        bir.a(posX, posY, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        bus.l();
    }
    
    private void prepareServerIcon() {
        if (this.serverData.getBase64EncodedIconData() == null) {
            this.mc.N().c(this.serverIcon);
            this.dynamicTexture = null;
        }
        else {
            final ByteBuf bytebuf = Unpooled.copiedBuffer((CharSequence)this.serverData.getBase64EncodedIconData(), Charsets.UTF_8);
            final ByteBuf bytebuf2 = Base64.decode(bytebuf);
            BufferedImage bufferedimage = null;
            Label_0166: {
                try {
                    bufferedimage = cdt.a((InputStream)new ByteBufInputStream(bytebuf2));
                    Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    break Label_0166;
                }
                catch (Throwable throwable) {
                    this.serverData.setBase64EncodedIconData((String)null);
                }
                finally {
                    bytebuf.release();
                    bytebuf2.release();
                }
                return;
            }
            if (this.dynamicTexture == null) {
                this.dynamicTexture = new cdg(bufferedimage.getWidth(), bufferedimage.getHeight());
                this.mc.N().a(this.serverIcon, (cds)this.dynamicTexture);
            }
            bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.dynamicTexture.e(), 0, bufferedimage.getWidth());
            this.dynamicTexture.d();
        }
    }
    
    public ServerInfoRenderer setIndex(final int index) {
        this.index = index;
        return this;
    }
    
    public boolean canReachServer() {
        return this.canReachServer && this.serverData != null && !this.serverData.isPinging();
    }
    
    public ServerPingerData getServerData() {
        return this.serverData;
    }
    
    public nf getServerIcon() {
        return this.serverIcon;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public ServerData getLabymodServerData() {
        return this.labymodServerData;
    }
    
    public void setLabymodServerData(final ServerData labymodServerData) {
        this.labymodServerData = labymodServerData;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    static {
        UNKNOWN_SERVER = new nf("textures/misc/unknown_server.png");
        SERVER_SELECTION_BUTTONS = new nf("textures/gui/server_selection.png");
    }
}
