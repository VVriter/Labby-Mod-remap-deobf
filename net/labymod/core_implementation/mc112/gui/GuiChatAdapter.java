//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.gui;

import net.labymod.ingamechat.*;
import net.labymod.ingamechat.renderer.types.*;
import java.util.concurrent.*;
import net.labymod.main.*;
import net.labymod.api.events.*;
import net.labymod.core.*;
import net.labymod.utils.manager.*;
import net.labymod.utils.*;
import net.labymod.servermanager.*;
import net.labymod.ingamechat.tools.filter.*;
import net.labymod.ingamechat.renderer.*;
import java.util.*;
import org.apache.logging.log4j.*;
import java.beans.*;

public class GuiChatAdapter extends bjb
{
    private static final Logger logger;
    private IngameChatManager manager;
    private ChatRendererMain chatMain;
    private ChatRendererSecond chatSecond;
    private ChatRenderer[] chatRenderers;
    private Queue<QueuedMessage> queuedMessages;
    
    public GuiChatAdapter(final bib mcIn) {
        super(mcIn);
        this.manager = IngameChatManager.INSTANCE;
        this.chatMain = this.manager.getMain();
        this.chatSecond = this.manager.getSecond();
        this.chatRenderers = this.manager.getChatRenderers();
        this.queuedMessages = new ConcurrentLinkedQueue<QueuedMessage>();
    }
    
    public void a(final String message) {
        this.manager.addToSentMessages(message);
    }
    
    public List<String> b() {
        return this.manager.getSentMessages();
    }
    
    public void a(final int updateCounter) {
        if (LabyMod.getInstance() == null) {
            return;
        }
        final int offset = LabyMod.getInstance().getDrawUtils().getHeight() - 48;
        bus.c(0.0f, (float)(-offset), 0.0f);
        if (bib.z().t.o == aed.b.c) {
            return;
        }
        for (final ChatRenderer chatRenderer : this.chatRenderers) {
            chatRenderer.renderChat(updateCounter);
        }
        if (this.queuedMessages.size() != 0) {
            QueuedMessage queuedMessage = null;
            while ((queuedMessage = this.queuedMessages.poll()) != null) {
                this.setChatLine(queuedMessage.getComponent(), queuedMessage.getChatLineId(), bib.z().q.e(), false, false, "Global", null);
            }
        }
    }
    
    public void c(final int id) {
    }
    
    public void deleteChatLine(final ChatRenderer chatRenderer, final int id) {
        chatRenderer.deleteChatLine(id);
    }
    
    public void a(final boolean forceClearChat) {
        if (LabyMod.getInstance() == null) {
            return;
        }
        if (LabyMod.getSettings().clearChatOnJoin || !forceClearChat) {
            for (final ChatRenderer chatRenderer : this.chatRenderers) {
                chatRenderer.clearChatMessages(true);
            }
            this.manager.getSentMessages().clear();
        }
    }
    
    public void b(int amount) {
        if (LabyMod.getInstance() == null) {
            return;
        }
        if (amount != 1 && amount != -1) {
            amount = (int)(amount / 7.0 * LabyMod.getSettings().chatScrollSpeed);
        }
        for (final ChatRenderer chatRenderer : this.chatRenderers) {
            if (chatRenderer.isMouseOver()) {
                chatRenderer.scroll(amount);
            }
        }
    }
    
    public void a(final hh component, final int chatLineId) {
        if (LabyMod.getInstance() == null) {
            return;
        }
        boolean cancel = false;
        for (final MessageReceiveEvent event : LabyMod.getInstance().getEventManager().getMessageReceive()) {
            if (event.onReceive(component.d(), component.c())) {
                cancel = true;
            }
        }
        if (!cancel) {
            this.queuedMessages.add(new QueuedMessage(component, chatLineId));
        }
    }
    
