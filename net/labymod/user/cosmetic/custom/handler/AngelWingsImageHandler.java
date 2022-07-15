//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.user.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.utils.texture.*;

public class AngelWingsImageHandler extends CosmeticImageHandler
{
    public AngelWingsImageHandler(final String userAgent) {
        super(userAgent, "labymodangelwings", false);
    }
    
    public UserTextureContainer getContainer(final User user) {
        return user.getAngelWingsContainer();
    }
    
    public void unload() {
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return null;
    }
}
