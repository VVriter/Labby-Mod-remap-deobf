//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import net.labymod.support.util.*;
import com.google.gson.*;
import java.util.*;
import java.nio.charset.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class IssueCollector
{
    private static final String os;
    private static final String url = "https://issue.labymod.net/";
    private static boolean handled;
    
    public static void handle(final String ip, final int port) {
        if (IssueCollector.handled) {
            return;
        }
        IssueCollector.handled = true;
        Gson gson;
        Map<String, Object> data;
        String dataString;
        Executors.newCachedThreadPool().execute(() -> {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Collect data to report the issue...");
            try {
                gson = new Gson();
                data = collectData(ip, port);
                dataString = gson.toJson((Object)data);
                try {
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Post data: " + postData("https://issue.labymod.net/labyConnectSubmit.php", gson.toJson((Object)data)));
                }
                catch (IOException e) {
                    Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Could not paste data: " + dataString);
                    e.printStackTrace();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        });
    }
    
    private static Map<String, Object> collectData(final String ip, final int port) {
        final InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
        final Map<String, Object> data = new HashMap<String, Object>();
        data.put("ip", socketAddress.getAddress().getHostAddress());
        data.put("port", socketAddress.getPort());
        final String connect = canConnect(socketAddress);
        if ("true".equalsIgnoreCase(connect)) {
            data.put("canConnect", true);
        }
        else {
            data.put("canConnect", false);
            data.put("connectError", connect);
        }
        try {
            data.put("currentIp", getMyCurrentIP());
        }
        catch (Exception e) {
            e.printStackTrace();
            data.put("currentIp", getStackTrace(e));
        }
        data.put("traceRoute", traceRoute(ip));
        return data;
    }
    
    private static Map<String, String> traceRoute(final String ip) {
        final Map<String, String> response = new HashMap<String, String>();
        try {
            Process traceRt;
            if (IssueCollector.os.contains("win")) {
                traceRt = Runtime.getRuntime().exec("tracert " + ip);
            }
            else {
                traceRt = Runtime.getRuntime().exec("traceroute -I " + ip);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(traceRt.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder string = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                string.append(line);
                Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, line);
            }
            reader.close();
            response.put("response", string.toString());
            reader = new BufferedReader(new InputStreamReader(traceRt.getErrorStream(), StandardCharsets.UTF_8));
            string = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                string.append(line);
            }
            reader.close();
            if (!string.toString().isEmpty()) {
                Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, string.toString());
            }
            response.put("errors", string.toString());
        }
        catch (IOException e) {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "error while performing trace route command");
            e.printStackTrace();
        }
        return response;
    }
    
    public static String getMyCurrentIP() throws Exception {
        final StringBuilder result = new StringBuilder();
        final URL apiUrl = new URL("https://issue.labymod.net/myIp.php");
        final HttpURLConnection conn = (HttpURLConnection)apiUrl.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        conn.setRequestMethod("GET");
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }
    
    private static String canConnect(final InetSocketAddress socketAddress) {
        final Socket socket = new Socket();
        try {
            socket.connect(socketAddress, (int)TimeUnit.SECONDS.toMillis(10L));
            socket.close();
        }
        catch (IOException e) {
            return getStackTrace(e);
        }
        return "true";
    }
    
    private static String getStackTrace(final Exception e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    
    private static String postData(final String url, final String data) throws IOException {
        final URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36");
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        try (final OutputStream output = connection.getOutputStream()) {
            output.write(data.getBytes("UTF-8"));
        }
        try (final BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            final StringBuilder s = new StringBuilder();
            String l;
            while ((l = response.readLine()) != null) {
                s.append(l).append("\n");
            }
            return s.toString();
        }
    }
    
    static {
        os = System.getProperty("os.name").toLowerCase();
        IssueCollector.handled = false;
    }
}
