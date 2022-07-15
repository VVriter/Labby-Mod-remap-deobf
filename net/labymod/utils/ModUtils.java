//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.util.regex.*;
import java.util.zip.*;
import org.apache.commons.io.*;
import java.nio.charset.*;
import javax.net.ssl.*;
import java.security.*;
import java.io.*;
import net.labymod.main.lang.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;

public class ModUtils
{
    public static String getProfileNameByIp(String ip) {
        if (ip == null) {
            return null;
        }
        if (ip.contains(":")) {
            ip = ip.split(":")[0];
        }
        if (ip.contains(".")) {
            final String[] parts = ip.split("\\.");
            if (parts.length >= 2) {
                return parts[parts.length - 2] + "." + parts[parts.length - 1];
            }
        }
        return ip.toLowerCase();
    }
    
    public static List<String> extractUrls(final String text) {
        final List<String> containedUrls = new ArrayList<String>();
        final String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        final Pattern pattern = Pattern.compile(urlRegex, 2);
        final Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
        }
        return containedUrls;
    }
    
    public static String download(final String urlStr) {
        try {
            final URL url = new URL(urlStr);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            final String encoding = conn.getContentEncoding();
            InputStream inStr = null;
            if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                inStr = new GZIPInputStream(conn.getInputStream());
            }
            else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
                inStr = new InflaterInputStream(conn.getInputStream(), new Inflater(true));
            }
            else {
                inStr = conn.getInputStream();
            }
            return IOUtils.toString(inStr);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static String getContentString(final String page) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(page).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            final BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            String s = "";
            String line;
            while ((line = r.readLine()) != null) {
                s += line;
            }
            return s;
        }
        catch (Exception error) {
            error.printStackTrace();
            return "";
        }
    }
    
    public static ArrayList<String> getContentList(final String page) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(page).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            final BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            final ArrayList<String> s = new ArrayList<String>();
            String line;
            while ((line = r.readLine()) != null) {
                s.add(line);
            }
            return s;
        }
        catch (Exception error) {
            error.printStackTrace();
            return new ArrayList<String>();
        }
    }
    
    public static String parseTimeNormalize(final long time) {
        final long formatb = time / 60L % 60L;
        final long formatc = time % 60L;
        final long formatd = time / 600L / 60L % 24L;
        final long formate = time / 600L / 60L / 24L;
        String out = "";
        if (formate != 0L) {
            out = out + formate + "d ";
        }
        if (formatd != 0L) {
            out = out + formatd + "h ";
        }
        if (formatb != 0L) {
            out = out + formatb + "m ";
        }
        if (formatc != 0L) {
            out = out + formatc + "s";
        }
        return out;
    }
    
    public static String performPost(final URL url, final String parameters, final String contentType, final boolean returnErrorPage) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        final byte[] paramAsBytes = parameters.getBytes(Charset.forName("UTF-8"));
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", "" + paramAsBytes.length);
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        final DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.write(paramAsBytes);
        writer.flush();
        writer.close();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e) {
            if (!returnErrorPage) {
                throw e;
            }
            final InputStream stream = connection.getErrorStream();
            if (stream == null) {
                throw e;
            }
            reader = new BufferedReader(new InputStreamReader(stream));
        }
        final StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        reader.close();
        return response.toString();
    }
    
    public static URL constantURL(final String input) {
        try {
            return new URL(input);
        }
        catch (MalformedURLException ex) {
            return null;
        }
    }
    
    public static String jsonPost(final String urlStr, final String json) throws Exception {
        final URL url = new URL(urlStr);
        final HttpsURLConnection httpConnection = (HttpsURLConnection)url.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestMethod("POST");
        final OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
        out.write(json);
        out.close();
        final BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        final StringBuffer sb = new StringBuffer();
        for (String str = br.readLine(); str != null; str = br.readLine()) {
            sb.append(str);
        }
        br.close();
        return sb.toString();
    }
    
    public static String normalizeString(final String input) {
        final char[] c = input.toLowerCase().toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }
    
    public static String sha1(final String string) {
        String sha1 = "";
        try {
            final MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return sha1;
    }
    
    private static String byteToHex(final byte[] hash) {
        final Formatter formatter = new Formatter();
        for (final byte b : hash) {
            formatter.format("%02x", b);
        }
        final String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public static String translateAlternateColorCodes(final char altColorChar, final String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '§';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
    
    public static String parseTimer(final int seconds) {
        return (seconds >= 3600) ? String.format("%02d:%02d", seconds / 60 / 60, seconds / 60, seconds % 60) : String.format("%02d:%02d", seconds / 60, seconds % 60);
    }
    
    public static String humanReadableByteCount(final long bytes, final boolean si, final boolean space) {
        final int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        final int exp = (int)(Math.log((double)bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f" + (space ? " " : "") + "%sB", bytes / Math.pow(unit, exp), pre);
    }
    
    public static String getTimeDiff(final long timestamp) {
        if (timestamp == 0L) {
            return LanguageManager.translate("time_unknown");
        }
        final long time = System.currentTimeMillis() - timestamp;
        if (time == 0L) {
            return LanguageManager.translate("time_now");
        }
        if (time < 0L) {
            return LanguageManager.translate("time_future");
        }
        final long secs = time / 1000L;
        final long mins = secs / 60L;
        final long hours = mins / 60L;
        final long days = hours / 24L;
        final long months = days / 31L;
        final long years = months / 12L;
        String date = null;
        if (months >= 12L) {
            date = years + " " + LanguageManager.translate("time_" + ((years == 1L) ? "year" : "years"));
        }
        else if (days >= 31L) {
            date = months + " " + LanguageManager.translate("time_" + ((months == 1L) ? "month" : "months"));
        }
        else if (hours >= 24L) {
            date = days + " " + LanguageManager.translate("time_" + ((days == 1L) ? "day" : "days"));
        }
        else if (mins >= 60L) {
            date = hours + " " + LanguageManager.translate("time_" + ((hours == 1L) ? "hour" : "hours"));
        }
        else if (secs >= 60L) {
            date = mins + " " + LanguageManager.translate("time_" + ((mins == 1L) ? "minute" : "minutes"));
        }
        else {
            date = secs + " " + LanguageManager.translate("time_" + ((secs == 1L) ? "second" : "seconds"));
        }
        date = LanguageManager.translate("time_ago", new Object[] { date }).toLowerCase();
        return date;
    }
    
    public static String getStringByInputStream(final InputStream inputStream) {
        final StringBuilder sb = new StringBuilder();
        try {
            int ch;
            while ((ch = inputStream.read()) != -1) {
                sb.append((char)ch);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static String getMAC() {
        byte[] mac = new byte[6];
        try {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                final NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isVirtual() && networkInterface.isUp()) {
                    final byte[] elementMac = networkInterface.getHardwareAddress();
                    if (elementMac != null) {
                        mac = elementMac;
                        break;
                    }
                    continue;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mac.length; ++i) {
            stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return stringBuilder.toString();
    }
    
    public static class ConvertJsonToObject
    {
        private static Gson gson;
        
        public static final <T> T getFromJSON(final String json, final Class<T> clazz) {
            return (T)ConvertJsonToObject.gson.fromJson(json, (Class)clazz);
        }
        
        public static final <T> String toJSON(final T clazz) {
            return ConvertJsonToObject.gson.toJson((Object)clazz);
        }
        
        static {
            ConvertJsonToObject.gson = new GsonBuilder().setPrettyPrinting().create();
        }
    }
}
