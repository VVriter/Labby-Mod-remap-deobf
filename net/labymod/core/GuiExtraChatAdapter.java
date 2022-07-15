//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

public interface GuiExtraChatAdapter
{
    void drawChat(final int p0);
    
    void clearChatMessages();
    
    void setChatLine(final Object p0, final int p1, final int p2, final boolean p3, final boolean p4);
    
    void refreshChat();
    
    void scroll(final int p0);
    
    Object getChatComponent(final int p0, final int p1);
}
