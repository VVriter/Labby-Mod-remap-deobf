//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.external.model;

import com.google.gson.annotations.*;
import java.util.*;

public class ExternalAccounts
{
    public Map<UUID, ExternalAccount> accounts;
    @SerializedName("client_token")
    public String clientToken;
    
    public ExternalAccounts() {
        this.accounts = new HashMap<UUID, ExternalAccount>();
        this.clientToken = this.createNewClientToken();
    }
    
    public String createNewClientToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
