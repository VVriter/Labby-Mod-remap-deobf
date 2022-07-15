//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

import net.labymod.api.protocol.chunk.*;
import net.labymod.api.protocol.shadow.*;

public interface ProtocolAdapter
{
    void onReceiveChunkPacket(final Object p0, final Object p1);
    
    void loadChunk(final ChunkCachingProtocol p0, final Extracted p1, final int p2, final int p3, final boolean p4);
    
    void loadChunkBulk(final ChunkCachingProtocol p0, final Extracted[] p1, final int[] p2, final int[] p3);
    
    boolean handleOutgoingPacket(final Object p0, final ShadowProtocol p1);
}
