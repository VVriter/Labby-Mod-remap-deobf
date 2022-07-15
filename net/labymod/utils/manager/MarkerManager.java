//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import net.labymod.api.events.*;
import net.labymod.labyconnect.packets.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.gson.*;
import net.labymod.core.*;
import io.netty.buffer.*;
import java.util.*;
import net.labymod.api.permissions.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import java.beans.*;

public class MarkerManager implements PluginMessageEvent, ServerMessageEvent, Consumer<PacketAddonDevelopment>
{
    private static final nf SOUND_PLACE_MARKER;
    private static final nf SOUND_MARKER_NOTIFY;
    private Map<UUID, Marker> markers;
    private boolean enabled;
    private boolean sendMarkers;
    
    public MarkerManager() {
        this.markers = new HashMap<UUID, Marker>();
        this.enabled = true;
        this.sendMarkers = false;
        LabyMod.getInstance().getLabyModAPI().getEventManager().register((PluginMessageEvent)this);
        LabyMod.getInstance().getLabyModAPI().getEventManager().register((ServerMessageEvent)this);
        LabyMod.getInstance().getLabyModAPI().getEventManager().registerOnAddonDevelopmentPacket((Consumer)this);
        LabyMod.getInstance().getLabyModAPI().registerForgeListener((Object)this);
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        this.render(LabyMod.getInstance().getPartialTicks());
    }
    
