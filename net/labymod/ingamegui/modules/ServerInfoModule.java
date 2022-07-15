//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.utils.manager.*;
import net.labymod.core.*;
import java.util.concurrent.*;
import net.labymod.main.*;
import net.labymod.settings.elements.*;
import net.labymod.utils.*;
import net.labymod.ingamegui.enums.*;

public class ServerInfoModule extends ResizeableModule
{
    private long lastUpdate;
    private long updateCooldown;
    private ServerInfoRenderer serverInfoRenderer;
    
    public ServerInfoModule() {
        super((short)305, (short)34, (short)200, (short)34, (short)305, (short)34);
        this.lastUpdate = 0L;
        this.updateCooldown = 2000L;
        this.serverInfoRenderer = new ServerInfoRenderer("Unknown Server", null);
    }
    
    @Override
    public void drawModule(final int x, final int y, final int rightX, final int width, final int height, final int mouseX, final int mouseY) {
        if (!bib.z().E() && bib.z().C() != null && this.lastUpdate + this.updateCooldown < System.currentTimeMillis()) {
            this.lastUpdate = System.currentTimeMillis();
            LabyModCore.getServerPinger().pingServer((ExecutorService)null, this.lastUpdate, bib.z().C().b, (Consumer)new Consumer<ServerPingerData>() {
                @Override
                public void accept(final ServerPingerData accepted) {
                    if (accepted != null && accepted.getTimePinged() != ServerInfoModule.this.lastUpdate) {
                        return;
                    }
                    final String address = bib.z().C().b;
                    ServerInfoModule.this.serverInfoRenderer.init(address, address, accepted);
                }
            });
        }
        LabyMod.getInstance().getDrawUtils().drawRectangle(x, y, x + width, y + height, Integer.MIN_VALUE);
        this.serverInfoRenderer.drawEntry(x + 1, y + 1, width - 1, mouseX, mouseY);
    }
    
    public void loadSettings() {
    }
    
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.PAINTING);
    }
    
    public String getSettingName() {
        return "server_info";
    }
    
    public String getDescription() {
        return "";
    }
    
    public boolean isShown() {
        return LabyModCore.getMinecraft().getPlayer() != null && !bib.z().E();
    }
    
    public int getSortingId() {
        return 0;
    }
    
    public EnumDisplayType[] getDisplayTypes() {
        return new EnumDisplayType[] { EnumDisplayType.ESCAPE };
    }
}
