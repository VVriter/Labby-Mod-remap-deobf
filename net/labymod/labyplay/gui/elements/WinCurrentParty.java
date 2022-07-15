//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyplay.gui.elements;

import net.labymod.labyplay.gui.*;
import net.labymod.labyplay.party.model.*;
import net.labymod.gui.layout.*;
import net.labymod.main.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;
import net.labymod.gui.elements.*;
import java.util.*;

public class WinCurrentParty extends WindowElement<GuiPlayLayout>
{
    private PartyMember hoverMember;
    private PartyMember dropDownMember;
    private SmallDropDownMenu dropDown;
    
    public WinCurrentParty(final GuiPlayLayout layout) {
        super((WindowLayout)layout);
        this.hoverMember = null;
        this.dropDownMember = null;
        this.dropDown = null;
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        final int padding = 6;
        final int space = 2;
        final String invitePlayer = "Invite player";
        final int invitePlayerWidth = this.draw.getStringWidth(invitePlayer) + padding;
        buttonlist.add(new bja(1, left, bottom - 20, invitePlayerWidth, 20, invitePlayer));
        final String leave = "Leave";
        final int leaveWidth = this.draw.getStringWidth(leave) + padding;
        buttonlist.add(new bja(2, left + invitePlayerWidth + space, bottom - 20, leaveWidth, 20, leave));
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        final int paddingX = 2;
        final int paddingY = 4;
        int posX = this.left + paddingX;
        final int posY = this.top + paddingY + 14;
        final int headSize = 18;
        final int space = 3;
        this.draw.drawString("Party", this.left + paddingX, this.top + paddingY);
        this.hoverMember = null;
        for (final PartyMember member : ((GuiPlayLayout)this.layout).getPartySystem().getMembers()) {
            if (this.drawMember(member, posX, posY, mouseX, mouseY, headSize)) {
                posX += headSize + space;
            }
        }
        if (this.dropDown != null) {
            this.dropDown.renderButton(mouseX, mouseY);
        }
    }
    
    private boolean drawMember(final PartyMember member, final int posX, final int posY, final int mouseX, final int mouseY, final int headSize) {
        final boolean hover = mouseX > posX && mouseX < posX + headSize && mouseY > posY && mouseY < posY + headSize;
        LabyMod.getInstance().getDrawUtils().drawPlayerHead(member.getUuid(), posX, posY, headSize);
        if (!member.isMember()) {
            final long inviteAliveDuration = System.currentTimeMillis() - member.getTimestamp();
            if (inviteAliveDuration > 60000L) {
                return false;
            }
            final double progress = headSize / 60000.0 * (60000L - inviteAliveDuration);
            DrawUtils.a(posX, posY, posX + headSize, posY + headSize, ModColor.toRGB(0, 0, 0, 200));
            DrawUtils.a(posX, posY + headSize - 1, posX + headSize, posY + headSize, ModColor.toRGB(0, 0, 0, 200));
            this.draw.drawRect(posX, posY + headSize - 1, posX + progress, posY + headSize, ModColor.toRGB(100, 200, 100, 200));
        }
        if (member.isOwner()) {
            final int crownSize = 10;
            bib.z().N().a(ModTextures.MISC_CROWN);
            this.draw.drawTexture(posX, posY - crownSize / 2 - 1, 255.0, 255.0, crownSize, crownSize);
        }
        if (hover && this.dropDown == null) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, member.getName());
            this.hoverMember = member;
        }
        return true;
    }
    
    public void actionPerformed(final bja button) {
        if (button.k == 1) {
            bib.z().a((blk)new GuiTextboxPrompt(bib.z().m, "Player to invite:", "Invite", "Cancel", "", (Consumer)new Consumer<String>() {
                @Override
                public void accept(final String username) {
                    if (!username.isEmpty()) {
                        ((GuiPlayLayout)WinCurrentParty.this.layout).getPartySystem().invitePlayer(username);
                    }
                }
            }));
        }
        if (button.k == 2) {
            ((GuiPlayLayout)this.layout).getPartySystem().leaveParty();
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.dropDown != null && this.dropDownMember != null) {
            final int result = this.dropDown.onClick(mouseX, mouseY);
            if (((GuiPlayLayout)this.layout).getPartySystem().hasParty()) {
                final UUID targetUUID = this.dropDownMember.getUuid();
                switch (result) {
                    case 0: {
                        ((GuiPlayLayout)this.layout).getPartySystem().kickPlayer(targetUUID);
                        break;
                    }
                    case 1: {
                        ((GuiPlayLayout)this.layout).getPartySystem().changeOwner(targetUUID);
                        break;
                    }
                }
            }
            this.dropDown = null;
            this.dropDownMember = null;
        }
        if (this.hoverMember != null && this.dropDown == null && mouseButton == 1) {
            final PartyMember clientMember = ((GuiPlayLayout)this.layout).getPartySystem().getClientMember();
            final boolean isPartyOwner = clientMember.isOwner();
            final boolean isClientMember = this.hoverMember.getUuid().equals(clientMember.getUuid());
            if (isPartyOwner && !isClientMember) {
                (this.dropDown = new SmallDropDownMenu(mouseX, mouseY, 0, 0)).addDropDownEntry(ModColor.RED + "Kick " + this.hoverMember.getName());
                this.dropDown.addDropDownEntry(ModColor.AQUA + "Make party leader");
                this.dropDown.setMinecraftStyle(false);
                this.dropDown.setOpen(true);
                this.dropDown.setDropDownX(mouseX);
                this.dropDown.setDropDownY(mouseY - this.dropDown.getMaxY());
                this.dropDownMember = this.hoverMember;
            }
        }
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
