//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.permissions;

import java.io.*;
import net.labymod.main.*;
import net.labymod.api.protocol.liquid.*;
import net.labymod.support.util.*;
import java.util.*;

public class OldPluginMessage
{
    @Deprecated
    public static void handlePluginMessage(final String channel, final gy data) {
        try {
            if (data != null && channel != null && channel.equals("LABYMOD")) {
                final int length = data.capacity();
                final byte[] array = new byte[length];
                for (int i = 0; i < length; ++i) {
                    array[i] = data.readByte();
                }
                final ByteArrayInputStream byteIn = new ByteArrayInputStream(array);
                final ObjectInputStream in = new ObjectInputStream(byteIn);
                final Map<String, Boolean> list = (Map<String, Boolean>)in.readObject();
                for (final Map.Entry<String, Boolean> entry : list.entrySet()) {
                    try {
                        final Permissions.Permission permission = Permissions.Permission.valueOf(entry.getKey().toUpperCase());
                        LabyMod.getInstance().getServerManager().getPermissionMap().put(permission, entry.getValue());
                        if (permission != Permissions.Permission.IMPROVED_LAVA) {
                            continue;
                        }
                        FixedLiquidBucketProtocol.onPermissionUpdate(entry.getValue());
                    }
                    catch (Exception error2) {
                        Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "[OldVersionSupport] Permission " + entry.getKey() + " is not supported!");
                    }
                }
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
}
