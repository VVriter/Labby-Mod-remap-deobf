//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.linux;

public class GKResult
{
    public static final int OK = 0;
    private final GKLib gklib;
    private final int code;
    
    public GKResult(final GKLib gklib, final int code) {
        this.gklib = gklib;
        this.code = code;
    }
    
    public <T> void error() throws RuntimeException {
        throw new RuntimeException(this.gklib.gnome_keyring_result_to_message(this.code));
    }
    
    public boolean success() {
        return this.code == 0;
    }
}
