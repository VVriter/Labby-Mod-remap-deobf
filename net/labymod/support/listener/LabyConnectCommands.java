//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.listener;

import net.labymod.api.events.*;
import net.labymod.labyconnect.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import com.google.gson.*;
import net.labymod.labyconnect.packets.*;
import java.beans.*;

public class LabyConnectCommands implements MessageSendEvent
{
    private ClientConnection connection;
    
    public boolean onSend(final String message) {
        final String lowercaseMessage = message.toLowerCase();
        try {
            if (lowercaseMessage.startsWith("/labymodchatdebug")) {
                if (!lowercaseMessage.contains(" ")) {
                    LabyMod.getInstance().getLabyModAPI().displayMessageInChat(ModColor.cl("c") + "/labymodchatdebug ip:port");
                    return true;
                }
                String ip = lowercaseMessage.split(" ")[1];
                int port = 25565;
                if (ip.contains(":")) {
                    final String[] split = ip.split(":");
                    ip = split[0];
                    port = Integer.parseInt(split[1]);
                }
                LabyMod.getInstance().getLabyModAPI().displayMessageInChat(ModColor.cl("a") + "Connecting to " + ModColor.cl("f") + ip + ModColor.cl("a") + " on port " + ModColor.cl("f") + port);
                this.connection.customIp = ip;
                this.connection.customPort = port;
                this.connection.connect();
                return true;
            }
            else {
                final char[] actions = { 'k', 'o', 'b', 'u' };
                for (int action = 0; action < actions.length; ++action) {
                    final char character = actions[action];
                    if (lowercaseMessage.startsWith("/lm" + character)) {
                        final String[] words = message.split(" ");
                        final JsonObject object = new JsonObject();
                        if (action % 2 != 0) {
                            if (words.length < 2) {
                                LabyMod.getInstance().getLabyModAPI().displayMessageInChat(ModColor.cl("c") + "/lm" + character + " <target>");
                                return true;
                            }
                        }
                        else {
                            if (words.length < 3) {
                                LabyMod.getInstance().getLabyModAPI().displayMessageInChat(ModColor.cl("c") + "/lm" + character + " <target> <string>");
                                return true;
                            }
                            String string = "";
                            for (int i = 2; i < words.length; ++i) {
                                if (!string.isEmpty()) {
                                    string += " ";
                                }
                                string += words[i];
                            }
                            object.addProperty("string", string);
                        }
                        object.addProperty("target", words[1]);
                        object.addProperty("action", (Number)action);
                        this.connection.sendPacket((Packet)new PacketAddonMessage("LMAC", object.toString()));
                        return true;
                    }
                }
            }
        }
        catch (Exception error) {
            LabyMod.getInstance().getLabyModAPI().displayMessageInChat(ModColor.cl("c") + "Error: " + error.getMessage());
        }
        return false;
    }
    
    @ConstructorProperties({ "connection" })
    public LabyConnectCommands(final ClientConnection connection) {
        this.connection = connection;
    }
}
