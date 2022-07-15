//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package net.labymod.core_implementation.mc112.gui;

import net.labymod.utils.manager.*;
import net.labymod.ingamegui.*;
import net.labymod.api.permissions.*;
import net.labymod.core.*;
import net.labymod.main.*;
import com.mojang.authlib.*;
import net.labymod.main.listeners.*;
import net.labymod.utils.*;
import net.labymod.user.*;
import net.labymod.user.group.*;
import com.mojang.realmsclient.gui.*;
import org.lwjgl.opengl.*;
import java.util.*;
import com.google.common.collect.*;

public class ModPlayerTabOverlay extends bjq
{
    private static final Ordering<bsc> ENTRY_ORDERING;
    private final bib mc;
    private final biq guiIngame;
    private hh footer;
    private hh header;
    private long lastTimeOpened;
    private boolean isBeingRendered;
    private long lastServerBannerInitTime;
    private int lastServerBannerHash;
    
    public ModPlayerTabOverlay(final bib mcIn, final biq guiIngameIn) {
        super(mcIn, guiIngameIn);
        this.lastServerBannerInitTime = -1L;
        this.lastServerBannerHash = 0;
        this.mc = mcIn;
        this.guiIngame = guiIngameIn;
    }
    
    public String a(final bsc networkPlayerInfoIn) {
        final String name = (networkPlayerInfoIn.l() != null) ? networkPlayerInfoIn.l().d() : bhh.a((bhm)networkPlayerInfoIn.j(), networkPlayerInfoIn.a().getName());
        final String tagName = TagManager.getTaggedMessage(name);
        if (tagName != null) {
            return tagName;
        }
        return name;
    }
    
    public void a(final boolean willBeRendered) {
        if (willBeRendered && !this.isBeingRendered) {
            this.lastTimeOpened = bib.I();
        }
        this.isBeingRendered = willBeRendered;
    }
    
    public void a(final int width, final bhk scoreboardIn, final bhg scoreObjectiveIn) {
        Module.lastTablistRendered = System.currentTimeMillis();
        if (LabyMod.getInstance().getPriorityOverlayRenderer().isWatermarkValid()) {
            this.tournamentTabOverlay(width, scoreboardIn, scoreObjectiveIn);
        }
        else if (LabyMod.getSettings().oldTablist && Permissions.isAllowed(Permissions.Permission.ANIMATIONS)) {
            this.oldTabOverlay(width, scoreboardIn, scoreObjectiveIn);
        }
        else {
            this.newTabOverlay(width, scoreboardIn, scoreObjectiveIn);
        }
    }
    
