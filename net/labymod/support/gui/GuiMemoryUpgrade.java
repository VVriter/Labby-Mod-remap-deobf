//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.gui;

import net.labymod.main.lang.*;
import net.labymod.settings.elements.*;
import org.apache.commons.io.*;
import java.nio.charset.*;
import com.google.gson.*;
import net.labymod.utils.*;
import java.io.*;
import net.labymod.main.*;
import org.lwjgl.input.*;
import java.util.*;

public class GuiMemoryUpgrade extends blk
{
    private final File launcherProfilesFile;
    private final GsonBuilder gsonBuilder;
    private static final long TRIGGER_MAX_MEMORY = 2048000000L;
    private static final int TRIGGER_SCORE = 3;
    private static final long TRIGGER_FRAME_DURATION = 100L;
    private static long maxMemoryUsed;
    private static long lastMemoryMax;
    private static boolean outOfMemoryDetected;
    private static long lastFrameMemory;
    private static long lastFrameTimestamp;
    private static int frameHits;
    private SliderElement element;
    private bja buttonRestartGame;
    private String errorMessage;
    private int totalMB;
    private int updatedMB;
    
    public GuiMemoryUpgrade() {
        this.launcherProfilesFile = new File(LauncherDirectoryUtils.getWorkingDirectory(), "launcher_profiles.json");
        this.gsonBuilder = new GsonBuilder().setPrettyPrinting();
        this.errorMessage = null;
        final long maxMemory = Runtime.getRuntime().maxMemory();
        int totalMB = (int)(maxMemory / 1000000L);
        if (totalMB < 1) {
            totalMB = 1;
        }
        this.totalMB = totalMB;
        this.updatedMB = totalMB;
    }
    
