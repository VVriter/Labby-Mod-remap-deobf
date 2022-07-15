//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.settings;

import net.labymod.ingamegui.enums.*;
import net.labymod.main.*;
import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.lang.*;
import org.lwjgl.input.*;
import net.labymod.gui.elements.*;
import net.labymod.core.*;
import java.io.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;
import java.util.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;

public class LabyModModuleEditorGui extends blk
{
    private static EnumDisplayType selectedTab;
    private static final ModuleGui moduleGui;
    public static String selectedProfile;
    public static final String GLOBAL_PROFILE;
    public static final String SINGLEPLAYER_PROFILE;
    private blk lastScreen;
    private boolean mouseClickSplitScreen;
    private Scrollbar scrollbar;
    private SettingsElement mouseOverElement;
    private BooleanElement masterElement;
    private List<SettingsElement> path;
    private bja buttonGoBack;
    private ModTextField textFieldSearch;
    private String searchedString;
    private ControlElement selectedControlElement;
    private long lightUpSelectedElement;
    private EnumDisplayType hoveredTab;
    private DropDownMenu<EnumModuleEditorScale> scalingDropdown;
    private DropDownMenu<String> profilesDropdown;
    private boolean hoverResetButton;
    private Consumer<Module> moduleDoubleClickModuleListener;
    private Consumer<Module> moduleClickModuleListener;
    
    public LabyModModuleEditorGui() {
        this(null);
    }
    
