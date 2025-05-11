package com.chen1335.shieldSystem.API.shieldAPI;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IShield extends INBTSerializable<CompoundTag> {
    void tick(LivingEntity livingEntity);

    float getTotalAmount();

    void reduceShieldAmount(float amount);

    default float getAbsorbAmount(LivingEntity livingEntity, IShield shield, DamageSource damageSource, float amount) {
        return Math.min(shield.getTotalAmount(), amount);
    }

    void addShieldAmount(float amount);

    default float tryAbsorb(LivingEntity livingEntity, IShield shield, DamageSource damageSource, float amount) {
        float absorbAmount = getAbsorbAmount(livingEntity, shield, damageSource, amount);
        reduceShieldAmount(absorbAmount);
        return amount - absorbAmount;
    }
}
