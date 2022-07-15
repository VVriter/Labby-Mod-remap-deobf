//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update.migration;

public interface InstallationProgressCallback
{
    void completed();
    
    void progress(final double p0, final String p1);
    
    void failed();
}
