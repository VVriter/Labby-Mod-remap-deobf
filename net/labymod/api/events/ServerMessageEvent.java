//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.events;

import com.google.gson.*;

public interface ServerMessageEvent
{
    void onServerMessage(final String p0, final JsonElement p1);
}
