package com.chen1335.shieldSystem.API.objects;

import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.shieldSystem.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ShieldTypes {
    public static final DeferredRegister<Shield.ShieldType<?>> SHIELD_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.SHIELD_TYPE, ShieldSystem.MODID);

    public static final DeferredHolder<Shield.ShieldType<?>, Shield.ShieldType<UnitShield>> TEST = SHIELD_TYPE_DEFERRED_REGISTER.register("test", () -> new Shield.ShieldType<>(UnitShield::new));

    public static final DeferredHolder<Shield.ShieldType<?>, Shield.ShieldType<GroupShield<TimeLimitedUnitShield>>> COMMON_TIME_LIMITED = SHIELD_TYPE_DEFERRED_REGISTER.register("common_time_limited", () -> new Shield.ShieldType<>(() -> new GroupShield<>(initAmount -> new TimeLimitedUnitShield(100, initAmount))));

    public static final DeferredHolder<Shield.ShieldType<?>, Shield.ShieldType<GroupShield<DecayShield>>> COMMON_DECAY = SHIELD_TYPE_DEFERRED_REGISTER.register("common_decay", () -> new Shield.ShieldType<>(() -> new GroupShield<>(initAmount -> new DecayShield(100, initAmount))));

}
