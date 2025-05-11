package com.chen1335.shieldSystem.shieldSystem;


import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.API.objects.ShieldTypes;
import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.API.shieldAPI.IUnitShield;
import com.chen1335.shieldSystem.attachmentTypes.EntityShield;
import com.chen1335.shieldSystem.events.RegisterShieldPriorityEvent;
import com.chen1335.shieldSystem.kubejs.kubeEvents.Events;
import com.chen1335.shieldSystem.kubejs.kubeEvents.RegisterShieldPriorityEventJS;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class Shield {
    public static ImmutableList<ShieldType<? extends IShield>> SHIELDS_PRIORITY = ImmutableList.of();

    public static ImmutableList<ShieldType<? extends IShield>> getShieldsPriority() {
        return SHIELDS_PRIORITY;
    }

    public static void handleShieldAbsorb(LivingDamageEvent.Pre event) {
        LivingEntity livingEntity = event.getEntity();
        EntityShield entityShield = livingEntity.getData(AttachmentTypes.ENTITY_SHIELD);
        for (ShieldType<? extends IShield> holder : SHIELDS_PRIORITY) {
            IShield shield = entityShield.shieldInstanceHolders.get(holder).getShield();
            if (shield != null && shield.getTotalAmount() > 0) {
                float newDamage = shield.tryAbsorb(livingEntity, shield, event.getSource(), event.getNewDamage());
                event.setNewDamage(newDamage);
            }
        }
    }


    public static void setPriority() {
        List<ShieldType<? extends IShield>> shieldsPriority = new ArrayList<>(64);
        shieldsPriority.add(ShieldTypes.TEST.get());
        RegisterShieldPriorityEvent event = new RegisterShieldPriorityEvent(shieldsPriority);
        NeoForge.EVENT_BUS.post(event);
        if (ModList.get().isLoaded("kubejs")) {
            Events.REGISTER_SHIELD_PRIORITY_EVENT.post(new RegisterShieldPriorityEventJS(event));
        }
        SHIELDS_PRIORITY = ImmutableList.copyOf(shieldsPriority);
    }

    public record ShieldType<T extends IShield>(ShieldFactory<T> factory) {
    }

    public interface ShieldFactory<T extends IShield> {
        T create();
    }

    public interface UnitShieldFactory<T extends IUnitShield> {
        T create();
    }
}
