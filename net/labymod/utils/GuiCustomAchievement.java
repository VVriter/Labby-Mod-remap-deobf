//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import com.mojang.authlib.*;
import net.labymod.main.*;
import java.util.*;

public class GuiCustomAchievement extends bir
{
    private bib mc;
    private int width;
    private int height;
    private String achievementTitle;
    private String achievementDescription;
    private long notificationTime;
    private GameProfile gameProfile;
    private String imageURL;
    
    public GuiCustomAchievement(final bib mc) {
        this.mc = mc;
    }
    
    public void displayAchievement(final String title, final String description) {
        this.achievementTitle = title;
        this.achievementDescription = description;
        this.notificationTime = bib.I();
        this.gameProfile = null;
        this.imageURL = null;
    }
    
    public void displayAchievement(final GameProfile gameProfile, final String title, final String description) {
        this.achievementTitle = title;
        this.achievementDescription = description;
        this.notificationTime = bib.I();
        this.gameProfile = gameProfile;
        this.imageURL = null;
    }
    
    public void displayAchievement(final String imageURL, final String title, final String description) {
        this.achievementTitle = title;
        this.achievementDescription = description;
        this.notificationTime = bib.I();
        this.imageURL = imageURL;
        this.gameProfile = null;
    }
    
    private void updateAchievementWindowScale() {
        bus.b(0, 0, this.mc.d, this.mc.e);
        bus.n(5889);
        bus.F();
        bus.n(5888);
        bus.F();
        this.width = this.mc.d;
        this.height = this.mc.e;
        final bit scaledresolution = LabyMod.getInstance().getDrawUtils().getScaledResolution();
        this.width = scaledresolution.a();
        this.height = scaledresolution.b();
        bus.m(256);
        bus.n(5889);
        bus.F();
        bus.a(0.0, (double)this.width, (double)this.height, 0.0, 1000.0, 3000.0);
        bus.n(5888);
        bus.F();
        bus.c(0.0f, 0.0f, -2000.0f);
    }
    
    public void updateAchievementWindow() {
        if (this.notificationTime != 0L) {
            final double d0 = (bib.I() - this.notificationTime) / 3000.0;
            if (d0 < 0.0 || d0 > 1.0) {
                this.notificationTime = 0L;
                return;
            }
            this.updateAchievementWindowScale();
            bus.j();
            bus.a(false);
            double d2 = d0 * 2.0;
            if (d2 > 1.0) {
                d2 = 2.0 - d2;
            }
            d2 *= 4.0;
            d2 = 1.0 - d2;
            if (d2 < 0.0) {
                d2 = 0.0;
            }
            d2 *= d2;
            d2 *= d2;
            final int i = this.width - 160;
            final int j = 0 - (int)(d2 * 36.0);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.y();
            this.mc.N().a(ModTextures.ACHIEVEMENT);
            bus.g();
            this.b(i, j, 96, 202, 160, 32);
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            final int startX = i + ((this.gameProfile == null && this.imageURL == null) ? 8 : 30);
            final String title = draw.trimStringToWidth(this.achievementTitle, this.width - startX - 10);
            final List<String> descriptions = (List<String>)draw.listFormattedStringToWidth(this.achievementDescription, (int)((this.width - startX - 10) / 0.7), 2);
            if (descriptions.size() == 1) {
                draw.c(draw.fontRenderer, title, startX, j + 7, -256);
                draw.drawString(this.achievementDescription, (double)startX, (double)(j + 5 + 13), 0.7);
            }
            else {
                draw.c(draw.fontRenderer, title, startX, j + 5, -256);
                int y = j + 14 - 7;
                for (final String line : descriptions) {
                    final DrawUtils drawUtils = draw;
                    final String s = line;
                    final double n = startX;
                    y += 7;
                    drawUtils.drawString(s, n, (double)y, 0.75);
                }
            }
            if (this.gameProfile != null) {
                bhz.c();
                bus.g();
                bus.D();
                bus.h();
                bus.g();
                bus.d(1.0f, 1.0f, 1.0f);
                draw.drawPlayerHead(this.gameProfile, i + 8, j + 8, 16);
            }
            if (this.imageURL != null) {
                bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                LabyMod.getInstance().getDrawUtils().drawImageUrl(this.imageURL, (double)(i + 8), (double)(j + 8), 255.0, 255.0, 16.0, 16.0);
            }
            bus.a(true);
            bus.k();
        }
    }
    
    public void clearAchievements() {
        this.notificationTime = 0L;
        this.gameProfile = null;
        this.imageURL = null;
    }
}
