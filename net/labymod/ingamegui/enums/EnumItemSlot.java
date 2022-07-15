//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.enums;

import net.labymod.core.*;

public enum EnumItemSlot
{
    NONE {
        @Override
        public double getX(final double width) {
            return 0.0;
        }
        
        @Override
        public double getY(final double height) {
            return 0.0;
        }
        
        @Override
        public boolean isRightbound() {
            return false;
        }
    }, 
    LEFT_TOP {
        @Override
        public double getX(final double width) {
            return width / 2.0 - 110.0 - this.getSecondHandShift(false);
        }
        
        @Override
        public double getY(final double height) {
            return height - 50.0;
        }
        
        @Override
        public boolean isRightbound() {
            return true;
        }
        
        @Override
        public EnumItemSlot[] getAlternativeSlots() {
            return new EnumItemSlot[] { EnumItemSlot$2.LEFT_CENTER, EnumItemSlot$2.LEFT_BOTTOM };
        }
    }, 
    LEFT_CENTER {
        @Override
        public double getX(final double width) {
            return width / 2.0 - 110.0 - this.getSecondHandShift(false);
        }
        
        @Override
        public double getY(final double height) {
            return height - 34.0;
        }
        
        @Override
        public boolean isRightbound() {
            return true;
        }
        
        @Override
        public EnumItemSlot[] getAlternativeSlots() {
            return new EnumItemSlot[] { EnumItemSlot$3.LEFT_BOTTOM };
        }
    }, 
    LEFT_BOTTOM {
        @Override
        public double getX(final double width) {
            return width / 2.0 - 110.0 - this.getSecondHandShift(false);
        }
        
        @Override
        public double getY(final double height) {
            return height - 18.0;
        }
        
        @Override
        public boolean isRightbound() {
            return true;
        }
    }, 
    RIGHT_TOP {
        @Override
        public double getX(final double width) {
            return width / 2.0 + 96.0 - this.getSecondHandShift(true);
        }
        
        @Override
        public double getY(final double height) {
            return height - 50.0;
        }
        
        @Override
        public boolean isRightbound() {
            return false;
        }
        
        @Override
        public EnumItemSlot[] getAlternativeSlots() {
            return new EnumItemSlot[] { EnumItemSlot$5.RIGHT_CENTER, EnumItemSlot$5.RIGHT_BOTTOM };
        }
    }, 
    RIGHT_CENTER {
        @Override
        public double getX(final double width) {
            return width / 2.0 + 96.0 - this.getSecondHandShift(true);
        }
        
        @Override
        public double getY(final double height) {
            return height - 34.0;
        }
        
        @Override
        public boolean isRightbound() {
            return false;
        }
        
        @Override
        public EnumItemSlot[] getAlternativeSlots() {
            return new EnumItemSlot[] { EnumItemSlot$6.RIGHT_BOTTOM };
        }
    }, 
    RIGHT_BOTTOM {
        @Override
        public double getX(final double width) {
            return width / 2.0 + 96.0 - this.getSecondHandShift(true);
        }
        
        @Override
        public double getY(final double height) {
            return height - 18.0;
        }
        
        @Override
        public boolean isRightbound() {
            return false;
        }
    };
    
    public EnumItemSlot[] getAlternativeSlots() {
        return null;
    }
    
    public abstract double getX(final double p0);
    
    public abstract double getY(final double p0);
    
    public abstract boolean isRightbound();
    
    public int getSecondHandShift(final boolean right) {
        final aip itemStack = LabyModCore.getMinecraft().getOffHandItem();
        if (LabyModCore.getMinecraft().isUsingLeftHand() != right) {
            return 0;
        }
        return (itemStack == null || ain.a(itemStack.c()) == 0) ? 0 : (right ? -30 : 30);
    }
}
