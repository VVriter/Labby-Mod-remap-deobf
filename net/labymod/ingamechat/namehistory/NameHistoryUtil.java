//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.namehistory;

import java.util.*;
import net.labymod.utils.*;
import java.util.concurrent.*;

public class NameHistoryUtil
{
    private static HashMap<String, NameHistory> cacheHistory;
    private static final ExecutorService EXECUTOR_SERVICE;
    
    public static NameHistory getNameHistory(final String name) {
        if (NameHistoryUtil.cacheHistory.containsKey(name)) {
            return NameHistoryUtil.cacheHistory.get(name);
        }
        final NameHistory nameHistory = new NameHistory(UUID.randomUUID(), new UUIDFetcher[0]);
        NameHistoryUtil.cacheHistory.put(name, nameHistory);
        NameHistoryUtil.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                final NameHistory nameHistory = requestHistory(name);
                NameHistoryUtil.cacheHistory.put(name, nameHistory);
            }
        });
        return nameHistory;
    }
    
    public static void getNameHistory(final String name, final Consumer<NameHistory> callback) {
        if (NameHistoryUtil.cacheHistory.containsKey(name)) {
            callback.accept(NameHistoryUtil.cacheHistory.get(name));
            return;
        }
        final NameHistory nameHistory = new NameHistory(UUID.randomUUID(), new UUIDFetcher[0]);
        NameHistoryUtil.cacheHistory.put(name, nameHistory);
        NameHistoryUtil.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                final NameHistory history = requestHistory(name);
                NameHistoryUtil.cacheHistory.put(name, history);
                callback.accept(history);
            }
        });
    }
    
    public static boolean isInCache(final String name) {
        return NameHistoryUtil.cacheHistory.containsKey(name);
    }
    
    private static NameHistory requestHistory(final String name) {
        final UUID uuid = UUIDFetcher.getUUID(name);
        if (uuid == null) {
            return null;
        }
        return UUIDFetcher.getHistory(uuid);
    }
    
    static {
        NameHistoryUtil.cacheHistory = new HashMap<String, NameHistory>();
        EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    }
}
