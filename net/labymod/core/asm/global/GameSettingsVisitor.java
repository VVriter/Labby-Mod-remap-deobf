//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class GameSettingsVisitor extends ClassEditor
{
    private String saveOptionsName;
    
    public GameSettingsVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.saveOptionsName = LabyModTransformer.getMappingImplementation().getSaveOptionsName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.saveOptionsName) && desc.equals("()V")) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    if ((opcode == 182 && owner.equals("java/io/PrintWriter") && name.equals("close")) || (opcode == 184 && owner.equals("org/apache/commons/io/IOUtils") && name.equals("closeQuietly"))) {
                        super.visitVarInsn(25, 1);
                        super.visitMethodInsn(184, "BytecodeMethods", "writeOptions", "(Ljava/io/PrintWriter;)V", false);
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 183 && owner.equals("java/io/PrintWriter") && name.equals("<init>") && desc.equals("(Ljava/io/Writer;)V")) {
                        super.visitMethodInsn(184, "BytecodeMethods", "createWriter", "(L" + owner + ";)L" + owner + ";", false);
                    }
                }
            };
        }
        return mv;
    }
}
