//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.permissions;

import net.labymod.api.events.*;
import net.labymod.support.util.*;
import net.labymod.main.*;
import net.labymod.api.protocol.liquid.*;
import java.util.*;
import com.google.gson.*;

public class PermissionsListener implements ServerMessageEvent
{
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!messageKey.equals("PERMISSIONS") || !serverMessage.isJsonObject()) {
            return;
        }
        final JsonObject permissionsObject = serverMessage.getAsJsonObject();
        for (final Map.Entry<String, JsonElement> jsonEntry : permissionsObject.entrySet()) {
            final Permissions.Permission permission = Permissions.Permission.getPermissionByName((String)jsonEntry.getKey());
            final JsonPrimitive primitive = jsonEntry.getValue().isJsonPrimitive() ? jsonEntry.getValue().getAsJsonPrimitive() : null;
            if (permission == null || primitive == null || !primitive.isBoolean()) {
                Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "Permission " + jsonEntry.getKey() + " is not supported!");
            }
            else {
                LabyMod.getInstance().getServerManager().getPermissionMap().put(permission, primitive.getAsBoolean());
                if (permission != Permissions.Permission.IMPROVED_LAVA) {
                    continue;
                }
                FixedLiquidBucketProtocol.onPermissionUpdate(primitive.getAsBoolean());
            }
        }
    }
}
