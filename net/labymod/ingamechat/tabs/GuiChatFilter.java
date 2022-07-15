//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.tabs;

import net.labymod.ingamechat.*;
import net.labymod.gui.elements.*;
import net.labymod.main.*;
import net.labymod.core.*;
import org.lwjgl.input.*;
import java.io.*;
import net.labymod.utils.*;
import net.labymod.main.lang.*;
import net.labymod.ingamechat.tools.filter.*;
import java.awt.*;
import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import java.lang.reflect.*;

public class GuiChatFilter extends GuiChatCustom
{
    private Scrollbar scrollbar;
    private Filters.Filter selectedFilter;
    private bje textFieldFilterName;
    private bje textFieldFilterContains;
    private bje textFieldFilterContainsNot;
    private bje textFieldFilterSoundfile;
    private bje textFieldFilterRoom;
    private int sliderDrag;
    private boolean markFilterNameRed;
    private boolean markContainsRed;
    private boolean markSoundNameRed;
    private String editStartName;
    private boolean canScroll;
    private static List<String> soundNames;
    
    public GuiChatFilter(final String defaultText) {
        super(defaultText);
        this.scrollbar = new Scrollbar(15);
        this.sliderDrag = -1;
        this.markFilterNameRed = false;
        this.markContainsRed = false;
        this.markSoundNameRed = false;
        this.editStartName = "";
    }
    
