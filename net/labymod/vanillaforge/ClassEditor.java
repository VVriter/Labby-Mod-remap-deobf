//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge;

import org.objectweb.asm.tree.*;
import org.objectweb.asm.*;

public abstract class ClassEditor extends ClassVisitor
{
    private ClassEditorType type;
    
    public ClassEditor(final ClassEditorType type) {
        super(262144);
        this.type = type;
    }
    
    public void accept(final String name, final ClassNode node) {
    }
    
    public void accept(final String name, final ClassVisitor visitor) {
        this.cv = visitor;
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
    
    public ClassEditorType getType() {
        return this.type;
    }
    
    public enum ClassEditorType
    {
        CLASS_VISITOR, 
        CLASS_NODE;
    }
}
