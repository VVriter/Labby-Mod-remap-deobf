//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class EntityVisitor extends ClassEditor
{
    private String entityName;
    private String setDead;
    
    public EntityVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.entityName = LabyModTransformer.getMappingImplementation().getEntityClassName();
        this.setDead = LabyModTransformer.getMappingImplementation().getSetDeadName();
    }
    
    public void visitSource(final String s, final String s1) {
        super.visitSource(s, s1);
        this.visitField(1, "afecState", "Lnet/labymod/mojang/afec/EnumQueryState;", (String)null, (Object)null).visitEnd();
        this.visitField(1, "afecTimeoutTime", "J", (String)null, (Object)null).visitEnd();
        this.visitField(1, "afecId", "I", (String)null, (Object)null).visitEnd();
        this.visitField(1, "afecVisible", "Z", (String)null, (Object)null).visitEnd();
        this.visitField(1, "afecNameTag", "Z", (String)null, (Object)null).visitEnd();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.setDead) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    if (opcode == 181) {
                        super.visitVarInsn(25, 0);
                        super.visitMethodInsn(184, "BytecodeMethods", "onEntitySetDead", "(L" + EntityVisitor.this.entityName + ";)V", false);
                    }
                }
            };
        }
        return mv;
    }
}
