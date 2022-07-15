//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;
import net.labymod.core.asm.*;
import org.objectweb.asm.*;

public class CapeImageBufferVisitor extends ClassEditor
{
    private String abstractClientPlayerName;
    private String resourceLocationName;
    
    public CapeImageBufferVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR);
        this.abstractClientPlayerName = (LabyModCoreMod.isObfuscated() ? "bet" : "net/minecraft/client/entity/AbstractClientPlayer");
        this.resourceLocationName = LabyModTransformer.getMappingImplementation().getResourceLocationName();
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new MethodVisitor(262144, mv) {
            public void visitMethodInsn(int opcode, String owner, final String name, String desc, final boolean itf) {
                if (name.equals("parseCape")) {
                    owner = "CapeUtils";
                }
                if (name.equals("setLocationOfCape")) {
                    opcode = 182;
                    owner = CapeImageBufferVisitor.this.abstractClientPlayerName;
                    desc = "(L" + CapeImageBufferVisitor.this.resourceLocationName + ";)V";
                }
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        };
    }
}
