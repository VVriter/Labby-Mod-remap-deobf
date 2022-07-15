//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update.migration;

import net.labymod.main.lang.*;
import java.util.concurrent.*;
import net.labymod.addon.online.info.*;
import java.util.function.*;
import net.labymod.main.update.migration.gui.*;
import java.util.*;
import net.labymod.support.util.*;
import net.labymod.addon.online.*;
import com.google.common.io.*;
import net.labymod.utils.request.*;
import net.labymod.addon.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import org.apache.commons.codec.binary.*;
import java.awt.*;
import com.google.gson.*;
import java.io.*;

public class MigrationUpdater
{
    private static final String SODIUM_NAME = "Sodium";
    private static final JsonParser jsonParser;
    public static final String LIBRARY_NAME = "LabyMod";
    public static final String LIBRARY_MAJOR_VERSION = "3";
    public static final String MOD_NAME = "LabyMod-3";
    private final File minecraftDirectory;
    private final AddonInfoManager addonManager;
    private List<OnlineAddonInfo> migratedAddons;
    private List<OnlineAddonInfo> missingAddons;
    private boolean usingOptiFine;
    
    public MigrationUpdater() {
        this.minecraftDirectory = QuickInstaller.getWorkingDirectory();
        this.addonManager = AddonInfoManager.getInstance();
        this.migratedAddons = new ArrayList<OnlineAddonInfo>();
        this.missingAddons = new ArrayList<OnlineAddonInfo>();
        this.usingOptiFine = false;
    }
    
    public boolean hasLatestVersion() {
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        if (latestVersion == null) {
            return true;
        }
        final File librariesJar = new File(this.minecraftDirectory, "libraries/net/labymod/LabyMod/3_" + latestVersion.minecraftVersion + "/LabyMod-3_" + latestVersion.minecraftVersion + ".jar");
        return librariesJar.exists();
    }
    
