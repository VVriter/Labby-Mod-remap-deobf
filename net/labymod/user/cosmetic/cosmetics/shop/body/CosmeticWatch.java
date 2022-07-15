//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import org.lwjgl.opengl.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticWatch extends CosmeticRenderer<CosmeticWatchData>
{
    public static final int ID = 33;
    private brs watchCase;
    private brs numberField;
    private brs band;
    private brs pointerHourMinute;
    private brs pointerSeconds;
    private Calendar calendar;
    
    public CosmeticWatch() {
        this.calendar = Calendar.getInstance();
    }
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int widthCase = 36;
        final int heightCase = 19;
        final int widthDesign = 20;
        final int heightDesign = 21;
        final boolean isSlim = modelCosmetics.i.l.get(0).d == 2.0f;
        final float caseDepth = isSlim ? 0.0f : 4.6f;
        (this.band = new ModelRendererHook((bqf)modelCosmetics).b(widthDesign, heightDesign).a(0, isSlim ? 6 : 0)).a(isSlim ? -1.5f : -1.4f, 8.7f, -2.5f, isSlim ? 4 : 5, 1, 5, modelSize);
        (this.watchCase = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(0, 0)).a(-3.0f, -4.0f, caseDepth, 6, 8, 1, modelSize);
        this.watchCase.g = (float)Math.toRadians(90.0);
        brs model = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(16, 0);
        model.a(-4.0f, -3.0f, caseDepth, 1, 6, 1, modelSize);
        model.a(3.0f, -3.0f, caseDepth, 1, 6, 1, modelSize);
        this.watchCase.a(model);
        (this.numberField = new ModelRendererHook((bqf)modelCosmetics).b(widthDesign, heightDesign).a(0, 12)).a(-3.0f, -4.0f, caseDepth - 0.1f, 6, 8, 1, modelSize);
        this.numberField.g = (float)Math.toRadians(90.0);
        final float depht = caseDepth + 0.3f;
        final float addedScale = 0.01f;
        model = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(0, 9);
        model.a(-2.0f, -4.0f, depht, 4, 1, 1, modelSize + addedScale);
        model.a(-2.0f, 3.0f, depht, 4, 1, 1, modelSize + addedScale);
        this.watchCase.a(model);
        model = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(0, 9);
        model.a(-2.0f, -4.0f, depht, 4, 1, 1, modelSize + addedScale);
        model.a(-2.0f, 3.0f, depht, 4, 1, 1, modelSize + addedScale);
        model.h = (float)Math.toRadians(90.0);
        this.watchCase.a(model);
        model = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(14, 9);
        model.a(2.0f, -3.0f, depht, 1, 1, 1, modelSize + addedScale);
        model.a(-3.0f, -3.0f, depht, 1, 1, 1, modelSize + addedScale);
        model.a(2.0f, 2.0f, depht, 1, 1, 1, modelSize + addedScale);
        model.a(-3.0f, 2.0f, depht, 1, 1, 1, modelSize + addedScale);
        model.a(-0.5f, -0.5f, caseDepth + 0.6f, 1, 1, 1, -0.2f);
        this.watchCase.a(model);
        this.pointerHourMinute = new ModelRendererHook((bqf)modelCosmetics);
        final brs pointerHour = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(24, 0);
        pointerHour.a(6.0f * caseDepth, 0.0f, -1.0f, 1, 12, 2, modelSize);
        this.pointerHourMinute.a(pointerHour);
        final brs pointerMinute = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(30, 0);
        pointerMinute.a(6.0f * caseDepth + 0.2f, 0.0f, -1.0f, 1, 17, 2, modelSize);
        this.pointerHourMinute.a(pointerMinute);
        (this.pointerSeconds = new ModelRendererHook((bqf)modelCosmetics).b(widthCase, heightCase).a(20, 0)).a(6.0f * caseDepth + 0.3f, 0.0f, -0.5f, 1, 16, 1, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.watchCase.j = invisible;
        this.band.j = invisible;
        this.pointerHourMinute.j = invisible;
        this.pointerSeconds.j = invisible;
        this.numberField.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticWatchData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final nf textureDesign = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getWatchImageHandler().getResourceLocation((bua)entityIn);
        if (textureDesign == null) {
            return;
        }
        final Color caseColor = cosmeticData.getColor();
        final boolean useRightHand = cosmeticData.isUseRightHand();
        final brs model = useRightHand ? modelCosmetics.h : modelCosmetics.i;
        bus.G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bib.z().N().a(textureDesign);
        bus.c(model.c * scale, model.d * scale, model.e * scale);
        bus.b(model.h * 57.295776f, 0.0f, 0.0f, 1.0f);
        bus.b(model.g * 57.295776f, 0.0f, 1.0f, 0.0f);
        bus.b(model.f * 57.295776f, 1.0f, 0.0f, 0.0f);
        if (useRightHand) {
            bus.b(180.0f, 0.0f, 1.0f, 0.0f);
        }
        bus.c(scale * -1.0f, scale * -2.0f, scale * -2.0f);
        bus.c(0.0f, scale * -cosmeticData.getPositionY() * 0.5f, 0.0f);
        final double bandScale = 0.824999988079071;
        final double watchScale = 0.20000000298023224;
        final double pointerScale = 0.03200000151991844;
        bus.G();
        bus.c(scale * 1.0f, scale * 2.0f, scale * 2.0f);
        bus.G();
        bus.a(bandScale, bandScale, bandScale);
        bus.c(scale * 0.1f, scale * 1.7f, 0.0f);
        this.band.a(scale);
        bus.H();
        bus.G();
        bus.c(scale * 2.1f, scale * 9.0f, 0.0f);
        bus.a(watchScale, watchScale, watchScale);
        bus.b(useRightHand ? -90.0f : 90.0f, -1.0f, 0.0f, 0.0f);
        this.bindTextureAndColor(caseColor, ModTextures.COSMETIC_WATCH_CASE, this.watchCase).a(scale);
        this.bindTextureAndColor(Color.white, textureDesign, this.numberField).a(scale);
        bus.H();
        bus.H();
        bus.c(scale * 3.0f + 0.02f, scale * 11.0f, scale * 2.0f);
        bus.a(pointerScale, pointerScale, pointerScale);
        this.calendar.setTimeInMillis(System.currentTimeMillis());
        final int minute = this.calendar.get(12);
        final int hour = this.calendar.get(10);
        final int seconds = this.calendar.get(13);
        final float hourInterpolation = 0.016666668f * minute;
        final float mirror = cosmeticData.isUseRightHand() ? 0.0f : 180.0f;
        final List<brs> cm = (List<brs>)this.pointerHourMinute.m;
        cm.get(0).f = (float)Math.toRadians(-30.0f * (hour + hourInterpolation) - mirror);
        cm.get(1).f = (float)Math.toRadians(-6.0f * minute - mirror);
        this.pointerSeconds.f = (float)Math.toRadians(-6.0f * seconds - mirror);
        bus.b(90.0f, -1.0f, 0.0f, 0.0f);
        if (cosmeticData.isDisplaySecondPointer()) {
            this.bindTextureAndColor(Color.WHITE, ModTextures.COSMETIC_WATCH_CASE, this.pointerSeconds).a(scale);
        }
        this.bindTextureAndColor(caseColor, ModTextures.COSMETIC_WATCH_CASE, this.pointerHourMinute).a(scale);
        bus.H();
    }
    
    public int getCosmeticId() {
        return 33;
    }
    
    public String getCosmeticName() {
        return "Watch";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public boolean hasLeftHandSupport() {
        return false;
    }
    
    public boolean isVisibleInFirstPerson(final CosmeticWatchData data, final boolean rightHand) {
        return rightHand ? data.isUseRightHand() : (!data.isUseRightHand());
    }
    
    public static class CosmeticWatchData extends CosmeticData
    {
        private Color color;
        private boolean useRightHand;
        private int positionY;
        private boolean displaySecondPointer;
        private UserTextureContainer userTextureContainer;
        
        public CosmeticWatchData() {
            this.color = Color.DARK_GRAY;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getWatchContainer();
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.color = Color.decode("#" + data[0]);
            this.useRightHand = (Integer.parseInt(data[1]) == 1);
            this.positionY = Integer.parseInt(data[2]);
            this.displaySecondPointer = (Integer.parseInt(data[3]) == 1);
            this.userTextureContainer.setFileName(UUID.fromString(data[4]));
            this.positionY = Math.max(0, this.positionY);
            this.positionY = Math.min(10, this.positionY);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.ARM;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isUseRightHand() {
            return this.useRightHand;
        }
        
        public int getPositionY() {
            return this.positionY;
        }
        
        public boolean isDisplaySecondPointer() {
            return this.displaySecondPointer;
        }
    }
}
