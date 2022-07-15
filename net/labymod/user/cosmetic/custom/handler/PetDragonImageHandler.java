//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.user.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.utils.texture.*;

public class PetDragonImageHandler extends CosmeticImageHandler
{
    public PetDragonImageHandler(final String userAgent) {
        super(userAgent, "labymodpetdragons", false);
    }
    
    public UserTextureContainer getContainer(final User user) {
        return user.getPetDragonContainer();
    }
    
    public void unload() {
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return null;
    }
}
