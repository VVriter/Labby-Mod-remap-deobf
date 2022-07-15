//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.support.gui;

import java.awt.event.*;
import net.labymod.support.util.*;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;
import net.labymod.addon.*;
import net.labymod.api.*;
import net.labymod.user.cosmetic.util.*;
import net.labymod.utils.*;
import net.labymod.core.asm.*;
import net.labymod.main.*;
import java.util.*;
import net.labymod.user.*;
import net.labymod.user.cosmetic.remote.*;
import java.io.*;
import javax.swing.text.*;

public class GuiDebugConsole extends JFrame
{
    private JTextPane textPane;
    private long idle;
    
    public GuiDebugConsole(final Consumer<Boolean> consumer) {
        this.textPane = new JTextPane();
        this.idle = 0L;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        this.setSize(900, 520);
        this.setLocationRelativeTo(null);
        this.setTitle("LabyMod Debug Console");
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(GuiDebugConsole.class.getResource("/assets/minecraft/labymod/textures/labymod_logo.png")));
        }
        catch (Exception error) {
            error.printStackTrace();
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                consumer.accept(true);
                e.getWindow().dispose();
            }
        });
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), 1));
        final JPanel controlPanel = new JPanel();
        this.getContentPane().add(controlPanel);
        controlPanel.setLayout(new FlowLayout(2, 5, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        final JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String log = GuiDebugConsole.this.createLog();
                Hastebin.upload(log);
            }
        });
        controlPanel.add(uploadButton);
        final JPanel guiPanel = new JPanel();
        controlPanel.add(guiPanel);
        guiPanel.setLayout(new BorderLayout(0, 0));
        final JButton restartDebugButton = new JButton("Restart in debug");
        restartDebugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    Debug.DEBUG_FILE.createNewFile();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                bib.z().n();
            }
        });
        guiPanel.add(restartDebugButton);
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        this.textPane.setFont(new Font("Courier New", 0, 15));
        this.textPane.setEditable(false);
        this.textPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(final CaretEvent evt) {
                if (evt.getDot() == evt.getMark()) {
                    return;
                }
                final JTextPane txtPane = (JTextPane)evt.getSource();
                final DefaultHighlighter highlighter = (DefaultHighlighter)txtPane.getHighlighter();
                highlighter.removeAllHighlights();
                final DefaultHighlighter.DefaultHighlightPainter hPainter = new DefaultHighlighter.DefaultHighlightPainter(new Color(16755200));
                final String selText = txtPane.getSelectedText();
                if (selText.isEmpty() || selText.equals("\n")) {
                    return;
                }
                String contText = "";
                final DefaultStyledDocument document = (DefaultStyledDocument)txtPane.getDocument();
                try {
                    contText = document.getText(0, document.getLength());
                    if (contText.isEmpty() || contText.equals("\n")) {
                        return;
                    }
                }
                catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                int index = 0;
                while ((index = contText.indexOf(selText, index)) > -1) {
                    try {
                        highlighter.addHighlight(index, selText.length() + index, hPainter);
                        index += selText.length();
                    }
                    catch (BadLocationException ex2) {
                        ex2.printStackTrace();
                    }
                }
            }
        });
        final JScrollPane scrollPane = new JScrollPane(this.textPane);
        panel.add(scrollPane);
        this.getContentPane().add(panel);
        this.setVisible(true);
        final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUnitIncrement(16);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Debug.log(Debug.EnumDebugMode.GENERAL, "Debug console started");
                try {
                    final BufferedReader br = new BufferedReader(new FileReader(new File("logs/latest.log")));
                    while (GuiDebugConsole.this.isVisible()) {
                        final boolean isAtBottom = scrollBar.getValue() + 10 >= scrollBar.getMaximum() - scrollBar.getVisibleAmount();
                        if (!scrollBar.isVisible() || isAtBottom) {
                            GuiDebugConsole.this.readLogFile(br);
                        }
                        if (GuiDebugConsole.this.idle < System.currentTimeMillis()) {
                            try {
                                Thread.sleep(2000L);
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                Debug.log(Debug.EnumDebugMode.GENERAL, "Debug console closed");
            }
        }).start();
    }
    
    private String createLog() {
        String log = this.textPane.getText();
        final long maxMemory = Runtime.getRuntime().maxMemory();
        final long totalMemory = Runtime.getRuntime().totalMemory();
        final long freeMemory = Runtime.getRuntime().freeMemory();
        final long usedMemory = totalMemory - freeMemory;
        final long percent = usedMemory * 100L / maxMemory;
        String addons = "";
        for (final LabyModAddon addon : AddonLoader.getAddons()) {
            if (addon != null && addon.about != null) {
                addons = addons + addon.about.name + ", ";
            }
        }
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final User user = userManager.getUser(LabyMod.getInstance().getPlayerUUID());
        final RemoteCosmeticLoader remote = userManager.getRemoteCosmeticLoader();
        log += "\n---------------------------------------";
        log = log + "\nTime: " + new Date().toString();
        log = log + "\nUsername: " + LabyMod.getInstance().getPlayerName();
        log = log + "\nUUID: " + user.getUuid().toString();
        log = log + "\nGroup: " + ((user.getGroup() == null) ? "NONE" : user.getGroup().getName());
        log = log + "\nWhitelist: " + userManager.isWhitelisted(user.getUuid());
        log = log + "\nRemote Cosmetics: " + ((remote.getIndex() == null) ? "null" : Integer.valueOf(remote.getIndex().cosmetics.size()));
        log = log + "\nCosmetics: " + user.getCosmetics().size();
        for (final CosmeticData cos : user.getCosmetics().values()) {
            log = log + "\n- " + cos.getClass().getSimpleName();
        }
        log = log + "\nMemory: " + (int)percent + "% (" + ModUtils.humanReadableByteCount(maxMemory, true, true) + ")";
        log = log + "\nAddons: [" + AddonLoader.getAddons().size() + "] " + addons;
        log = log + "\nForge: " + LabyModCoreMod.isForge();
        log = log + "\nVersion: " + Source.getUserAgent();
        log += "\n---------------------------------------";
        return log;
    }
    
    private void readLogFile(final BufferedReader br) {
        try {
            boolean changed = false;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Session ID is token")) {
                    continue;
                }
                this.addString(line + "\n");
                changed = true;
            }
            if (changed) {
                this.textPane.repaint();
                this.idle = System.currentTimeMillis() + 5000L;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addString(final String string) {
        try {
            final Document doc = this.textPane.getDocument();
            final StringReader sr = new StringReader(string);
            final EditorKit ek = this.textPane.getEditorKit();
            ek.read(sr, doc, this.textPane.getDocument().getLength());
            this.textPane.setCaretPosition(doc.getLength());
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
}
