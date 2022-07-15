//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa;

import java.util.*;

public enum XALType
{
    DEFAULT("Xal.1794566092.Production.Default"), 
    MSA("Xal.1794566092.Production.Msa.%s"), 
    RETAIL_T("Xal.1794566092.Production.RETAIL.T"), 
    RETAIL_USER("Xal.1794566092.Production.RETAIL.User.%s"), 
    FOCI("Xal.Production.Msa.Foci.1"), 
    RETAIL_D("Xal.Production.RETAIL.D"), 
    DEVICE_IDENTITY("Xal.Production.RETAIL.DeviceIdentity");
    
    private String key;
    
    private XALType(final String key) {
        this.key = key;
    }
    
    private static String toBase64(final String xuid) {
        return Base64.getEncoder().encodeToString(xuid.getBytes()).replace("=", "");
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getKey(final String base64) {
        return String.format(this.key, base64);
    }
    
    public String getBase64Key(final String value) {
        return String.format(this.key, toBase64(value));
    }
}
