//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.api.protocol.cinematic;

public enum EnumPositionValue
{
    X((IPositionValueAdapter)new IPositionValueAdapter() {
        @Override
        public double get(final Position position) {
            return position.getX();
        }
        
        @Override
        public void set(final Position position, final double value) {
            position.setX(value);
        }
    }), 
    Y((IPositionValueAdapter)new IPositionValueAdapter() {
        @Override
        public double get(final Position position) {
            return position.getY();
        }
        
        @Override
        public void set(final Position position, final double value) {
            position.setY(value);
        }
    }), 
    Z((IPositionValueAdapter)new IPositionValueAdapter() {
        @Override
        public double get(final Position position) {
            return position.getZ();
        }
        
        @Override
        public void set(final Position position, final double value) {
            position.setZ(value);
        }
    }), 
    YAW((IPositionValueAdapter)new IPositionValueAdapter() {
        @Override
        public double get(final Position position) {
            return position.getYaw();
        }
        
        @Override
        public void set(final Position position, final double value) {
            position.setYaw(value);
        }
    }), 
    PITCH((IPositionValueAdapter)new IPositionValueAdapter() {
        @Override
        public double get(final Position position) {
            return position.getPitch();
        }
        
        @Override
        public void set(final Position position, final double value) {
            position.setPitch(value);
        }
    }), 
    TILT((IPositionValueAdapter)new IPositionValueAdapter() {
        @Override
        public double get(final Position position) {
            return position.getTilt();
        }
        
        @Override
        public void set(final Position position, final double value) {
            position.setTilt(value);
        }
    });
    
    private final IPositionValueAdapter adapter;
    
    private EnumPositionValue(final IPositionValueAdapter adapter) {
        this.adapter = adapter;
    }
    
    public IPositionValueAdapter getAdapter() {
        return this.adapter;
    }
}
