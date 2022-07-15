//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.splash;

import net.labymod.splash.advertisement.*;
import net.labymod.splash.splashdates.*;
import net.labymod.splash.dailyemotes.*;

public class SplashEntries
{
    private Advertisement[] left;
    private Advertisement[] right;
    private SplashDate[] splashDates;
    private DailyEmote[] dailyEmotes;
    
    public Advertisement[] getLeft() {
        return this.left;
    }
    
    public Advertisement[] getRight() {
        return this.right;
    }
    
    public SplashDate[] getSplashDates() {
        return this.splashDates;
    }
    
    public DailyEmote[] getDailyEmotes() {
        return this.dailyEmotes;
    }
}
