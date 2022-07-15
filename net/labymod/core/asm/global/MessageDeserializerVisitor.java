//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import org.objectweb.asm.*;

public class MessageDeserializerVisitor extends ClassEditor
{
    public MessageDeserializerVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (desc.endsWith(";Ljava/util/List;)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean add = false;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 185 && owner.equals("java/util/List") && name.equals("add") && desc.equals("(Ljava/lang/Object;)Z")) {
                        this.add = true;
                    }
                }
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    if (opcode == 178 && this.add) {
                        this.add = false;
                        super.visitIntInsn(25, 6);
                        super.visitMethodInsn(184, "BytecodeMethods", "onIncomingPacket", "(Ljava/lang/Object;)V", false);
                    }
                    super.visitFieldInsn(opcode, owner, name, desc);
                }
            };
        }
        return mv;
    }
}
