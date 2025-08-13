package com.chen1335.shieldSystem.API.shieldAPI;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.API.objects.ShieldTypes;
import com.chen1335.shieldSystem.attachmentTypes.EntityShield;
import com.chen1335.shieldSystem.events.AddShieldAmountEvent;
import com.chen1335.shieldSystem.shieldSystem.*;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

public class ShieldAPI {

    @Nullable
    public static <T extends IShield> ShieldInstanceHolder<T> getShieldInstance(LivingEntity livingEntity, Shield.ShieldType<T> shieldType) {
        return livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(shieldType);
    }

    public static void addShieldAmount(LivingEntity livingEntity, Shield.ShieldType<? extends IShield> shieldType, float amount) {
        @Nullable ShieldInstanceHolder<? extends IShield> instanceHolder = livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(shieldType);
        if (instanceHolder != null) {
            AddShieldAmountEvent event = new AddShieldAmountEvent(livingEntity, shieldType, instanceHolder, amount);
            NeoForge.EVENT_BUS.post(event);
            instanceHolder.getShield().addShieldAmount(event.getAmount());
        }
    }

    public static <T extends IUnitShield> void addUnitShieldToGroup(LivingEntity livingEntity, Shield.ShieldType<GroupShield<T>> shieldType, T unit) {
        @Nullable ShieldInstanceHolder<GroupShield<T>> instanceHolder = livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(shieldType);
        if (instanceHolder != null) {
            AddShieldAmountEvent event = new AddShieldAmountEvent(livingEntity, shieldType, instanceHolder, unit.getAmount());
            NeoForge.EVENT_BUS.post(event);
            unit.setAmount(event.getAmount());
            instanceHolder.getShield().addUnit(unit);
        }
    }

    public static void addCommonTimeLimitedShield(LivingEntity livingEntity, float amount, int time) {
        @Nullable ShieldInstanceHolder<GroupShield<TimeLimitedUnitShield>> instanceHolder = livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(ShieldTypes.COMMON_TIME_LIMITED.get());
        if (instanceHolder != null) {
            AddShieldAmountEvent event = new AddShieldAmountEvent(livingEntity, ShieldTypes.COMMON_TIME_LIMITED.get(), instanceHolder, amount);
            NeoForge.EVENT_BUS.post(event);
            instanceHolder.getShield().addUnit(new TimeLimitedUnitShield(time, event.getAmount()));
        }
    }

    public static void addCommonDecayShield(LivingEntity livingEntity, float amount, int time) {
        @Nullable ShieldInstanceHolder<GroupShield<DecayShield>> instanceHolder = livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(ShieldTypes.COMMON_DECAY.get());
        if (instanceHolder != null) {
            AddShieldAmountEvent event = new AddShieldAmountEvent(livingEntity, ShieldTypes.COMMON_DECAY.get(), instanceHolder, amount);
            NeoForge.EVENT_BUS.post(event);
            instanceHolder.getShield().addUnit(new DecayShield(time, event.getAmount()));
        }
    }

    public static void reduceShieldAmount(LivingEntity livingEntity, Shield.ShieldType<?> shieldType, float amount) {
        livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).reduceTotalShieldAmount(livingEntity, shieldType, amount);
    }

    public static void reduceTotalShieldAmount(LivingEntity livingEntity, float amount) {
        livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).reduceTotalShieldAmount(amount);
    }

    public static EntityShield getEntityShield(LivingEntity livingEntity) {
        return livingEntity.getData(AttachmentTypes.ENTITY_SHIELD);
    }

    //for server
    public static float getTotalShield(LivingEntity livingEntity) {
        return livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldAmount();
    }

    //for client
    public static float getRenderShield(LivingEntity livingEntity) {
        return livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getRenderShieldAmount();
    }
}
