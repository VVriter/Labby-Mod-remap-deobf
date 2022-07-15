//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote;

import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.geometry.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.user.cosmetic.*;

public interface IRemoteLoadCallback
{
    CosmeticRenderer<?> getRemoteRenderer(final RemoteObject p0, final IGeometryProviderCallback<RemoteData> p1);
}
