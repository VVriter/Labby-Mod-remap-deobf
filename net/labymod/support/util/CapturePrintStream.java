//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.util;

import java.io.*;
import org.apache.logging.log4j.*;

public class CapturePrintStream extends PrintStream
{
    private static final Logger LOGGER;
    
    public CapturePrintStream(final OutputStream outStream) {
        super(outStream);
    }
    
    @Override
    public void println(final String string) {
        this.logString(string);
    }
    
    @Override
    public void println(final Object obj) {
        if (obj == null) {
            this.logString("null");
            return;
        }
        if (obj instanceof Throwable) {
            CapturePrintStream.LOGGER.catching((Throwable)obj);
        }
        else if (!obj.toString().startsWith("\t")) {
            this.logString(String.valueOf(obj));
        }
    }
    
    private void logString(final String string) {
        CapturePrintStream.LOGGER.info("{}", new Object[] { string });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
