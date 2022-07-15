//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.minecraft;

public class ProfileResponse
{
    public String id;
    public String name;
    public Texture[] skins;
    public Texture[] capes;
    public String path;
    public String errorType;
    public String error;
    public String errorMessage;
    public String developerMessage;
    
    public static class Texture
    {
        public String id;
        public String state;
        public String url;
        public String variant;
        public String alias;
    }
}
