package com.chen1335.shieldSystem.API.shieldAPI;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.ShieldInstanceHolder;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ShieldAPI {

    @Nullable
    public static <T extends IShield> ShieldInstanceHolder<T> getShieldInstance(LivingEntity livingEntity, Shield.ShieldType<T> shieldType) {
        return livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(shieldType);
    }

    public static void addShieldAmount(LivingEntity livingEntity, Shield.ShieldType<?> shieldType, float amount) {
        livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).addShieldAmount(livingEntity, shieldType, amount);
    }

    public static void reduceShieldAmount(LivingEntity livingEntity, Shield.ShieldType<?> shieldType, float amount) {
        livingEntity.getData(AttachmentTypes.ENTITY_SHIELD).addShieldAmount(livingEntity, shieldType, amount);
    }
}
