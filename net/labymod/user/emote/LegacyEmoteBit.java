//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote;

import net.labymod.main.*;
import java.util.*;
import net.labymod.core.*;

public class LegacyEmoteBit
{
    public static void broadcastBitUpdate(final boolean bit) {
        if (LabyMod.getInstance().isServerHasEmoteSpamProtection()) {
            return;
        }
        final bid gameSettings = bib.z().t;
        int i = 0;
        for (final aee enumplayermodelparts : gameSettings.d()) {
            i |= enumplayermodelparts.a();
        }
        if (bit) {
            sendBitMask(i ^ 0x90);
            sendBitMask(i ^ 0x80);
        }
        else {
            sendBitMask(i);
        }
    }
    
    private static void sendBitMask(final int bitMask) {
        final bid gameSettings = bib.z().t;
        LabyModCore.getMinecraft().sendClientSettings(gameSettings.aJ, gameSettings.e, gameSettings.o, gameSettings.p, bitMask);
    }
}
