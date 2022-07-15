//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture.async;

import net.labymod.utils.*;
import java.util.*;

public class BulkTask
{
    private final Queue<Runnable> queue;
    private final Consumer<BulkTask> callback;
    
    public BulkTask(final Consumer<BulkTask> callback) {
        this.queue = new LinkedList<Runnable>();
        this.callback = callback;
    }
    
    public void queue(final Runnable runnable) {
        this.queue.add(runnable);
    }
    
    public void completed() {
        this.callback.accept((Object)this);
    }
    
    public Runnable remove() {
        return this.queue.poll();
    }
}
