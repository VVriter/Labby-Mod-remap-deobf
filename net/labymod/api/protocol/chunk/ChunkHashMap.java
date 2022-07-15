//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.chunk;

import java.util.*;

public class ChunkHashMap<K, V> extends HashMap<K, V>
{
    private List<K> sortedList;
    
    public ChunkHashMap() {
        this.sortedList = new LinkedList<K>();
    }
    
    @Override
    public V put(final K key, final V value) {
        if (this.sortedList.contains(key)) {
            this.sortedList.remove(key);
        }
        this.sortedList.add(key);
        return super.put(key, value);
    }
    
    @Override
    public V remove(final Object key) {
        this.sortedList.remove(key);
        return super.remove(key);
    }
    
    public K getEldestEntry() {
        return this.sortedList.get(0);
    }
    
    public V removeEldestEntry() {
        return this.remove(this.sortedList.get(0));
    }
    
    public void renewEntry(final K key) {
        if (this.containsKey(key)) {
            this.put(key, this.remove(key));
        }
    }
    
    @Override
    public void clear() {
        this.sortedList.clear();
        super.clear();
    }
}
