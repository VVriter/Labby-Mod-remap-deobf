//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.renderer.types;

import net.labymod.ingamechat.renderer.*;
import net.labymod.ingamechat.*;
import net.labymod.main.*;
import net.labymod.core.*;

public class ChatRendererSecond extends ChatRenderer
{
    private bid gameSettings;
    
    public ChatRendererSecond(final IngameChatManager manager) {
        super(manager, false, true);
        this.gameSettings = bib.z().t;
    }
    
    public String getLogPrefix() {
        return "SECOND CHAT";
    }
    
    public float getChatWidth() {
        return (float)LabyMod.getSettings().secondChatWidth;
    }
    
    public int getChatHeight() {
        return this.isChatOpen() ? LabyMod.getSettings().secondChatHeight : (LabyMod.getSettings().secondChatHeight / 2);
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
        if (percent > 99.0f) {
            return (float)(screenWidth - 2.0);
        }
        double pos = screenWidth - screenWidth / 100.0 * (100.0f - percent);
        if (pos < this.getChatWidth()) {
            pos = this.getChatWidth();
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
        return LabyMod.getSettings().secondChatPercentX;
    }
    
    public float getChatPercentY() {
        return LabyMod.getSettings().secondChatPercentY;
    }
    
    public void updateChatSetting(final ChatRenderer.ChatSettingType type, final float value) {
        switch (type) {
            case WIDTH: {
                final int min = 40;
                final int max = 320;
                LabyMod.getSettings().secondChatWidth = (int)(value * (max - min) + min);
                break;
            }
            case HEIGHT: {
                final int min = 20;
                final int max = 180;
                LabyMod.getSettings().secondChatHeight = (int)(value * (max - min) + min);
                break;
            }
            case X: {
                LabyMod.getSettings().secondChatPercentX = value;
                break;
            }
            case Y: {
                LabyMod.getSettings().secondChatPercentY = value;
                break;
            }
        }
    }
    
    public void save() {
        LabyMod.getMainConfig().save();
    }
}
