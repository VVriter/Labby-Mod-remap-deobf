//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.head;

import net.labymod.user.cosmetic.*;
import net.labymod.utils.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.core.*;
import java.awt.*;
import net.labymod.user.cosmetic.custom.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.util.*;

public class CosmeticScarf extends CosmeticRenderer<CosmeticScarfData>
{
    public static final int ID = 41;
    private brs scarfRing;
    private brs scarfHanging;
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 26;
        final int height = 9;
        (this.scarfRing = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0)).a(-4.5f, -0.25f, -4.5f, 1, 1, 8);
        final brs subRing = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(10, 0);
        subRing.a(-4.0f, 0.5f, -4.0f, 1, 1, 7);
        this.scarfRing.a(subRing);
        this.scarfHanging = new ModelRendererHook((bqf)modelCosmetics);
        brs last = this.scarfHanging;
        for (int i = 0; i <= 4; ++i) {
            final brs part = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 0);
            part.a(-1.0f, 0.0f, -0.5f, 2, 2, 1);
            part.a(0.05f, 2.0f, 0.05f);
            last.a(part);
            last = part;
        }
        final ModelRendererHook part2 = new ModelRendererHook((bqf)modelCosmetics);
        part2.b(width, height).a(10, 0);
        part2.a(0.0f, 2.0f, 0.0f);
        part2.a(0.0f, 0.0f, -0.5f, 1, 4, 1);
        part2.a(1.8f, 0.0f, -0.5f, 1, 4, 1);
        part2.a(-2.3f, 0.0f, -0.5f, 1, 4, 1);
        part2.setHook(new Consumer<ModelRendererHook>() {
            @Override
            public void accept(final ModelRendererHook accepted) {
                final double k = 0.3;
                bus.b(0.0, 0.085, 0.0);
                bus.a(k, k, k);
                accepted.renderSuper();
            }
        });
        last.a((brs)part2);
    }
    
    public void setInvisible(final boolean invisible) {
        this.scarfRing.j = invisible;
        this.scarfHanging.j = invisible;
    }
    
    public void render(final ModelCosmetics modelCosmetics, final vg entityIn, final CosmeticScarfData cosmeticData, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX, final float partialTicks) {
        bus.G();
        final nf location = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getScarfImageHandler().getResourceLocation((bua)entityIn);
        bib.z().N().a((location == null || location == ModTextures.VOID) ? ModTextures.COSMETIC_SCARF : location);
        if (entityIn.aU()) {
            bus.b(0.0, 0.06, 0.0);
        }
        final Color color = cosmeticData.getColor();
        if (color != null) {
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
        }
        bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        bus.b(secondRotationX, 1.0f, 0.0f, 0.0f);
        bus.a(1.001, 1.001, 1.001);
        final bua entitylivingbaseIn = (bua)entityIn;
        final double motionX = entitylivingbaseIn.bE + (entitylivingbaseIn.bH - entitylivingbaseIn.bE) * partialTicks - (entitylivingbaseIn.m + (entitylivingbaseIn.p - entitylivingbaseIn.m) * partialTicks);
        final double motionY = entitylivingbaseIn.bF + (entitylivingbaseIn.bI - entitylivingbaseIn.bF) * partialTicks - (entitylivingbaseIn.n + (entitylivingbaseIn.q - entitylivingbaseIn.n) * partialTicks);
        final double motionZ = entitylivingbaseIn.bG + (entitylivingbaseIn.bJ - entitylivingbaseIn.bG) * partialTicks - (entitylivingbaseIn.o + (entitylivingbaseIn.r - entitylivingbaseIn.o) * partialTicks);
        final float motionYaw = entitylivingbaseIn.aO + (entitylivingbaseIn.aN - entitylivingbaseIn.aO) * partialTicks;
        final double motionSin = LabyModCore.getMath().sin(motionYaw * 3.1415927f / 180.0f);
        final double motionCos = -LabyModCore.getMath().cos(motionYaw * 3.1415927f / 180.0f);
        float rotation = (float)motionY * 10.0f;
        rotation = LabyModCore.getMath().clamp_float(rotation, -6.0f, 32.0f);
        float motionAdd = (float)(motionX * motionSin + motionZ * motionCos) * 100.0f;
        float motionSub = (float)(motionX * motionCos - motionZ * motionSin) * 100.0f;
        final float pitch = entitylivingbaseIn.y + (entitylivingbaseIn.w - entitylivingbaseIn.y) * partialTicks;
        if (motionAdd > 3.0f) {
            motionAdd = 3.0f;
        }
        if (motionSub < -100.0f) {
            motionSub = -100.0f;
        }
        if (motionSub > 100.0f) {
            motionSub = 100.0f;
        }
        final float strength = 200.0f;
        final float inertiaZ = motionSub / strength;
        final float inertiaX = motionAdd / strength;
        final float gravityX = ((pitch > 0.0f) ? 0.0f : (pitch / 400.0f)) + (float)((motionY < 0.0) ? (motionY / 60.0) : (motionY / 10.0));
        this.scarfHanging.f = -pitch / 250.0f;
        brs next = this.scarfHanging;
        for (int i = 0; i <= 4; ++i) {
            next = next.m.get(0);
            next.h = inertiaZ;
            next.f = inertiaX - gravityX;
            if (i == 1 && pitch > 0.0f) {
                next.f = inertiaX - pitch / 55.0f;
            }
        }
        this.scarfHanging.a(2.5f, -2.0f, -2.9f);
        this.scarfHanging.a(scale);
        this.scarfHanging.a(2.0f, -2.0f, -5.0f);
        this.scarfHanging.a(scale / 1.4f);
        for (int i = 0; i < 4; ++i) {
            this.scarfRing.a(scale);
            bus.b(90.0f, 0.0f, 1.0f, 0.0f);
        }
        bus.H();
    }
    
    public int getCosmeticId() {
        return 41;
    }
    
    public String getCosmeticName() {
        return "Scarf";
    }
    
    public boolean isOfflineAvailable() {
        return false;
    }
    
    public static class CosmeticScarfData extends CosmeticData
    {
        private UserTextureContainer userTextureContainer;
        private Color color;
        
        public CosmeticScarfData() {
            this.color = Color.WHITE;
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.color = Color.decode("#" + data[0]);
            if (data.length >= 2) {
                this.userTextureContainer.setFileName(UUID.fromString(data[1]));
            }
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getScarfContainer();
        }
        
        @Override
        public EnumLegacyCosmeticType getType() {
            return EnumLegacyCosmeticType.FACE;
        }
        
        public Color getColor() {
            return this.color;
        }
    }
}
