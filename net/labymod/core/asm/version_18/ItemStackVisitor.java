//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class ItemStackVisitor extends ClassEditor
{
    private String itemStackName;
    private String getIsItemStackEqualName;
    
    public ItemStackVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.itemStackName = LabyModTransformer.getMappingImplementation().getItemStackName();
        this.getIsItemStackEqualName = (LabyModCoreMod.isObfuscated() ? "c" : "getIsItemStackEqual");
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.getIsItemStackEqualName) && desc.equals("(L" + this.itemStackName + ";)Z")) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 183) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(25, 1);
                        super.visitMethodInsn(184, "BytecodeMethods", "isItemStackEqual", "(ZL" + ItemStackVisitor.this.itemStackName + ";L" + ItemStackVisitor.this.itemStackName + ";)Z", false);
                    }
                }
            };
        }
        return mv;
    }
}
