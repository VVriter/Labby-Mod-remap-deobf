//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.credentials.windows;

import java.util.*;

public enum CredentialType
{
    CRED_TYPE_GENERIC(1), 
    CRED_TYPE_DOMAIN_PASSWORD(2), 
    CRED_TYPE_DOMAIN_CERTIFICATE(3), 
    CRED_TYPE_DOMAIN_VISIBLE_PASSWORD(4), 
    CRED_TYPE_GENERIC_CERTIFICATE(5), 
    CRED_TYPE_DOMAIN_EXTENDED(6), 
    CRED_TYPE_MAXIMUM(7);
    
    private static Map<Integer, CredentialType> map;
    private int code;
    
    private CredentialType(final int code) {
        this.code = code;
    }
    
    public static CredentialType valueOf(final int n) {
        if (n > 7) {
            throw new RuntimeException("unknown CredentialType");
        }
        return CredentialType.map.get(n);
    }
    
    static {
        CredentialType.map = new HashMap<Integer, CredentialType>();
        for (final CredentialType c : values()) {
            CredentialType.map.put(c.code, c);
        }
    }
}
