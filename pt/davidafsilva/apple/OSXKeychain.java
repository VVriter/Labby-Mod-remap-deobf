//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package pt.davidafsilva.apple;

import java.io.*;
import java.util.*;

public class OSXKeychain
{
    private static OSXKeychain instance;
    private static final Map<Integer, OSXKeychainProtocolType> PROTOCOLS;
    
    private OSXKeychain() {
    }
    
    public static OSXKeychain getInstance() throws OSXKeychainException {
        if (OSXKeychain.instance == null) {
            try {
                loadSharedObject();
            }
            catch (IOException e) {
                throw new OSXKeychainException("Failed to load osxkeychain.so", e);
            }
            OSXKeychain.instance = new OSXKeychain();
        }
        return OSXKeychain.instance;
    }
    
    public void addGenericPassword(final String serviceName, final String accountName, final String password) throws OSXKeychainException {
        this._addGenericPassword(serviceName, accountName, password);
    }
    
    public void modifyGenericPassword(final String serviceName, final String accountName, final String password) throws OSXKeychainException {
        this._modifyGenericPassword(serviceName, accountName, password);
    }
    
    public Optional<String> findGenericPassword(final String serviceName, final String accountName) throws OSXKeychainException {
        return Optional.ofNullable(this._findGenericPassword(serviceName, accountName));
    }
    
    public void deleteGenericPassword(final String serviceName, final String accountName) throws OSXKeychainException {
        this._deleteGenericPassword(serviceName, accountName);
    }
    
    private native void _addGenericPassword(final String p0, final String p1, final String p2) throws OSXKeychainException;
    
    private native void _modifyGenericPassword(final String p0, final String p1, final String p2) throws OSXKeychainException;
    
    private native String _findGenericPassword(final String p0, final String p1) throws OSXKeychainException;
    
    private native void _deleteGenericPassword(final String p0, final String p1) throws OSXKeychainException;
    
    private static void loadSharedObject() throws IOException {
        final File tmpFile = File.createTempFile("osxkeychain", ".so");
        try (final InputStream soInJarStream = OSXKeychain.class.getResourceAsStream("/osxkeychain.so");
             final OutputStream soInTmpStream = new FileOutputStream(tmpFile)) {
            final File soInTmp = File.createTempFile("osxkeychain", ".so");
            soInTmp.deleteOnExit();
            final byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = soInJarStream.read(buffer)) > 0) {
                soInTmpStream.write(buffer, 0, bytesRead);
            }
        }
        System.load(tmpFile.getAbsolutePath());
    }
    
    static {
        (PROTOCOLS = new HashMap<Integer, OSXKeychainProtocolType>(32)).put(548, OSXKeychainProtocolType.AFP);
        OSXKeychain.PROTOCOLS.put(3020, OSXKeychainProtocolType.CIFS);
        OSXKeychain.PROTOCOLS.put(2401, OSXKeychainProtocolType.CVSpserver);
        OSXKeychain.PROTOCOLS.put(3689, OSXKeychainProtocolType.DAAP);
        OSXKeychain.PROTOCOLS.put(3031, OSXKeychainProtocolType.EPPC);
        OSXKeychain.PROTOCOLS.put(21, OSXKeychainProtocolType.FTP);
        OSXKeychain.PROTOCOLS.put(990, OSXKeychainProtocolType.FTPS);
        OSXKeychain.PROTOCOLS.put(80, OSXKeychainProtocolType.HTTP);
        OSXKeychain.PROTOCOLS.put(443, OSXKeychainProtocolType.HTTPS);
        OSXKeychain.PROTOCOLS.put(143, OSXKeychainProtocolType.IMAP);
        OSXKeychain.PROTOCOLS.put(993, OSXKeychainProtocolType.IMAPS);
        OSXKeychain.PROTOCOLS.put(631, OSXKeychainProtocolType.IPP);
        OSXKeychain.PROTOCOLS.put(6667, OSXKeychainProtocolType.IRC);
        OSXKeychain.PROTOCOLS.put(994, OSXKeychainProtocolType.IRCS);
        OSXKeychain.PROTOCOLS.put(389, OSXKeychainProtocolType.LDAP);
        OSXKeychain.PROTOCOLS.put(636, OSXKeychainProtocolType.LDAPS);
        OSXKeychain.PROTOCOLS.put(119, OSXKeychainProtocolType.NNTP);
        OSXKeychain.PROTOCOLS.put(563, OSXKeychainProtocolType.NNTPS);
        OSXKeychain.PROTOCOLS.put(110, OSXKeychainProtocolType.POP3);
        OSXKeychain.PROTOCOLS.put(995, OSXKeychainProtocolType.POP3S);
        OSXKeychain.PROTOCOLS.put(554, OSXKeychainProtocolType.RTSP);
        OSXKeychain.PROTOCOLS.put(25, OSXKeychainProtocolType.SMTP);
        OSXKeychain.PROTOCOLS.put(1080, OSXKeychainProtocolType.SOCKS);
        OSXKeychain.PROTOCOLS.put(22, OSXKeychainProtocolType.SSH);
        OSXKeychain.PROTOCOLS.put(3690, OSXKeychainProtocolType.SVN);
        OSXKeychain.PROTOCOLS.put(23, OSXKeychainProtocolType.Telnet);
        OSXKeychain.PROTOCOLS.put(992, OSXKeychainProtocolType.TelnetS);
    }
}