    public void receiveMessage(final String channelName, final gy packetBuffer) {
        if (channelName.equals("minecraft:brand")) {
            this.enabled = true;
        }
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (!messageKey.equals("marker")) {
            return;
        }
        try {
            final JsonObject object = serverMessage.getAsJsonObject();
            if (object.has("enabled")) {
                this.enabled = object.get("enabled").getAsBoolean();
            }
            if (object.has("send_markers")) {
                this.sendMarkers = object.get("send_markers").getAsBoolean();
            }
            if (object.has("add_marker")) {
                final JsonObject markerObject = object.get("add_marker").getAsJsonObject();
                final UUID sender = UUID.fromString(markerObject.get("sender").getAsString());
                final int x = markerObject.get("x").getAsInt();
                final int y = markerObject.get("y").getAsInt();
                final int z = markerObject.get("z").getAsInt();
                final boolean isOutside = markerObject.get("large").getAsBoolean();
                final UUID target = markerObject.has("target") ? UUID.fromString(markerObject.get("target").getAsString()) : null;
                this.updateMarker(sender, x, y, z, isOutside, target);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void accept(final PacketAddonDevelopment packet) {
        try {
            final String key = packet.getKey();
            if (!key.equals("labymod:marker") || LabyModCore.getMinecraft().getWorld() == null || !LabyMod.getSettings().marker) {
                return;
            }
            UUID target = null;
            final ByteBuf buffer = Unpooled.wrappedBuffer(packet.getData());
            final int version = buffer.readInt();
            if (version != 0) {
                buffer.release();
                return;
            }
            final int x = buffer.readInt();
            final int y = buffer.readInt();
            final int z = buffer.readInt();
            final boolean isOutside = buffer.readBoolean();
            final boolean markedEntity = buffer.readBoolean();
            if (markedEntity) {
                target = new UUID(buffer.readLong(), buffer.readLong());
            }
            buffer.release();
            final bud clientPlayer = LabyModCore.getMinecraft().getPlayer();
            final aed player = LabyModCore.getMinecraft().getWorld().b(packet.getSender());
            if (player == null || clientPlayer == null || LabyModCore.getMinecraft().getDistanceToEntitySqr((vg)player, clientPlayer) > 4096.0) {
                return;
            }
            this.updateMarker(packet.getSender(), x, y, z, isOutside, target);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateMarker(final UUID uuid, final int x, final int y, final int z, final boolean isOutside, final UUID target) {
        final Marker prevMarker = this.markers.get(uuid);
        if (prevMarker == null || !prevMarker.isVisible()) {
            final Marker marker = new Marker(x, y, z, System.currentTimeMillis(), isOutside, target, null);
            this.addMarker(uuid, marker);
            LabyModCore.getMinecraft().playSound(MarkerManager.SOUND_MARKER_NOTIFY, 2.0f);
        }
        else {
            prevMarker.x = x;
            prevMarker.y = y;
            prevMarker.z = z;
            prevMarker.timestamp = System.currentTimeMillis();
            prevMarker.isOutside = isOutside;
            prevMarker.target = target;
            prevMarker.cachedEntity = null;
        }
    }
    
    public void setClientMarker() {
        try {
            final bud player = LabyModCore.getMinecraft().getPlayer();
            final UUID uuid = LabyMod.getInstance().getPlayerUUID();
            final amu world = (amu)LabyModCore.getMinecraft().getWorld();
            final Vector3d targetPoint = this.getTargetPoint(world, LabyModCore.getMinecraft().getLookVector(player), LabyModCore.getMinecraft().getPositionEyes(player, 0.0f), 100, 2.0);
            if (targetPoint == null) {
                return;
            }
            final Vector3d targetLocation = this.getHighestPointAt(world, targetPoint, 100);
            final boolean isOutside = this.isOutside(world, targetLocation, 20);
            UUID target = this.getTargetEntity(world, targetLocation, 2);
            if (target != null && target.equals(uuid)) {
                target = null;
            }
            final Marker marker = new Marker((int)Math.floor(targetLocation.x), (int)targetLocation.y, (int)Math.floor(targetLocation.z), System.currentTimeMillis(), isOutside, target, null);
            final List<UUID> receivers = new ArrayList<UUID>();
            for (final aed playerEntity : world.i) {
                final UUID playerUUID = playerEntity.da().getId();
                if (LabyMod.getInstance().getLabyConnect().hasFriendOnline(playerUUID)) {
                    receivers.add(playerUUID);
                }
            }
            final UUID[] uuidArray = new UUID[receivers.size()];
            receivers.toArray(uuidArray);
            this.addMarker(uuid, marker);
            LabyModCore.getMinecraft().playSound(MarkerManager.SOUND_PLACE_MARKER, 2.0f);
            if (uuidArray.length != 0 && this.enabled) {
                final ByteBuf buffer = Unpooled.buffer();
                buffer.writeInt(0);
                buffer.writeInt(marker.x);
                buffer.writeInt(marker.y);
                buffer.writeInt(marker.z);
                buffer.writeBoolean(marker.isOutside);
                buffer.writeBoolean(target != null);
                if (target != null) {
                    buffer.writeLong(target.getMostSignificantBits());
                    buffer.writeLong(target.getLeastSignificantBits());
                }
                LabyMod.getInstance().getLabyModAPI().sendAddonDevelopmentPacket(new PacketAddonDevelopment(uuid, uuidArray, "labymod:marker", buffer.array()));
                buffer.release();
            }
            if (this.sendMarkers) {
                final JsonObject object = new JsonObject();
                object.addProperty("version", (Number)0);
                object.addProperty("x", (Number)marker.x);
                object.addProperty("y", (Number)marker.y);
                object.addProperty("z", (Number)marker.z);
                object.addProperty("large", Boolean.valueOf(marker.isOutside));
                if (marker.target != null) {
                    object.addProperty("target", marker.target.toString());
                }
                LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("marker", (JsonElement)object);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private UUID getTargetEntity(final amu world, final Vector3d location, final int range) {
        if (!Permissions.isAllowed(Permissions.Permission.ENTITY_MARKER)) {
            return null;
        }
        vg nearest = null;
        final double nearestDistance = Double.MAX_VALUE;
        for (final vg allEntity : world.e) {
            final double distance = allEntity.d(location.x, location.y, location.z);
            if (distance < range * range && distance < nearestDistance && allEntity != LabyModCore.getMinecraft().getPlayer()) {
                nearest = allEntity;
            }
        }
        return (nearest == null) ? null : nearest.bm();
    }
    
    private boolean isOutside(final amu world, final Vector3d source, final int distance) {
        Vector3d target = source;
        for (int i = 0; i < distance; ++i) {
            final awt state = LabyModCore.getMinecraft().getBlockState(world, target.x, target.y + 0.5, target.z);
            if (LabyModCore.getMinecraft().isSolid(state)) {
                return false;
            }
            target = target.add(0.0, 1.0, 0.0);
        }
        return true;
    }
    
    private Vector3d getTargetPoint(final amu world, final Vector3d direction, final Vector3d source, final int distance, final double stepsPerBlock) {
        final Vector3d lookVector = direction.scale(1.0 / stepsPerBlock);
        Vector3d target = source;
        for (int max = (int)(distance * stepsPerBlock), i = 0; i < max * 2; ++i) {
            final awt state = LabyModCore.getMinecraft().getBlockState(world, target.x, target.y, target.z);
            if (LabyModCore.getMinecraft().isSolid(state)) {
                break;
            }
            if (this.getTargetEntity(world, target, 2) != null && i > 5) {
                break;
            }
            target = target.add(lookVector);
            if (i + 1 == max) {
                return null;
            }
        }
        return target;
    }
    
    private Vector3d getHighestPointAt(final amu world, final Vector3d source, final int distance) {
        Vector3d target = source;
        for (int i = 0; i < distance; ++i) {
            final awt state = LabyModCore.getMinecraft().getBlockState(world, target.x, target.y + 0.5, target.z);
            if (!LabyModCore.getMinecraft().isSolid(state)) {
                break;
            }
            target = target.add(0.0, 1.0, 0.0);
        }
        return target;
    }
    
    public void addMarker(final UUID uuid, final Marker marker) {
        final Map<UUID, Marker> markers = new HashMap<UUID, Marker>();
        final Map<UUID, Marker> map;
        this.markers.forEach((uuidEntry, markerEntry) -> {
            if (markerEntry.isVisible()) {
                map.put(uuidEntry, markerEntry);
            }
            return;
        });
        markers.put(uuid, marker);
        this.markers = markers;
    }
    
    public void render(final float partialTicks) {
        if (!LabyMod.getSettings().marker) {
            return;
        }
        final vg entity = bib.z().aa();
        final double camX = entity.M + (entity.p - entity.M) * partialTicks;
        final double camY = entity.N + (entity.q - entity.N) * partialTicks;
        final double camZ = entity.O + (entity.r - entity.O) * partialTicks;
        final double camX2;
        final double camY2;
        final double camZ2;
        this.markers.forEach((uuid, marker) -> {
            if (marker.isVisible()) {
                marker.render(uuid, camX2, camY2, camZ2, partialTicks);
            }
        });
    }
    
    public void renderOverlay(final float partialTicks) {
        if (LabyMod.getSettings().marker) {
            this.markers.forEach((uuid, marker) -> {
                if (marker.isVisible()) {
                    marker.renderOverlay(uuid, partialTicks);
                }
            });
        }
    }
    
    public static double wrapAngleTo180_double(double value) {
        value %= 360.0;
        if (value >= 180.0) {
            value -= 360.0;
        }
        if (value < -180.0) {
            value += 360.0;
        }
        return value;
    }
    
    static {
        SOUND_PLACE_MARKER = new nf("note.snare");
        SOUND_MARKER_NOTIFY = new nf("note.pling");
    }
    
    public static class Marker
    {
        public int x;
        public int y;
        public int z;
        public long timestamp;
        public boolean isOutside;
        public UUID target;
        public vg cachedEntity;
        
        public boolean isVisible() {
            return this.timestamp + LabyMod.getSettings().markerDuration * 1000 > System.currentTimeMillis();
        }
        
        public void render(final UUID uuid, final double camX, final double camY, final double camZ, final float partialTicks) {
            final double yShift = this.y - Math.round((float)this.y);
            double x = this.x - camX + 0.5;
            double y = this.y - camY - yShift + 1.0;
            double z = this.z - camZ + 0.5;
            if (this.cachedEntity == null && this.target != null) {
                final bsb world = LabyModCore.getMinecraft().getWorld();
                if (world != null) {
                    for (final vg allEntity : world.e) {
                        if (allEntity.bm().equals(this.target)) {
                            this.cachedEntity = allEntity;
                            break;
                        }
                    }
                }
            }
            if (this.cachedEntity != null && this.target != null) {
                final double playerX = this.cachedEntity.m + (this.cachedEntity.p - this.cachedEntity.m) * partialTicks;
                final double playerY = this.cachedEntity.n + (this.cachedEntity.q - this.cachedEntity.n) * partialTicks;
                final double playerZ = this.cachedEntity.o + (this.cachedEntity.r - this.cachedEntity.o) * partialTicks;
                x = playerX - camX;
                y = playerY - camY + this.cachedEntity.H;
                z = playerZ - camZ;
            }
            final long markerDuration = LabyMod.getSettings().markerDuration * 1000;
            final long timeRemaining = markerDuration - (System.currentTimeMillis() - this.timestamp);
            final long fadeInDuration = 300L;
            final double distance = x * x + y * y + z * z;
            final double fadeOut = Math.min(1.0, 1.0 / markerDuration * (timeRemaining * timeRemaining));
            final double fadeIn = Math.cos(Math.min(fadeInDuration, System.currentTimeMillis() - this.timestamp) / (float)fadeInDuration * 3.141592653589793 / 1.2999999523162842 - 2.0943951023931953 + 0.5);
            final double transition = fadeIn * fadeOut;
            final double scale = Math.max(0.5, Math.min(this.isOutside ? 4.0 : 2.0, distance / 100.0)) * transition;
            final double offsetY = (1.0 + Math.max(0.0, Math.min(this.isOutside ? 10.0 : 2.0, distance / 100.0))) * transition;
            final double lineHeight = offsetY - scale + (this.isOutside ? (scale * 1.5) : (scale / 2.0));
            bus.G();
            bus.b(x, y, z);
            bus.r();
            bus.d(1.0f, 1.0f, 1.0f);
            bus.G();
            bus.b(0.0, lineHeight, 0.0);
            float yaw = bib.z().ac().e;
            final float pitch = LabyModCore.getMinecraft().getRenderViewEntity().w;
            bus.b(-yaw, 0.0f, 1.0f, 0.0f);
            bus.b(pitch, 1.0f, 0.0f, 0.0f);
            bus.b(180.0f, 0.0f, 0.0f, 1.0f);
            final nf texture = LabyMod.getInstance().getDrawUtils().getPlayerSkinTextureCache().getSkinTexture(uuid);
            if (texture != null) {
                final double frame = scale / 15.0;
                bus.d(0.6f, 0.0f, 0.0f);
                bib.z().N().a(ModTextures.VOID);
                LabyMod.getInstance().getDrawUtils().drawTexture(-scale / 2.0 - frame, -scale / 2.0 - frame, 32.0, 32.0, 32.0, 32.0, scale + frame * 2.0, scale + frame * 2.0, 1.1f);
                bus.b(0.0, 0.0, -0.01);
                bib.z().N().a(texture);
                LabyMod.getInstance().getDrawUtils().drawTexture(-scale / 2.0, -scale / 2.0, 32.0, 32.0, 32.0, 32.0, scale, scale);
                bus.b(0.0, 0.0, -0.01);
                LabyMod.getInstance().getDrawUtils().drawTexture(-scale / 2.0, -scale / 2.0, 160.0, 32.0, 32.0, 32.0, scale, scale);
            }
            bus.H();
            bus.G();
            yaw = LabyModCore.getMinecraft().getRenderViewEntity().v;
            bus.b(-yaw, 0.0f, 1.0f, 0.0f);
            bus.b(180.0f, 0.0f, 0.0f, 1.0f);
            bus.b(0.0, 0.0, 0.01);
            final double lineWidth = 0.2 * scale * 2.0;
            bib.z().N().a(ModTextures.MISC_MARKER_CIRCLE);
            LabyMod.getInstance().getDrawUtils().drawTexture(-lineWidth / 2.0, -lineHeight + scale / 2.0, 128.0, 0.0, 128.0, 256.0, lineWidth, lineHeight - scale / 2.0);
            bus.H();
            bus.G();
            bus.b(0.0, 0.01, 0.0);
            bus.b(90.0f, 1.0f, 0.0f, 0.0f);
            bib.z().N().a(ModTextures.MISC_MARKER_CIRCLE);
            LabyMod.getInstance().getDrawUtils().drawTexture(-0.5 * transition, -0.5 * transition, 0.0, 0.0, 128.0, 256.0, transition, transition);
            bus.H();
            bus.q();
            bus.e();
            bus.H();
        }
        
        public void renderOverlay(final UUID uuid, final float partialTicks) {
            final aed player = (aed)LabyModCore.getMinecraft().getPlayer();
            if (player == null) {
                return;
            }
            final double playerX = player.m + (player.p - player.m) * partialTicks;
            final double playerZ = player.o + (player.r - player.o) * partialTicks;
            final double xDiff = this.x - playerX + 0.5;
            final double zDiff = this.z - playerZ + 0.5;
            final double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
            double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
            if (zDiff < 0.0) {
                yaw += Math.abs(180.0 - yaw) * 2.0;
            }
            yaw -= 90.0;
            if (yaw < 0.0) {
                yaw += 360.0;
            }
            bus.G();
            bus.j();
            bus.d(1.0f, 1.0f, 1.0f);
            final double clientYaw = MarkerManager.wrapAngleTo180_double(player.v);
            final double yawOffset = MarkerManager.wrapAngleTo180_double(clientYaw - yaw + 180.0);
            final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
            final int x = (int)(draw.getWidth() / 2 + draw.getWidth() / 180 * yawOffset);
            draw.drawPlayerHead(uuid, x, draw.getHeight() - 20, 16);
            float pause = (float)(Math.sin(System.currentTimeMillis() / 500.0) * 3.0);
            if (pause < 0.0f) {
                pause = 0.0f;
            }
            final float bounce = (float)Math.abs(Math.cos(System.currentTimeMillis() / 100.0) * -pause);
            bib.z().N().a(ModTextures.BUTTON_EXCLAMATION);
            draw.drawTexture((double)(x + 20 - 7), (double)(draw.getHeight() - 20 - bounce), 0.0, 0.0, 120.0, 255.0, 8.0, 16.0, 1.1f);
            bus.e();
            bus.m();
            bus.H();
        }
        
        @ConstructorProperties({ "x", "y", "z", "timestamp", "isOutside", "target", "cachedEntity" })
        public Marker(final int x, final int y, final int z, final long timestamp, final boolean isOutside, final UUID target, final vg cachedEntity) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.timestamp = timestamp;
            this.isOutside = isOutside;
            this.target = target;
            this.cachedEntity = cachedEntity;
        }
    }
    
    public static class Vector3d
    {
        public double x;
        public double y;
        public double z;
        
        public Vector3d(final double x, final double y, final double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Vector3d add(final Vector3d vector) {
            return new Vector3d(this.x + vector.x, this.y + vector.y, this.z + vector.z);
        }
        
        public Vector3d add(final double x, final double y, final double z) {
            return new Vector3d(this.x + x, this.y + y, this.z + z);
        }
        
        public Vector3d scale(final double factor) {
            return new Vector3d(this.x * factor, this.y * factor, this.z * factor);
        }
    }
}
