//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.*;
import net.labymod.main.*;

public class ItemVisitor extends ClassEditor
{
    private static final boolean MC18;
    private String shouldCauseReequipAnimationName;
    
    public ItemVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR_AND_REMAPPER);
        this.shouldCauseReequipAnimationName = "shouldCauseReequipAnimation";
    }
    
    public String visitMapping(final String typeName) {
        if (typeName.equals(LabyModTransformer.getMappingImplementation().getItemBucketName())) {
            return "net/labymod/core_implementation/" + (LabyModTransformer.getVersion().startsWith("1.8") ? "mc18" : "mc112") + "/item/ItemBucketCustom";
        }
        return super.visitMapping(typeName);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (ItemVisitor.MC18 && name.equals(this.shouldCauseReequipAnimationName)) {
            return new MethodVisitor(262144, mv) {
                public void visitMethodInsn(final int opcode, String owner, String name, final String desc, final boolean itf) {
                    if (opcode == 184) {
                        owner = "BytecodeMethods";
                        name = "shouldCancelReequipAnimation";
                    }
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            };
        }
        return mv;
    }
    
    static {
        MC18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
}
