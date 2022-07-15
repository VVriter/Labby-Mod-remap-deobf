//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.util;

import java.util.*;
import java.util.regex.*;
import com.google.common.collect.*;

public class StringMessage
{
    private static final Map<Character, a> formatMap;
    private static final Pattern INCREMENTAL_PATTERN;
    private final List<hh> list;
    private ho currentChatComponent;
    private hn modifier;
    private final hh[] output;
    private int currentIndex;
    private final String message;
    
    public StringMessage(final String message, final boolean keepNewlines) {
        this.list = new ArrayList<hh>();
        this.currentChatComponent = new ho("");
        this.modifier = new hn();
        this.message = message;
        if (message == null) {
            this.output = new hh[] { (hh)this.currentChatComponent };
            return;
        }
        this.list.add((hh)this.currentChatComponent);
        final Matcher matcher = StringMessage.INCREMENTAL_PATTERN.matcher(message);
        String match = null;
        while (matcher.find()) {
            int groupId = 0;
            while ((match = matcher.group(++groupId)) == null) {}
            this.appendNewComponent(matcher.start(groupId));
            Label_0471: {
                switch (groupId) {
                    case 1: {
                        final a format = StringMessage.formatMap.get(match.toLowerCase(Locale.ENGLISH).charAt(1));
                        if (format == a.v) {
                            this.modifier = new hn();
                            break;
                        }
                        if (!format.c()) {
                            this.modifier = new hn().a(format);
                            break;
                        }
                        switch (format) {
                            case r: {
                                this.modifier.a(Boolean.TRUE);
                                break Label_0471;
                            }
                            case u: {
                                this.modifier.b(Boolean.TRUE);
                                break Label_0471;
                            }
                            case s: {
                                this.modifier.c(Boolean.TRUE);
                                break Label_0471;
                            }
                            case t: {
                                this.modifier.d(Boolean.TRUE);
                                break Label_0471;
                            }
                            case q: {
                                this.modifier.e(Boolean.TRUE);
                                break Label_0471;
                            }
                            default: {
                                throw new AssertionError((Object)"Unexpected message format");
                            }
                        }
                        break;
                    }
                    case 2: {
                        if (keepNewlines) {
                            this.currentChatComponent.a((hh)new ho("\n"));
                            break;
                        }
                        this.currentChatComponent = null;
                        break;
                    }
                    case 3: {
                        if (!match.startsWith("http://") && !match.startsWith("https://")) {
                            match = "http://" + match;
                        }
                        this.modifier.a(new hg(hg.a.a, match));
                        this.appendNewComponent(matcher.end(groupId));
                        this.modifier.a((hg)null);
                        break;
                    }
                }
            }
            this.currentIndex = matcher.end(groupId);
        }
        if (this.currentIndex < message.length()) {
            this.appendNewComponent(message.length());
        }
        this.output = this.list.toArray(new hh[this.list.size()]);
    }
    
    private void appendNewComponent(final int index) {
        if (index <= this.currentIndex) {
            return;
        }
        final hh addition = new ho(this.message.substring(this.currentIndex, index)).a(this.modifier);
        this.currentIndex = index;
        this.modifier = this.modifier.m();
        if (this.currentChatComponent == null) {
            this.currentChatComponent = new ho("");
            this.list.add((hh)this.currentChatComponent);
        }
        this.currentChatComponent.a(addition);
    }
    
    public hh[] getOutput() {
        return this.output;
    }
    
    static {
        INCREMENTAL_PATTERN = Pattern.compile("(" + String.valueOf('§') + "[0-9a-fk-or])|(\\n)|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))", 2);
        final ImmutableMap.Builder<Character, a> builder = (ImmutableMap.Builder<Character, a>)ImmutableMap.builder();
        for (final a format : a.values()) {
            builder.put((Object)Character.toLowerCase(format.toString().charAt(1)), (Object)format);
        }
        formatMap = (Map)builder.build();
    }
}
