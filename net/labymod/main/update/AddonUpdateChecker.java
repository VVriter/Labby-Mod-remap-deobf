//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update;

import java.util.concurrent.*;
import net.labymod.addon.*;
import net.labymod.support.util.*;
import net.labymod.addon.online.*;
import net.labymod.addon.online.info.*;
import java.util.zip.*;
import java.nio.charset.*;
import java.util.jar.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;

public class AddonUpdateChecker
{
    private static final JsonParser PARSER;
    
    public CompletableFuture<UpdateData[]> getAddonUpdateData() {
        final HashMap<String, File> addons;
        ArrayList<UpdateData> result;
        final Iterator<Map.Entry<String, File>> iterator;
        Map.Entry<String, File> entry;
        OnlineAddonInfo info;
        String requiredHash;
        String hash;
        final UpdateData updateData;
        final Object o;
        return CompletableFuture.supplyAsync(() -> {
            addons = new HashMap<String, File>();
            try {
                this.findOnlineAddons(AddonLoader.getAddonsDirectory(), addons);
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
            if (addons.isEmpty()) {
                return new UpdateData[0];
            }
            else {
                result = new ArrayList<UpdateData>();
                addons.entrySet().iterator();
                while (iterator.hasNext()) {
                    entry = iterator.next();
                    info = this.getAddonInfo(entry.getKey());
                    if (info == null) {
                        continue;
                    }
                    else {
                        requiredHash = info.getHash();
                        if (requiredHash != null) {
                            if (requiredHash.isEmpty()) {
                                continue;
                            }
                            else {
                                hash = null;
                                try {
                                    hash = AddonLoader.getCheckSum((File)entry.getValue());
                                }
                                catch (Exception exception2) {
                                    exception2.printStackTrace();
                                }
                                if (hash != null && !requiredHash.equalsIgnoreCase(hash)) {
                                    new UpdateData(String.valueOf(info.getVersion()), new short[] { (short)info.getVersion() }, true);
                                    ((Collection<UpdateData>)o).add(updateData);
                                    Debug.log(Debug.EnumDebugMode.ADDON, info.getName() + ": " + hash + " != " + requiredHash);
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
                return (UpdateData[])result.toArray(new UpdateData[0]);
            }
        });
    }
    
    private OnlineAddonInfo getAddonInfo(final String uuid) {
        AddonInfoManager.getInstance().init();
        final AddonInfo info = AddonInfoManager.getInstance().getAddonInfoMap().get(UUID.fromString(uuid));
        if (!(info instanceof OnlineAddonInfo)) {
            return null;
        }
        return (OnlineAddonInfo)info;
    }
    
    private void findOnlineAddons(final File directory, final Map<String, File> target) throws IOException {
        final File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (final File file : files) {
            Label_0648: {
                if (!file.isDirectory()) {
                    if (!file.getName().equals("debug.jar")) {
                        Debug.log(Debug.EnumDebugMode.ADDON, "Check " + file.getName());
                        try (final JarFile jarFile = new JarFile(file)) {
                            final JarEntry entry = jarFile.getJarEntry("addon.json");
                            if (entry != null) {
                                try (final Reader reader = new InputStreamReader(jarFile.getInputStream(entry), StandardCharsets.UTF_8)) {
                                    final JsonElement element = AddonUpdateChecker.PARSER.parse(reader);
                                    if (!element.isJsonObject()) {
                                        break Label_0648;
                                    }
                                    final JsonElement uuidElement = element.getAsJsonObject().get("uuid");
                                    if (uuidElement.isJsonPrimitive()) {
                                        final String uuid = uuidElement.getAsString();
                                        if (uuid.equals("%uuid%")) {
                                            break Label_0648;
                                        }
                                        if (element.getAsJsonObject().has("debug")) {
                                            break Label_0648;
                                        }
                                        target.put(uuid, file);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    static {
        PARSER = new JsonParser();
    }
}
