//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package pt.davidafsilva.apple;

public enum OSXKeychainProtocolType
{
    AFP("AFP", 1634103328), 
    Any("Any", 0), 
    AppleTalk("AppleTalk", 1635019883), 
    CIFS("CIFS", 1667851891), 
    CVSpserver("CVSpserver", 1668707184), 
    DAAP("DAAP", 1684103536), 
    EPPC("EPPC", 1701867619), 
    FTP("FTP", 1718906912), 
    FTPAccount("FTPAccount", 1718906977), 
    FTPProxy("FTPProxy", 1718907000), 
    FTPS("FTPS", 1718906995), 
    HTTP("HTTP", 1752462448), 
    HTTPProxy("HTTPProxy", 1752461432), 
    HTTPS("HTTPS", 1752461427), 
    HTTPSProxy("HTTPSProxy", 1752462200), 
    IMAP("IMAP", 1768776048), 
    IMAPS("IMAPS", 1768779891), 
    IPP("IPP", 1768976416), 
    IRC("IRC", 1769104160), 
    IRCS("IRCS", 1769104243), 
    LDAP("LDAP", 1818517872), 
    LDAPS("LDAPS", 1818521715), 
    NNTP("NNTP", 1852732528), 
    NNTPS("NNTPS", 1853124723), 
    POP3("POP3", 1886351411), 
    POP3S("POP3S", 1886351475), 
    RTSP("RTSP", 1920234352), 
    RTSPProxy("RTSPProxy", 1920234360), 
    SMB("SMB", 1936548384), 
    SMTP("SMTP", 1936553072), 
    SOCKS("SOCKS", 1936685088), 
    SSH("SSH", 1936943136), 
    SVN("SVN", 1937141280), 
    Telnet("Telnet", 1952803950), 
    TelnetS("TelnetS", 1952803955);
    
    private final String symbol;
    private final int value;
    
    private OSXKeychainProtocolType(final String sym, final int val) {
        this.symbol = sym;
        this.value = val;
    }
    
    public int getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.symbol;
    }
}
