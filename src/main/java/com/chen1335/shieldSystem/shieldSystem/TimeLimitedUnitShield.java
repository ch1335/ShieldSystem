package com.chen1335.shieldSystem.shieldSystem;

import net.minecraft.world.entity.LivingEntity;

public class TimeLimitedUnitShield extends UnitShield {
    private int timeLeft = 0;

    public TimeLimitedUnitShield(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        super.tick(livingEntity);
        if (getAmount() > 0) {
            if (timeLeft <= 0) {
                setAmount(0);
            }
            timeLeft--;
        }
    }
}
