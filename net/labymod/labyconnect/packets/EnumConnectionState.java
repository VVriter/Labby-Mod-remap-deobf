//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.main.lang.*;

public enum EnumConnectionState
{
    HELLO(-1, "d"), 
    LOGIN(0, "b"), 
    PLAY(1, "a"), 
    ALL(2, "f", "ALL"), 
    OFFLINE(3, "c");
    
    private int number;
    private String displayColor;
    private String buttonState;
    
    private EnumConnectionState(final int number, final String displayColor) {
        this.number = number;
        this.displayColor = displayColor;
        this.buttonState = LanguageManager.translate("chat_button_state_" + this.name().toLowerCase());
    }
    
    private EnumConnectionState(final int number, final String displayColor, final String buttonState) {
        this.number = number;
        this.displayColor = displayColor;
        this.buttonState = buttonState;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public String getDisplayColor() {
        return this.displayColor;
    }
    
    public String getButtonState() {
        return this.buttonState;
    }
}
