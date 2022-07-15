//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.jpountz.xxhash;

final class StreamingXXHash32JNI extends StreamingXXHash32
{
    private long state;
    
    StreamingXXHash32JNI(final int seed) {
        super(seed);
        this.state = XXHashJNI.XXH32_init(seed);
    }
    
    private void checkState() {
        if (this.state == 0L) {
            throw new AssertionError((Object)"Already finalized");
        }
    }
    
    public void reset() {
        this.checkState();
        XXHashJNI.XXH32_free(this.state);
        this.state = XXHashJNI.XXH32_init(this.seed);
    }
    
    public int getValue() {
        this.checkState();
        return XXHashJNI.XXH32_digest(this.state);
    }
    
    public void update(final byte[] bytes, final int off, final int len) {
        this.checkState();
        XXHashJNI.XXH32_update(this.state, bytes, off, len);
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        XXHashJNI.XXH32_free(this.state);
        this.state = 0L;
    }
    
    static class Factory implements StreamingXXHash32.Factory
    {
        public static final StreamingXXHash32.Factory INSTANCE;
        
        public StreamingXXHash32 newStreamingHash(final int seed) {
            return new StreamingXXHash32JNI(seed);
        }
        
        static {
            INSTANCE = (StreamingXXHash32.Factory)new Factory();
        }
    }
}
