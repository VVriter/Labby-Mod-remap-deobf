//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.user.group;

import com.google.gson.*;
import java.util.*;
import net.labymod.support.util.*;
import net.labymod.utils.request.*;
import net.labymod.user.*;
import java.awt.*;

public class GroupManager
{
    public static final LabyGroup DEFAULT_GROUP;
    public static final short GROUP_ID_PLUS = 10;
    public static final short GROUP_ID_COSMETIC_CREATOR = 11;
    private final Gson gson;
    private Map<Short, LabyGroup> groups;
    private Short[] groupOrder;
    
    public GroupManager() {
        this.gson = new Gson();
        this.groups = new HashMap<Short, LabyGroup>();
        this.groupOrder = new Short[0];
        this.load();
    }
    
    public void load() {
        DownloadServerRequest.getStringAsync("https://dl.labymod.net/groups.json", new ServerResponse<String>() {
            @Override
            public void success(final String json) {
                final Map<Short, LabyGroup> map = new HashMap<Short, LabyGroup>();
                final GroupData groupData = (GroupData)GroupManager.this.gson.fromJson(json, (Class)GroupData.class);
                final LabyGroup[] labyGroups = groupData.getGroups();
                final Short[] array = new Short[labyGroups.length];
                int index = 0;
                for (final LabyGroup group : labyGroups) {
                    final short id = (short)group.getId();
                    group.init();
                    map.put(id, group);
                    array[index] = id;
                    ++index;
                }
                GroupManager.this.groups = map;
                GroupManager.this.groupOrder = array;
            }
            
            @Override
            public void failed(final RequestException exception) {
                exception.printStackTrace();
                Debug.log(Debug.EnumDebugMode.USER_MANAGER, "Wrong response code while loading groups: " + exception.getCode());
            }
        });
    }
    
    public LabyGroup getGroupById(final short id) {
        if (id == GroupManager.DEFAULT_GROUP.getId()) {
            return GroupManager.DEFAULT_GROUP;
        }
        return this.groups.get(id);
    }
    
    public boolean hasPermissionOf(final User user, final short groupId) {
        final LabyGroup userGroup = user.getGroup();
        if (groupId == GroupManager.DEFAULT_GROUP.getId()) {
            return true;
        }
        if (userGroup == null) {
            return false;
        }
        final short userGroupId = (short)userGroup.getId();
        for (final short orderId : this.groupOrder) {
            if (orderId == userGroupId) {
                return true;
            }
            if (orderId == groupId) {
                return false;
            }
        }
        return false;
    }
    
    static {
        DEFAULT_GROUP = new LabyGroup(0, "user", "User", null, 'f', "USER", "NONE", null, null).init();
    }
}
