//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

import java.util.concurrent.*;
import net.labymod.utils.*;

public interface ServerPingerAdapter
{
    void pingServer(final ExecutorService p0, final long p1, final String p2, final Consumer<ServerPingerData> p3);
    
    void pingServer(final ServerPingerData p0, final Consumer<ServerPingerData> p1) throws Throwable;
    
    void tick();
    
    void closePendingConnections();
}
