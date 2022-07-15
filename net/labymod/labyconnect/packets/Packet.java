//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.labyconnect.handling.*;

public abstract class Packet
{
    public abstract void read(final PacketBuf p0);
    
    public abstract void write(final PacketBuf p0);
    
    public abstract void handle(final PacketHandler p0);
}
