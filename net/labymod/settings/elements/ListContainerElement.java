//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings.elements;

import net.labymod.main.lang.*;
import net.labymod.main.*;
import net.labymod.utils.*;

public class ListContainerElement extends ControlElement
{
    public ListContainerElement(final String displayName, final ControlElement.IconData iconData) {
        super(displayName, (String)null, iconData);
        final String key = "container_" + displayName;
        final String translation = LanguageManager.translate(key);
        if (!key.equals(translation)) {
            this.setDisplayName(translation);
        }
        this.setSettingEnabled(true);
    }
    
    public void draw(final int x, final int y, final int maxX, final int maxY, final int mouseX, final int mouseY) {
        super.draw(x, y, maxX, maxY, mouseX, mouseY);
        LabyMod.getInstance().getDrawUtils().drawRectangle(x - 1, y, x, maxY, ModColor.toRGB(120, 120, 120, 120));
    }
    
    public int getObjectWidth() {
        return 0;
    }
}
