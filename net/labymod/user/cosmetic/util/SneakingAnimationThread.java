//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.util;

import net.labymod.core.*;
import net.labymod.main.*;

public class SneakingAnimationThread extends Thread
{
    private float ySize;
    
    public SneakingAnimationThread() {
        this.ySize = 0.0f;
    }
    
    @Override
    public void run() {
        while (this.isInUse()) {
            try {
                Thread.sleep(5L);
            }
            catch (Exception error) {
                error.printStackTrace();
            }
            if (LabyModCore.getMinecraft().getPlayer() != null) {
                if (LabyModCore.getMinecraft().getPlayer().e == null) {
                    continue;
                }
                final boolean isSneaking = LabyModCore.getMinecraft().getPlayer().e.h;
                if (isSneaking) {
                    this.ySize += 0.01f;
                    if (this.ySize <= 0.08f) {
                        continue;
                    }
                    this.ySize = 0.08f;
                }
                else {
                    this.ySize *= 0.91f;
                }
            }
        }
    }
    
    public boolean isInUse() {
        return LabyMod.getInstance() != null && LabyMod.getMainConfig() != null && LabyMod.getSettings().oldSneaking;
    }
    
    public float getYSize() {
        return this.ySize;
    }
}
