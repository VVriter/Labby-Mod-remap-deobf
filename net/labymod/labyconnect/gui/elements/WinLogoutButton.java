//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.gui.*;
import net.labymod.gui.layout.*;
import java.util.*;
import net.labymod.main.lang.*;
import net.labymod.main.*;

public class WinLogoutButton extends WindowElement<GuiFriendsLayout>
{
    public WinLogoutButton(final GuiFriendsLayout chatLayout) {
        super((WindowLayout)chatLayout);
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        final int paddingWidth = 0;
        final int dragLineWidth = 2;
        buttonlist.add(new bja(4, left + paddingWidth, top + (bottom - top - 20) / 2 + 1, right - left - paddingWidth * 2 - dragLineWidth, 20, LanguageManager.translate("button_logout")));
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        return false;
    }
    
    public void actionPerformed(final bja button) {
        if (button.k == 4) {
            LabyMod.getInstance().getLabyConnect().setForcedLogout(true);
            LabyMod.getInstance().getLabyConnect().getClientConnection().disconnect(false);
            ((GuiFriendsLayout)this.layout).b();
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void updateScreen() {
    }
}
