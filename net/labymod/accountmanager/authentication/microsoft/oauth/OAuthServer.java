//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.oauth;

import java.util.concurrent.*;
import java.util.function.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import net.labymod.accountmanager.authentication.microsoft.*;

public class OAuthServer
{
    private static final String CO_BRAND_ID = "8058f65d-ce06-4c30-9559-473c9275a65d";
    private static final String URL_OAUTH_LOGIN;
    private final ServerSocket serverSocket;
    private final ExecutorService executor;
    
    public OAuthServer() throws IOException {
        this.serverSocket = new ServerSocket(8086);
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    public void listenForCodeAsync(final Consumer<String> callback) {
        this.executor.execute(() -> callback.accept(this.listenForCode()));
    }
    
    public String listenForCode() {
        while (this.serverSocket.isBound()) {
            try {
                final Socket socket = this.serverSocket.accept();
                final Scanner scanner = new Scanner(socket.getInputStream());
                final String path = scanner.nextLine().split(" ")[1];
                final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.write("HTTP/1.0 200 OK\r\n");
                printWriter.write("Content-Type: html; charset=UTF-8\r\n");
                printWriter.write("\r\n");
                printWriter.write("You can close this window now");
                printWriter.flush();
                printWriter.close();
                scanner.close();
                socket.close();
                this.close();
                if (path.contains("=") && path.contains("?code=")) {
                    return path.substring(path.indexOf("=") + 1);
                }
                if (path.contains("?error=")) {
                    return null;
                }
                continue;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
        return null;
    }
    
    public void close() {
        try {
            this.serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public URL getUrl() throws IOException {
        return new URL(OAuthServer.URL_OAUTH_LOGIN);
    }
    
    public static void main(final String[] args) throws Exception {
        final OAuthServer server = new OAuthServer();
        Desktop.getDesktop().browse(server.getUrl().toURI());
        System.out.println(server.listenForCode());
        server.close();
    }
    
    static {
        URL_OAUTH_LOGIN = String.format("https://login.live.com/oauth20_authorize.srf?client_id=%s&response_type=code&redirect_uri=%s&scope=%s&cobrandid=%s&prompt=select_account", "27843883-6e3b-42cb-9e51-4f55a700601e", MicrosoftAuthentication.REDIRECT_URL, "XboxLive.signin%20offline_access", "8058f65d-ce06-4c30-9559-473c9275a65d");
    }
}
