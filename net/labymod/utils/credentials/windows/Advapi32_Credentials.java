//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.windows;

import com.sun.jna.win32.*;
import com.sun.jna.ptr.*;
import com.sun.jna.*;

public interface Advapi32_Credentials extends StdCallLibrary
{
    public static final Advapi32_Credentials INSTANCE = (Advapi32_Credentials)Native.loadLibrary("advapi32", (Class)Advapi32_Credentials.class);
    
    boolean CredEnumerateW(final String p0, final int p1, final IntByReference p2, final PointerByReference p3);
}
