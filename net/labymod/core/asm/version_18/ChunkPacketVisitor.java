//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class ChunkPacketVisitor extends ClassEditor
{
    private String readPacketDataName;
    private String packetBufferName;
    
    public ChunkPacketVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.readPacketDataName = LabyModTransformer.getMappingImplementation().getReadPacketDataName();
        this.packetBufferName = LabyModTransformer.getMappingImplementation().getPacketBufferName();
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.readPacketDataName) && desc.equals("(L" + this.packetBufferName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int opcode) {
                    if (opcode == 177) {
                        super.visitIntInsn(25, 1);
                        super.visitIntInsn(25, 0);
                        super.visitMethodInsn(184, "BytecodeMethods", "onReceiveChunkData", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
