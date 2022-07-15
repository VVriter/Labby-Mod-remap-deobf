//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.lz4;

public class LZ4Exception extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public LZ4Exception(final String msg, final Throwable t) {
        super(msg, t);
    }
    
    public LZ4Exception(final String msg) {
        super(msg);
    }
    
    public LZ4Exception() {
    }
}
