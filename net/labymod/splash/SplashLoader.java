//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.splash;

import net.labymod.support.*;
import com.google.gson.*;
import java.awt.*;
import net.labymod.splash.advertisement.*;
import java.lang.reflect.*;
import java.util.*;
import net.labymod.splash.splashdates.*;
import net.labymod.utils.request.*;
import net.labymod.main.update.migration.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.support.util.*;

public class SplashLoader
{
    private static SplashLoader loader;
    private final Gson gson;
    private final LatestMinecraftPopupRenderer latestMinecraftPopupRenderer;
    private SplashEntries entries;
    private Advertisement hoverAdvertisement;
    private String customSplashText;
    private DashboardConnector dashboardConnector;
    
    public SplashLoader() {
        this.latestMinecraftPopupRenderer = new LatestMinecraftPopupRenderer();
        this.dashboardConnector = new DashboardConnector();
        this.gson = new GsonBuilder().registerTypeAdapter((Type)Color.class, (Object)new AdColorAdapter()).create();
        this.load();
    }
    
    public void load() {
        DownloadServerRequest.getStringAsync("https://dl.labymod.net/advertisement/entries.json", new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                SplashLoader.this.entries = (SplashEntries)SplashLoader.this.gson.fromJson(json, (Class)SplashEntries.class);
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                for (final SplashDate date : SplashLoader.this.entries.getSplashDates()) {
                    if (calendar.get(2) + 1 == date.getMonth() && calendar.get(5) == date.getDay()) {
                        SplashLoader.this.customSplashText = date.getDisplayString();
                    }
                }
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
            }
        });
    }
    
    public void render(final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        this.hoverAdvertisement = null;
        if (this.entries != null) {
            bus.e();
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            try {
                this.renderList(this.entries.getLeft(), false, 3, 3, mouseX, mouseY);
                this.renderList(this.entries.getRight(), true, draw.getWidth() - 3, 3, mouseX, mouseY);
            }
            catch (Exception error) {
                error.printStackTrace();
                this.entries = null;
            }
            if (this.latestMinecraftPopupRenderer.isVisible()) {
                final LatestMinecraftVersion latest = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
                this.latestMinecraftPopupRenderer.draw(mouseX, mouseY, latest.minecraftVersion);
            }
            bus.d();
        }
    }
    
    private void renderList(final Advertisement[] advertisements, final boolean rightBound, final int positionX, int positionY, final int mouseX, final int mouseY) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int iconSize = 15;
        for (final Advertisement advertisement : advertisements) {
            if (advertisement != null) {
                if (advertisement.isVisible()) {
                    int x = positionX;
                    bus.G();
                    final int width = draw.getStringWidth(advertisement.getTitle()) + 2;
                    if (rightBound) {
                        x -= iconSize + width;
                    }
                    final boolean hover = mouseX > x && mouseX < x + iconSize + width && mouseY > positionY && mouseY < positionY + iconSize;
                    final Color textColor = hover ? advertisement.getColorHover() : advertisement.getColor();
                    final String url = String.format("https://dl.labymod.net/advertisement/icons/%s.png", advertisement.getIconName());
                    LabyMod.getInstance().getDrawUtils().drawImageUrl(url, x - (hover ? 1 : 0) + (rightBound ? width : 0), positionY - (hover ? 1 : 0), 255.0, 255.0, iconSize + (hover ? 2 : 0), iconSize + (hover ? 2 : 0));
                    if (rightBound) {
                        x -= iconSize + 2;
                    }
                    bus.H();
                    if (advertisement.isNew()) {
                        float pause = (float)(Math.sin(System.currentTimeMillis() / 500.0) * 3.0);
                        if (pause < 0.0f) {
                            pause = 0.0f;
                        }
                        final float bounce = (float)Math.abs(Math.cos(System.currentTimeMillis() / 100.0) * -pause);
                        bib.z().N().a(ModTextures.BUTTON_EXCLAMATION);
                        draw.drawTexture(x + (rightBound ? (width + 7) : 0) + 10 + (hover ? (rightBound ? -1 : 1) : 0), positionY + 5 + (hover ? 1 : 0) - bounce, hover ? 125.0 : 0.0, 0.0, 120.0, 255.0, 5.0, 10.0, 1.0f);
                    }
                    draw.c(draw.getFontRenderer(), ModColor.createColors(advertisement.getTitle()), x + iconSize + 2 + (hover ? (rightBound ? -1 : 1) : 0), positionY + iconSize / 2 - 4, textColor.getRGB());
                    if (hover) {
                        this.hoverAdvertisement = advertisement;
                    }
                    positionY += iconSize + 1;
                }
            }
        }
        if (rightBound) {
            this.dashboardConnector.renderIcon(positionX - 10, positionY + 10, mouseX, mouseY);
        }
    }
    
    public void onClick(final int mouseX, final int mouseY) {
        if (this.hoverAdvertisement != null) {
            LabyMod.getInstance().openWebpage(this.hoverAdvertisement.getUrl(), true);
        }
        this.dashboardConnector.mouseClicked(mouseX, mouseY, 1);
        if (mouseX < 10 && mouseY > LabyMod.getInstance().getDrawUtils().getHeight() - 10) {
            Debug.openDebugConsole();
        }
        this.latestMinecraftPopupRenderer.onClick(mouseX, mouseY);
    }
    
    public static SplashLoader getLoader() {
        return SplashLoader.loader;
    }
    
    public SplashEntries getEntries() {
        return this.entries;
    }
    
    public String getCustomSplashText() {
        return this.customSplashText;
    }
    
    static {
        SplashLoader.loader = new SplashLoader();
    }
}
