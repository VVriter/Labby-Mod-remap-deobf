//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;
import net.labymod.main.*;

public class ItemRendererVisitor extends ClassEditor
{
    private static final boolean MC18;
    private String transformFirstPersonItemName;
    private String renderItemInFirstPersonName;
    private String itemStackName;
    private String itemRendererName;
    private String itemToRenderName;
    private String pushMatrixName;
    
    public ItemRendererVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.transformFirstPersonItemName = LabyModTransformer.getMappingImplementation().getTransformFirstPersonItemName();
        this.renderItemInFirstPersonName = LabyModTransformer.getMappingImplementation().getRenderItemInFirstPersonName();
        this.itemStackName = LabyModTransformer.getMappingImplementation().getItemStackName();
        this.itemRendererName = LabyModTransformer.getMappingImplementation().getItemRendererName();
        this.itemToRenderName = LabyModTransformer.getMappingImplementation().getItemToRenderName();
        this.pushMatrixName = LabyModTransformer.getMappingImplementation().getPushMatrixName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        Label_0073: {
            if (name.equals(this.transformFirstPersonItemName)) {
                if (ItemRendererVisitor.MC18) {
                    if (!desc.equals("(FF)V")) {
                        break Label_0073;
                    }
                }
                else if (!desc.startsWith("(L") || !desc.endsWith(";F)V")) {
                    break Label_0073;
                }
                return new MethodVisitor(262144, mv) {
                    public void visitCode() {
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, ItemRendererVisitor.this.itemRendererName, ItemRendererVisitor.this.itemToRenderName, "L" + ItemRendererVisitor.this.itemStackName + ";");
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "transformFirstPersonItem", "(L" + ItemRendererVisitor.this.itemStackName + ";)V", false);
                        super.visitCode();
                    }
                };
            }
        }
        if (name.equals(this.renderItemInFirstPersonName)) {
            if (ItemRendererVisitor.MC18) {
                if (!desc.equals("(F)V")) {
                    return mv;
                }
            }
            else if (!desc.endsWith("(;F)V") || !desc.contains(";FFL")) {
                return mv;
            }
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                    if (opcode == 184 && name.equals(ItemRendererVisitor.this.pushMatrixName)) {
                        this.mv.visitVarInsn(25, 0);
                        this.mv.visitFieldInsn(180, ItemRendererVisitor.this.itemRendererName, ItemRendererVisitor.this.itemToRenderName, "L" + ItemRendererVisitor.this.itemStackName + ";");
                        this.mv.visitIntInsn(23, 4);
                        this.mv.visitMethodInsn(184, "BytecodeMethods", "renderItemInFirstPerson", "(L" + ItemRendererVisitor.this.itemStackName + ";F)F", false);
                        this.mv.visitIntInsn(56, 6);
                    }
                }
                
                public void visitInsn(final int opcode) {
                    if (opcode == 11 && ItemRendererVisitor.MC18) {
                        this.mv.visitIntInsn(23, 6);
                    }
                    else {
                        super.visitInsn(opcode);
                    }
                }
            };
        }
        return mv;
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
}
