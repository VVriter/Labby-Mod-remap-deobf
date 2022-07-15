//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry;

public interface IGeometryProviderCallback<T>
{
    String getGeometryJson();
    
    String getAnimationJson();
    
    nf getTexture(final vg p0, final T p1);
}