    public void b() {
        this.n.clear();
        final int divY = this.m / 2 - 40 + 50;
        this.n.add(new bja(0, this.l / 2 - 100, divY + 30, 90, 20, LanguageManager.translate("button_cancel")));
        this.n.add(this.buttonRestartGame = new bja(1, this.l / 2 + 10, divY + 30, 90, 20, LanguageManager.translate("button_restart")));
        this.buttonRestartGame.l = (this.totalMB != this.updatedMB);
        this.element = new SliderElement(LanguageManager.translate("out_of_memory_slider"), new ControlElement.IconData(Material.IRON_PICKAXE), this.updatedMB).addCallback((Consumer)new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                GuiMemoryUpgrade.this.buttonRestartGame.l = (GuiMemoryUpgrade.this.totalMB != accepted);
                GuiMemoryUpgrade.this.updatedMB = accepted;
            }
        }).setRange(512, 4096).setSteps(512);
        super.b();
    }
    
    private boolean updateMemoryArguments(final String version, final int mb) throws Exception {
        final JsonObject mainObject = JsonParse.parse(IOUtils.toString((InputStream)new FileInputStream(this.launcherProfilesFile))).getAsJsonObject();
        final JsonObject profiles = mainObject.get("profiles").getAsJsonObject();
        if (!profiles.has(version)) {
            return false;
        }
        final JsonObject versionObject = profiles.get(version).getAsJsonObject();
        final String javaArgs = "javaArgs";
        if (versionObject.has(javaArgs)) {
            final String arguments = versionObject.get(javaArgs).getAsString();
            final String newArguments = arguments.replaceAll("-[x|X][m|M][x|X][0-9]+\\w+", "-Xmx" + mb + "M");
            versionObject.addProperty(javaArgs, newArguments);
        }
        else {
            versionObject.addProperty(javaArgs, "-Xmx" + mb + "M");
        }
        IOUtils.write(this.gsonBuilder.create().toJson((JsonElement)mainObject), (OutputStream)new FileOutputStream(this.launcherProfilesFile), Charset.forName("UTF-8"));
        return true;
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int divY = this.m / 2 - 40;
        draw.drawCenteredString(ModColor.cl('c') + LanguageManager.translate("out_of_memory_slider"), this.l / 2, divY);
        draw.drawCenteredString(LanguageManager.translate("out_of_memory_subtitle"), this.l / 2, divY + 10);
        draw.drawCenteredString((this.errorMessage == null) ? (ModColor.cl('a') + LanguageManager.translate("out_of_memory_recommendation")) : (ModColor.cl("4") + this.errorMessage), this.l / 2, divY + 30);
        final int centerPosX = this.l / 2;
        final int centerPosY = divY + 55;
        final int elementWidth = 200;
        final int elementHeight = 22;
        final String value = ModColor.cl('b') + "= " + ModUtils.humanReadableByteCount(this.updatedMB * 1000000L, true, true);
        draw.drawString(value, centerPosX + elementWidth / 2 + 5, centerPosY - 3);
        this.element.draw(centerPosX - elementWidth / 2, centerPosY - elementHeight / 2, centerPosX + elementWidth / 2, centerPosY + elementHeight / 2, mouseX, mouseY);
        super.a(mouseX, mouseY, partialTicks);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.element.mouseClicked(mouseX, mouseY, mouseButton);
        super.a(mouseX, mouseY, mouseButton);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        this.element.mouseRelease(mouseX, mouseY, state);
        super.b(mouseX, mouseY, state);
    }
    
    protected void a(final bja button) throws IOException {
        switch (button.k) {
            case 0: {
                bib.z().a((blk)(LabyMod.getInstance().isInGame() ? null : new blr()));
                break;
            }
            case 1: {
                try {
                    final String version = bib.z().c();
                    this.killMinecraftLauncher();
                    if (this.updateMemoryArguments(Source.PROFILE_VERSION_NAME, this.updatedMB) | this.updateMemoryArguments(version, this.updatedMB)) {
                        bib.z().n();
                    }
                    else {
                        this.errorMessage = LanguageManager.translate("out_of_memory_profile_not_found", new Object[] { version });
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    this.errorMessage = "ERROR: " + e.getMessage();
                }
                break;
            }
        }
        super.a(button);
    }
    
    private void killMinecraftLauncher() {
        try {
            final Runtime runtime = Runtime.getRuntime();
            runtime.exec("taskkill /F /IM MinecraftLauncher.exe");
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
    
    public static void renderTickOutOfMemoryDetector() {
        final long maxMemory = Runtime.getRuntime().maxMemory();
        if (maxMemory >= 2048000000L) {
            return;
        }
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();
        final long usedMemory = totalMemory - freeMemory;
        GuiMemoryUpgrade.maxMemoryUsed = Math.max(usedMemory, (GuiMemoryUpgrade.lastMemoryMax == maxMemory) ? GuiMemoryUpgrade.maxMemoryUsed : usedMemory);
        GuiMemoryUpgrade.lastMemoryMax = maxMemory;
        if (!GuiMemoryUpgrade.outOfMemoryDetected && GuiMemoryUpgrade.lastFrameMemory != -1L) {
            final long frameDuration = System.currentTimeMillis() - GuiMemoryUpgrade.lastFrameTimestamp;
            if (GuiMemoryUpgrade.maxMemoryUsed >= 99L) {
                GuiMemoryUpgrade.outOfMemoryDetected = true;
            }
            else if (GuiMemoryUpgrade.lastFrameMemory > usedMemory) {
                if (frameDuration > 100L) {
                    ++GuiMemoryUpgrade.frameHits;
                    if (GuiMemoryUpgrade.frameHits >= 3) {
                        GuiMemoryUpgrade.outOfMemoryDetected = true;
                    }
                }
                else if (GuiMemoryUpgrade.frameHits > 0) {
                    --GuiMemoryUpgrade.frameHits;
                }
            }
            else if (frameDuration > 100L && GuiMemoryUpgrade.frameHits > 0) {
                --GuiMemoryUpgrade.frameHits;
            }
        }
        final long percent = GuiMemoryUpgrade.maxMemoryUsed * 100L / maxMemory;
        if (percent >= 90L) {
            GuiMemoryUpgrade.lastFrameMemory = usedMemory;
            GuiMemoryUpgrade.lastFrameTimestamp = System.currentTimeMillis();
        }
        else {
            GuiMemoryUpgrade.lastFrameMemory = -1L;
        }
        if (GuiMemoryUpgrade.outOfMemoryDetected) {
            renderWarning(percent);
            GuiMemoryUpgrade.frameHits = 0;
        }
    }
    
    private static void renderWarning(final long percent) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bus.G();
        bus.c(0.0f, 0.0f, 10.0f);
        final String string = LanguageManager.translate("out_of_memory_warning_explanation");
        final int margin = 3;
        final int warningWidth = 200;
        final List<String> lines = draw.listFormattedStringToWidth(string, warningWidth - margin * 3);
        final int warningHeight = 10 + lines.size() * 10 + margin + 1;
        final double warningX = draw.getWidth() - warningWidth - 1;
        final int warningY = 2;
        draw.drawRect(warningX, warningY, warningX + warningWidth, warningY + warningHeight, Integer.MIN_VALUE);
        draw.drawRectBorder(warningX, warningY, warningX + warningWidth, warningY + warningHeight, Integer.MIN_VALUE, 1.0);
        draw.drawString(ModColor.cl('c') + ModColor.cl('n') + LanguageManager.translate("out_of_memory_warning_title"), warningX + margin, warningY + margin);
        draw.drawRightString(ModColor.cl('4') + percent + "%", warningX + warningWidth - margin, warningY + margin);
        int lineY = 11;
        for (final String line : lines) {
            draw.drawString(ModColor.cl('f') + line, warningX + margin, warningY + margin + lineY);
            lineY += 10;
        }
        if (Keyboard.isKeyDown(50)) {
            GuiMemoryUpgrade.outOfMemoryDetected = false;
            bib.z().a((blk)new GuiMemoryUpgrade());
        }
        if (Keyboard.isKeyDown(37)) {
            GuiMemoryUpgrade.outOfMemoryDetected = false;
            LabyMod.getSettings().outOfMemoryWarning = false;
        }
        bus.c(0.0f, 0.0f, -10.0f);
        bus.H();
    }
    
    static {
        GuiMemoryUpgrade.maxMemoryUsed = 0L;
        GuiMemoryUpgrade.lastMemoryMax = 0L;
        GuiMemoryUpgrade.outOfMemoryDetected = false;
        GuiMemoryUpgrade.lastFrameMemory = -1L;
        GuiMemoryUpgrade.lastFrameTimestamp = -1L;
        GuiMemoryUpgrade.frameHits = 0;
    }
}
