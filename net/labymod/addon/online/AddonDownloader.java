//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon.online;

import net.labymod.addon.online.info.*;
import net.labymod.addon.*;
import java.util.concurrent.*;
import java.io.*;
import net.labymod.utils.request.*;

public class AddonDownloader
{
    private AddonInfo addonInfo;
    private CallbackAddonDownloadProcess callback;
    private File file;
    
    public AddonDownloader(final OnlineAddonInfo addonInfo, final File file, final CallbackAddonDownloadProcess callback) {
        this.addonInfo = addonInfo;
        this.callback = callback;
        this.file = file;
    }
    
    public AddonDownloader(final OnlineAddonInfo addonInfo, final CallbackAddonDownloadProcess callback) {
        this.addonInfo = addonInfo;
        this.callback = callback;
        if (addonInfo.isIncludeInJar()) {
            this.file = new File("LabyMod/ofhandler/", "optifine.jar");
            this.file.getParentFile().mkdir();
        }
        else {
            this.file = new File(AddonLoader.getAddonsDirectory(), addonInfo.getName() + ".jar");
        }
    }
    
    public void downloadAsync() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AddonDownloader.this.downloadSync();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    AddonDownloader.this.callback.failed(e.getMessage());
                }
            }
        });
    }
    
    public void downloadSync() throws Exception {
        final InputStreamOutput inputStream = DownloadServerRequest.getInputStream(this.addonInfo.getDownloadURL());
        final DataInputStream dataInputStream = new DataInputStream(inputStream.getInputStream());
        final FileOutputStream fileOutputStream = new FileOutputStream(this.file);
        final int total = inputStream.getContentLength();
        int current = 0;
        boolean smooth = total < 1000000;
        final long start = System.currentTimeMillis();
        final byte[] buff = new byte[2048];
        int readBytes = 0;
        final int iterations = total / buff.length;
        final long animationDuration = 2000L;
        final long sleep = Math.min(30L, animationDuration / iterations);
        while ((readBytes = dataInputStream.read(buff, 0, buff.length)) != -1) {
            fileOutputStream.write(buff, 0, readBytes);
            current += readBytes;
            if (total != -1 && current != total) {
                this.callback.progress(current / (double)total * 100.0);
            }
            if (System.currentTimeMillis() - start > animationDuration) {
                smooth = false;
            }
            if (smooth) {
                Thread.sleep(sleep);
            }
        }
        dataInputStream.close();
        fileOutputStream.close();
        this.callback.progress(100.0);
        this.callback.success(this.file);
    }
}
