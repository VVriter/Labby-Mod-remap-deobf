//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import javax.xml.bind.*;

public class Base64Manager
{
    public static String encode(final String string) {
        final String encoded = DatatypeConverter.printBase64Binary(string.getBytes());
        return encoded;
    }
    
    public static String decode(final String base64String) {
        final String decoded = new String(DatatypeConverter.parseBase64Binary(base64String));
        return decoded;
    }
}
