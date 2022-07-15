//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.labyconnect;

import java.util.concurrent.atomic.*;
import net.labymod.main.*;
import org.lwjgl.opengl.*;
import net.labymod.labyconnect.user.*;
import java.nio.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class GameIconHelper
{
    private static AtomicBoolean iconModified;
    
    public static void updateIcon(final boolean force, final boolean increase) {
        if (!LabyMod.getSettings().unreadMessageIcon) {
            return;
        }
        final h.a util$enumos = h.a();
        if (util$enumos == h.a.d) {
            return;
        }
        if (Display.isActive() && !force) {
            return;
        }
        if (!GameIconHelper.iconModified.get() && !increase) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int unreadMessages = 0;
                    for (final ChatUser chatUser : LabyMod.getInstance().getLabyConnect().getFriends()) {
                        unreadMessages += chatUser.getUnreadMessages();
                    }
                    final ByteBuffer smallIcon = provideIconBuffer((unreadMessages == 0) ? "assets/minecraft/labymod/data/icons/icon_16x16.png" : "assets/minecraft/labymod/data/icons/icon_notify_16x16.png", unreadMessages);
                    final ByteBuffer normalIcon = provideIconBuffer((unreadMessages == 0) ? "assets/minecraft/labymod/data/icons/icon_32x32.png" : "assets/minecraft/labymod/data/icons/icon_notify_32x32.png", unreadMessages);
                    final boolean modified = unreadMessages != 0;
                    bib.z().a((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            GameIconHelper.iconModified.set(modified);
                            Display.setIcon(new ByteBuffer[] { smallIcon, normalIcon });
                        }
                    });
                }
                catch (Exception ioexception) {
                    ioexception.printStackTrace();
                }
            }
        }).start();
    }
    
    private static BufferedImage addUnreadMessageNumber(final BufferedImage bufferedImage, int unreadMessages) {
        if (unreadMessages == 0) {
            return bufferedImage;
        }
        if (unreadMessages > 99) {
            unreadMessages = 99;
        }
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final BufferedImage newBufferedImage = new BufferedImage(width, height, 2);
        final Graphics2D graphics = newBufferedImage.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, null);
        graphics.setPaint(Color.WHITE);
        graphics.setFont(new Font("Arial", 1, 16));
        final String string = String.valueOf(unreadMessages);
        final int stringwidth = graphics.getFontMetrics().stringWidth(string);
        graphics.drawString(String.valueOf(unreadMessages), 22 - stringwidth / 2, 28);
        graphics.dispose();
        return newBufferedImage;
    }
    
    private static ByteBuffer provideIconBuffer(final String source, final int unreadMessages) throws IOException {
        final File file = new File("LabyMod/", "icon.png");
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(source);
        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        final byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        fileOutputStream.close();
        final ByteBuffer byteBuffer = readImageToBuffer(new FileInputStream(file), unreadMessages);
        return byteBuffer;
    }
    
    private static ByteBuffer readImageToBuffer(final InputStream imageStream, final int unreadMessages) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        bufferedimage = addUnreadMessageNumber(bufferedimage, unreadMessages);
        final int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        final ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        for (final int i : aint) {
            bytebuffer.putInt(i << 8 | (i >> 24 & 0xFF));
        }
        bytebuffer.flip();
        return bytebuffer;
    }
    
    static {
        GameIconHelper.iconModified = new AtomicBoolean(false);
    }
}
