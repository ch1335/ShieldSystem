package com.chen1335.shieldSystem.events;

import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.ShieldInstanceHolder;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class AddShieldAmountEvent extends Event {
    private final LivingEntity livingEntity;
    private final Shield.ShieldType<?> shieldType;
    private final ShieldInstanceHolder<?> instanceHolder;
    private float amount;

    public AddShieldAmountEvent(LivingEntity livingEntity, Shield.ShieldType<?> shieldType, ShieldInstanceHolder<?> instanceHolder, float amount) {
        this.livingEntity = livingEntity;
        this.shieldType = shieldType;
        this.instanceHolder = instanceHolder;
        this.amount = amount;
    }


    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ShieldInstanceHolder<?> getInstanceHolder() {
        return instanceHolder;
    }

    public Shield.ShieldType<?> getShieldType() {
        return shieldType;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }
}
