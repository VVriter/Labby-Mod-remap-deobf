//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.cosmetic.cosmetics.shop.pet;

import net.labymod.user.cosmetic.pet.object.*;
import net.labymod.user.cosmetic.pet.ai.*;
import net.labymod.user.cosmetic.*;
import net.labymod.utils.*;
import net.labymod.user.cosmetic.pet.*;
import net.labymod.user.cosmetic.geometry.*;
import net.labymod.user.cosmetic.animation.*;
import net.labymod.user.cosmetic.animation.model.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.main.*;
import net.labymod.core.*;
import net.labymod.user.cosmetic.custom.*;
import net.labymod.user.*;
import java.util.*;

public class CosmeticPetDragon extends CosmeticPet<CosmeticPetDragonData>
{
    public static final int ID = 44;
    private brs dragon;
    private brs body;
    private brs wing;
    private brs wingTip;
    private brs thigh;
    private brs lowerLeg;
    private brs foot;
    private brs overArm;
    private brs lowerArm;
    private brs hand;
    private brs tail;
    private brs neck;
    private brs head;
    public final PetCodeAnimator EMOTE_FLY_UP;
    public final PetCodeAnimator EMOTE_FLY_DOWN;
    
    public CosmeticPetDragon() {
        this.EMOTE_FLY_UP = new PetCodeAnimator() {
            @Override
            public void create() {
                this.keyframe(0L, EnumPetDragonBodyPart.DRAGON.ordinal(), -50.0f, 0.0f, 0.0f, 0.0f, -60.0f, 50.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.NECK_FIRST_HALF.ordinal(), -20.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.NECK_SECOND_HALF.ordinal(), 25.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.TAIL.ordinal(), 20.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.WING.ordinal(), 0.0f, -30.0f, 60.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.WING_TIP.ordinal(), 0.0f, 0.0f, -160.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.OVER_ARM.ordinal(), 30.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.LOWER_ARM.ordinal(), 10.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.HAND.ordinal(), 10.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.THIGH.ordinal(), -50.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.LOWER_LEG.ordinal(), 120.0f, 0.0f, 0.0f, true);
                this.keyframe(0L, EnumPetDragonBodyPart.FOOT.ordinal(), -50.0f, 0.0f, 0.0f, true);
                this.keyframe(200L, EnumPetDragonBodyPart.OVER_ARM.ordinal(), -10.0f, 0.0f, 0.0f, true);
                this.keyframe(200L, EnumPetDragonBodyPart.LOWER_ARM.ordinal(), 80.0f, 0.0f, 0.0f, true);
                this.keyframe(200L, EnumPetDragonBodyPart.HAND.ordinal(), -60.0f, 0.0f, 0.0f, true);
                this.keyframe(200L, EnumPetDragonBodyPart.LOWER_LEG.ordinal(), 150.0f, 0.0f, 0.0f, true);
                this.keyframe(200L, EnumPetDragonBodyPart.NECK_SECOND_HALF.ordinal(), 24.0f, 0.0f, 0.0f, true);
                this.keyframe(200L, EnumPetDragonBodyPart.WING_TIP.ordinal(), 0.0f, 0.0f, -30.0f, true);
                this.keyframe(300L, EnumPetDragonBodyPart.WING.ordinal(), 0.0f, -30.0f, 80.0f, true);
                this.keyframe(300L, EnumPetDragonBodyPart.DRAGON.ordinal(), -20.0f, 0.0f, 0.0f, 0.0f, -60.0f, 50.0f, true);
                this.keyframe(600L, EnumPetDragonBodyPart.WING.ordinal(), 0.0f, 0.0f, -10.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.WING_TIP.ordinal(), 0.0f, 0.0f, -50.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.LOWER_ARM.ordinal(), 30.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.HAND.ordinal(), 20.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.THIGH.ordinal(), -20.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.LOWER_LEG.ordinal(), 50.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.FOOT.ordinal(), 0.0f, 0.0f, 0.0f, true);
                this.keyframe(800L, EnumPetDragonBodyPart.TAIL.ordinal(), 0.0f, 0.0f, 0.0f, true);
                this.keyframe(800L, EnumPetDragonBodyPart.WING.ordinal(), 0.0f, 0.0f, 20.0f, true);
                this.keyframe(900L, EnumPetDragonBodyPart.WING_TIP.ordinal(), 0.0f, 0.0f, -20.0f, true);
                this.keyframe(900L, EnumPetDragonBodyPart.NECK_FIRST_HALF.ordinal(), 0.0f, 0.0f, 0.0f, true);
                this.keyframe(900L, EnumPetDragonBodyPart.NECK_SECOND_HALF.ordinal(), 0.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.LOWER_LEG.ordinal(), 80.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.LOWER_ARM.ordinal(), 60.0f, 0.0f, 0.0f, true);
                this.keyframe(700L, EnumPetDragonBodyPart.HEAD.ordinal(), 30.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.DRAGON.ordinal(), 0.0f, 0.0f, 0.0f, 0.0f, -60.0f, 50.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.HEAD.ordinal(), 0.0f, 0.0f, 0.0f, true);
            }
        };
        this.EMOTE_FLY_DOWN = new PetCodeAnimator() {
            @Override
            public void create() {
                this.keyframe(0L, EnumPetDragonBodyPart.DRAGON.ordinal(), 0.0f, 0.0f, 0.0f, 0.0f, -60.0f, 50.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.DRAGON.ordinal(), -50.0f, 0.0f, 0.0f, 0.0f, -60.0f, 50.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.NECK_FIRST_HALF.ordinal(), -20.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.NECK_SECOND_HALF.ordinal(), 25.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.TAIL.ordinal(), 20.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.WING.ordinal(), 0.0f, -30.0f, 60.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.WING_TIP.ordinal(), 0.0f, 0.0f, -160.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.OVER_ARM.ordinal(), 30.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.LOWER_ARM.ordinal(), 10.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.HAND.ordinal(), 10.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.THIGH.ordinal(), -50.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.LOWER_LEG.ordinal(), 120.0f, 0.0f, 0.0f, true);
                this.keyframe(1000L, EnumPetDragonBodyPart.FOOT.ordinal(), -50.0f, 0.0f, 0.0f, true);
            }
        };
        this.init(44, new FlyingAI<CosmeticPetDragonData>(this));
    }
    
