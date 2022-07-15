//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.utils;

import java.util.*;

public class UUIDUtil
{
    public static UUID getUUIDFromCompactUUID(final String compactUUID) {
        return UUID.fromString(compactUUID.substring(0, 8) + "-" + compactUUID.substring(8, 12) + "-" + compactUUID.substring(12, 16) + "-" + compactUUID.substring(16, 20) + "-" + compactUUID.substring(20, 32));
    }
    
    public static boolean isValidUUIDv4(final UUID uuid) {
        return (uuid.getMostSignificantBits() >> 12 & 0xFL) == 0x4L;
    }
}
