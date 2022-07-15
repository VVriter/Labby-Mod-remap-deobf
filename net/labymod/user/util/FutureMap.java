//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.util;

import java.util.*;
import java.util.function.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

public class FutureMap<K, V>
{
    private static ScheduledExecutorService scheduledExecutorService;
    private Map<K, SettableFuture<V>> futureMap;
    private Map<K, Long> startLoadTimesMap;
    private Consumer<K> loadCallback;
    private long timeout;
    private V defaultValue;
    
    public FutureMap(final Consumer<K> loadCallback, final long timeout, final V defaultValue) {
        this.futureMap = new ConcurrentHashMap<K, SettableFuture<V>>();
        this.startLoadTimesMap = new ConcurrentHashMap<K, Long>();
        this.loadCallback = loadCallback;
        this.timeout = timeout;
        this.defaultValue = defaultValue;
    }
    
    public ListenableFuture<V> get(final K key) {
        if (this.futureMap.containsKey(key)) {
            return (ListenableFuture<V>)this.futureMap.get(key);
        }
        final SettableFuture<V> valueFuture = (SettableFuture<V>)SettableFuture.create();
        this.futureMap.put(key, valueFuture);
        this.startLoadTimesMap.put(key, System.currentTimeMillis());
        this.loadCallback.accept(key);
        final SettableFuture settableFuture;
        FutureMap.scheduledExecutorService.schedule(() -> {
            if (!settableFuture.isDone()) {
                settableFuture.set((Object)this.defaultValue);
                this.futureMap.remove(key);
                this.startLoadTimesMap.remove(key);
            }
            return;
        }, this.timeout, TimeUnit.MILLISECONDS);
        return (ListenableFuture<V>)valueFuture;
    }
    
    public void resolve(final K key, final V value) {
        final SettableFuture<V> valueFuture = this.futureMap.remove(key);
        if (valueFuture != null) {
            valueFuture.set((Object)value);
            this.startLoadTimesMap.remove(key);
        }
    }
    
    static {
        FutureMap.scheduledExecutorService = new ScheduledThreadPoolExecutor(0, new ThreadFactoryBuilder().setNameFormat("LabyMod Futuremap Resolver #%d").setDaemon(true).build());
    }
}
