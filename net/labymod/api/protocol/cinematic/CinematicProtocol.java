//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.cinematic;

import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.api.*;
import net.labymod.core.*;
import com.google.gson.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class CinematicProtocol implements ServerMessageEvent
{
    private Spline spline;
    private long duration;
    private long startTime;
    private float currentTilt;
    
    public CinematicProtocol() {
        this.spline = new Spline();
        this.duration = 0L;
        this.startTime = -1L;
        this.currentTilt = 0.0f;
        final LabyModAPI api = LabyMod.getInstance().getLabyModAPI();
        final EventManager eventManager = api.getEventManager();
        eventManager.register((ServerMessageEvent)this);
        api.registerForgeListener((Object)this);
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("cinematic")) {
            final JsonObject cinematic = serverMessage.getAsJsonObject();
            this.reset(false);
            final bud player = LabyModCore.getMinecraft().getPlayer();
            if (player == null || player.bO == null || !player.bO.c) {
                return;
            }
            if (cinematic.has("points")) {
                final JsonArray points = cinematic.get("points").getAsJsonArray();
                for (int i = 0; i < points.size(); ++i) {
                    final JsonObject point = points.get(i).getAsJsonObject();
                    final double x = point.get("x").getAsDouble();
                    final double y = point.get("y").getAsDouble();
                    final double z = point.get("z").getAsDouble();
                    final double yaw = point.get("yaw").getAsDouble();
                    final double pitch = point.get("pitch").getAsDouble();
                    final double tilt = point.has("tilt") ? point.get("tilt").getAsDouble() : 0.0;
                    this.spline.add(new Position(x, y, z, yaw, pitch, tilt));
                }
                this.spline.calculate();
            }
            if (cinematic.has("duration")) {
                this.duration = cinematic.get("duration").getAsLong();
            }
            if (cinematic.has("clear_chat")) {
                LabyModCore.getMinecraft().clearChatMessages();
            }
            this.startTime = System.currentTimeMillis();
        }
    }
    
    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        if (!this.isRunning()) {
            return;
        }
        final long timePassed = System.currentTimeMillis() - this.startTime;
        final float progress = Math.min(1.0f, timePassed / (float)this.duration);
        if (progress >= 1.0f) {
            this.reset(true);
            return;
        }
        final Position position = this.spline.get(progress);
        this.currentTilt = (float)position.getTilt();
        bib.z().t.aw = 0;
        final bud player = LabyModCore.getMinecraft().getPlayer();
        if (player != null) {
            player.a(position.getX(), position.getY(), position.getZ(), (float)position.getYaw(), (float)position.getPitch());
            player.x = player.v;
            player.y = player.w;
            player.M = player.p;
            player.N = player.q;
            player.O = player.r;
        }
    }
    
    public boolean isRunning() {
        return this.duration != 0L && this.spline.isValid();
    }
    
    public void reset(final boolean completed) {
        if (completed) {
            final JsonObject cinematic = new JsonObject();
            cinematic.addProperty("completed", Boolean.valueOf(true));
            LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("cinematic", (JsonElement)cinematic);
        }
        this.spline.reset();
        this.duration = 0L;
        this.currentTilt = 0.0f;
    }
    
    public float getCurrentTilt() {
        return this.currentTilt;
    }
}
