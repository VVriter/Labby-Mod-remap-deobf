//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api;

import net.labymod.utils.*;
import net.labymod.labyconnect.packets.*;
import net.labymod.api.events.*;
import net.labymod.core.*;
import com.google.gson.*;
import net.labymod.user.*;
import java.util.*;
import net.labymod.user.util.*;

public class EventManager
{
    private Set<MessageModifyChatEvent> messageModifyChat;
    private Set<MessageReceiveEvent> messageReceive;
    private Set<MessageSendEvent> messageSend;
    private Set<TabListEvent> tabList;
    private Set<PluginMessageEvent> pluginMessage;
    private Set<ServerMessageEvent> serverMessage;
    private Set<RenderEntityEvent> renderEntity;
    private Set<Consumer<ServerData>> joinServer;
    private Set<Consumer<ServerData>> quitServer;
    private Set<Consumer<vg>> attackEntity;
    private Set<Consumer<PacketAddonMessage>> addonMessage;
    private Set<Consumer<Object>> incomingPackets;
    private Set<Consumer<PacketAddonDevelopment>> addonDevelopmentPackets;
    private Set<UserMenuActionEvent> createUserMenuActions;
    private Set<MouseInputEvent> mouseInput;
    private Set<RenderIngameOverlayEvent> renderIngameOverlay;
    private Set<Runnable> shutdownHook;
    
    public EventManager() {
        this.messageModifyChat = new HashSet<MessageModifyChatEvent>();
        this.messageReceive = new HashSet<MessageReceiveEvent>();
        this.messageSend = new HashSet<MessageSendEvent>();
        this.tabList = new HashSet<TabListEvent>();
        this.pluginMessage = new HashSet<PluginMessageEvent>();
        this.serverMessage = new HashSet<ServerMessageEvent>();
        this.renderEntity = new HashSet<RenderEntityEvent>();
        this.joinServer = new HashSet<Consumer<ServerData>>();
        this.quitServer = new HashSet<Consumer<ServerData>>();
        this.attackEntity = new HashSet<Consumer<vg>>();
        this.addonMessage = new HashSet<Consumer<PacketAddonMessage>>();
        this.incomingPackets = new HashSet<Consumer<Object>>();
        this.addonDevelopmentPackets = new HashSet<Consumer<PacketAddonDevelopment>>();
        this.createUserMenuActions = new HashSet<UserMenuActionEvent>();
        this.mouseInput = new HashSet<MouseInputEvent>();
        this.renderIngameOverlay = new HashSet<RenderIngameOverlayEvent>();
        this.shutdownHook = new HashSet<Runnable>();
    }
    
    public void callAllHeader(final ChatComponent tabListHeader) {
        if (tabListHeader == null || tabListHeader == null) {
            return;
        }
        for (final TabListEvent tabListUpdateListener : this.tabList) {
            tabListUpdateListener.onUpdate(TabListEvent.Type.HEADER, tabListHeader.getFormattedText(), tabListHeader.getUnformattedText());
        }
    }
    
    public void callAllFooter(final ChatComponent tabListFooter) {
        if (tabListFooter == null || tabListFooter == null) {
            return;
        }
        for (final TabListEvent tabListUpdateListener : this.tabList) {
            tabListUpdateListener.onUpdate(TabListEvent.Type.FOOTER, tabListFooter.getFormattedText(), tabListFooter.getUnformattedText());
        }
    }
    
    public void callAllPluginMessage(final String channelName, final gy packetBuffer) {
        for (final PluginMessageEvent tabListUpdateListener : this.pluginMessage) {
            tabListUpdateListener.receiveMessage(channelName, packetBuffer);
        }
    }
    
    public void callShutdownHook() {
        for (final Runnable runnable : this.shutdownHook) {
            runnable.run();
        }
    }
    
    public void callRenderEntity(final vg entity, final double x, final double y, final double z, final float partialTicks) {
        for (final RenderEntityEvent renderEntityEvent : this.renderEntity) {
            renderEntityEvent.onRender(entity, x, y, z, partialTicks);
        }
    }
    
    public void callJoinServer(final ServerData serverData) {
        for (final Consumer<ServerData> consumer : this.joinServer) {
            consumer.accept(serverData);
        }
    }
    
