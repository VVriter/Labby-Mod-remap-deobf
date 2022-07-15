//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core.asm.version_18;

import net.labymod.core.asm.global.*;

public class CapeUtilsVisitor extends ClassEditor
{
    public CapeUtilsVisitor() {
        super(ClassEditor.ClassEditorType.CLASS_VISITOR_AND_REMAPPER);
    }
    
    public String visitMapping(final String typeName) {
        if (typeName.equals("CapeUtils$1")) {
            return "net/labymod/core_implementation/mc18/of/CapeImageBuffer";
        }
        return super.visitMapping(typeName);
    }
}
