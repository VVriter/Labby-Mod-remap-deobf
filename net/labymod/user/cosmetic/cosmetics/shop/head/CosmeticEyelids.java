//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.main.*;
import java.awt.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticEyelids extends CosmeticRenderer<CosmeticEyelidsData>
{
    public static final int ID = 36;
    private static final int BLINK_SPEED = 100;
    private static final int MAX_IDLE_DURATION = 240000;
    private static final double IDLE_CLOSE_SPEED = 5000.0;
    private brs model;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        this.model = new ModelRendererHook((bqf)modelCosmetics, 0, 0).b(4, 2);
        this.model.a(0, 0).a(0.0f, 0.0f, 0.0f, 1, 1, 1, modelSize);
    }
    
    public void setInvisible(final boolean invisible) {
        this.model.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticEyelidsData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        if (entityIn.aU()) {
            double sneakRotate = entityIn.w / 70000.0;
            sneakRotate = Math.max(0.0, sneakRotate);
            bus.b(0.0, 0.062, 0.0);
            bus.b(0.0, sneakRotate, -sneakRotate);
        }
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.b(0.11f, 0.0f, 1.0f, 0.0f);
        bus.c(scale * -4.0f, scale * -8.0f, scale * -4.0f - 7.0E-4f);
        final int x = cosmeticData.getX();
        final int y = cosmeticData.getY();
        final int width = cosmeticData.getWidth();
        final int height = cosmeticData.getHeight();
        final long timeLeftToBlink = cosmeticData.getBlinkTimeLeft();
        final double timeFrame = (double)(cosmeticData.durationBlinkPause - timeLeftToBlink - 100L);
        final double animation = Math.min(Math.abs(timeFrame), 100.0);
        double percentage = cosmeticData.isCanBlink() ? (1.0 - animation / 100.0) : 0.0;
        final long idleDuration = cosmeticData.getIdleDuration(entityIn);
        final boolean isIdle = idleDuration > 240000L && cosmeticData.isCanSleep();
        if (isIdle) {
            percentage = 2.0E-4 * Math.min(5000.0, (double)(idleDuration - 240000L));
        }
        cosmeticData.lastRenderedPercetage = percentage;
        final double smooth = -Math.cos(1.5707963267948966 + 1.5707963267948966 * percentage);
        final double offset = 0.001;
        if (percentage != 0.0) {
            if (entityIn.w < -40.0f) {
                bus.g();
            }
            else {
                bus.f();
            }
            this.bindTextureAndColor(cosmeticData.getColor(), ModTextures.COSMETIC_EYELIDS, (brs)null);
            for (int i = 0; i < 2; ++i) {
                bus.G();
                bus.b(((i == 0) ? x : (8 - x - width)) * scale - offset, y * scale - offset, -0.0010000000474974513);
                bus.a(width + offset * 2.0 / scale, height * smooth + offset * 2.0 / scale, 0.019999999552965164);
                this.model.a(scale);
                bus.H();
            }
            bus.f();
        }
        bus.H();
        bus.G();
        if (isIdle) {
            bus.G();
            bus.c(scale * 5.0f, scale * -4.0f, scale * -(entityIn.w / 15.0f + 4.0f));
            bib.z().N().a(ModTextures.COSMETIC_EYELIDS_SLEEP);
            final int totalAmount = 4;
            final int speed = 1000;
            final int renderedAmount = totalAmount - Math.min(totalAmount, (int)(idleDuration - 240000L) / speed);
            final int startTimeOffset = (totalAmount - 1) * speed;
            for (int j = renderedAmount; j < totalAmount + 1; ++j) {
                final int sleepTimeFrame = ((int)idleDuration + j * speed + startTimeOffset) % (totalAmount * speed);
                final double percentZMoved = 1.0 / (totalAmount * (double)speed) * sleepTimeFrame;
                bus.G();
                bus.b(percentZMoved / 2.0, -percentZMoved / 2.0, 0.0);
                this.rotateToPlayersCamera(entityIn, true, true);
                bus.b((float)(Math.cos(percentZMoved * 5.0) * 20.0 - 10.0) * ((j % 2 == 0) ? -1 : 1), 0.0f, 0.0f, 1.0f);
                final double scaleZ = scale / 2.0 + percentZMoved / 8.0;
                final float transparency = 1.0f - (float)Math.abs((percentZMoved - 0.5) * 2.0);
                LabyMod.getInstance().getDrawUtils().drawTexture(-scaleZ / 2.0, -scaleZ / 2.0, 0.0, 0.0, 256.0, 256.0, scaleZ, scaleZ, transparency);
                bus.H();
            }
            bus.H();
        }
        bus.H();
    }
    
    public int getCosmeticId() {
        return 36;
    }
    
    public String getCosmeticName() {
        return "Eyelids";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticEyelidsData extends CosmeticData
    {
        public double lastRenderedPercetage;
        public long timestampNextBlink;
        public long durationBlinkPause;
        private double lastPositionHash;
        private long lastPositionChanged;
        private int x;
        private int y;
        private int width;
        private int height;
        private Color color;
        private boolean canBlink;
        private boolean canSleep;
        
        public CosmeticEyelidsData() {
            this.lastRenderedPercetage = 0.0;
            this.timestampNextBlink = System.currentTimeMillis();
            this.durationBlinkPause = 0L;
            this.lastPositionHash = 0.0;
            this.lastPositionChanged = 0L;
            this.x = 1;
            this.y = 4;
            this.width = 2;
            this.height = 1;
            this.color = Color.WHITE;
            this.canBlink = true;
            this.canSleep = true;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        public long getBlinkTimeLeft() {
            final long currentTime = System.currentTimeMillis();
            final long timeLeftToBlink = this.timestampNextBlink - currentTime;
            if (timeLeftToBlink < 0L) {
                this.durationBlinkPause = 800 + LabyMod.getRandom().nextInt(8) * 1000;
                this.timestampNextBlink = currentTime + this.durationBlinkPause;
            }
            return timeLeftToBlink;
        }
        
        public long getIdleDuration(final vg entity) {
            double hash = entity.p + entity.q + entity.r + entity.v + entity.w + (entity.aU() ? 1 : 0);
            if (entity instanceof bua) {
                hash += ((bua)entity).aD;
            }
            if (this.lastPositionHash != hash) {
                this.lastPositionChanged = System.currentTimeMillis();
                this.lastPositionHash = hash;
            }
            return System.currentTimeMillis() - this.lastPositionChanged;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            Exception exception = null;
            try {
                this.x = Integer.parseInt(data[0]);
                this.y = Integer.parseInt(data[1]);
                this.width = Integer.parseInt(data[2]);
                this.height = Integer.parseInt(data[3]);
                this.color = Color.decode("#" + data[4]);
                this.canBlink = (Integer.parseInt(data[5]) == 1);
                this.canSleep = (Integer.parseInt(data[6]) == 1);
            }
            catch (Exception e) {
                exception = e;
            }
            this.x = Math.min(this.x, 7);
            this.y = Math.min(this.y, 7);
            this.width = Math.min(this.width, 4);
            this.height = Math.min(this.height, 4);
            this.x = Math.max(this.x, 0);
            this.y = Math.max(this.y, 0);
            this.width = Math.max(this.width, 1);
            this.height = Math.max(this.height, 1);
            if (exception != null) {
                throw exception;
            }
        }
        
        @Override
        public void init(final User user) {
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public double getLastRenderedPercetage() {
            return this.lastRenderedPercetage;
        }
        
        public long getTimestampNextBlink() {
            return this.timestampNextBlink;
        }
        
        public long getDurationBlinkPause() {
            return this.durationBlinkPause;
        }
        
        public double getLastPositionHash() {
            return this.lastPositionHash;
        }
        
        public long getLastPositionChanged() {
            return this.lastPositionChanged;
        }
        
        public int getX() {
            return this.x;
        }
        
        public int getY() {
            return this.y;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public Color getColor() {
            return this.color;
        }
        
        public boolean isCanBlink() {
            return this.canBlink;
        }
        
        public boolean isCanSleep() {
            return this.canSleep;
        }
    }
}
