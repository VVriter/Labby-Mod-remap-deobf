//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.mojang.settings;

import java.io.*;
import java.util.*;

public class OptionsPrintWriter extends PrintWriter
{
    private final Map<String, String> configurations;
    
    public OptionsPrintWriter(final Writer writer) {
        super(writer);
        this.configurations = new HashMap<String, String>();
    }
    
    @Override
    public void println(final String line) {
        super.println(line);
        final String[] parts = line.split(":");
        if (parts.length == 2) {
            this.configurations.put(parts[0], parts[1]);
        }
    }
    
    public void write(final Map<String, String> configurations) {
        for (final Map.Entry<String, String> entry : configurations.entrySet()) {
            if (!this.configurations.containsKey(entry.getKey())) {
                super.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }
}
