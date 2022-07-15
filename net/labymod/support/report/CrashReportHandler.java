//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.report;

import java.io.*;
import net.labymod.main.*;
import net.labymod.main.update.*;
import net.labymod.main.lang.*;
import net.labymod.addon.*;
import java.util.*;
import net.labymod.api.*;

public class CrashReportHandler
{
    private static final CrashReportHandler INSTANCE;
    
    public void report(final File mcReportFile, final b mcCrashReport) {
        final ReportData reportData = new ReportData(mcCrashReport.b());
        Updater updater = (LabyMod.getInstance() == null) ? null : LabyMod.getInstance().getUpdater();
        if (updater == null) {
            updater = new Updater();
        }
        final ReportArguments reportArguments = new ReportArguments();
        reportArguments.setMinecraftCrashReportFile(mcReportFile.getAbsolutePath());
        reportArguments.setCrashReportJson(reportData.createJsonReport());
        final String key = (mcCrashReport.b() instanceof OutOfMemoryError) ? "memory" : (this.isCrashCausedByMinecraft(mcCrashReport) ? "minecraft" : "labymod");
        reportArguments.setTitle(LanguageManager.translate("crash_reporter_title_" + key));
        final UUID uuid = this.getTargetAddonUUID(mcCrashReport);
        if (uuid != null) {
            reportArguments.setAddonUUID(uuid);
            final LabyModAddon addon = AddonLoader.getAddonByUUID(uuid);
            final File jarFile = AddonLoader.getFiles().get(uuid);
            if (jarFile != null) {
                reportArguments.setAddonPath(jarFile.getAbsolutePath());
                reportArguments.setAddonName(jarFile.getName());
            }
            if (addon != null && addon.about != null && addon.about.name != null && !addon.about.name.isEmpty()) {
                reportArguments.setAddonName(addon.about.name);
            }
            if (reportArguments.getAddonName() != null) {
                reportArguments.setTitle(LanguageManager.translate("crash_reporter_title_addon", new Object[] { reportArguments.getAddonName() }));
            }
        }
        updater.executeReport(reportArguments);
    }
    
    private boolean isCrashCausedByMinecraft(final b mcCrashReport) {
        try {
            for (final StackTraceElement frame : mcCrashReport.b().getStackTrace()) {
                if (frame != null && frame.getClassName() != null && frame.getClassName().toLowerCase().contains("labymod")) {
                    return false;
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        return true;
    }
    
    private UUID getTargetAddonUUID(final b mcCrashReport) {
        try {
            for (final StackTraceElement frame : mcCrashReport.b().getStackTrace()) {
                try {
                    final Class<?> type = Class.forName(frame.getClassName());
                    final UUID uuid = AddonLoader.getUUIDByClass((Class)type);
                    if (uuid != null) {
                        return uuid;
                    }
                }
                catch (ClassNotFoundException ex) {}
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }
    
    public static CrashReportHandler getInstance() {
        return CrashReportHandler.INSTANCE;
    }
    
    static {
        INSTANCE = new CrashReportHandler();
    }
}
