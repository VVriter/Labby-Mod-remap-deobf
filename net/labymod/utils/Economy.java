//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.utils;

import java.text.*;
import com.google.gson.*;
import net.labymod.main.*;

public class Economy
{
    private boolean visible;
    private int balance;
    private int prevBalance;
    private long lastBalanceUpdated;
    private String icon;
    private int divisor;
    private DecimalFormat decimalFormat;
    
    public Economy() {
        this.divisor = 0;
    }
    
    public void update(final JsonObject object, final String key) {
        if (object.has(key)) {
            final JsonObject economy = object.get(key).getAsJsonObject();
            if (economy.has("visible")) {
                this.visible = economy.get("visible").getAsBoolean();
            }
            if (economy.has("balance")) {
                this.updateBalance(economy.get("balance").getAsInt());
            }
            if (economy.has("icon")) {
                this.icon = economy.get("icon").getAsString();
            }
            if (economy.has("decimal")) {
                final JsonObject decimal = economy.get("decimal").getAsJsonObject();
                try {
                    this.divisor = decimal.get("divisor").getAsInt();
                    this.decimalFormat = new DecimalFormat(decimal.get("format").getAsString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void updateBalance(final int value) {
        this.prevBalance = this.balance;
        this.balance = value;
        this.lastBalanceUpdated = System.currentTimeMillis();
    }
    
    public nf getIcon(final nf fallback) {
        return (this.icon == null) ? fallback : LabyMod.getInstance().getDynamicTextureManager().getTexture("economy_icon_" + this.icon.hashCode(), this.icon);
    }
    
    public String getDisplayValue() {
        final int balance = this.getInterpolatedBalance();
        if (this.divisor <= 0 || this.decimalFormat == null) {
            return String.valueOf(balance);
        }
        return this.decimalFormat.format(balance / (double)this.divisor);
    }
    
    private int getInterpolatedBalance() {
        final long timePassed = System.currentTimeMillis() - this.lastBalanceUpdated;
        final long duration = 2000L;
        if (timePassed > duration) {
            return this.balance;
        }
        final double input = Math.min(duration, timePassed) / (double)duration * 4.0;
        final double sigmoid = 1.0 / (1.0 + Math.exp(-input * 2.0 + 4.0));
        final int economyDifference = this.prevBalance - this.balance;
        return (int)Math.round(this.prevBalance - economyDifference * sigmoid);
    }
    
    public void reset() {
        this.visible = false;
        this.balance = 0;
        this.prevBalance = 0;
        this.lastBalanceUpdated = 0L;
        this.icon = null;
        this.divisor = 0;
        this.decimalFormat = null;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public int getBalance() {
        return this.balance;
    }
    
    public String getIcon() {
        return this.icon;
    }
}
