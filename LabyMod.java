//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

public class LabyMod
{
    private static LabyMod instance;
    private EventCaller eventCaller;
    
    public void init() {
        LabyMod.instance = this;
        this.eventCaller = new EventCaller();
    }
    
    public EventCaller getEventCaller() {
        return this.eventCaller;
    }
    
    public static LabyMod instance() {
        return LabyMod.instance;
    }
}
