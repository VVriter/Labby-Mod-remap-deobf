//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom.handler;

import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.*;
import net.labymod.utils.texture.*;

public class RemoteImageHandler extends CosmeticImageHandler
{
    public final int id;
    private final boolean hideCape;
    private boolean userBound;
    private final String textureDirectory;
    private boolean forceClear;
    
    public RemoteImageHandler(final int id, final String userAgent, final boolean userBound, final String textureDirectory, final Ratio ratio, final boolean canUnload, final boolean hideCape) {
        super(userAgent, "remote_" + id, canUnload, ratio);
        this.forceClear = false;
        this.id = id;
        this.userBound = userBound;
        this.textureDirectory = textureDirectory;
        this.hideCape = hideCape;
    }
    
    public UserTextureContainer getContainer(final User user) {
        final Map<Integer, UserTextureContainer> containerMap = user.getRemoteContainer();
        if (containerMap.containsKey(this.id)) {
            if (this.forceClear) {
                this.forceClear = false;
                for (final UserTextureContainer value : containerMap.values()) {
                    value.unload();
                }
            }
        }
        else {
            final UserTextureContainer container = this.userBound ? new UserTextureContainer(this.id, this.textureDirectory, user.getUuid()) : new UserTextureContainer(this.id, this.textureDirectory);
            containerMap.put(this.id, container);
        }
        return containerMap.get(this.id);
    }
    
    public void unload() {
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return null;
    }
    
    public boolean isHideCape() {
        return this.hideCape;
    }
}
