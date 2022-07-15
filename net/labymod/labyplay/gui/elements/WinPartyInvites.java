//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.gui.elements;

import net.labymod.labyplay.gui.*;
import net.labymod.gui.layout.*;
import net.labymod.labyplay.party.model.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import java.util.*;

public class WinPartyInvites extends WindowElement<GuiPlayLayout>
{
    private UUID hoverParty;
    private boolean hoverAccept;
    private boolean hoverDeny;
    
    public WinPartyInvites(final GuiPlayLayout layout) {
        super((WindowLayout)layout);
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        final int padding = 5;
        final int posX = this.left + padding;
        int posY = this.top + padding + 15;
        this.draw.drawString("Party invites", this.left + padding, this.top + padding);
        this.hoverParty = null;
        this.hoverAccept = false;
        this.hoverDeny = false;
        for (final PartyInvite party : ((GuiPlayLayout)this.layout).getPartySystem().getPartyInvites()) {
            LabyMod.getInstance().getDrawUtils().drawPlayerHead(party.getUsername(), posX, posY, 18);
            this.draw.drawString(ModColor.cl('a') + party.getUsername(), posX + 18 + 2, posY + 1);
            this.draw.drawString(ModColor.cl('e') + "invites you", posX + 18 + 2, posY + 10, 0.7);
            final int iconSize = 13;
            final boolean hoverDeny = mouseX > this.right - 20 && mouseX < this.right - 20 + iconSize && mouseY > posY + 1 && mouseY < posY + 1 + iconSize;
            final boolean hoverAccept = mouseX > this.right - 40 && mouseX < this.right - 40 + iconSize && mouseY > posY + 1 && mouseY < posY + 1 + iconSize;
            this.hoverDeny = hoverDeny;
            this.hoverAccept = hoverAccept;
            this.hoverParty = party.getPartyUUID();
            bib.z().N().a(ModTextures.BUTTON_DENY);
            this.draw.drawTexture(this.right - 20 - (hoverDeny ? 1 : 0), posY + 1 - (hoverDeny ? 1 : 0), 255.0, 255.0, iconSize + (hoverDeny ? 2 : 0), iconSize + (hoverDeny ? 2 : 0), 1.0f);
            bib.z().N().a(ModTextures.BUTTON_ACCEPT);
            this.draw.drawTexture(this.right - 40 - (hoverAccept ? 1 : 0), posY + 1 - (hoverAccept ? 1 : 0), 255.0, 255.0, iconSize + (hoverAccept ? 2 : 0), iconSize + (hoverAccept ? 2 : 0), 1.0f);
            posY += 20;
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.hoverAccept && this.hoverParty != null) {
            ((GuiPlayLayout)this.layout).getPartySystem().sendInvitePlayerResponse(this.hoverParty, true);
        }
        if (this.hoverDeny && this.hoverParty != null) {
            ((GuiPlayLayout)this.layout).getPartySystem().sendInvitePlayerResponse(this.hoverParty, false);
        }
        return false;
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public void actionPerformed(final bja button) {
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void updateScreen() {
    }
}