    public void addModels(final ModelCosmetics modelCosmetics, final float modelSize) {
        final int width = 448;
        final int height = 282;
        modelCosmetics.a("body.body", 0, 0);
        modelCosmetics.a("body.horn", 130, 235);
        modelCosmetics.a("wingtip.bone", 176, 73);
        modelCosmetics.a("wing.skin", 176, 16);
        modelCosmetics.a("wing.bone", 176, 0);
        modelCosmetics.a("wingtip.skin", 176, 81);
        modelCosmetics.a("tail.tail", 130, 138);
        modelCosmetics.a("tail.horn", 130, 235);
        modelCosmetics.a("neck.neck", 130, 197);
        modelCosmetics.a("neck.horn", 130, 235);
        modelCosmetics.a("head.head", 248, 138);
        modelCosmetics.a("head.uppermouth", 248, 202);
        modelCosmetics.a("head.lowermouth", 248, 242);
        modelCosmetics.a("head.nose", 146, 235);
        this.dragon = new ModelRendererHook((bqf)modelCosmetics);
        this.dragon.p = -4.375f;
        (this.body = new ModelRendererHook((bqf)modelCosmetics, "body").b(width, height)).a(0.0f, 4.0f, 8.0f);
        this.body.a("body", -12.0f, 0.0f, -16.0f, 24, 24, 64);
        this.body.a("horn", -2.0f, -6.0f, -2.0f, 4, 8, 4);
        this.body.a("horn", -2.0f, -7.0f, 18.0f, 4, 8, 4);
        this.body.a("horn", -2.0f, -6.0f, 38.0f, 4, 8, 4);
        this.dragon.a(this.body);
        (this.wing = new ModelRendererHook((bqf)modelCosmetics, "wing").b(width, height)).a(-8.0f, 5.0f, 2.0f);
        this.wing.a("skin", -46.0f, 0.0f, 2.0f, 46, 1, 56);
        (this.wingTip = new ModelRendererHook((bqf)modelCosmetics, "wingtip").b(width, height)).a(-46.0f, 0.5f, 0.0f);
        this.wingTip.a("skin", -80.0f, -0.5f, 0.0f, 80, 1, 56);
        this.wing.a(this.wingTip);
        (this.thigh = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 88)).a(-8.0f, -4.0f, -8.0f, 16, 32, 16);
        this.thigh.a(-16.0f, 16.0f, 44.0f);
        (this.lowerLeg = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(64, 98)).a(-6.0f, -4.0f, -6.0f, 12, 26, 12);
        this.lowerLeg.a(0.0f, 26.0f, 0.0f);
        this.thigh.a(this.lowerLeg);
        (this.foot = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 136)).a(-6.0f, 0.0f, -16.0f, 12, 6, 24);
        this.foot.a(0.0f, 22.0f, 0.0f);
        this.lowerLeg.a(this.foot);
        (this.overArm = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 166)).a(-8.0f, -4.0f, -8.0f, 16, 32, 16);
        this.overArm.a(-16.0f, 16.0f, 4.0f);
        (this.lowerArm = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(64, 176)).a(-6.0f, -4.0f, -6.0f, 12, 26, 12);
        this.lowerArm.a(0.0f, 26.0f, 0.0f);
        this.overArm.a(this.lowerArm);
        (this.hand = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(0, 214)).a(-6.0f, 0.0f, -16.0f, 12, 6, 24);
        this.hand.a(0.0f, 22.0f, 0.0f);
        this.lowerArm.a(this.hand);
        (this.tail = new ModelRendererHook((bqf)modelCosmetics).b(width, height)).a(0.0f, 16.0f, 26.0f);
        brs tailTile = this.tail;
        for (int i = 0; i <= 6; ++i) {
            final ModelRendererHook part = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics, "tail").b(width, height);
            part.a("tail", -12.0f, -12.0f, 0.0f, 24, 24, 35);
            part.a("horn", -2.0f, -20.0f, 20.0f, 4, 8, 4);
            part.a(0.0f, 0.0f, 35.0f);
            part.setHook(new Consumer<ModelRendererHook>() {
                @Override
                public void accept(final ModelRendererHook accepted) {
                    final double k = 0.8100000023841858;
                    bus.a(k, k, k);
                    accepted.renderSuper();
                }
            });
            tailTile.a((brs)part);
            tailTile = part;
        }
        (this.neck = new ModelRendererHook((bqf)modelCosmetics).b(width, height)).a(0.0f, 21.0f, 0.0f);
        brs neckTile = this.neck;
        for (int j = 0; j <= 8; ++j) {
            final ModelRendererHook part2 = (ModelRendererHook)new ModelRendererHook((bqf)modelCosmetics, "neck").b(width, height);
            part2.a("neck", -12.0f, -12.0f, -12.0f, 24, 24, 14);
            if (j != 0) {
                part2.a("horn", -2.0f, -20.0f, -2.0f, 4, 8, 4);
            }
            part2.a(0.0f, 0.0f, -8.0f);
            part2.setHook(new Consumer<ModelRendererHook>() {
                @Override
                public void accept(final ModelRendererHook accepted) {
                    final double k = 0.9700000286102295;
                    bus.a(k, k, k);
                    accepted.renderSuper();
                }
            });
            neckTile.a((brs)part2);
            neckTile = part2;
        }
        (this.head = new ModelRendererHook((bqf)modelCosmetics, "head").b(width, height)).a(-0.5f, -4.0f, -8.0f);
        this.head.a("head", -16.0f, -16.0f, -32.0f, 32, 32, 32);
        this.head.a("uppermouth", -16.0f, 0.0f, -64.0f, 32, 8, 32);
        this.head.a("lowermouth", -16.0f, 8.2f, -64.0f, 32, 8, 32);
        this.head.a("nose", -14.0f, -3.0f, -56.0f, 6, 3, 10);
        this.head.a("nose", 8.0f, -3.0f, -56.0f, 6, 3, 10);
        neckTile.a(this.head);
        for (int j = 0; j < 2; ++j) {
            final int mirror = (j == 0) ? -1 : 1;
            final brs horn = new ModelRendererHook((bqf)modelCosmetics).b(width, height).a(178, 235);
            horn.a(11.0f * mirror, -20.0f, -10.0f);
            horn.a(-4.0f, -24.0f, -4.0f, 8, 36, 8);
            horn.f = -0.8f;
            horn.h = 0.3f * mirror;
            this.head.a(horn);
        }
    }
    
    public void setInvisible(final boolean invisible) {
        this.dragon.j = invisible;
        this.body.j = invisible;
        this.thigh.j = invisible;
        this.tail.j = invisible;
        this.overArm.j = invisible;
        this.neck.j = invisible;
        this.wing.j = invisible;
        this.wingTip.j = invisible;
    }
    
    protected void updateFlyingAnimation(final PetStorage<CosmeticPetDragonData> data, final float tickValue) {
        final float speed = 3.0f;
        final float rotation = tickValue / speed - 1.4f;
        this.wing.f = 0.125f - (float)Math.cos(rotation) * 0.2f;
        this.wing.g = 0.25f;
        this.wing.h = (float)(Math.sin(rotation) + 1.225) * 0.3f;
        this.wingTip.h = -(float)(Math.sin(rotation + 2.0f) + 0.5) * 0.75f;
        this.thigh.f = -0.2f;
        this.lowerLeg.f = 1.0f + (float)Math.cos(tickValue / speed) * 0.2f;
        this.hand.f = 0.5f;
        this.overArm.f = -0.2f;
        this.lowerArm.f = 1.0f + (float)Math.cos(tickValue / speed) * 0.2f;
        this.hand.f = 0.6f;
        brs tailTile = this.tail;
        for (int i = 0; i <= 6; ++i) {
            tailTile = tailTile.m.get(0);
            tailTile.f = (float)(-Math.cos(-i * 0.6f + tickValue / speed + 5.6f)) / 15.0f;
            tailTile.g = 0.0f;
            tailTile.h = 0.0f;
        }
        brs neckTile = this.neck;
        for (int j = 0; j <= 8; ++j) {
            neckTile = neckTile.m.get(0);
            neckTile.f = (float)(-Math.cos(j * 0.6f + tickValue / speed)) / 15.0f;
            neckTile.g = 0.0f;
            neckTile.h = 0.0f;
        }
        this.head.f = (float)(Math.cos(6.2999997f + tickValue / speed - 0.7f) / 6.0);
        this.head.g = 0.0f;
        this.head.h = 0.0f;
        this.dragon.f = (float)Math.toRadians(-Math.cos(tickValue / speed - 0.7f) * 7.0);
    }
    
    protected void updateIdleAnimation(final PetStorage<CosmeticPetDragonData> data, final float tickValue) {
        final float rotation = tickValue / 100.0f * 3.1415927f * 2.0f;
        this.wing.f = 0.1f - (float)Math.cos(rotation) * 0.2f;
        this.wing.g = -0.4f - (float)(Math.sin(rotation) + 1.225) * 0.1f;
        this.wing.h = 0.9f;
        this.wingTip.h = -2.6f + (float)(Math.sin(rotation + 2.0f) + 0.5) * 0.05f;
        this.thigh.f = -0.8f;
        this.lowerLeg.f = 2.3f;
        this.foot.f = -0.6f;
        this.overArm.f = 0.5f;
        this.lowerArm.f = 0.1f;
        this.hand.f = 0.3f;
        this.head.f = (float)(-Math.cos(tickValue / 40.0f)) / 8.0f + 0.5f;
        this.head.g = 0.0f;
        this.head.h = 0.0f;
        brs tailTile = this.tail;
        for (int i = 0; i <= 6; ++i) {
            tailTile = tailTile.m.get(0);
            tailTile.f = (float)Math.cos(tickValue / 10.0f) / 15.0f + 0.3f;
            tailTile.g = (float)Math.cos(tickValue / (14.0f - i)) / 5.0f;
        }
        brs neckTile = this.neck;
        for (int j = 0; j <= 8; ++j) {
            neckTile = neckTile.m.get(0);
            neckTile.h = (float)Math.cos(tickValue / 90.0f) / 20.0f;
            neckTile.f = (float)(((j < 5) ? -0.3f : (0.05f + j * 0.07f)) + Math.cos(tickValue / 40.0f) / 40.0);
        }
        this.dragon.f = (float)Math.toRadians(-50.0);
    }
    
    public BlockBenchLoader getGeometry() {
        return null;
    }
    
    @Override
    public void handleEvent(final EnumTrigger event, final PetStorage<CosmeticPetDragonData> data, final float currentTick, final vg entity) {
        if (event == EnumTrigger.IDLE) {
            this.updateIdleAnimation(data, currentTick);
        }
        if (event == EnumTrigger.MOVING) {
            this.updateFlyingAnimation(data, currentTick);
        }
        if (event == EnumTrigger.STOP_MOVING || event == EnumTrigger.START_MOVING) {
            data.animationController.play((event == EnumTrigger.STOP_MOVING) ? this.EMOTE_FLY_DOWN.getAnimation() : this.EMOTE_FLY_UP.getAnimation(), currentTick);
            data.attached = false;
        }
    }
    
    public void transform(final String boneName, final KeyframeVector rotation, final KeyframeVector position, final KeyframeVector scale) {
        switch (Integer.parseInt(boneName)) {
            case 0: {
                this.applyTransform(this.dragon, rotation, position);
                break;
            }
            case 1: {
                this.applyTransform(this.head, rotation, position);
                break;
            }
            case 2: {
                brs neckTile = this.neck;
                for (int i = 0; i <= 8; ++i) {
                    neckTile = neckTile.m.get(0);
                    this.applyTransform(neckTile, rotation, position);
                }
                break;
            }
            case 3: {
                brs tailTile = this.tail;
                for (int j = 0; j <= 6; ++j) {
                    tailTile = tailTile.m.get(0);
                    this.applyTransform(tailTile, rotation, position);
                }
                break;
            }
            case 4: {
                this.applyTransform(this.thigh, rotation, position);
                break;
            }
            case 5: {
                this.applyTransform(this.lowerLeg, rotation, position);
                break;
            }
            case 6: {
                this.applyTransform(this.foot, rotation, position);
                break;
            }
            case 7: {
                this.applyTransform(this.overArm, rotation, position);
                break;
            }
            case 8: {
                this.applyTransform(this.lowerArm, rotation, position);
                break;
            }
            case 9: {
                this.applyTransform(this.hand, rotation, position);
                break;
            }
            case 10: {
                this.applyTransform(this.wing, rotation, position);
                break;
            }
            case 11: {
                this.applyTransform(this.wingTip, rotation, position);
                break;
            }
            case 12: {
                brs neckTilePart1 = this.neck;
                for (int k = 0; k <= 8; ++k) {
                    neckTilePart1 = neckTilePart1.m.get(0);
                    if (k < 4) {
                        this.applyTransform(neckTilePart1, rotation, position);
                    }
                }
                break;
            }
            case 13: {
                brs neckTilePart2 = this.neck;
                for (int l = 0; l <= 8; ++l) {
                    neckTilePart2 = neckTilePart2.m.get(0);
                    if (l >= 4) {
                        this.applyTransform(neckTilePart2, rotation, position);
                    }
                }
                break;
            }
            case 14: {
                this.applyTransform(this.body, rotation, position);
                break;
            }
        }
    }
    
    public void resetTransformation(final String boneName) {
    }
    
    public void applyEffects(final vg entity, final CosmeticData data, final float movementFactor, final float walkingSpeed, final float currentTick, final float partialTicks, final boolean rightSide) {
    }
    
    private void applyTransform(final brs model, final KeyframeVector rotation, final KeyframeVector position) {
        model.f = (float)Math.toRadians(rotation.x);
        model.g = (float)Math.toRadians(rotation.y);
        model.h = (float)Math.toRadians(rotation.z);
        model.o = (float)(position.x / 16.0);
        model.p = (float)(position.y / 16.0);
        model.q = (float)(position.z / 16.0);
    }
    
    @Override
    protected nf getResourceLocation(final bua player, final CosmeticPetDragonData data) {
        return LabyMod.getInstance().getUserManager().getCosmeticImageManager().getPetDragonImageHandler().getResourceLocation(player);
    }
    
    public void renderModel(final float renderScale) {
        bus.G();
        bus.b(0.045f, 0.045f, 0.045f);
        bus.c(this.dragon.o, this.dragon.p, this.dragon.q);
        bus.c(this.dragon.c * renderScale, this.dragon.d * renderScale, this.dragon.e * renderScale);
        if (this.dragon.h != 0.0f) {
            bus.b(this.dragon.h * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        if (this.dragon.g != 0.0f) {
            bus.b(this.dragon.g * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (this.dragon.f != 0.0f) {
            bus.b(this.dragon.f * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        bus.G();
        bus.q();
        for (int i = 0; i < 2; ++i) {
            if (i == 1) {
                bus.b(-1.0f, 1.0f, 1.0f);
                LabyModCore.getRenderImplementation().cullFaceFront();
            }
            bus.G();
            bus.a(0.9, 0.9, 0.9);
            this.thigh.a(renderScale);
            this.overArm.a(renderScale);
            bus.H();
            this.wing.a(renderScale);
        }
        LabyModCore.getRenderImplementation().cullFaceBack();
        bus.H();
        this.body.a(renderScale);
        bus.G();
        this.tail.a(renderScale);
        bus.H();
        bus.G();
        final double k = 0.800000011920929;
        bus.a(k, k, k);
        this.neck.a(renderScale);
        bus.H();
        bus.H();
    }
    
    public int getCosmeticId() {
        return 44;
    }
    
    public String getCosmeticName() {
        return "Pet Dragon";
    }
    
    @Override
    protected PetStorage<CosmeticPetDragonData> getStorage(final CosmeticPetDragonData cosmeticData) {
        return cosmeticData.storage;
    }
    
    public static class CosmeticPetDragonData extends CosmeticData
    {
        protected PetStorage<CosmeticPetDragonData> storage;
        private UserTextureContainer userTextureContainer;
        
        public CosmeticPetDragonData() {
            this.storage = new PetStorage<CosmeticPetDragonData>();
        }
        
        @Override
        public boolean isEnabled() {
            return true;
        }
        
        @Override
        public void init(final User user) {
            this.userTextureContainer = user.getPetDragonContainer();
        }
        
        @Override
        public void loadData(final String[] data) throws Exception {
            this.userTextureContainer.setFileName(UUID.fromString(data[0]));
            this.storage.rightShoulder = (Integer.parseInt(data[1]) == 1);
        }
    }
    
    public enum EnumPetDragonBodyPart
    {
        DRAGON, 
        HEAD, 
        NECK, 
        TAIL, 
        THIGH, 
        LOWER_LEG, 
        FOOT, 
        OVER_ARM, 
        LOWER_ARM, 
        HAND, 
        WING, 
        WING_TIP, 
        NECK_FIRST_HALF, 
        NECK_SECOND_HALF, 
        BODY;
    }
}
