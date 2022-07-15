//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom;

import java.awt.image.*;
import net.labymod.utils.texture.async.*;

public class BufferedImageTexture extends cdf
{
    private BufferedImage bufferedImage;
    
    public BufferedImageTexture(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
    
    public void a(final cep resourceManager) {
        if (this.bufferedImage == null) {
            return;
        }
        AsyncTextureUtil.uploadTextureImage(this.b(), this.bufferedImage);
        this.bufferedImage = null;
    }
    
    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }
}
