//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage;

public enum StorageType
{
    JAVA((String)null), 
    MICROSOFT("MCL"), 
    MICROSOFT_STORE("MCLMS"), 
    EXTERNAL((String)null);
    
    private final String credentialsPrefix;
    
    private StorageType(final String credentialsPrefix) {
        this.credentialsPrefix = credentialsPrefix;
    }
    
    public String getCredentialsPrefix() {
        return this.credentialsPrefix;
    }
}
