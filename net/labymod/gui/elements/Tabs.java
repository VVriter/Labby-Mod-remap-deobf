//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.labymod.gui.*;
import net.labymod.labyplay.gui.*;
import net.labymod.labyconnect.gui.*;
import net.labymod.settings.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.labyconnect.user.*;
import net.labymod.utils.*;
import net.labymod.core.*;
import net.labymod.gui.account.*;
import java.util.*;

public class Tabs
{
    private static Map<String, Class<? extends blk>[]> guiMap;
    private static Map<String, nf> iconMap;
    public static String lastOpenScreen;
    private static List<Consumer<Map<String, Class<? extends blk>[]>>> tabUpdateListener;
    private static Class<? extends blk> lastInitializedScreen;
    private static List<bja> buttonList;
    private static bja hoverButton;
    private static final bja BUTTON_ARROW_RIGHT;
    private static final DropDownMenu<bja> DROPDOWN;
    
    public static void registerTab(final String title, final Class<? extends blk> screen) {
        registerTab(title, null, screen);
    }
    
    public static void registerTab(final String title, final nf icon, final Class<? extends blk> screen) {
        Tabs.tabUpdateListener.add(new Consumer<Map<String, Class<? extends blk>[]>>() {
            @Override
            public void accept(final Map<String, Class<? extends blk>[]> accepted) {
                Tabs.guiMap.put(title, new Class[] { screen });
                if (icon != null && LabyMod.getSettings().tabIcons) {
                    Tabs.iconMap.put(title, icon);
                }
            }
        });
    }
    
    private static void init(final blk screen) {
        Tabs.buttonList.clear();
        Tabs.guiMap.clear();
        Tabs.iconMap.clear();
        if (LabyMod.getInstance().isInGame()) {
            Tabs.guiMap.put("tab_menu", new Class[] { ModGuiIngameMenu.class });
        }
        if (LabyMod.getSettings().multiplayerIngame || !LabyMod.getInstance().isInGame()) {
            Tabs.guiMap.put("tab_multiplayer", new Class[] { ModGuiMultiplayer.class, GuiServerList.class, GuiPlayLayout.class });
        }
        Tabs.guiMap.put("tab_chat", new Class[] { GuiFriendsLayout.class, GuiFriendsNotConnected.class });
        if (LabyMod.getSettings().labymodSettingsInTabs) {
            Tabs.guiMap.put("tab_labymod", new Class[] { LabyModSettingsGui.class });
            Tabs.guiMap.put("tab_gui", new Class[] { LabyModModuleEditorGui.class });
            Tabs.guiMap.put("tab_addons", new Class[] { LabyModAddonsGui.class });
        }
        if (LabyMod.getSettings().tabIcons) {
            Tabs.iconMap.put("tab_multiplayer", ModTextures.TAB_MULTIPLAYER);
            Tabs.iconMap.put("tab_labymod", ModTextures.LOGO_LABYMOD_LOGO);
            Tabs.iconMap.put("tab_gui", ModTextures.TAB_GUI);
            Tabs.iconMap.put("tab_addons", ModTextures.TAB_ADDONS);
            Tabs.iconMap.put("tab_chat", ModTextures.TAB_CHAT);
        }
        for (final Consumer<Map<String, Class<? extends blk>[]>> consumer : Tabs.tabUpdateListener) {
            consumer.accept(Tabs.guiMap);
        }
        int positionX = 0;
        int index = 0;
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        for (final Map.Entry<String, Class<? extends blk>[]> guiEntry : Tabs.guiMap.entrySet()) {
            String displayString = LanguageManager.translate(guiEntry.getKey());
            boolean isSelected = false;
            for (final Class<? extends blk> guiClass : guiEntry.getValue()) {
                if (screen != null) {
                    final Class<? extends blk> screenClass = screen.getClass();
                    if (screenClass.isAssignableFrom(guiClass)) {
                        isSelected = true;
                        break;
                    }
                }
            }
            if (!isSelected && guiEntry.getKey().equals("tab_chat")) {
                int count = 0;
                for (final ChatUser chatUser : LabyMod.getInstance().getLabyConnect().getFriends()) {
                    count += chatUser.getUnreadMessages();
                }
                if (count != 0) {
                    displayString = displayString + ModColor.cl('c') + " (" + count + ")";
                }
            }
            final nf icon = Tabs.iconMap.get(guiEntry.getKey());
            final int nameWidth = draw.getStringWidth(displayString) + ((icon == null) ? 0 : 15);
            final bja button = (bja)new TabbedGuiButton(icon, 100 + index, 5 + positionX, 5, 10 + nameWidth, 20, displayString);
            button.l = !isSelected;
            Tabs.buttonList.add(button);
            if (Tabs.lastOpenScreen != null && Tabs.lastOpenScreen.equals(guiEntry.getKey()) && !isSelected) {
                actionPerformed(button);
                break;
            }
            positionX += nameWidth + 12;
            ++index;
        }
        if (!LabyMod.getInstance().getLabyPlay().getPartySystem().hasParty() && screen != null) {
            final String displayString2 = LanguageManager.translate("tab_account");
            final int nameWidth2 = draw.getStringWidth(displayString2) + 15;
            double screenWidth = screen.l;
            if (screen instanceof LabyModModuleEditorGui) {
                final bit scaled = new bit(bib.z());
                final double rescale = scaled.e() / LabyMod.getInstance().getDrawUtils().getCustomScaling();
                screenWidth = screen.l / rescale;
            }
            final TabbedGuiButton button2 = new TabbedGuiButton(ModTextures.MISC_HEAD_QUESTION, 200, (int)(screenWidth - nameWidth2 - 12.0), 5, 10 + nameWidth2, 20, displayString2);
            button2.setRightBound(true);
            button2.setIconRenderCallback((TabbedGuiButton.IconRenderCallback)new TabbedGuiButton.IconRenderCallback() {
                public void render(final int x, final int y, final int iconSize) {
                    if (bib.z().K() != null && bib.z().K().e() != null) {
                        draw.drawPlayerHead(bib.z().K().e(), x, y, iconSize);
                    }
                    else {
                        draw.drawTexture(x, y, 255.0, 255.0, iconSize, iconSize);
                    }
                }
            });
            Tabs.buttonList.add((bja)button2);
        }
    }
    
