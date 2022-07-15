//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.linux;

import com.sun.jna.*;
import java.util.*;

public class GList extends Structure
{
    public Pointer data;
    public Pointer next;
    
    public GList(final Pointer p) {
        super(p);
        this.read();
    }
    
    protected List<String> getFieldOrder() {
        return Arrays.asList("data", "next");
    }
}
