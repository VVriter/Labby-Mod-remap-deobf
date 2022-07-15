//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tabs;

import net.labymod.ingamechat.*;
import net.labymod.gui.elements.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.core.*;
import java.io.*;
import net.labymod.utils.*;
import net.labymod.ingamechat.namehistory.*;

public class GuiChatNameHistory extends GuiChatCustom
{
    private final int windowWidth = 160;
    private final int windowHeight = 16;
    private final int entryHeight = 11;
    private final int maxEntries = 10;
    private Scrollbar scrollbar;
    private ModTextField fieldSearch;
    private boolean hoverSearchButton;
    private NameHistory nameHistory;
    private boolean lastSearchNotFound;
    private String forceNameSearch;
    private boolean hoverLabyNet;
    
    public GuiChatNameHistory(final String defaultText) {
        super(defaultText);
        this.getClass();
        this.scrollbar = new Scrollbar(11);
        this.hoverSearchButton = false;
        this.lastSearchNotFound = false;
        this.forceNameSearch = null;
        this.hoverLabyNet = false;
    }
    
    public GuiChatNameHistory(final String defaultText, final String username) {
        super(defaultText);
        this.getClass();
        this.scrollbar = new Scrollbar(11);
        this.hoverSearchButton = false;
        this.lastSearchNotFound = false;
        this.forceNameSearch = null;
        this.hoverLabyNet = false;
        this.forceNameSearch = username;
    }
    
