//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.util;

import net.labymod.core_implementation.mc112.gui.*;
import net.labymod.main.*;

public class BossbarUtil
{
    public static String lastRenderedMessage;
    
    public static int shouldRenderBar(final int y, final Object object) {
        final bjj bossinfoclient = (bjj)object;
        BossbarUtil.lastRenderedMessage = bossinfoclient.e().d();
        final bjq tab = bib.z().q.h();
        final boolean tablistOpen = tab instanceof ModPlayerTabOverlay && ((ModPlayerTabOverlay)tab).isBeingRendered();
        final boolean hasServerBanner = LabyMod.getInstance().getPriorityOverlayRenderer().hasServerBanner();
        return (LabyMod.getSettings().showBossBar && (!hasServerBanner || !tablistOpen)) ? y : -20;
    }
}
