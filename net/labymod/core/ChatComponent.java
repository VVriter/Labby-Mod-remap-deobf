//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core;

public class ChatComponent
{
    private String unformattedText;
    private String formattedText;
    private String json;
    
    public ChatComponent(final String unformattedText, final String formattedText) {
        this(unformattedText, formattedText, null);
    }
    
    public ChatComponent(final String unformattedText, final String formattedText, final String json) {
        this.unformattedText = unformattedText;
        this.formattedText = formattedText;
        this.json = json;
    }
    
    public String getUnformattedText() {
        return this.unformattedText;
    }
    
    public String getFormattedText() {
        return this.formattedText;
    }
    
    public String getJson() {
        return this.json;
    }
}
