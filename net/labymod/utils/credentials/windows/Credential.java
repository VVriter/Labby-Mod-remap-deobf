//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.credentials.windows;

import com.sun.jna.*;
import java.util.*;

public class Credential extends Structure
{
    public int Flags;
    public int Type;
    public Pointer TargetName;
    public Pointer Comment;
    public Pointer LastWritten;
    public int CredentialBlobSize;
    public Pointer CredentialBlob;
    public int Persist;
    public int AttributeCount;
    public Pointer Attributes;
    public Pointer TargetAlias;
    public Pointer UserName;
    
    protected List getFieldOrder() {
        return Arrays.asList("Flags", "Type", "TargetName", "Comment", "LastWritten", "CredentialBlobSize", "CredentialBlob", "Persist", "AttributeCount", "Attributes", "TargetAlias", "UserName");
    }
    
    public Credential(final Pointer p) {
        super(p);
    }
    
    public Credential() {
    }
}
