//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.sticker.data;

import java.beans.*;

public class Sticker
{
    private short id;
    private String name;
    private String[] tags;
    
    public short getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getTags() {
        return this.tags;
    }
    
    @ConstructorProperties({ "id", "name", "tags" })
    public Sticker(final short id, final String name, final String[] tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }
}
