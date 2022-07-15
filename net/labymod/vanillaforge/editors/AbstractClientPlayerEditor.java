//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import net.labymod.vanillaforge.*;
import net.minecraftforge.client.event.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class AbstractClientPlayerEditor extends ClassEditor
{
    public AbstractClientPlayerEditor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
    }
    
    public void accept(final String name, final ClassNode node) {
        for (final Object methodObj : node.methods) {
            final MethodNode method = (MethodNode)methodObj;
            boolean isOFClass = false;
            if (method.name.equals(Names.GET_FOV_MODIFIER.getName()) && method.desc.equals("()F")) {
                AbstractInsnNode returnNode = null;
                for (final AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() == 174) {
                        if (returnNode != null) {
                            isOFClass = true;
                        }
                        returnNode = instruction;
                    }
                }
                final String fovUpdateEvent = Type.getInternalName((Class)FOVUpdateEvent.class);
                final InsnList insertBefore = new InsnList();
                insertBefore.add((AbstractInsnNode)new TypeInsnNode(187, fovUpdateEvent));
                insertBefore.add((AbstractInsnNode)new InsnNode(89));
                insertBefore.add((AbstractInsnNode)new VarInsnNode(25, 0));
                final InsnList insertList = new InsnList();
                insertList.add((AbstractInsnNode)new MethodInsnNode(183, fovUpdateEvent, "<init>", "(L" + Names.ENTITY_PLAYER.getName() + ";F)V", false));
                insertList.add((AbstractInsnNode)new VarInsnNode(58, isOFClass ? 6 : 5));
                insertList.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
                insertList.add((AbstractInsnNode)new VarInsnNode(25, isOFClass ? 6 : 5));
                insertList.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                insertList.add((AbstractInsnNode)new VarInsnNode(25, isOFClass ? 6 : 5));
                insertList.add((AbstractInsnNode)new FieldInsnNode(180, fovUpdateEvent, "newfov", "F"));
                method.instructions.insertBefore(returnNode.getPrevious(), insertBefore);
                method.instructions.insertBefore(returnNode, insertList);
                break;
            }
        }
    }
}
