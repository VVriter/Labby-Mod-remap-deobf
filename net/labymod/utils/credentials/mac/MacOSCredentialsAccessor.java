//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.mac;

import net.labymod.accountmanager.storage.credentials.*;
import pt.davidafsilva.apple.*;
import net.labymod.accountmanager.storage.*;

public class MacOSCredentialsAccessor implements CredentialsAccessor
{
    private OSXKeychain keychain;
    
    public String getValue(final StorageType storageType, final String id) {
        try {
            if (this.keychain == null) {
                this.keychain = OSXKeychain.getInstance();
            }
            return this.keychain.findGenericPassword("mojangTokenService", id).orElse(null);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}
