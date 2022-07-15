//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture.async;

import java.util.concurrent.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;

public class AsyncTextureLoader extends Thread
{
    private final BlockingQueue<Runnable> queue;
    private final List<BulkTask> bulkTasks;
    private Drawable drawable;
    
    public AsyncTextureLoader() {
        this.queue = new LinkedBlockingQueue<Runnable>();
        this.bulkTasks = new ArrayList<BulkTask>();
        if (OSUtil.getOS() == OSUtil.WIN_32) {
            return;
        }
        try {
            this.drawable = (Drawable)new SharedDrawable(Display.getDrawable());
            this.start();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public boolean isAsyncAvailable() {
        return this.drawable != null && LabyMod.getSettings().loadTexturesAsync;
    }
    
    public void runTaskAsync(final Runnable runnable) {
        if (this.isAsyncAvailable()) {
            this.queue.add(runnable);
        }
        else {
            bib.z().a(runnable);
        }
    }
    
    public void runBulkTask(final BulkTask task) {
        this.bulkTasks.add(task);
    }
    
    public void processBulkTasks() {
        try {
            final BulkTask task = this.bulkTasks.isEmpty() ? null : this.bulkTasks.get(0);
            if (task != null) {
                final Runnable runnable = task.remove();
                if (runnable == null) {
                    task.completed();
                    this.bulkTasks.remove(task);
                }
                else {
                    runnable.run();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.bulkTasks.clear();
        }
    }
    
    public void uploadTextureAsync(final nf resourceLocation, final cdf texture) {
        this.runTaskAsync(() -> this.uploadTexture(resourceLocation, texture));
    }
    
    public void uploadTexture(final nf resourceLocation, final cdf texture) {
        try {
            texture.a(bib.z().O());
            bib.z().a(() -> LabyMod.getInstance().getDynamicTextureManager().getMapTextureObjects().put(resourceLocation, (cds)texture));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            this.drawable.makeCurrent();
            while (!Thread.interrupted()) {
                try {
                    this.uploadNextTextureInQueue();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GL11.glFlush();
            }
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
        if (this.isAsyncAvailable()) {
            this.drawable.destroy();
            this.drawable = null;
        }
    }
    
    private void uploadNextTextureInQueue() throws InterruptedException {
        this.queue.take().run();
    }
}
