//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.util;

import java.nio.charset.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import com.google.gson.*;
import java.awt.datatransfer.*;

public class Hastebin
{
    private static final JsonParser JSON_PARSER;
    
    public static void upload(final String content) {
        try {
            final byte[] postData = content.getBytes(StandardCharsets.UTF_8);
            final int postDataLength = postData.length;
            final String request = "https://paste.labymod.net/documents";
            final URL url = new URL(request);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            final DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            final Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String json = "";
            int c;
            while ((c = in.read()) >= 0) {
                json += (char)c;
            }
            final JsonElement jsonElement = Hastebin.JSON_PARSER.parse(json);
            if (jsonElement.getAsJsonObject().has("key")) {
                final String key = jsonElement.getAsJsonObject().get("key").getAsString();
                final StringSelection selection = new StringSelection("https://paste.labymod.net/" + key);
                final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URI("https://paste.labymod.net/" + key));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, json, "Error while uploading log", 0);
            }
        }
        catch (Exception error) {
            error.printStackTrace();
            JOptionPane.showMessageDialog(null, error.getMessage(), "Error while uploading log", 0);
        }
    }
    
    static {
        JSON_PARSER = new JsonParser();
    }
}
