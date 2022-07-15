//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.listeners;

import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.utils.*;
import net.labymod.gui.*;
import com.google.gson.*;

public class InputPromptListener implements ServerMessageEvent
{
    private LabyMod labymod;
    
    public InputPromptListener(final LabyMod labymod) {
        this.labymod = labymod;
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("input_prompt")) {
            final JsonObject obj = serverMessage.getAsJsonObject();
            try {
                final int id = obj.get("id").getAsInt();
                final String message = obj.get("message").getAsString();
                final String value = obj.get("value").getAsString();
                final String placeholder = obj.get("placeholder").getAsString();
                final int maxLength = obj.get("max_length").getAsInt();
                bib.z().a((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        bib.z().a((blk)new GuiInputPrompt(message, value, placeholder, maxLength, (Consumer)new Consumer<String>() {
                            @Override
                            public void accept(final String value) {
                                InputPromptListener.this.submitUserInput(id, value);
                            }
                        }));
                    }
                });
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void submitUserInput(final int id, final String value) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", (Number)id);
        jsonObject.addProperty("value", value);
        LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("input_prompt", (JsonElement)jsonObject);
    }
}
