//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.remote;

import com.google.gson.*;
import net.labymod.user.cosmetic.*;
import java.util.concurrent.*;
import net.labymod.utils.request.*;
import net.labymod.user.cosmetic.remote.objects.*;
import net.labymod.support.util.*;
import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.geometry.*;
import net.labymod.user.cosmetic.remote.objects.data.*;
import net.labymod.utils.texture.*;
import net.labymod.main.*;
import net.labymod.user.cosmetic.custom.handler.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.*;

public class RemoteCosmeticLoader
{
    public static final Gson GSON;
    private RemoteCosmeticIndex index;
    private final List<CosmeticRenderer<?>> remoteRenderer;
    private final List<IRemoteCallback> listener;
    private Map<Integer, RemoteObject> discoverableRemoteObjects;
    private final List<Integer> alreadyDiscovered;
    private boolean remoteRefresh;
    
    public RemoteCosmeticLoader() {
        this.remoteRenderer = new CopyOnWriteArrayList<CosmeticRenderer<?>>();
        this.listener = new CopyOnWriteArrayList<IRemoteCallback>();
        this.discoverableRemoteObjects = new HashMap<Integer, RemoteObject>();
        this.alreadyDiscovered = new ArrayList<Integer>();
    }
    
    public void load() {
        final boolean clientCanSeeDraftCosmetics = LabyMod.getInstance().getUserManager().getClientUser().isCanSeeDraftCosmetics();
        DownloadServerRequest.getStringAsync("https://dl.labymod.net/cosmetics/index.json", new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                try {
                    RemoteCosmeticLoader.this.index = (RemoteCosmeticIndex)RemoteCosmeticLoader.GSON.fromJson(json, (Class)RemoteCosmeticIndex.class);
                    final Map<Integer, RemoteObject> discoverableRemoteObjects = new HashMap<Integer, RemoteObject>();
                    if (RemoteCosmeticLoader.this.index != null && RemoteCosmeticLoader.this.index.cosmetics != null) {
                        for (final RemoteObject cosmetic : RemoteCosmeticLoader.this.index.cosmetics.values()) {
                            if (!clientCanSeeDraftCosmetics && cosmetic.draft) {
                                continue;
                            }
                            discoverableRemoteObjects.put(cosmetic.id, cosmetic);
                        }
                        RemoteCosmeticLoader.this.discoverableRemoteObjects = discoverableRemoteObjects;
                        for (final Integer id : RemoteCosmeticLoader.this.alreadyDiscovered) {
                            RemoteCosmeticLoader.this.discover(id);
                        }
                        RemoteCosmeticLoader.this.alreadyDiscovered.clear();
                        if (!RemoteCosmeticLoader.this.remoteRefresh) {
                            LabyMod.getInstance().getUserManager().refresh();
                            RemoteCosmeticLoader.this.remoteRefresh = true;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void failed(final RequestException e) {
                e.printStackTrace();
            }
        });
    }
    
    public void discover(final int objectId) {
        final RemoteObject object = this.discoverableRemoteObjects.remove(objectId);
        if (object == null) {
            if (!this.alreadyDiscovered.contains(objectId)) {
                this.alreadyDiscovered.add(objectId);
            }
            return;
        }
        if (object.type == EnumCosmeticType.COSMETIC) {
            this.loadRemote(object, RemoteCosmetic::new);
        }
        if (object.type.isPet()) {
            this.loadRemote(object, RemotePet::new);
        }
    }
    
    protected void loadRemote(final RemoteObject object, final IRemoteLoadCallback callback) {
        Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Load remote cosmetic " + object.name + " with id " + object.id + " as " + object.type.name());
        try {
            final String geometryJson = DownloadServerRequest.getString(String.format("https://dl.labymod.net/cosmetics/%s/geo.json", object.id));
            final String animationJson = DownloadServerRequest.getString(String.format("https://dl.labymod.net/cosmetics/%s/animation.json", object.id));
            if (object.textureType == EnumTextureType.MOJANG_BOUND) {
                final CosmeticRenderer<?> renderer = (CosmeticRenderer<?>)callback.getRemoteRenderer(object, (IGeometryProviderCallback)new IGeometryProviderCallback<RemoteData>() {
                    public String getGeometryJson() {
                        return geometryJson;
                    }
                    
                    public String getAnimationJson() {
                        return animationJson;
                    }
                    
                    public nf getTexture(final vg entity, final RemoteData data) {
                        final PlayerSkinTextureCache textureLoader = LabyMod.getInstance().getDrawUtils().getPlayerSkinTextureCache();
                        if (data.textureUUID == null) {
                            return textureLoader.getSkinTexture(entity.bm());
                        }
                        return textureLoader.getSkinTexture(data.textureUUID);
                    }
                });
                this.remoteRenderer.add(renderer);
                for (final IRemoteCallback listener : this.listener) {
                    listener.load((CosmeticRenderer)renderer);
                }
            }
            else {
                final boolean userBound = object.textureType == EnumTextureType.USER_BOUND;
                final RemoteImageHandler handler = new RemoteImageHandler(object.id, Source.getUserAgent(), userBound, object.textureDirectory, object.ratio, object.textureType == EnumTextureType.USER_BOUND, object.hideCape);
                LabyMod.getInstance().getUserManager().getCosmeticImageManager().getCosmeticImageHandlers().add(handler);
                final CosmeticRenderer<?> renderer2 = (CosmeticRenderer<?>)callback.getRemoteRenderer(object, (IGeometryProviderCallback)new IGeometryProviderCallback<RemoteData>() {
                    public String getGeometryJson() {
                        return geometryJson;
                    }
                    
                    public String getAnimationJson() {
                        return animationJson;
                    }
                    
                    public nf getTexture(final vg entity, final RemoteData data) {
                        final AnimatedResourceLocation texture = handler.getAnimatedResourceLocation((bua)entity);
                        if (texture != null) {
                            data.updateDepthMap(texture.getDepthMap());
                        }
                        return (texture == null) ? null : texture.getFrameAtCurrentTime();
                    }
                });
                this.remoteRenderer.add(renderer2);
                for (final IRemoteCallback listener2 : this.listener) {
                    listener2.load((CosmeticRenderer)renderer2);
                }
            }
        }
        catch (Exception e) {
            Debug.log(Debug.EnumDebugMode.REMOTE_COSMETIC, "Could not download remote cosmetic " + object.name + " (" + object.id + "): " + e.getMessage());
        }
    }
    
    public void getAsync(final IRemoteCallback consumer) {
        for (final CosmeticRenderer<?> cosmeticRenderer : new ArrayList<CosmeticRenderer>(this.remoteRenderer)) {
            consumer.load((CosmeticRenderer)cosmeticRenderer);
        }
        this.listener.add(consumer);
    }
    
    public RemoteCosmeticIndex getIndex() {
        return this.index;
    }
    
    public RemoteObject getObject(final int id) {
        final Collection<RemoteObject> cosmetics = LabyMod.getInstance().getUserManager().getRemoteCosmeticLoader().getIndex().cosmetics.values();
        for (final RemoteObject object : cosmetics) {
            if (object.id == id) {
                return object;
            }
        }
        return null;
    }
    
    public void reset() {
        for (final IRemoteCallback callback : this.listener) {
            for (final CosmeticRenderer<?> cosmeticRenderer : new ArrayList<CosmeticRenderer>(this.remoteRenderer)) {
                callback.unload((CosmeticRenderer)cosmeticRenderer);
            }
        }
        this.remoteRenderer.clear();
    }
    
    static {
        GSON = new Gson();
    }
}
