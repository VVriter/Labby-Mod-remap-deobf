//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_112;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class RenderLivingBaseVisitor extends ClassEditor
{
    private String entityLivingBase;
    private boolean injectedMethod;
    
    public RenderLivingBaseVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.injectedMethod = false;
        this.entityLivingBase = LabyModTransformer.getMappingImplementation().getEntityLivingBaseName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!this.injectedMethod && desc.equals("(L" + this.entityLivingBase + ";DDDFF)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean injected = false;
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    if (!this.injected && opcode == 181) {
                        this.injected = true;
                        RenderLivingBaseVisitor.this.injectedMethod = true;
                        super.visitMethodInsn(184, "BytecodeMethods", "onPreRenderLivingBase", "()V", false);
                    }
                }
            };
        }
        return mv;
    }
}