    public LabyModModuleEditorGui(final blk lastScreen) {
        this.mouseClickSplitScreen = false;
        this.path = new ArrayList<SettingsElement>();
        this.searchedString = "";
        this.lightUpSelectedElement = 0L;
        this.hoveredTab = null;
        this.hoverResetButton = false;
        this.moduleDoubleClickModuleListener = new Consumer<Module>() {
            @Override
            public void accept(final Module accepted) {
                final ControlElement element = (ControlElement)accepted.getBooleanElement();
                if (element.hasSubList() && element.getButtonAdvanced().l) {
                    element.getButtonAdvanced().a(bib.z().U());
                    LabyModModuleEditorGui.this.path.clear();
                    if (LabyModModuleEditorGui.selectedTab == EnumDisplayType.INGAME) {
                        if (element.getModule() != null && element.getModule().getCategory() != null && element.getModule().getCategory().getCategoryElement() != null) {
                            LabyModModuleEditorGui.this.path.add(element.getModule().getCategory().getCategoryElement());
                        }
                        else {
                            LabyModModuleEditorGui.this.path.add(element);
                        }
                    }
                    LabyModModuleEditorGui.moduleGui.setFocusedModule(element.getModule());
                    LabyModModuleEditorGui.this.selectedControlElement = element;
                    LabyModModuleEditorGui.this.lightUpSelectedElement = System.currentTimeMillis();
                    LabyModModuleEditorGui.this.initList(true);
                    LabyModModuleEditorGui.this.updateScrollbarValues(4);
                    int index = -1;
                    final List<SettingsElement> foundModules = LabyModModuleEditorGui.this.getFoundModules();
                    for (final SettingsElement settingsElement : foundModules) {
                        ++index;
                        if (!settingsElement.equals(element)) {
                            continue;
                        }
                        LabyModModuleEditorGui.this.scrollbar.scrollTo(index);
                        break;
                    }
                }
            }
        };
        this.moduleClickModuleListener = new Consumer<Module>() {
            @Override
            public void accept(final Module module) {
                if (module == null) {
                    return;
                }
                final ControlElement element = (ControlElement)module.getBooleanElement();
                if (element.hasSubList() && element.getButtonAdvanced().l) {
                    LabyModModuleEditorGui.moduleGui.setFocusedModule(element.getModule());
                    if (LabyModModuleEditorGui.this.masterElement != null && LabyModModuleEditorGui.this.masterElement.equals(LabyModModuleEditorGui.this.getCurrentSelectedSubList())) {
                        return;
                    }
                    LabyModModuleEditorGui.this.selectedControlElement = element;
                    LabyModModuleEditorGui.this.initList(false);
                }
            }
        };
        this.lastScreen = lastScreen;
        if (LabyMod.getSettings().moduleEditorZoom == -1.0) {
            LabyMod.getSettings().moduleEditorZoom = 33.333333333333336;
        }
        Module.setLastDrawnDisplayType(LabyModModuleEditorGui.selectedTab);
        this.scrollbar = new Scrollbar(24);
        this.masterElement = new BooleanElement("LabyMod GUI", new ControlElement.IconData(ModTextures.LOGO_LABYMOD_LOGO), (Consumer)new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                ModuleConfig.getConfig().setModulesEnabled((boolean)accepted);
            }
        }, ModuleConfig.getConfig().isModulesEnabled());
        DefaultElementsCreator.createAlignment((Module)null, true, (List)this.masterElement.getSubSettings().getElements());
        DefaultElementsCreator.createFormatting((TextModule)null, true, (List)this.masterElement.getSubSettings().getElements());
        DefaultElementsCreator.createColorPicker((TextModule)null, true, (List)this.masterElement.getSubSettings().getElements());
        DefaultElementsCreator.createDurability((ItemModule)null, true, (List)this.masterElement.getSubSettings().getElements());
        DefaultElementsCreator.createKeyVisible((TextModule)null, true, (List)this.masterElement.getSubSettings().getElements());
        DefaultElementsCreator.createBackgroundVisible((Module)null, true, (List)this.masterElement.getSubSettings().getElements());
        DefaultElementsCreator.createPadding((Module)null, true, (List)this.masterElement.getSubSettings().getElements());
        this.masterElement.getSubSettings().getElements().add((SettingsElement)new BooleanElement(LanguageManager.translate("grid_switch"), new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_GRID_MOVEMENT), (Consumer)new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                ModuleConfig.getConfig().setGridEnabled((boolean)accepted);
            }
        }, ModuleConfig.getConfig().isGridEnabled()));
        this.masterElement.getSubSettings().getElements().add((SettingsElement)new BooleanElement(LanguageManager.translate("item_gravity"), new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_ITEM_GRAVITY), (Consumer)new Consumer<Boolean>() {
            @Override
            public void accept(final Boolean accepted) {
                ModuleConfig.getConfig().setItemSlotGravity((boolean)accepted);
            }
        }, ModuleConfig.getConfig().isItemSlotGravity()));
        this.masterElement.getSubSettings().getElements().add((SettingsElement)new SliderElement(LanguageManager.translate("gui_scale"), new ControlElement.IconData(ModTextures.SETTINGS_DEFAULT_GUI_SCALING), ModuleConfig.getConfig().getGuiScale()).setRange(50, 150).setSteps(5).addCallback((Consumer)new Consumer<Integer>() {
            @Override
            public void accept(final Integer accepted) {
                ModuleConfig.getConfig().setGuiScale((int)accepted);
            }
        }));
    }
    
    public void m() {
        ModuleConfig.getConfigManager().save();
        PreviewRenderer.getInstance().kill();
        Keyboard.enableRepeatEvents(false);
        LabyModModuleEditorGui.moduleGui.m();
        Module.setCurrentModuleGui((ModuleGui)null);
    }
    
    public void b() {
        Keyboard.enableRepeatEvents(true);
        PreviewRenderer.getInstance().init(LabyModModuleEditorGui.class);
        Module.setCurrentModuleGui(LabyModModuleEditorGui.moduleGui);
        LabyModModuleEditorGui.moduleGui.b();
        this.initList(true);
        Tabs.initGui((blk)this);
    }
    
    private void initList(final boolean scrollUp) {
        this.n.clear();
        if (!LabyModModuleEditorGui.moduleGui.getClickModuleListeners().contains(this.moduleClickModuleListener)) {
            LabyModModuleEditorGui.moduleGui.getClickModuleListeners().add(this.moduleClickModuleListener);
        }
        if (!LabyModModuleEditorGui.moduleGui.getDoubleClickModuleListeners().contains(this.moduleDoubleClickModuleListener)) {
            LabyModModuleEditorGui.moduleGui.getDoubleClickModuleListeners().add(this.moduleDoubleClickModuleListener);
        }
        this.scrollbar.setPosition(LabyMod.getSettings().moduleEditorSplitX - 8, 0, LabyMod.getSettings().moduleEditorSplitX - 4, this.m - 31);
        this.scrollbar.setSpeed(10);
        if (scrollUp) {
            this.scrollbar.setScrollY(0.0);
        }
        this.updateScrollbarValues(4);
        this.scrollbar.init();
        final boolean displayBackButton = this.path.size() != 0;
        (this.textFieldSearch = new ModTextField(0, LabyModCore.getMinecraft().getFontRenderer(), displayBackButton ? 60 : 6, 0, LabyMod.getSettings().moduleEditorSplitX - (displayBackButton ? 69 : 12), 14)).setText(this.searchedString);
        if (!displayBackButton) {
            this.textFieldSearch.setFocused(true);
        }
        this.textFieldSearch.setCursorPositionEnd();
        this.textFieldSearch.setPlaceHolder(LanguageManager.translate("search_textbox_placeholder"));
        if (this.lastScreen != null) {
            this.n.add(new bja(5, 5, this.m - 25, LabyMod.getSettings().moduleEditorSplitX - 10, 20, LanguageManager.translate("button_done")));
        }
        (this.scalingDropdown = (DropDownMenu<EnumModuleEditorScale>)new DropDownMenu("", this.l - 75, this.m - 12 - 5, 70, 12).fill((Object[])EnumModuleEditorScale.values())).setSelected((Object)EnumModuleEditorScale.getByZoom(LabyMod.getSettings().moduleEditorZoom));
        this.scalingDropdown.setMaxY(this.m);
        this.scalingDropdown.setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                LabyMod.getInstance().getDrawUtils().drawString(((EnumModuleEditorScale)object).getDisplayName(), x, y);
            }
        });
        (this.profilesDropdown = (DropDownMenu<String>)new DropDownMenu("", (this.lastScreen == null) ? 5 : (LabyMod.getSettings().moduleEditorSplitX + 5), this.m - 12 - 5, 150, 12)).addOption((Object)LabyModModuleEditorGui.GLOBAL_PROFILE);
        String currentServer = null;
        if (bib.z().C() != null) {
            this.profilesDropdown.addOption((Object)(currentServer = ModUtils.getProfileNameByIp(bib.z().C().b)));
        }
        if (bib.z().E()) {
            this.profilesDropdown.addOption((Object)LabyModModuleEditorGui.SINGLEPLAYER_PROFILE);
        }
        for (final File file : ModuleConfig.listProfileFiles()) {
            final String profileName = file.getName().replace(".json", "");
            if (!profileName.equals("singleplayer") && (currentServer == null || !profileName.equals(currentServer))) {
                this.profilesDropdown.addOption((Object)profileName);
            }
        }
        this.profilesDropdown.setSelected((Object)((LabyModModuleEditorGui.selectedProfile == null) ? LabyModModuleEditorGui.GLOBAL_PROFILE : (LabyModModuleEditorGui.selectedProfile.equals("singleplayer") ? LabyModModuleEditorGui.SINGLEPLAYER_PROFILE : LabyModModuleEditorGui.selectedProfile)));
        this.profilesDropdown.setMaxY(this.m);
        this.profilesDropdown.setEntryDrawer((DropDownMenu.DropDownEntryDrawer)new DropDownMenu.DropDownEntryDrawer() {
            public void draw(final Object object, final int x, final int y, final String trimmedEntry) {
                final File file = ModuleConfig.getFile(object.equals(LabyModModuleEditorGui.GLOBAL_PROFILE) ? null : object.toString());
                final String prefix = file.exists() ? "" : (ModColor.cl('e') + "+ " + ModColor.cl('f'));
                LabyMod.getInstance().getDrawUtils().drawString(prefix + object.toString(), x, y);
            }
        });
        this.n.add(this.buttonGoBack = new bja(7, 5, 47, 20, 20, "<"));
        this.checkSplitscreenBorder();
        super.b();
        if (this.path.size() != 0) {
            for (final SettingsElement module : this.getCurrentSelectedSubList().getSubSettings().getElements()) {
                module.init();
            }
        }
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.j.a(this.lastScreen);
            return;
        }
        if (this.textFieldSearch.textboxKeyTyped(typedChar, keyCode)) {
            this.searchedString = this.textFieldSearch.getText();
            this.scrollbar.update(this.getFoundModules().size());
        }
        LabyModModuleEditorGui.moduleGui.a(typedChar, keyCode);
        if (this.selectedControlElement != null) {
            this.selectedControlElement.keyTyped(typedChar, keyCode);
        }
    }
    
    protected void a(final bja button) throws IOException {
        if (button.k == 5) {
            this.j.a(this.lastScreen);
        }
        if (button.k == this.buttonGoBack.k) {
            this.searchedString = "";
            if (this.path.size() != 0) {
                this.path.remove(this.path.size() - 1);
            }
            LabyModModuleEditorGui.moduleGui.setFocusedModule((Module)null);
            this.b();
        }
        super.a(button);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (Tabs.mouseClicked((blk)this)) {
            return;
        }
        if (this.scalingDropdown.onClick(mouseX, mouseY, mouseButton) && this.scalingDropdown.getSelected() != null && !this.scalingDropdown.isOpen()) {
            final double scaling = ((EnumModuleEditorScale)this.scalingDropdown.getSelected()).getScaling() / 3.0 * 100.0;
            LabyMod.getSettings().moduleEditorZoom = scaling;
            bib.z().a((blk)new LabyModModuleEditorGui(this.lastScreen));
            return;
        }
        if (this.profilesDropdown.onClick(mouseX, mouseY, mouseButton) && this.profilesDropdown.getSelected() != null && !this.profilesDropdown.isOpen()) {
            LabyModModuleEditorGui.selectedProfile = (String)this.profilesDropdown.getSelected();
            if (LabyModModuleEditorGui.selectedProfile != null && LabyModModuleEditorGui.selectedProfile.equals(LabyModModuleEditorGui.GLOBAL_PROFILE)) {
                LabyModModuleEditorGui.selectedProfile = null;
            }
            ModuleConfig.switchProfile(LabyModModuleEditorGui.selectedProfile, true);
            bib.z().a((blk)new LabyModModuleEditorGui(this.lastScreen));
            return;
        }
        if (this.hoverResetButton) {
            bib.z().a((blk)new bkq((bkp)new bkp() {
                public void a(final boolean flag, final int id) {
                    if (!flag) {
                        ModuleConfig.deleteProfile(LabyModModuleEditorGui.selectedProfile);
                    }
                    bib.z().a((blk)new LabyModModuleEditorGui(LabyModModuleEditorGui.this.lastScreen));
                }
            }, LanguageManager.translate((LabyModModuleEditorGui.selectedProfile == null) ? LanguageManager.translate("reset_modules_global") : LanguageManager.translate("reset_modules_profile", new Object[] { LabyModModuleEditorGui.selectedProfile })), LanguageManager.translate("reset_modules_undone"), LanguageManager.translate("button_no"), LanguageManager.translate("button_yes"), 0));
        }
        if (mouseY > this.m - 30) {
            super.a(mouseX, mouseY, mouseButton);
            return;
        }
        final List<SettingsElement> foundModules = this.getFoundModules();
        for (final SettingsElement element : foundModules) {
            if (element instanceof DropDownElement && ((DropDownElement)element).onClickDropDown(mouseX, mouseY, mouseButton)) {
                return;
            }
            if (element instanceof ColorPickerCheckBoxBulkElement && ((ColorPickerCheckBoxBulkElement)element).onClickBulkEntry(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
        this.textFieldSearch.mouseClicked(mouseX, mouseY, mouseButton);
        this.unfocusSubListTextfields(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        if (this.mouseOverElement != null) {
            boolean flag = true;
            if (this.mouseOverElement instanceof ControlElement) {
                final ControlElement element2 = (ControlElement)this.mouseOverElement;
                final boolean mouseOver = mouseX < LabyMod.getSettings().moduleEditorSplitX - element2.getObjectWidth() - element2.getSubListButtonWidth() - (this.scrollbar.isHidden() ? 5 : 10) - 2;
                final Module module = element2.getModule();
                if (module != null && mouseOver) {
                    this.selectedControlElement = element2;
                    LabyModModuleEditorGui.moduleGui.setFocusedModule(module);
                    this.textFieldSearch.setFocused(false);
                    this.unfocusSubListTextfields(mouseX, mouseY, mouseButton);
                    flag = false;
                }
            }
            if (this.mouseOverElement instanceof CategoryModuleEditorElement) {
                this.path.add(this.mouseOverElement);
                LabyModModuleEditorGui.moduleGui.setFocusedModule((Module)null);
            }
            if (flag) {
                this.mouseOverElement.mouseClicked(mouseX, mouseY, mouseButton);
            }
            if (this.mouseOverElement instanceof ControlElement) {
                this.selectedControlElement = (ControlElement)this.mouseOverElement;
            }
            this.updateScrollbarValues(4);
        }
        if (!this.mouseClickSplitScreen) {
            LabyModModuleEditorGui.moduleGui.a(mouseX, mouseY, mouseButton);
        }
        if (this.mouseOverElement != null && this.mouseOverElement instanceof ControlElement) {
            final ControlElement element3 = (ControlElement)this.mouseOverElement;
            if (element3.hasSubList() && element3.getButtonAdvanced().a() && element3.getButtonAdvanced().l) {
                element3.getButtonAdvanced().a(this.j.U());
                this.path.add((SettingsElement)element3);
                LabyModModuleEditorGui.moduleGui.setFocusedModule(element3.getModule());
                this.b();
            }
        }
        if (mouseButton == 0 && mouseX > LabyMod.getSettings().moduleEditorSplitX - 5 && mouseX < LabyMod.getSettings().moduleEditorSplitX + 2 && mouseY > 45 && mouseY < this.m - 30) {
            this.mouseClickSplitScreen = true;
        }
        if (this.hoveredTab != null && LabyModModuleEditorGui.selectedTab != this.hoveredTab) {
            LabyModModuleEditorGui.selectedTab = this.hoveredTab;
            for (final Module module2 : Module.getModules()) {
                module2.setBooleanElement((BooleanElement)null);
            }
            for (final ModuleCategory moduleCategory : ModuleCategoryRegistry.getCategories()) {
                moduleCategory.setCategoryElement((CategoryModuleEditorElement)null);
            }
            Module.setLastDrawnDisplayType(LabyModModuleEditorGui.selectedTab);
            LabyModModuleEditorGui.moduleGui.setDisplayType(LabyModModuleEditorGui.selectedTab);
            this.path.clear();
            this.b();
        }
        super.a(mouseX, mouseY, mouseButton);
    }
    
    private void unfocusSubListTextfields(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.path.size() != 0) {
            for (final SettingsElement moduleElement : this.getCurrentSelectedSubList().getSubSettings().getElements()) {
                moduleElement.unfocus(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    private SettingsElement getCurrentSelectedSubList() {
        return (this.path.size() == 0) ? null : this.path.get(this.path.size() - 1);
    }
    
    protected void b(final int mouseX, final int mouseY, final int mouseButton) {
        super.b(mouseX, mouseY, mouseButton);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        if (!this.mouseClickSplitScreen) {
            LabyModModuleEditorGui.moduleGui.b(mouseX, mouseY, mouseButton);
        }
        if (mouseButton == 0) {
            this.mouseClickSplitScreen = false;
        }
        if (this.mouseOverElement != null) {
            this.mouseOverElement.mouseRelease(mouseX, mouseY, mouseButton);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
        if (!this.mouseClickSplitScreen) {
            LabyModModuleEditorGui.moduleGui.a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
        if (this.mouseClickSplitScreen) {
            LabyMod.getSettings().moduleEditorSplitX = mouseX;
            this.checkSplitscreenBorder();
            this.b();
            this.textFieldSearch.setFocused(false);
        }
        if (this.mouseOverElement != null) {
            this.mouseOverElement.mouseClickMove(mouseX, mouseY, clickedMouseButton);
        }
    }
    
    private void checkSplitscreenBorder() {
        if (LabyMod.getSettings().moduleEditorSplitX < 180) {
            LabyMod.getSettings().moduleEditorSplitX = 180;
        }
        if (LabyMod.getSettings().moduleEditorSplitX > 300) {
            LabyMod.getSettings().moduleEditorSplitX = 300;
        }
    }
    
    public void k() throws IOException {
        super.k();
        this.scrollbar.mouseInput();
        if (!this.mouseClickSplitScreen) {
            LabyModModuleEditorGui.moduleGui.k();
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final bit scaled = LabyMod.getInstance().getDrawUtils().getScaledResolution();
        final double rescale = scaled.e() / LabyMod.getInstance().getDrawUtils().getCustomScaling();
        bib.z().b().a(true);
        final int left = LabyMod.getSettings().moduleEditorSplitX - 1;
        final int top = 38 + (int)(20.0 * rescale);
        final int right = this.l - 1;
        final int bottom = this.m - 31;
        final int scrollBarListY = top - 26;
        LabyMod.getInstance().getDrawUtils().drawDimmedOverlayBackground(0, scrollBarListY, this.l, this.m - 30);
        this.renderPreview(LabyModModuleEditorGui.selectedTab, left, top + 1, right, bottom - 1, mouseX, mouseY);
        Module.draw((double)left, (double)top, (double)right, (double)bottom, LabyModModuleEditorGui.selectedTab, LabyModModuleEditorGui.selectedTab == EnumDisplayType.INGAME);
        LabyModModuleEditorGui.moduleGui.a(mouseX, mouseY, partialTicks);
        final List<SettingsElement> foundModules = this.getFoundModules();
        Collections.reverse(foundModules);
        double totalHeight = 0.0;
        for (final SettingsElement element : foundModules) {
            totalHeight += element.getEntryHeight() + 1;
        }
        final double moduleListStartY = scrollBarListY + 28 + ((this.path.size() == 0) ? 20 : 0) + this.scrollbar.getScrollY();
        this.updateScrollbarValues(scrollBarListY);
        this.scrollbar.draw(mouseX, mouseY);
        this.renderModuleList(foundModules, moduleListStartY, totalHeight, mouseX, mouseY);
        draw.drawOverlayBackground(0, top);
        draw.drawOverlayBackground(bottom, this.m);
        draw.drawOverlayBackground(0, 0, LabyMod.getSettings().moduleEditorSplitX - 4, scrollBarListY + 25);
        draw.drawGradientShadowTop(top, 0.0, LabyMod.getSettings().moduleEditorSplitX - 1);
        draw.drawGradientShadowBottom(bottom, 0.0, LabyMod.getSettings().moduleEditorSplitX - 1);
        final boolean hoverSplitscreen = mouseX > LabyMod.getSettings().moduleEditorSplitX - 5 && mouseX < LabyMod.getSettings().moduleEditorSplitX + 1 && mouseY > 45 && mouseY < this.m - 30;
        if (hoverSplitscreen || this.mouseClickSplitScreen) {
            draw.drawRectangle(LabyMod.getSettings().moduleEditorSplitX - 2, top, LabyMod.getSettings().moduleEditorSplitX - 1, this.m - 31, Integer.MAX_VALUE);
            draw.drawCenteredString("|||", mouseX + 1, mouseY - 3);
        }
        if (draw.getWidth() > 400) {
            draw.drawRightString(LanguageManager.translate("labymod_gui_editor"), this.l - 10, top - 18);
        }
        if (this.path.size() != 0) {
            final SettingsElement element2 = this.getCurrentSelectedSubList();
            final int posY = scrollBarListY + 2;
            final List<String> list = draw.listFormattedStringToWidth(element2.getDisplayName(), LabyMod.getSettings().moduleEditorSplitX - 33 - 5 - 15, 3);
            final int fontHeight = 16 - list.size() * 3;
            double listY = posY + 6;
            for (final String line : list) {
                draw.drawString(line, 33.0, listY - list.size() * fontHeight / 2 + fontHeight / 2);
                listY += fontHeight;
            }
            ControlElement.IconData masterIconData = null;
            if (element2 instanceof ControlElement) {
                masterIconData = ((((ControlElement)element2).getModule() == null) ? null : ((ControlElement)element2).getModule().getIconData());
            }
            if (element2 instanceof CategoryModuleEditorElement) {
                masterIconData = ((CategoryModuleEditorElement)element2).getIconData();
            }
            if (masterIconData != null) {
                if (masterIconData.hasTextureIcon()) {
                    bib.z().N().a(masterIconData.getTextureIcon());
                    LabyMod.getInstance().getDrawUtils().drawTexture(LabyMod.getSettings().moduleEditorSplitX - 22, posY + 3, 256.0, 256.0, 16.0, 16.0);
                }
                else if (masterIconData.hasMaterialIcon()) {
                    LabyMod.getInstance().getDrawUtils().drawItem(masterIconData.getMaterialIcon().createItemStack(), LabyMod.getSettings().moduleEditorSplitX - 22, posY + 3, null);
                }
            }
            LabyModCore.getMinecraft().setButtonYPosition(this.buttonGoBack, posY);
        }
        else if (this.path.size() == 0) {
            this.textFieldSearch.yPosition = top + 4;
            this.textFieldSearch.drawTextBox();
            final int masterY = top - 26;
            this.masterElement.setHoverable(true).setSelected(this.selectedControlElement == null).draw(5, masterY, LabyMod.getSettings().moduleEditorSplitX - 4, masterY + 22, mouseX, mouseY);
        }
        this.buttonGoBack.m = (this.path.size() != 0);
        int tabX = left + 3;
        final int tabY = top - 12;
        this.hoveredTab = null;
        for (final EnumDisplayType type : EnumDisplayType.values()) {
            int tabLen = draw.getStringWidth(type.getDisplayName()) + 6;
            final int tabHeight = 12;
            if (mouseX > tabX && mouseX < tabX + tabLen && mouseY > tabY && mouseY < tabY + tabHeight) {
                this.hoveredTab = type;
            }
            final boolean selected = LabyModModuleEditorGui.selectedTab == type;
            final boolean hovered = this.hoveredTab == type;
            final int animate = (selected || hovered) ? (selected ? 2 : 1) : 0;
            if (selected) {
                tabLen += 6;
            }
            final int tabMid = tabX + tabLen / 2;
            draw.drawRectangle(tabX, tabY - animate * 2, tabX + tabLen, tabY + tabHeight, ModColor.toRGB(5, 5, 5, 140));
            draw.drawRectBorder(tabX, tabY - animate * 2, tabX + tabLen, tabY + tabHeight, ModColor.toRGB(5, 5, 5, 140), 1.0);
            if (selected) {
                draw.drawRectangle(tabX + 1, tabY - animate * 2 + 1, tabX + tabLen - 1, tabY + tabHeight, ModColor.toRGB(55, 55, 55, 65));
            }
            draw.drawCenteredString(((selected || hovered) ? ModColor.cl("f") : ModColor.cl("8")) + type.getDisplayName(), tabMid, tabY + 2 - animate);
            tabX += tabLen + 1;
        }
        draw.drawGradientShadowBottom(top, 0.0, this.l);
        draw.drawGradientShadowTop(bottom, 0.0, this.l);
        draw.drawString(LanguageManager.translate("preview_zoom"), this.l - 75, this.m - 26, 0.75);
        this.scalingDropdown.draw(mouseX, mouseY);
        draw.drawString(LanguageManager.translate("module_profiles"), (this.lastScreen == null) ? 5.0 : ((double)(LabyMod.getSettings().moduleEditorSplitX + 5)), this.m - 26, 0.75);
        this.profilesDropdown.draw(mouseX, mouseY);
        final int resetX = ((this.lastScreen == null) ? 5 : (LabyMod.getSettings().moduleEditorSplitX + 5)) + this.profilesDropdown.getWidth() + 5;
        this.hoverResetButton = (mouseX > resetX && mouseX < resetX + 16 && mouseY > this.m - 19 && mouseY < this.m - 19 + 16);
        draw.bindTexture(ModTextures.BUTTON_RESET);
        draw.drawTexture(resetX, this.m - 19, 0.0, this.hoverResetButton ? 127.5 : 0.0, 255.0, 127.5, 16.0, 16.0);
        if (this.hoverResetButton) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, 0L, (LabyModModuleEditorGui.selectedProfile == null) ? LanguageManager.translate("reset_modules_description") : LanguageManager.translate("delete_profile_description", new Object[] { LabyModModuleEditorGui.selectedProfile }));
        }
        if (this.profilesDropdown.isMouseOver(mouseX, mouseY)) {
            TooltipHelper.getHelper().pointTooltip(mouseX, mouseY, LanguageManager.translate("module_profiles_description"));
        }
        this.renderDescriptions(foundModules, moduleListStartY, totalHeight, mouseX, mouseY);
        super.a(mouseX, mouseY, partialTicks);
        Tabs.drawScreen((blk)this, mouseX, mouseY);
    }
    
    private void updateScrollbarValues(final int scrollBarListY) {
        final List<SettingsElement> foundModules = this.getFoundModules();
        double totalHeight = 0.0;
        for (final SettingsElement element : foundModules) {
            totalHeight += element.getEntryHeight() + 1;
        }
        this.scrollbar.setEntryHeight(totalHeight / foundModules.size());
        this.scrollbar.update(foundModules.size());
        this.scrollbar.setPosTop(scrollBarListY + 25 + 2 + ((this.path.size() == 0) ? 24 : 0));
        this.scrollbar.setSpeed((int)this.scrollbar.getEntryHeight() / 2);
    }
    
    private void renderModuleList(final List<SettingsElement> foundModules, final double listY, final double totalHeight, final int mouseX, final int mouseY) {
        SettingsElement mouseOverElement = null;
        for (int zLevel = 0; zLevel < 2; ++zLevel) {
            final int x = 5;
            double y = listY + totalHeight;
            final int maxX = LabyMod.getSettings().moduleEditorSplitX - (this.scrollbar.isHidden() ? 5 : 10);
            for (final SettingsElement element : foundModules) {
                final int nextSetting = element.getEntryHeight();
                y -= nextSetting + 1;
                final boolean inYRange = mouseY > listY && mouseY < this.m - 30;
                if (((!(element instanceof DropDownElement) || element instanceof ColorPickerCheckBoxBulkElement) && zLevel == 0) || ((element instanceof DropDownElement || element instanceof ColorPickerCheckBoxBulkElement) && zLevel == 1)) {
                    if (element instanceof ControlElement) {
                        final ControlElement controlElement = (ControlElement)element;
                        final boolean hoverable = controlElement.isModule() && mouseX < LabyMod.getSettings().moduleEditorSplitX - controlElement.getObjectWidth() - controlElement.getSubListButtonWidth() - (this.scrollbar.isHidden() ? 5 : 10) - 2;
                        final boolean selected = this.selectedControlElement != null && this.selectedControlElement == controlElement;
                        controlElement.setHoverable(hoverable && inYRange).setSelected(selected);
                        controlElement.draw(x, (int)y, maxX, (int)y + nextSetting, mouseX, mouseY);
                        if (this.selectedControlElement != null && this.selectedControlElement.equals(controlElement) && this.lightUpSelectedElement + 1000L > System.currentTimeMillis()) {
                            final int alpha = (int)((System.currentTimeMillis() - this.lightUpSelectedElement) * -1L + 1000L) / 4;
                            LabyMod.getInstance().getDrawUtils().drawRectBorder(x, y, maxX, y + nextSetting, ModColor.toRGB(200, 20, 20, alpha), 1.0);
                        }
                    }
                    if (element instanceof CategoryModuleEditorElement) {
                        final CategoryModuleEditorElement categoryElement = (CategoryModuleEditorElement)element;
                        categoryElement.draw(x, (int)y, maxX, (int)y + nextSetting, mouseX, mouseY);
                    }
                }
                if (element.isMouseOver() && inYRange && zLevel == 1) {
                    mouseOverElement = element;
                }
            }
        }
        if (this.masterElement.isMouseOver() && this.path.size() == 0) {
            mouseOverElement = (SettingsElement)this.masterElement;
        }
        this.mouseOverElement = mouseOverElement;
    }
    
    private void renderDescriptions(final List<SettingsElement> foundModules, final double listY, final double totalHeight, final int mouseX, final int mouseY) {
        for (final SettingsElement element : foundModules) {
            if (element.isMouseOver() && mouseY > listY && mouseY < listY + totalHeight) {
                element.drawDescription(mouseX, mouseY, this.l);
            }
        }
    }
    
    public void e() {
        super.e();
        if (this.path.size() != 0) {
            for (final SettingsElement module : this.getCurrentSelectedSubList().getSubSettings().getElements()) {
                module.updateScreen();
            }
        }
        this.textFieldSearch.updateCursorCounter();
    }
    
    private void renderPreview(final EnumDisplayType displayType, final int left, final int top, final int right, final int bottom, final int mouseX, final int mouseY) {
        PreviewRenderer.getInstance().render(displayType, left, top, right, bottom, LabyMod.getInstance().getPartialTicks(), mouseX, mouseY);
    }
    
    private List<SettingsElement> getFoundModules() {
        final boolean isMasterOpen = this.path.size() != 0 && this.path.get(0) == this.masterElement;
        boolean b = false;
        Label_0122: {
            Label_0117: {
                if (this.textFieldSearch == null || this.textFieldSearch.getText().replaceAll(" ", "").isEmpty()) {
                    if (this.path.size() >= 2) {
                        break Label_0117;
                    }
                }
                else if (this.path.size() >= 1) {
                    break Label_0117;
                }
                if (LabyModModuleEditorGui.selectedTab == EnumDisplayType.INGAME || this.path.size() == 0 || isMasterOpen) {
                    b = false;
                    break Label_0122;
                }
            }
            b = true;
        }
        final boolean isSubSettingOpen = b;
        if (LabyModModuleEditorGui.selectedTab != EnumDisplayType.INGAME && !isMasterOpen && !isSubSettingOpen) {
            final ArrayList<SettingsElement> list = new ArrayList<SettingsElement>();
            for (final Module module : Module.getModules()) {
                boolean isCurrentTab = false;
                for (final EnumDisplayType type : module.getDisplayTypes()) {
                    if (type == LabyModModuleEditorGui.selectedTab) {
                        isCurrentTab = true;
                    }
                }
                if (isCurrentTab && module.getBooleanElement().isVisible()) {
                    list.add((SettingsElement)module.getBooleanElement());
                }
            }
            return list;
        }
        if (this.path.size() != 0) {
            final ArrayList<SettingsElement> list = new ArrayList<SettingsElement>();
            for (final SettingsElement element : this.getCurrentSelectedSubList().getSubSettings().getElements()) {
                boolean isCurrentTab = isMasterOpen || isSubSettingOpen;
                if (element instanceof ControlElement) {
                    final Module module2 = ((ControlElement)element).getModule();
                    if (module2 != null) {
                        for (final EnumDisplayType type2 : module2.getDisplayTypes()) {
                            if (type2 == LabyModModuleEditorGui.selectedTab) {
                                isCurrentTab = true;
                            }
                        }
                    }
                }
                if (isCurrentTab && element instanceof ControlElement && element.isVisible()) {
                    list.add(element);
                }
            }
            return list;
        }
        if (this.textFieldSearch == null) {
            return this.toElements(ModuleCategoryRegistry.getCategories());
        }
        final String searchValue = this.textFieldSearch.getText().toLowerCase();
        if (searchValue.replace(" ", "").isEmpty()) {
            return this.toElements(ModuleCategoryRegistry.getCategories());
        }
        final ArrayList<SettingsElement> list2 = new ArrayList<SettingsElement>();
        for (final Module module3 : Module.getModules()) {
            if (!module3.getControlName().toLowerCase().contains(searchValue) && !module3.getDescription().toLowerCase().contains(searchValue) && !module3.getCategory().getName().toLowerCase().contains(searchValue) && !searchValue.equals("*")) {
                continue;
            }
            boolean isCurrentTab2 = isMasterOpen || isSubSettingOpen;
            for (final EnumDisplayType type2 : module3.getDisplayTypes()) {
                if (type2 == LabyModModuleEditorGui.selectedTab) {
                    isCurrentTab2 = true;
                }
            }
            if (!isCurrentTab2 || !module3.getBooleanElement().isVisible()) {
                continue;
            }
            list2.add((SettingsElement)module3.getBooleanElement());
        }
        return list2;
    }
    
    private List<SettingsElement> toElements(final List<ModuleCategory> moduleCategories) {
        final ArrayList<SettingsElement> list = new ArrayList<SettingsElement>();
        for (final ModuleCategory module : moduleCategories) {
            list.add((SettingsElement)module.getCategoryElement());
        }
        return list;
    }
    
    public static EnumDisplayType getSelectedTab() {
        return LabyModModuleEditorGui.selectedTab;
    }
    
    public blk getLastScreen() {
        return this.lastScreen;
    }
    
    static {
        LabyModModuleEditorGui.selectedTab = EnumDisplayType.INGAME;
        moduleGui = new ModuleGui(true, true, LabyModModuleEditorGui.selectedTab);
        LabyModModuleEditorGui.selectedProfile = null;
        GLOBAL_PROFILE = LanguageManager.translate("global_profile");
        SINGLEPLAYER_PROFILE = LanguageManager.translate("singleplayer_profile");
    }
    
    public enum EnumModuleEditorScale
    {
        SMALL(LanguageManager.translate("scaletype_small"), 1), 
        NORMAL(LanguageManager.translate("scaletype_normal"), 2), 
        LARGE(LanguageManager.translate("scaletype_large"), 3), 
        AUTO(LanguageManager.translate("scaletype_auto"), 4);
        
        private String displayName;
        private int scaling;
        
        public static EnumModuleEditorScale getByScaling(final int guiScale) {
            for (final EnumModuleEditorScale emes : values()) {
                if (emes.getScaling() >= guiScale) {
                    return emes;
                }
            }
            return EnumModuleEditorScale.NORMAL;
        }
        
        public static EnumModuleEditorScale getByZoom(final double zoom) {
            final double customScaleFactor = 1.0 + zoom * 0.03;
            for (final EnumModuleEditorScale emes : values()) {
                if (emes.getScaling() == Math.round(customScaleFactor) - 1L) {
                    return emes;
                }
            }
            return EnumModuleEditorScale.NORMAL;
        }
        
        public String getDisplayName() {
            return this.displayName;
        }
        
        public int getScaling() {
            return this.scaling;
        }
        
        private EnumModuleEditorScale(final String displayName, final int scaling) {
            this.displayName = displayName;
            this.scaling = scaling;
        }
    }
}
