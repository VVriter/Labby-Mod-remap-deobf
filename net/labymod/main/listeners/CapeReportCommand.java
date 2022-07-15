//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;

public class CapeReportCommand implements MessageSendEvent
{
    private static long lastReport;
    
    public boolean onSend(final String msg) {
        final String m = msg.toLowerCase();
        if (m.startsWith("/capereport") || m.startsWith("/reportcape")) {
            if (msg.contains(" ")) {
                if (CapeReportCommand.lastReport < System.currentTimeMillis()) {
                    final String user = msg.split(" ")[1];
                    report(user);
                }
                else {
                    LabyMod.getInstance().displayMessageInChat(ModColor.cl("c") + "You've just reported a cape, please wait for a short while..");
                }
            }
            else {
                LabyMod.getInstance().displayMessageInChat(ModColor.cl("c") + msg + " <player>");
            }
            return true;
        }
        return false;
    }
    
    public static void report(final String user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CapeReportCommand.lastReport = System.currentTimeMillis() + 20000L;
                    LabyMod.getInstance().displayMessageInChat(CapeReportCommand.jsonPost("https://next.api.labymod.net/cloak/report/v3", "reporter=" + LabyMod.getInstance().getPlayerName() + "&owner=" + user));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    public static String jsonPost(final String urlStr, final String json) throws Exception {
        final URL url = new URL(urlStr);
        final HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        httpConnection.setRequestMethod("POST");
        final OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
        out.write(json);
        out.close();
        final int code = httpConnection.getResponseCode();
        if (code / 100 != 5) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            for (String str = br.readLine(); str != null; str = br.readLine()) {
                sb.append(str);
            }
            br.close();
            return ((JsonObject)new Gson().fromJson(sb.toString(), (Class)JsonObject.class)).get("message").getAsString().replaceAll("\u00c2", "");
        }
        return "Response: " + code;
    }
    
    static {
        CapeReportCommand.lastReport = 0L;
    }
}
