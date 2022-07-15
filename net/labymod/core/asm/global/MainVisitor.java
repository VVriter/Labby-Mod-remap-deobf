//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import org.objectweb.asm.*;
import net.labymod.core.asm.*;

public class MainVisitor extends ClassEditor
{
    public MainVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("main")) {
            return new MethodVisitor(262144, mv) {
                private String lastInvokeOwner;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 182) {
                        this.lastInvokeOwner = owner;
                    }
                }
                
                public void visitInsn(final int opcode) {
                    super.visitInsn(opcode);
                    if (opcode == 177 && this.lastInvokeOwner != null) {
                        LabyModTransformer.resolveMinecraftClass(this.lastInvokeOwner);
                    }
                }
            };
        }
        return mv;
    }
}
