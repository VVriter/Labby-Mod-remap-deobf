//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.user.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.utils.texture.*;

public class BandanaImageHandler extends CosmeticImageHandler
{
    public BandanaImageHandler(final String userAgent) {
        super(userAgent, "labymodbandana", true);
    }
    
    public UserTextureContainer getContainer(final User user) {
        return user.getBandanaContainer();
    }
    
    public void unload() {
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return null;
    }
}
