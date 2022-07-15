//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.labymod.main.*;
import net.labymod.labyplay.party.model.*;
import net.labymod.utils.*;
import net.labymod.gui.*;
import net.labymod.labyplay.gui.*;
import net.labymod.main.lang.*;

public class MultiplayerTabs
{
    private static int hoverMultiplayerTab;
    private static int lastOpenTab;
    
    public static void initMultiplayerTabs(final int tabIndex) {
        if (MultiplayerTabs.lastOpenTab != -1 && tabIndex != MultiplayerTabs.lastOpenTab) {
            openMultiPlayerTab(MultiplayerTabs.lastOpenTab);
        }
    }
    
    public static void drawParty(final int mouseX, final int mouseY, final int width) {
        final int headSize = 10;
        int posX = width - headSize - 2;
        final int posY = 3;
        for (final PartyMember member : LabyMod.getInstance().getLabyPlay().getPartySystem().getMembers()) {
            final boolean hover = mouseX > posX && mouseX < posX + headSize && mouseY > posY && mouseY < posY + headSize;
            LabyMod.getInstance().getDrawUtils().drawPlayerHead(member.getUuid(), posX - (hover ? 1 : 0), posY - (hover ? 1 : 0), headSize + (hover ? 2 : 0));
            if (hover) {
                LabyMod.getInstance().getDrawUtils().drawRightString(member.getName(), width - 3, 16.0);
            }
            posX -= headSize + 2;
        }
    }
    
    public static void drawMultiplayerTabs(final int tabIndex, final int mouseX, final int mouseY, final boolean isScrolled, final boolean isIndex0Selected) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        bus.g();
        MultiplayerTabs.hoverMultiplayerTab = -1;
        final int space = 3;
        final int paddingWidth = 6;
        int posX = (draw.getWidth() + space) / 2;
        final int tabY = 28;
        final int tabHeight = 12;
        int index = 0;
        for (final TabType tab : TabType.values()) {
            if (tab.isVisible()) {
                posX -= (draw.getStringWidth(tab.getDisplayName()) + space + paddingWidth) / 2;
            }
            ++index;
        }
        index = 0;
        for (final TabType tab : TabType.values()) {
            if (tab.isVisible()) {
                final String displayString = tab.getDisplayName();
                final int tabWidth = draw.getStringWidth(displayString) + paddingWidth;
                final boolean hover = mouseX > posX && mouseX < posX + tabWidth && mouseY > tabY && mouseY < tabY + tabHeight;
                draw.drawRectangle(posX, tabY, posX + tabWidth, tabY + tabHeight, Integer.MIN_VALUE);
                if (tabIndex == index) {
                    draw.drawOverlayBackground(posX, tabY, tabWidth, tabY + tabHeight + 2 + ((isIndex0Selected || !isScrolled) ? 0 : 3), 32);
                    draw.drawGradientShadowTop(tabY, posX, posX + tabWidth);
                    MultiplayerTabs.lastOpenTab = index;
                }
                else {
                    draw.drawRectangle(posX, tabY + tabHeight, posX + tabWidth, tabY + tabHeight + 1, ModColor.toRGB(100, 100, 100, 60));
                }
                draw.drawCenteredString(ModColor.cl((tabIndex == index) ? "f" : (hover ? "7" : "8")) + displayString, posX + tabWidth / 2, tabY + 2);
                if (hover) {
                    MultiplayerTabs.hoverMultiplayerTab = index;
                }
                posX += tabWidth + space;
            }
            ++index;
        }
    }
    
    public static void mouseClickedMultiplayerTabs(final int index, final int mouseX, final int mouseY) {
        if (index == MultiplayerTabs.hoverMultiplayerTab || MultiplayerTabs.hoverMultiplayerTab == -1) {
            return;
        }
        openMultiPlayerTab(MultiplayerTabs.hoverMultiplayerTab);
    }
    
    private static void openMultiPlayerTab(final int index) {
        blk lastScreen = bib.z().m;
        if (lastScreen instanceof ModGuiMultiplayer) {
            lastScreen = ((ModGuiMultiplayer)lastScreen).getParentScreen();
        }
        if (lastScreen instanceof GuiServerList) {
            lastScreen = ((GuiServerList)lastScreen).getParentScreen();
        }
        if (lastScreen instanceof GuiPlayLayout) {
            lastScreen = ((GuiPlayLayout)lastScreen).getParentScreen();
        }
        switch (MultiplayerTabs.lastOpenTab = index) {
            case 0: {
                bib.z().a((blk)new bnf(lastScreen));
                break;
            }
            case 1: {
                bib.z().a((blk)new GuiServerList(lastScreen));
                break;
            }
            case 2: {
                bib.z().a((blk)new GuiPlayLayout(lastScreen));
                break;
            }
        }
    }
    
    static {
        MultiplayerTabs.hoverMultiplayerTab = -1;
        MultiplayerTabs.lastOpenTab = -1;
    }
    
    private enum TabType
    {
        MY_SERVER_LIST("tab_my_server_list"), 
        PUBLIC_SERVER_LIST("tab_public_server_list");
        
        private String langKey;
        
        public String getDisplayName() {
            return LanguageManager.translate(this.langKey);
        }
        
        public boolean isVisible() {
            switch (this.ordinal()) {
                case 0: {
                    return true;
                }
                case 1: {
                    return LabyMod.getSettings().publicServerList;
                }
                case 2: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        public String getLangKey() {
            return this.langKey;
        }
        
        private TabType(final String langKey) {
            this.langKey = langKey;
        }
    }
}
