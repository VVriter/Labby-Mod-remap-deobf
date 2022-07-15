//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class GuiScreenDrawScreenPostEventEditor extends ClassEditor
{
    public GuiScreenDrawScreenPostEventEditor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
    }
    
    public void accept(final String name, final ClassNode node) {
        for (final Object methodObj : node.methods) {
            final MethodNode method = (MethodNode)methodObj;
            if (method.name.equals("<init>") && !method.desc.equals("()V")) {
                method.desc = "(L" + Names.GUI_SCREEN.getName(true) + ";IIF)V";
                for (final AbstractInsnNode insnNode : method.instructions.toArray()) {
                    if (insnNode instanceof MethodInsnNode) {
                        final MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                        if (methodInsnNode.getOpcode() == 183) {
                            methodInsnNode.desc = "(L" + Names.GUI_SCREEN.getName(true) + ";IIF)V";
                            for (final AbstractInsnNode abstractInsnNode : method.instructions.toArray()) {
                                if (abstractInsnNode instanceof VarInsnNode) {
                                    if (((VarInsnNode)abstractInsnNode).var != 0) {
                                        ++((VarInsnNode)abstractInsnNode).var;
                                    }
                                }
                            }
                            method.instructions.insertBefore(insnNode.getPrevious().getPrevious().getPrevious(), (AbstractInsnNode)new VarInsnNode(25, 1));
                            break;
                        }
                    }
                }
                break;
            }
        }
    }
    
    private void print(final AbstractInsnNode abstractInsnNode) {
        System.out.println(abstractInsnNode.getClass().getName());
        if (!(abstractInsnNode instanceof VarInsnNode)) {
            return;
        }
        final VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode;
        System.out.println(varInsnNode.getOpcode() + " " + varInsnNode.var);
    }
}
