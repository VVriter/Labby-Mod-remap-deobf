//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.vanillaforge.editors;

import org.objectweb.asm.*;
import net.labymod.vanillaforge.*;
import net.minecraftforge.client.event.*;
import org.objectweb.asm.tree.*;
import java.util.*;

public class EntityRendererEditor extends ClassEditor
{
    private String renderWorldLastEventName;
    
    public EntityRendererEditor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
        this.renderWorldLastEventName = Type.getInternalName((Class)RenderWorldLastEvent.class);
    }
    
    public void accept(final String name, final ClassNode node) {
        for (final Object methodObj : node.methods) {
            final MethodNode method = (MethodNode)methodObj;
            if (method.desc.equals("(FJ)V") && method.name.equals(Names.UPDATE_CAMERA_AND_RENDER.getName())) {
                for (final AbstractInsnNode insnNode : method.instructions.toArray()) {
                    if (insnNode instanceof MethodInsnNode) {
                        final MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                        if (methodInsnNode.owner.equals(Names.GUI_SCREEN.getName(true)) && methodInsnNode.name.equals(Names.DRAW_SCREEN.getName(true))) {
                            if (methodInsnNode.desc.equals("(IIF)V")) {
                                final String guiScreenDrawScreenPostEvent = Type.getInternalName((Class)GuiScreenEvent.DrawScreenEvent.Post.class);
                                final InsnList insnList = new InsnList();
                                insnList.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
                                insnList.add((AbstractInsnNode)new TypeInsnNode(187, guiScreenDrawScreenPostEvent));
                                insnList.add((AbstractInsnNode)new InsnNode(89));
                                insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
                                insnList.add((AbstractInsnNode)new FieldInsnNode(180, Names.ENTITY_RENDERER.getName(true), Names.ENTITY_RENDERER_MINECRAFT_FIELD.getName(), "L" + Names.MINECRAFT_CLASS.getName(true) + ";"));
                                insnList.add((AbstractInsnNode)new FieldInsnNode(180, Names.MINECRAFT_CLASS.getName(true), Names.CURRENT_SCREEN.getName(), "L" + Names.GUI_SCREEN.getName(true) + ";"));
                                insnList.add((AbstractInsnNode)new VarInsnNode(21, 8));
                                insnList.add((AbstractInsnNode)new VarInsnNode(21, 9));
                                insnList.add((AbstractInsnNode)new VarInsnNode(23, 1));
                                insnList.add((AbstractInsnNode)new MethodInsnNode(183, guiScreenDrawScreenPostEvent, "<init>", "(L" + Names.GUI_SCREEN.getName(true) + ";IIF)V", false));
                                insnList.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                                insnList.add((AbstractInsnNode)new InsnNode(87));
                                method.instructions.insert((AbstractInsnNode)methodInsnNode, insnList);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                if (!method.desc.equals("(IFJ)V") || !method.name.equals(Names.RENDER_WORLD_PASS.getName())) {
                    continue;
                }
                String lastString = null;
                boolean isOFClass = false;
                for (final AbstractInsnNode insnNode2 : method.instructions.toArray()) {
                    if (insnNode2 instanceof LdcInsnNode) {
                        final LdcInsnNode ldcInsnNode = (LdcInsnNode)insnNode2;
                        if (ldcInsnNode.cst instanceof String) {
                            lastString = (String)ldcInsnNode.cst;
                        }
                    }
                    else {
                        if (!isOFClass && insnNode2 instanceof MethodInsnNode) {
                            final MethodInsnNode methodInsnNodeOptifine = (MethodInsnNode)insnNode2;
                            if (methodInsnNodeOptifine.name.equals("isShaders")) {
                                isOFClass = true;
                            }
                        }
                        if (insnNode2 instanceof MethodInsnNode) {
                            final MethodInsnNode methodInsnNode2 = (MethodInsnNode)insnNode2;
                            if (lastString != null && lastString.equals("hand") && methodInsnNode2.name.equals(Names.END_START_SECTION.getName())) {
                                if (methodInsnNode2.desc.equals("(Ljava/lang/String;)V")) {
                                    final InsnList insnList2 = new InsnList();
                                    insnList2.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/fml/common/eventhandler/EventBus;"));
                                    insnList2.add((AbstractInsnNode)new TypeInsnNode(187, this.renderWorldLastEventName));
                                    insnList2.add((AbstractInsnNode)new InsnNode(89));
                                    insnList2.add((AbstractInsnNode)new VarInsnNode(25, isOFClass ? 6 : 5));
                                    insnList2.add((AbstractInsnNode)new VarInsnNode(23, 2));
                                    insnList2.add((AbstractInsnNode)new MethodInsnNode(183, this.renderWorldLastEventName, "<init>", "(L" + Names.RENDER_GLOBAL.getName() + ";F)V", false));
                                    insnList2.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/fml/common/eventhandler/EventBus", "post", "(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", false));
                                    insnList2.add((AbstractInsnNode)new InsnNode(87));
                                    method.instructions.insert(insnNode2, insnList2);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
