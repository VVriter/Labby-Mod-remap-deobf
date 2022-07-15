//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import com.google.gson.*;
import net.labymod.utils.request.*;
import net.labymod.gui.elements.*;
import net.labymod.main.lang.*;
import net.labymod.core.*;
import java.util.concurrent.*;
import net.labymod.utils.*;
import net.labymod.utils.manager.*;
import net.labymod.main.*;
import java.io.*;
import java.util.*;

public class GuiServerList extends blk
{
    private static Map<String, ServerInfoRenderer> serverInfoRenderers;
    private static List<ServerInfoRenderer> serverInfoRenderersSorted;
    private static List<ServerData> publicServerListEntrys;
    private static boolean initialized;
    private static boolean partnersOnly;
    private blk parentScreen;
    private Scrollbar scrollbar;
    private long lastServerDataUpdate;
    private ServerPingerData joinOnServerData;
    private ServerPingerData saveServerData;
    private ServerPingerData hoverServerData;
    private ServerPingerData selectedServerData;
    private int selectedServerId;
    private long lastTimeSelected;
    private bja buttonConnect;
    private bja buttonSaveServer;
    private CheckBox checkBox;
    private int alternativeDragClickY;
    
    public GuiServerList(final blk parentScreen) {
        this.alternativeDragClickY = -1;
        this.parentScreen = parentScreen;
        if (GuiServerList.initialized) {
            return;
        }
        GuiServerList.initialized = true;
        DownloadServerRequest.getJsonObjectAsync("https://dl.labymod.net/public_servers.json", new ServerResponse<JsonElement>() {
            @Override
            public void success(final JsonElement result) {
                final JsonObject servers = result.getAsJsonObject().get("servers").getAsJsonObject();
                int index = 0;
                final Set<Map.Entry<String, JsonElement>> entrySet = (Set<Map.Entry<String, JsonElement>>)servers.entrySet();
                for (final Map.Entry<String, JsonElement> entry : entrySet) {
                    final String address = entry.getKey();
                    final JsonObject data = entry.getValue().getAsJsonObject();
                    final boolean partner = data.has("partner") && data.get("partner").getAsBoolean();
                    final ServerData serverData = new ServerData(address, 25565, partner);
                    serverData.setIndex(index);
                    GuiServerList.publicServerListEntrys.add(serverData);
                    ++index;
                }
                GuiServerList.this.refreshServerList();
                GuiServerList.this.sortServerList();
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
            }
        });
    }
    
    public void b() {
        MultiplayerTabs.initMultiplayerTabs(1);
        Tabs.initGui((blk)this);
        (this.scrollbar = new Scrollbar(36)).setPosition(this.l / 2 + 150 + 4, 41, this.l / 2 + 150 + 4 + 6, this.m - 40);
        this.scrollbar.setSpeed(20);
        this.scrollbar.setSpaceBelow(5);
        this.refreshServerList();
        this.sortServerList();
        this.lastServerDataUpdate = System.currentTimeMillis() + 5000L;
        this.n.add(this.buttonSaveServer = new bja(6, this.l / 2 - 50 - 5 - 100, this.m - 30, 100, 20, LanguageManager.translate("button_save_server")));
        this.n.add(this.buttonConnect = new bja(5, this.l / 2 - 50, this.m - 30, 100, 20, LanguageManager.translate("button_connect")));
        final bja cancelButton = new bja(4, this.l / 2 + 50 + 5, this.m - 30, 100, 20, LanguageManager.translate("button_cancel"));
        if (LabyMod.getInstance().isInGame()) {
            cancelButton.l = false;
        }
        this.n.add(cancelButton);
        this.checkBox = new CheckBox("", GuiServerList.partnersOnly ? CheckBox.EnumCheckBoxValue.ENABLED : CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, this.l / 2 - 180, this.m - 28, 19, 19);
        super.b();
    }
    
