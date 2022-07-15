//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.renderer;

import net.labymod.ingamechat.tools.filter.*;
import java.beans.*;

public class MessageData
{
    private boolean displayInSecondChat;
    private Filters.Filter filter;
    
    public boolean isDisplayInSecondChat() {
        return this.displayInSecondChat;
    }
    
    public Filters.Filter getFilter() {
        return this.filter;
    }
    
    @ConstructorProperties({ "displayInSecondChat", "filter" })
    public MessageData(final boolean displayInSecondChat, final Filters.Filter filter) {
        this.displayInSecondChat = displayInSecondChat;
        this.filter = filter;
    }
}
