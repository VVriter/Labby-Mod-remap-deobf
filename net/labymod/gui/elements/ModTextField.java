//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui.elements;

import net.minecraftforge.fml.relauncher.*;
import net.labymod.utils.*;
import com.google.common.base.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.core.*;

@SideOnly(Side.CLIENT)
public class ModTextField extends bir
{
    private final int id;
    private final bip fontRendererInstance;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    private String text;
    private int maxStringLength;
    private int cursorCounter;
    private boolean enableBackgroundDrawing;
    private boolean canLoseFocus;
    private boolean isFocused;
    private boolean isEnabled;
    private int lineScrollOffset;
    private int cursorPosition;
    private int selectionEnd;
    private int enabledColor;
    private int disabledColor;
    private boolean visible;
    private Predicate<String> validator;
    private boolean blackBox;
    private boolean modPasswordBox;
    private String modBlacklistWord;
    private boolean colorBarEnabled;
    private ModColor hoveredModColor;
    private String colorAtCursor;
    private String placeHolder;
    private boolean backgroundColor;
    
    public ModTextField(final int componentId, final bip fontrenderer, final int x, final int y, final int par5Width, final int par6Height) {
        this.text = "";
        this.maxStringLength = 32;
        this.enableBackgroundDrawing = true;
        this.canLoseFocus = true;
        this.isEnabled = true;
        this.enabledColor = 14737632;
        this.disabledColor = 7368816;
        this.visible = true;
        this.validator = (Predicate<String>)Predicates.alwaysTrue();
        this.blackBox = true;
        this.modPasswordBox = false;
        this.modBlacklistWord = "";
        this.colorBarEnabled = false;
        this.hoveredModColor = null;
        this.colorAtCursor = null;
        this.backgroundColor = false;
        this.id = componentId;
        this.fontRendererInstance = fontrenderer;
        this.xPosition = x;
        this.yPosition = y;
        this.width = par5Width;
        this.height = par6Height;
    }
    
