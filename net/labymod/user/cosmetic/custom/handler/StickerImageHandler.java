//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.utils.texture.*;

public class StickerImageHandler extends CosmeticImageHandler
{
    private Map<Short, UserTextureContainer> userTextureContainers;
    
    public StickerImageHandler(final String userAgent) {
        super(userAgent, "labymodsticker", false);
        this.userTextureContainers = new HashMap<Short, UserTextureContainer>();
    }
    
    public UserTextureContainer getContainer(final User user, final short stickerId) {
        UserTextureContainer container = this.userTextureContainers.get(stickerId);
        if (container != null) {
            return container;
        }
        container = new UserTextureContainer("sticker", new UUID(stickerId, 0L));
        container.resolved();
        this.userTextureContainers.put(stickerId, container);
        return container;
    }
    
    public UserTextureContainer getContainer(final User user) {
        final short playingSticker = user.getPlayingSticker();
        if (playingSticker == -1) {
            return null;
        }
        return this.getContainer(user, playingSticker);
    }
    
    public void unload() {
        this.userTextureContainers.clear();
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return null;
    }
}
