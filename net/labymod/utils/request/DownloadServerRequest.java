//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.request;

import com.google.gson.*;
import java.nio.charset.*;
import java.io.*;
import java.net.*;
import net.labymod.main.*;
import net.labymod.support.util.*;
import java.util.concurrent.*;

public class DownloadServerRequest
{
    private static final Executor EXECUTOR;
    private static final JsonParser JSON_PARSER;
    
    public static void getJsonObjectAsync(final String url, final ServerResponse<JsonElement> response) {
        getStringAsync(url, new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                response.success(DownloadServerRequest.JSON_PARSER.parse(json));
            }
            
            @Override
            public void failed(final RequestException exception) {
                response.failed(exception);
            }
        });
    }
    
    public static void getStringAsync(final String url, final ServerResponse<String> response) {
        getBytesAsync(url, new ServerResponse<byte[]>() {
            @Override
            public void success(final byte[] bytes) {
                response.success(new String(bytes, StandardCharsets.UTF_8));
            }
            
            @Override
            public void failed(final RequestException exception) {
                response.failed(exception);
            }
        });
    }
    
    public static void getBytesAsync(final String url, final ServerResponse<byte[]> response) {
        DownloadServerRequest.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    response.success(DownloadServerRequest.getBytes(url));
                }
                catch (RequestException exception) {
                    response.failed(exception);
                }
            }
        });
    }
    
    public static String getString(final String url) throws Exception {
        return new String(getBytes(url), StandardCharsets.UTF_8);
    }
    
    public static JsonElement getJsonObject(final String url) throws Exception {
        return DownloadServerRequest.JSON_PARSER.parse(getString(url));
    }
    
    public static void writeBytesAsync(final String url, final OutputStream outputStream, final ServerStatus status) {
        DownloadServerRequest.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DownloadServerRequest.writeBytes(url, outputStream);
                    try {
                        status.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    status.success();
                }
                catch (RequestException exception) {
                    try {
                        status.close();
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    status.failed(exception);
                }
            }
        });
    }
    
    public static byte[] getBytes(final String url) throws RequestException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        writeBytes(url, buffer);
        return buffer.toByteArray();
    }
    
    public static void writeBytes(final String url, final OutputStream outputStream) throws RequestException {
        try {
            final InputStream inputStream = getInputStream(url).getInputStream();
            final byte[] data = new byte[1024];
            int num;
            while ((num = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, num);
            }
            outputStream.flush();
        }
        catch (Exception ex) {
            throw new RequestException(ex);
        }
    }
    
    public static InputStreamOutput getInputStream(final String url) throws RequestException {
        try {
            URL urlObject = new URL(url);
            final boolean isEncrypted = urlObject.getProtocol().equals("https");
            for (int i = 0; i < (isEncrypted ? 2 : 1); ++i) {
                final boolean isUsingHttpNow = i == 1 || !isEncrypted;
                if (isUsingHttpNow && isEncrypted) {
                    urlObject = new URL("http", urlObject.getHost(), urlObject.getPort(), urlObject.getFile());
                }
                try {
                    final HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
                    connection.setRequestProperty("User-Agent", Source.getUserAgent());
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(2000);
                    connection.connect();
                    final int responseCode = connection.getResponseCode();
                    if (responseCode / 100 == 2) {
                        final InputStream inputStream = connection.getInputStream();
                        final int contentLength = connection.getContentLength();
                        return new InputStreamOutput(inputStream, contentLength);
                    }
                    if (isUsingHttpNow) {
                        throw new RequestException(responseCode);
                    }
                    Debug.log(Debug.EnumDebugMode.GENERAL, "Failed to request " + url + " because error code " + responseCode);
                }
                catch (Exception ex) {
                    if (isUsingHttpNow) {
                        throw new RequestException(ex);
                    }
                    Debug.log(Debug.EnumDebugMode.GENERAL, "Failed to request " + url + " because " + ex.getMessage());
                }
            }
            throw new RequestException(-1);
        }
        catch (Exception e) {
            throw new RequestException(e);
        }
    }
    
    public static void getInputStreamAsync(final String url, final ServerInputStream serverInputStream) {
        DownloadServerRequest.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final InputStreamOutput output = DownloadServerRequest.getInputStream(url);
                    serverInputStream.opened(output.getInputStream(), output.getContentLength());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    if (exception instanceof RequestException) {
                        serverInputStream.failed((RequestException)exception);
                    }
                    else {
                        serverInputStream.failed(new RequestException(exception));
                    }
                }
            }
        });
    }
    
    static {
        EXECUTOR = Executors.newFixedThreadPool(5);
        JSON_PARSER = new JsonParser();
    }
}
