//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.custom;

import net.labymod.user.*;
import net.labymod.main.*;
import net.labymod.utils.texture.*;
import org.apache.commons.io.*;
import net.labymod.support.util.*;
import net.labymod.utils.request.*;
import net.labymod.user.cosmetic.remote.model.*;
import net.labymod.user.cosmetic.remote.*;
import java.net.*;
import javax.imageio.*;
import java.util.*;
import java.awt.image.*;
import net.labymod.utils.texture.async.*;
import java.util.concurrent.*;

public abstract class CosmeticImageHandler
{
    private static final Executor EXECUTOR;
    private final Ratio ratio;
    protected Map<UUID, AnimatedResourceLocation> resourceLocations;
    private String userAgent;
    private String resourceName;
    private boolean canUnload;
    
    public CosmeticImageHandler(final String userAgent, final String resourceName, final boolean canUnload) {
        this(userAgent, resourceName, canUnload, null);
    }
    
    public CosmeticImageHandler(final String userAgent, final String resourceName, final boolean canUnload, final Ratio ratio) {
        this.resourceLocations = new HashMap<UUID, AnimatedResourceLocation>();
        this.userAgent = userAgent;
        this.resourceName = resourceName;
        this.canUnload = canUnload;
        this.ratio = ratio;
    }
    
    public void loadUserTexture(final UUID uuid, final String url) {
        if (url == null || (this.resourceLocations.containsKey(uuid) && !this.canUnload)) {
            return;
        }
        if (this.ratio == null) {
            this.loadStaticImageTexture(uuid, url);
        }
        else {
            this.loadAnimatedImageTexture(uuid, url);
        }
    }
    
    public AnimatedResourceLocation getAnimatedResourceLocation(final bua player) {
        final User user = LabyMod.getInstance().getUserManager().getUser(player.bm());
        final UserTextureContainer container = this.getContainer(user);
        if (container == null) {
            return null;
        }
        container.validateTexture(this);
        return this.resourceLocations.get(container.getFileName());
    }
    
    public nf getResourceLocation(final bua player, final int frame) {
        final AnimatedResourceLocation resource = this.getAnimatedResourceLocation(player);
        return (resource == null) ? ModTextures.TRANSPARENT : resource.get(frame);
    }
    
    public nf getResourceLocation(final bua player) {
        return this.getResourceLocation(player, 0);
    }
    
    public abstract UserTextureContainer getContainer(final User p0);
    
    public void validate(final User user) {
        final UserTextureContainer container = this.getContainer(user);
        if (container != null) {
            container.validateTexture(this);
        }
    }
    
    public abstract void unload();
    
    public abstract ThreadDownloadTextureImage.TextureImageParser getTextureImageParser();
    
    private void loadStaticImageTexture(final UUID uuid, final String url) {
        final AnimatedResourceLocation animatedResource = new AnimatedResourceLocation(this.resourceName + "/" + FilenameUtils.getBaseName(url));
        final nf resourceLocation = animatedResource.getDefault();
        final AnimatedResourceLocation animatedResourceLocation;
        final ThreadDownloadTextureImage textureCosmetic = new ThreadDownloadTextureImage(url, animatedResource.getDefault(), success -> animatedResourceLocation = this.resourceLocations.put(uuid, animatedResource), this.userAgent);
        textureCosmetic.setDebugMode(Debug.EnumDebugMode.COSMETIC_IMAGE_MANAGER);
        textureCosmetic.setTextureImageParser(this.getTextureImageParser());
        textureCosmetic.setCallback(image -> animatedResource.setDepthMap(new DepthMap(image)));
        Debug.log(Debug.EnumDebugMode.COSMETIC_IMAGE_MANAGER, "Load static image " + animatedResource.getPath());
        LabyMod.getInstance().getAsyncTextureLoader().uploadTextureAsync(resourceLocation, (cdf)textureCosmetic);
    }
    
    protected void loadAnimatedImageTexture(final UUID uuid, final String url) {
        String metaJson;
        TextureMeta textureMeta;
        BufferedImage spriteMapImage;
        int frameWidth;
        int frameHeight;
        int frames;
        final AnimatedResourceLocation animatedResourceLocation;
        AnimatedResourceLocation animatedResource;
        ArrayList<BufferedImageTexture> queue;
        int frame;
        BufferedImage imageFrame;
        AsyncTextureLoader asyncTextureLoader;
        int frame2;
        final ArrayList<BufferedImageTexture> list;
        BufferedImageTexture texture;
        final AnimatedResourceLocation animatedResourceLocation2;
        nf resource;
        final AsyncTextureLoader asyncTextureLoader2;
        AnimatedResourceLocation animatedResourceLocation3;
        BulkTask bulkTask;
        int frame3;
        BufferedImageTexture texture2;
        nf resource2;
        CosmeticImageHandler.EXECUTOR.execute(() -> {
            try {
                metaJson = DownloadServerRequest.getString(url + ".json");
                textureMeta = (TextureMeta)RemoteCosmeticLoader.GSON.fromJson(metaJson, (Class)TextureMeta.class);
                spriteMapImage = ImageIO.read(new URL(url).openStream());
                frameWidth = spriteMapImage.getWidth();
                frameHeight = (int)(frameWidth / (this.ratio.width / (float)this.ratio.height));
                frames = spriteMapImage.getHeight() / frameHeight;
                new AnimatedResourceLocation(this.resourceName + "/" + FilenameUtils.getBaseName(url), frames, (long)textureMeta.delay);
                animatedResource = animatedResourceLocation;
                Debug.log(Debug.EnumDebugMode.COSMETIC_IMAGE_MANAGER, "Load animated image " + animatedResource.getPath());
                queue = new ArrayList<BufferedImageTexture>();
                for (frame = 0; frame < frames; ++frame) {
                    imageFrame = spriteMapImage.getSubimage(0, frame * frameHeight, frameWidth, frameHeight);
                    queue.add(new BufferedImageTexture(imageFrame));
                }
                asyncTextureLoader = LabyMod.getInstance().getAsyncTextureLoader();
                if (asyncTextureLoader.isAsyncAvailable()) {
                    asyncTextureLoader.runTaskAsync(() -> {
                        for (frame2 = 0; frame2 < list.size(); ++frame2) {
                            texture = list.get(frame2);
                            resource = animatedResourceLocation2.get(frame2);
                            if (resource != null) {
                                asyncTextureLoader2.uploadTexture(resource, (cdf)texture);
                            }
                        }
                        this.resourceLocations.put(uuid, animatedResourceLocation2);
                    });
                }
                else {
                    bulkTask = new BulkTask(accepted -> animatedResourceLocation3 = this.resourceLocations.put(uuid, animatedResource));
                    for (frame3 = 0; frame3 < queue.size(); ++frame3) {
                        texture2 = queue.get(frame3);
                        resource2 = animatedResource.get(frame3);
                        if (resource2 != null) {
                            bulkTask.queue(() -> bib.z().N().a(resource2, (cds)texture2));
                        }
                    }
                    asyncTextureLoader.runBulkTask(bulkTask);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public Map<UUID, AnimatedResourceLocation> getResourceLocations() {
        return this.resourceLocations;
    }
    
    public String getResourceName() {
        return this.resourceName;
    }
    
    public boolean isCanUnload() {
        return this.canUnload;
    }
    
    static {
        EXECUTOR = Executors.newFixedThreadPool(5);
    }
}
