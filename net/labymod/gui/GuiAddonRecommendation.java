//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.gui.elements.*;
import net.labymod.addon.online.*;
import net.labymod.main.lang.*;
import net.labymod.addon.*;
import net.labymod.addon.online.info.*;
import net.labymod.main.*;
import net.labymod.settings.*;
import net.labymod.main.listeners.*;
import java.io.*;
import net.labymod.utils.*;
import java.util.*;
import java.beans.*;

public class GuiAddonRecommendation extends blk
{
    private final String serverName;
    private final List<RecommendedAddon> addons;
    private final boolean notifyServer;
    private bja buttonDone;
    private CheckBox checkBox;
    
    public GuiAddonRecommendation(final String serverName, final List<RecommendedAddon> addons, final boolean notifyServer) {
        this.serverName = serverName;
        this.addons = addons;
        this.notifyServer = notifyServer;
        AddonInfoManager.getInstance().createElementsForAddons();
    }
    
    public void b() {
        super.b();
        boolean hasRequired = false;
        for (final RecommendedAddon addon : this.addons) {
            final OnlineAddonInfo info = addon.getOnlineAddonInfo();
            if (info != null && addon.isRequired()) {
                hasRequired = true;
            }
        }
        if (hasRequired) {
            this.n.add(new bja(1, this.l / 2 - 100 - 5, this.m - 50, 100, 20, LanguageManager.translate("button_cancel")));
            this.n.add(this.buttonDone = new bja(0, this.l / 2 + 5, this.m - 50, 100, 20, LanguageManager.translate("button_done")));
        }
        else {
            this.n.add(this.buttonDone = new bja(0, this.l / 2 - 100, this.m - 50, LanguageManager.translate("button_done")));
        }
        this.checkBox = new CheckBox("", CheckBox.EnumCheckBoxValue.DISABLED, (CheckBox.DefaultCheckBoxValueCallback)null, 0, this.m - 80, 20, 20);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c();
        int amount = 0;
        boolean hasRequired = false;
        boolean allInstalled = true;
        for (final RecommendedAddon addon : this.addons) {
            final OnlineAddonInfo info = addon.getOnlineAddonInfo();
            if (info == null) {
                continue;
            }
            ++amount;
            if (!addon.isRequired()) {
                continue;
            }
            hasRequired = true;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int width = 300;
        final int x = draw.getWidth() / 2 - width / 2;
        int y = draw.getHeight() / 2 - 42 * amount / 2;
        final String translationKey = hasRequired ? "addon_recommendation_title_required" : "addon_recommendation_title_optional";
        final ServerData serverData = LabyMod.getInstance().getCurrentServerData();
        final String ip = (this.serverName == null) ? ((serverData == null || serverData.getIp() == null) ? "<server>" : serverData.getIp()) : this.serverName;
        final String title = String.format(ModColor.cl('a') + LanguageManager.translate(translationKey), ModColor.cl('E') + ip + ModColor.cl('a'));
        draw.drawCenteredString(title, draw.getWidth() / 2, y - 20);
        for (final RecommendedAddon addon2 : this.addons) {
            final OnlineAddonInfo info2 = addon2.getOnlineAddonInfo();
            if (info2 == null) {
                continue;
            }
            info2.getAddonElement().draw(x, y, x + width, y + 40, mouseX, mouseY, false);
            if (AddonLoader.hasInstalled((AddonInfo)addon2.getOnlineAddonInfo())) {
                bib.z().N().a(ModTextures.BUTTON_CHECKBOX);
                draw.drawTexture(x - 25, y + 10, 255.0, 255.0, 20.0, 20.0);
            }
            else if (addon2.isRequired()) {
                bib.z().N().a(ModTextures.BUTTON_EXCLAMATION);
                draw.drawTexture(x - 20, y + 10, 127.0, 255.0, 10.0, 20.0);
                allInstalled = false;
            }
            else {
                allInstalled = false;
            }
            if (addon2.isRequired()) {
                draw.drawString(ModColor.cl('c') + ModColor.cl('l') + LanguageManager.translate("addon_recommendation_required"), x + width + 5, y + 15);
            }
            y += 42;
        }
        if (!hasRequired) {
            final String checkBoxLabel = LanguageManager.translate("addon_recommendation_ignore");
            this.checkBox.setX(draw.getWidth() / 2 - draw.getStringWidth(checkBoxLabel) / 2 - 25);
            this.checkBox.drawCheckbox(mouseX, mouseY);
            draw.drawCenteredString(checkBoxLabel, draw.getWidth() / 2, draw.getHeight() - 74);
        }
        this.buttonDone.l = (!hasRequired || allInstalled);
        if (hasRequired && LabyModAddonsGui.isRestartRequired()) {
            this.buttonDone.j = LanguageManager.translate("button_restart");
            this.buttonDone.l = true;
        }
        else {
            this.buttonDone.j = LanguageManager.translate("button_done");
        }
        super.a(mouseX, mouseY, partialTicks);
    }
    
    public void m() {
        super.m();
        if (this.notifyServer) {
            AddonRecommendationListener.sendMissingStatus(this.addons, true);
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
    }
    
    public void e() {
        super.e();
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        boolean hasRequired = false;
        for (final RecommendedAddon addon : this.addons) {
            final OnlineAddonInfo info = addon.getOnlineAddonInfo();
            if (info == null) {
                continue;
            }
            if (!addon.isRequired()) {
                continue;
            }
            hasRequired = true;
        }
        switch (button.k) {
            case 0: {
                final ServerData currentServer = LabyMod.getInstance().getCurrentServerData();
                final boolean trusted = currentServer != null && currentServer.getIp() != null && this.checkBox.getValue() == CheckBox.EnumCheckBoxValue.ENABLED;
                if (trusted) {
                    final List<String> list = new ArrayList<String>(Arrays.asList(LabyMod.getSettings().ignoredAddonRecommendationServers));
                    final String profileAddress = ModUtils.getProfileNameByIp(currentServer.getIp());
                    if (trusted && !list.contains(profileAddress)) {
                        list.add(profileAddress);
                        final String[] array = new String[list.size()];
                        list.toArray(array);
                        LabyMod.getSettings().ignoredAddonRecommendationServers = array;
                        LabyMod.getMainConfig().save();
                    }
                }
                if (hasRequired && LabyModAddonsGui.isRestartRequired()) {
                    bib.z().n();
                    break;
                }
                bib.z().a((blk)null);
                break;
            }
            case 1: {
                bib.z().a((blk)null);
                break;
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        for (final RecommendedAddon addon : this.addons) {
            final OnlineAddonInfo info = addon.getOnlineAddonInfo();
            if (info == null) {
                continue;
            }
            info.getAddonElement().mouseClicked(mouseX, mouseY, mouseButton);
        }
        boolean hasRequired = false;
        for (final RecommendedAddon addon2 : this.addons) {
            final OnlineAddonInfo info2 = addon2.getOnlineAddonInfo();
            if (info2 == null) {
                continue;
            }
            if (!addon2.isRequired()) {
                continue;
            }
            hasRequired = true;
        }
        if (!hasRequired) {
            this.checkBox.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public static class RecommendedAddon
    {
        private UUID uuid;
        private boolean required;
        private transient OnlineAddonInfo onlineAddonInfo;
        
        public void bindAddon(final AddonInfoManager manager) {
            final AddonInfo addonInfo = manager.getAddonInfoMap().get(this.uuid);
            if (addonInfo instanceof OnlineAddonInfo) {
                this.onlineAddonInfo = (OnlineAddonInfo)addonInfo;
            }
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
        
        public boolean isRequired() {
            return this.required;
        }
        
        public OnlineAddonInfo getOnlineAddonInfo() {
            return this.onlineAddonInfo;
        }
        
        @ConstructorProperties({ "uuid", "required", "onlineAddonInfo" })
        public RecommendedAddon(final UUID uuid, final boolean required, final OnlineAddonInfo onlineAddonInfo) {
            this.uuid = uuid;
            this.required = required;
            this.onlineAddonInfo = onlineAddonInfo;
        }
    }
}
