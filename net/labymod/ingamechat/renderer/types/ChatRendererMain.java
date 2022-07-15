//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.renderer.types;

import net.labymod.ingamechat.renderer.*;
import net.labymod.ingamechat.*;
import net.labymod.core.*;
import net.labymod.main.*;

public class ChatRendererMain extends ChatRenderer
{
    private bid gameSettings;
    
    public ChatRendererMain(final IngameChatManager manager) {
        super(manager, true, false);
        this.gameSettings = bib.z().t;
    }
    
    public String getLogPrefix() {
        return "CHAT";
    }
    
    public float getChatWidth() {
        final float max = 320.0f;
        final float min = 40.0f;
        return (float)LabyModCore.getMath().floor_float(this.gameSettings.H * (max - min) + min);
    }
    
    public int getChatHeight() {
        final float height = this.isChatOpen() ? this.gameSettings.J : this.gameSettings.I;
        final float max = 180.0f;
        final float min = 20.0f;
        return LabyModCore.getMath().floor_float(height * (max - min) + min);
    }
    
    public float getChatScale() {
        return this.gameSettings.G;
    }
    
    public float getChatOpacity() {
        return bib.z().t.s;
    }
    
    public float getChatPositionX() {
        final double screenWidth = LabyMod.getInstance().getDrawUtils().getWidth();
        final float percent = this.getChatPercentX();
        final double total = screenWidth / 2.0 + this.getChatWidth();
        if (percent < 1.0f) {
            return 2.0f;
        }
        double pos = total / 100.0 * percent;
        if (pos > screenWidth - this.getChatWidth()) {
            pos = screenWidth - this.getChatWidth();
        }
        return (float)pos;
    }
    
    public float getChatPositionY() {
        final float height = this.lastRenderedLinesCount * LabyModCore.getMinecraft().getFontRenderer().a * this.getChatScale();
        final double screenHeight = LabyMod.getInstance().getDrawUtils().getHeight() - 28;
        final float percent = this.getChatPercentY();
        if (percent > 99.0f) {
            return (float)screenHeight;
        }
        if (percent < 50.0f) {
            return (float)(height + 2.0f + screenHeight / 100.0 * percent);
        }
        return (float)(screenHeight / 100.0 * percent);
    }
    
    public float getChatPercentX() {
        return LabyMod.getSettings().mainChatPercentX;
    }
    
    public float getChatPercentY() {
        return LabyMod.getSettings().mainChatPercentY;
    }
    
    public void updateChatSetting(final ChatRenderer.ChatSettingType type, final float value) {
        switch (type) {
            case WIDTH: {
                this.gameSettings.H = value;
                break;
            }
            case HEIGHT: {
                this.gameSettings.J = value;
                this.gameSettings.I = value / 2.0f;
                break;
            }
            case X: {
                LabyMod.getSettings().mainChatPercentX = value;
                break;
            }
            case Y: {
                LabyMod.getSettings().mainChatPercentY = value;
                break;
            }
        }
    }
    
    public void save() {
        bib.z().t.b();
    }
}
