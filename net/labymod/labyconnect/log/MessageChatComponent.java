//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.log;

import java.beans.*;

public class MessageChatComponent
{
    private String sender;
    private long sentTime;
    private String message;
    
    public String getSender() {
        return this.sender;
    }
    
    public long getSentTime() {
        return this.sentTime;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    @ConstructorProperties({ "sender", "sentTime", "message" })
    public MessageChatComponent(final String sender, final long sentTime, final String message) {
        this.sender = sender;
        this.sentTime = sentTime;
        this.message = message;
    }
}
