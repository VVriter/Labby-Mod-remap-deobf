//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.report;

import java.util.concurrent.*;
import net.labymod.main.*;
import com.google.gson.reflect.*;
import java.lang.management.*;
import net.labymod.addon.*;
import net.labymod.api.*;
import net.labymod.core.asm.*;
import org.apache.commons.lang3.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public class ReportData
{
    private final Throwable cause;
    private Map<String, Callable<String>> entries;
    
    public ReportData(final Throwable causeThrowable) {
        this.entries = new HashMap<String, Callable<String>>();
        this.cause = causeThrowable;
        this.putAllEntries();
    }
    
    private void putAllEntries() {
        final Gson gson = new GsonBuilder().create();
        this.entries.put("mc_uuid", new Callable<String>() {
            @Override
            public String call() {
                return LabyMod.getInstance().getPlayerUUID().toString();
            }
        });
        this.entries.put("mc_username", new Callable<String>() {
            @Override
            public String call() {
                return LabyMod.getInstance().getPlayerName();
            }
        });
        this.entries.put("mc_version", new Callable<String>() {
            @Override
            public String call() {
                return Source.ABOUT_MC_VERSION;
            }
        });
        this.entries.put("labymod_version", new Callable<String>() {
            @Override
            public String call() {
                return "3.9.41";
            }
        });
        this.entries.put("os", new Callable<String>() {
            @Override
            public String call() {
                return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
            }
        });
        this.entries.put("java_version", new Callable<String>() {
            @Override
            public String call() {
                return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
            }
        });
        this.entries.put("java_vm_version", new Callable<String>() {
            @Override
            public String call() {
                return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
            }
        });
        this.entries.put("mc_memory", new Callable<String>() {
            @Override
            public String call() {
                final Runtime runtime = Runtime.getRuntime();
                final long i = runtime.maxMemory();
                final long j = runtime.totalMemory();
                final long k = runtime.freeMemory();
                final long l = i / 1024L / 1024L;
                final long i2 = j / 1024L / 1024L;
                final long j2 = k / 1024L / 1024L;
                return k + " bytes (" + j2 + " MB) / " + j + " bytes (" + i2 + " MB) up to " + i + " bytes (" + l + " MB)";
            }
        });
        this.entries.put("jvm_flags", new Callable<String>() {
            @Override
            public String call() {
                final RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
                final List<String> list = runtimemxbean.getInputArguments();
                return gson.toJson((Object)list, new TypeToken<List<String>>() {}.getType());
            }
        });
        this.entries.put("addons", new Callable<String>() {
            @Override
            public String call() {
                final List<String> list = new ArrayList<String>();
                for (final LabyModAddon addon : AddonLoader.getAddons()) {
                    if (addon != null && addon.about != null && addon.about.uuid != null && addon.about.name != null) {
                        list.add(addon.about.name + " (" + addon.about.uuid.toString() + ")");
                    }
                }
                return gson.toJson((Object)list, new TypeToken<List<String>>() {}.getType());
            }
        });
        this.entries.put("vanilla_forge", new Callable<String>() {
            @Override
            public String call() {
                return String.valueOf(LabyModCoreMod.isForge() ? 0 : 1);
            }
        });
        this.entries.put("exception", new Callable<String>() {
            @Override
            public String call() {
                final byte[] bytes = SerializationUtils.serialize((Serializable)ReportData.this.cause);
                return Base64.getEncoder().encodeToString(bytes);
            }
        });
    }
    
    public String createJsonReport() {
        final JsonObject reportData = new JsonObject();
        for (final Map.Entry<String, Callable<String>> entry : this.entries.entrySet()) {
            final String key = entry.getKey();
            String value = "Unknown";
            try {
                value = entry.getValue().call();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            reportData.addProperty(key, value);
        }
        return reportData.toString();
    }
}
