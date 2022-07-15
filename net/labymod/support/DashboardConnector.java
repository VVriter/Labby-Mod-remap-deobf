//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support;

import net.labymod.main.*;
import net.labymod.utils.manager.*;
import net.labymod.main.lang.*;
import net.labymod.labyconnect.*;
import net.labymod.utils.*;
import java.util.*;

public class DashboardConnector
{
    private long lastRequest;
    private boolean hoverIcon;
    
    public DashboardConnector() {
        this.lastRequest = -1L;
        this.hoverIcon = false;
    }
    
    public void renderIcon(final int x, final int y, final int mouseX, final int mouseY) {
        final bii session = bib.z().K();
        final LabyConnect labyConnect = LabyMod.getInstance().getLabyConnect();
        if (session == null || session.e() == null || !labyConnect.isOnline()) {
            return;
        }
        final int iconSize = 20;
        final int gearSize = 13;
        this.hoverIcon = (mouseX > x - iconSize / 2 && mouseX < x + iconSize / 2 && mouseY > y - iconSize / 2 && mouseY < y + iconSize / 2);
        final int hoverAnimation = this.hoverIcon ? 1 : 0;
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        if (this.isOnCooldown()) {
            bus.d(0.5f, 0.5f, 0.5f);
        }
        else {
            bus.d(1.0f, 1.0f, 1.0f);
        }
        draw.drawPlayerHead(session.e(), x - iconSize / 2 - hoverAnimation, y - iconSize / 2 - hoverAnimation, iconSize + hoverAnimation * 2);
        bib.z().N().a(ModTextures.BUTTON_ADVANCED);
        draw.drawTexture(x - hoverAnimation, y - hoverAnimation, 255.0, 255.0, gearSize + hoverAnimation * 2, gearSize + hoverAnimation * 2);
        if (this.hoverIcon) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, LanguageManager.translate("open_dashboard_website"));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.hoverIcon) {
            this.openDashboard();
        }
    }
    
    private boolean isOnCooldown() {
        return System.currentTimeMillis() - this.lastRequest < 5000L;
    }
    
    public void openDashboard() {
        final LabyConnect labyConnect = LabyMod.getInstance().getLabyConnect();
        final UUID uuid = LabyMod.getInstance().getPlayerUUID();
        if (!labyConnect.isOnline()) {
            LabyMod.getInstance().openWebpage("https://www.labymod.net/dashboard", false);
        }
        else if (!this.isOnCooldown()) {
            this.lastRequest = System.currentTimeMillis();
            labyConnect.getClientConnection().requestDashboardPin(pin -> LabyMod.getInstance().openWebpage(String.format("https://www.labymod.net/key/?id=%s&pin=%s", uuid, pin), false));
        }
    }
}
