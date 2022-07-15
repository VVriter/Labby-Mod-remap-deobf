//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.lang;

import net.labymod.support.util.*;
import java.util.*;
import java.nio.charset.*;
import net.labymod.utils.*;
import java.io.*;
import java.util.concurrent.*;

public class LanguageManager
{
    private static Map<String, Language> lang;
    private static Language language;
    private static Language defaultLanguage;
    public static String lastLocaleCode;
    
    public static void updateLang() {
        String mcLanguage;
        final String defaultLanguageCode = mcLanguage = "en_US";
        if (bib.z() != null && bib.z().Q() != null && bib.z().Q().c() != null && bib.z().Q().c().a() != null) {
            mcLanguage = bib.z().Q().c().a();
        }
        Language targetLanguage = null;
        for (final Language lang : LanguageManager.lang.values()) {
            if (mcLanguage.equals(lang.getName())) {
                targetLanguage = lang;
            }
        }
        if (targetLanguage == null) {
            Debug.log(Debug.EnumDebugMode.LANGUAGE, mcLanguage + " is not loaded! Trying to load it..");
            targetLanguage = load(mcLanguage);
        }
        else {
            Debug.log(Debug.EnumDebugMode.LANGUAGE, "Detected Minecraft language: " + mcLanguage);
        }
        if (LanguageManager.defaultLanguage == null) {
            LanguageManager.defaultLanguage = load(defaultLanguageCode);
        }
        if (targetLanguage == null) {
            Debug.log(Debug.EnumDebugMode.LANGUAGE, mcLanguage + " doesn't exists, using default language instead.");
            targetLanguage = LanguageManager.defaultLanguage;
        }
        if (targetLanguage == null) {
            Debug.log(Debug.EnumDebugMode.LANGUAGE, "Using no language!");
        }
        else {
            Debug.log(Debug.EnumDebugMode.LANGUAGE, "Using language " + targetLanguage.getName() + " now.");
        }
        LanguageManager.language = targetLanguage;
        LanguageManager.lastLocaleCode = mcLanguage;
    }
    
    public static Language load(final String name) {
        try {
            final Properties prop = new Properties();
            if (name.contains("_")) {
                final String[] sp = name.split("_");
                final String[] files = { sp[1], name, sp[0] };
                boolean found = false;
                for (final String fileName : files) {
                    final InputStream stream = LanguageManager.class.getResourceAsStream("/assets/minecraft/labymod/lang/" + fileName.toUpperCase() + ".properties");
                    if (stream != null) {
                        final InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
                        prop.load(reader);
                        reader.close();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Debug.log(Debug.EnumDebugMode.LANGUAGE, "Cannot find following language: " + name + " (" + sp[0] + ", " + sp[1] + ")");
                    return null;
                }
            }
            final Language lang = new Language(name);
            for (final Map.Entry<Object, Object> s : prop.entrySet()) {
                lang.translations.put(s.getKey().toString(), ModColor.createColors(s.getValue().toString()));
            }
            LanguageManager.lang.put(name, lang);
            return lang;
        }
        catch (Exception error) {
            error.printStackTrace();
            Debug.log(Debug.EnumDebugMode.LANGUAGE, "Couldn't load language file " + name + " " + error.getMessage());
            return null;
        }
    }
    
    public static String translateString(final String key, final boolean format, final Object... args) {
        if (bib.z() != null && bib.z().Q() != null && bib.z().Q().c() != null && bib.z().Q().c().a() != null && !LanguageManager.lastLocaleCode.equals(bib.z().Q().c().a())) {
            updateLang();
        }
        if (key == null || LanguageManager.language == null) {
            return key;
        }
        String trans = LanguageManager.language.get(key);
        if (trans == null && (LanguageManager.defaultLanguage == null || (trans = LanguageManager.defaultLanguage.get(key)) == null)) {
            return key;
        }
        if (format && args != null) {
            try {
                trans = String.format(trans, args);
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
        return trans;
    }
    
    public static String translateOrReturnKey(final String key, final Object... args) {
        final String returned = translate(key, args);
        return returned.contains("N/A") ? key : returned;
    }
    
    public static String translate(final String key) {
        return translateString(key, false, new Object[0]);
    }
    
    public static String translate(final String key, final Object... args) {
        return translateString(key, true, args);
    }
    
    public static Language getLanguage() {
        return LanguageManager.language;
    }
    
    static {
        LanguageManager.lang = new ConcurrentHashMap<String, Language>();
        LanguageManager.lastLocaleCode = "";
    }
}
