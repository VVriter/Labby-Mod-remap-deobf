//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic;

import net.labymod.user.cosmetic.custom.handler.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.support.util.*;

public class CosmeticImageManager
{
    private List<CosmeticImageHandler> cosmeticImageHandlers;
    private final CloakImageHandler cloakImageHandler;
    private final BandanaImageHandler bandanaImageHandler;
    private final ShoesImageHandler shoesImageHandler;
    private final StickerImageHandler stickerImageHandler;
    private final KawaiiMaskImageHandler kawaiiMaskImageHandler;
    private final CoverMaskImageHandler coverMaskImageHandler;
    private final WatchImageHandler watchImageHandler;
    private final AngelWingsImageHandler angelWingsImageHandler;
    private final CapImageHandler capImageHandler;
    private final PetDragonImageHandler petDragonImageHandler;
    private final BunnyShoesImageHandler bunnyShoesImageHandler;
    private final ScarfImageHandler scarfImageHandler;
    
    public CosmeticImageManager(final String userAgent) {
        (this.cosmeticImageHandlers = new ArrayList<CosmeticImageHandler>()).add(this.cloakImageHandler = new CloakImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.bandanaImageHandler = new BandanaImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.shoesImageHandler = new ShoesImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.stickerImageHandler = new StickerImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.kawaiiMaskImageHandler = new KawaiiMaskImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.coverMaskImageHandler = new CoverMaskImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.watchImageHandler = new WatchImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.angelWingsImageHandler = new AngelWingsImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.capImageHandler = new CapImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.petDragonImageHandler = new PetDragonImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.bunnyShoesImageHandler = new BunnyShoesImageHandler(userAgent));
        this.cosmeticImageHandlers.add(this.scarfImageHandler = new ScarfImageHandler(userAgent));
    }
    
    public void loadPlayersInView() {
        if (!LabyMod.getInstance().isInGame()) {
            return;
        }
        for (final aed player : LabyModCore.getMinecraft().getWorld().i) {
            if (player.equals((Object)LabyModCore.getMinecraft().getPlayer())) {
                continue;
            }
            final User user = LabyMod.getInstance().getUserManager().getUser(player.bm());
            for (final CosmeticImageHandler handler : this.cosmeticImageHandlers) {
                handler.validate(user);
            }
        }
    }
    
    public void unloadUnusedTextures(final boolean forceNotIngame, final boolean forceSelf) {
        for (final CosmeticImageHandler handler : new ArrayList<CosmeticImageHandler>(this.cosmeticImageHandlers)) {
            if (handler != null) {
                if (!handler.isCanUnload() && !forceSelf) {
                    continue;
                }
                try {
                    final List<UUID> toRemove = new ArrayList<UUID>(handler.getResourceLocations().keySet());
                    if (LabyMod.getInstance().isInGame() && !forceNotIngame) {
                        for (final aed player : LabyModCore.getMinecraft().getWorld().i) {
                            final UUID uuid = player.bm();
                            if (toRemove.contains(uuid)) {
                                toRemove.remove(uuid);
                            }
                        }
                    }
                    int noImage = 0;
                    for (final UUID rem : toRemove) {
                        if (rem.equals(LabyMod.getInstance().getPlayerUUID()) && !forceSelf) {
                            continue;
                        }
                        final nf resourceLocation = handler.getResourceLocations().get(rem).getDefault();
                        if (resourceLocation == null || !resourceLocation.a().startsWith(handler.getResourceName() + "/")) {
                            ++noImage;
                        }
                        else {
                            for (final nf frame : handler.getResourceLocations().get(rem).getAllFrames()) {
                                bib.z().N().c(frame);
                            }
                            Debug.log(Debug.EnumDebugMode.COSMETIC_IMAGE_MANAGER, "Unloaded " + resourceLocation.a());
                        }
                        handler.getResourceLocations().remove(rem);
                        final User user = LabyMod.getInstance().getUserManager().getUser(rem);
                        if (user == null) {
                            continue;
                        }
                        user.unloadCosmeticTextures();
                    }
                    handler.unload();
                    Debug.log(Debug.EnumDebugMode.COSMETIC_IMAGE_MANAGER, "Unloaded " + toRemove.size() + " unused " + handler.getResourceName() + " and " + noImage + " had no labymod textures! " + handler.getResourceLocations().size() + " " + handler.getResourceName() + " left.");
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }
    }
    
    public List<CosmeticImageHandler> getCosmeticImageHandlers() {
        return this.cosmeticImageHandlers;
    }
    
    public CloakImageHandler getCloakImageHandler() {
        return this.cloakImageHandler;
    }
    
    public BandanaImageHandler getBandanaImageHandler() {
        return this.bandanaImageHandler;
    }
    
    public ShoesImageHandler getShoesImageHandler() {
        return this.shoesImageHandler;
    }
    
    public StickerImageHandler getStickerImageHandler() {
        return this.stickerImageHandler;
    }
    
    public KawaiiMaskImageHandler getKawaiiMaskImageHandler() {
        return this.kawaiiMaskImageHandler;
    }
    
    public CoverMaskImageHandler getCoverMaskImageHandler() {
        return this.coverMaskImageHandler;
    }
    
    public WatchImageHandler getWatchImageHandler() {
        return this.watchImageHandler;
    }
    
    public AngelWingsImageHandler getAngelWingsImageHandler() {
        return this.angelWingsImageHandler;
    }
    
    public CapImageHandler getCapImageHandler() {
        return this.capImageHandler;
    }
    
    public PetDragonImageHandler getPetDragonImageHandler() {
        return this.petDragonImageHandler;
    }
    
    public BunnyShoesImageHandler getBunnyShoesImageHandler() {
        return this.bunnyShoesImageHandler;
    }
    
    public ScarfImageHandler getScarfImageHandler() {
        return this.scarfImageHandler;
    }
}
