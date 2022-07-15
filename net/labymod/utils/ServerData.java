//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

public class ServerData
{
    private String ip;
    private int port;
    private int index;
    private boolean partner;
    
    public ServerData(final String ip, final int port) {
        this(ip, port, false);
    }
    
    public ServerData(final String ip, final int port, final boolean partner) {
        this.port = 0;
        this.index = 0;
        this.ip = ip;
        this.port = port;
        this.partner = partner;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public boolean isPartner() {
        return this.partner;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public void setPartner(final boolean partner) {
        this.partner = partner;
    }
}
