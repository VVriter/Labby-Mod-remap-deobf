//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main.update;

public class UpdateData
{
    private final String latestVersion;
    private final short[] latestVersionShort;
    private boolean updateAvailable;
    
    public UpdateData(final String latestVersion, final short[] latestVersionShort, final boolean updateAvailable) {
        this.latestVersion = latestVersion;
        this.latestVersionShort = latestVersionShort;
        this.updateAvailable = updateAvailable;
    }
    
    public static short[] getShortVersionOfString(final String versionString) {
        if (!versionString.contains(".")) {
            return new short[0];
        }
        final String[] versionParts = versionString.split("\\.");
        final short[] shortArray = new short[versionParts.length];
        int slot = 0;
        for (final String part : versionParts) {
            shortArray[slot] = Short.valueOf(part);
            ++slot;
        }
        return shortArray;
    }
    
    public String getLatestVersion() {
        return this.latestVersion;
    }
    
    public short[] getLatestVersionShort() {
        return this.latestVersionShort;
    }
    
    public boolean isUpdateAvailable() {
        return this.updateAvailable;
    }
    
    public void setUpdateAvailable(final boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }
}
