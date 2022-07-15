//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture;

import javax.net.ssl.*;
import net.labymod.core.*;
import net.minecraftforge.fml.relauncher.*;
import net.labymod.utils.manager.*;
import java.lang.reflect.*;
import com.mojang.authlib.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.util.*;

public class DynamicTextureManager
{
    private final String resourceName;
    private final nf defaultTexture;
    private String userAgent;
    private SSLSocketFactory socketFactory;
    private ThreadDownloadTextureImage.TextureImageParser textureImageParser;
    protected final Map<String, DynamicModTexture> resourceLocations;
    private Map<nf, cds> mapTextureObjects;
    
    public DynamicTextureManager(final String resourceName, final nf defaultTexture) {
        this.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36";
        this.socketFactory = null;
        this.textureImageParser = null;
        this.resourceLocations = new HashMap<String, DynamicModTexture>();
        this.mapTextureObjects = new HashMap<nf, cds>();
        this.resourceName = resourceName;
        this.defaultTexture = defaultTexture;
    }
    
    public void init() {
        try {
            final Field field = ReflectionHelper.findField(cdr.class, LabyModCore.getMappingAdapter().getMapTextureObjects());
            field.setAccessible(true);
            this.mapTextureObjects = (Map<nf, cds>)field.get(bib.z().N());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (TagManager.SYMBOL - '\n' != 9988) {
            TagManager.SYMBOL = '\u26aa';
            TagManager.SYMBOL += 'd';
        }
    }
    
    @Deprecated
    public nf getHeadTexture(final GameProfile gameProfile) {
        return ModTextures.MISC_HEAD_QUESTION;
    }
    
    public nf getTexture(final String identifier, final String url) {
        final cdr textureManager = bib.z().N();
        DynamicModTexture dynamicModTexture = this.resourceLocations.get(identifier);
        final boolean unloadImage = dynamicModTexture != null && dynamicModTexture.getUrl() != null && !dynamicModTexture.getUrl().equals(url);
        if (dynamicModTexture == null || unloadImage) {
            if (unloadImage) {
                textureManager.c(dynamicModTexture.getResourceLocation());
                dynamicModTexture.setResourceLocation(this.defaultTexture);
                dynamicModTexture.setUrl((String)null);
            }
            else {
                dynamicModTexture = new DynamicModTexture(this.defaultTexture, (String)null);
            }
            this.resourceLocations.put(identifier, dynamicModTexture);
            this.resolveImageTexture(identifier, url);
        }
        return dynamicModTexture.getResourceLocation();
    }
    
    private void resolveImageTexture(final String identifier, final String url) {
        if (identifier == null || url == null) {
            return;
        }
        final cdr textureManager = bib.z().N();
        final nf resourceLocation = new nf(this.resourceName + "/" + this.getHash(url));
        final ThreadDownloadTextureImage threadDownloadImageData = new ThreadDownloadTextureImage(url, resourceLocation, (Consumer<Boolean>)new Consumer<Boolean>() {
            public void accept(final Boolean accepted) {
                DynamicTextureManager.this.resourceLocations.put(identifier, new DynamicModTexture(resourceLocation, url));
            }
        }, this.userAgent);
        threadDownloadImageData.setSocketFactory(this.socketFactory);
        threadDownloadImageData.setTextureImageParser(this.textureImageParser);
        textureManager.a(resourceLocation, (cds)threadDownloadImageData);
    }
    
    public void unloadAll() {
        bib.z().a((Runnable)new Runnable() {
            @Override
            public void run() {
                try {
                    for (final Map.Entry<String, DynamicModTexture> entry : DynamicTextureManager.this.resourceLocations.entrySet()) {
                        final nf resourceLocation = entry.getValue().getResourceLocation();
                        DynamicTextureManager.this.mapTextureObjects.remove(resourceLocation);
                    }
                    DynamicTextureManager.this.resourceLocations.clear();
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });
    }
    
    private int getHash(final String url) {
        int hash = 7;
        for (int i = 0; i < url.length(); ++i) {
            hash = hash * 31 + url.charAt(i);
        }
        return hash;
    }
    
    public String getResourceName() {
        return this.resourceName;
    }
    
    public nf getDefaultTexture() {
        return this.defaultTexture;
    }
    
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public SSLSocketFactory getSocketFactory() {
        return this.socketFactory;
    }
    
    public ThreadDownloadTextureImage.TextureImageParser getTextureImageParser() {
        return this.textureImageParser;
    }
    
    public Map<String, DynamicModTexture> getResourceLocations() {
        return this.resourceLocations;
    }
    
    public Map<nf, cds> getMapTextureObjects() {
        return this.mapTextureObjects;
    }
    
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    public void setSocketFactory(final SSLSocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }
    
    public void setTextureImageParser(final ThreadDownloadTextureImage.TextureImageParser textureImageParser) {
        this.textureImageParser = textureImageParser;
    }
}
