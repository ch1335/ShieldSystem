package com.chen1335.shieldSystem.client;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.attachmentTypes.EntityShield;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class AdditionalHeartRender {
    public static final ResourceLocation SHIELD_HEART = ResourceLocation.fromNamespaceAndPath(ShieldSystem.MODID, "textures/hud/shield_heart.png");

    public static void render(GuiGraphics guiGraphics) {

        Gui gui = Minecraft.getInstance().gui;
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        renderShieldHeart(gui, player, guiGraphics);
    }

    public static void renderShieldHeart(Gui gui, Player player, GuiGraphics guiGraphics) {
        EntityShield entityShield = player.getData(AttachmentTypes.ENTITY_SHIELD);

        int renderShieldAmount = (int) entityShield.getRenderShieldAmount();
        float f = Math.max((float) player.getAttributeValue(Attributes.MAX_HEALTH), renderShieldAmount);
        int k1 = Mth.ceil(player.getAbsorptionAmount());
        int l1 = Mth.ceil((f + (float) k1) / 2.0F / 10.0F);
        int height = Math.max(10 - (l1 - 2), 3);
        int l2 = Mth.ceil(renderShieldAmount / 20F);
        int x = guiGraphics.guiWidth() / 2 - 91;
        int y = guiGraphics.guiHeight() - gui.leftHeight;
        gui.leftHeight += (l2 - 1) * height + 10 - (10 - height);
        renderHearts(guiGraphics, x, y + (10 - height), height, renderShieldAmount, (int) renderShieldAmount);
    }

    private static void renderHearts(
            GuiGraphics guiGraphics,
            int x,
            int y,
            int height,
            float maxHealth,
            int currentHealth
    ) {
        int i = Mth.ceil((double) maxHealth / 2.0);
        for (int l = i - 1; l >= 0; l--) {
            int i1 = l / 10;
            int j1 = l % 10;
            int k1 = x + j1 * 8;
            int l1 = y - i1 * height;
            renderHeartBackGround(guiGraphics, k1, l1);
            int i2 = l * 2;
            if (i2 < currentHealth) {
                boolean flag4 = i2 + 1 == currentHealth;
                renderHeart(guiGraphics, k1, l1, flag4);
            }
        }
    }


    public static void renderHeart(GuiGraphics guiGraphics, int x, int y, boolean halfHeart) {
        RenderSystem.enableBlend();
        if (halfHeart) {
            guiGraphics.blit(SHIELD_HEART, x, y, 9, 9, 0, 9, 9, 9, 90, 90);
        } else {
            guiGraphics.blit(SHIELD_HEART, x, y, 0, 0, 9, 9, 90, 90);
        }
        RenderSystem.disableBlend();
    }

    public static void renderHeartBackGround(GuiGraphics guiGraphics, int x, int y) {
        RenderSystem.enableBlend();
        guiGraphics.blitSprite(Gui.HeartType.CONTAINER.getSprite(false, false, false), x, y, 9, 9);
        RenderSystem.disableBlend();
    }
}
