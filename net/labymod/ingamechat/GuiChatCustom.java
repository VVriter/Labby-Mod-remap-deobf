//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat;

import net.labymod.api.permissions.*;
import net.labymod.ingamegui.*;
import net.labymod.main.*;
import net.labymod.settings.elements.*;
import java.io.*;
import net.labymod.utils.manager.*;
import net.labymod.main.lang.*;
import net.labymod.ingamechat.tabs.*;
import net.labymod.settings.*;
import net.labymod.ingamechat.renderer.*;
import net.labymod.core.*;
import org.lwjgl.input.*;
import net.labymod.ingamechat.namehistory.*;
import net.labymod.utils.*;
import net.labymod.ingamechat.tools.shortcuts.*;
import net.labymod.api.events.*;
import java.util.*;
import net.labymod.ingamegui.enums.*;

public class GuiChatCustom extends bkn
{
    private static final ModuleGui moduleGui;
    public static int activeTab;
    private ChatButton[] chatButtons;
    private String defaultText;
    private IngameChatManager ingameChatManager;
    
    public GuiChatCustom(final String defaultText) {
        super(defaultText);
        this.ingameChatManager = IngameChatManager.INSTANCE;
        this.defaultText = defaultText;
    }
    
    public GuiChatCustom() {
        this.ingameChatManager = IngameChatManager.INSTANCE;
    }
    
    public static ModuleGui getModuleGui() {
        return GuiChatCustom.moduleGui;
    }
    
    public void b() {
        super.b();
        if (this.j.m != null && this.j.m.getClass() == GuiChatCustom.class) {
            GuiChatCustom.activeTab = -1;
        }
        final boolean chatFeaturesAllowed = LabyMod.getInstance().getServerManager().isAllowed(Permissions.Permission.CHAT);
        Module.setCurrentModuleGui(GuiChatCustom.moduleGui);
        GuiChatCustom.moduleGui.b();
        final List<ChatButton> chatButtonList = new ArrayList<ChatButton>();
        if (LabyMod.getSettings().chatSymbols) {
            chatButtonList.add(new ChatButton(0, "symbols", new ControlElement.IconData(ModTextures.CHAT_TAB_SYMBOLS), chatFeaturesAllowed));
        }
        if (LabyMod.getSettings().autoText) {
            chatButtonList.add(new ChatButton(1, "autotext", new ControlElement.IconData(ModTextures.CHAT_TAB_AUTOTEXT), chatFeaturesAllowed));
        }
        if (LabyMod.getSettings().chatShortcuts) {
            chatButtonList.add(new ChatButton(2, "shortcut", new ControlElement.IconData(ModTextures.CHAT_TAB_SHORTCUT), chatFeaturesAllowed));
        }
        if (LabyMod.getSettings().playerMenu && LabyMod.getSettings().playerMenuEditor) {
            chatButtonList.add(new ChatButton(3, "playermenu", new ControlElement.IconData(ModTextures.CHAT_TAB_PLAYERMENU), chatFeaturesAllowed));
        }
        if (LabyMod.getSettings().chatFilter) {
            chatButtonList.add(new ChatButton(4, "filter", new ControlElement.IconData(ModTextures.CHAT_TAB_FILTER), true));
        }
        if (LabyMod.getSettings().nameHistory) {
            chatButtonList.add(new ChatButton(5, "namehistory", new ControlElement.IconData(Material.BOOK_AND_QUILL), true));
        }
        if (LabyMod.getSettings().showModuleEditorShortcut) {
            chatButtonList.add(new ChatButton(6, "module_editor", new ControlElement.IconData(ModTextures.CHAT_TAB_GUI_EDITOR), true));
        }
        chatButtonList.toArray(this.chatButtons = new ChatButton[chatButtonList.size()]);
        this.a.a((this.defaultText == null) ? "" : this.defaultText);
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        switch (button.k) {
            case 4: {
                this.j.a((blk)new GuiChatNameHistory(this.a.b()));
                break;
            }
        }
    }
    
