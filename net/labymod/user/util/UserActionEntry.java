//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.util;

import net.labymod.utils.*;
import net.labymod.user.*;
import java.awt.*;
import net.labymod.api.permissions.*;
import net.labymod.core.*;
import net.labymod.main.*;
import net.labymod.ingamechat.*;
import java.awt.datatransfer.*;

public class UserActionEntry
{
    private String displayName;
    private EnumActionType type;
    private String value;
    private ActionExecutor executor;
    
    public UserActionEntry(final String displayName, final EnumActionType type, final String value, final ActionExecutor executor) {
        this.displayName = ModColor.createColors(displayName);
        this.type = type;
        this.value = value;
        this.executor = executor;
    }
    
    public void execute(final User user, final aed entityPlayer, final bsc networkPlayerInfo) {
        if (this.value != null && entityPlayer != null && networkPlayerInfo != null) {
            String string = this.value.replace("{name}", entityPlayer.h_()).replace("{uuid}", entityPlayer.bm().toString());
            switch (this.type) {
                case CLIPBOARD: {
                    final StringSelection stringSelection = new StringSelection(string);
                    final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    break;
                }
                case RUN_COMMAND: {
                    if (!string.startsWith("/")) {
                        string = "/" + string;
                    }
                    if (Permissions.isAllowed(Permissions.Permission.CHAT)) {
                        LabyModCore.getMinecraft().getPlayer().g(string);
                        break;
                    }
                    LabyMod.getInstance().displayMessageInChat(ModColor.cl('c') + "This feature is not allowed on this server!");
                    bib.z().a((blk)new GuiChatCustom(string));
                    break;
                }
                case SUGGEST_COMMAND: {
                    if (!string.startsWith("/")) {
                        string = "/" + string;
                    }
                    bib.z().a((blk)new GuiChatCustom(string));
                    break;
                }
                case OPEN_BROWSER: {
                    LabyMod.getInstance().openWebpage(string, true);
                    break;
                }
            }
        }
        if (this.executor != null) {
            this.executor.execute(user, entityPlayer, networkPlayerInfo);
        }
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public EnumActionType getType() {
        return this.type;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public ActionExecutor getExecutor() {
        return this.executor;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public void setType(final EnumActionType type) {
        this.type = type;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public void setExecutor(final ActionExecutor executor) {
        this.executor = executor;
    }
    
    public enum EnumActionType
    {
        NONE, 
        CLIPBOARD, 
        RUN_COMMAND, 
        SUGGEST_COMMAND, 
        OPEN_BROWSER;
    }
    
    public interface ActionExecutor
    {
        void execute(final User p0, final aed p1, final bsc p2);
        
        boolean canAppear(final User p0, final aed p1, final bsc p2);
    }
}
