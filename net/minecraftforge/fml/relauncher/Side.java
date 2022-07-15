//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.minecraftforge.fml.relauncher;

public enum Side
{
    CLIENT, 
    SERVER;
    
    public boolean isServer() {
        return !this.isClient();
    }
    
    public boolean isClient() {
        return this == Side.CLIENT;
    }
}
