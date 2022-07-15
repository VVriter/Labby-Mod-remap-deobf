//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.request;

public class RequestException extends Exception
{
    private static final long serialVersionUID = -5437299376222011036L;
    private int code;
    
    public RequestException(final int code) {
        super("Response code: " + code);
        this.code = -1;
        this.code = code;
    }
    
    public RequestException(final Exception exception) {
        super(exception.getMessage());
        this.code = -1;
        this.initCause(exception);
    }
    
    public int getCode() {
        return this.code;
    }
}
