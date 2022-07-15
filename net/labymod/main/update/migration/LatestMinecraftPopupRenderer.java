//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update.migration;

import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.utils.*;
import java.util.*;
import java.util.function.*;
import net.labymod.main.update.migration.gui.*;
import net.labymod.main.lang.*;
import net.labymod.support.util.*;
import net.labymod.main.update.*;

public class LatestMinecraftPopupRenderer
{
    private MigrationUpdater latestMinecraftVersionInstaller;
    private boolean hoverUpdateClose;
    private boolean hoverUpdate;
    private boolean updateStarted;
    private boolean updateFinished;
    private String updateProgressMessage;
    private double updateProgress;
    private double prevUpdateProgress;
    private long lastProgressUpdate;
    
    public LatestMinecraftPopupRenderer() {
        this.latestMinecraftVersionInstaller = new MigrationUpdater();
        this.hoverUpdateClose = false;
        this.hoverUpdate = false;
        this.updateStarted = false;
        this.updateFinished = false;
        this.updateProgressMessage = null;
        this.updateProgress = 0.0;
        this.prevUpdateProgress = 0.0;
        this.lastProgressUpdate = 0L;
    }
    
    public void draw(final int mouseX, final int mouseY, final String version) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final double updateWidth = 100.0;
        final double updateHeight = 20.0;
        final double centerX = draw.getWidth() / 2;
        final double centerY = 90 + (draw.getHeight() / 2 - 40 - 90) / 2;
        int closeSize = 5;
        final double closeX = centerX + updateWidth;
        final double closeY = centerY - updateHeight / 2.0;
        final boolean hoverClose = mouseX > closeX - closeSize && mouseX < closeX + closeSize && mouseY > closeY - closeSize && mouseY < closeY + closeSize;
        closeSize = (hoverClose ? 5 : 4);
        final boolean hover = !hoverClose && mouseX > centerX - updateWidth && mouseX < centerX + updateWidth && mouseY > centerY - updateHeight / 2.0 && mouseY < centerY + updateHeight / 2.0;
        draw.drawRect(centerX - updateWidth - 1.0, centerY - updateHeight / 2.0 - 1.0, centerX + updateWidth + 1.0, centerY + updateHeight / 2.0 + 1.0, hover ? ModColor.toRGB(200, 100, 200, 100) : Integer.MAX_VALUE);
        if (this.updateStarted && !this.updateFinished) {
            final double partialTicks = Math.min(1.0f, (System.currentTimeMillis() - this.lastProgressUpdate) / 1000.0f);
            final double progress = this.prevUpdateProgress + (this.updateProgress - this.prevUpdateProgress) * partialTicks;
            final double progressWith = (updateWidth * 2.0 + 2.0) / 100.0 * Math.min(100.0, progress);
            draw.drawRect(centerX - updateWidth - 1.0, centerY - updateHeight / 2.0 - 1.0, centerX - updateWidth - 1.0 + progressWith, centerY + updateHeight / 2.0 + 1.0, hover ? ModColor.toRGB(100, 250, 100, 100) : Integer.MAX_VALUE);
        }
        draw.drawRect(centerX - updateWidth, centerY - updateHeight / 2.0, centerX + updateWidth, centerY + updateHeight / 2.0, Integer.MIN_VALUE);
        draw.drawRect(centerX - updateWidth, centerY - updateHeight / 2.0, centerX + updateWidth, centerY + updateHeight / 2.0, Integer.MIN_VALUE);
        final String message = ModColor.createColors((this.updateProgressMessage == null) ? LabyMod.getMessage("update_minecraft", new Object[] { version }) : this.updateProgressMessage);
        final int lenght = draw.getStringWidth(message);
        if (lenght * 0.7 > updateWidth * 2.0) {
            final List<String> list = draw.listFormattedStringToWidth(message, (int)(updateWidth * 2.0 / 0.7), 2);
            double listY = centerY - 6.5;
            for (final String line : list) {
                draw.drawCenteredString(line, draw.getWidth() / 2, listY, 0.7);
                listY += 8.0;
            }
        }
        else {
            draw.drawCenteredString(message, draw.getWidth() / 2, centerY - 2.5, 0.7);
        }
        if (!this.updateStarted && !this.updateFinished) {
            bus.G();
            bus.z();
            bus.d(hoverClose ? 0.8f : 0.2f, 0.2f, 0.2f);
            bus.b(closeX, closeY, 0.0);
            GL11.glBegin(9);
            for (int i = 0; i < 6; ++i) {
                GL11.glVertex2d(Math.sin(i / 6.0 * 2.0 * 3.141592653589793) * closeSize, Math.cos(i / 6.0 * 2.0 * 3.141592653589793) * closeSize);
            }
            GL11.glEnd();
            bus.y();
            bus.H();
            bus.G();
            bus.d(0.8f, 0.2f, 0.2f);
            bus.b(closeX, closeY, 0.0);
            for (int i = 1; i < 3; ++i) {
                bus.b((float)(45 * i), 0.0f, 0.0f, 1.0f);
                draw.drawRect(-closeSize / 2.0, -0.3, closeSize / 2.0, 0.3, -1);
            }
            bus.H();
        }
        this.hoverUpdate = hover;
        this.hoverUpdateClose = hoverClose;
    }
    
    public void onClick(final int mouseX, final int mouseY) {
        if (this.hoverUpdate && this.isVisible()) {
            this.hoverUpdate = false;
            this.migrateNow();
        }
        if (this.hoverUpdateClose && !this.updateStarted && !this.updateFinished) {
            this.hoverUpdateClose = false;
            bib.z().a((blk)new GuiGiveItATry(bib.z().m, (Consumer)new Consumer<Integer>() {
                @Override
                public void accept(final Integer option) {
                    if (option == 0) {
                        LabyMod.getSettings().ignoreMigration = true;
                        LabyMod.getMainConfig().save();
                    }
                    if (option == 2) {
                        LatestMinecraftPopupRenderer.this.migrateNow();
                    }
                }
            }));
        }
    }
    
    private void migrateNow() {
        if (!this.updateStarted) {
            this.updateStarted = true;
            this.latestMinecraftVersionInstaller.initAndInstallAsnyc((InstallationProgressCallback)new InstallationProgressCallback() {
                public void progress(final double progress, final String message) {
                    LatestMinecraftPopupRenderer.this.prevUpdateProgress = LatestMinecraftPopupRenderer.this.updateProgress;
                    LatestMinecraftPopupRenderer.this.lastProgressUpdate = System.currentTimeMillis();
                    LatestMinecraftPopupRenderer.this.updateProgressMessage = message;
                    LatestMinecraftPopupRenderer.this.updateProgress = progress;
                }
                
                public void failed() {
                    LatestMinecraftPopupRenderer.this.updateProgressMessage = ModColor.cl('c') + LanguageManager.translate("update_minecraft_failed");
                }
                
                public void completed() {
                    LatestMinecraftPopupRenderer.this.updateFinished = true;
                    LatestMinecraftPopupRenderer.this.updateProgressMessage = ModColor.cl('a') + LanguageManager.translate("update_minecraft_finished");
                }
            });
        }
        else if (this.updateFinished) {
            bib.z().n();
        }
    }
    
    public MigrationUpdater getInstaller() {
        return this.latestMinecraftVersionInstaller;
    }
    
    public boolean isVisible() {
        final LabyModUpdateChecker update = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker();
        return !LabyMod.getSettings().ignoreMigration && (this.updateStarted || this.updateFinished || (update != null && update.getLatestMinecraftVersion() != null && (!update.getLatestMinecraftVersion().isPlusOnly || Debug.isActive()) && !this.latestMinecraftVersionInstaller.hasLatestVersion()));
    }
}
