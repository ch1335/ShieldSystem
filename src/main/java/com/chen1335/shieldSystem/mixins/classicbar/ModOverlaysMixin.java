package com.chen1335.shieldSystem.mixins.classicbar;

import com.chen1335.shieldSystem.classicbar.ShieldBarOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.classicbar.ClassicBar;
import tfar.classicbar.client.EventHandler;
import tfar.classicbar.register.ModOverlays;

@Mixin(ModOverlays.class)
public class ModOverlaysMixin {
    @Inject(method = "register", at = @At("RETURN"), remap = false)
    private static void onRegister(CallbackInfo ci) {
        ClassicBar.logger.info("Registering Shield compat overlay (Shield System)");
        EventHandler.register(new ShieldBarOverlay());
    }
}
