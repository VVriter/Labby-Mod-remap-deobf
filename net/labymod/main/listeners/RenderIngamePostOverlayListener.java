//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.api.events.*;
import java.util.*;
import net.labymod.main.*;
import net.labymod.api.permissions.*;
import net.labymod.utils.*;
import com.google.gson.*;

public class RenderIngamePostOverlayListener implements ServerMessageEvent, RenderIngameOverlayEvent
{
    private Economy cash;
    private Economy bank;
    private boolean watermarkVisible;
    private long watermarkValidTime;
    private int csPrevCoverage;
    private int csCoverage;
    private long csDuration;
    private long csLastChanged;
    private Map<UUID, nf> languageFlags;
    private boolean labyOnlyServer;
    private String serverBanner;
    
    public RenderIngamePostOverlayListener() {
        this.cash = new Economy();
        this.bank = new Economy();
        this.watermarkVisible = false;
        this.watermarkValidTime = 0L;
        this.csPrevCoverage = 0;
        this.csCoverage = 20;
        this.csDuration = 1000L;
        this.csLastChanged = 0L;
        this.languageFlags = new HashMap<UUID, nf>();
        this.labyOnlyServer = false;
        this.serverBanner = null;
    }
    
    public void onRender(final float partialTicks) {
        bus.G();
        bus.c(0.0f, 0.0f, 10.0f);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        LabyMod.getInstance().getMarkerManager().renderOverlay(partialTicks);
        if (this.cash.isVisible()) {
            bus.G();
            bib.z().N().a(this.cash.getIcon(ModTextures.MISC_ECONOMY_CASH));
            draw.drawTexture(draw.getWidth() - 17, 8.5, 255.0, 255.0, 14.0, 14.0);
            draw.drawRightString(this.cash.getDisplayValue(), draw.getWidth() - 20, 10.0, 1.5);
            bus.H();
        }
        if (this.bank.isVisible()) {
            bus.G();
            bib.z().N().a(this.bank.getIcon(ModTextures.MISC_ECONOMY_BANK));
            draw.drawTexture(draw.getWidth() - 17, 8.5f + (this.cash.isVisible() ? 17 : 0), 255.0, 255.0, 14.0, 14.0);
            draw.drawRightString(this.bank.getDisplayValue(), draw.getWidth() - 20, 10 + (this.cash.isVisible() ? 17 : 0), 1.5);
            bus.H();
        }
        if (this.watermarkVisible && !(bib.z().m instanceof bkn)) {
            bus.G();
            bib.z().N().a(ModTextures.TITLE_LABYMOD_BANNER_WATERMARK);
            draw.drawTexture(draw.getWidth() - 100 - 2, draw.getHeight() - 20 - 2, 255.0, 255.0, 100.0, 20.0);
            bus.H();
        }
        if (this.isCineScopeActive()) {
            final long timePassed = System.currentTimeMillis() - this.csLastChanged;
            final double x = 10.0 / this.csDuration * Math.min(this.csDuration, timePassed) - 5.0;
            final double y = 1.0 / (1.0 + Math.exp(-x));
            final double difference = this.csCoverage - this.csPrevCoverage;
            final double cineScopeProgress = this.csPrevCoverage + difference * y;
            final double cineScopeHeight = draw.getHeight() / 100.0 * (cineScopeProgress * 1.01);
            draw.drawRect(0.0, 0.0, draw.getWidth(), cineScopeHeight, ModColor.BLACK.getColor().getRGB());
            draw.drawRect(0.0, draw.getHeight() - cineScopeHeight, draw.getWidth(), draw.getHeight(), ModColor.BLACK.getColor().getRGB());
        }
        LabyMod.getInstance().getGuiCustomAchievement().updateAchievementWindow();
        Permissions.getPermissionNotifyRenderer().render(draw.getWidth());
        bus.c(0.0f, 0.0f, -10.0f);
        bus.H();
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("economy")) {
            final JsonObject economy = serverMessage.getAsJsonObject();
            this.cash.update(economy, "cash");
            this.bank.update(economy, "bank");
        }
        if (messageKey.equals("watermark")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            if (obj.has("visible")) {
                this.watermarkVisible = obj.get("visible").getAsBoolean();
                this.watermarkValidTime = System.currentTimeMillis() + 10000L;
            }
        }
        if (messageKey.equals("cinescopes")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            if (obj.has("coverage")) {
                this.csLastChanged = System.currentTimeMillis();
                this.csPrevCoverage = this.csCoverage;
                this.csCoverage = obj.get("coverage").getAsInt();
                this.csCoverage = Math.min(50, this.csCoverage);
                this.csCoverage = Math.max(0, this.csCoverage);
            }
            if (obj.has("duration")) {
                this.csDuration = obj.get("duration").getAsLong();
                this.csDuration = Math.min(10000L, this.csDuration);
                this.csDuration = Math.max(10L, this.csDuration);
            }
        }
        if (messageKey.equals("language_flag")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            if (obj.has("users")) {
                final JsonArray array = obj.get("users").getAsJsonArray();
                for (int i = 0; i < array.size(); ++i) {
                    final JsonObject userEntry = array.get(i).getAsJsonObject();
                    if (userEntry.has("uuid") && userEntry.has("code")) {
                        final UUID uuid = UUID.fromString(userEntry.get("uuid").getAsString());
                        final String code = userEntry.get("code").getAsString().toLowerCase();
                        if (!code.contains(".") && !code.contains("/") && !code.contains("\\")) {
                            this.languageFlags.put(uuid, new nf(String.format("labymod/textures/flags/%s.png", code)));
                        }
                    }
                }
            }
        }
        if (messageKey.equals("server_banner")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            this.serverBanner = (obj.has("url") ? obj.get("url").getAsString() : null);
        }
        if (messageKey.equals("laby_only_server")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            if (obj.has("enabled")) {
                this.labyOnlyServer = obj.get("enabled").getAsBoolean();
            }
        }
    }
    
    public void reset() {
        this.csCoverage = 0;
        this.csDuration = 1000L;
        this.csLastChanged = -1L;
        this.watermarkVisible = false;
        this.bank.reset();
        this.cash.reset();
        this.languageFlags.clear();
        this.labyOnlyServer = false;
        this.serverBanner = null;
    }
    
    public boolean isWatermarkValid() {
        return this.watermarkVisible || System.currentTimeMillis() < this.watermarkValidTime;
    }
    
    public boolean isCineScopeActive() {
        return this.csCoverage != 0 || System.currentTimeMillis() < this.csLastChanged + this.csDuration;
    }
    
    public boolean isLabyOnlyServer() {
        return this.labyOnlyServer;
    }
    
    public nf getFlagFor(final UUID uuid) {
        return this.languageFlags.get(uuid);
    }
    
    public Map<UUID, nf> getLanguageFlags() {
        return this.languageFlags;
    }
    
    public String getServerBanner() {
        return this.serverBanner;
    }
    
    public boolean hasServerBanner() {
        return (this.serverBanner != null || this.labyOnlyServer) && LabyMod.getSettings().serverBanner;
    }
}
