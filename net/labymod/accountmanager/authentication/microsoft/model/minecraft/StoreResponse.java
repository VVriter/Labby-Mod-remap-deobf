//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.authentication.microsoft.model.minecraft;

public class StoreResponse
{
    public String keyId;
    public String signature;
    public Item[] items;
    
    public boolean hasItem(final String name) {
        for (final Item item : this.items) {
            if (item.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public static class Item
    {
        public String name;
        public String signature;
    }
}