    private void refreshServerList() {
        final boolean firstInit = GuiServerList.serverInfoRenderers.isEmpty();
        for (final ServerData entry : GuiServerList.publicServerListEntrys) {
            if (firstInit) {
                final ServerPingerData dummyPingerData = new ServerPingerData(entry.getIp(), 0L);
                dummyPingerData.setPingToServer(-1L);
                dummyPingerData.setMotd(LanguageManager.translate("status_pinging_server"));
                dummyPingerData.setPinging(true);
                dummyPingerData.setVersion(Source.ABOUT_MC_PROTOCOL_VERSION);
                final ServerInfoRenderer serverInfoRenderer = new ServerInfoRenderer(entry.getIp(), entry.getIp(), dummyPingerData).setIndex(entry.getIndex());
                serverInfoRenderer.setLabymodServerData(entry);
                GuiServerList.serverInfoRenderers.put(entry.getIp(), serverInfoRenderer);
            }
            final long pingStartTime = System.currentTimeMillis();
            LabyModCore.getServerPinger().pingServer((ExecutorService)null, pingStartTime, entry.getIp() + ":" + entry.getPort(), (Consumer)new Consumer<ServerPingerData>() {
                @Override
                public void accept(final ServerPingerData accepted) {
                    if (accepted != null && accepted.getTimePinged() != pingStartTime) {
                        return;
                    }
                    final ServerInfoRenderer preInfo = GuiServerList.serverInfoRenderers.get(entry.getIp());
                    if (preInfo != null) {
                        if (accepted == null) {
                            if (!preInfo.canReachServer()) {
                                preInfo.setHidden(true);
                            }
                        }
                        else {
                            preInfo.init(entry.getIp(), entry.getIp(), accepted);
                            preInfo.setHidden(false);
                        }
                    }
                }
            });
        }
        if (firstInit) {
            this.sortServerList();
        }
    }
    
    private void sortServerList() {
        final List<ServerInfoRenderer> list = new ArrayList<ServerInfoRenderer>();
        int count = 0;
        final String versionSplit = Source.ABOUT_MC_VERSION.replaceFirst("\\.", "/");
        final String majorVersion = (versionSplit.contains(".") ? versionSplit.split("\\.")[0] : versionSplit).replaceAll("/", ".");
        for (final ServerInfoRenderer serverInfoRenderer : GuiServerList.serverInfoRenderers.values()) {
            if ((serverInfoRenderer.isClientOutOfDate() || serverInfoRenderer.isServerOutOfDate()) && !serverInfoRenderer.getServerData().getMotd().contains(majorVersion) && !serverInfoRenderer.getServerData().getGameVersion().contains(majorVersion)) {
                continue;
            }
            if (serverInfoRenderer.getServerData() != null && serverInfoRenderer.canReachServer() && !serverInfoRenderer.getServerData().isPinging() && serverInfoRenderer.getServerData().getCurrentPlayers() < 30) {
                continue;
            }
            if (serverInfoRenderer.isHidden()) {
                continue;
            }
            final boolean partner = serverInfoRenderer.getLabymodServerData() != null && serverInfoRenderer.getLabymodServerData().isPartner();
            if (!partner && GuiServerList.partnersOnly) {
                continue;
            }
            list.add(serverInfoRenderer);
            if (++count >= 50) {
                break;
            }
        }
        Collections.sort(list, new Comparator<ServerInfoRenderer>() {
            @Override
            public int compare(final ServerInfoRenderer a, final ServerInfoRenderer b) {
                return a.getIndex() - b.getIndex();
            }
        });
        GuiServerList.serverInfoRenderersSorted = list;
        this.scrollbar.update(GuiServerList.serverInfoRenderersSorted.size());
    }
    
