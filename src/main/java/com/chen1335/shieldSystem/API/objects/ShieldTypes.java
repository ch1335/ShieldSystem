package com.chen1335.shieldSystem.API.objects;

import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.UnitShield;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ShieldTypes {
    public static final DeferredRegister<Shield.ShieldType<?>> SHIELD_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.SHIELD_TYPE, ShieldSystem.MODID);

    public static final DeferredHolder<Shield.ShieldType<?>, Shield.ShieldType<UnitShield>> TEST = SHIELD_TYPE_DEFERRED_REGISTER.register("test", () -> new Shield.ShieldType<>(UnitShield::new));

}
