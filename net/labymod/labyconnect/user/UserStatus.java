//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.user;

import net.labymod.main.lang.*;

public enum UserStatus
{
    ONLINE((byte)0, "a"), 
    AWAY((byte)1, "b"), 
    BUSY((byte)2, "5"), 
    OFFLINE((byte)(-1), "c");
    
    private byte id;
    private String chatColor;
    private String name;
    
    private UserStatus(final byte id, final String chatColor) {
        this.id = id;
        this.chatColor = chatColor;
        this.name = LanguageManager.translate("user_status_" + this.name().toLowerCase());
    }
    
    public static UserStatus getById(final int id) {
        for (final UserStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        return UserStatus.OFFLINE;
    }
    
    public byte getId() {
        return this.id;
    }
    
    public String getChatColor() {
        return this.chatColor;
    }
    
    public String getName() {
        return this.name;
    }
}
