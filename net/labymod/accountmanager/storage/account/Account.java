//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.account;

import java.util.*;
import net.labymod.accountmanager.storage.*;

public interface Account
{
    String getUsername();
    
    UUID getUUID();
    
    String getAccessToken();
    
    boolean isMicrosoft();
    
    String getAvatarImage();
    
    boolean isDemo();
    
    boolean isPremium();
    
    boolean isAccessTokenExpired();
    
    AccountSessionState getSessionState();
    
    void setSessionState(final AccountSessionState p0);
    
    StorageType getStorageType();
    
    long getLastAccessTokenUpdated();
}
