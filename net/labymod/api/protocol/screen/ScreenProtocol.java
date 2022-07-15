//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.screen;

import net.labymod.api.events.*;
import net.labymod.main.*;
import net.labymod.serverapi.common.widgets.*;
import net.labymod.api.protocol.screen.render.*;
import net.labymod.support.util.*;
import com.google.gson.*;
import net.labymod.api.protocol.screen.render.components.*;
import net.labymod.serverapi.common.widgets.components.*;
import java.util.*;
import net.labymod.serverapi.common.widgets.util.*;
import net.labymod.api.protocol.screen.render.util.*;

public class ScreenProtocol implements IInteractionCallback, ServerMessageEvent
{
    public ScreenProtocol() {
        LabyMod.getInstance().getEventManager().register((ServerMessageEvent)this);
    }
    
    public void onServerMessage(final String messageKey, final JsonElement serverMessage) {
        if (messageKey.equals("screen")) {
            try {
                final JsonObject screenObject = serverMessage.getAsJsonObject();
                final WidgetScreen widgetScreen = WidgetScreen.from(screenObject);
                final EnumScreenAction screenAction = EnumScreenAction.values()[screenObject.get("action").getAsInt()];
                final bib minecraft = bib.z();
                switch (screenAction) {
                    case OPEN: {
                        final bib bib;
                        blk currentScreen;
                        final WidgetScreen screen;
                        minecraft.a(() -> {
                            try {
                                currentScreen = bib.m;
                                bib.a((blk)new GuiScreenProtocol(currentScreen, screen.getId(), (IInteractionCallback)this, (List)this.getRenderWidgets(screen)));
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            return;
                        });
                        break;
                    }
                    case UPDATE: {
                        final blk currentScreen2 = minecraft.m;
                        if (currentScreen2 instanceof GuiScreenProtocol) {
                            ((GuiScreenProtocol)currentScreen2).setWidgets((List)this.getRenderWidgets(widgetScreen));
                            break;
                        }
                        break;
                    }
                    case WRAP_INVENTORY: {
                        final bib bib2;
                        blk currentScreen3;
                        bmg containerScreen;
                        final WidgetScreen screen2;
                        minecraft.a(() -> {
                            try {
                                currentScreen3 = bib2.m;
                                if (currentScreen3 instanceof bmg) {
                                    containerScreen = (bmg)currentScreen3;
                                    bib2.a((blk)new GuiWrappedInventory(containerScreen, screen2.getId(), (IInteractionCallback)this, (List)this.getRenderWidgets(screen2), screen2.getLayout()));
                                }
                            }
                            catch (Exception exception2) {
                                exception2.printStackTrace();
                            }
                            return;
                        });
                        break;
                    }
                    case CLOSE: {
                        final blk currentScreen4;
                        GuiScreenProtocol screenProtocol;
                        minecraft.a(() -> {
                            currentScreen4 = minecraft.m;
                            if (currentScreen4 instanceof GuiScreenProtocol) {
                                screenProtocol = (GuiScreenProtocol)currentScreen4;
                                screenProtocol.closeProperly();
                            }
                            return;
                        });
                        break;
                    }
                }
            }
            catch (Exception e) {
                Debug.log(Debug.EnumDebugMode.API, "Can't parse screen: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private List<RenderWidget<? extends Widget>> getRenderWidgets(final WidgetScreen screen) {
        final List<RenderWidget<? extends Widget>> renderWidgets = new ArrayList<RenderWidget<? extends Widget>>();
        if (!screen.getWidgets().isEmpty()) {
            for (final Widget widget : screen.getWidgets()) {
                final RenderWidget<? extends Widget> renderWidget = (RenderWidget<? extends Widget>)RenderWidget.from(widget, (IInteractionCallback)this);
                if (renderWidget != null) {
                    renderWidgets.add(renderWidget);
                }
            }
        }
        return renderWidgets;
    }
    
    public void sendResponse(final int screenId, final EnumResponse responseType, final int widgetId, final IStateCollector collector) {
        final JsonObject response = new JsonObject();
        response.addProperty("id", (Number)screenId);
        response.addProperty("type", (Number)responseType.ordinal());
        if (responseType == EnumResponse.INTERACT) {
            response.addProperty("widget_id", (Number)widgetId);
        }
        final JsonObject states = new JsonObject();
        collector.collect(states);
        response.add("states", (JsonElement)states);
        LabyMod.getInstance().getLabyModAPI().sendJsonMessageToServer("screen", (JsonElement)response);
    }
}
