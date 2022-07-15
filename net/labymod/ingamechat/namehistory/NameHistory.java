//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamechat.namehistory;

import java.util.*;
import net.labymod.utils.*;
import org.apache.commons.lang3.*;

public class NameHistory
{
    private UUID uuid;
    private UUIDFetcher[] changes;
    
    public NameHistory(final UUID uuid, final UUIDFetcher[] changes) {
        this.uuid = uuid;
        ArrayUtils.reverse((Object[])(this.changes = changes));
    }
    
    public UUIDFetcher[] getChanges() {
        return this.changes;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
}
