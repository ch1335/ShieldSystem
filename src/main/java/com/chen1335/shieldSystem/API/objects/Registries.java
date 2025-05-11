package com.chen1335.shieldSystem.API.objects;

import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class Registries {
    public static final ResourceKey<Registry<Shield.ShieldType<? extends IShield>>> SHIELD_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ShieldSystem.MODID, "shield_type"));
    public static final Registry<Shield.ShieldType<? extends IShield>> SHIELD_TYPE = new RegistryBuilder<>(SHIELD_TYPE_KEY)
            .sync(true)
            .create();
}