    public static void initGui(final blk screen) {
        init(screen);
    }
    
    public static void drawScreen(final blk screen, final int mouseX, final int mouseY) {
        bus.G();
        if (screen instanceof LabyModModuleEditorGui) {
            final bit scaled = LabyMod.getInstance().getDrawUtils().getScaledResolution();
            final double rescale = scaled.e() / LabyMod.getInstance().getDrawUtils().getCustomScaling();
            bus.a(rescale, rescale, 1.0);
        }
        drawScreen(0, 0, mouseX, mouseY);
        bus.H();
    }
    
    public static void drawScreen(final int x, final int y, final int mouseX, final int mouseY) {
        if (Tabs.lastInitializedScreen == null) {
            Tabs.lastInitializedScreen = bib.z().m.getClass();
            init(bib.z().m);
        }
        bus.G();
        bus.c((float)x, (float)y, 0.0f);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        int rightBoundWidth = 30;
        for (final bja button : Tabs.buttonList) {
            if (button instanceof TabbedGuiButton && ((TabbedGuiButton)button).isRightBound()) {
                rightBoundWidth += button.b();
            }
        }
        final boolean prevOpen = Tabs.DROPDOWN.isOpen();
        Tabs.hoverButton = null;
        Tabs.DROPDOWN.clear();
        Tabs.DROPDOWN.setOpen(prevOpen);
        double rescale = 1.0;
        if (bib.z().m != null && bib.z().m instanceof LabyModModuleEditorGui) {
            final bit scaled = LabyMod.getInstance().getDrawUtils().getScaledResolution();
            rescale = scaled.e() / LabyMod.getInstance().getDrawUtils().getCustomScaling();
        }
        int lastLeftBoundButtonX = -1;
        int maxDropdownWidth = 0;
        for (final bja button2 : Tabs.buttonList) {
            if (!(button2 instanceof TabbedGuiButton) || !((TabbedGuiButton)button2).isRightBound()) {
                final int buttonX = LabyModCore.getMinecraft().getXPosition(button2);
                if (buttonX + button2.b() > draw.getWidth() / rescale - rightBoundWidth) {
                    if (lastLeftBoundButtonX == -1) {
                        lastLeftBoundButtonX = buttonX;
                    }
                    Tabs.DROPDOWN.addOption((Object)button2);
                    maxDropdownWidth = Math.max(button2.b(), maxDropdownWidth);
                    continue;
                }
            }
            LabyModCore.getMinecraft().drawButton(button2, mouseX, mouseY);
            if (button2.a()) {
                Tabs.hoverButton = button2;
            }
        }
        if (lastLeftBoundButtonX != -1) {
            LabyModCore.getMinecraft().setButtonXPosition(Tabs.BUTTON_ARROW_RIGHT, lastLeftBoundButtonX);
            LabyModCore.getMinecraft().drawButton(Tabs.BUTTON_ARROW_RIGHT, mouseX, mouseY);
            if (Tabs.BUTTON_ARROW_RIGHT.a()) {
                Tabs.hoverButton = Tabs.BUTTON_ARROW_RIGHT;
            }
            if (Tabs.DROPDOWN.isOpen()) {
                Tabs.DROPDOWN.setWidth(maxDropdownWidth);
                final double scaledMouseX = mouseX / rescale;
                final double scaledMouseY = mouseY / rescale;
                bus.k();
                bus.a(true);
                bus.c(0.0f, 0.0f, 1.0f);
                Tabs.DROPDOWN.drawMenuDirect(lastLeftBoundButtonX + 1, 4, (int)scaledMouseX, (int)scaledMouseY);
                bus.c(0.0f, 0.0f, -1.0f);
            }
        }
        bus.H();
        LabyMod.getInstance().getGuiCustomAchievement().updateAchievementWindow();
    }
    
