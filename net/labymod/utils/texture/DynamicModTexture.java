//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture;

import java.beans.*;

public class DynamicModTexture
{
    private nf resourceLocation;
    private String url;
    
    public nf getResourceLocation() {
        return this.resourceLocation;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setResourceLocation(final nf resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    @ConstructorProperties({ "resourceLocation", "url" })
    public DynamicModTexture(final nf resourceLocation, final String url) {
        this.resourceLocation = resourceLocation;
        this.url = url;
    }
}
