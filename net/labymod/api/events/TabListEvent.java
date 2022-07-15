//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.events;

public interface TabListEvent
{
    void onUpdate(final Type p0, final String p1, final String p2);
    
    public enum Type
    {
        HEADER, 
        FOOTER;
    }
}