    public static boolean mouseClicked(final blk screen) {
        if (Tabs.hoverButton != null) {
            actionPerformed(Tabs.hoverButton);
        }
        else if (Tabs.DROPDOWN.isOpen()) {
            Tabs.DROPDOWN.setOpen(false);
            if (Tabs.DROPDOWN.getHoverSelected() != null) {
                actionPerformed((bja)Tabs.DROPDOWN.getHoverSelected());
                return true;
            }
        }
        return false;
    }
    
    private static void actionPerformed(final bja button) {
        int index = 0;
        for (final Map.Entry<String, Class<? extends blk>[]> guiEntry : Tabs.guiMap.entrySet()) {
            if (button.k == 100 + index) {
                final blk screen = getGuiScreenByClass(guiEntry.getValue());
                if (screen == null) {
                    break;
                }
                if (LabyMod.getInstance().isInGame()) {
                    Tabs.lastOpenScreen = guiEntry.getKey();
                }
                else {
                    Tabs.lastOpenScreen = null;
                }
                bib.z().a(screen);
                break;
            }
            else {
                ++index;
            }
        }
        if (button.k == 200) {
            bib.z().a((blk)new GuiAccountManager(bib.z().m));
        }
        if (button.k == Tabs.BUTTON_ARROW_RIGHT.k) {
            Tabs.DROPDOWN.setOpen(!Tabs.DROPDOWN.isOpen());
        }
        else {
            Tabs.DROPDOWN.setOpen(false);
        }
    }
    
    private static blk getGuiScreenByClass(final Class<? extends blk>[] guiScreenClasses) {
        for (final Class<? extends blk> guiClass : guiScreenClasses) {
            if (bnf.class.isAssignableFrom(guiClass)) {
                return (blk)new ModGuiMultiplayer((blk)(LabyMod.getInstance().isInGame() ? new blg() : new blr()));
            }
        }
        try {
            return (blk)guiScreenClasses[0].newInstance();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return null;
    }
    
    @Deprecated
    public static void initGuiScreen(final List<bja> list, final blk screen) {
        init(screen);
        list.addAll(Tabs.buttonList);
    }
    
    @Deprecated
    public static void drawParty(final int mouseX, final int mouseY, final int width) {
        MultiplayerTabs.drawParty(mouseX, mouseY, width);
    }
    
    @Deprecated
    public static void actionPerformedButton(final bja button) {
        actionPerformed(button);
    }
    
    @Deprecated
    public static Map<String, Class<? extends blk>[]> getGuiMap() {
        return Tabs.guiMap;
    }
    
    @Deprecated
    public static List<Consumer<Map<String, Class<? extends blk>[]>>> getTabUpdateListener() {
        return Tabs.tabUpdateListener;
    }
    
    static {
        Tabs.guiMap = new LinkedHashMap<String, Class<? extends blk>[]>();
        Tabs.iconMap = new LinkedHashMap<String, nf>();
        Tabs.lastOpenScreen = null;
        Tabs.tabUpdateListener = new ArrayList<Consumer<Map<String, Class<? extends blk>[]>>>();
        Tabs.buttonList = new ArrayList<bja>();
        BUTTON_ARROW_RIGHT = (bja)new TabbedGuiButton(1337, 0, 5, 20, 20, "...");
        (DROPDOWN = new DropDownMenu("", 0, 5, 100, 20)).setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                LabyMod.getInstance().getDrawUtils().drawString(((bja)object).j, x, y);
            }
        });
    }
}
