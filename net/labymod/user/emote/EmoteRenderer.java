//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.emote;

import java.util.*;
import net.labymod.user.emote.keys.provider.*;
import net.labymod.user.emote.keys.*;
import net.labymod.main.*;
import net.labymod.core.*;

public class EmoteRenderer
{
    private UUID uuid;
    private short emoteId;
    private long timeout;
    private boolean stream;
    private EmoteProvider emoteProvider;
    private long startTime;
    private PoseAtTime[] emotePosesAtTime;
    private boolean aborted;
    private long resetKeyframeEnd;
    private EmoteBodyPart[] bodyParts;
    private float fadedYaw;
    private float fadedPitch;
    private boolean visible;
    private boolean mc18;
    
    public EmoteRenderer(final UUID uuid, final short emoteId, final long timeout, final boolean stream, final EmoteProvider emoteProvider, final EmoteRenderer prevRenderer) {
        this.startTime = System.currentTimeMillis();
        this.emotePosesAtTime = new PoseAtTime[7];
        this.aborted = false;
        this.resetKeyframeEnd = -1L;
        this.visible = true;
        this.mc18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
        this.uuid = uuid;
        this.emoteId = emoteId;
        this.timeout = timeout;
        this.stream = stream;
        this.emoteProvider = emoteProvider;
        this.bodyParts = new EmoteBodyPart[7];
        for (int i = 0; i < this.bodyParts.length; ++i) {
            this.bodyParts[i] = new EmoteBodyPart(i);
        }
        if (prevRenderer != null) {
            this.bodyParts = prevRenderer.getBodyParts();
            for (final EmoteBodyPart bodyPart : this.bodyParts) {
                bodyPart.cancel();
            }
        }
    }
    
    public void checkForNextFrame() {
        final long currentTick = System.currentTimeMillis() - this.startTime;
        if (this.emoteProvider.isWaiting()) {
            return;
        }
        boolean shouldReset = true;
        if (this.resetKeyframeEnd == -1L) {
            for (int bodyPartId = 0; bodyPartId < this.emotePosesAtTime.length; ++bodyPartId) {
                final PoseAtTime emotePose = this.emotePosesAtTime[bodyPartId];
                if ((emotePose != null && emotePose.getOffset() >= currentTick) || this.emoteProvider.hasNext(bodyPartId)) {
                    shouldReset = false;
                }
                if ((emotePose == null || emotePose.getOffset() < currentTick) && this.emoteProvider.hasNext(bodyPartId)) {
                    final PoseAtTime nextPose = this.emoteProvider.next(bodyPartId);
                    this.bindNextPose(this.emotePosesAtTime[bodyPartId] = nextPose);
                }
            }
        }
        if (shouldReset) {
            if (this.resetKeyframeEnd == -1L) {
                this.resetKeyframeEnd = currentTick + this.timeout;
                for (int bodyPartId = 0; bodyPartId < this.emotePosesAtTime.length; ++bodyPartId) {
                    final EmotePose pose = new EmotePose(bodyPartId, 0.0f, 0.0f, 0.0f, false);
                    for (final EmoteBodyPart bodyPart : this.bodyParts) {
                        if (bodyPart.getId() == pose.getBodyPart()) {
                            bodyPart.cancel();
                            bodyPart.applyPose(pose, this.timeout);
                        }
                    }
                }
            }
            else if (this.resetKeyframeEnd < currentTick && !this.aborted) {
                this.aborted = true;
                if (this.aborted) {
                    LabyMod.getInstance().getEmoteRegistry().setCleanPlayingMap(true);
                }
            }
        }
    }
    
    public void bindNextPose(final PoseAtTime poseAtTime) {
        for (final EmoteBodyPart bodyPart : this.bodyParts) {
            if (bodyPart.getId() == poseAtTime.getPose().getBodyPart()) {
                bodyPart.applyPose(poseAtTime.getPose(), poseAtTime.isAnimate() ? poseAtTime.getOffset() : 0L);
            }
        }
    }
    
    public void animate() {
        for (final EmoteBodyPart bodyPart : this.bodyParts) {
            bodyPart.animateOnTime();
        }
    }
    
