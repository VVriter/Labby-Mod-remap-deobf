//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.windows;

import net.labymod.accountmanager.storage.credentials.*;
import net.labymod.accountmanager.storage.*;
import com.sun.jna.ptr.*;
import java.nio.charset.*;
import com.sun.jna.*;

public class WindowsCredentialsAccessor implements CredentialsAccessor
{
    public String getValue(final StorageType storageType, final String id) {
        final IntByReference reference = new IntByReference();
        final PointerByReference pointerByReference = new PointerByReference();
        final boolean result = Advapi32_Credentials.INSTANCE.CredEnumerateW((String)null, 0, reference, pointerByReference);
        if (!result) {
            return null;
        }
        final Pointer[] pointers = pointerByReference.getValue().getPointerArray(0L, reference.getValue());
        for (int i = 0; i < reference.getValue(); ++i) {
            try {
                final Credential credential = new Credential(pointers[i]);
                credential.read();
                if (CredentialType.valueOf(credential.Type) == CredentialType.CRED_TYPE_GENERIC) {
                    final String address = credential.TargetName.getString(0L, true);
                    if (address != null && address.equals(storageType.getCredentialsPrefix() + "|" + id) && credential.CredentialBlobSize > 0) {
                        final byte[] bytes = credential.CredentialBlob.getByteArray(0L, credential.CredentialBlobSize);
                        return new String(bytes, StandardCharsets.UTF_8);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
