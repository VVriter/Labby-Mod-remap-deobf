//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import com.mojang.authlib.*;
import net.labymod.main.*;

@Deprecated
public class ModTextureUtils
{
    @Deprecated
    public static ModTextureUtils INSTANCE;
    
    @Deprecated
    public nf getSkinTexture(final GameProfile gameProfile) {
        return LabyMod.getInstance().getDynamicTextureManager().getHeadTexture(gameProfile);
    }
    
    @Deprecated
    public void drawImageURL(final String username, final String url, final double x, final double y, final double imageWidth, final double imageHeight, final double maxWidth, final double maxHeight) {
        LabyMod.getInstance().getDrawUtils().drawImageUrl(url, x, y, imageWidth, imageHeight, maxWidth, maxHeight);
    }
    
    static {
        ModTextureUtils.INSTANCE = new ModTextureUtils();
    }
}
