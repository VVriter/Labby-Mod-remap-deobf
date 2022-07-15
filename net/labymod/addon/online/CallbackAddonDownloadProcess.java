//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.addon.online;

import java.io.*;

public interface CallbackAddonDownloadProcess
{
    void progress(final double p0);
    
    void success(final File p0);
    
    void failed(final String p0);
}
