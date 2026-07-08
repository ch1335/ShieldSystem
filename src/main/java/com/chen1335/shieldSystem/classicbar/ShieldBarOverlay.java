package com.chen1335.shieldSystem.classicbar;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.attachmentTypes.EntityShield;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tfar.classicbar.client.HudRenderContext;
import tfar.classicbar.impl.BarOverlayImpl;
import tfar.classicbar.util.Color;
import tfar.classicbar.util.ModUtils;

public class ShieldBarOverlay extends BarOverlayImpl {
    public static final ResourceLocation SHIELD_ICON = ResourceLocation.fromNamespaceAndPath("classicbar", "textures/gui/icons/shield_icon.png");
    private static final Color SHIELD_COLOR = Color.from(102, 204, 255); // 浅蓝色

    public ShieldBarOverlay() {
        super("shield_system_shield");
    }

    @Override
    public boolean shouldRender(Player player) {
        EntityShield entityShield = player.getData(AttachmentTypes.ENTITY_SHIELD);
        float renderShieldAmount = entityShield.getRenderShieldAmount();
        return renderShieldAmount > 0.0f;
    }

    @Override
    public void renderBar(HudRenderContext context, GuiGraphics graphics, Player player, int screenWidth, int screenHeight, int vOffset) {
        EntityShield entityShield = player.getData(AttachmentTypes.ENTITY_SHIELD);
        double shieldAmount = entityShield.getRenderShieldAmount();
        double maxHealth = player.getMaxHealth();
        double barWidth = this.getBarWidth(player);

        int xStart = screenWidth / 2 + this.getHOffset();
        int yStart = screenHeight - vOffset;

        if (this.rightHandSide()) {
            xStart = (int)((double)xStart + (77.0 - barWidth));
        }

        // 渲染背景
        this.renderBarBackground(graphics, player, screenWidth, screenHeight, vOffset);

        // 渲染护盾条
        this.applyConfiguredBarColor(SHIELD_COLOR);
        this.renderPartialBar(graphics, xStart + 2, yStart + 2, barWidth);
    }

    @Override
    public double getBarWidth(Player player) {
        EntityShield entityShield = player.getData(AttachmentTypes.ENTITY_SHIELD);
        double shieldAmount = entityShield.getRenderShieldAmount();
        double maxHealth = player.getMaxHealth();
        return Math.ceil(77.0 * Math.min(maxHealth, shieldAmount) / maxHealth);
    }

    @Override
    public Color getPrimaryBarColor(int index, Player player) {
        return SHIELD_COLOR;
    }

    @Override
    public boolean isFitted() {
        return true; // 护盾条适应实际宽度
    }

    @Override
    public void renderText(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
        EntityShield entityShield = player.getData(AttachmentTypes.ENTITY_SHIELD);
        double shieldAmount = entityShield.getRenderShieldAmount();
        this.renderSimpleText(graphics, width, height, vOffset, shieldAmount, player.getMaxHealth(), player);
    }


    @Override
    public void renderIcon(GuiGraphics graphics, Player player, int width, int height, int vOffset) {
        int xStart = width / 2 + this.getIconOffset();
        int yStart = height - vOffset;
        ModUtils.drawIconWithTexture(graphics, xStart, yStart, 9, getIconRL());
    }
}
