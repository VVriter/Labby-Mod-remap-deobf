//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.linux;

import net.labymod.accountmanager.storage.credentials.*;
import net.labymod.accountmanager.storage.*;
import com.sun.jna.ptr.*;
import com.sun.jna.*;

public class LinuxCredentialsAccessor implements CredentialsAccessor
{
    public String getValue(final StorageType storageType, final String id) {
        try {
            final GKLib gklib = GKLib.INSTANCE;
            final PointerByReference keyring_ref = new PointerByReference();
            final GKResult result = new GKResult(gklib, gklib.gnome_keyring_get_default_keyring_sync(keyring_ref));
            if (result.success()) {
                final String keyRing = keyring_ref.getValue().getString(0L);
                final PointerByReference pref = new PointerByReference();
                final GKResult idResult = new GKResult(gklib, gklib.gnome_keyring_list_item_ids_sync(keyRing, pref));
                if (idResult.success()) {
                    final Pointer p = pref.getValue();
                    GList gkal = new GList(p);
                    while (true) {
                        final long keyRingId = Pointer.nativeValue(gkal.data);
                        final PointerByReference item_info_ref = new PointerByReference();
                        Pointer item_info = null;
                        try {
                            final GKResult resultItem = new GKResult(gklib, gklib.gnome_keyring_item_get_info_full_sync(keyRing, (int)keyRingId, 1, item_info_ref));
                            if (resultItem.success()) {
                                item_info = item_info_ref.getValue();
                                final String display = gklib.gnome_keyring_item_info_get_display_name(item_info);
                                final String secret = gklib.gnome_keyring_item_info_get_secret(item_info);
                                if (display.equals(id)) {
                                    return secret;
                                }
                            }
                            else {
                                resultItem.error();
                            }
                        }
                        finally {
                            if (item_info != null) {
                                gklib.gnome_keyring_item_info_free(item_info);
                            }
                        }
                        if (gkal.next == Pointer.NULL) {
                            break;
                        }
                        gkal = new GList(gkal.next);
                    }
                }
            }
            else {
                result.error();
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}
