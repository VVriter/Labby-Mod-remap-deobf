//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.settings;

import java.io.*;
import java.util.*;

public class OptionsRemapper
{
    public static final OptionsRemapper INSTANCE;
    private File optionsFile;
    private final Map<String, String> configurations;
    
    public OptionsRemapper() {
        this.optionsFile = null;
        this.configurations = new HashMap<String, String>();
    }
    
    public void init() {
        this.optionsFile = new File(bib.z().w, "options.txt");
        this.read();
        if (this.remap()) {
            try {
                final FileWriter fileWriter = new FileWriter(this.optionsFile);
                for (final Map.Entry<String, String> entry : this.configurations.entrySet()) {
                    fileWriter.write(entry.getKey() + ":" + entry.getValue() + "\n");
                }
                fileWriter.flush();
                fileWriter.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void write(final PrintWriter printWriter) {
        if (printWriter instanceof OptionsPrintWriter) {
            ((OptionsPrintWriter)printWriter).write((Map)this.configurations);
        }
    }
    
    private void read() {
        try {
            final Scanner scanner = new Scanner(this.optionsFile);
            while (scanner.hasNextLine()) {
                final String[] parts = scanner.nextLine().split(":");
                if (parts.length == 2) {
                    this.configurations.put(parts[0], parts[1]);
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean remap() {
        boolean optionChanged = false;
        for (final Map.Entry<String, String> entry : this.configurations.entrySet()) {
            if (entry.getKey().startsWith("key")) {
                final int key = KeyBindMappings.getScanCode((String)entry.getValue());
                if (key == -1) {
                    continue;
                }
                this.configurations.put(entry.getKey(), String.valueOf(key));
                optionChanged = true;
            }
        }
        return optionChanged;
    }
    
    static {
        INSTANCE = new OptionsRemapper();
    }
}
