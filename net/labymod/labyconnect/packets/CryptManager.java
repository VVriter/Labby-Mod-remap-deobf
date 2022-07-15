//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect.packets;

import net.labymod.support.util.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.spec.*;
import java.security.*;

public class CryptManager
{
    public static SecretKey createNewSharedKey() {
        try {
            final KeyGenerator key = KeyGenerator.getInstance("AES");
            key.init(128);
            return key.generateKey();
        }
        catch (NoSuchAlgorithmException var1) {
            throw new Error(var1);
        }
    }
    
    public static KeyPair createNewKeyPair() {
        try {
            final KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA");
            keyPair.initialize(1024);
            return keyPair.generateKeyPair();
        }
        catch (NoSuchAlgorithmException var1) {
            var1.printStackTrace();
            Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Key pair generation failed!");
            return null;
        }
    }
    
    public static byte[] getServerIdHash(final String input, final PublicKey publicKey, final SecretKey secretKey) {
        try {
            return digestOperation("SHA-1", new byte[][] { input.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static byte[] digestOperation(final String type, final byte[]... bytes) {
        try {
            final MessageDigest disgest = MessageDigest.getInstance(type);
            final byte[][] byts = bytes;
            for (int length = bytes.length, i = 0; i < length; ++i) {
                final byte[] b = byts[i];
                disgest.update(b);
            }
            return disgest.digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static PublicKey decodePublicKey(final byte[] encodedKey) {
        try {
            final X509EncodedKeySpec var1 = new X509EncodedKeySpec(encodedKey);
            final KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var1);
        }
        catch (NoSuchAlgorithmException ex) {}
        catch (InvalidKeySpecException ex2) {}
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Public key reconstitute failed!");
        return null;
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey key, final byte[] secretKeyEncrypted) {
        return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
    }
    
    public static byte[] encryptData(final Key key, final byte[] data) {
        return cipherOperation(1, key, data);
    }
    
    public static byte[] decryptData(final Key key, final byte[] data) {
        return cipherOperation(2, key, data);
    }
    
    private static byte[] cipherOperation(final int opMode, final Key key, final byte[] data) {
        try {
            return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
        }
        catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5) {
            var5.printStackTrace();
        }
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Cipher data failed!");
        return null;
    }
    
    private static Cipher createTheCipherInstance(final int opMode, final String transformation, final Key key) {
        try {
            final Cipher var3 = Cipher.getInstance(transformation);
            var3.init(opMode, key);
            return var3;
        }
        catch (InvalidKeyException var4) {
            var4.printStackTrace();
        }
        catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        }
        catch (NoSuchPaddingException var6) {
            var6.printStackTrace();
        }
        Debug.log(Debug.EnumDebugMode.LABYMOD_CHAT, "Cipher creation failed!");
        return null;
    }
    
    public static Cipher createNetCipherInstance(final int opMode, final Key key) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        }
        catch (GeneralSecurityException generalsecurityexception) {
            throw new RuntimeException(generalsecurityexception);
        }
    }
}