    public void callQuitServer(final ServerData lastServerData) {
        for (final Consumer<ServerData> consumer : this.quitServer) {
            consumer.accept(lastServerData);
        }
    }
    
    public void callAttackEntity(final vg entity) {
        for (final Consumer<vg> consumer : this.attackEntity) {
            consumer.accept(entity);
        }
    }
    
    public void callServerMessage(final String messageKey, final JsonElement serverMessage) {
        for (final ServerMessageEvent serverMessageListener : this.serverMessage) {
            try {
                serverMessageListener.onServerMessage(messageKey, serverMessage);
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
    
    public void callAddonMessage(final PacketAddonMessage packet) {
        for (final Consumer<PacketAddonMessage> consumer : this.addonMessage) {
            consumer.accept(packet);
        }
    }
    
    public void callincomingPacket(final Object packet) {
        for (final Consumer<Object> consumer : this.incomingPackets) {
            consumer.accept(packet);
        }
    }
    
    public void callAddonDevelopmentPacket(final PacketAddonDevelopment packet) {
        for (final Consumer<PacketAddonDevelopment> consumer : this.addonDevelopmentPackets) {
            consumer.accept(packet);
        }
    }
    
    public void callCreateUserMenuActions(final User user, final aed entityPlayer, final bsc networkPlayerInfo, final List<UserActionEntry> entries) {
        for (final UserMenuActionEvent event : this.createUserMenuActions) {
            try {
                event.createActions(user, entityPlayer, networkPlayerInfo, entries);
            }
            catch (Exception error) {
                error.printStackTrace();
            }
        }
    }
    
    public void callMouseInput(final int mouseButton) {
        for (final MouseInputEvent consumer : this.mouseInput) {
            consumer.receiveMouseInput(mouseButton);
        }
    }
    
    public void callRenderIngameOverlay(final float partialTicks) {
        for (final RenderIngameOverlayEvent event : this.renderIngameOverlay) {
            bus.G();
            bus.e();
            bus.m();
            event.onRender(partialTicks);
            bus.e();
            bus.m();
            bus.H();
        }
    }
    
    public void register(final TabListEvent listener) {
        this.tabList.add(listener);
    }
    
    public void register(final MessageModifyChatEvent listener) {
        this.messageModifyChat.add(listener);
    }
    
    public void register(final MessageReceiveEvent listener) {
        this.messageReceive.add(listener);
    }
    
    public void register(final MessageSendEvent listener) {
        this.messageSend.add(listener);
    }
    
    public void register(final PluginMessageEvent listener) {
        this.pluginMessage.add(listener);
    }
    
    public void register(final RenderEntityEvent listener) {
        this.renderEntity.add(listener);
    }
    
    public void register(final ServerMessageEvent listener) {
        this.serverMessage.add(listener);
    }
    
    public void register(final RenderIngameOverlayEvent listener) {
        this.renderIngameOverlay.add(listener);
    }
    
    public void registerOnJoin(final Consumer<ServerData> listener) {
        this.joinServer.add(listener);
    }
    
    public void registerOnQuit(final Consumer<ServerData> listener) {
        this.quitServer.add(listener);
    }
    
    public void registerOnAttack(final Consumer<vg> listener) {
        this.attackEntity.add(listener);
    }
    
    public void registerAddonMessage(final Consumer<PacketAddonMessage> listener) {
        this.addonMessage.add(listener);
    }
    
    public void registerOnIncomingPacket(final Consumer<Object> listener) {
        this.incomingPackets.add(listener);
    }
    
    public void registerOnAddonDevelopmentPacket(final Consumer<PacketAddonDevelopment> listener) {
        this.addonDevelopmentPackets.add(listener);
    }
    
    public void register(final UserMenuActionEvent listener) {
        this.createUserMenuActions.add(listener);
    }
    
    public void register(final MouseInputEvent listener) {
        this.mouseInput.add(listener);
    }
    
    public void registerShutdownHook(final Runnable runnable) {
        this.shutdownHook.add(runnable);
    }
    
    public Set<MessageModifyChatEvent> getMessageModifyChat() {
        return this.messageModifyChat;
    }
    
    public Set<MessageReceiveEvent> getMessageReceive() {
        return this.messageReceive;
    }
    
    public Set<MessageSendEvent> getMessageSend() {
        return this.messageSend;
    }
}