    public void transformEntity(final vg entity, final boolean firstPerson, final float yaw, final float pitch) {
        if (this.aborted || !this.visible) {
            return;
        }
        if (!this.mc18 && bib.z().t.aw == 0) {
            this.checkForNextFrame();
            this.animate();
        }
        this.fadedYaw = yaw;
        this.fadedPitch = pitch;
        for (final EmoteBodyPart bodyPart : this.bodyParts) {
            for (final PoseAtTime pose : this.emotePosesAtTime) {
                if (pose != null) {
                    if (pose.getPose().getBodyPart() == bodyPart.getId()) {
                        switch (bodyPart.getId()) {
                            case 0: {
                                final float emoteYaw = bodyPart.getX() * 57.295776f / 2.0f;
                                final float emotePitch = bodyPart.getY() * 57.295776f / 2.0f;
                                if (this.stream || this.timeout == 0L) {
                                    this.fadedYaw = emoteYaw;
                                    this.fadedPitch = emotePitch;
                                    break;
                                }
                                final long currentTick = System.currentTimeMillis() - this.startTime;
                                final long keyframeEnd = this.resetKeyframeEnd;
                                final float maxFade = (float)this.timeout;
                                final float fadeIn = maxFade - ((currentTick > maxFade) ? maxFade : ((float)currentTick));
                                final float fadeOut = (float)(this.timeout - ((currentTick > keyframeEnd) ? 0L : (keyframeEnd - currentTick)));
                                float fade = fadeIn;
                                if (keyframeEnd != -1L) {
                                    fade = fadeOut;
                                }
                                final float diff = (yaw - emoteYaw + 180.0f) % 360.0f - 180.0f;
                                final float rangeYaw = (diff < -180.0f) ? (diff + 360.0f) : diff;
                                this.fadedYaw = emoteYaw + rangeYaw / maxFade * fade;
                                final float rangePitch = pitch - emotePitch;
                                this.fadedPitch = emotePitch + rangePitch / maxFade * fade;
                                break;
                            }
                            case 5: {
                                bus.c(0.0f, firstPerson ? 1.0f : 0.4f, 0.0f);
                                final int modifier = firstPerson ? -1 : 1;
                                bus.b(bodyPart.getX() * 57.295776f * modifier, 1.0f, 0.0f, 0.0f);
                                bus.b(bodyPart.getY() * 57.295776f, 0.0f, 1.0f, 0.0f);
                                bus.b(bodyPart.getZ() * 57.295776f, 0.0f, 0.0f, 1.0f);
                                bus.c(0.0f, firstPerson ? -1.0f : -0.4f, 0.0f);
                                break;
                            }
                            case 6: {
                                bus.b(bodyPart.getX() / 10.0, bodyPart.getY() / 10.0, bodyPart.getZ() / 10.0);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (Math.abs(entity.m - entity.p) > 0.04 || Math.abs(entity.n - entity.q) > 0.04 || Math.abs(entity.o - entity.r) > 0.04) {
            for (final PoseAtTime poseAtTime : this.emotePosesAtTime) {
                if (poseAtTime != null && poseAtTime.getPose().isBlockMovement()) {
                    this.abort();
                    break;
                }
            }
        }
        if (entity instanceof aed && ((aed)entity).ay != 0) {
            this.abort();
        }
        if (LabyModCore.getMinecraft().isElytraFlying(entity)) {
            this.abort();
        }
    }
    
    public void transformModel(final bpx model) {
        if (bib.z().t.aw == 0) {
            this.checkForNextFrame();
            this.animate();
            bib.z().g.o();
        }
        if (model == null || this.aborted || !this.visible) {
            return;
        }
        for (final EmoteBodyPart bodyPart : this.bodyParts) {
            for (final PoseAtTime pose : this.emotePosesAtTime) {
                if (pose != null && pose.getPose().getBodyPart() == bodyPart.getId()) {
                    switch (bodyPart.getId()) {
                        case 0: {
                            model.e.g = this.getFadedYaw() / 57.295776f;
                            model.e.f = this.getFadedPitch() / 57.295776f;
                            break;
                        }
                        case 1: {
                            model.h.f = bodyPart.getX();
                            model.h.g = bodyPart.getY();
                            model.h.h = bodyPart.getZ();
                            break;
                        }
                        case 2: {
                            model.i.f = bodyPart.getX();
                            model.i.g = bodyPart.getY();
                            model.i.h = bodyPart.getZ();
                            break;
                        }
                        case 3: {
                            model.k.f = bodyPart.getX();
                            model.k.g = bodyPart.getY();
                            model.k.h = bodyPart.getZ();
                            break;
                        }
                        case 4: {
                            model.j.f = bodyPart.getX();
                            model.j.g = bodyPart.getY();
                            model.j.h = bodyPart.getZ();
                            break;
                        }
                    }
                }
                if (model instanceof bqj) {
                    copyToSecondLayer((bqj)model);
                }
            }
        }
    }
    
    public static void resetModel(final bpx model) {
        resetModelRenderer(model.h);
        resetModelRenderer(model.i);
        resetModelRenderer(model.k);
        resetModelRenderer(model.j);
        resetModelRenderer(model.g);
        if (model instanceof bqj) {
            copyToSecondLayer((bqj)model);
        }
    }
    
    private static void resetModelRenderer(final brs modelRenderer) {
        modelRenderer.f = 0.0f;
        modelRenderer.g = 0.0f;
        modelRenderer.h = 0.0f;
    }
    
    private static void copyToSecondLayer(final bqj model) {
        bpx.a(model.e, model.f);
        bpx.a(model.g, model.u);
        bpx.a(model.h, model.b);
        bpx.a(model.i, model.a);
        bpx.a(model.k, model.c);
        bpx.a(model.j, model.d);
    }
    
    public void abort() {
        for (final EmoteBodyPart bodyPart : this.bodyParts) {
            bodyPart.cancel();
        }
        this.emoteProvider.clear();
        this.resetKeyframeEnd = 0L;
        this.checkForNextFrame();
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public short getEmoteId() {
        return this.emoteId;
    }
    
    public long getTimeout() {
        return this.timeout;
    }
    
    public boolean isStream() {
        return this.stream;
    }
    
    public EmoteProvider getEmoteProvider() {
        return this.emoteProvider;
    }
    
    public long getStartTime() {
        return this.startTime;
    }
    
    public PoseAtTime[] getEmotePosesAtTime() {
        return this.emotePosesAtTime;
    }
    
    public boolean isAborted() {
        return this.aborted;
    }
    
    public long getResetKeyframeEnd() {
        return this.resetKeyframeEnd;
    }
    
    public EmoteBodyPart[] getBodyParts() {
        return this.bodyParts;
    }
    
    public float getFadedYaw() {
        return this.fadedYaw;
    }
    
    public float getFadedPitch() {
        return this.fadedPitch;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public boolean isMc18() {
        return this.mc18;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
}
