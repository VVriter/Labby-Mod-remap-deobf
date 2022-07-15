//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112;

import net.labymod.core_implementation.mc112.serverpinger.*;
import net.labymod.utils.*;
import net.labymod.core.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

public class ServerPingerImplementation implements ServerPingerAdapter
{
    private static final ThreadPoolExecutor threadPool;
    private ServerPinger serverPinger;
    
    public ServerPingerImplementation() {
        this.serverPinger = new ServerPinger();
    }
    
    public void pingServer(ExecutorService threadPool, final long timePinged, final String ipAddress, final Consumer<ServerPingerData> callback) {
        if (threadPool == null) {
            threadPool = ServerPingerImplementation.threadPool;
        }
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerPingerImplementation.this.serverPinger.ping(callback, new ServerPingerData(ipAddress, timePinged));
                }
                catch (Throwable throwable) {
                    callback.accept(null);
                }
            }
        });
    }
    
    public void pingServer(final ServerPingerData serverData, final Consumer<ServerPingerData> serverDataCallback) throws Throwable {
        this.serverPinger.ping((Consumer)serverDataCallback, serverData);
    }
    
    public void tick() {
        this.serverPinger.pingPendingNetworks();
    }
    
    public void closePendingConnections() {
        this.serverPinger.clearPendingNetworks();
    }
    
    static {
        threadPool = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("LabyMod Server Pinger #%d").setDaemon(true).build());
    }
}
