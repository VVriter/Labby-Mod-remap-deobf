//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.request;

import java.io.*;

public interface ServerInputStream
{
    void opened(final InputStream p0, final int p1) throws Exception;
    
    void failed(final RequestException p0);
}
