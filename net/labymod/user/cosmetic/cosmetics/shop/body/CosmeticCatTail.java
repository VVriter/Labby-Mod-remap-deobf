//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.body;

import net.labymod.user.cosmetic.*;
import net.labymod.core.*;
import net.labymod.user.emote.*;
import net.labymod.main.*;
import net.labymod.user.emote.keys.*;
import java.awt.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticCatTail extends CosmeticRenderer<CosmeticCatTailData>
{
    public static final int ID = 3;
    private brs tail;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        (this.tail = new ModelRendererHook((bqf)modelCosmetics).b(4, 3).a(0, 0)).a(-0.5f, 0.0f, 0.0f, 1, 2, 1, modelSize);
        this.tail.a(0.0f, 11.0f, 2.0f);
        this.tail.f = 0.9424778f;
        brs last = this.tail;
        for (int i = 0; i <= 7; ++i) {
            final brs part = new ModelRendererHook((bqf)modelCosmetics).b(4, 3);
            part.a(-0.5f, 0.0f, 0.0f, 1, 2, 1, modelSize - 0.01f * i);
            part.a(0.0f, 1.6f, 0.0f);
            last.a(part);
            last = part;
        }
    }
    
    public void setInvisible(final boolean invisible) {
        this.tail.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticCatTailData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        final brs tail = this.bindTextureAndColor(cosmeticData.getColor(), ModTextures.COSMETIC_CAT_TAIL, this.tail);
        bus.G();
        if (entityIn.aU()) {
            bus.c(0.0f, 0.1f, -0.15f);
            bus.b(45.0f, 1.0f, 0.0f, 0.0f);
        }
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionX = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        final double motionZ = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
        final float motionYaw = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
        final double yawSin = LabyModCore.getMath().sin(motionYaw * 3.1415927f / 180.0f);
        final double yawCos = -LabyModCore.getMath().cos(motionYaw * 3.1415927f / 180.0f);
        float rotation = (float)motionY * 10.0f;
        rotation = LabyModCore.getMath().clamp_float(rotation, -6.0f, 32.0f);
        float motionAdd = (float)(motionX * yawSin + motionZ * yawCos) * 100.0f;
        final float motionSub = (float)(motionX * yawCos - motionZ * yawSin) * 100.0f;
        if (motionAdd < 0.0f) {
            motionAdd = 0.0f;
        }
        if (motionAdd >= 130.0f) {
            motionAdd = 130.0f + (motionAdd - 180.0f) * 0.2f;
        }
        try {
            float roll = 0.0f;
            final EmoteRenderer emoteRenderer = LabyMod.getInstance().getEmoteRegistry().getPlayingEmotes().get(entitylivingbaseIn.bm());
            if (emoteRenderer != null && !emoteRenderer.isAborted() && emoteRenderer.isVisible() && !emoteRenderer.isStream()) {
                final long duration = System.currentTimeMillis() - emoteRenderer.getStartTime();
                float percent = 0.001f * Math.min(duration, 1000L);
                final float fadeOut = (emoteRenderer.getTimeout() == 0L) ? 0.0f : ((float)((duration > emoteRenderer.getResetKeyframeEnd()) ? emoteRenderer.getTimeout() : (emoteRenderer.getResetKeyframeEnd() - duration)));
                final float timeout = (emoteRenderer.getTimeout() == 0L) ? 1.0f : (1.0f / emoteRenderer.getTimeout() * fadeOut);
                percent *= timeout;
                roll = 0.3f * percent;
                boolean isBlockMovement = false;
                for (final PoseAtTime poseAtTime : emoteRenderer.getEmotePosesAtTime()) {
                    if (poseAtTime != null && poseAtTime.getPose().isBlockMovement()) {
                        isBlockMovement = true;
                        break;
                    }
                }
                if (!isBlockMovement) {
                    roll = 0.0f;
                }
            }
            int index = 0;
            for (brs next = this.tail; next != null && next.m != null && next.m.size() != 0; next = next.m.get(0), ++index) {
                final float walk = LabyModCore.getMath().cos(movementFactor + index * 0.5f) * 0.03f * index * ((index % 4 != 0) ? -1 : 1);
                if (index >= 0 && index < 2) {
                    next.f = -0.4f + walkingSpeed / (index + 1) + walk + roll / 2.0f;
                }
                else if (index >= 5) {
                    next.f = -walkingSpeed / 2.0f + 0.4f + walk + roll / 2.0f;
                }
                else {
                    next.f = roll;
                }
                if (index == 0) {
                    final brs brs = next;
                    brs.f += (float)(0.85 + roll * 3.0f);
                }
                next.h = motionSub / 400.0f;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            bus.H();
        }
        tail.a(scale);
        bus.H();
    }
    
    public int getCosmeticId() {
        return 3;
    }
    
    public String getCosmeticName() {
        return "Cat Tail";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticCatTailData extends CosmeticData
    {
        private Color color;
        
        public CosmeticCatTailData() {
            this.color = Color.WHITE;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) {
            this.color = Color.decode("#" + data[0]);
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.BODY;
        }
        
        public Color getColor() {
            return this.color;
        }
    }
}