    public void b() {
        super.b();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final double buttonWidth = draw.getStringWidth(LanguageManager.translate("button_search")) * 0.7 + 4.0;
        final int n = 0;
        final bip fontRenderer = LabyModCore.getMinecraft().getFontRenderer();
        final int n2 = 0;
        final int n3 = 0;
        this.getClass();
        final int n4 = (int)(160.0 - buttonWidth);
        this.getClass();
        (this.fieldSearch = new ModTextField(n, fontRenderer, n2, n3, n4, 16)).setEnableBackgroundDrawing(false);
        this.fieldSearch.setMaxStringLength(16);
        this.fieldSearch.setFocused(true);
        final Scrollbar scrollbar = this.scrollbar;
        this.getClass();
        scrollbar.setSpeed(11);
        this.scrollbar.init();
        if (this.forceNameSearch != null) {
            this.fieldSearch.setText(this.forceNameSearch);
            this.fieldSearch.setFocused(true);
            this.fieldSearch.setCursorPositionEnd();
            this.doNameSearch(this.forceNameSearch);
            this.forceNameSearch = null;
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final int n = this.l - 2;
        this.getClass();
        final int x = n - 160;
        final int n2 = this.m - 16;
        this.getClass();
        double y = n2 - 16;
        y -= this.pushSearchBarUp();
        final DrawUtils drawUtils = draw;
        final double left = x;
        final double top = y;
        final int n3 = x;
        this.getClass();
        final double right = n3 + 160;
        final double n4 = y;
        this.getClass();
        drawUtils.drawRect(left, top, right, n4 + 16.0, Integer.MIN_VALUE);
        final int paddingField = 1;
        final int fieldColor = this.fieldSearch.isFocused() ? Integer.MIN_VALUE : ModColor.toRGB(12, 12, 12, 180);
        final DrawUtils drawUtils2 = draw;
        final double left2 = x + paddingField;
        final double top2 = y + paddingField;
        final double right2 = x + this.fieldSearch.getWidth() - paddingField;
        final double n5 = y;
        this.getClass();
        drawUtils2.drawRect(left2, top2, right2, n5 + 16.0 - paddingField, fieldColor);
        this.fieldSearch.xPosition = x + 3;
        this.fieldSearch.yPosition = (int)(y + 4.0);
        this.fieldSearch.drawTextBox();
        final int searchX = x + this.fieldSearch.getWidth();
        final double searchY = y;
        final int searchWidth = this.l - 2 - searchX;
        this.hoverSearchButton = (mouseX > searchX && searchX < searchX + searchWidth && mouseY > searchY && mouseY < searchY + 16.0);
        final int buttonColor = this.fieldSearch.getText().isEmpty() ? ModColor.toRGB(100, 100, 100, 200) : (this.hoverSearchButton ? ModColor.toRGB(100, 200, 100, 200) : Integer.MAX_VALUE);
        final int paddingButton = 1;
        final DrawUtils drawUtils3 = draw;
        final double left3 = searchX + paddingButton;
        final double top3 = searchY + paddingButton;
        final double right3 = searchX + searchWidth - paddingButton;
        final double n6 = searchY;
        this.getClass();
        drawUtils3.drawRect(left3, top3, right3, n6 + 16.0 - paddingButton, buttonColor);
        draw.drawCenteredString(LanguageManager.translate("button_search"), searchX + searchWidth / 2.0, searchY + 5.0, 0.7);
        final double topY = y;
        if (this.nameHistory == null && this.lastSearchNotFound) {
            final double n7 = y;
            this.getClass();
            y = n7 + 16.0;
            final DrawUtils drawUtils4 = draw;
            final double left4 = x;
            final double top4 = y + 1.0;
            final int n8 = x;
            this.getClass();
            drawUtils4.drawRect(left4, top4, n8 + 160, y + 14.0, Integer.MIN_VALUE);
            draw.drawString(ModColor.cl('c') + LanguageManager.translate("search_not_found"), x + 3, y + 4.0);
        }
        else if (this.nameHistory != null && this.nameHistory.getChanges() != null) {
            final double n9 = y;
            this.getClass();
            y = n9 + 16.0;
            y += this.scrollbar.getScrollY();
            final double labyNetX = searchX - 5;
            final double labyNetY = searchY - 11.0;
            this.hoverLabyNet = (mouseX > labyNetX && mouseX < searchX + searchWidth && mouseY > labyNetY && mouseY < labyNetY + 10.0);
            draw.drawRect(labyNetX, labyNetY, searchX + searchWidth, labyNetY + 10.0, Integer.MIN_VALUE);
            draw.drawString(ModColor.cl(this.hoverLabyNet ? 'e' : 'f') + ModColor.cl('l') + "LABYnet", labyNetX + 1.0, labyNetY + 2.0, 0.7);
            int index = 0;
            for (final UUIDFetcher fetchedName : this.nameHistory.getChanges()) {
                final double n10 = y;
                final double n11 = topY;
                this.getClass();
                if (n10 >= n11 + 16.0 && y < this.m - 16 && this.nameHistory != null && this.nameHistory.getChanges() != null && fetchedName != null) {
                    final boolean latestName = index == 0;
                    final boolean originalName = index == this.nameHistory.getChanges().length - 1;
                    final DrawUtils drawUtils5 = draw;
                    final double left5 = x;
                    final double top5 = y + 1.0;
                    final int n12 = x;
                    this.getClass();
                    final double right4 = n12 + 160;
                    final double n13 = y;
                    this.getClass();
                    drawUtils5.drawRect(left5, top5, right4, n13 + 11.0, Integer.MIN_VALUE);
                    final String nameColor = ModColor.cl(latestName ? 'a' : (originalName ? 'b' : '7'));
                    draw.drawString(nameColor + fetchedName.name, x + 2, y + ((latestName || originalName) ? 2 : 3), (latestName || originalName) ? 1.0 : 0.7);
                    final String timeDiff = (fetchedName.changedToAt == 0L) ? LanguageManager.translate("original_name") : ModUtils.getTimeDiff(fetchedName.changedToAt);
                    final DrawUtils drawUtils6 = draw;
                    final String string = ModColor.cl('e') + timeDiff;
                    final int n14 = x;
                    this.getClass();
                    drawUtils6.drawRightString(string, n14 + 160 - 4, y + 4.0, 0.6);
                    if (this.nameHistory != null && this.nameHistory.getChanges() != null && this.nameHistory.getChanges().length > 1) {
                        final int color = originalName ? ModColor.toRGB(100, 200, 200, 250) : (latestName ? ModColor.toRGB(100, 200, 100, 250) : ModColor.toRGB(180, 180, 180, 100));
                        final DrawUtils drawUtils7 = draw;
                        final double left6 = x - 3;
                        final double n15 = y;
                        int n16;
                        if (latestName) {
                            this.getClass();
                            n16 = 11 / 2;
                        }
                        else {
                            n16 = 0;
                        }
                        final double top6 = n15 + n16;
                        final double right5 = x - 3 + 1;
                        final double n17 = y;
                        int n18;
                        if (originalName) {
                            this.getClass();
                            n18 = 11 / 2 + 1;
                        }
                        else {
                            this.getClass();
                            n18 = 11;
                        }
                        drawUtils7.drawRect(left6, top6, right5, n17 + n18, color);
                        final DrawUtils drawUtils8 = draw;
                        final double left7 = x - 3 + 1;
                        final double n19 = y;
                        this.getClass();
                        final double top7 = n19 + 11 / 2;
                        final double right6 = x;
                        final double n20 = y;
                        this.getClass();
                        drawUtils8.drawRect(left7, top7, right6, n20 + 11 / 2 + 1.0, color);
                    }
                }
                final double n21 = y;
                this.getClass();
                y = n21 + 11.0;
                ++index;
            }
        }
        final Scrollbar scrollbar = this.scrollbar;
        final int n22 = x;
        this.getClass();
        final double n23 = n22 + 160 - 3;
        final double n24 = topY;
        this.getClass();
        final double n25 = n24 + 16.0;
        final int n26 = x;
        this.getClass();
        scrollbar.setPosition(n23, n25, (double)(n26 + 160), (double)(this.m - 16));
        this.scrollbar.draw();
        draw.drawString(LanguageManager.translate("ingame_chat_tab_namehistory"), x, topY - 10.0);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        final int n = this.l - 2;
        this.getClass();
        final int x = n - 160;
        final int n2 = this.m - 16;
        this.getClass();
        int y = n2 - 16;
        y -= this.pushSearchBarUp();
        this.fieldSearch.mouseClicked(mouseX, mouseY, mouseButton);
        Label_0120: {
            if (mouseX > x && mouseX < x + this.fieldSearch.getWidth() && mouseY > y) {
                final int n3 = y;
                this.getClass();
                if (mouseY < n3 + 16) {
                    this.fieldSearch.setFocused(true);
                    break Label_0120;
                }
            }
            this.fieldSearch.setFocused(false);
        }
        if (this.hoverLabyNet && this.nameHistory != null) {
            LabyMod.getInstance().openWebpage(String.format("https://laby.net/@%s", this.nameHistory.getUUID()), false);
        }
        if (this.hoverSearchButton && !this.fieldSearch.getText().isEmpty()) {
            this.doNameSearch(this.fieldSearch.getText());
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (this.fieldSearch.isFocused()) {
            this.fieldSearch.textboxKeyTyped(typedChar, keyCode);
            if (keyCode == 28) {
                this.doNameSearch(this.fieldSearch.getText());
            }
        }
        else {
            super.a(typedChar, keyCode);
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        final Scrollbar scrollbar = this.scrollbar;
        final double scrollY = this.scrollbar.getScrollY();
        this.getClass();
        final int n = (int)(scrollY / 11.0);
        this.getClass();
        scrollbar.setScrollY((double)(n * 11));
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
    }
    
    public void e() {
        if (this.fieldSearch.isFocused()) {
            this.fieldSearch.updateCursorCounter();
        }
        else {
            super.e();
        }
    }
    
    private int pushSearchBarUp() {
        if (this.nameHistory != null) {
            final int length = this.nameHistory.getChanges().length;
            this.getClass();
            int n2;
            if (length > 10) {
                this.getClass();
                final int n = 10;
                this.getClass();
                n2 = n * 11;
            }
            else {
                final int length2 = this.nameHistory.getChanges().length;
                this.getClass();
                n2 = length2 * 11;
            }
            return n2;
        }
        if (this.lastSearchNotFound) {
            return 14;
        }
        return 0;
    }
    
    private void doNameSearch(final String username) {
        NameHistoryUtil.getNameHistory(username, (Consumer)new Consumer<NameHistory>() {
            @Override
            public void accept(final NameHistory nameHistory) {
                GuiChatNameHistory.this.nameHistory = nameHistory;
                GuiChatNameHistory.this.lastSearchNotFound = (nameHistory == null);
                if (nameHistory == null) {
                    GuiChatNameHistory.this.scrollbar.update(0);
                }
                else {
                    GuiChatNameHistory.this.scrollbar.update(nameHistory.getChanges().length);
                    GuiChatNameHistory.this.fieldSearch.setFocused(false);
                }
            }
        });
    }
}
