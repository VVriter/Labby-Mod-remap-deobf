//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.geometry.effect.util;

public enum PhysicMapping
{
    X, 
    Y, 
    Z, 
    F, 
    G, 
    S, 
    N;
    
    public static PhysicMapping get(final char character) {
        return (character == 'x') ? PhysicMapping.X : ((character == 'y') ? PhysicMapping.Y : ((character == 'z') ? PhysicMapping.Z : ((character == 'f') ? PhysicMapping.F : ((character == 's') ? PhysicMapping.S : ((character == 'g') ? PhysicMapping.G : PhysicMapping.N)))));
    }
}
