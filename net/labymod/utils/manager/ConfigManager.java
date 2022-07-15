//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import com.google.common.base.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import net.labymod.support.util.*;
import java.io.*;
import com.google.gson.*;

public class ConfigManager<T>
{
    public static final Gson GSON;
    private File file;
    private Class<? extends T> configDefaults;
    private T settings;
    
    public ConfigManager(final File file, final Class<? extends T> configDefaults) {
        this.file = (File)Preconditions.checkNotNull((Object)file);
        this.configDefaults = (Class<? extends T>)Preconditions.checkNotNull((Object)configDefaults);
        this.loadConfig(false);
    }
    
    private void loadConfig(final boolean reload) {
        if (!this.file.getParentFile().exists()) {
            this.file.getParentFile().mkdir();
        }
        boolean createdNewFile = false;
        if (reload && this.file.exists()) {
            createdNewFile = true;
        }
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                createdNewFile = true;
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(this.file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.settings = (T)ConfigManager.GSON.fromJson(createdNewFile ? ConfigManager.GSON.toJson((Object)this.configDefaults.newInstance()) : IOUtils.toString((InputStream)stream, StandardCharsets.UTF_8), (Class)this.configDefaults);
            Debug.log(Debug.EnumDebugMode.CONFIG_MANAGER, (this.settings != null) ? ("Loaded " + this.file.getName() + "!") : "Loaded file but settings is null");
            if (!reload && this.settings == null) {
                this.loadConfig(true);
            }
            else if (this.settings != null) {
                this.save();
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
            Debug.log(Debug.EnumDebugMode.CONFIG_MANAGER, "Failed to load " + this.file.getName() + " config!");
            if (!reload) {
                this.loadConfig(true);
            }
            try {
                stream.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }
    
    public void save() {
        try {
            final PrintWriter w = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file), StandardCharsets.UTF_8), true);
            w.print(ConfigManager.GSON.toJson((Object)this.settings));
            w.flush();
            w.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public T getSettings() {
        return this.settings;
    }
    
    public File getFile() {
        return this.file;
    }
    
    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}
