package com.chen1335.shieldSystem.shieldSystem;

import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.events.AddShieldAmountEvent;
import com.chen1335.shieldSystem.network.ShieldPack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

public class ShieldInstanceHolder<T extends IShield> {
    private float renderAmount = 0;

    private final T shield;

    private final Shield.ShieldType<T> type;
    private float oldAmount = 0;

    public ShieldInstanceHolder(Shield.ShieldType<T> type, T shield) {
        this.shield = shield;
        this.type = type;
    }


    public T getShield() {
        return shield;
    }

    public void tick(LivingEntity living) {
        shield.tick(living);
        if (shield.getTotalAmount() != oldAmount) {
            oldAmount = shield.getTotalAmount();
            if (living instanceof ServerPlayer player) {
                sycShieldAmount(player);
            }
        }
    }


    public void sycShieldAmount(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new ShieldPack(type, getTotalAmount()));
    }

    public Shield.ShieldType<T> getType() {
        return type;
    }

    public float getTotalAmount() {
        return shield.getTotalAmount();
    }

    public float getRenderAmount() {
        return renderAmount;
    }

    public void setRenderAmount(float renderAmount) {
        this.renderAmount = renderAmount;
    }

    public void addAmount(LivingEntity livingEntity, float amount) {
        AddShieldAmountEvent event = new AddShieldAmountEvent(livingEntity, type, this, amount);
        NeoForge.EVENT_BUS.post(event);
        getShield().addShieldAmount(amount);
    }

    public void reduceAmount(LivingEntity livingEntity, float amount) {
        getShield().reduceShieldAmount(amount);
    }
}
