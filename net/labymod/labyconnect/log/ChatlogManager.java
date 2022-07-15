//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.log;

import com.mojang.authlib.*;
import net.labymod.labyconnect.user.*;
import java.util.*;
import net.labymod.support.util.*;
import java.io.*;
import java.nio.charset.*;

public class ChatlogManager
{
    private final int MAX_LOG_MESSAGE_COUNT = 1000;
    private List<SingleChat> chats;
    
    public ChatlogManager() {
        this.chats = new ArrayList<SingleChat>();
    }
    
    public SingleChat getChat(final ChatUser user) {
        for (final SingleChat chat : this.chats) {
            if (chat.getChatPartner().equals(user)) {
                return chat.apply(user);
            }
        }
        final SingleChat singleChat = new SingleChat(this.chats.size(), user, new ArrayList<MessageChatComponent>());
        this.chats.add(singleChat);
        return singleChat;
    }
    
    public void loadChatlogs(final UUID accountUUID) {
        this.chats.clear();
        final File chatlogFile = new File(String.format("LabyMod/chatlog/%s.log", accountUUID.toString()));
        if (!chatlogFile.exists()) {
            return;
        }
        try {
            final DataInputStream dis = new DataInputStream(new FileInputStream(chatlogFile));
            for (int total = dis.readInt(), i = 0; i < total; ++i) {
                dis.readInt();
                final String name = this.readString(dis);
                final UUID uuid = new UUID(dis.readLong(), dis.readLong());
                final ArrayList<MessageChatComponent> messageArray = new ArrayList<MessageChatComponent>();
                final int totalMessages = dis.readInt();
                if (totalMessages < 1000) {
                    for (int b = 0; b < totalMessages; ++b) {
                        final String sender = this.readString(dis);
                        final long time = dis.readLong();
                        final String message = this.readString(dis);
                        messageArray.add(new MessageChatComponent(sender, time, message));
                    }
                }
                final GameProfile dummyGameProfile = new GameProfile(uuid, name);
                final SingleChat dummySingleChat = this.getChat(new ChatUser(dummyGameProfile, UserStatus.OFFLINE, "", null, 0, System.currentTimeMillis(), 0L, "", 0L, 0L, 0, false));
                dummySingleChat.getMessages().addAll(messageArray);
            }
            dis.close();
        }
        catch (Exception e) {
            chatlogFile.delete();
            e.printStackTrace();
        }
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Loaded " + this.chats.size() + " chats!");
    }
    
    public void saveChatlogs(final UUID accountUUID) {
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Save chat log file for " + accountUUID.toString());
        final File chatlogFile = new File(String.format("LabyMod/chatlog/%s.log", accountUUID.toString()));
        if (!chatlogFile.getParentFile().exists()) {
            chatlogFile.getParentFile().mkdirs();
        }
        if (!chatlogFile.exists()) {
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Create new log file for " + accountUUID.toString());
            try {
                chatlogFile.createNewFile();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        try {
            final List<SingleChat> chatTemp = new ArrayList<SingleChat>(this.chats);
            final Iterator<SingleChat> iterator = chatTemp.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getChatPartner().isParty()) {
                    iterator.remove();
                }
            }
            final DataOutputStream dos = new DataOutputStream(new FileOutputStream(chatlogFile));
            dos.writeInt(chatTemp.size());
            for (final SingleChat chat : chatTemp) {
                dos.writeInt(chat.getId());
                final GameProfile profile = chat.getChatPartner().getGameProfile();
                this.writeString(dos, profile.getName());
                dos.writeLong(profile.getId().getMostSignificantBits());
                dos.writeLong(profile.getId().getLeastSignificantBits());
                final List<MessageChatComponent> messageArray = chat.getMessages();
                int count;
                final int size = count = messageArray.size();
                final boolean flag = size > 300;
                dos.writeInt(flag ? 300 : size);
                for (int b = 0; b < size; ++b) {
                    if (flag && count > 300) {
                        --count;
                    }
                    else {
                        final MessageChatComponent component = messageArray.get(b);
                        this.writeString(dos, component.getSender());
                        dos.writeLong(component.getSentTime());
                        this.writeString(dos, component.getMessage());
                    }
                }
            }
            dos.flush();
            dos.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Saved " + this.chats.size() + " chats!");
    }
    
    private void writeString(final DataOutputStream dos, final String string) {
        try {
            final byte[] bytes = string.getBytes(Charset.forName("UTF8"));
            dos.writeInt(bytes.length);
            for (final byte b : bytes) {
                dos.writeByte(b);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String readString(final DataInputStream dis) {
        try {
            final int length = dis.readInt();
            final byte[] bytes = new byte[length];
            for (int i = 0; i < length; ++i) {
                bytes[i] = dis.readByte();
            }
            return new String(bytes, Charset.forName("UTF8"));
        }
        catch (Exception ex) {
            return "";
        }
    }
    
    public int getMAX_LOG_MESSAGE_COUNT() {
        this.getClass();
        return 1000;
    }
    
    public List<SingleChat> getChats() {
        return this.chats;
    }
}
