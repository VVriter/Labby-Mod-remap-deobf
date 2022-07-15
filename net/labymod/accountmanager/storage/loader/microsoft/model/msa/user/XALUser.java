//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.microsoft.model.msa.user;

public class XALUser
{
    public String deviceId;
    public Token[] tokens;
    
    public XALUser(final String deviceId, final Token[] tokens) {
        this.deviceId = deviceId;
        this.tokens = tokens;
    }
    
    public Token getTokenByRelyingParty(final String relyingParty) {
        for (final Token token : this.tokens) {
            if (token.relyingParty.equals(relyingParty)) {
                return token;
            }
        }
        return null;
    }
}
