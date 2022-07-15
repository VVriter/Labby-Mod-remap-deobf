//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.gui.elements;

import net.labymod.labyconnect.gui.*;
import net.labymod.gui.elements.*;
import net.labymod.labyconnect.user.*;
import net.labymod.gui.layout.*;
import java.text.*;
import net.labymod.main.*;
import net.labymod.labyconnect.log.*;
import java.util.*;
import net.labymod.utils.*;
import java.util.regex.*;

public class WinChatlog extends WindowElement<GuiFriendsLayout>
{
    private static final Pattern URL_PATTERNS;
    private Scrollbar scrollbar;
    private ChatUser clientUser;
    private long prevMessageTime;
    private String lastRenderHoveringMessage;
    private DateFormat timeDateFormat;
    
    public WinChatlog(final GuiFriendsLayout chatLayout, final ChatUser clientUser) {
        super((WindowLayout)chatLayout);
        this.prevMessageTime = 0L;
        this.lastRenderHoveringMessage = null;
        this.timeDateFormat = new SimpleDateFormat("HH:mm");
        this.clientUser = clientUser;
    }
    
    protected void init(final List<bja> buttonlist, final int left, final int top, final int right, final int bottom) {
        (this.scrollbar = new Scrollbar(0)).init();
        this.scrollbar.setSpeed(15);
        this.scrollbar.setPosition(this.right - 4, this.top + 4, this.right, this.bottom - 4);
        this.updateGuiElements();
    }
    
