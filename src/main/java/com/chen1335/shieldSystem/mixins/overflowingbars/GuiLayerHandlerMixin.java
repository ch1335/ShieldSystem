package com.chen1335.shieldSystem.mixins.overflowingbars;

import com.chen1335.shieldSystem.overflowingbars.OverFlowingBarsMain;
import fuzs.overflowingbars.client.handler.GuiLayerHandler;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiLayerHandler.class)
public class GuiLayerHandlerMixin {
    @Inject(method = "onRenderPlayerHealth", at = @At("RETURN"))
    private static void onRenderPlayerHealth(Minecraft minecraft, GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfoReturnable<EventResult> cir) {
        if (cir.getReturnValue() == EventResult.INTERRUPT) {
            OverFlowingBarsMain.onRenderPlayerShield(minecraft, guiGraphics, deltaTracker);
        }
    }
}