    private void tournamentTabOverlay(final int legacyWidth, final bhk scoreboard, final bhg scoreObjective) {
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        final brz nethandlerplayclient = LabyModCore.getMinecraft().getPlayer().d;
        final Collection<bhh> teams = (Collection<bhh>)scoreboard.g();
        int totalMembers = 0;
        int maxMembersInTeam = 0;
        int maxMemberLength = 0;
        for (final bhh team : scoreboard.g()) {
            maxMembersInTeam = Math.max(maxMembersInTeam, team.d().size());
            if (team.c() != null) {
                maxMemberLength = Math.max(maxMemberLength, draw.getStringWidth(team.c()));
            }
            for (final String member : team.d()) {
                maxMemberLength = Math.max(maxMemberLength, draw.getStringWidth(member));
                ++totalMembers;
            }
        }
        int maxTeamsInRow = 5;
        int maxSpectatorLength = draw.getStringWidth("Spectator");
        boolean outOfScreen;
        do {
            final int teamsInRow = Math.min(maxTeamsInRow, Math.max(1, teams.size()));
            final int memberHeight = 20;
            final int teamWidth = (maxMemberLength + memberHeight + 4) * teamsInRow / teamsInRow;
            final double predictedWidth = teamWidth * teamsInRow;
            outOfScreen = (predictedWidth > draw.getWidth() - maxSpectatorLength);
            if (outOfScreen) {
                --maxTeamsInRow;
            }
        } while (maxTeamsInRow > 1 && outOfScreen);
        final int teamsInRow = Math.min(maxTeamsInRow, Math.max(1, teams.size()));
        final int teamsInColumn = (int)Math.ceil(teams.size() / (double)maxTeamsInRow);
        if (teamsInRow <= 0) {
            return;
        }
        final int memberHeight2 = 20;
        final int teamWidth2 = (maxMemberLength + memberHeight2 + 4) * teamsInRow / teamsInRow;
        final int teamHeight = 10 + memberHeight2 * maxMembersInTeam + 2;
        final double width = teamWidth2 * teamsInRow;
        final double height = teamHeight * teamsInColumn;
        final double offsetX = legacyWidth / 2.0 - width / 2.0;
        final double offsetY = 40.0;
        double relX = 0.0;
        double relY = 0.0;
        int index = 0;
        this.mc.N().a(ModTextures.TITLE_LABYMOD_BANNER_WATERMARK);
        draw.drawTexture(legacyWidth / 2 - 50, offsetY - 30.0, 256.0, 256.0, 100.0, 20.0);
        draw.drawRect(offsetX - 1.0, offsetY - 1.0, offsetX + width, offsetY + height, Integer.MIN_VALUE);
        final String[] memberArray = new String[totalMembers];
        int arrayIndex = 0;
        for (final bhh team2 : scoreboard.g()) {
            final double x = offsetX + relX;
            final double y = offsetY + relY;
            draw.drawRect(x, y, x + teamWidth2 - 1.0, y + teamHeight - 1.0, 553648127);
            draw.drawRect(x, y, x + teamWidth2 - 1.0, y + 10.0, 553648127);
            draw.drawCenteredString(team2.e() + team2.c(), x + teamWidth2 / 2.0, y + 1.0);
            double memberY = y + 10.0;
            for (final String member2 : team2.d()) {
                memberArray[arrayIndex] = member2;
                ++arrayIndex;
                draw.drawRect(x + 1.0, memberY + 1.0, x + teamWidth2 - 2.0, memberY + memberHeight2, ModColor.toRGB(0, 0, 0, 40));
                final bsc networkPlayerInfo = nethandlerplayclient.a(member2);
                this.mc.N().a((networkPlayerInfo == null) ? ModTextures.MISC_HEAD_QUESTION : networkPlayerInfo.g());
                final boolean alive = networkPlayerInfo != null && networkPlayerInfo.b() != ams.e;
                bus.c(1.0f, 1.0f, 1.0f, alive ? 1.0f : 0.5f);
                bus.e();
                bus.m();
                bus.a(770, 771, 1, 0);
                if (networkPlayerInfo == null) {
                    draw.drawTexture(x + 1.0, memberY + 1.0, 256.0, 256.0, memberHeight2 - 1, memberHeight2 - 1, 1.1f);
                }
                else {
                    bir.a((int)x + 1, (int)memberY + 1, 8.0f, 8.0f, 8, 8, memberHeight2 - 1, memberHeight2 - 1, 64.0f, 64.0f);
                    bir.a((int)x + 1, (int)memberY + 1, 40.0f, 8.0f, 8, 8, memberHeight2 - 1, memberHeight2 - 1, 64.0f, 64.0f);
                }
                if (!alive) {
                    bus.c(1.0f, 1.0f, 1.0f, 0.6f);
                    this.mc.N().a(ModTextures.MISC_HEAD_SKULL);
                    draw.drawTexture(x + 1.0, memberY + 1.0, 256.0, 256.0, memberHeight2 - 1, memberHeight2 - 1, 1.1f);
                }
                bus.m();
                draw.drawStringWithShadow(ModColor.cl(alive ? 'a' : 'c') + member2, x + memberHeight2 + 2.0, memberY + 2.0, alive ? -1 : -1862270977);
                if (scoreObjective != null && scoreObjective.e() == bhq.a.a && alive) {
                    final int score = scoreboard.c(member2, scoreObjective).c();
                    this.mc.N().a(ModTextures.MISC_ECONOMY_CASH);
                    draw.drawTexture(x + memberHeight2 + 2.0, memberY + 2.0 + 10.0, 256.0, 256.0, 8.0, 8.0);
                    draw.drawString(ModColor.cl('e') + score, x + memberHeight2 + 2.0 + 8.0 + 2.0, memberY + 2.0 + 10.0 + 1.5, 0.7);
                }
                memberY += memberHeight2;
            }
            relX += teamWidth2;
            if (++index % teamsInRow == 0) {
                relX = 0.0;
                relY += teamHeight;
            }
        }
        if (this.footer != null) {
            final String formattedText = this.footer.d();
            if (!formattedText.isEmpty()) {
                int footerY = 3;
                final List<String> list = (List<String>)LabyModCore.getMinecraft().getFontRenderer().c(this.footer.d(), (int)(width - 2.0));
                draw.drawRect(offsetX - 1.0, offsetY + height + 1.0, offsetX + width, offsetY + height + footerY + list.size() * 10, Integer.MIN_VALUE);
                for (final String line : list) {
                    draw.drawCenteredString(line, offsetX + width / 2.0, offsetY + height + footerY);
                    footerY += 10;
                }
            }
        }
        final bsa playerController = bib.z().c;
        if (playerController != null && playerController.a()) {
            int spectatorAmount = 0;
            int notRendered = 0;
            double yPosInCount = offsetY - 1.0 + 11.0;
            for (final bsc networkPlayerInfo2 : nethandlerplayclient.d()) {
                if (networkPlayerInfo2.b() != ams.e) {
                    continue;
                }
                final GameProfile gameProfile = networkPlayerInfo2.a();
                if (gameProfile == null) {
                    continue;
                }
                boolean inInATeam = false;
                for (final String member3 : memberArray) {
                    if (member3 != null && member3.equals(gameProfile.getName())) {
                        inInATeam = true;
                        break;
                    }
                }
                if (inInATeam) {
                    continue;
                }
                if (yPosInCount > offsetY + height) {
                    ++notRendered;
                }
                else {
                    ++spectatorAmount;
                    yPosInCount += 11.0;
                    maxSpectatorLength = Math.max(maxSpectatorLength, draw.getStringWidth(gameProfile.getName()));
                }
            }
            if (spectatorAmount > 0) {
                final double padding = 5.0;
                final double specX = offsetX + width;
                double specY = offsetY - 1.0;
                final double specWidth = maxSpectatorLength + padding * 2.0 + 8.0 + 1.0;
                final double specHeight = (spectatorAmount + 1) * 11;
                draw.drawRect(specX + 1.0, specY, specX + specWidth, specY + specHeight, Integer.MIN_VALUE);
                draw.drawCenteredString("Spectator", specX + specWidth / 2.0, specY + 2.0);
                specY += 11.0;
                for (final bsc networkPlayerInfo3 : nethandlerplayclient.d()) {
                    if (networkPlayerInfo3.b() != ams.e) {
                        continue;
                    }
                    final GameProfile gameProfile2 = networkPlayerInfo3.a();
                    if (gameProfile2 == null) {
                        continue;
                    }
                    boolean inInATeam2 = false;
                    for (final String member4 : memberArray) {
                        if (member4 != null && member4.equals(gameProfile2.getName())) {
                            inInATeam2 = true;
                            break;
                        }
                    }
                    if (inInATeam2) {
                        continue;
                    }
                    final String name = ModColor.cl('7') + ModColor.cl('o') + gameProfile2.getName();
                    draw.drawRect(specX + 2.0, specY, specX + specWidth - 1.0, specY + 10.0, 553648127);
                    this.mc.N().a(networkPlayerInfo3.g());
                    bir.a((int)(specX + 3.0), (int)(specY + 1.0), 8.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
                    bir.a((int)(specX + 3.0), (int)(specY + 1.0), 40.0f, 8.0f, 8, 8, 8, 8, 64.0f, 64.0f);
                    bus.c(1.0f, 1.0f, 1.0f, 1.0f);
                    bus.e();
                    bus.m();
                    bus.a(770, 771, 1, 0);
                    draw.c(draw.getFontRenderer(), name, (int)(specX + padding + 8.0 + 1.0), (int)specY + 1, -1862270977);
                    specY += 11.0;
                    if (specY > offsetY + height && notRendered != 0) {
                        draw.c(draw.getFontRenderer(), ModColor.cl('7') + ModColor.cl('o') + notRendered + " more...", (int)(specX + 4.0), (int)specY + 1, -1862270977);
                        break;
                    }
                }
            }
        }
    }
    
    public void newTabOverlay(final int width, final bhk scoreboardIn, final bhg scoreObjectiveIn) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        final RenderIngamePostOverlayListener overlayRenderer = LabyMod.getInstance().getPriorityOverlayRenderer();
        final boolean labyOnlyServer = overlayRenderer.isLabyOnlyServer();
        final boolean serverBanner = overlayRenderer.hasServerBanner();
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        if (serverBanner) {
            final ServerData serverData = LabyMod.getInstance().getCurrentServerData();
            nf resourceLocation = null;
            boolean visible = true;
            if (overlayRenderer.getServerBanner() == null || serverData == null || serverData.getIp() == null) {
                resourceLocation = ModTextures.TITLE_LABYMOD_BANNER_WATERMARK;
            }
            else {
                final int hash = serverData.getIp().hashCode();
                if (hash != this.lastServerBannerHash) {
                    this.lastServerBannerInitTime = System.currentTimeMillis();
                    this.lastServerBannerHash = hash;
                }
                visible = (this.lastServerBannerInitTime + 200L < System.currentTimeMillis());
                resourceLocation = LabyMod.getInstance().getDynamicTextureManager().getTexture("server_banner_" + overlayRenderer.getServerBanner().hashCode(), overlayRenderer.getServerBanner());
            }
            this.mc.N().a(resourceLocation);
            if (visible) {
                draw.drawTexture(draw.getWidth() / 2 - 100, 15.0, 256.0, 256.0, 200.0, 40.0);
            }
        }
        int familiarCount = 0;
        int totalCount = 0;
        final brz nethandlerplayclient = LabyModCore.getMinecraft().getPlayer().d;
        List<bsc> var5 = (List<bsc>)ModPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)nethandlerplayclient.d());
        int var6 = 0;
        int var7 = 0;
        for (final bsc networkplayerinfo : var5) {
            int k = LabyModCore.getMinecraft().getFontRenderer().a(this.a(networkplayerinfo));
            if (LabyMod.getSettings().revealFamiliarUsers && !labyOnlyServer) {
                final UUID uuid = networkplayerinfo.a().getId();
                final User user = userManager.getUser(uuid);
                if (user.isFamiliar()) {
                    k += 10;
                    ++familiarCount;
                }
                ++totalCount;
            }
            var6 = Math.max(var6, k);
            if (scoreObjectiveIn != null && scoreObjectiveIn.e() != bhq.a.b) {
                k = LabyModCore.getMinecraft().getFontRenderer().a(" " + scoreboardIn.c(networkplayerinfo.a().getName(), scoreObjectiveIn).c());
                var7 = Math.max(var7, k);
            }
        }
        var5 = var5.subList(0, Math.min(var5.size(), 80));
        int var9;
        int var8;
        int j4;
        for (var8 = (var9 = var5.size()), j4 = 1; var9 > 20; var9 = (var8 + j4 - 1) / j4) {
            ++j4;
        }
        final boolean var10 = this.mc.D() || LabyModCore.getMinecraft().getConnection().a().f();
        int var11;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.e() == bhq.a.b) {
                var11 = 90;
            }
            else {
                var11 = var7;
            }
        }
        else {
            var11 = 0;
        }
        final int var12 = Math.min(j4 * ((var10 ? 9 : 0) + var6 + var11 + 13), width - 50) / j4;
        final int var13 = width / 2 - (var12 * j4 + (j4 - 1) * 5) / 2;
        int var14 = serverBanner ? 60 : 10;
        int var15 = var12 * j4 + (j4 - 1) * 5;
        List<String> var16 = null;
        List<String> var17 = null;
        if (this.header != null) {
            var16 = (List<String>)LabyModCore.getMinecraft().getFontRenderer().c(this.header.d(), width - 50);
            for (final String s : var16) {
                var15 = Math.max(var15, LabyModCore.getMinecraft().getFontRenderer().a(s));
            }
        }
        if (this.footer != null) {
            var17 = (List<String>)LabyModCore.getMinecraft().getFontRenderer().c(this.footer.d(), width - 50);
            for (final String s2 : var17) {
                var15 = Math.max(var15, LabyModCore.getMinecraft().getFontRenderer().a(s2));
            }
        }
        if (var16 != null) {
            a(width / 2 - var15 / 2 - 1, var14 - 1, width / 2 + var15 / 2 + 2, var14 + var16.size() * LabyModCore.getMinecraft().getFontRenderer().a, Integer.MIN_VALUE);
            for (final String s3 : var16) {
                final int i2 = LabyModCore.getMinecraft().getFontRenderer().a(s3);
                LabyModCore.getMinecraft().getFontRenderer().a(s3, (float)(width / 2 - i2 / 2), (float)var14, -1);
                var14 += LabyModCore.getMinecraft().getFontRenderer().a;
            }
            ++var14;
        }
        a(width / 2 - var15 / 2 - 1, var14 - 1, width / 2 + var15 / 2 + 2, var14 + var9 * 9, Integer.MIN_VALUE);
        for (int var18 = 0; var18 < var8; ++var18) {
            final int var19 = var18 / var9;
            final int var20 = var18 % var9;
            int var21 = var13 + var19 * var12 + var19 * 5;
            final int var22 = var14 + var20 * 9;
            a(var21, var22, var21 + var12, var22 + 8, 553648127);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.e();
            bus.m();
            bus.a(770, 771, 1, 0);
            if (var18 < var5.size()) {
                final bsc networkplayerinfo2 = var5.get(var18);
                String s4 = this.a(networkplayerinfo2);
                final GameProfile gameprofile = networkplayerinfo2.a();
                if (var10) {
                    final aed entityplayer = LabyModCore.getMinecraft().getWorld().b(gameprofile.getId());
                    final boolean flag1 = entityplayer != null && entityplayer.a(aee.a) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                    this.mc.N().a(networkplayerinfo2.g());
                    final int l2 = 8 + (flag1 ? 8 : 0);
                    final int i3 = 8 * (flag1 ? -1 : 1);
                    bir.a(var21, var22, 8.0f, (float)l2, 8, i3, 8, 8, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.a(aee.g)) {
                        final int j5 = 8 + (flag1 ? 8 : 0);
                        final int k2 = 8 * (flag1 ? -1 : 1);
                        bir.a(var21, var22, 40.0f, (float)j5, 8, k2, 8, 8, 64.0f, 64.0f);
                    }
                    var21 += 9;
                }
                if (networkplayerinfo2.b() == ams.e) {
                    s4 = a.u + s4;
                    LabyModCore.getMinecraft().getFontRenderer().a(s4, (float)var21, (float)var22, -1862270977);
                }
                else {
                    boolean badgeVisible = false;
                    if (LabyMod.getSettings().revealFamiliarUsers && !labyOnlyServer) {
                        final User user2 = userManager.getUser(networkplayerinfo2.a().getId());
                        if (user2.isFamiliar()) {
                            final LabyGroup group = user2.getGroup();
                            if (group != null) {
                                group.renderBadge(var21 - 1, var22, 8.0, 8.0, true);
                                badgeVisible = true;
                            }
                        }
                    }
                    LabyModCore.getMinecraft().getFontRenderer().a(s4, var21 + (float)(badgeVisible ? 8 : 0), (float)var22, -1);
                }
                if (scoreObjectiveIn != null && networkplayerinfo2.b() != ams.e) {
                    final int var23 = var21 + var6 + 1;
                    final int var24 = var23 + var11;
                    if (var24 - var23 > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, var22, gameprofile.getName(), var23, var24, networkplayerinfo2);
                    }
                }
                final nf flag2 = LabyMod.getSettings().showFlags ? overlayRenderer.getFlagFor(gameprofile.getId()) : null;
                if (flag2 == null) {
                    this.a(var12, var21 - (var10 ? 9 : 0), var22, networkplayerinfo2);
                }
                else {
                    bib.z().N().a(flag2);
                    draw.drawTexture(var21 + var12 - 8 - 10, var22 + 1, 256.0, 256.0, 9.0, 6.0);
                }
            }
        }
        if (var17 != null) {
            var14 = var14 + var9 * 9 + 1;
            a(width / 2 - var15 / 2 - 1, var14 - 1, width / 2 + var15 / 2 + 2, var14 + var17.size() * LabyModCore.getMinecraft().getFontRenderer().a, Integer.MIN_VALUE);
            for (final String s5 : var17) {
                final int j6 = LabyModCore.getMinecraft().getFontRenderer().a(s5);
                LabyModCore.getMinecraft().getFontRenderer().a(s5, (float)(width / 2 - j6 / 2), (float)var14, -1);
                var14 += LabyModCore.getMinecraft().getFontRenderer().a;
            }
        }
        if (LabyMod.getSettings().revealFamiliarUsers && LabyMod.getSettings().revealFamiliarUsersPercentage && !labyOnlyServer) {
            final int percent = (int)((totalCount == 0) ? 0L : Math.round(100.0 / totalCount * familiarCount));
            final String displayString = ModColor.cl('7') + familiarCount + ModColor.cl('8') + "/" + ModColor.cl('7') + totalCount + " " + ModColor.cl('a') + percent + "%";
            LabyMod.getInstance().getDrawUtils().drawRightString(displayString, width / 2 + var15 / 2, (serverBanner ? 60 : 10) - 7, 0.7);
        }
    }
    
    public void oldTabOverlay(final int width, final bhk scoreboardIn, final bhg scoreObjectiveIn) {
        final UserManager userManager = LabyMod.getInstance().getUserManager();
        int familiarCount = 0;
        int totalCount = 0;
        try {
            final brz var4 = LabyModCore.getMinecraft().getPlayer().d;
            final List var5 = ModPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)var4.d());
            int var7;
            final int var6 = var7 = var4.a;
            final bit var8 = LabyMod.getInstance().getDrawUtils().getScaledResolution();
            int var9 = 0;
            final int var10 = var8.a();
            int var11 = 0;
            int var12 = 0;
            int var13 = 0;
            for (var9 = 1; var7 > 20; var7 = (var6 + var9 - 1) / var9) {
                ++var9;
            }
            int var14 = 300 / var9;
            if (var14 > 150) {
                var14 = 150;
            }
            final int var15 = (var10 - var9 * var14) / 2;
            final byte var16 = 10;
            a(var15 - 1, var16 - 1, var15 + var14 * var9, var16 + 9 * var7, Integer.MIN_VALUE);
            for (var11 = 0; var11 < var6; ++var11) {
                var12 = var15 + var11 % var9 * var14;
                var13 = var16 + var11 / var9 * 9;
                a(var12, var13, var12 + var14 - 1, var13 + 8, 553648127);
                bus.e();
                if (var11 < var5.size()) {
                    final bsc var17 = var5.get(var11);
                    final String name = var17.a().getName();
                    final bhh var18 = LabyModCore.getMinecraft().getWorld().af().g(name);
                    final String var19 = this.a(var17);
                    boolean badgeVisible = false;
                    if (LabyMod.getSettings().revealFamiliarUsers) {
                        final User user = userManager.getUser(var17.a().getId());
                        if (user.isFamiliar()) {
                            final LabyGroup group = user.getGroup();
                            if (group != null) {
                                group.renderBadge(var12, var13, 8.0, 8.0, true);
                                badgeVisible = true;
                            }
                            ++familiarCount;
                        }
                    }
                    ++totalCount;
                    LabyMod.getInstance().getDrawUtils().drawString(var19, var12 + (badgeVisible ? 9 : 0), var13);
                    if (scoreObjectiveIn != null) {
                        final int var20 = var12 + LabyMod.getInstance().getDrawUtils().getStringWidth(var19) + 5;
                        final int var21 = var12 + var14 - 12 - 5;
                        if (var21 - var20 > 5) {
                            final bhi var22 = scoreboardIn.c(name, scoreObjectiveIn);
                            final String var23 = ChatFormatting.YELLOW + "" + var22.c();
                            LabyMod.getInstance().getDrawUtils().drawString(var23, var21 - LabyMod.getInstance().getDrawUtils().getStringWidth(var23), var13, 1.6777215E7);
                        }
                    }
                    this.a(50, var12 + var14 - 52, var13, var17);
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            bus.g();
            bus.e();
            if (LabyMod.getSettings().revealFamiliarUsers && LabyMod.getSettings().revealFamiliarUsersPercentage) {
                final int percent = (int)((totalCount == 0) ? 0L : Math.round(100.0 / totalCount * familiarCount));
                final String displayString = ModColor.cl('7') + familiarCount + ModColor.cl('8') + "/" + ModColor.cl('7') + totalCount + " " + ModColor.cl('a') + percent + "%";
                LabyMod.getInstance().getDrawUtils().drawRightString(displayString, var15 + var14 * var9, 3.0, 0.7);
            }
        }
        catch (Exception ex) {}
    }
    
    protected void a(final int p_175245_1_, final int p_175245_2_, final int p_175245_3_, final bsc networkPlayerInfoIn) {
        if (!LabyMod.getSettings().tabPing) {
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.N().a(ModPlayerTabOverlay.d);
            final byte var5 = 0;
            final boolean var6 = false;
            byte var7;
            if (networkPlayerInfoIn.c() < 0) {
                var7 = 5;
            }
            else if (networkPlayerInfoIn.c() < 150) {
                var7 = 0;
            }
            else if (networkPlayerInfoIn.c() < 300) {
                var7 = 1;
            }
            else if (networkPlayerInfoIn.c() < 600) {
                var7 = 2;
            }
            else if (networkPlayerInfoIn.c() < 1000) {
                var7 = 3;
            }
            else {
                var7 = 4;
            }
            this.e += 100.0f;
            this.b(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + var5 * 10, 176 + var7 * 8, 10, 8);
        }
        else {
            this.e += 100.0f;
        }
        final DrawUtils draw = LabyMod.getInstance().getDrawUtils();
        GL11.glPushMatrix();
        bus.a(0.5, 0.5, 0.5);
        int ping = networkPlayerInfoIn.c();
        if (ping >= 1000) {
            ping = 999;
        }
        if (ping < 0) {
            ping = 0;
        }
        String c = "a";
        if (ping > 150) {
            c = "2";
        }
        if (ping > 300) {
            c = "c";
        }
        if (ping > 600) {
            c = "4";
        }
        String showPing = "" + ping;
        if (ping == 0) {
            showPing = "?";
        }
        if (LabyMod.getSettings().tabPing) {
            draw.drawCenteredString(ModColor.cl(c) + showPing + "", (p_175245_2_ + p_175245_1_) * 2 - 12, p_175245_3_ * 2 + 5);
        }
        GL11.glPopMatrix();
        this.e -= 100.0f;
    }
    
    private void drawScoreboardValues(final bhg objective, final int p_175247_2_, final String name, final int p_175247_4_, final int p_175247_5_, final bsc info) {
        final int i = objective.a().c(name, objective).c();
        if (objective.e() == bhq.a.b) {
            this.mc.N().a(ModPlayerTabOverlay.d);
            if (this.lastTimeOpened == info.q()) {
                if (i < info.m()) {
                    info.a(bib.I());
                    info.b((long)(this.guiIngame.e() + 20));
                }
                else if (i > info.m()) {
                    info.a(bib.I());
                    info.b((long)(this.guiIngame.e() + 10));
                }
            }
            if (bib.I() - info.o() > 1000L || this.lastTimeOpened != info.q()) {
                info.b(i);
                info.c(i);
                info.a(bib.I());
            }
            info.c(this.lastTimeOpened);
            info.b(i);
            final int j = LabyModCore.getMath().ceiling_float_int(Math.max(i, info.n()) / 2.0f);
            final int k = Math.max(LabyModCore.getMath().ceiling_float_int((float)(i / 2)), Math.max(LabyModCore.getMath().ceiling_float_int((float)(info.n() / 2)), 10));
            final boolean flag = info.p() > this.guiIngame.e() && (info.p() - this.guiIngame.e()) / 3L % 2L == 1L;
            if (j > 0) {
                final float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0f);
                if (f > 3.0f) {
                    for (int l = j; l < k; ++l) {
                        this.a(p_175247_4_ + l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                    }
                    for (int j2 = 0; j2 < j; ++j2) {
                        this.a(p_175247_4_ + j2 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                        if (flag) {
                            if (j2 * 2 + 1 < info.n()) {
                                this.a(p_175247_4_ + j2 * f, (float)p_175247_2_, 70, 0, 9, 9);
                            }
                            if (j2 * 2 + 1 == info.n()) {
                                this.a(p_175247_4_ + j2 * f, (float)p_175247_2_, 79, 0, 9, 9);
                            }
                        }
                        if (j2 * 2 + 1 < i) {
                            this.a(p_175247_4_ + j2 * f, (float)p_175247_2_, (j2 >= 10) ? 160 : 52, 0, 9, 9);
                        }
                        if (j2 * 2 + 1 == i) {
                            this.a(p_175247_4_ + j2 * f, (float)p_175247_2_, (j2 >= 10) ? 169 : 61, 0, 9, 9);
                        }
                    }
                }
                else {
                    final float f2 = LabyModCore.getMath().clamp_float(i / 20.0f, 0.0f, 1.0f);
                    final int i2 = (int)((1.0f - f2) * 255.0f) << 16 | (int)(f2 * 255.0f) << 8;
                    String s = "" + i / 2.0f;
                    if (p_175247_5_ - LabyModCore.getMinecraft().getFontRenderer().a(s + "hp") >= p_175247_4_) {
                        s += "hp";
                    }
                    LabyModCore.getMinecraft().getFontRenderer().a(s, (float)((p_175247_5_ + p_175247_4_) / 2 - LabyModCore.getMinecraft().getFontRenderer().a(s) / 2), (float)p_175247_2_, i2);
                }
            }
        }
        else {
            final String s2 = ChatFormatting.YELLOW + "" + i;
            LabyModCore.getMinecraft().getFontRenderer().a(s2, (float)(p_175247_5_ - LabyModCore.getMinecraft().getFontRenderer().a(s2)), (float)p_175247_2_, 16777215);
        }
    }
    
    public void a(final hh footerIn) {
        super.a(footerIn);
        this.footer = footerIn;
        LabyMod.getInstance().getEventManager().callAllFooter(LabyModCore.getMinecraft().getChatComponent((Object)footerIn));
    }
    
    public void b(final hh headerIn) {
        super.b(headerIn);
        this.header = headerIn;
        LabyMod.getInstance().getEventManager().callAllHeader(LabyModCore.getMinecraft().getChatComponent((Object)headerIn));
    }
    
    public void resetFooterHeader() {
        super.b((hh)null);
        super.a((hh)null);
        this.header = null;
        this.footer = null;
    }
    
    public boolean isBeingRendered() {
        return this.isBeingRendered;
    }
    
    static {
        ENTRY_ORDERING = Ordering.from((Comparator)new PlayerComparator());
    }
    
    static class PlayerComparator implements Comparator<bsc>
    {
        private PlayerComparator() {
        }
        
        @Override
        public int compare(final bsc p_compare_1_, final bsc p_compare_2_) {
            final bhh scoreplayerteam = p_compare_1_.j();
            final bhh scoreplayerteam2 = p_compare_2_.j();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.b() != ams.e, p_compare_2_.b() != ams.e).compare((Comparable)((scoreplayerteam != null) ? scoreplayerteam.b() : ""), (Comparable)((scoreplayerteam2 != null) ? scoreplayerteam2.b() : "")).compare((Comparable)p_compare_1_.a().getName(), (Comparable)p_compare_2_.a().getName()).result();
        }
    }
}
