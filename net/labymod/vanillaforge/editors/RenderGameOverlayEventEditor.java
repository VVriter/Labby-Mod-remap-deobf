//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import net.minecraftforge.client.event.*;
import org.objectweb.asm.*;

public class RenderGameOverlayEventEditor extends ClassEditor
{
    private boolean initEdited;
    
    public RenderGameOverlayEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, String desc, final String signature, final String[] exceptions) {
        if (name.equals("<init>") && !this.initEdited) {
            desc = "(FL" + Names.SCALED_RESOLUTION.getName() + ";)V";
            final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            this.initEdited = true;
            return new MethodVisitor(262144, mv) {
                public void visitInsn(final int i) {
                    if (i == 177) {
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitVarInsn(25, 2);
                        this.mv.visitFieldInsn(181, Type.getInternalName((Class)RenderGameOverlayEvent.class), "resolution", "L" + Names.SCALED_RESOLUTION.getName() + ";");
                    }
                    super.visitInsn(i);
                }
                
                public void visitEnd() {
                    super.visitEnd();
                    final MethodVisitor mv = RenderGameOverlayEventEditor.this.visitMethod(1, "<init>", "()V", null, null);
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
                    mv.visitLocalVariable("this", "Lnet/minecraftforge/client/event/RenderGameOverlayEvent;", (String)null, l0, l3, 0);
                    mv.visitMaxs(1, 1);
                    mv.visitEnd();
                }
            };
        }
        return super.visitMethod(access, name, desc, signature, exceptions);
    }
    
    public void visitSource(final String s, final String s1) {
        super.visitSource(s, s1);
        this.visitField(17, "resolution", "L" + Names.SCALED_RESOLUTION.getName() + ";", (String)null, (Object)null).visitEnd();
    }
}