    public void setBackgroundColor(final boolean backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public boolean isBackgroundColor() {
        return this.backgroundColor;
    }
    
    public void updateCursorCounter() {
        ++this.cursorCounter;
    }
    
    public void setText(final String textIn) {
        if (this.validator.apply((Object)textIn)) {
            if (textIn.length() > this.maxStringLength) {
                this.text = textIn.substring(0, this.maxStringLength);
            }
            else {
                this.text = textIn;
            }
            this.setCursorPosition(0);
        }
    }
    
    public void setPlaceHolder(final String placeHolder) {
        this.placeHolder = placeHolder;
    }
    
    public String getPlaceHolder() {
        return this.placeHolder;
    }
    
    public String getText() {
        return this.text;
    }
    
    public String getSelectedText() {
        final int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
        final int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
        return this.text.substring(i, j);
    }
    
    public void setValidator(final Predicate<String> theValidator) {
        this.validator = theValidator;
    }
    
    public void writeText(final String textToWrite) {
        String s = "";
        final String s2 = g.a(textToWrite);
        final int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
        final int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
        final int k = this.maxStringLength - this.text.length() - (i - j);
        int l = 0;
        if (this.text.length() > 0) {
            s += this.text.substring(0, i);
        }
        if (k < s2.length()) {
            s += s2.substring(0, k);
            l = k;
        }
        else {
            s += s2;
            l = s2.length();
        }
        if (this.text.length() > 0 && j < this.text.length()) {
            s += this.text.substring(j);
        }
        if (this.validator.apply((Object)s)) {
            this.text = s;
            this.moveCursorBy(i - this.selectionEnd + l);
        }
    }
    
    public void deleteWords(final int num) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                this.deleteFromCursor(this.getNthWordFromCursor(num) - this.cursorPosition);
            }
        }
    }
    
    public void deleteFromCursor(final int num) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            }
            else {
                final boolean flag = num < 0;
                final int i = flag ? (this.cursorPosition + num) : this.cursorPosition;
                final int j = flag ? this.cursorPosition : (this.cursorPosition + num);
                String s = "";
                if (i >= 0) {
                    s = this.text.substring(0, i);
                }
                if (j < this.text.length()) {
                    s += this.text.substring(j);
                }
                if (this.validator.apply((Object)s)) {
                    this.text = s;
                    if (flag) {
                        this.moveCursorBy(num);
                    }
                }
            }
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getNthWordFromCursor(final int numWords) {
        return this.getNthWordFromPos(numWords, this.getCursorPosition());
    }
    
    public int getNthWordFromPos(final int n, final int pos) {
        return this.getNthWordFromPosWS(n, pos, true);
    }
    
    public int getNthWordFromPosWS(final int n, final int pos, final boolean skipWs) {
        int i = pos;
        final boolean flag = n < 0;
        for (int j = Math.abs(n), k = 0; k < j; ++k) {
            if (!flag) {
                final int l = this.text.length();
                i = this.text.indexOf(32, i);
                if (i == -1) {
                    i = l;
                }
                else {
                    while (skipWs && i < l && this.text.charAt(i) == ' ') {
                        ++i;
                    }
                }
            }
            else {
                while (skipWs && i > 0 && this.text.charAt(i - 1) == ' ') {
                    --i;
                }
                while (i > 0 && this.text.charAt(i - 1) != ' ') {
                    --i;
                }
            }
        }
        return i;
    }
    
    public void moveCursorBy(final int num) {
        this.setCursorPosition(this.selectionEnd + num);
    }
    
    public void setCursorPosition(final int pos) {
        this.cursorPosition = pos;
        final int i = this.text.length();
        this.setSelectionPos(this.cursorPosition = LabyModCore.getMath().clamp_int(this.cursorPosition, 0, i));
    }
    
    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }
    
    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }
    
    public boolean textboxKeyTyped(final char typedChar, final int keyCode) {
        if (!this.isFocused) {
            return false;
        }
        if (blk.g(keyCode)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        }
        if (blk.f(keyCode)) {
            if (!this.isPasswordBox()) {
                blk.e(this.getSelectedText());
            }
            return true;
        }
        if (blk.e(keyCode)) {
            if (this.isEnabled) {
                this.writeText(blk.o());
            }
            return true;
        }
        if (blk.d(keyCode)) {
            if (!this.isPasswordBox()) {
                blk.e(this.getSelectedText());
            }
            if (this.isEnabled) {
                this.writeText("");
            }
            return true;
        }
        switch (keyCode) {
            case 14: {
                if (blk.r()) {
                    if (this.isEnabled) {
                        this.deleteWords(-1);
                    }
                }
                else if (this.isEnabled) {
                    this.deleteFromCursor(-1);
                }
                return true;
            }
            case 199: {
                if (blk.s()) {
                    this.setSelectionPos(0);
                }
                else {
                    this.setCursorPositionZero();
                }
                return true;
            }
            case 203: {
                if (blk.s()) {
                    if (blk.r()) {
                        this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() - 1);
                    }
                }
                else if (blk.r()) {
                    this.setCursorPosition(this.getNthWordFromCursor(-1));
                }
                else {
                    this.moveCursorBy(-1);
                }
                return true;
            }
            case 205: {
                if (blk.s()) {
                    if (blk.r()) {
                        this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
                    }
                    else {
                        this.setSelectionPos(this.getSelectionEnd() + 1);
                    }
                }
                else if (blk.r()) {
                    this.setCursorPosition(this.getNthWordFromCursor(1));
                }
                else {
                    this.moveCursorBy(1);
                }
                return true;
            }
            case 207: {
                if (blk.s()) {
                    this.setSelectionPos(this.text.length());
                }
                else {
                    this.setCursorPositionEnd();
                }
                return true;
            }
            case 211: {
                if (blk.r()) {
                    if (this.isEnabled) {
                        this.deleteWords(1);
                    }
                }
                else if (this.isEnabled) {
                    this.deleteFromCursor(1);
                }
                return true;
            }
            default: {
                if (g.a(typedChar)) {
                    if (this.isEnabled) {
                        this.writeText(Character.toString(typedChar));
                    }
                    return true;
                }
                return false;
            }
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean flag = mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
        if (this.colorBarEnabled && this.hoveredModColor != null) {
            this.writeText("&" + this.hoveredModColor.getColorChar());
            return true;
        }
        if (this.canLoseFocus) {
            this.setFocused(flag);
        }
        if (this.isFocused && flag && mouseButton == 0) {
            int i = mouseX - this.xPosition;
            if (this.enableBackgroundDrawing) {
                i -= 4;
            }
            final String s = this.fontRendererInstance.a(this.text.substring(this.lineScrollOffset), this.getWidth());
            this.setCursorPosition(this.fontRendererInstance.a(s, i).length() + this.lineScrollOffset);
        }
        return this.isFocused;
    }
    
    public void drawTextBox() {
        if (this.getVisible()) {
            if (!this.getBlacklistWord().isEmpty() && this.getText().contains(this.getBlacklistWord())) {
                this.setText(this.getText().replace(this.getBlacklistWord(), ""));
            }
            if (this.getEnableBackgroundDrawing()) {
                if (this.blackBox) {
                    a(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
                    a(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
                }
                else if (this.isFocused) {
                    LabyMod.getInstance().getDrawUtils().drawRectBorder(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, ModColor.toRGB(220, 220, 225, 62), 1.0);
                    LabyMod.getInstance().getDrawUtils().drawRectangle(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, ModColor.toRGB(0, 0, 3, 180));
                }
                else {
                    a(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, ModColor.toRGB(70, 60, 53, 122));
                    a(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, ModColor.toRGB(0, 0, 3, 180));
                }
            }
            final int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            final int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String theText = this.getText().substring(this.lineScrollOffset);
            if (this.isPasswordBox()) {
                theText = theText.replaceAll(".", "*");
            }
            final String s = this.fontRendererInstance.a(theText, this.getWidth());
            final boolean flag = j >= 0 && j <= s.length();
            final boolean flag2 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            final int l = this.enableBackgroundDrawing ? (this.xPosition + 4) : this.xPosition;
            final int i2 = this.enableBackgroundDrawing ? (this.yPosition + (this.height - 8) / 2) : this.yPosition;
            int j2 = l;
            this.colorAtCursor = null;
            if (k > s.length()) {
                k = s.length();
            }
            if (s.length() > 0) {
                final String s2 = flag ? s.substring(0, j) : s;
                j2 += this.fontRendererInstance.a(this.visualColorForText(s2, true));
            }
            final boolean placeHolder = this.placeHolder != null && this.getText().isEmpty() && !this.isFocused;
            final boolean flag3 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k2 = j2;
            if (!flag) {
                k2 = ((j > 0) ? (l + this.width) : l);
            }
            else if (flag3) {
                k2 = j2 - 1;
                --j2;
            }
            if (s.length() > 0 && flag && j < s.length()) {
                j2 += this.fontRendererInstance.a(this.visualColorForText(s.substring(j), false));
            }
            this.fontRendererInstance.a(this.visualColorForText(s, false), (float)l, (float)i2, i);
            if (flag2 && !placeHolder) {
                if (flag3) {
                    bir.a(k2, i2 - 1, k2 + 1, i2 + 1 + this.fontRendererInstance.a, -3092272);
                }
                else {
                    this.fontRendererInstance.a("_", (float)k2, (float)i2, i);
                }
            }
            if (k != j) {
                final int l2 = l + this.fontRendererInstance.a(s.substring(0, k));
                this.drawCursorVertical(k2, i2 - 1, l2 - 1, i2 + 1 + this.fontRendererInstance.a);
            }
            if (placeHolder) {
                this.c(this.fontRendererInstance, this.placeHolder, k2, i2, Color.LIGHT_GRAY.getRGB());
            }
        }
    }
    
    public void drawColorBar(final int mouseX, final int mouseY) {
        if (this.colorBarEnabled) {
            this.hoveredModColor = null;
            final int ll = 9;
            int pX = this.xPosition + this.width / 2 - (ModColor.values().length * ll - ll) / 2;
            final int pY = this.yPosition + this.height + 5;
            for (final ModColor color : ModColor.values()) {
                boolean hovered = mouseX > pX - ll / 2 && mouseX < pX + ll / 2 && mouseY > pY - 1 && mouseY < pY + 9;
                if (hovered) {
                    this.hoveredModColor = color;
                }
                if (this.colorAtCursor != null && this.colorAtCursor.equals("" + color.getColorChar())) {
                    hovered = true;
                }
                a(pX - ll / 2, pY - 1, pX + ll / 2, pY + 9, hovered ? Integer.MAX_VALUE : ModColor.toRGB(120, 120, 120, 120));
                LabyMod.getInstance().getDrawUtils().drawCenteredString(color.toString() + color.getColorChar(), pX, pY);
                pX += ll;
            }
        }
    }
    
    private String visualColorForText(final String text, final boolean saveCursorColor) {
        String coloredString = "";
        boolean foundColor = false;
        for (int i = 0; i < text.length(); ++i) {
            final char c = text.charAt(i);
            if (c == '&' && i != text.length() - 1) {
                if (foundColor) {
                    coloredString += "&";
                }
                foundColor = true;
            }
            else {
                if (foundColor) {
                    foundColor = false;
                    coloredString = coloredString + "§" + c + '&';
                    if (saveCursorColor) {
                        this.colorAtCursor = "" + c;
                    }
                }
                coloredString += c;
            }
        }
        return coloredString;
    }
    
    private void drawCursorVertical(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            final int i = startX;
            startX = endX;
            endX = i;
        }
        if (startY < endY) {
            final int j = startY;
            startY = endY;
            endY = j;
        }
        if (endX > this.xPosition + this.width) {
            endX = this.xPosition + this.width;
        }
        if (startX > this.xPosition + this.width) {
            startX = this.xPosition + this.width;
        }
        final bve tessellator = bve.a();
        final WorldRendererAdapter worldrenderer = LabyModCore.getWorldRenderer();
        bus.c(0.0f, 0.0f, 255.0f, 255.0f);
        bus.z();
        bus.w();
        bus.f(5387);
        worldrenderer.begin(7, cdy.e);
        worldrenderer.pos((double)startX, (double)endY, 0.0).endVertex();
        worldrenderer.pos((double)endX, (double)endY, 0.0).endVertex();
        worldrenderer.pos((double)endX, (double)startY, 0.0).endVertex();
        worldrenderer.pos((double)startX, (double)startY, 0.0).endVertex();
        tessellator.b();
        bus.x();
        bus.y();
    }
    
    public void setMaxStringLength(final int length) {
        this.maxStringLength = length;
        if (this.text.length() > length) {
            this.text = this.text.substring(0, length);
        }
    }
    
    public int getMaxStringLength() {
        return this.maxStringLength;
    }
    
    public int getCursorPosition() {
        return this.cursorPosition;
    }
    
    public void setBlacklistWord(final String modBlacklistWords) {
        this.modBlacklistWord = modBlacklistWords;
    }
    
    public String getBlacklistWord() {
        return this.modBlacklistWord;
    }
    
    public void setPasswordBox(final boolean modPasswordBox) {
        this.modPasswordBox = modPasswordBox;
    }
    
    public boolean isPasswordBox() {
        return this.modPasswordBox;
    }
    
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }
    
    public void setEnableBackgroundDrawing(final boolean enableBackgroundDrawingIn) {
        this.enableBackgroundDrawing = enableBackgroundDrawingIn;
    }
    
    public boolean isBlackBox() {
        return this.blackBox;
    }
    
    public void setBlackBox(final boolean blackBox) {
        this.blackBox = blackBox;
    }
    
    public void setTextColor(final int color) {
        this.enabledColor = color;
    }
    
    public void setDisabledTextColour(final int color) {
        this.disabledColor = color;
    }
    
    public void setFocused(final boolean isFocusedIn) {
        if (isFocusedIn && !this.isFocused) {
            this.cursorCounter = 0;
        }
        this.isFocused = isFocusedIn;
    }
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    public void setEnabled(final boolean enabled) {
        this.isEnabled = enabled;
    }
    
    public int getSelectionEnd() {
        return this.selectionEnd;
    }
    
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
    }
    
    public void setSelectionPos(int position) {
        final int i = this.text.length();
        if (position > i) {
            position = i;
        }
        if (position < 0) {
            position = 0;
        }
        this.selectionEnd = position;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > i) {
                this.lineScrollOffset = i;
            }
            final int j = this.getWidth();
            final String s = this.fontRendererInstance.a(this.text.substring(this.lineScrollOffset), j);
            final int k = s.length() + this.lineScrollOffset;
            if (position == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.a(this.text, j, true).length();
            }
            if (position > k) {
                this.lineScrollOffset += position - k;
            }
            else if (position <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - position;
            }
            this.lineScrollOffset = LabyModCore.getMath().clamp_int(this.lineScrollOffset, 0, i);
        }
    }
    
    public void setCanLoseFocus(final boolean canLoseFocusIn) {
        this.canLoseFocus = canLoseFocusIn;
    }
    
    public boolean getVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean isVisible) {
        this.visible = isVisible;
    }
    
    public boolean isColorBarEnabled() {
        return this.colorBarEnabled;
    }
    
    public void setColorBarEnabled(final boolean colorBar) {
        this.colorBarEnabled = colorBar;
    }
}
