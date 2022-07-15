//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.global;

import net.labymod.core.asm.*;
import org.objectweb.asm.tree.*;
import java.util.*;
import net.labymod.main.*;

public class NetHandlerPlayClientVisitor extends ClassEditor
{
    private static final boolean MC_18;
    private String packetCustomPayloadName;
    private String handleCustomPayloadName;
    private String getChannelNameName;
    private String getBufferDataName;
    private String packetBufferName;
    private String handleResourcePackName;
    private String packetResourcePackSendName;
    private String packetPlayerListItemName;
    private String networkPlayerInfoName;
    
    public NetHandlerPlayClientVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_NODE);
        this.packetCustomPayloadName = LabyModTransformer.getMappingImplementation().getCustomPayLoadPacketName();
        this.handleCustomPayloadName = LabyModTransformer.getMappingImplementation().getHandleCustomPayLoadName();
        this.getChannelNameName = LabyModTransformer.getMappingImplementation().getChannelNameName();
        this.getBufferDataName = LabyModTransformer.getMappingImplementation().getBufferDataName();
        this.packetBufferName = LabyModTransformer.getMappingImplementation().getPacketBufferName();
        this.handleResourcePackName = LabyModTransformer.getMappingImplementation().getHandleResourcePackName();
        this.packetResourcePackSendName = LabyModTransformer.getMappingImplementation().getPacketResourcePackSendName();
        this.packetPlayerListItemName = LabyModTransformer.getMappingImplementation().getPacketPlayerListItemName();
        this.networkPlayerInfoName = LabyModTransformer.getMappingImplementation().getNetworkPlayerInfoName();
    }
    
    public void accept(final String name, final ClassNode node) {
        for (final MethodNode methodNode : node.methods) {
            if (methodNode.name.equals(this.handleCustomPayloadName) && methodNode.desc.equals("(L" + this.packetCustomPayloadName + ";)V")) {
                AbstractInsnNode returnNode = null;
                for (final AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode instanceof InsnNode && ((InsnNode)insnNode).getOpcode() == 177) {
                        returnNode = insnNode;
                        break;
                    }
                }
                if (returnNode == null) {
                    continue;
                }
                final InsnList insnList = new InsnList();
                insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                insnList.add((AbstractInsnNode)new MethodInsnNode(182, this.packetCustomPayloadName, this.getChannelNameName, "()Ljava/lang/String;", false));
                insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                insnList.add((AbstractInsnNode)new MethodInsnNode(182, this.packetCustomPayloadName, this.getBufferDataName, "()L" + this.packetBufferName + ";", false));
                insnList.add((AbstractInsnNode)new MethodInsnNode(184, "BytecodeMethods", "onReceivePluginMessage", "(Ljava/lang/String;L" + this.packetBufferName + ";)V", false));
                methodNode.instructions.insertBefore(returnNode, insnList);
            }
            if (NetHandlerPlayClientVisitor.MC_18 && methodNode.name.equals(this.handleResourcePackName) && methodNode.desc.equals("(L" + this.packetResourcePackSendName + ";)V")) {
                MethodInsnNode methodInsnNode = null;
                for (final AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode instanceof MethodInsnNode && ((MethodInsnNode)insnNode).getOpcode() == 182) {
                        methodInsnNode = (MethodInsnNode)insnNode;
                        break;
                    }
                }
                if (methodInsnNode == null) {
                    continue;
                }
                methodNode.instructions.insert((AbstractInsnNode)methodInsnNode, (AbstractInsnNode)new MethodInsnNode(184, "BytecodeMethods", "modifyResourcePackURL", "(Ljava/lang/String;)Ljava/lang/String;", false));
            }
            if (methodNode.desc.equals("(L" + this.packetPlayerListItemName + ";)V")) {
                AbstractInsnNode targetNode = null;
                for (final AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode.getOpcode() == 177) {
                        targetNode = insnNode;
                        break;
                    }
                }
                if (targetNode != null) {
                    final InsnList insnList = new InsnList();
                    insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                    insnList.add((AbstractInsnNode)new MethodInsnNode(184, "BytecodeMethods", "handlePlayerListItem", "(Ljava/lang/Object;)V", false));
                    methodNode.instructions.insertBefore(targetNode, insnList);
                }
            }
            if (methodNode.desc.equals("(Ljava/util/UUID;)L" + this.networkPlayerInfoName + ";")) {
                AbstractInsnNode targetNode = null;
                for (final AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode.getOpcode() == 176) {
                        targetNode = insnNode;
                        break;
                    }
                }
                if (targetNode == null) {
                    continue;
                }
                final InsnList insnList = new InsnList();
                insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
                insnList.add((AbstractInsnNode)new MethodInsnNode(184, "BytecodeMethods", "getCachedPlayerInfo", "(L" + this.networkPlayerInfoName + ";Ljava/util/UUID;)L" + this.networkPlayerInfoName + ";", false));
                methodNode.instructions.insertBefore(targetNode, insnList);
            }
        }
    }
    
    static {
        MC_18 = Source.ABOUT_MC_VERSION.startsWith("1.8");
    }
}
