//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.sticker;

import net.labymod.user.cosmetic.*;
import net.labymod.user.*;
import net.labymod.main.*;

public class StickerRenderer
{
    private ModelCosmetics modelCosmetics;
    private brs bubble;
    private brs image;
    
    public StickerRenderer(final ModelCosmetics modelCosmetics, final float modelSize) {
        this.modelCosmetics = modelCosmetics;
        this.initModel(modelSize);
    }
    
    public void initModel(final float modelSize) {
        final int bubbleWidth = 12;
        final int bubbleHeight = 10;
        final brs bubble = new brs((bqf)this.modelCosmetics);
        bubble.a((float)(-bubbleWidth / 2), (float)(-bubbleHeight / 2), 0.0f, bubbleWidth, bubbleHeight, 1, modelSize);
        bubble.k = true;
        final brs edgeTop = new brs((bqf)this.modelCosmetics);
        edgeTop.a((float)(-bubbleWidth / 2 + 1), (float)(-bubbleHeight / 2 - 1), 0.0f, bubbleWidth - 2, 1, 1, modelSize);
        bubble.a(edgeTop);
        final brs edgeLeft = new brs((bqf)this.modelCosmetics);
        edgeLeft.a((float)(-bubbleWidth / 2 - 1), (float)(-bubbleHeight / 2 + 1), 0.0f, 1, bubbleHeight - 2, 1, modelSize);
        bubble.a(edgeLeft);
        final brs edgeRight = new brs((bqf)this.modelCosmetics);
        edgeRight.a((float)(bubbleWidth / 2), (float)(-bubbleHeight / 2 + 1), 0.0f, 1, bubbleHeight - 2, 1, modelSize);
        bubble.a(edgeRight);
        final brs edgeBottom = new brs((bqf)this.modelCosmetics);
        edgeBottom.a((float)(-bubbleWidth / 2 + 1), (float)(bubbleHeight / 2), 0.0f, bubbleWidth - 2, 1, 1, modelSize);
        bubble.a(edgeBottom);
        final brs tip = new brs((bqf)this.modelCosmetics);
        tip.a((float)(-bubbleWidth / 2 + 3), (float)(bubbleHeight / 2 + 1), 0.0f, 3, 1, 1, modelSize);
        tip.a(-bubbleWidth / 2 + 3.4f, (float)(bubbleHeight / 2 + 2), 0.2f, 2, 1, 1, modelSize);
        tip.a(-bubbleWidth / 2 + 4.5f, (float)(bubbleHeight / 2 + 3), 0.4f, 1, 1, 1, modelSize);
        bubble.a(tip);
        this.bubble = bubble;
        (this.image = new brs((bqf)this.modelCosmetics).b(22, 11).a(0, 0)).a((float)(-bubbleHeight / 2), (float)(-bubbleHeight / 2), 0.0f, bubbleHeight, bubbleHeight, 1, modelSize);
        this.image.k = true;
    }
    
    public void render(final vg entityIn, final User user, final long timePassed, final float scale, final float movementFactor, final float walkingSpeed, final float tickValue, final float firstRotationX, final float secondRotationX) {
        final nf location = LabyMod.getInstance().getUserManager().getCosmeticImageManager().getStickerImageHandler().getResourceLocation((bua)entityIn);
        if (location == null) {
            return;
        }
        bus.G();
        bus.f();
        double popAnimation = 1.0 / (timePassed / 1000.0 * 1.7000000476837158) - 2.0;
        popAnimation = Math.max(-1.0, popAnimation);
        final double scaleAnimation = Math.min(0.0, popAnimation) / 1.0;
        bus.b(0.0, -1.0 / (popAnimation * 2.0) - 2.5, 0.0);
        bus.a(-scaleAnimation, -scaleAnimation, -scaleAnimation);
        final bzf renderManager = bib.z().ac();
        if (entityIn instanceof bua) {
            final bua entity = (bua)entityIn;
            final float rotation = renderManager.e - entity.aP;
            if (entity != bib.z().aa()) {
                bus.b(rotation + 180.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (bib.z().t.aw == 1) {
                bus.b(180.0f, 0.0f, 1.0f, 0.0f);
            }
            bus.b(firstRotationX, 0.0f, 1.0f, 0.0f);
        }
        bib.z().N().a(ModTextures.VOID);
        this.bubble.k = false;
        this.bubble.a(scale);
        this.bubble.k = true;
        bib.z().N().a(location);
        bus.b(0.95f, 0.95f, 0.95f);
        bus.b(1.0f, 1.0f, 1.5f);
        bus.c(0.0f, 0.0f, -0.01f);
        this.image.k = false;
        this.image.a(scale);
        this.image.k = true;
        bus.H();
    }
}
