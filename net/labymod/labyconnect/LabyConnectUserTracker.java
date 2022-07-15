//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import net.labymod.core.*;
import java.util.*;
import net.labymod.labyconnect.packets.*;

public class LabyConnectUserTracker
{
    private static final long LIST_UPDATE_INTERVAL = 10000L;
    private static final long ENTITY_UPDATE_INTERVAL = 3000L;
    private final LabyConnect labyConnect;
    private final Map<PacketUserTracker.EnumTrackingAction, List<UUID>> listBuffer;
    private final Map<PacketUserTracker.EnumTrackingAction, List<UUID>> entityBuffer;
    private long nextTimeDrainList;
    private long nextTimeDrainEntities;
    private boolean empty;
    
    public LabyConnectUserTracker(final LabyConnect labyConnect) {
        this.listBuffer = new HashMap<PacketUserTracker.EnumTrackingAction, List<UUID>>();
        this.entityBuffer = new HashMap<PacketUserTracker.EnumTrackingAction, List<UUID>>();
        this.nextTimeDrainList = -1L;
        this.nextTimeDrainEntities = -1L;
        this.empty = true;
        this.labyConnect = labyConnect;
        for (final PacketUserTracker.EnumTrackingAction action : PacketUserTracker.EnumTrackingAction.values()) {
            this.listBuffer.put(action, new ArrayList<UUID>());
            this.entityBuffer.put(action, new ArrayList<UUID>());
        }
    }
    
    public void onLoadWorld() {
        this.clearBuffers();
    }
    
    public void onDisconnectServer() {
        this.clearBuffers();
    }
    
    public void onLabyConnectConnect() {
        this.clearBuffers();
        final brz connection = LabyModCore.getMinecraft().getConnection();
        if (connection != null) {
            for (final bsc playerInfo : connection.d()) {
                if (this.isPlayerInfoValid(playerInfo)) {
                    this.listBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).add(playerInfo.a().getId());
                    this.empty = false;
                }
            }
        }
        final bsb world = LabyModCore.getMinecraft().getWorld();
        if (world != null) {
            for (final aed player : world.i) {
                this.entityBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).add(player.bm());
                this.empty = false;
            }
        }
    }
    
    public void onGameTick() {
        if (!this.labyConnect.isOnline()) {
            return;
        }
        if (this.nextTimeDrainList < System.currentTimeMillis() || this.listBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).size() > 10) {
            this.nextTimeDrainList = System.currentTimeMillis() + 10000L;
            this.drain(PacketUserTracker.EnumTrackingChannel.LIST, this.listBuffer);
        }
        if (this.nextTimeDrainEntities < System.currentTimeMillis() || this.entityBuffer.get(PacketUserTracker.EnumTrackingAction.ADD).size() > 10) {
            this.nextTimeDrainEntities = System.currentTimeMillis() + 3000L;
            this.drain(PacketUserTracker.EnumTrackingChannel.ENTITIES, this.entityBuffer);
        }
    }
    
    private void drain(final PacketUserTracker.EnumTrackingChannel channel, final Map<PacketUserTracker.EnumTrackingAction, List<UUID>> map) {
        for (final Map.Entry<PacketUserTracker.EnumTrackingAction, List<UUID>> entry : map.entrySet()) {
            final List<UUID> buffer = entry.getValue();
            if (buffer.isEmpty()) {
                continue;
            }
            this.labyConnect.getClientConnection().sendPacket((Packet)new PacketUserTracker(channel, entry.getKey(), buffer.toArray(new UUID[0])));
            buffer.clear();
        }
    }
    
    public void onPlayerInfoAdd(final bsc playerInfo) {
        if (this.isPlayerInfoValid(playerInfo)) {
            this.update(this.listBuffer, playerInfo.a().getId(), PacketUserTracker.EnumTrackingAction.REMOVE, PacketUserTracker.EnumTrackingAction.ADD);
        }
    }
    
    public void onPlayerInfoRemove(final UUID uniqueId) {
        if (this.isUniqueIdValid(uniqueId)) {
            this.update(this.listBuffer, uniqueId, PacketUserTracker.EnumTrackingAction.ADD, PacketUserTracker.EnumTrackingAction.REMOVE);
        }
    }
    
    public void onVisiblePlayer(final UUID uniqueId) {
        if (this.isUniqueIdValid(uniqueId)) {
            this.update(this.entityBuffer, uniqueId, PacketUserTracker.EnumTrackingAction.REMOVE, PacketUserTracker.EnumTrackingAction.ADD);
        }
    }
    
    public void onEntityDestruct(final UUID uniqueId) {
        if (this.isUniqueIdValid(uniqueId)) {
            this.update(this.entityBuffer, uniqueId, PacketUserTracker.EnumTrackingAction.ADD, PacketUserTracker.EnumTrackingAction.REMOVE);
        }
    }
    
    private void update(final Map<PacketUserTracker.EnumTrackingAction, List<UUID>> buffers, final UUID uniqueId, final PacketUserTracker.EnumTrackingAction cleanTarget, final PacketUserTracker.EnumTrackingAction actionTarget) {
        if (!this.labyConnect.isOnline()) {
            return;
        }
        final List<UUID> buffer = buffers.get(cleanTarget);
        if (buffer.contains(uniqueId)) {
            buffer.remove(uniqueId);
        }
        else {
            buffers.get(actionTarget).add(uniqueId);
            this.empty = false;
        }
    }
    
    private void clearBuffers() {
        for (final List<UUID> buffer : this.listBuffer.values()) {
            buffer.clear();
        }
        for (final List<UUID> buffer : this.entityBuffer.values()) {
            buffer.clear();
        }
        if (this.labyConnect.isOnline() && !this.empty) {
            this.labyConnect.getClientConnection().sendPacket((Packet)new PacketUserTracker(PacketUserTracker.EnumTrackingChannel.LIST, PacketUserTracker.EnumTrackingAction.CLEAR));
            this.labyConnect.getClientConnection().sendPacket((Packet)new PacketUserTracker(PacketUserTracker.EnumTrackingChannel.ENTITIES, PacketUserTracker.EnumTrackingAction.CLEAR));
        }
        this.empty = true;
    }
    
    private boolean isPlayerInfoValid(final bsc playerInfo) {
        final UUID uuid = playerInfo.a().getId();
        return this.isUniqueIdValid(uuid);
    }
    
    private boolean isUniqueIdValid(final UUID uniqueId) {
        return uniqueId.getMostSignificantBits() != 0L && uniqueId.getLeastSignificantBits() != 0L && (uniqueId.getMostSignificantBits() >> 12 & 0xFL) == 0x4L;
    }
}
