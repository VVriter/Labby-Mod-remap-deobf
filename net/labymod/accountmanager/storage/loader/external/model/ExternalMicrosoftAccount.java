//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.accountmanager.storage.loader.external.model;

import com.google.gson.annotations.*;
import net.labymod.accountmanager.storage.loader.external.model.token.*;
import net.labymod.accountmanager.authentication.microsoft.model.*;
import net.labymod.accountmanager.authentication.microsoft.model.minecraft.*;
import java.util.*;
import java.net.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class ExternalMicrosoftAccount extends ExternalAccount
{
    @Deprecated
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("avatar_image")
    private String avatarImage;
    @SerializedName("tokens")
    private TokenChain tokens;
    
    public ExternalMicrosoftAccount(final UUID uuid, final String name, final MicrosoftAccountResult result) {
        super(uuid, name, result.getMinecraftAPILogin().accessToken);
        (this.tokens = new TokenChain()).update(result);
        this.updateProfile(result);
    }
    
    public boolean isMicrosoft() {
        return true;
    }
    
    public String getAvatarImage() {
        return this.avatarImage;
    }
    
    public TokenChain getTokens() {
        return this.tokens;
    }
    
    public void updateProfile(final MicrosoftAccountResult result) {
        if (this.tokens == null) {
            this.tokens = new TokenChain();
        }
        this.tokens.update(result);
        this.setAvatarUrl(result.getAvatarUrl());
        final LoginResponse minecraft = result.getMinecraftAPILogin();
        this.setAccessToken(minecraft.getToken());
        this.setAccessTokenExpiresAt(minecraft.getExpiresAt());
        this.setUsername(result.getMinecraftProfile().name);
    }
    
    public void setAvatarUrl(final String avatarUrl) {
        try {
            final Base64.Encoder base64 = Base64.getEncoder();
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(ImageIO.read(new URL(avatarUrl)), "png", byteArrayOutputStream);
            this.avatarImage = "data:image/png;base64," + base64.encodeToString(byteArrayOutputStream.toByteArray());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
