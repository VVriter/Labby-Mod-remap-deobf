//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.utils.*;
import net.labymod.support.util.*;
import net.labymod.main.*;
import net.labymod.main.lang.*;
import net.labymod.core.*;
import java.io.*;
import java.util.*;

public class GuiWebPanel extends blk
{
    private blk multiplayerScreen;
    private String pin;
    private bje field;
    
    public static void open(String message, final blk multiplayerScreen) {
        message = ModColor.removeColor(message);
        Debug.log(Debug.EnumDebugMode.MINECRAFT, "Disconnected: " + message);
        if (message.equals(cey.a("disconnect.loginFailedInfo", new Object[] { cey.a("disconnect.loginFailedInfo.invalidSession", new Object[0]) }))) {}
        if (!message.contains("Created PIN for ") && !message.contains("You need LabyMod to register")) {
            return;
        }
        if (!message.contains(":")) {
            return;
        }
        bib.z().a((blk)new GuiWebPanel(message.split("\n")[1], multiplayerScreen));
    }
    
    public GuiWebPanel(final String pin, final blk multiplayerScreen) {
        this.pin = pin;
        this.multiplayerScreen = multiplayerScreen;
        LabyMod.getInstance().openWebpage(String.format("https://www.labymod.net/key/?id=%s&pin=%s", LabyMod.getInstance().getPlayerUUID().toString(), this.pin), false);
    }
    
    public void b() {
        this.n.clear();
        this.n.add(new bja(6, this.l / 2 - 120, this.m / 2 + 10, 100, 20, LanguageManager.translate("button_not_working")));
        this.n.add(new bja(5, this.l / 2 - 10, this.m / 2 + 10, 130, 20, LanguageManager.translate("button_okay")));
        (this.field = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), this.l / 2 - 100, this.m / 2 + 35, 200, 20)).e(false);
        this.field.f(640);
        this.field.a(String.format("https://www.labymod.net/key/?id=%s&pin=%s", LabyMod.getInstance().getPlayerUUID().toString(), this.pin));
        super.b();
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 5) {
            bib.z().a(this.multiplayerScreen);
        }
        if (button.k == 6) {
            this.field.e(true);
            this.field.b(true);
            this.field.e();
            this.field.i(this.field.h() - 1);
            button.l = false;
        }
        super.a(button);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        this.field.a(typedChar, keyCode);
        super.a(typedChar, keyCode);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.field.a(mouseX, mouseY, mouseButton);
        super.a(mouseX, mouseY, mouseButton);
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.c(0);
        final List<String> list = LabyMod.getInstance().getDrawUtils().listFormattedStringToWidth(LanguageManager.translate("tab_opened"), this.l / 3, 4);
        int y = 0;
        for (final String s : list) {
            LabyMod.getInstance().getDrawUtils().drawCenteredString(ModColor.cl("a") + s, this.l / 2, this.m / 2 - 40 + y);
            y += 10;
        }
        this.field.g();
        if (this.field.r()) {
            LabyMod.getInstance().getDrawUtils().drawCenteredString(ModColor.cl("c") + LanguageManager.translate("open_link_in_browser"), this.l / 2, this.m / 2 + 60);
        }
        super.a(mouseX, mouseY, partialTicks);
    }
}
