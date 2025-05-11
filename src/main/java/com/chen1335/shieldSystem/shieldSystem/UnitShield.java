package com.chen1335.shieldSystem.shieldSystem;

import com.chen1335.shieldSystem.API.shieldAPI.IUnitShield;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class UnitShield implements IUnitShield {
    protected float amount = 0;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public UnitShield() {

    }

    public UnitShield(float amount) {
        this.amount = amount;
    }

    @Override
    public void tick(LivingEntity livingEntity) {

    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("amount", amount);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        this.amount = nbt.getFloat("amount");
    }
}
