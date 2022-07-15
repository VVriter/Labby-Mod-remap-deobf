//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update;

import net.labymod.main.*;
import net.labymod.support.util.*;
import net.labymod.support.report.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.addon.*;
import java.io.*;
import java.util.concurrent.*;
import net.labymod.utils.request.*;
import com.google.gson.*;

public class Updater
{
    private static final Gson GSON;
    private final LabyModUpdateChecker labyModUpdateChecker;
    private final AddonUpdateChecker addonUpdateChecker;
    private UpdateData coreUpdate;
    private UpdateData[] addonUpdates;
    private boolean updateAvailable;
    private int addonUpdateCount;
    private boolean forceUpdate;
    private boolean backupMethod;
    
    public Updater() {
        this.labyModUpdateChecker = new LabyModUpdateChecker();
        this.addonUpdateChecker = new AddonUpdateChecker();
        this.coreUpdate = new UpdateData("unknown", (short[])null, false);
        this.addonUpdates = new UpdateData[0];
        this.updateAvailable = false;
        this.addonUpdateCount = 0;
        this.forceUpdate = false;
        this.backupMethod = false;
    }
    
    public void addShutdownHook() {
        LabyMod.getInstance().getEventManager().registerShutdownHook(() -> {
            if (this.updateAvailable || this.forceUpdate) {
                this.executeUpdater();
            }
            else {
                this.labyModUpdateChecker.getUpdateData().thenAccept(data -> {
                    if (data.isUpdateAvailable()) {
                        this.executeUpdater();
                    }
                });
            }
        });
    }
    
    public void checkUpdate() {
        try {
            int length;
            int i = 0;
            UpdateData update;
            this.ensureUpdaterAvailable(accepted -> {
                if (!accepted) {
                    Debug.log(Debug.EnumDebugMode.UPDATER, "Failed to download updater, skipping update checks");
                }
                else {
                    this.labyModUpdateChecker.getUpdateData().thenAccept(data -> {
                        this.printInfo(data);
                        this.coreUpdate = data;
                        this.updateAvailable = data.isUpdateAvailable();
                        this.addonUpdateChecker.getAddonUpdateData().thenAccept(addonUpdates -> {
                            this.addonUpdates = addonUpdates;
                            this.addonUpdateCount = 0;
                            for (length = addonUpdates.length; i < length; ++i) {
                                update = addonUpdates[i];
                                if (update.isUpdateAvailable()) {
                                    this.updateAvailable = true;
                                    ++this.addonUpdateCount;
                                }
                            }
                        });
                    });
                }
            });
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public void executeReport(final ReportArguments reportArguments) {
        final String json = Updater.GSON.toJson((Object)reportArguments);
        this.execute(true, json);
    }
    
    public void executeUpdater() {
        Debug.log(Debug.EnumDebugMode.UPDATER, "Executing LabyMod Updater");
        this.execute(false, null);
        Debug.log(Debug.EnumDebugMode.UPDATER, "Updating complete");
    }
    
    public void execute(final boolean report, final String json) {
        try {
            final String javaHome = System.getProperty("java.home");
            final String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            final List<String> arguments = new ArrayList<String>();
            arguments.add(this.backupMethod ? "java" : javaBin);
            arguments.add("-jar");
            arguments.add(new File("LabyMod/Updater.jar").getAbsolutePath());
            if (report) {
                arguments.add("report");
                arguments.add(Base64.getEncoder().encodeToString(json.getBytes()));
            }
            final StringBuilder debug = new StringBuilder();
            for (final String arg : arguments) {
                debug.append(arg).append(" ");
            }
            Debug.log(Debug.EnumDebugMode.UPDATER, debug.toString());
            final ProcessBuilder pb = new ProcessBuilder(arguments);
            pb.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void printInfo(final UpdateData data) {
        Debug.log(Debug.EnumDebugMode.UPDATER, "The latest LabyMod version is v" + data.getLatestVersion() + ", you are currently using LabyMod version v" + "3.9.41");
        if (data.isUpdateAvailable()) {
            Debug.log(Debug.EnumDebugMode.UPDATER, "You are outdated!");
        }
        else {
            Debug.log(Debug.EnumDebugMode.UPDATER, "You are using the latest version.");
        }
    }
    
    public void ensureUpdaterAvailable(final Consumer<Boolean> consumer) throws Exception {
        final File file = new File("LabyMod/Updater.jar");
        if (!file.exists()) {
            this.downloadUpdaterFile(consumer);
            return;
        }
        final File file2;
        this.loadUpdaterHash().thenAccept(hash -> {
            if (hash == null) {
                consumer.accept(false);
            }
            else {
                try {
                    if (AddonLoader.getCheckSum(file2).equals(hash)) {
                        consumer.accept(true);
                    }
                    else {
                        this.downloadUpdaterFile(consumer);
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    consumer.accept(false);
                }
            }
        });
    }
    
    private void downloadUpdaterFile(final Consumer<Boolean> consumer) throws Exception {
        final File file = new File("LabyMod/Updater.jar");
        final FileOutputStream fos = new FileOutputStream(file);
        DownloadServerRequest.writeBytesAsync("https://dl.labymod.net/latest/install/updater/updater.jar", fos, new ServerStatus() {
            @Override
            public void success() {
                Debug.log(Debug.EnumDebugMode.UPDATER, "Downloaded latest Updater https://dl.labymod.net/latest/install/updater/updater.jar");
                consumer.accept(file.exists());
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                consumer.accept(file.exists());
            }
            
            @Override
            public void close() throws Exception {
                fos.close();
            }
        });
    }
    
    private CompletableFuture<String> loadUpdaterHash() {
        final CompletableFuture<String> future = new CompletableFuture<String>();
        DownloadServerRequest.getJsonObjectAsync("https://dl.labymod.net/latest/install/updater/hash.json", new ServerResponse<JsonElement>() {
            @Override
            public void success(final JsonElement result) {
                final String hash = result.getAsJsonObject().get("updater").getAsString();
                future.complete(hash);
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                future.complete(null);
            }
        });
        return future;
    }
    
    public boolean isUpdateAvailable() {
        return this.updateAvailable;
    }
    
    public UpdateData getCoreUpdate() {
        return this.coreUpdate;
    }
    
    public UpdateData[] getAddonUpdates() {
        return this.addonUpdates;
    }
    
    public int getAddonUpdateCount() {
        return this.addonUpdateCount;
    }
    
    public LabyModUpdateChecker getLabyModUpdateChecker() {
        return this.labyModUpdateChecker;
    }
    
    public AddonUpdateChecker getAddonUpdateChecker() {
        return this.addonUpdateChecker;
    }
    
    public boolean isForceUpdate() {
        return this.forceUpdate;
    }
    
    public boolean isBackupMethod() {
        return this.backupMethod;
    }
    
    public void setForceUpdate(final boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
    
    public void setBackupMethod(final boolean backupMethod) {
        this.backupMethod = backupMethod;
    }
    
    static {
        GSON = new Gson();
    }
}
