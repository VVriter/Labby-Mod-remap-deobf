//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.liquid;

import net.labymod.api.permissions.*;
import net.labymod.support.util.*;
import io.netty.buffer.*;
import net.labymod.core.*;
import net.labymod.main.*;

public class FixedLiquidBucketProtocol
{
    public static void handleBucketAction(final Action bucketAction, final int x, final int y, final int z) {
        if (!Permissions.isAllowed(Permissions.Permission.IMPROVED_LAVA)) {
            Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "Can't handle LAVA_UPDATE with action " + bucketAction.name() + " - not allowed!");
            return;
        }
        final gy packetBuffer = new gy(Unpooled.buffer());
        packetBuffer.writeInt(bucketAction.ordinal());
        if (bucketAction == Action.FILL_BUCKET || bucketAction == Action.EMPTY_BUCKET) {
            packetBuffer.writeInt(x);
            packetBuffer.writeInt(y);
            packetBuffer.writeInt(z);
            Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "LAVA_UPDATE with action " + bucketAction.name() + " at " + x + " " + y + " " + z);
        }
        else {
            Debug.log(Debug.EnumDebugMode.PLUGINMESSAGE, "LAVA_UPDATE with action " + bucketAction.name());
        }
        LabyModCore.getMinecraft().sendPluginMessage("LAVA_UPDATE", packetBuffer);
        LabyModCore.getMinecraft().sendPluginMessage("labymod3:lava_update", packetBuffer);
    }
    
    public static void onPermissionUpdate(final boolean allowed) {
        if (!allowed) {
            return;
        }
        if (LabyMod.getSettings().improvedLavaFixedGhostBlocks) {
            handleBucketAction(Action.ENABLE, 0, 0, 0);
        }
    }
    
    public enum Action
    {
        FILL_BUCKET, 
        EMPTY_BUCKET, 
        ENABLE, 
        DISABLE;
    }
}