    public void drawButtons(final int mouseX, final int mouseY, final float partialTicks) {
        int slot = 0;
        for (final ChatButton chatButton : this.chatButtons) {
            final boolean enabled = chatButton.isEnabled();
            final int x = this.l - 2 - 13 - slot * 14;
            final int y = this.m - 14;
            if (slot == GuiChatCustom.activeTab) {
                a(x, y - 2, x + 13, y, Integer.MIN_VALUE);
            }
            a(x, y, x + 13, y + 12, Integer.MIN_VALUE);
            final boolean hoverSymbols = mouseX >= x && mouseX < x + 13 && mouseY > y && mouseY < y + 12;
            if (chatButton.getIconData().hasMaterialIcon()) {
                bus.G();
                final double scale = hoverSymbols ? 0.7 : 0.6;
                bus.a(scale, scale, 1.0);
                LabyMod.getInstance().getDrawUtils().renderItemIntoGUI(chatButton.getItem(), (x + 5.5 - scale * 6.0) / scale, (y + 5 - scale * 6.0) / scale);
                bus.H();
            }
            else if (chatButton.getIconData().hasTextureIcon()) {
                bib.z().N().a(chatButton.getIconData().getTextureIcon());
                LabyMod.getInstance().getDrawUtils().drawTexture(x + 2 - (hoverSymbols ? 1 : 0), y + 2 - (hoverSymbols ? 1 : 0), 255.0, 255.0, hoverSymbols ? 11.0 : 9.0, hoverSymbols ? 11.0 : 9.0);
            }
            if (hoverSymbols) {
                TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, enabled ? chatButton.getDisplayName() : LanguageManager.translate("ingame_chat_feature_not_allowed", chatButton.getDisplayName()));
            }
            ++slot;
        }
    }
    
    public void onButtonClick(final int mouseX, final int mouseY, final int mouseButton) {
        for (int slot = 0; slot < this.chatButtons.length; ++slot) {
            final ChatButton chatButton = this.chatButtons[slot];
            final boolean hoverSymbols = mouseX > this.l - 2 - 13 - slot * 14 && mouseX < this.l - 2 - slot * 14 && mouseY > this.m - 14 && mouseY < this.m - 2;
            if (hoverSymbols && chatButton.isEnabled()) {
                switch (chatButton.getId()) {
                    case 0: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)((this.j.m instanceof GuiChatSymbols) ? new GuiChatCustom(this.a.b()) : new GuiChatSymbols(this.a.b())));
                        break;
                    }
                    case 1: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)((this.j.m instanceof GuiChatAutoText) ? new GuiChatCustom(this.a.b()) : new GuiChatAutoText(this.a.b())));
                        break;
                    }
                    case 2: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)((this.j.m instanceof GuiChatShortcuts) ? new GuiChatCustom(this.a.b()) : new GuiChatShortcuts(this.a.b())));
                        break;
                    }
                    case 3: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)((this.j.m instanceof GuiChatPlayerMenu) ? new GuiChatCustom(this.a.b()) : new GuiChatPlayerMenu(this.a.b())));
                        break;
                    }
                    case 4: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)((this.j.m instanceof GuiChatFilter) ? new GuiChatCustom(this.a.b()) : new GuiChatFilter(this.a.b())));
                        break;
                    }
                    case 5: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)((this.j.m instanceof GuiChatNameHistory) ? new GuiChatCustom(this.a.b()) : new GuiChatNameHistory(this.a.b())));
                        break;
                    }
                    case 6: {
                        GuiChatCustom.activeTab = slot;
                        this.j.a((blk)new LabyModModuleEditorGui((blk)new GuiChatCustom(this.a.b())));
                        break;
                    }
                }
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.ingameChatManager.handleMouse(mouseX, mouseY, mouseButton, EnumMouseAction.CLICKED);
        super.a(mouseX, mouseY, mouseButton);
        GuiChatCustom.moduleGui.a(mouseX, mouseY, mouseButton);
        this.onButtonClick(mouseX, mouseY, mouseButton);
        if (mouseButton == 1) {
            final String value = LabyModCore.getMinecraft().getClickEventValue(Mouse.getX(), Mouse.getY());
            if (value != null && value.startsWith("/msg ")) {
                final String name = value.replace("/msg ", "").replace(" ", "");
                if (!NameHistoryUtil.isInCache(name)) {
                    NameHistoryUtil.getNameHistory(name);
                }
            }
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        this.ingameChatManager.handleMouse(mouseX, mouseY, state, EnumMouseAction.RELEASED);
        super.b(mouseX, mouseY, state);
        GuiChatCustom.moduleGui.b(mouseX, mouseY, state);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.ingameChatManager.handleMouse(mouseX, mouseY, clickedMouseButton, EnumMouseAction.DRAGGING);
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        GuiChatCustom.moduleGui.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void k() throws IOException {
        super.k();
        GuiChatCustom.moduleGui.k();
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        GuiChatCustom.moduleGui.a(typedChar, keyCode);
    }
    
    public void m() {
        super.m();
        GuiChatCustom.moduleGui.m();
        Module.setCurrentModuleGui(null);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawModifiedSuperScreen(mouseX, mouseY, partialTicks);
        this.ingameChatManager.handleMouse(mouseX, mouseY, -1, EnumMouseAction.RENDER);
        GuiChatCustom.moduleGui.a(mouseX, mouseY, partialTicks);
        this.drawButtons(mouseX, mouseY, partialTicks);
        final String value = LabyModCore.getMinecraft().getClickEventValue(Mouse.getX(), Mouse.getY());
        if (LabyMod.getSettings().hoverNameHistory && value != null && value.startsWith("/msg ")) {
            final String name = value.replace("/msg ", "").replace(" ", "");
            if (NameHistoryUtil.isInCache(name)) {
                final NameHistory history = NameHistoryUtil.getNameHistory(name);
                final ArrayList<String> lines = new ArrayList<String>();
                boolean currentName = true;
                for (final UUIDFetcher change : history.getChanges()) {
                    if (change.changedToAt != 0L) {
                        final String date = ModUtils.getTimeDiff(change.changedToAt);
                        String c = "7";
                        if (currentName) {
                            c = "6";
                        }
                        currentName = false;
                        lines.add(ModColor.cl(c) + change.name + ModColor.cl("8") + " - " + ModColor.cl("8") + date);
                    }
                    else {
                        lines.add(ModColor.cl("a") + change.name);
                    }
                }
                this.a((List)lines, mouseX, mouseY);
                bus.g();
            }
            else {
                final ArrayList<String> lines2 = new ArrayList<String>();
                lines2.add(LanguageManager.translate("ingame_chat_rightclick_for_namechanges"));
                this.a((List)lines2, mouseX, mouseY);
                bus.g();
            }
        }
    }
    
    private void drawModifiedSuperScreen(final int mouseX, final int mouseY, final float partialTicks) {
        a(2, this.m - 14, this.l - 2 - this.chatButtons.length * 14, this.m - 2, Integer.MIN_VALUE);
        this.a.g();
        this.a(this.j.q.d().a(Mouse.getX(), Mouse.getY()), mouseX, mouseY);
        for (int i = 0; i < this.n.size(); ++i) {
            LabyModCore.getMinecraft().drawButton((bja)this.n.get(i), mouseX, mouseY);
        }
        for (int j = 0; j < this.o.size(); ++j) {
            this.o.get(j).a(this.j, mouseX, mouseY);
        }
    }
    
    public void b(String msg, final boolean addToChat) {
        boolean cancelled = false;
        for (final Shortcuts.Shortcut shortcut : LabyMod.getInstance().getChatToolManager().getShortcuts()) {
            try {
                msg = msg.replace(shortcut.getShortcut(), String.format(shortcut.getReplacement(), LabyMod.getInstance().getPlayerName()));
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
        for (final MessageSendEvent messageSend : LabyMod.getInstance().getEventManager().getMessageSend()) {
            if (messageSend.onSend(msg) && !cancelled) {
                cancelled = true;
            }
        }
        if (cancelled) {
            if (addToChat) {
                this.j.q.d().a(msg);
            }
            return;
        }
        super.b(msg, addToChat);
    }
    
    static {
        moduleGui = new ModuleGui(false, false, EnumDisplayType.INGAME);
        GuiChatCustom.activeTab = -1;
    }
    
    private class ChatButton
    {
        private int id;
        private String displayName;
        private boolean enabled;
        private aip item;
        private ControlElement.IconData iconData;
        
        public ChatButton(final int id, final String languageKey, final ControlElement.IconData iconData, final boolean enabled) {
            this.id = id;
            this.displayName = LanguageManager.translate("ingame_chat_tab_" + languageKey);
            this.item = (iconData.hasMaterialIcon() ? iconData.getMaterialIcon().createItemStack() : null);
            this.iconData = iconData;
            this.enabled = enabled;
        }
        
        public int getId() {
            return this.id;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public boolean isEnabled() {
            return this.enabled;
        }
        
        public aip getItem() {
            return this.item;
        }
        
        public ControlElement.IconData getIconData() {
            return this.iconData;
        }
    }
}
