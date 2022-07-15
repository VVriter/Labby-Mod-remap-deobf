//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.minecraftforge.client.event.*;
import net.labymod.vanillaforge.*;
import org.objectweb.asm.*;

public class RenderWorldLastEventEditor extends ClassEditor
{
    private String eventName;
    private String renderGlobalName;
    private boolean replacedInit;
    
    public RenderWorldLastEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.eventName = Type.getInternalName((Class)RenderWorldLastEvent.class);
        this.renderGlobalName = Names.RENDER_GLOBAL.getName(true);
    }
    
    public void visitSource(final String s, final String s1) {
        super.visitSource(s, s1);
        this.visitField(1, "context", "L" + Names.RENDER_GLOBAL.getName() + ";", (String)null, (Object)null).visitEnd();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, String desc, final String signature, final String[] exceptions) {
        if (name.equals("<init>") && !this.replacedInit) {
            desc = "(L" + Names.RENDER_GLOBAL.getName() + ";F)V";
            final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int i) {
                    if (i == 177) {
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitVarInsn(25, 1);
                        this.mv.visitFieldInsn(181, RenderWorldLastEventEditor.this.eventName, "context", "L" + Names.RENDER_GLOBAL.getName() + ";");
                    }
                    super.visitInsn(i);
                }
                
                public void visitVarInsn(final int opcode, int var) {
                    if (opcode == 23 && var == 1) {
                        var = 2;
                    }
                    super.visitVarInsn(opcode, var);
                }
                
                public void visitEnd() {
                    super.visitEnd();
                    RenderWorldLastEventEditor.this.replacedInit = true;
                    final MethodVisitor mv = RenderWorldLastEventEditor.this.visitMethod(1, "<init>", "()V", null, null);
                    mv.visitCode();
                    final Label l0 = new Label();
                    mv.visitLabel(l0);
                    mv.visitLineNumber(13, l0);
                    mv.visitVarInsn(25, 0);
                    mv.visitMethodInsn(183, "net/minecraftforge/fml/common/eventhandler/Event", "<init>", "()V", false);
                    final Label l2 = new Label();
                    mv.visitLabel(l2);
                    mv.visitLineNumber(15, l2);
                    mv.visitInsn(177);
                    final Label l3 = new Label();
                    mv.visitLabel(l3);
                    mv.visitLocalVariable("this", "L" + RenderWorldLastEventEditor.this.eventName + ";", (String)null, l0, l3, 0);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
            };
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
}
