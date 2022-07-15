//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import org.objectweb.asm.*;
import net.labymod.core.asm.*;

public class GuiContainerVisitor extends ClassEditor
{
    private String itemStackName;
    private String mouseReleasedName;
    private String mouseClickedName;
    private String slotName;
    private String getStackName;
    private String guiContainerName;
    private String checkHotbarKeysName;
    
    public GuiContainerVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.itemStackName = LabyModTransformer.getMappingImplementation().getItemStackName();
        this.mouseReleasedName = LabyModTransformer.getMappingImplementation().getGuiContainerMouseReleasedName();
        this.mouseClickedName = LabyModTransformer.getMappingImplementation().getGuiScreenMouseClickedName();
        this.slotName = LabyModTransformer.getMappingImplementation().getSlotName();
        this.getStackName = LabyModTransformer.getMappingImplementation().getSlotGetStackName();
        this.guiContainerName = LabyModTransformer.getMappingImplementation().getGuiContainerName();
        this.checkHotbarKeysName = LabyModTransformer.getMappingImplementation().getCheckHotbarKeysName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.mouseReleasedName) && desc.equals("(III)V")) {
            return new MethodVisitor(262144, mv) {
                private boolean invokeStaticCalled = false;
                private boolean getFieldCalled = false;
                
                public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
                    super.visitFieldInsn(opcode, owner, name, desc);
                    this.getFieldCalled = (opcode == 180 && desc.equals("L" + GuiContainerVisitor.this.itemStackName + ";"));
                }
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (!(this.invokeStaticCalled = (opcode == 184))) {
                        this.getFieldCalled = false;
                    }
                }
                
                public void visitJumpInsn(final int opcode, final Label label) {
                    super.visitJumpInsn(opcode, label);
                    if (opcode != 153) {
                        this.invokeStaticCalled = false;
                        this.getFieldCalled = false;
                    }
                    if (opcode == 153 && this.invokeStaticCalled && this.getFieldCalled) {
                        this.invokeStaticCalled = false;
                        this.getFieldCalled = false;
                        super.visitIntInsn(25, (LabyModCoreMod.isObfuscated() || LabyModCoreMod.isForge()) ? 10 : 9);
                        super.visitMethodInsn(182, GuiContainerVisitor.this.slotName, GuiContainerVisitor.this.getStackName, "()L" + GuiContainerVisitor.this.itemStackName + ";", false);
                        super.visitMethodInsn(184, "BytecodeMethods", "allowedToShiftAllItems", "(L" + GuiContainerVisitor.this.itemStackName + ";)Z", false);
                        super.visitJumpInsn(153, label);
                    }
                }
            };
        }
        if (name.equals(this.mouseClickedName) && desc.equals("(III)V")) {
            return new MethodVisitor(262144, mv) {
                private int index = 0;
                private boolean locked = false;
                
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (owner.equals(GuiContainerVisitor.this.guiContainerName) && name.equals(GuiContainerVisitor.this.checkHotbarKeysName) && desc.equals("(I)Z")) {
                        this.locked = true;
                    }
                }
                
                public void visitInsn(final int opcode) {
                    if (opcode == 177 && this.index == 1 && !this.locked) {
                        super.visitVarInsn(25, 0);
                        super.visitVarInsn(21, 3);
                        super.visitIntInsn(16, 100);
                        super.visitInsn(100);
                        super.visitMethodInsn(183, GuiContainerVisitor.this.guiContainerName, GuiContainerVisitor.this.checkHotbarKeysName, "(I)Z", false);
                    }
                    else if (opcode == 177) {
                        ++this.index;
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return mv;
    }
}
