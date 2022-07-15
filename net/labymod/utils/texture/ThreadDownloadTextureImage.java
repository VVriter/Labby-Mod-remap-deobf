//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.texture;

import net.labymod.utils.*;
import net.labymod.support.util.*;
import java.awt.image.*;
import net.labymod.utils.texture.async.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.concurrent.*;

public class ThreadDownloadTextureImage extends cdm
{
    private static ExecutorService executorService;
    private String imageUrl;
    private Consumer<Boolean> consumer;
    private String userAgent;
    private SSLSocketFactory socketFactory;
    private TextureImageParser textureImageParser;
    private Debug.EnumDebugMode debugMode;
    private BufferedImage bufferedImage;
    private boolean textureLoaded;
    private Consumer<BufferedImage> callback;
    
    public ThreadDownloadTextureImage(final String imageUrl, final nf textureResourceLocation, final Consumer<Boolean> consumer, final String userAgent) {
        super(textureResourceLocation);
        this.userAgent = "Unknown";
        this.debugMode = Debug.EnumDebugMode.GENERAL;
        this.textureLoaded = false;
        this.imageUrl = imageUrl;
        this.consumer = consumer;
        this.userAgent = userAgent;
    }
    
    public int b() {
        final int textureId = super.b();
        if (!this.textureLoaded && this.bufferedImage != null) {
            this.textureLoaded = true;
            AsyncTextureUtil.uploadTextureImage(textureId, this.bufferedImage);
        }
        return textureId;
    }
    
    public void a(final cep resourceManager) throws IOException {
        this.downloadTexture(null);
    }
    
    public void downloadTexture(final Consumer<ThreadDownloadTextureImage> loadTextureCallback) {
        ThreadDownloadTextureImage.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ThreadDownloadTextureImage.this.imageUrl.startsWith("data:")) {
                        final String[] parts = ThreadDownloadTextureImage.this.imageUrl.split(",");
                        if (parts.length >= 2) {
                            final String data = parts[1];
                            BufferedImage loadedImage = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(data)));
                            if (ThreadDownloadTextureImage.this.textureImageParser != null) {
                                loadedImage = ThreadDownloadTextureImage.this.textureImageParser.parse(loadedImage);
                            }
                            ThreadDownloadTextureImage.this.bufferedImage = loadedImage;
                            if (ThreadDownloadTextureImage.this.bufferedImage == null) {
                                Debug.log(ThreadDownloadTextureImage.this.debugMode, "Failed to convert base64 texture " + data);
                            }
                            else {
                                Debug.log(ThreadDownloadTextureImage.this.debugMode, "Converted texture from base64 with size " + loadedImage.getWidth() + "x" + loadedImage.getHeight());
                            }
                        }
                    }
                    else {
                        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(ThreadDownloadTextureImage.this.imageUrl).openConnection();
                        httpURLConnection.setRequestProperty("User-Agent", ThreadDownloadTextureImage.this.userAgent);
                        if (ThreadDownloadTextureImage.this.socketFactory != null && httpURLConnection instanceof HttpsURLConnection) {
                            ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory(ThreadDownloadTextureImage.this.socketFactory);
                        }
                        httpURLConnection.connect();
                        final int responseCode = httpURLConnection.getResponseCode();
                        if (responseCode / 100 == 2) {
                            BufferedImage loadedImage = cdt.a(httpURLConnection.getInputStream());
                            if (ThreadDownloadTextureImage.this.textureImageParser != null) {
                                loadedImage = ThreadDownloadTextureImage.this.textureImageParser.parse(loadedImage);
                            }
                            ThreadDownloadTextureImage.this.bufferedImage = loadedImage;
                            if (ThreadDownloadTextureImage.this.bufferedImage == null) {
                                Debug.log(ThreadDownloadTextureImage.this.debugMode, "Failed to download texture " + ThreadDownloadTextureImage.this.imageUrl + " (" + responseCode + ")");
                            }
                            else {
                                Debug.log(ThreadDownloadTextureImage.this.debugMode, "Downloaded texture " + ThreadDownloadTextureImage.this.imageUrl);
                            }
                        }
                        else {
                            Debug.log(ThreadDownloadTextureImage.this.debugMode, "Response code for " + ThreadDownloadTextureImage.this.imageUrl + " is " + responseCode);
                        }
                    }
                }
                catch (Exception error) {
                    error.printStackTrace();
                }
                if (ThreadDownloadTextureImage.this.callback != null) {
                    ThreadDownloadTextureImage.this.callback.accept((Object)ThreadDownloadTextureImage.this.bufferedImage);
                }
                ThreadDownloadTextureImage.this.consumer.accept((Object)(ThreadDownloadTextureImage.this.bufferedImage != null));
                if (loadTextureCallback != null) {
                    loadTextureCallback.accept((Object)ThreadDownloadTextureImage.this);
                }
            }
        });
    }
    
    public void setCallback(final Consumer<BufferedImage> callback) {
        this.callback = callback;
    }
    
    public void setSocketFactory(final SSLSocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }
    
    public void setTextureImageParser(final TextureImageParser textureImageParser) {
        this.textureImageParser = textureImageParser;
    }
    
    public void setDebugMode(final Debug.EnumDebugMode debugMode) {
        this.debugMode = debugMode;
    }
    
    static {
        ThreadDownloadTextureImage.executorService = Executors.newFixedThreadPool(5);
    }
    
    public interface TextureImageParser
    {
        BufferedImage parse(final BufferedImage p0);
    }
}