    public void initAndInstallAsnyc(final InstallationProgressCallback callback) {
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        if (latestVersion == null) {
            callback.failed();
            return;
        }
        this.missingAddons.clear();
        this.migratedAddons.clear();
        callback.progress(10.0, LanguageManager.translate("update_minecraft_progress_migrate_addons"));
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AddonInfoManager.getInstance().init();
                    MigrationUpdater.this.initAndInstall(callback);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    callback.failed();
                }
            }
        });
    }
    
    private void initAndInstall(final InstallationProgressCallback callback) {
        for (final OnlineAddonInfo addonInfo : this.addonManager.getAllMinecraftVersionsAddonInfoList()) {
            if (AddonLoader.hasInstalled((AddonInfo)addonInfo)) {
                final OnlineAddonInfo latestAddonInfo = this.getNewerAddonVersion(addonInfo);
                if (latestAddonInfo == null) {
                    this.missingAddons.add(addonInfo);
                }
                else {
                    this.migratedAddons.add(latestAddonInfo);
                }
                if (!addonInfo.isIncludeInJar()) {
                    continue;
                }
                this.usingOptiFine = true;
            }
        }
        final OnlineAddonInfo sodium = this.getSodium();
        if (sodium == null) {
            this.install(callback, false);
        }
        else {
            bib.z().a((Runnable)new Runnable() {
                @Override
                public void run() {
                    final blk currentScreen = bib.z().m;
                    bib.z().a((blk)new GuiTrySodium(currentScreen, (Consumer)new Consumer<Boolean>() {
                        @Override
                        public void accept(final Boolean useSodium) {
                            if (useSodium) {
                                MigrationUpdater.this.migratedAddons.add(sodium);
                            }
                            MigrationUpdater.this.installAsync(callback, useSodium);
                        }
                    }, MigrationUpdater.this.usingOptiFine));
                }
            });
        }
    }
    
    private void installAsync(final InstallationProgressCallback callback, final Boolean useSodium) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MigrationUpdater.this.install(callback, useSodium);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    callback.failed();
                }
            }
        });
    }
    
    private void install(final InstallationProgressCallback callback, final boolean useSodium) {
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        final File directoryAddonsCurrent = AddonLoader.getAddonsDirectory();
        final File directoryAddonsLatest = new File("LabyMod/", "addons-" + latestVersion.minecraftVersionRaw[0] + "." + latestVersion.minecraftVersionRaw[1]);
        directoryAddonsLatest.mkdirs();
        new File("LabyMod/", "ofhandler").mkdir();
        final double maxPercentOffset = 70.0;
        final double maxPercent = this.migratedAddons.size() * 100;
        int i = 0;
        for (final OnlineAddonInfo migratedAddon : this.migratedAddons) {
            if (useSodium && migratedAddon.isIncludeInJar()) {
                continue;
            }
            final double totalPercent = i * 100;
            try {
                File file;
                if (migratedAddon.isIncludeInJar()) {
                    file = new File("LabyMod/", "ofhandler/optifine.jar");
                }
                else {
                    final File file2;
                    final StringBuilder sb;
                    file = new File(file2, sb.append(migratedAddon.getName()).append(".jar").toString());
                    file2 = directoryAddonsLatest;
                    sb = new StringBuilder();
                }
                final File destination = file;
                final double percentOffset = maxPercentOffset / maxPercent * (totalPercent + 0.0);
                callback.progress(10.0 + percentOffset, LanguageManager.translate("update_minecraft_progress_download_addon", new Object[] { migratedAddon.getName(), "0%" }));
                new AddonDownloader(migratedAddon, destination, (CallbackAddonDownloadProcess)new CallbackAddonDownloadProcess() {
                    public void success(final File file) {
                        final double percentOffset = maxPercentOffset / maxPercent * (totalPercent + 100.0);
                        Debug.log(Debug.EnumDebugMode.UPDATER, "Downloaded " + file.getAbsolutePath());
                        callback.progress(10.0 + percentOffset, LanguageManager.translate("update_minecraft_progress_download_addon", new Object[] { migratedAddon.getName(), "100%" }));
                    }
                    
                    public void progress(final double percent) {
                        final double percentOffset = maxPercentOffset / maxPercent * (totalPercent + percent);
                        callback.progress(10.0 + percentOffset, LanguageManager.translate("update_minecraft_progress_download_addon", new Object[] { migratedAddon.getName(), String.valueOf((int)percent) }) + "%");
                    }
                    
                    public void failed(final String message) {
                        Debug.log(Debug.EnumDebugMode.UPDATER, "Error: " + message);
                    }
                }).downloadSync();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            ++i;
        }
        if (directoryAddonsCurrent.exists() && directoryAddonsLatest.exists()) {
            final File currentConfig = new File(directoryAddonsCurrent, "config");
            final File latestConfig = new File(directoryAddonsLatest, "config");
            currentConfig.mkdir();
            latestConfig.mkdir();
            final File[] files = currentConfig.listFiles();
            if (files != null) {
                for (final File json : files) {
                    if (json.getName().endsWith(".json")) {
                        try {
                            Files.copy(json, new File(latestConfig, json.getName()));
                        }
                        catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
        final File minecraftDirectory = QuickInstaller.getWorkingDirectory();
        final File latestVersionDirectory = new File(minecraftDirectory, "versions/LabyMod-3-" + latestVersion.minecraftVersion);
        callback.progress(80.0, LanguageManager.translate("update_minecraft_progress_download_mc_json"));
        try {
            final File jsonFile = new File(latestVersionDirectory, "LabyMod-3-" + latestVersion.minecraftVersion + ".json");
            jsonFile.getParentFile().mkdirs();
            final FileOutputStream fos = new FileOutputStream(jsonFile);
            DownloadServerRequest.writeBytes(String.format("https://dl.labymod.net/latest/install/json/%s.json", latestVersion.minecraftVersion), fos);
            fos.close();
        }
        catch (Exception e3) {
            e3.printStackTrace();
            callback.failed();
            return;
        }
        final File jarFile = new File(latestVersionDirectory, "LabyMod-3-" + latestVersion.minecraftVersion + ".jar");
        callback.progress(85.0, LanguageManager.translate("update_minecraft_progress_download_mc_jar"));
        try {
            jarFile.getParentFile().mkdirs();
            final FileOutputStream fos = new FileOutputStream(jarFile);
            DownloadServerRequest.writeBytes(String.format(latestVersion.mcUrl, latestVersion.minecraftVersion), fos);
            fos.close();
        }
        catch (Exception e4) {
            e4.printStackTrace();
            callback.failed();
            return;
        }
        final File librariesJar = new File(minecraftDirectory, "libraries/net/labymod/LabyMod/3_" + latestVersion.minecraftVersion + "/LabyMod-3_" + latestVersion.minecraftVersion + ".jar");
        callback.progress(90.0, LanguageManager.translate("update_minecraft_progress_download_labymod"));
        try {
            librariesJar.getParentFile().mkdirs();
            final FileOutputStream fos2 = new FileOutputStream(librariesJar);
            DownloadServerRequest.writeBytes(String.format(latestVersion.url, latestVersion.minecraftVersion), fos2);
            fos2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            callback.failed();
            return;
        }
        if (!librariesJar.exists()) {
            Debug.log(Debug.EnumDebugMode.UPDATER, librariesJar.getAbsolutePath() + " is still not there. AntiVirus?");
            callback.failed();
            return;
        }
        if (this.usingOptiFine && !useSodium) {
            callback.progress(95.0, LanguageManager.translate("update_minecraft_progress_install_optifine"));
            try {
                LabyModOFAddon.downloadOFHandler(false);
                LabyModOFAddon.executeOfHandlerDirect(true, latestVersion.minecraftVersion, jarFile);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        callback.progress(98.0, LanguageManager.translate("update_minecraft_progress_update_launcher"));
        boolean success = this.updateLauncherProfiles(new File(minecraftDirectory, "launcher_profiles.json"), useSodium);
        if (this.updateLauncherProfiles(new File(minecraftDirectory, "launcher_profiles_microsoft_store.json"), useSodium)) {
            success = true;
        }
        try {
            this.updateLauncherUIState(new File(minecraftDirectory, "launcher_ui_state.json"));
        }
        catch (Exception e5) {
            e5.printStackTrace();
        }
        try {
            this.updateLauncherUIState(new File(minecraftDirectory, "launcher_ui_state_microsoft_store.json"));
        }
        catch (Exception e5) {
            e5.printStackTrace();
        }
        if (success) {
            callback.completed();
        }
        else {
            callback.failed();
        }
    }
    
    private OnlineAddonInfo getNewerAddonVersion(final OnlineAddonInfo info) {
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        final String major = latestVersion.minecraftVersionRaw[0] + "" + latestVersion.minecraftVersionRaw[1];
        for (final OnlineAddonInfo addonInfo : this.addonManager.getAllMinecraftVersionsAddonInfoList()) {
            if (addonInfo.getMcVersion().equals(major) && ((addonInfo.isIncludeInJar() && info.isIncludeInJar()) || (addonInfo.getName().equalsIgnoreCase(info.getName()) && addonInfo.getAuthor().equalsIgnoreCase(info.getAuthor())))) {
                return addonInfo;
            }
        }
        return null;
    }
    
    private OnlineAddonInfo getSodium() {
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        final String major = latestVersion.minecraftVersionRaw[0] + "" + latestVersion.minecraftVersionRaw[1];
        for (final OnlineAddonInfo addonInfo : this.addonManager.getAllMinecraftVersionsAddonInfoList()) {
            if (addonInfo.getMcVersion().equals(major) && addonInfo.getName().equalsIgnoreCase("Sodium")) {
                return addonInfo;
            }
        }
        return null;
    }
    
    private boolean updateLauncherProfiles(final File launcherProfilesFile, final boolean useSodium) {
        if (!launcherProfilesFile.exists()) {
            return false;
        }
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        final String profileName = "LabyMod-3-" + latestVersion.minecraftVersion;
        final String profileNameCurrent = "LabyMod-3-" + Source.ABOUT_MC_VERSION;
        String json = null;
        try {
            json = readFile(launcherProfilesFile);
        }
        catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        final JsonObject root = (JsonObject)JsonParse.parse(json);
        if (!root.has("profiles")) {
            return false;
        }
        int profileFormatVersion = 0;
        try {
            profileFormatVersion = root.get("launcherVersion").getAsJsonObject().get("profilesFormat").getAsInt();
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        final JsonObject profiles = root.get("profiles").getAsJsonObject();
        String javaArgs = useSodium ? null : "-Xmx5G";
        try {
            if (profiles.has(profileNameCurrent)) {
                final JsonObject obj = profiles.get(profileNameCurrent).getAsJsonObject();
                if (obj.has("javaArgs")) {
                    javaArgs = (useSodium ? obj.get("javaArgs").getAsString() : obj.get("javaArgs").getAsString().replaceAll("-Xmx\\d\\w+", javaArgs));
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        final JsonObject profileObject = new JsonObject();
        profileObject.addProperty("name", profileName);
        profileObject.addProperty("lastVersionId", profileName);
        if (javaArgs != null) {
            profileObject.addProperty("javaArgs", javaArgs);
        }
        if (profileFormatVersion >= 2) {
            try {
                final ImageIcon imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MigrationUpdater.class.getResource("/assets/minecraft/labymod/textures/labymod_logo.png")));
                final BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), 2);
                final Graphics graphics = bufferedImage.createGraphics();
                imageIcon.paintIcon(null, graphics, 0, 0);
                graphics.dispose();
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                final String encodedBase64Image = new String(Base64.encodeBase64(byteArrayOutputStream.toByteArray()));
                profileObject.addProperty("icon", "data:image/png;base64," + encodedBase64Image);
            }
            catch (Exception error2) {
                error2.printStackTrace();
            }
        }
        if (!profiles.has(profileName)) {
            profiles.add(profileName, (JsonElement)profileObject);
        }
        root.addProperty("selectedProfile", profileName);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            final FileWriter fwJson = new FileWriter(launcherProfilesFile);
            fwJson.write(gson.toJson((JsonElement)root));
            fwJson.flush();
            fwJson.close();
            return true;
        }
        catch (IOException e3) {
            e3.printStackTrace();
            return false;
        }
    }
    
    private void updateLauncherUIState(final File launcherUIStateFile) {
        if (!launcherUIStateFile.exists()) {
            Debug.log(Debug.EnumDebugMode.UPDATER, "launcher_ui_state.json doesn't exist. Skipping it...");
            return;
        }
        final LatestMinecraftVersion latestVersion = LabyMod.getInstance().getUpdater().getLabyModUpdateChecker().getLatestMinecraftVersion();
        final String profileName = "LabyMod-3-" + latestVersion.minecraftVersion;
        String json = null;
        try {
            json = readFile(launcherUIStateFile);
        }
        catch (Throwable e) {
            Debug.log(Debug.EnumDebugMode.UPDATER, "Couldn't read launcher_ui_state.json: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        final JsonObject root = (JsonObject)MigrationUpdater.jsonParser.parse(json);
        if (!root.has("data")) {
            Debug.log(Debug.EnumDebugMode.UPDATER, "Didn't find data-attribute in launcher_ui_state.json");
            return;
        }
        int uiStateFormatVersion = 0;
        try {
            uiStateFormatVersion = root.get("formatVersion").getAsInt();
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        if (uiStateFormatVersion != 1) {
            Debug.log(Debug.EnumDebugMode.UPDATER, "It's a newer version: " + uiStateFormatVersion);
            return;
        }
        final JsonObject dataObject = root.get("data").getAsJsonObject();
        final String uiEventsJson = dataObject.get("UiEvents").getAsString();
        final JsonObject uiEventsObject = (JsonObject)MigrationUpdater.jsonParser.parse(uiEventsJson);
        JsonObject target = null;
        if (uiEventsObject.has("hidePlayerSafetyDisclaimer")) {
            target = uiEventsObject.get("hidePlayerSafetyDisclaimer").getAsJsonObject();
        }
        else {
            uiEventsObject.add("hidePlayerSafetyDisclaimer", (JsonElement)(target = new JsonObject()));
        }
        if (target != null) {
            target.addProperty(profileName + "_" + profileName, Boolean.valueOf(true));
        }
        dataObject.addProperty("UiEvents", uiEventsObject.toString());
        final String uiSettingsJson = dataObject.get("UiSettings").getAsString();
        final JsonObject uiSettingsObject = (JsonObject)MigrationUpdater.jsonParser.parse(uiSettingsJson);
        if (uiSettingsObject.has("javaConfigurationFilter")) {
            final JsonObject javaConfigurationFilter = uiSettingsObject.get("javaConfigurationFilter").getAsJsonObject();
            javaConfigurationFilter.addProperty("modded", Boolean.valueOf(true));
        }
        dataObject.addProperty("UiSettings", uiSettingsObject.toString());
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            final FileWriter fwJson = new FileWriter(launcherUIStateFile);
            fwJson.write(gson.toJson((JsonElement)root));
            fwJson.flush();
            fwJson.close();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public static String readFile(final File file) throws IOException {
        final FileInputStream fin = new FileInputStream(file);
        final InputStreamReader inr = new InputStreamReader(fin, "UTF-8");
        final BufferedReader br = new BufferedReader(inr);
        final StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        inr.close();
        fin.close();
        return sb.toString();
    }
    
    static {
        jsonParser = new JsonParser();
    }
}
