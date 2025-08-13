package com.chen1335.shieldSystem.overflowingbars;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.attachmentTypes.EntityShield;
import com.chen1335.shieldSystem.client.AdditionalHeartRender;
import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.overflowingbars.client.gui.RowCountRenderer;
import fuzs.puzzleslib.api.client.core.v1.ClientAbstractions;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;

public class OverFlowingBarsMain {
    public static ResourceLocation PLAYER_SHIELD = ShieldSystem.id("player_shield");

    public static void onRenderPlayerShield(Minecraft minecraft, GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = minecraft.player;
        if (player == null) {
            return;
        }
        int guiLeftHeight = ClientAbstractions.INSTANCE.getGuiLeftHeight(minecraft.gui);
        EntityShield entityShield = player.getData(AttachmentTypes.ENTITY_SHIELD);
        float shieldAmount = entityShield.getRenderShieldAmount();
        int shieldAmountCeil = Mth.ceil(shieldAmount);
        if (shieldAmountCeil > 0) {
            ClientAbstractions.INSTANCE.addGuiLeftHeight(minecraft.gui, 10);
        }

        int posX = guiGraphics.guiWidth() / 2 - 91;
        int posY = guiGraphics.guiHeight() - guiLeftHeight;


        renderShieldHeart(guiGraphics, posX, posY, player, minecraft.getProfiler(), shieldAmountCeil);
        if (shieldAmountCeil > 20) {
            RowCountRenderer.drawBarRowCount(guiGraphics, posX - 2, posY, shieldAmountCeil, true, minecraft.font);
        }
    }

    private static void renderShieldHeart(GuiGraphics guiGraphics, int posX, int posY, Player player, ProfilerFiller profiler, int shieldAmountCeil) {
        int hearts = (int) Math.min(10, Math.ceil((double) shieldAmountCeil / 2));


        for (int i = 0; i < hearts; i++) {
            AdditionalHeartRender.renderHeartBackGround(guiGraphics, posX + i * 8, posY);
            if (shieldAmountCeil <= 20) {
                AdditionalHeartRender.renderHeart(guiGraphics, posX + i * 8, posY, i == hearts - 1 && shieldAmountCeil % 2 != 0);
            }
        }

        if (shieldAmountCeil > 20) {
            for (int i = 0; i < 10; i++) {
                renderShieldBackHeart(guiGraphics, posX + i * 8, posY, false);
            }
            int current = shieldAmountCeil % 20;
            int currentHearts = (int) Math.ceil((double) current / 2);
            if (current > 0) {
                for (int i = 0; i < currentHearts; i++) {
                    AdditionalHeartRender.renderHeart(guiGraphics, posX + i * 8, posY, i == currentHearts - 1 && shieldAmountCeil % 2 != 0);
                }
            }
        }
    }

    private static void renderShieldBackHeart(GuiGraphics guiGraphics, int x, int y, boolean halfHeart) {
        RenderSystem.enableBlend();
        if (halfHeart) {
            guiGraphics.blit(AdditionalHeartRender.SHIELD_HEART, x, y, 9, 9, 9, 9, 90, 90);
        } else {
            guiGraphics.blit(AdditionalHeartRender.SHIELD_HEART, x, y, 9, 0, 9, 9, 90, 90);
        }
        RenderSystem.disableBlend();
    }
}
