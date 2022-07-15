//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class EntityPlayerVisitor extends ClassEditor
{
    private String entityName;
    private String attackTargetEntityWithCurrentItemName;
    
    public EntityPlayerVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.entityName = LabyModTransformer.getMappingImplementation().getEntityClassName();
        this.attackTargetEntityWithCurrentItemName = (LabyModCoreMod.isObfuscated() ? "f" : "attackTargetEntityWithCurrentItem");
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.attackTargetEntityWithCurrentItemName) && desc.equals("(L" + this.entityName + ";)V")) {
            return new MethodVisitor(262144, mv) {
                private int invoked = 0;
                private boolean injected = false;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && !this.injected && this.invoked < 2 && desc.endsWith(";)I")) {
                        ++this.invoked;
                    }
                }
                
                public void visitVarInsn(final int opcode, final int var) {
                    super.visitVarInsn(opcode, var);
                    if (opcode == 54 && this.invoked == 2 && !this.injected) {
                        this.injected = true;
                        super.visitIntInsn(21, 5);
                        super.visitMethodInsn(184, "BytecodeMethods", "modifyCriticalHit", "(Z)Z", false);
                        super.visitIntInsn(54, 5);
                        super.visitIntInsn(23, 4);
                        super.visitMethodInsn(184, "BytecodeMethods", "modifyEnchantmentCritical", "(F)F", false);
                        super.visitIntInsn(56, 4);
                    }
                }
            };
        }
        return mv;
    }
}
