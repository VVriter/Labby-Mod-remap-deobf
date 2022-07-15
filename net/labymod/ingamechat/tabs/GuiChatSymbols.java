//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tabs;

import net.labymod.ingamechat.*;
import net.labymod.gui.elements.*;
import org.lwjgl.input.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import net.labymod.main.lang.*;
import net.labymod.settings.elements.*;
import java.util.*;
import java.io.*;

public class GuiChatSymbols extends GuiChatCustom
{
    private static final char[] SYMBOLS;
    private Scrollbar scrollbar;
    private HashMap<String, Long> pressedAnimation;
    private boolean canScroll;
    
    public GuiChatSymbols(final String defaultText) {
        super(defaultText);
        this.scrollbar = new Scrollbar(15);
        this.pressedAnimation = new HashMap<String, Long>();
    }
    
    public void b() {
        super.b();
        this.scrollbar.setPosition(this.l - 8, this.m - 145, this.l - 3, this.m - 20);
        this.scrollbar.update(GuiChatSymbols.SYMBOLS.length / 9);
        this.scrollbar.setSpeed(10);
        this.scrollbar.setEntryHeight(10.0);
    }
    
    public void k() throws IOException {
        super.k();
        if (this.canScroll) {
            this.scrollbar.mouseInput();
            final int i = Mouse.getEventDWheel();
            if (i != 0) {
                this.j.q.d().c();
            }
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        this.scrollbar.calc();
        a(this.l - 100, this.m - 150, this.l - 2, this.m - 16, Integer.MIN_VALUE);
        a(this.l - 6, this.m - 145, this.l - 5, this.m - 20, Integer.MIN_VALUE);
        a(this.l - 7, (int)this.scrollbar.getTop(), this.l - 4, (int)(this.scrollbar.getTop() + this.scrollbar.getBarLength()), Integer.MAX_VALUE);
        this.canScroll = (mouseX > this.l - 100 && mouseX < this.l - 2 && mouseY > this.m - 150 && mouseY < this.m - 16);
        int row = 0;
        int column = 0;
        for (final char symbol : GuiChatSymbols.SYMBOLS) {
            if (column * 10 + this.scrollbar.getScrollY() > -5.0 && column * 10 + this.scrollbar.getScrollY() < 125.0) {
                final boolean hoverSymbol = mouseX > this.l - 93 + row * 10 - 5 && mouseX < this.l - 93 + row * 10 + 6 && mouseY > this.m - 147 + column * 10 + this.scrollbar.getScrollY() - 5.0 && mouseY < this.m - 147 + column * 10 + this.scrollbar.getScrollY() + 6.0;
                this.a(LabyModCore.getMinecraft().getFontRenderer(), String.valueOf(symbol), this.l - 93 + row * 10, (int)(this.m - 147 + column * 10 + this.scrollbar.getScrollY()), hoverSymbol ? -24000 : -1);
            }
            if (++row > 8) {
                row = 0;
                ++column;
            }
        }
        row = 0;
        column = 0;
        for (final String code : ModColor.COLOR_CODES) {
            final Long longPressed = this.pressedAnimation.get(code);
            final int pressedCode = (int)((longPressed == null) ? 0L : (longPressed - System.currentTimeMillis()));
            final boolean hoverCode = mouseX > this.l - 111 - column * 11 && mouseX < this.l - 111 + 10 - column * 11 && mouseY > this.m - 150 + row * 11 && mouseY < this.m - 150 + 10 + row * 11;
            a(this.l - 111 - column * 11, this.m - 150 + row * 11, this.l - 111 + 10 - column * 11, this.m - 150 + 10 + row * 11, (hoverCode || pressedCode != 0) ? ModColor.toRGB(132, 132, 132, (pressedCode == 0) ? 130 : (pressedCode / 4)) : Integer.MIN_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), ModColor.cl(code) + code, this.l - 111 - column * 11 + 5, this.m - 150 + row * 11 + 1, Integer.MAX_VALUE);
            if (pressedCode < 0) {
                this.pressedAnimation.remove(code);
            }
            if (++row > 11 || code.equals("9") || code.equals("f")) {
                row = 0;
                ++column;
            }
        }
        boolean contains = false;
        for (final String code2 : ModColor.COLOR_CODES) {
            if (this.a.b().contains("&" + code2)) {
                contains = true;
                break;
            }
        }
        if (contains) {
            a(2, this.m - 16 - 11, this.l - 101, this.m - 15, Integer.MIN_VALUE);
            String string;
            for (string = this.a.b().replace("&", ModColor.getCharAsString()); string.contains("  "); string = string.replace("  ", " ")) {}
            this.c(LabyModCore.getMinecraft().getFontRenderer(), ModColor.cl("r") + string, 4, this.m - 16 - 9, Integer.MAX_VALUE);
        }
        this.c(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("ingame_chat_tab_symbols"), this.l - 100, this.m - 160, -1);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        int row = 0;
        int column = 0;
        for (final char symbol : GuiChatSymbols.SYMBOLS) {
            if (column * 10 + this.scrollbar.getScrollY() > -5.0 && column * 10 + this.scrollbar.getScrollY() < 125.0) {
                final boolean hoverSymbol = mouseX > this.l - 93 + row * 10 - 5 && mouseX < this.l - 93 + row * 10 + 6 && mouseY > this.m - 147 + column * 10 + this.scrollbar.getScrollY() - 5.0 && mouseY < this.m - 147 + column * 10 + this.scrollbar.getScrollY() + 6.0;
                if (hoverSymbol) {
                    this.a.a(symbol, 0);
                    LabyModCore.getMinecraft().playSound(SettingsElement.BUTTON_PRESS_SOUND, 2.0f);
                    break;
                }
            }
            if (++row > 8) {
                row = 0;
                ++column;
            }
        }
        row = 0;
        column = 0;
        for (final String code : ModColor.COLOR_CODES) {
            if (mouseX > this.l - 111 - column * 11 && mouseX < this.l - 111 + 10 - column * 11 && mouseY > this.m - 150 + row * 11 && mouseY < this.m - 150 + 10 + row * 11) {
                this.a.a("&".charAt(0), 0);
                this.a.a(code.charAt(0), 0);
                LabyModCore.getMinecraft().playSound(SettingsElement.BUTTON_PRESS_SOUND, 2.0f);
            }
            if (++row > 11 || code.equals("9") || code.equals("f")) {
                row = 0;
                ++column;
            }
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        final String currentText = this.a.b();
        final int curser = this.a.i();
        if (curser > 1 && currentText.length() > 0) {
            final String curserText = String.valueOf(typedChar);
            boolean isColorCode = false;
            for (final String code : ModColor.COLOR_CODES) {
                if (code.equals(curserText)) {
                    isColorCode = true;
                    break;
                }
            }
            if (curser > 1 && isColorCode && String.valueOf(currentText.charAt(curser - 2)).equals("&")) {
                this.pressedAnimation.put(curserText, System.currentTimeMillis() + 1000L);
            }
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        super.b(mouseX, mouseY, state);
    }
    
    private static char[] loadSymbols() {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/minecraft/labymod/data/symbols.txt");
        final DataInputStream dis = new DataInputStream(inputStream);
        String string = "";
        final Scanner scanner = new Scanner(dis, "UTF-8");
        while (scanner.hasNext()) {
            string += scanner.next();
        }
        scanner.close();
        return string.toCharArray();
    }
    
    static {
        SYMBOLS = loadSymbols();
    }
}
