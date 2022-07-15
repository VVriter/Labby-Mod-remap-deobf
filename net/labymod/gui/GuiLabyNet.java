//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.gui;

import net.labymod.user.cosmetic.geometry.*;
import java.util.*;
import com.google.gson.*;
import net.labymod.main.lang.*;
import java.io.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.utils.*;
import net.labymod.gui.elements.*;

public class GuiLabyNet extends blk
{
    private final List<LabyNetSetting> settings;
    private final Consumer<JsonObject> callback;
    private final blk previousScreen;
    private bja doneButton;
    private bja openButton;
    private BlockBenchLoader blockBenchLoader;
    
    public GuiLabyNet(final JsonObject options, final Consumer<JsonObject> callback) {
        this.settings = new ArrayList<LabyNetSetting>();
        this.previousScreen = bib.z().m;
        this.callback = callback;
        try {
            for (final Map.Entry<String, JsonElement> entry : options.entrySet()) {
                final JsonArray values = entry.getValue().getAsJsonObject().get("values").getAsJsonArray();
                final String selected = entry.getValue().getAsJsonObject().get("selected").getAsString();
                this.settings.add(new LabyNetSetting(entry.getKey(), values, selected));
            }
            final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/minecraft/labymod/models/alex.geo.json");
            String json = "";
            final Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                json += scanner.nextLine();
            }
            scanner.close();
            this.blockBenchLoader = new GeometryLoader(json).toBlockBenchLoader(null);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public void b() {
        super.b();
        this.n.add(this.doneButton = new bja(0, 10, 0, 100, 20, LanguageManager.translate("button_save")));
        this.n.add(this.openButton = new bja(1, this.l - 105, 5, 100, 20, LanguageManager.translate("labynet_button_open_profile")));
    }
    
    protected void a(final bja button) throws IOException {
        super.a(button);
        if (button.k == 0) {
            final JsonObject response = new JsonObject();
            for (final LabyNetSetting setting : this.settings) {
                response.addProperty(setting.getId(), ((SettingOption)setting.menu.getSelected()).value);
            }
            this.callback.accept(response);
            bib.z().a(this.previousScreen);
        }
        if (button.k == 1) {
            LabyMod.getInstance().openWebpage("https://laby.net/@" + LabyMod.getInstance().getPlayerName(), false);
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final DrawUtils drawUtils = LabyMod.getInstance().getDrawUtils();
        drawUtils.drawBackground(32);
        final int menuLeft = this.l / 2 - 80;
        final int menuTop = 50;
        final int menuBottom = menuTop + 150;
        drawUtils.drawOverlayBackground(0, 30, this.l, menuBottom, 32);
        bir.a(0, 0, this.l, 30, ModColor.toRGB(30, 30, 30, 255));
        bus.m();
        bib.z().N().a(ModTextures.TITLE_LABYMOD_WHITE_WOLF);
        drawUtils.drawTexture(10.0, 5.0, 0.0, 0.0, 256.0, 256.0, 20.0, 20.0, 1.0f);
        drawUtils.drawString(ModColor.cl('e') + LanguageManager.translate("labynet_title", ModColor.cl('f') + ModColor.cl('f') + "LABY.net"), 40.0, 12.0, 1.0);
        drawUtils.drawString(ModColor.cl('f') + LabyMod.getInstance().getPlayerName(), menuLeft, menuTop, 3.0);
        drawUtils.drawString(ModColor.cl('7') + LabyMod.getInstance().getPlayerUUID(), menuLeft, menuTop + 30, 1.0);
        int settingX = menuLeft;
        int settingY = menuTop + 70;
        final int elementWidth = Math.min(140, this.l / 3);
        for (final LabyNetSetting setting : this.settings) {
            setting.menu.setX(settingX);
            setting.menu.setY(settingY);
            setting.menu.setWidth(elementWidth);
            setting.menu.setHeight(20);
            settingX += setting.menu.getWidth() + 10;
            if (settingX > this.l - setting.menu.getWidth()) {
                settingY += 50;
                settingX = menuLeft;
            }
        }
        LabyModCore.getMinecraft().setButtonXPosition(this.doneButton, settingX + elementWidth * 2 - this.doneButton.b() + 10);
        LabyModCore.getMinecraft().setButtonYPosition(this.doneButton, menuBottom + 5);
        if (this.blockBenchLoader != null) {
            try {
                final float rotation = 40.0f;
                final float scale = 6.0f;
                bus.G();
                bus.c((float)(menuLeft - 80), (float)(menuTop + 50), -100.0f);
                bus.b(rotation + 180.0f, 0.0f, 1.0f, 0.0f);
                bus.b(scale, scale, scale);
                this.blockBenchLoader.getModel("Head").rotateAngleY = (float)Math.toRadians(-rotation);
                this.blockBenchLoader.getModel("LeftLeg").rotateAngleX = (float)Math.toRadians(-20.0);
                this.blockBenchLoader.getModel("RightLeg").rotateAngleX = (float)Math.toRadians(20.0);
                this.blockBenchLoader.getModel("LeftArm").rotateAngleX = (float)Math.toRadians(20.0);
                this.blockBenchLoader.getModel("RightArm").rotateAngleX = (float)Math.toRadians(-20.0);
                final nf skin = drawUtils.getPlayerSkinTextureCache().getSkinTexture(LabyMod.getInstance().getPlayerUUID());
                bib.z().N().a(skin);
                bus.g();
                bus.d(1.0f, 1.0f, 1.0f);
                bus.k();
                bus.r();
                this.blockBenchLoader.getModel().render(1.0f);
                bus.q();
                bus.H();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        drawUtils.drawOverlayBackground(menuBottom, this.m);
        drawUtils.drawGradientShadowTop(30.0, 0.0, this.l);
        drawUtils.drawGradientShadowBottom(menuBottom, 0.0, this.l);
        if (this.m - menuBottom > 60) {
            drawUtils.drawCenteredString(ModColor.cl('9') + ModColor.cl('n') + "laby.net/@" + LabyMod.getInstance().getPlayerName(), this.l / 2.0f, this.m - (this.m - menuBottom) / 2.0f);
        }
        super.a(mouseX, mouseY, partialTicks);
        for (int i = this.settings.size() - 1; i >= 0; --i) {
            final LabyNetSetting setting = this.settings.get(i);
            setting.menu.draw(mouseX, mouseY);
        }
    }
    
    protected void a(final int mouseX, final int mouseY, final int button) throws IOException {
        for (final LabyNetSetting setting : this.settings) {
            if (setting.menu.onClick(mouseX, mouseY, button)) {
                for (final LabyNetSetting innerSetting : this.settings) {
                    if (innerSetting != setting) {
                        innerSetting.menu.setOpen(false);
                    }
                }
                return;
            }
        }
        if ((mouseY < 30 && mouseX < this.l / 2) || (mouseY > this.m - (this.m - 200) / 2.0f && mouseY < this.m - (this.m - 200) / 2.0f + 10.0f)) {
            LabyMod.getInstance().openWebpage("https://laby.net/@" + LabyMod.getInstance().getPlayerName(), true);
        }
        super.a(mouseX, mouseY, button);
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        if (keyCode == 1) {
            this.a(this.doneButton);
        }
        super.a(typedChar, keyCode);
    }
    
    public class LabyNetSetting
    {
        private final DropDownMenu<SettingOption> menu;
        private final String id;
        
        public LabyNetSetting(final String id, final JsonArray values, final String selected) {
            this.id = id;
            final String displayString = LanguageManager.translate("labynet_setting_" + id.replace(".", "_"));
            this.menu = (DropDownMenu<SettingOption>)new DropDownMenu(displayString, 0, 0, 0, 0);
            for (final JsonElement value : values) {
                final JsonObject entry = value.getAsJsonObject();
                final char character = entry.get("character").getAsString().charAt(0);
                final String valueString = entry.get("value").getAsString();
                final SettingOption option = new SettingOption(character, valueString);
                this.menu.addOption((Object)option);
                if (valueString.equals(selected)) {
                    this.menu.setSelected((Object)option);
                }
            }
            this.menu.setEntryDrawer((object, x, y, trimmedEntry) -> {
                final SettingOption value = (SettingOption)object;
                LabyMod.getInstance().getDrawUtils().drawString(value.toString(), x, y);
            });
        }
        
        public DropDownMenu<SettingOption> getMenu() {
            return this.menu;
        }
        
        public String getId() {
            return this.id;
        }
    }
    
    public class SettingOption
    {
        private final char character;
        private final String value;
        private final String displayString;
        
        public SettingOption(final char character, final String value) {
            this.character = character;
            this.value = value;
            this.displayString = ModColor.cl(this.character) + LanguageManager.translate("labynet_setting_option_" + value.toLowerCase());
        }
        
        public char getCharacter() {
            return this.character;
        }
        
        public String getValue() {
            return this.value;
        }
        
        @Override
        public String toString() {
            return this.displayString;
        }
    }
}
