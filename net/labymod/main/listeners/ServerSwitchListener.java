//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.gui.*;
import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import com.google.gson.*;
import java.util.*;
import net.labymod.support.util.*;

public class ServerSwitchListener implements ServerMessageEvent, Consumer<ServerData>, GuiSwitchServer.Result, MessageSendEvent
{
    private LabyMod labymod;
    private String destinationAddressToTrust;
    private boolean sessionTrusted;
    
    public ServerSwitchListener(final LabyMod labymod) {
        this.sessionTrusted = false;
        this.labymod = labymod;
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("server_switch")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            if (obj.has("title") && obj.has("address")) {
                final String title = obj.get("title").getAsString();
                final String address = obj.get("address").getAsString();
                final boolean preview = obj.has("preview") && obj.get("preview").getAsBoolean();
                if (this.labymod.getCurrentServerData() != null && !LabyMod.isForge()) {
                    if (this.sessionTrusted) {
                        bib.z().a((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                ServerSwitchListener.this.notifyServer(address, true, true);
                                ServerSwitchListener.this.labymod.switchServer(address, true);
                            }
                        });
                        return;
                    }
                    final List<String> list = Arrays.asList(LabyMod.getSettings().trustedServers);
                    if (list.contains(ModUtils.getProfileNameByIp(this.labymod.getCurrentServerData().getIp()))) {
                        bib.z().a((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                ServerSwitchListener.this.notifyServer(address, true, true);
                                ServerSwitchListener.this.labymod.switchServer(address, true);
                            }
                        });
                        return;
                    }
                }
                bib.z().a((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        bib.z().a((blk)new GuiSwitchServer(title, address, preview, (GuiSwitchServer.Result)ServerSwitchListener.this));
                    }
                });
            }
        }
    }
    
    public void notifyServer(final String address, final boolean accepted, final boolean trusted) {
        if (accepted) {
            this.destinationAddressToTrust = address;
        }
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("address", address);
        jsonObject.addProperty("accepted", Boolean.valueOf(accepted));
        this.labymod.getLabyModAPI().sendJsonMessageToServer("server_switch", (JsonElement)jsonObject);
    }
    
    public void accept(final ServerData accepted) {
        if (accepted == null) {
            return;
        }
        if (this.destinationAddressToTrust != null && accepted.getIp().equalsIgnoreCase(this.destinationAddressToTrust)) {
            this.sessionTrusted = true;
        }
        else {
            this.sessionTrusted = false;
            this.destinationAddressToTrust = null;
        }
    }
    
    public boolean onSend(final String msg) {
        if (Debug.isActive() && msg.startsWith("/connect") && msg.contains(" ")) {
            final String ip = msg.split(" ")[1];
            JsonObject object;
            final String s;
            new Thread(() -> {
                try {
                    Thread.sleep(10L);
                    bib.z().a(() -> {
                        object = new JsonObject();
                        object.addProperty("title", "LabyMod Server Switcher");
                        object.addProperty("address", s);
                        object.addProperty("preview", Boolean.valueOf(true));
                        this.onServerMessage("server_switch", (JsonElement)object);
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }).start();
            return true;
        }
        return false;
    }
}
