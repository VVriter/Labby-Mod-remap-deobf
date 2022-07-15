//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials;

import net.labymod.accountmanager.storage.credentials.*;
import net.labymod.utils.*;
import net.labymod.utils.credentials.windows.*;
import net.labymod.utils.credentials.mac.*;
import net.labymod.utils.credentials.linux.*;

public class CredentialsStorages
{
    public static CredentialsAccessor createAccessorForOS() {
        switch (OSUtil.getOS()) {
            case WIN_32: {
                return null;
            }
            case WIN_64: {
                return (CredentialsAccessor)new WindowsCredentialsAccessor();
            }
            case MAC: {
                return (CredentialsAccessor)new MacOSCredentialsAccessor();
            }
            case UNIX: {
                return (CredentialsAccessor)new LinuxCredentialsAccessor();
            }
            default: {
                return null;
            }
        }
    }
}