    private void setChatLine(hh component, final int chatLineId, final int updateCounter, final boolean refresh, boolean secondChat, String room, Integer highlightColor) {
        if (component == null || LabyMod.getInstance() == null) {
            return;
        }
        ChatRenderer target = null;
        if (!refresh) {
            final ChatDisplayAction serverDisplayActionResponse = LabyMod.getInstance().getServerManager().handleChatMessage(component.c(), component.d());
            final MessageData messageData = this.manager.handleSwap(serverDisplayActionResponse, LabyModCore.getMinecraft().getChatComponent((Object)component));
            if (messageData == null) {
                return;
            }
            secondChat = messageData.isDisplayInSecondChat();
            component = (hh)TagManager.tagComponent(component);
            final Filters.Filter filter = messageData.getFilter();
            if (filter != null) {
                room = filter.getRoom();
                if (filter.isHighlightMessage()) {
                    highlightColor = ModColor.toRGB(filter.getHighlightColorR(), filter.getHighlightColorG(), filter.getHighlightColorB(), 120);
                }
            }
        }
        target = (secondChat ? this.chatSecond : this.chatMain);
        if (chatLineId != 0) {
            this.deleteChatLine(target, chatLineId);
        }
        final int width = target.getVisualWidth();
        final List<hh> list = (List<hh>)bjc.a(component, width, LabyModCore.getMinecraft().getFontRenderer(), false, false);
        for (final hh ITextComponent : list) {
            target.addChatLine(ITextComponent.d(), secondChat, room, ITextComponent, updateCounter, chatLineId, highlightColor, refresh);
        }
        target.checkLimit();
        if (!refresh) {
            target.handleBackendLines(component.d(), secondChat, room, component, chatLineId, updateCounter, highlightColor);
            this.manager.handleUnread(room);
            GuiChatAdapter.logger.info("[" + target.getLogPrefix() + "] " + component.c());
        }
    }
    
    public void a() {
        for (final ChatRenderer chatRenderer : this.chatRenderers) {
            chatRenderer.clearChatMessages(false);
            for (int i = chatRenderer.getBackendComponents().size() - 1; i >= 0; --i) {
                final ChatLine chatLine = chatRenderer.getBackendComponents().get(i);
                this.setChatLine((hh)chatLine.getComponent(), chatLine.getChatLineId(), chatLine.getUpdateCounter(), true, chatLine.isSecondChat(), chatLine.getRoom(), chatLine.getHighlightColor());
            }
        }
    }
    
    public hh a(final int mouseX, final int mouseY) {
        for (final ChatRenderer chatRenderer : this.chatRenderers) {
            final Object component = this.getHoveringComponent(chatRenderer);
            if (component != null) {
                return (hh)component;
            }
        }
        return null;
    }
    
    private Object getHoveringComponent(final ChatRenderer chatRenderer) {
        if (!chatRenderer.isChatOpen() || !chatRenderer.isMouseOver()) {
            return null;
        }
        float mouseX = chatRenderer.isRightBound() ? (chatRenderer.lastMouseX - chatRenderer.getChatPositionX() + chatRenderer.getChatWidth() + 3.0f) : (-(chatRenderer.getChatPositionX() - chatRenderer.lastMouseX));
        float mouseY = -chatRenderer.lastMouseY + chatRenderer.getChatPositionY();
        mouseX /= this.g();
        mouseY /= this.g();
        final List<ChatLine> list = new LinkedList<ChatLine>();
        for (final ChatLine chatline : chatRenderer.getChatLines()) {
            if (chatline != null) {
                if (!chatline.getRoom().equals(this.manager.getSelectedRoom())) {
                    continue;
                }
                list.add(chatline);
            }
        }
        final int hoveredLine = (int)mouseY / bib.z().k.a + chatRenderer.getScrollPos();
        if (hoveredLine < 0 || hoveredLine >= list.size()) {
            return null;
        }
        int x = 0;
        final ChatLine chatline2 = list.get(hoveredLine);
        for (final hh ITextComponent : (hh)chatline2.getComponent()) {
            if (!(ITextComponent instanceof ho)) {
                continue;
            }
            x += bib.z().k.a(bjc.a(((ho)ITextComponent).g(), false));
            if (x > mouseX) {
                return ITextComponent;
            }
        }
        return null;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    private class QueuedMessage
    {
        private hh component;
        private int chatLineId;
        
        public hh getComponent() {
            return this.component;
        }
        
        public int getChatLineId() {
            return this.chatLineId;
        }
        
        @ConstructorProperties({ "component", "chatLineId" })
        public QueuedMessage(final hh component, final int chatLineId) {
            this.component = component;
            this.chatLineId = chatLineId;
        }
    }
}