    private void updateGuiElements() {
        if (GuiFriendsLayout.selectedUser == null) {
            return;
        }
        double totalEntryHeight = 0.0;
        final SingleChat singleChat = LabyMod.getInstance().getLabyConnect().getChatlogManager().getChat(GuiFriendsLayout.selectedUser);
        final List<MessageChatComponent> messages = new ArrayList<MessageChatComponent>();
        messages.addAll(singleChat.getMessages());
        if (messages.size() != 0) {
            for (final MessageChatComponent messageChatComponent : messages) {
                totalEntryHeight += this.drawMessageEntry(messageChatComponent, totalEntryHeight, false, 0, 0);
            }
            totalEntryHeight = totalEntryHeight / messages.size() + 2.0;
        }
        final long sentTime = (singleChat.getMessages().size() == 0) ? 0L : singleChat.getMessages().get(singleChat.getMessages().size() - 1).getSentTime();
        if (this.prevMessageTime != sentTime) {
            this.prevMessageTime = sentTime;
            this.scrollbar.requestBottom();
        }
        this.scrollbar.setEntryHeight(totalEntryHeight);
        this.scrollbar.update(messages.size());
    }
    
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        if (GuiFriendsLayout.selectedUser == null) {
            return;
        }
        this.updateGuiElements();
        this.lastRenderHoveringMessage = null;
        final SingleChat singleChat = LabyMod.getInstance().getLabyConnect().getChatlogManager().getChat(GuiFriendsLayout.selectedUser);
        final List<MessageChatComponent> messages = singleChat.getMessages();
        try {
            double entryY = this.top + this.scrollbar.getScrollY() + 4.0;
            for (final MessageChatComponent messageChatComponent : messages) {
                entryY += this.drawMessageEntry(messageChatComponent, entryY, true, mouseX, mouseY);
                entryY += 2.0;
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        this.scrollbar.draw();
    }
    
    public void actionPerformed(final bja button) {
    }
    
    private int drawMessageEntry(final MessageChatComponent messageChatComponent, final double entryY, final boolean shouldDraw, final int mouseX, final int mouseY) {
        final DrawUtils drawUtils = LabyMod.getInstance().getDrawUtils();
        final String meName = LabyMod.getInstance().getPlayerName();
        final boolean isMe = messageChatComponent.getSender().equals(meName);
        final String message = messageChatComponent.getMessage();
        final int margineWidth = 20;
        final int margineBorder = 30;
        final int paddingWidth = 11;
        final int paddingHeight = 2;
        final int spaceBetweenText = 10;
        final int headSize = 12;
        final double maxWidth = this.right - this.left - (this.right - this.left) / 5.0 - margineWidth - paddingWidth / 2.0 - 20.0;
        final List<String> messageList = drawUtils.listFormattedStringToWidth(message, (int)maxWidth);
        final int entryHeight = messageList.size() * spaceBetweenText + paddingHeight * 2;
        final int firstMessageWidth = drawUtils.getStringWidth(message);
        if (!shouldDraw) {
            return entryHeight;
        }
        if (isMe) {
            double entryLeft = this.right - margineBorder - maxWidth - margineWidth - paddingWidth / 2.0;
            final double entryRight = this.right - margineBorder - margineWidth;
            if (messageList.size() == 1) {
                entryLeft = this.right - margineBorder - firstMessageWidth - margineWidth - paddingWidth;
            }
            final double entryTop = entryY;
            final double entryBottom = entryY + entryHeight;
            drawUtils.drawRect(entryLeft, entryTop, entryRight, entryBottom, ModColor.toRGB(80, 80, 80, 60));
            double lineYAdd = entryTop + paddingHeight + 1.0;
            for (String messageLine : messageList) {
                if (mouseX > entryLeft && mouseX < entryRight && mouseY > lineYAdd && mouseY < lineYAdd + spaceBetweenText) {
                    this.lastRenderHoveringMessage = messageLine;
                    if (this.containsUrl(messageLine)) {
                        messageLine = ModColor.cl('9') + messageLine + ModColor.cl('f');
                    }
                }
                drawUtils.drawString(messageLine, entryLeft + paddingWidth / 2.0, lineYAdd);
                lineYAdd += spaceBetweenText;
            }
            if (GuiFriendsLayout.selectedUser.isParty()) {
                LabyMod.getInstance().getDrawUtils().drawPlayerHead(messageChatComponent.getSender(), (int)(entryRight + margineWidth / 2.0 - headSize / 2.0), (int)(entryTop + (entryBottom - entryTop) / 2.0) - headSize / 2, headSize);
            }
            else {
                drawUtils.drawPlayerHead(this.clientUser.getGameProfile(), (int)(entryRight + margineWidth / 2.0 - headSize / 2.0), (int)(entryTop + (entryBottom - entryTop) / 2.0) - headSize / 2, headSize);
            }
            final String displayTime = this.timeDateFormat.format(messageChatComponent.getSentTime());
            drawUtils.drawRightString(displayTime, entryLeft - 2.0, entryTop + 2.0, 0.5);
        }
        else {
            final double entryLeft = this.left + margineBorder + margineWidth;
            double entryRight = this.left + margineBorder + maxWidth + margineWidth + paddingWidth / 2.0;
            if (messageList.size() == 1) {
                entryRight = this.left + margineBorder + firstMessageWidth + margineWidth + paddingWidth;
            }
            final double entryTop = entryY;
            final double entryBottom = entryY + entryHeight;
            drawUtils.drawRect(entryLeft, entryTop, entryRight, entryBottom, ModColor.toRGB(80, 80, 80, 60));
            double lineYAdd = entryTop + paddingHeight + 1.0;
            for (String messageLine : messageList) {
                if (mouseX > entryLeft && mouseX < entryRight && mouseY > lineYAdd && mouseY < lineYAdd + spaceBetweenText) {
                    this.lastRenderHoveringMessage = messageLine;
                    if (this.containsUrl(messageLine)) {
                        messageLine = ModColor.cl('9') + messageLine + ModColor.cl('f');
                    }
                }
                drawUtils.drawString(messageLine, entryLeft + paddingWidth / 2, lineYAdd);
                lineYAdd += spaceBetweenText;
            }
            if (GuiFriendsLayout.selectedUser.isParty()) {
                LabyMod.getInstance().getDrawUtils().drawPlayerHead(messageChatComponent.getSender(), (int)(entryLeft - margineWidth / 2.0 - headSize / 2.0), (int)(entryTop + (entryBottom - entryTop) / 2.0 - headSize / 2.0), headSize);
            }
            else {
                drawUtils.drawPlayerHead(GuiFriendsLayout.selectedUser.getGameProfile(), (int)(entryLeft - margineWidth / 2.0 - headSize / 2.0), (int)(entryTop + (entryBottom - entryTop) / 2.0 - headSize / 2.0), headSize);
            }
            final String displayTime = this.timeDateFormat.format(messageChatComponent.getSentTime());
            drawUtils.drawString(displayTime, entryRight + 2.0, entryTop + 2.0, 0.5);
        }
        return entryHeight;
    }
    
    private boolean containsUrl(final String message) {
        return message.contains("http://") || message.contains("https://");
    }
    
    public void handleMouseInput() {
        if (this.isMouseOver()) {
            this.scrollbar.mouseInput();
        }
    }
    
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
        if (this.lastRenderHoveringMessage != null) {
            final Matcher matcher = WinChatlog.URL_PATTERNS.matcher(this.lastRenderHoveringMessage);
            if (matcher.find()) {
                final int matchStart = matcher.start(1);
                final int matchEnd = matcher.end();
                final String website = this.lastRenderHoveringMessage.substring(matchStart, matchEnd);
                LabyMod.getInstance().openWebpage(website, true);
            }
        }
        return false;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseInput() {
    }
    
    public boolean isScrolledToTop() {
        return this.scrollbar.getScrollY() == 0.0;
    }
    
    public void updateScreen() {
    }
    
    static {
        URL_PATTERNS = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)", 42);
    }
}