    public void e() {
        if (LabyMod.getSettings().serverlistLiveView && this.lastServerDataUpdate < System.currentTimeMillis()) {
            this.lastServerDataUpdate = System.currentTimeMillis() + 15000L;
            this.sortServerList();
            this.refreshServerList();
        }
        super.e();
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        draw.drawAutoDimmedBackground(this.scrollbar.getScrollY());
        this.joinOnServerData = null;
        this.saveServerData = null;
        this.hoverServerData = null;
        this.selectedServerId = -1;
        int id = 1;
        final int midX = this.l / 2;
        final int entryWidth = 300;
        final int entryHeight = 36;
        double posY = 45.0 + this.scrollbar.getScrollY();
        for (final ServerInfoRenderer serverInfoRenderer : GuiServerList.serverInfoRenderersSorted) {
            final boolean partner = serverInfoRenderer.getLabymodServerData() != null && serverInfoRenderer.getLabymodServerData().isPartner();
            if (this.selectedServerData != null && this.selectedServerData.getIpAddress().equals(serverInfoRenderer.getServerData().getIpAddress())) {
                draw.drawRect(midX - entryWidth / 2 - 2, posY - 2.0, midX + entryWidth / 2 + 2, posY + entryHeight - 2.0, ModColor.toRGB(partner ? 155 : 128, partner ? 155 : 128, partner ? 0 : 128, 255));
                draw.drawRect(midX - entryWidth / 2 - 1, posY - 1.0, midX + entryWidth / 2 + 1, posY + entryHeight - 3.0, ModColor.toRGB(0, 0, 0, 255));
                this.selectedServerId = id;
            }
            else if (partner) {
                final int color = ModColor.toRGB(100, 80, 0, 30);
                final int x = midX - entryWidth / 2 - 1;
                final int y = (int)posY - 1;
                final int x2 = midX + entryWidth / 2 + 2;
                final int y2 = (int)posY + entryHeight - 3;
                DrawUtils.a(x + 1, y + 1, x2 - 1, y2 - 1, color);
                DrawUtils.a(x, y + 2, x + 1, y2 - 2, color);
                DrawUtils.a(x2 - 1, y + 2, x2, y2 - 2, color);
                DrawUtils.a(x + 2, y, x2 - 2, y + 1, color);
                DrawUtils.a(x + 2, y2 - 1, x2 - 2, y2, color);
            }
            serverInfoRenderer.drawEntry(midX - entryWidth / 2, (int)posY, entryWidth + 5, mouseX, mouseY);
            if (mouseY > 41 && mouseY < this.m - 40) {
                if (serverInfoRenderer.drawJoinServerButton(midX - entryWidth / 2, (int)posY, entryWidth + 5, entryHeight, mouseX, mouseY)) {
                    this.joinOnServerData = serverInfoRenderer.getServerData();
                }
                if (serverInfoRenderer.drawSaveServerButton(midX - entryWidth / 2, (int)posY, entryWidth + 5, entryHeight, mouseX, mouseY)) {
                    this.saveServerData = serverInfoRenderer.getServerData();
                    TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 300L, LanguageManager.translate("button_save_in_my_server_list"));
                }
                if (mouseX > midX - entryWidth / 2 && mouseX < midX + entryWidth / 2 + 5 && mouseY > posY && mouseY < posY + entryHeight) {
                    this.hoverServerData = serverInfoRenderer.getServerData();
                }
            }
            String number = "#" + id;
            if (partner) {
                final double x3 = midX - entryWidth / 2 - draw.getStringWidth(number) - 12;
                final boolean hover = mouseX > x3 && mouseX < x3 + 10.0 && mouseY > posY && mouseY < posY + 10.0;
                draw.bindTexture(ModTextures.MISC_PARTNER_CROWN);
                draw.drawTexture(x3 - (hover ? 2 : 0), posY - (hover ? 2 : 0), 255.0, 255.0, 10 + (hover ? 4 : 0), 10 + (hover ? 4 : 0), 1.1f);
                if (hover) {
                    TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, ModColor.cl('e') + LanguageManager.translate("partner_server_list"));
                }
                number = ModColor.cl('e') + number;
            }
            draw.drawRightString(number, midX - entryWidth / 2 - 3, posY + 2.0, 0.7);
            posY += entryHeight;
            ++id;
        }
        this.buttonConnect.l = (this.selectedServerData != null);
        this.buttonSaveServer.l = (this.selectedServerData != null);
        draw.drawOverlayBackground(0, 41);
        draw.drawGradientShadowTop(41.0, 0.0, this.l);
        draw.drawOverlayBackground(this.m - 40, this.m);
        draw.drawGradientShadowBottom(this.m - 40, 0.0, this.l);
        if (GuiServerList.serverInfoRenderersSorted.isEmpty()) {
            draw.drawCenteredString(LanguageManager.translate("status_loading_server_list"), this.l / 2, this.m / 2);
        }
        final int qiX = this.l - 12;
        final int qiY = 44;
        final int qiW = 8;
        final int qiH = 12;
        final boolean hover2 = mouseX > qiX && mouseY > qiY && mouseX < qiX + qiW && mouseY < qiY + qiH;
        bib.z().N().a(ModTextures.BUTTON_QUESTION);
        draw.drawTexture(qiX, 44.0, hover2 ? 135.0 : 0.0, 0.0, 122.0, 255.0, qiW, qiH);
        if (hover2) {
            final String string = ModColor.cl("9") + ModColor.cl("n") + LanguageManager.translate("information") + "\n" + ModColor.cl("f") + LanguageManager.translate("info_public_server_list");
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, (String[])draw.listFormattedStringToWidth(string, this.l / 4).toArray());
        }
        this.scrollbar.draw();
        super.a(mouseX, mouseY, partialTicks);
        final boolean isScrolled = !LabyMod.getInstance().isInGame() && this.scrollbar.getScrollY() == 0.0;
        final boolean isIndex0Selected = !LabyMod.getInstance().isInGame() && this.selectedServerId == 1;
        MultiplayerTabs.drawMultiplayerTabs(1, mouseX, mouseY, isScrolled, isIndex0Selected);
        MultiplayerTabs.drawParty(mouseX, mouseY, this.l);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
        draw.bindTexture(ModTextures.MISC_PARTNER_CROWN);
        draw.drawTexture(this.l / 2 - 176, this.m - 36, 255.0, 255.0, 10.0, 10.0, 1.1f);
        this.checkBox.drawCheckbox(mouseX, mouseY);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        MultiplayerTabs.mouseClickedMultiplayerTabs(1, mouseX, mouseY);
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
        if (this.hoverServerData != null) {
            if (this.selectedServerData != null && this.selectedServerData.getIpAddress().equals(this.hoverServerData.getIpAddress()) && this.lastTimeSelected + 400L > System.currentTimeMillis()) {
                this.joinOnServerData = this.selectedServerData;
            }
            this.selectedServerData = this.hoverServerData;
            this.lastTimeSelected = System.currentTimeMillis();
            this.alternativeDragClickY = (int)(this.scrollbar.getScrollY() - mouseY);
        }
        if (this.joinOnServerData != null) {
            this.joinServer();
        }
        if (this.saveServerData != null) {
            this.saveServer(this.selectedServerData);
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        if (this.checkBox != null && this.checkBox.mouseClicked(mouseX, mouseY, mouseButton)) {
            GuiServerList.partnersOnly = (this.checkBox.getValue() == CheckBox.EnumCheckBoxValue.ENABLED);
            this.sortServerList();
        }
        super.a(mouseX, mouseY, mouseButton);
    }
    
    private void joinServer() {
        final ServerPingerData serverPingerData = this.selectedServerData;
        if (serverPingerData != null) {
            if (LabyMod.getInstance().isInGame() && !bib.z().E() && LabyMod.getSettings().confirmDisconnect) {
                final blk lastScreen = bib.z().m;
                bib.z().a((blk)new bkq((bkp)new bkp() {
                    public void a(final boolean result, final int id) {
                        if (result) {
                            LabyMod.getInstance().getLabyConnect().setViaServerList(true);
                            LabyMod.getInstance().connectToServer(serverPingerData.getIpAddress());
                        }
                        else {
                            bib.z().a(lastScreen);
                        }
                    }
                }, LanguageManager.translate("warning_server_disconnect"), ModColor.cl("c") + serverPingerData.getIpAddress(), 0));
            }
            else {
                LabyMod.getInstance().getLabyConnect().setViaServerList(true);
                LabyMod.getInstance().connectToServer(serverPingerData.getIpAddress());
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        if (this.alternativeDragClickY != -1) {
            this.scrollbar.setScrollY((double)(this.alternativeDragClickY + mouseY));
            this.scrollbar.checkOutOfBorders();
        }
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        this.alternativeDragClickY = -1;
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 4) {
            if (this.parentScreen instanceof ModGuiMultiplayer) {
                bib.z().a(((ModGuiMultiplayer)this.parentScreen).getParentScreen());
            }
            else {
                bib.z().a(this.parentScreen);
            }
        }
        if (button.k == 6) {
            this.saveServer(this.selectedServerData);
        }
        if (button.k == 5 && this.selectedServerData != null) {
            this.joinServer();
        }
    }
    
    private void saveServer(final ServerPingerData selectedServerData) {
        if (this.parentScreen instanceof ModGuiMultiplayer) {
            final ModGuiMultiplayer gmp = (ModGuiMultiplayer)this.parentScreen;
            gmp.h().a(selectedServerData.toMCServerData());
            gmp.h().b();
            LabyModCore.getMinecraft().updateServerList(gmp.getServerSelector(), gmp.h());
            gmp.getServerSelector().h(Integer.MAX_VALUE);
            gmp.getServerSelector().c(gmp.h().c() - 1);
            bib.z().a(this.parentScreen);
        }
        else {
            final bsf serverList = new bsf(bib.z());
            serverList.a();
            serverList.a(selectedServerData.toMCServerData());
            serverList.b();
        }
    }
    
    public void k() throws IOException {
        this.scrollbar.mouseInput();
        super.k();
    }
    
    public blk getParentScreen() {
        return this.parentScreen;
    }
    
    static {
        GuiServerList.serverInfoRenderers = new HashMap<String, ServerInfoRenderer>();
        GuiServerList.serverInfoRenderersSorted = new ArrayList<ServerInfoRenderer>();
        GuiServerList.publicServerListEntrys = new ArrayList<ServerData>();
    }
}
