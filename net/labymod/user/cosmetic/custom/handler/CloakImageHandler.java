//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.utils.texture.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.custom.*;
import java.awt.image.*;

public class CloakImageHandler extends CosmeticImageHandler
{
    private static final ThreadDownloadTextureImage.TextureImageParser TEXTURE_IMAGE_PARSER;
    
    public CloakImageHandler(final String userAgent) {
        super(userAgent, "labymodcloak", true);
    }
    
    public UserTextureContainer getContainer(final User user) {
        return user.getCloakContainer();
    }
    
    public void unload() {
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return CloakImageHandler.TEXTURE_IMAGE_PARSER;
    }
    
    static {
        TEXTURE_IMAGE_PARSER = new ThreadDownloadTextureImage.TextureImageParser() {
            @Override
            public BufferedImage parse(final BufferedImage input) {
                return input;
            }
        };
    }
    
    public enum EnumCapePriority
    {
        LABYMOD, 
        OTHER;
    }
}
