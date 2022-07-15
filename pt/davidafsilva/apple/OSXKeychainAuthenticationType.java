//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package pt.davidafsilva.apple;

public enum OSXKeychainAuthenticationType
{
    Any("Any", 0), 
    DPA("DPA", 1633775716), 
    Default("Default", 1953261156), 
    HTMLForm("HTMLForm", 1836216166), 
    HTTPBasic("HTTPBasic", 1886680168), 
    HTTPDigest("HTTPDigest", 1685353576), 
    MSN("MSN", 1634628461), 
    NTLM("NTLM", 1835824238), 
    RPA("RPA", 1633775730);
    
    private final String symbol;
    private final int value;
    
    private OSXKeychainAuthenticationType(final String sym, final int val) {
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
