//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.request;

public interface ServerStatus
{
    void success();
    
    void failed(final RequestException p0);
    
    void close() throws Exception;
}
