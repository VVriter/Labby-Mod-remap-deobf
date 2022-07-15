//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.gui.elements;

import net.labymod.labyplay.gui.*;
import net.labymod.gui.layout.*;
import java.util.*;
import net.labymod.utils.*;
import net.labymod.gui.elements.*;

public class WinPartyCreator extends WindowElement<GuiPlayLayout>
{
    public WinPartyCreator(final GuiPlayLayout layout) {
        super((WindowLayout)layout);
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        final int margin = 0;
        final int padding = 6;
        final String invitePlayer = "Invite player";
        final int invitePlayerWidth = this.draw.getStringWidth(invitePlayer) + padding * 2;
        buttonlist.add(new bja(1, left + margin, top + 18, invitePlayerWidth, 20, invitePlayer));
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        final int paddingX = 1;
        final int paddingY = 5;
        this.draw.drawString("Create new party", this.left + paddingX, this.top + paddingY);
    }
    
    public void actionPerformed(final bja button) {
        if (button.k == 1) {
            bib.z().a((blk)new GuiTextboxPrompt(bib.z().m, "Player to invite:", "Invite", "Cancel", "", (Consumer)new Consumer<String>() {
                @Override
                public void accept(final String username) {
                    if (!username.isEmpty()) {
                        ((GuiPlayLayout)WinPartyCreator.this.layout).getPartySystem().invitePlayer(username);
                    }
                }
            }));
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        return false;
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
