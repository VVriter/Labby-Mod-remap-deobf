//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.xxhash;

abstract class AbstractStreamingXXHash32Java extends StreamingXXHash32
{
    int v1;
    int v2;
    int v3;
    int v4;
    int memSize;
    long totalLen;
    final byte[] memory;
    
    AbstractStreamingXXHash32Java(final int seed) {
        super(seed);
        this.memory = new byte[16];
        this.reset();
    }
    
    @Override
    public void reset() {
        this.v1 = this.seed - 1640531535 - 2048144777;
        this.v2 = this.seed - 2048144777;
        this.v3 = this.seed + 0;
        this.v4 = this.seed + 1640531535;
        this.totalLen = 0L;
        this.memSize = 0;
    }
}
