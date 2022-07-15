//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.request;

import java.io.*;
import java.beans.*;

public class InputStreamOutput
{
    private InputStream inputStream;
    private int contentLength;
    
    public InputStream getInputStream() {
        return this.inputStream;
    }
    
    public int getContentLength() {
        return this.contentLength;
    }
    
    @ConstructorProperties({ "inputStream", "contentLength" })
    public InputStreamOutput(final InputStream inputStream, final int contentLength) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }
}
