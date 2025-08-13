package com.chen1335.shieldSystem.attachmentTypes;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.API.objects.Registries;
import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.ShieldInstanceHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.chen1335.shieldSystem.shieldSystem.Shield.SHIELDS_PRIORITY;

public class EntityShield implements INBTSerializable<CompoundTag> {
    public final Map<Shield.ShieldType<? extends IShield>, ShieldInstanceHolder<IShield>> shieldInstanceHolders = new HashMap<>();

    public EntityShield(IAttachmentHolder holder) {
        SHIELDS_PRIORITY.forEach(shieldType -> {
            shieldInstanceHolders.put(shieldType, new ShieldInstanceHolder<>((Shield.ShieldType<IShield>) shieldType, shieldType.factory().create()));
        });
    }

    public void tick(LivingEntity livingEntity) {
        for (ShieldInstanceHolder<IShield> shield : shieldInstanceHolders.values()) {
            shield.tick(livingEntity);
        }
    }

    public float getShieldAmount() {
        float totalShields = 0;
        for (ShieldInstanceHolder<IShield> value : shieldInstanceHolders.values()) {
            totalShields += value.getTotalAmount();
        }
        return totalShields;
    }

    public float getRenderShieldAmount() {
        float totalShields = 0;
        for (ShieldInstanceHolder<IShield> value : shieldInstanceHolders.values()) {
            totalShields += value.getRenderAmount();
        }
        return totalShields;
    }

    @Nullable
    public <T extends IShield> ShieldInstanceHolder<T> getShieldInstance(Shield.ShieldType<T> shieldType) {
        return (ShieldInstanceHolder<T>) shieldInstanceHolders.get(shieldType);
    }

    public void sycAllShields(ServerPlayer player) {
        player.getData(AttachmentTypes.ENTITY_SHIELD).shieldInstanceHolders.forEach((shieldType, iShieldShieldInstanceHolder) -> {
            iShieldShieldInstanceHolder.sycShieldAmount(player);
        });
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        shieldInstanceHolders.forEach((shieldType, iShield) -> {
            tag.put(Objects.requireNonNull(Registries.SHIELD_TYPE.getKey(shieldType)).toString(), iShield.getShield().serializeNBT(provider));
        });
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag compoundTag) {
        Shield.getShieldsPriority().forEach(shieldType -> {
            ShieldInstanceHolder<?> instanceHolder = getShieldInstance(shieldType);
            if (instanceHolder != null) {
                instanceHolder.getShield().deserializeNBT(provider, compoundTag.getCompound(Objects.requireNonNull(Registries.SHIELD_TYPE.getKey(shieldType)).toString()));
            }
        });
    }

    public void addShieldAmount(LivingEntity livingEntity, Shield.ShieldType<?> shieldType, float amount) {
        ShieldInstanceHolder<?> instanceHolder = getShieldInstance(shieldType);
        if (instanceHolder != null) {
            instanceHolder.addAmount(livingEntity, amount);
        }
    }

    public void reduceTotalShieldAmount(LivingEntity livingEntity, Shield.ShieldType<?> shieldType, float amount) {
        ShieldInstanceHolder<?> instanceHolder = getShieldInstance(shieldType);
        if (instanceHolder != null) {
            instanceHolder.reduceAmount(livingEntity, amount);
        }
    }

    public void reduceTotalShieldAmount(float amount) {
        for (Shield.ShieldType<? extends IShield> holder : SHIELDS_PRIORITY) {
            ShieldInstanceHolder<IShield> instanceHolder = shieldInstanceHolders.get(holder);
            if (instanceHolder != null && instanceHolder.getTotalAmount() > 0) {
                instanceHolder.getShield().reduceShieldAmount(amount);
            }
        }
    }
}
