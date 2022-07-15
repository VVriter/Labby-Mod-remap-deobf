//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.linux;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

public interface GKLib extends Library
{
    public static final GKLib INSTANCE = (GKLib)Native.loadLibrary("gnome-keyring", (Class)GKLib.class);
    
    int gnome_keyring_item_get_info_full_sync(final String p0, final int p1, final int p2, final PointerByReference p3);
    
    void gnome_keyring_item_info_free(final Pointer p0);
    
    String gnome_keyring_item_info_get_display_name(final Pointer p0);
    
    String gnome_keyring_item_info_get_secret(final Pointer p0);
    
    String gnome_keyring_result_to_message(final int p0);
    
    int gnome_keyring_get_default_keyring_sync(final PointerByReference p0);
    
    int gnome_keyring_list_item_ids_sync(final String p0, final PointerByReference p1);
}
