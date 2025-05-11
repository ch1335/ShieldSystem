package com.chen1335.shieldSystem.shieldSystem;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class DecayShield extends UnitShield {
    private float maxAmount;
    private int time;

    public DecayShield(int time, float initAmount) {
        super(initAmount);
        maxAmount = initAmount;
        this.time = time;
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        super.tick(livingEntity);
        reduceShieldAmount(maxAmount / time);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = super.serializeNBT(provider);
        tag.putInt("time", time);
        tag.putFloat("maxAmount", maxAmount);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
        time = nbt.getInt("time");
        maxAmount = nbt.getFloat("maxAmount");
    }
}
