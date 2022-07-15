//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.main;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.lookup.*;
import java.util.*;
import java.lang.reflect.*;
import java.beans.*;

public class Log4JJNDIFix
{
    public static void fix() {
        ((LoggerContext)LogManager.getContext(false)).addPropertyChangeListener(event -> {
            if (event.getPropertyName().equals("config")) {
                applyFix();
            }
            return;
        });
        applyFix();
    }
    
    private static void applyFix() {
        try {
            final Interpolator interpolator = (Interpolator)((LoggerContext)LogManager.getContext(false)).getConfiguration().getStrSubstitutor().getVariableResolver();
            if (interpolator == null) {
                return;
            }
            for (final Field field : Interpolator.class.getDeclaredFields()) {
                if (Map.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    ((Map)field.get(interpolator)).clear();
                }
            }
            System.out.println("Removed JNDI lookup");
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
