//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote.keys.provider;

import net.labymod.user.emote.keys.*;

public abstract class EmoteProvider
{
    public abstract boolean hasNext(final int p0);
    
    public abstract boolean isWaiting();
    
    public abstract PoseAtTime next(final int p0);
    
    public abstract void clear();
}
