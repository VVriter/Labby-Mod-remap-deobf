//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture;

import com.mojang.authlib.yggdrasil.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import com.mojang.authlib.*;
import com.mojang.authlib.minecraft.*;
import net.labymod.utils.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import net.labymod.support.util.*;

public class PlayerSkinTextureCache
{
    private final Map<UUID, nf> loadedSkins;
    private final Map<String, nf> loadedUsernameSkins;
    private final ExecutorService executorService;
    private final YggdrasilAuthenticationService authService;
    private final MinecraftSessionService sessionService;
    
    public PlayerSkinTextureCache() {
        this.loadedSkins = new HashMap<UUID, nf>();
        this.loadedUsernameSkins = new HashMap<String, nf>();
        this.executorService = Executors.newFixedThreadPool(3);
        this.authService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
    }
    
    public nf getSkinTexture(final GameProfile gameProfile) {
        return this.getSkinTexture(gameProfile.getId());
    }
    
    public nf getSkinTexture(final String username) {
        final nf resourceLocation = this.loadedUsernameSkins.get(username);
        if (resourceLocation == null) {
            this.loadTexture(null, username, (Consumer<nf>)new Consumer<nf>() {
                public void accept(final nf loadedTexture) {
                    PlayerSkinTextureCache.this.loadedUsernameSkins.put(username, loadedTexture);
                }
            });
        }
        return resourceLocation;
    }
    
    public nf getSkinTexture(final UUID uuid) {
        final nf resourceLocation = this.loadedSkins.get(uuid);
        if (resourceLocation == null) {
            this.loadTexture(uuid, null, (Consumer<nf>)new Consumer<nf>() {
                public void accept(final nf loadedTexture) {
                    PlayerSkinTextureCache.this.loadedSkins.put(uuid, loadedTexture);
                }
            });
        }
        return resourceLocation;
    }
    
    private void loadTexture(final UUID uuid, final String username, final Consumer<nf> callback) {
        callback.accept((Object)cef.a());
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final GameProfile gameProfile = PlayerSkinTextureCache.this.updateGameprofile(new GameProfile(uuid, username));
                    final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>)bib.z().Y().a(gameProfile);
                    bib.z().a((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            nf resourceLocation;
                            if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                                resourceLocation = bib.z().Y().a((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                            }
                            else {
                                final UUID uuid = aed.a(gameProfile);
                                resourceLocation = cef.a(uuid);
                            }
                            callback.accept((Object)resourceLocation);
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private GameProfile updateGameprofile(final GameProfile input) {
        if (input != null) {
            if (input.isComplete() && input.getProperties().containsKey((Object)"textures")) {
                return input;
            }
            try {
                GameProfile gameprofile = input;
                if (input.getId() == null && input.getName() != null) {
                    gameprofile = new GameProfile(UUIDFetcher.getUUID(input.getName()), input.getName());
                }
                final Property property = (Property)Iterables.getFirst((Iterable)gameprofile.getProperties().get((Object)"textures"), (Object)null);
                if (property == null) {
                    gameprofile = this.sessionService.fillProfileProperties(gameprofile, true);
                }
                return gameprofile;
            }
            catch (NullPointerException exception) {
                Debug.log(Debug.EnumDebugMode.GENERAL, String.format("A player with the name \"%s\" does not exist!", input.getName()));
                return input;
            }
        }
        return input;
    }
}
