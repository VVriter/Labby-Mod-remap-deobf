//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.user.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.utils.texture.*;

public class BunnyShoesImageHandler extends CosmeticImageHandler
{
    public BunnyShoesImageHandler(final String userAgent) {
        super(userAgent, "labymodbunnyshoes", false);
    }
    
    public UserTextureContainer getContainer(final User user) {
        return user.getBunnyShoesContainer();
    }
    
    public void unload() {
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return null;
    }
}
