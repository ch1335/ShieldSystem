package com.chen1335.shieldSystem.API.shieldAPI;

public interface IUnitShield extends IShield {
    @Override
    default float getTotalAmount() {
        return getAmount();
    }

    default void addShieldAmount(float amount) {
        setAmount(getAmount() + amount);
    }

    default void addShieldAmount(float amount, float max) {
        setAmount(Math.min(max, getAmount() + amount));
    }

    default void reduceShieldAmount(float amount) {
        setAmount(Math.max(0, getAmount() - amount));
    }

    float getAmount();

    void setAmount(float amount);
}
