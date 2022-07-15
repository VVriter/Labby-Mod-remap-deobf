//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.splash.splashdates;

import java.beans.*;

public class SplashDate
{
    private String displayString;
    private boolean birthday;
    private int month;
    private int day;
    
    public String getDisplayString() {
        return this.birthday ? ("Happy birthday, " + this.displayString) : this.displayString;
    }
    
    public boolean isBirthday() {
        return this.birthday;
    }
    
    public int getMonth() {
        return this.month;
    }
    
    public int getDay() {
        return this.day;
    }
    
    @ConstructorProperties({ "displayString", "birthday", "month", "day" })
    public SplashDate(final String displayString, final boolean birthday, final int month, final int day) {
        this.displayString = displayString;
        this.birthday = birthday;
        this.month = month;
        this.day = day;
    }
}
