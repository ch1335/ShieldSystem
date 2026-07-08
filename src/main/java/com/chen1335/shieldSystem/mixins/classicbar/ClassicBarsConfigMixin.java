package com.chen1335.shieldSystem.mixins.classicbar;

import com.chen1335.shieldSystem.classicbar.ShieldBarOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.classicbar.api.BarPlacement;
import tfar.classicbar.config.ClassicBarsConfig;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClassicBarsConfig.class)
public class ClassicBarsConfigMixin {
    @Shadow
    private static void registerBarConfig(ModConfigSpec.Builder builder, String name, ResourceLocation defaultIcon, boolean defaultShowText) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    private static String sectionKey(String name) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Mutable
    @Final
    @Shadow
    private static List<String> ACTIVE_BAR_IDS;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerConfig(ModConfigSpec.Builder builder, CallbackInfo ci) {
        String barId = "shield_system_shield";
        builder.translation(sectionKey("bars")).push("bars");
        registerBarConfig(builder, barId, ShieldBarOverlay.SHIELD_ICON, true);
        builder.pop();

        builder.translation(sectionKey("layout")).push("layout");
        builder.push(barId);
        ClassicBarsConfig.PLACEMENTS.put(barId, builder.defineEnum("placement", BarPlacement.LEFT));
        ClassicBarsConfig.SORT_PRIORITIES.put(barId, builder.defineInRange("sort_priority", 2, 1, 10));

        builder.pop();
        builder.pop();
    }

    @Inject(method = "<clinit>",at = @At("RETURN"))
    private static void clinit(CallbackInfo ci) {
        ArrayList<String> ids = new ArrayList<>(ACTIVE_BAR_IDS);
        ids.add("shield_system_shield");
        ACTIVE_BAR_IDS = ids;
    }
}
