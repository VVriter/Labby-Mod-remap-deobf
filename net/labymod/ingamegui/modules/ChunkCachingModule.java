//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.ingamegui.modules;

import net.labymod.ingamegui.moduletypes.*;
import net.labymod.main.*;
import net.labymod.api.protocol.chunk.*;
import net.labymod.utils.*;
import java.util.*;
import net.labymod.settings.elements.*;
import net.labymod.ingamegui.*;

public class ChunkCachingModule extends SimpleTextModule
{
    @Override
    public String[] getKeys() {
        final ChunkCachingProtocol ccp = LabyMod.getInstance().getChunkCachingProtocol();
        return ccp.isCachingSupported() ? new String[] { "Cached", "Cache size", "Downloaded", "Skipped" } : new String[] { "CCP" };
    }
    
    @Override
    public String[] getValues() {
        final ChunkCachingProtocol ccp = LabyMod.getInstance().getChunkCachingProtocol();
        if (!ccp.isCachingSupported()) {
            return new String[] { "Not supported here" };
        }
        final long cachedMemory = ccp.getCurrentlyCachedBytes().get();
        final long loadedBytes = ccp.getLoadedBytesInSession();
        final long downloadedBytes = ccp.getDownloadedBytesInSession();
        final int count = ccp.getChunkCache().size();
        final String lineAmount = count + "";
        final String lineSize = ModUtils.humanReadableByteCount(cachedMemory, true, true);
        final String lineDownloaded = ModUtils.humanReadableByteCount(downloadedBytes, true, true);
        final String lineSkipped = ModUtils.humanReadableByteCount(loadedBytes, true, true);
        return new String[] { lineAmount, lineSize, lineDownloaded, lineSkipped };
    }
    
    @Override
    public String[] getDefaultValues() {
        return this.getValues();
    }
    
    public boolean isShown() {
        final ChunkCachingProtocol ccp = LabyMod.getInstance().getChunkCachingProtocol();
        return ccp.isCachingSupported() || bib.z().m instanceof bkn;
    }
    
    @Override
    public String[] getDefaultKeys() {
        return this.getKeys();
    }
    
    public void loadSettings() {
    }
    
    @Override
    public void fillSubSettings(final List<SettingsElement> settingsElements) {
        super.fillSubSettings(settingsElements);
    }
    
    public ControlElement.IconData getIconData() {
        return this.getModuleIcon(this.getSettingName());
    }
    
    public String getControlName() {
        return "Chunk Caching Info";
    }
    
    public String getSettingName() {
        return "chunkcaching";
    }
    
    public String getDescription() {
        return "";
    }
    
    public int getSortingId() {
        return 6;
    }
    
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }
}
