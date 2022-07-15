//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils.manager;

import net.labymod.main.*;
import net.labymod.core.*;
import java.util.*;

public class SignManager
{
    private static SignSearchSettings signSearchSettings;
    private static Map<BlockPosition, SignData> signDataMap;
    
    public static void render(final awc tileEntitySign) {
        if (!LabyMod.getSettings().signSearch || !SignManager.signSearchSettings.isEnabled()) {
            if (!SignManager.signDataMap.isEmpty()) {
                reset();
            }
            return;
        }
        final BlockPosition blockPosition = LabyModCore.getMinecraft().getPosition((Object)tileEntitySign.w());
        SignData signData = SignManager.signDataMap.get(blockPosition);
        if (signData == null || signData.getLastSignUpdated() + 500L < System.currentTimeMillis()) {
            SignManager.signDataMap.put(blockPosition, signData = new SignData(tileEntitySign));
        }
        signData.getSignColor().applyColor();
    }
    
    public static void reset() {
        SignManager.signDataMap = new HashMap<BlockPosition, SignData>();
    }
    
    public static SignSearchSettings getSignSearchSettings() {
        return SignManager.signSearchSettings;
    }
    
    static {
        SignManager.signSearchSettings = new SignSearchSettings();
        SignManager.signDataMap = new HashMap<BlockPosition, SignData>();
    }
    
    public enum SignColor
    {
        NONE(1.0f, 1.0f, 1.0f, 1.0f), 
        GREEN(0.6f, 23.6f, 0.6f, 0.6f), 
        RED(23.6f, 0.6f, 0.6f, 0.6f), 
        ORANGE(10.0f, 10.0f, 0.6f, 0.6f), 
        GRAY(0.6f, 0.6f, 0.6f, 0.6f);
        
        private float red;
        private float green;
        private float blue;
        private float alpha;
        
        public void applyColor() {
            bus.c(this.red, this.green, this.blue, this.alpha);
        }
        
        private SignColor(final float red, final float green, final float blue, final float alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
        
        public float getRed() {
            return this.red;
        }
        
        public float getGreen() {
            return this.green;
        }
        
        public float getBlue() {
            return this.blue;
        }
        
        public float getAlpha() {
            return this.alpha;
        }
    }
    
    public static class SignData
    {
        private awc tileEntitySign;
        private SignColor signColor;
        private long lastSignUpdated;
        
        public SignData(final awc sign) {
            this.signColor = SignColor.NONE;
            this.tileEntitySign = sign;
            this.lastSignUpdated = System.currentTimeMillis();
            this.parseSignData();
        }
        
        private void parseSignData() {
            String fullString = "";
            final String[] lines = new String[4];
            int lineCount = -1;
            for (final Object chatComponentObj : this.tileEntitySign.a) {
                ++lineCount;
                if (chatComponentObj != null) {
                    final String line = LabyModCore.getMinecraft().getChatComponent(chatComponentObj).getUnformattedText();
                    if (line != null) {
                        fullString += line;
                        lines[lineCount] = line;
                    }
                }
            }
            if (fullString.isEmpty()) {
                return;
            }
            final SignSearchSettings settings = SignManager.signSearchSettings;
            fullString = fullString.toLowerCase();
            final String searchString = settings.getSearchString().toLowerCase();
            boolean searchFound = searchString.isEmpty() || fullString.contains(searchString);
            if (!searchFound && searchString.contains(",")) {
                for (final String word : searchString.split("\\,")) {
                    if (fullString.contains(word)) {
                        searchFound = true;
                    }
                }
            }
            if (settings.isUseAdvancedOptions()) {
                final boolean blacklistFound = !settings.getBlacklistString().isEmpty() && fullString.contains(settings.getBlacklistString().toLowerCase());
                final Integer currentUserCount = this.getUserCount(lines, true);
                final Integer maxUserCount = this.getUserCount(lines, false);
                if (searchFound && !blacklistFound) {
                    final boolean isEmpty = currentUserCount != null && currentUserCount == 0 && settings.isFilterEmptyServer();
                    final boolean isFull = maxUserCount != null && currentUserCount != null && currentUserCount >= maxUserCount && settings.isFilterFullServer();
                    if (!isEmpty && !isFull) {
                        this.signColor = SignColor.GREEN;
                    }
                    else {
                        this.signColor = (isEmpty ? SignColor.GRAY : (isFull ? SignColor.ORANGE : SignColor.RED));
                    }
                }
                else {
                    this.signColor = SignColor.RED;
                }
            }
            else if (searchFound) {
                this.signColor = SignColor.GREEN;
            }
            else {
                this.signColor = SignColor.RED;
            }
        }
        
        private Integer getUserCount(final String[] lines, final boolean pre) {
            for (final String line : lines) {
                if (line != null) {
                    if (line.contains("/")) {
                        final String[] parts = line.split("/");
                        if (parts.length > (pre ? 0 : 1)) {
                            final String result = parts[!pre].replaceAll(" ", "");
                            return result.matches("^-?\\d+$") ? Integer.valueOf(Integer.parseInt(result)) : null;
                        }
                    }
                }
            }
            return null;
        }
        
        public awc getTileEntitySign() {
            return this.tileEntitySign;
        }
        
        public SignColor getSignColor() {
            return this.signColor;
        }
        
        public long getLastSignUpdated() {
            return this.lastSignUpdated;
        }
        
        public void setTileEntitySign(final awc tileEntitySign) {
            this.tileEntitySign = tileEntitySign;
        }
        
        public void setSignColor(final SignColor signColor) {
            this.signColor = signColor;
        }
        
        public void setLastSignUpdated(final long lastSignUpdated) {
            this.lastSignUpdated = lastSignUpdated;
        }
    }
    
    public static class SignSearchSettings
    {
        private String searchString;
        private String blacklistString;
        private boolean useAdvancedOptions;
        private boolean filterFullServer;
        private boolean filterEmptyServer;
        private boolean enabled;
        
        public SignSearchSettings() {
            this.searchString = "";
            this.blacklistString = "";
            this.useAdvancedOptions = false;
            this.filterFullServer = false;
            this.filterEmptyServer = false;
        }
        
        public void update() {
            this.enabled = ((this.useAdvancedOptions && (this.filterFullServer || this.filterEmptyServer || !this.blacklistString.isEmpty())) || !this.searchString.isEmpty());
        }
        
        public String getSearchString() {
            return this.searchString;
        }
        
        public String getBlacklistString() {
            return this.blacklistString;
        }
        
        public boolean isUseAdvancedOptions() {
            return this.useAdvancedOptions;
        }
        
        public boolean isFilterFullServer() {
            return this.filterFullServer;
        }
        
        public boolean isFilterEmptyServer() {
            return this.filterEmptyServer;
        }
        
        public boolean isEnabled() {
            return this.enabled;
        }
        
        public void setSearchString(final String searchString) {
            this.searchString = searchString;
        }
        
        public void setBlacklistString(final String blacklistString) {
            this.blacklistString = blacklistString;
        }
        
        public void setUseAdvancedOptions(final boolean useAdvancedOptions) {
            this.useAdvancedOptions = useAdvancedOptions;
        }
        
        public void setFilterFullServer(final boolean filterFullServer) {
            this.filterFullServer = filterFullServer;
        }
        
        public void setFilterEmptyServer(final boolean filterEmptyServer) {
            this.filterEmptyServer = filterEmptyServer;
        }
        
        public void setEnabled(final boolean enabled) {
            this.enabled = enabled;
        }
    }
}