    public void b() {
        super.b();
        this.scrollbar.setPosition(this.l - 6, this.m - 196, this.l - 5, this.m - 20);
        this.scrollbar.update(LabyMod.getInstance().getChatToolManager().getFilters().size());
        this.scrollbar.setSpeed(10);
        this.scrollbar.setEntryHeight(10.0);
        this.textFieldFilterName = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.textFieldFilterContains = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.textFieldFilterContainsNot = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.textFieldFilterSoundfile = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.textFieldFilterRoom = new bje(0, LabyModCore.getMinecraft().getFontRenderer(), 0, 0, 110, 10);
        this.markContainsRed = false;
        this.markFilterNameRed = false;
        this.markSoundNameRed = false;
        this.selectedFilter = null;
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
        a(this.l - 150, this.m - 220, this.l - 2, this.m - 16, Integer.MIN_VALUE);
        this.canScroll = (mouseX > this.l - 150 && mouseX < this.l - 2 && mouseY > this.m - 150 && mouseY < this.m - 16);
        int row = 0;
        for (final Filters.Filter component : LabyMod.getInstance().getChatToolManager().getFilters()) {
            final double posY = this.m - 215 + row * 10 + this.scrollbar.getScrollY();
            ++row;
            if (posY >= this.m - 220) {
                if (posY > this.m - 25) {
                    continue;
                }
                final boolean hover = this.selectedFilter == null && mouseX > this.l - 150 + 1 && mouseX < this.l - 2 - 1 && mouseY > posY - 1.0 && mouseY < posY + 9.0;
                if (hover || (this.selectedFilter != null && (this.selectedFilter.getFilterName().equalsIgnoreCase(component.getFilterName()) || component.getFilterName().equalsIgnoreCase(this.editStartName)))) {
                    a(this.l - 150 + 1, (int)posY - 1, this.l - 2 - 1, (int)posY + 9, hover ? ModColor.toRGB(100, 200, 200, 100) : Integer.MAX_VALUE);
                }
                this.c(LabyModCore.getMinecraft().getFontRenderer(), LabyMod.getInstance().getDrawUtils().trimStringToWidth(component.getFilterName(), 130), this.l - 145, (int)posY, Integer.MAX_VALUE);
                if (this.selectedFilter != null) {
                    continue;
                }
                final boolean hoverX = mouseX > this.l - 12 - 1 && mouseX < this.l - 12 + 7 && mouseY > posY && mouseY < posY + 8.0;
                this.c(LabyModCore.getMinecraft().getFontRenderer(), ModColor.cl(hoverX ? "c" : "4") + "\u2718", this.l - 12, (int)posY, Integer.MAX_VALUE);
            }
        }
        if (!this.scrollbar.isHidden()) {
            a(this.l - 6, this.m - 145, this.l - 5, this.m - 20, Integer.MIN_VALUE);
            a(this.l - 7, (int)this.scrollbar.getTop(), this.l - 4, (int)(this.scrollbar.getTop() + this.scrollbar.getBarLength()), Integer.MAX_VALUE);
        }
        if (this.selectedFilter == null) {
            final boolean hover2 = mouseX > this.l - 165 && mouseX < this.l - 152 && mouseY > this.m - 220 && mouseY < this.m - 220 + 13;
            a(this.l - 165, this.m - 220, this.l - 152, this.m - 220 + 13, hover2 ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), "+", this.l - 158, this.m - 217, hover2 ? ModColor.toRGB(50, 220, 120, 210) : Integer.MAX_VALUE);
        }
        else {
            int relYAtSoundHint = 0;
            int relYAtRoomHint = 0;
            a(this.l - 270, this.m - 220, this.l - 152, this.m - 16, Integer.MIN_VALUE);
            final int relX = this.l - 270;
            int relY = this.m - 220;
            this.drawElementTextField("name", this.textFieldFilterName, relX, relY, mouseX, mouseY);
            this.drawElementTextField("contains", this.textFieldFilterContains, relX, relY + 23, mouseX, mouseY);
            this.drawElementTextField("contains_not", this.textFieldFilterContainsNot, relX, relY + 46, mouseX, mouseY);
            this.drawElementTextField("room", this.textFieldFilterRoom, relX, relY + 69, mouseX, mouseY);
            relYAtRoomHint = relY + 69;
            relY += 23;
            this.drawElementCheckBox("play_sound", this.selectedFilter.isPlaySound(), relX, relY + 69, mouseX, mouseY);
            if (this.selectedFilter.isPlaySound()) {
                this.drawElementTextField("", this.textFieldFilterSoundfile, relX, relY + 69, mouseX, mouseY);
                relYAtSoundHint = relY + 69;
            }
            else {
                relY -= 10;
            }
            this.drawElementCheckBox("highlight", this.selectedFilter.isHighlightMessage(), relX, relY + 92, mouseX, mouseY);
            if (this.selectedFilter.isHighlightMessage() && this.selectedFilter.getHighlightColor() != null) {
                this.drawElementSlider(this.selectedFilter.getHighlightColor().getRed(), relX, relY + 92 + 15, 0, mouseX, mouseY);
                this.drawElementSlider(this.selectedFilter.getHighlightColor().getGreen(), relX, relY + 92 + 15 + 9, 1, mouseX, mouseY);
                this.drawElementSlider(this.selectedFilter.getHighlightColor().getBlue(), relX, relY + 92 + 15 + 18, 2, mouseX, mouseY);
                a(relX + 85, relY + 92 + 1, relX + 85 + 9, relY + 92 + 1 + 9, this.selectedFilter.getHighlightColor().getRGB());
            }
            else {
                relY -= 26;
            }
            this.drawElementCheckBox("hide", this.selectedFilter.isHideMessage(), relX, relY + 115 + 15, mouseX, mouseY);
            this.drawElementCheckBox("second_chat", this.selectedFilter.isDisplayInSecondChat(), relX, relY + 115 + 15 + 12, mouseX, mouseY);
            this.drawElementCheckBox("tooltip", this.selectedFilter.isFilterTooltips(), relX, relY + 115 + 15 + 24, mouseX, mouseY);
            final boolean hoverCancel = mouseX > this.l - 268 && mouseX < this.l - 213 && mouseY > this.m - 30 && mouseY < this.m - 18;
            final boolean hoverSave = mouseX > this.l - 210 && mouseX < this.l - 154 && mouseY > this.m - 30 && mouseY < this.m - 18;
            a(this.l - 268, this.m - 30, this.l - 213, this.m - 18, hoverCancel ? ModColor.toRGB(200, 100, 100, 200) : Integer.MAX_VALUE);
            a(this.l - 210, this.m - 30, this.l - 154, this.m - 18, hoverSave ? ModColor.toRGB(100, 200, 100, 200) : Integer.MAX_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("button_cancel"), this.l - 262 + 22, this.m - 30 + 2, Integer.MAX_VALUE);
            this.a(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("button_save"), this.l - 205 + 23, this.m - 30 + 2, Integer.MAX_VALUE);
            this.textFieldFilterName.g();
            this.textFieldFilterContains.g();
            this.textFieldFilterContainsNot.g();
            this.textFieldFilterRoom.g();
            if (this.selectedFilter.isPlaySound()) {
                this.textFieldFilterSoundfile.g();
            }
            if (this.textFieldFilterSoundfile.m() && this.selectedFilter.isPlaySound() && this.textFieldFilterSoundfile != null && !this.textFieldFilterSoundfile.b().isEmpty()) {
                int count = 0;
                String hint = "";
                for (final String path : GuiChatFilter.soundNames) {
                    final String lowerCase = this.textFieldFilterSoundfile.b().toLowerCase();
                    if (path.startsWith(lowerCase) && !path.equals(lowerCase)) {
                        hint = hint + path + "\n";
                        if (++count > 5) {
                            break;
                        }
                        continue;
                    }
                }
                if (count == 0) {
                    for (final String path : GuiChatFilter.soundNames) {
                        final String lowerCase = this.textFieldFilterSoundfile.b().toLowerCase();
                        if (path.contains(lowerCase) && !path.equals(lowerCase)) {
                            hint = hint + path + "\n";
                            if (++count > 5) {
                                break;
                            }
                            continue;
                        }
                    }
                }
                if (count != 0) {
                    LabyMod.getInstance().getDrawUtils().drawHoveringText(relX, relYAtSoundHint + 40, hint.split("\n"));
                }
            }
            if (this.textFieldFilterRoom.m()) {
                int count = 0;
                String hint = "";
                if (this.textFieldFilterRoom.b().isEmpty() || "Global".contains(this.textFieldFilterRoom.b().toUpperCase())) {
                    ++count;
                    hint += "Global";
                }
                for (final Filters.Filter filterComponent : LabyMod.getInstance().getChatToolManager().getFilters()) {
                    if (filterComponent.getRoom() != null && filterComponent.getRoom().toLowerCase().contains(this.textFieldFilterRoom.b().toLowerCase()) && !hint.contains(filterComponent.getRoom())) {
                        hint = hint + filterComponent.getRoom() + "\n";
                        ++count;
                    }
                }
                if (count != 0) {
                    LabyMod.getInstance().getDrawUtils().drawHoveringText(relX, relYAtRoomHint + 40, hint.split("\n"));
                }
            }
        }
        this.c(LabyModCore.getMinecraft().getFontRenderer(), LanguageManager.translate("ingame_chat_tab_filter"), this.l - 150, this.m - 230, -1);
        if (this.sliderDrag != -1) {
            this.a(mouseX, mouseY, 0, 0L);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        if (this.selectedFilter == null && mouseX > this.l - 165 && mouseX < this.l - 152 && mouseY > this.m - 220 && mouseY < this.m - 220 + 13) {
            this.loadFilter(new Filters.Filter("", new String[0], new String[0], false, "note.harp", true, (short)200, (short)200, (short)50, false, false, false, "Global"));
        }
        if (this.selectedFilter == null) {
            int row = 0;
            Filters.Filter todoDelete = null;
            for (final Filters.Filter component : LabyMod.getInstance().getChatToolManager().getFilters()) {
                final double posY = this.m - 215 + row * 10 + this.scrollbar.getScrollY();
                ++row;
                if (posY >= this.m - 220) {
                    if (posY > this.m - 25) {
                        continue;
                    }
                    if (mouseX > this.l - 12 - 1 && mouseX < this.l - 12 + 7 && mouseY > posY && mouseY < posY + 8.0) {
                        todoDelete = component;
                    }
                    else {
                        if (mouseX <= this.l - 150 + 1 || mouseX >= this.l - 2 - 1 || mouseY <= posY - 1.0 || mouseY >= posY + 9.0) {
                            continue;
                        }
                        this.loadFilter(new Filters.Filter(component));
                        this.editStartName = component.getFilterName();
                    }
                }
            }
            if (todoDelete != null) {
                LabyMod.getInstance().getChatToolManager().getFilters().remove(todoDelete);
                FilterChatManager.removeFilterComponent(todoDelete);
                LabyMod.getInstance().getChatToolManager().saveTools();
            }
        }
        else {
            if (this.textFieldFilterRoom.b().contains(" ") || this.selectedFilter.getRoom() == null || this.selectedFilter.getRoom().contains(" ")) {
                this.textFieldFilterRoom.a(this.textFieldFilterRoom.b().replaceAll(" ", ""));
                this.selectedFilter.setRoom(this.textFieldFilterRoom.b());
            }
            if (this.textFieldFilterRoom.b().isEmpty() || this.selectedFilter.getRoom() == null || this.selectedFilter.getRoom().isEmpty()) {
                this.textFieldFilterRoom.a("Global");
                this.selectedFilter.setRoom(this.textFieldFilterRoom.b());
            }
            final int relX = this.l - 270;
            int relY = this.m - 220;
            relY += 23;
            if (this.isHoverElementCheckbox("play_sound", this.selectedFilter.isPlaySound(), relX, relY + 69, mouseX, mouseY)) {
                this.selectedFilter.setPlaySound(!this.selectedFilter.isPlaySound());
            }
            if (!this.selectedFilter.isPlaySound()) {
                relY -= 10;
            }
            if (this.isHoverElementCheckbox("highlight", this.selectedFilter.isHighlightMessage(), relX, relY + 92, mouseX, mouseY)) {
                this.selectedFilter.setHighlightMessage(!this.selectedFilter.isHighlightMessage());
            }
            if (this.selectedFilter.isHighlightMessage() && this.selectedFilter.getHighlightColor() != null) {
                this.dragElementSlider(relX, relY + 92 + 15, 0, mouseX, mouseY);
                this.dragElementSlider(relX, relY + 92 + 15 + 9, 1, mouseX, mouseY);
                this.dragElementSlider(relX, relY + 92 + 15 + 18, 2, mouseX, mouseY);
            }
            else {
                relY -= 26;
            }
            if (this.isHoverElementCheckbox("hide", this.selectedFilter.isHideMessage(), relX, relY + 115 + 15, mouseX, mouseY)) {
                this.selectedFilter.setHideMessage(!this.selectedFilter.isHideMessage());
            }
            if (this.isHoverElementCheckbox("second_chat", this.selectedFilter.isDisplayInSecondChat(), relX, relY + 115 + 15 + 12, mouseX, mouseY)) {
                this.selectedFilter.setDisplayInSecondChat(!this.selectedFilter.isDisplayInSecondChat());
            }
            if (this.isHoverElementCheckbox("tooltip", this.selectedFilter.isFilterTooltips(), relX, relY + 115 + 15 + 24, mouseX, mouseY)) {
                this.selectedFilter.setFilterTooltips(!this.selectedFilter.isFilterTooltips());
            }
            final boolean hoverCancel = mouseX > this.l - 268 && mouseX < this.l - 213 && mouseY > this.m - 30 && mouseY < this.m - 18;
            final boolean hoverSave = mouseX > this.l - 210 && mouseX < this.l - 154 && mouseY > this.m - 30 && mouseY < this.m - 18;
            if (hoverCancel) {
                this.selectedFilter = null;
            }
            if (hoverSave) {
                if (this.selectedFilter.getFilterName().replaceAll(" ", "").isEmpty()) {
                    this.markFilterNameRed = true;
                }
                if (this.selectedFilter.getWordsContains().length == 0) {
                    this.markContainsRed = true;
                }
                else {
                    this.markContainsRed = false;
                }
                if (this.selectedFilter.isPlaySound() && !GuiChatFilter.soundNames.contains(this.textFieldFilterSoundfile.b().toLowerCase())) {
                    this.markSoundNameRed = true;
                }
                else {
                    this.markSoundNameRed = false;
                }
                if (!this.markFilterNameRed && !this.markSoundNameRed && !this.markContainsRed) {
                    final Iterator<Filters.Filter> it = LabyMod.getInstance().getChatToolManager().getFilters().iterator();
                    while (it.hasNext()) {
                        final Filters.Filter next = it.next();
                        if (this.editStartName == null) {
                            if (!next.getFilterName().equalsIgnoreCase(this.selectedFilter.getFilterName())) {
                                continue;
                            }
                            it.remove();
                        }
                        else {
                            if (!next.getFilterName().equalsIgnoreCase(this.editStartName)) {
                                continue;
                            }
                            it.remove();
                        }
                    }
                    if (!LabyMod.getInstance().getChatToolManager().getFilters().contains(this.selectedFilter)) {
                        LabyMod.getInstance().getChatToolManager().getFilters().add(this.selectedFilter);
                    }
                    LabyMod.getInstance().getChatToolManager().saveTools();
                    FilterChatManager.getFilterResults().clear();
                    bib.z().q.d().a();
                    this.selectedFilter = null;
                    this.b();
                }
            }
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (this.selectedFilter != null && (this.textFieldFilterName.m() || this.textFieldFilterContains.m() || this.textFieldFilterContainsNot.m() || this.textFieldFilterRoom.m() || this.textFieldFilterSoundfile.m())) {
            if (keyCode == 15) {
                if (this.textFieldFilterName.m()) {
                    this.textFieldFilterName.b(false);
                    this.textFieldFilterContains.b(true);
                    return;
                }
                if (this.textFieldFilterContains.m()) {
                    this.textFieldFilterContains.b(false);
                    this.textFieldFilterContainsNot.b(true);
                    return;
                }
                if (this.textFieldFilterContainsNot.m()) {
                    this.textFieldFilterContainsNot.b(false);
                    this.textFieldFilterRoom.b(true);
                    return;
                }
                if (this.textFieldFilterRoom.m()) {
                    this.textFieldFilterRoom.b(false);
                    if (this.selectedFilter.isPlaySound()) {
                        this.textFieldFilterSoundfile.b(true);
                    }
                    else {
                        this.textFieldFilterName.b(true);
                    }
                    return;
                }
                if (this.textFieldFilterSoundfile.m()) {
                    this.textFieldFilterSoundfile.b(false);
                    this.textFieldFilterName.b(true);
                    return;
                }
            }
            if (this.textFieldFilterName.a(typedChar, keyCode)) {
                final String newText = this.textFieldFilterName.b().replaceAll(" ", "");
                boolean equals = false;
                for (final Filters.Filter filter : LabyMod.getInstance().getChatToolManager().getFilters()) {
                    if (filter.getFilterName().equalsIgnoreCase(newText) && !filter.getFilterName().equalsIgnoreCase(this.editStartName)) {
                        equals = true;
                        break;
                    }
                }
                if (equals) {
                    this.markFilterNameRed = true;
                    return;
                }
                this.selectedFilter.setFilterName(newText);
                this.markFilterNameRed = false;
            }
            if (this.textFieldFilterContains.a(typedChar, keyCode)) {
                this.selectedFilter.setWordsContains(this.splitWords(this.textFieldFilterContains.b()));
                this.markContainsRed = false;
            }
            if (this.textFieldFilterContainsNot.a(typedChar, keyCode)) {
                this.selectedFilter.setWordsContainsNot(this.splitWords(this.textFieldFilterContainsNot.b()));
            }
            if (this.textFieldFilterRoom.a(typedChar, keyCode)) {
                this.selectedFilter.setRoom(this.textFieldFilterRoom.b());
            }
            if (this.textFieldFilterSoundfile.a(typedChar, keyCode)) {
                this.selectedFilter.setSoundPath(this.textFieldFilterSoundfile.b().toLowerCase());
                this.markSoundNameRed = false;
            }
        }
        else {
            super.a(typedChar, keyCode);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (this.selectedFilter != null) {
            final int relX = this.l - 270;
            int relY = this.m - 200;
            if (!this.selectedFilter.isPlaySound()) {
                relY -= 10;
            }
            if (this.selectedFilter.isHighlightMessage() && this.selectedFilter.getHighlightColor() != null) {
                if (this.sliderDrag == 0) {
                    this.dragElementSlider(relX, relY + 92 + 15, 0, mouseX, mouseY);
                }
                if (this.sliderDrag == 1) {
                    this.dragElementSlider(relX, relY + 92 + 15 + 9, 1, mouseX, mouseY);
                }
                if (this.sliderDrag == 2) {
                    this.dragElementSlider(relX, relY + 92 + 15 + 18, 2, mouseX, mouseY);
                }
            }
        }
    }
    
    protected void b(final int mouseX, final int mouseY, final int mouseButton) {
        super.b(mouseX, mouseY, mouseButton);
        this.sliderDrag = -1;
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        this.textFieldFilterName.a(mouseX, mouseY, mouseButton);
        this.textFieldFilterContains.a(mouseX, mouseY, mouseButton);
        this.textFieldFilterContainsNot.a(mouseX, mouseY, mouseButton);
        this.textFieldFilterRoom.a(mouseX, mouseY, mouseButton);
        this.textFieldFilterSoundfile.a(mouseX, mouseY, mouseButton);
    }
    
    public void e() {
        super.e();
        this.textFieldFilterName.a();
        this.textFieldFilterContains.a();
        this.textFieldFilterContainsNot.a();
        this.textFieldFilterRoom.a();
        this.textFieldFilterSoundfile.a();
    }
    
    private void drawElementTextField(String prefix, final bje textField, final int x, final int y, final int mouseX, final int mouseY) {
        if (!prefix.isEmpty()) {
            prefix = LanguageManager.translate("chat_filter_" + prefix) + ":";
        }
        this.c(LabyModCore.getMinecraft().getFontRenderer(), prefix, x + 2, y + 2, Integer.MAX_VALUE);
        a(x + 2, y + 12, x + 2 + 114, y + 12 + 10, ((this.markContainsRed && textField != null && textField.equals(this.textFieldFilterContains)) || (this.markFilterNameRed && textField != null && textField.equals(this.textFieldFilterName)) || (this.markSoundNameRed && textField != null && textField.equals(this.textFieldFilterSoundfile))) ? ModColor.toRGB(200, 100, 100, 200) : Integer.MAX_VALUE);
        if (textField == null) {
            return;
        }
        LabyModCore.getMinecraft().setTextFieldXPosition(textField, x + 2);
        LabyModCore.getMinecraft().setTextFieldYPosition(textField, y + 13);
        textField.a(false);
        textField.f(120);
    }
    
    private void drawElementCheckBox(String text, final boolean check, int x, final int y, final int mouseX, final int mouseY) {
        final boolean hover = this.isHoverElementCheckbox(text, check, x, y, mouseX, mouseY);
        text = LanguageManager.translate("chat_filter_" + text);
        this.c(LabyModCore.getMinecraft().getFontRenderer(), text, x + 2, y + 2, Integer.MAX_VALUE);
        x += LabyModCore.getMinecraft().getFontRenderer().a(text) + 2;
        a(x + 3, y + 1, x + 12, y + 10, hover ? 2147483547 : Integer.MAX_VALUE);
        if (!check) {
            return;
        }
        this.a(LabyModCore.getMinecraft().getFontRenderer(), ModColor.cl("a") + "\u2714", x + 8, y + 1, Integer.MAX_VALUE);
    }
    
    private void drawElementSlider(final int value, final int x, final int y, final int id, final int mouseX, final int mouseY) {
        a(x + 2, y, x + 2 + 94, y + 1, Integer.MAX_VALUE);
        final double colorValue = value;
        final double percent = colorValue / 255.0 * 94.0;
        final int pos = (int)percent;
        a(x + 2 + pos, y - 3, x + 2 + pos + 2, y + 5, ModColor.toRGB((id == 0) ? value : 0, (id == 1) ? value : 0, (id == 2) ? value : 0, 255));
    }
    
    private boolean isHoverElementCheckbox(String text, final boolean check, int x, final int y, final int mouseX, final int mouseY) {
        text = LanguageManager.translate("chat_filter_" + text);
        x += LabyModCore.getMinecraft().getFontRenderer().a(text) + 2;
        return mouseX > x + 3 && mouseX < x + 12 && mouseY > y + 1 && mouseY < y + 10;
    }
    
    private void dragElementSlider(final int x, final int y, final int id, int mouseX, final int mouseY) {
        if (mouseX <= x || mouseX >= x + 94 || ((mouseY <= y - 5 || mouseY >= y + 5) && this.sliderDrag != id)) {
            return;
        }
        if (mouseX < x) {
            mouseX = x;
        }
        if (mouseX > x + 94) {
            mouseX = x + 94;
        }
        final double pos = mouseX - x;
        final double value = pos * 255.0 / 94.0;
        final int colorValue = (int)value;
        final Color highlightColor = this.selectedFilter.getHighlightColor();
        int r = highlightColor.getRed();
        int g = highlightColor.getGreen();
        int b = highlightColor.getBlue();
        switch (id) {
            case 0: {
                r = colorValue;
                break;
            }
            case 1: {
                g = colorValue;
                break;
            }
            case 2: {
                b = colorValue;
                break;
            }
        }
        this.selectedFilter.setHighlightColorR((short)r);
        this.selectedFilter.setHighlightColorG((short)g);
        this.selectedFilter.setHighlightColorB((short)b);
        this.sliderDrag = id;
    }
    
    private void loadFilter(final Filters.Filter filter) {
        this.editStartName = null;
        this.selectedFilter = filter;
        this.markFilterNameRed = false;
        this.textFieldFilterName.a(filter.getFilterName());
        this.textFieldFilterContains.a(this.wordsToString(filter.getWordsContains()));
        this.textFieldFilterContainsNot.a(this.wordsToString(filter.getWordsContainsNot()));
        this.textFieldFilterRoom.a((filter.getRoom() == null || filter.getRoom().isEmpty()) ? "Global" : filter.getRoom());
        this.textFieldFilterSoundfile.a(filter.getSoundPath());
    }
    
    private String[] splitWords(final String text) {
        return text.contains(",") ? text.split(",") : (text.isEmpty() ? new String[0] : new String[] { text });
    }
    
    private String wordsToString(final String[] words) {
        String output = "";
        for (final String word : words) {
            if (!output.isEmpty()) {
                output += ",";
            }
            output += word;
        }
        return output;
    }
    
    static {
        GuiChatFilter.soundNames = new ArrayList<String>();
        try {
            final Field soundRegistryInSoundHandlerField = ReflectionHelper.findField(cho.class, LabyModCore.getMappingAdapter().getSoundRegistryInSoundHandlerMappings());
            final Field soundRegistryInSoundRegistryField = ReflectionHelper.findField(chp.class, LabyModCore.getMappingAdapter().getSoundRegistryInSoundRegistryMappings());
            final chp soundRegistry = (chp)soundRegistryInSoundHandlerField.get(bib.z().U());
            final Map sounds = (Map)soundRegistryInSoundRegistryField.get(soundRegistry);
            for (final Object resourceObject : sounds.keySet()) {
                GuiChatFilter.soundNames.add(((nf)resourceObject).a());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
