//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.support.util.*;
import java.util.*;

public class Protocol
{
    private static final Protocol INSTANCE;
    private Map<Class<? extends Packet>, EnumConnectionState> protocol;
    private Map<Integer, Class<? extends Packet>> packets;
    
    public static Protocol getProtocol() {
        return Protocol.INSTANCE;
    }
    
    public Protocol() {
        this.protocol = new HashMap<Class<? extends Packet>, EnumConnectionState>();
        this.packets = new HashMap<Integer, Class<? extends Packet>>();
        this.register(0, (Class<? extends Packet>)PacketHelloPing.class, EnumConnectionState.HELLO);
        this.register(1, (Class<? extends Packet>)PacketHelloPong.class, EnumConnectionState.HELLO);
        this.register(2, (Class<? extends Packet>)PacketLoginStart.class, EnumConnectionState.LOGIN);
        this.register(3, (Class<? extends Packet>)PacketLoginData.class, EnumConnectionState.LOGIN);
        this.register(4, (Class<? extends Packet>)PacketLoginFriend.class, EnumConnectionState.LOGIN);
        this.register(5, (Class<? extends Packet>)PacketLoginRequest.class, EnumConnectionState.LOGIN);
        this.register(6, (Class<? extends Packet>)PacketLoginOptions.class, EnumConnectionState.LOGIN);
        this.register(7, (Class<? extends Packet>)PacketLoginComplete.class, EnumConnectionState.LOGIN);
        this.register(8, (Class<? extends Packet>)PacketLoginTime.class, EnumConnectionState.LOGIN);
        this.register(9, (Class<? extends Packet>)PacketLoginVersion.class, EnumConnectionState.LOGIN);
        this.register(10, (Class<? extends Packet>)PacketEncryptionRequest.class, EnumConnectionState.LOGIN);
        this.register(11, (Class<? extends Packet>)PacketEncryptionResponse.class, EnumConnectionState.LOGIN);
        this.register(14, (Class<? extends Packet>)PacketPlayPlayerOnline.class, EnumConnectionState.PLAY);
        this.register(16, (Class<? extends Packet>)PacketPlayRequestAddFriend.class, EnumConnectionState.PLAY);
        this.register(17, (Class<? extends Packet>)PacketPlayRequestAddFriendResponse.class, EnumConnectionState.PLAY);
        this.register(18, (Class<? extends Packet>)PacketPlayRequestRemove.class, EnumConnectionState.PLAY);
        this.register(19, (Class<? extends Packet>)PacketPlayDenyFriendRequest.class, EnumConnectionState.PLAY);
        this.register(20, (Class<? extends Packet>)PacketPlayFriendRemove.class, EnumConnectionState.PLAY);
        this.register(21, (Class<? extends Packet>)PacketPlayChangeOptions.class, EnumConnectionState.PLAY);
        this.register(22, (Class<? extends Packet>)PacketPlayServerStatus.class, EnumConnectionState.PLAY);
        this.register(23, (Class<? extends Packet>)PacketPlayFriendStatus.class, EnumConnectionState.PLAY);
        this.register(24, (Class<? extends Packet>)PacketPlayFriendPlayingOn.class, EnumConnectionState.PLAY);
        this.register(25, (Class<? extends Packet>)PacketPlayTyping.class, EnumConnectionState.PLAY);
        this.register(26, (Class<? extends Packet>)PacketMojangStatus.class, EnumConnectionState.PLAY);
        this.register(27, (Class<? extends Packet>)PacketActionPlay.class, EnumConnectionState.PLAY);
        this.register(28, (Class<? extends Packet>)PacketActionPlayResponse.class, EnumConnectionState.PLAY);
        this.register(29, (Class<? extends Packet>)PacketActionRequest.class, EnumConnectionState.PLAY);
        this.register(30, (Class<? extends Packet>)PacketActionRequestResponse.class, EnumConnectionState.PLAY);
        this.register(31, (Class<? extends Packet>)PacketUpdateCosmetics.class, EnumConnectionState.PLAY);
        this.register(32, (Class<? extends Packet>)PacketAddonMessage.class, EnumConnectionState.PLAY);
        this.register(33, (Class<? extends Packet>)PacketUserBadge.class, EnumConnectionState.PLAY);
        this.register(34, (Class<? extends Packet>)PacketAddonDevelopment.class, EnumConnectionState.PLAY);
        this.register(60, (Class<? extends Packet>)PacketDisconnect.class, EnumConnectionState.ALL);
        this.register(61, (Class<? extends Packet>)PacketKick.class, EnumConnectionState.ALL);
        this.register(62, (Class<? extends Packet>)PacketPing.class, EnumConnectionState.ALL);
        this.register(63, (Class<? extends Packet>)PacketPong.class, EnumConnectionState.ALL);
        this.register(64, (Class<? extends Packet>)PacketServerMessage.class, EnumConnectionState.ALL);
        this.register(65, (Class<? extends Packet>)PacketMessage.class, EnumConnectionState.ALL);
        this.register(66, (Class<? extends Packet>)PacketNotAllowed.class, EnumConnectionState.ALL);
        this.register(67, (Class<? extends Packet>)PacketChatVisibilityChange.class, EnumConnectionState.ALL);
        this.register(68, (Class<? extends Packet>)PacketPlayServerStatusUpdate.class, EnumConnectionState.PLAY);
        this.register(69, (Class<? extends Packet>)PacketUserTracker.class, EnumConnectionState.PLAY);
        this.register(70, (Class<? extends Packet>)PacketActionBroadcast.class, EnumConnectionState.PLAY);
    }
    
    public Map<Integer, Class<? extends Packet>> getPackets() {
        return this.packets;
    }
    
    private final void register(final int id, final Class<? extends Packet> clazz, final EnumConnectionState state) {
        try {
            clazz.newInstance();
            this.packets.put(id, clazz);
            this.protocol.put(clazz, state);
        }
        catch (Exception e) {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Class " + clazz.getSimpleName() + " does not contain a default Constructor, this might break the game :/");
        }
    }
    
    public Packet getPacket(final int id) throws IllegalAccessException, InstantiationException {
        if (!this.packets.containsKey(id)) {
            throw new RuntimeException("Packet with id " + id + " is not registered.");
        }
        return (Packet)this.packets.get(id).newInstance();
    }
    
    public int getPacketId(final Packet packet) {
        for (final Map.Entry<Integer, Class<? extends Packet>> entry : this.packets.entrySet()) {
            final Class<? extends Packet> clazz = entry.getValue();
            if (clazz.isInstance(packet)) {
                return entry.getKey();
            }
        }
        throw new RuntimeException("Packet " + packet + " is not registered.");
    }
    
    static {
        INSTANCE = new Protocol();
    }
}
