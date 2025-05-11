package com.chen1335.shieldSystem.kubejs.builders;

import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.shieldSystem.GroupShield;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.TimeLimitedUnitShield;
import com.chen1335.shieldSystem.shieldSystem.UnitShield;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import net.minecraft.resources.ResourceLocation;

public class ShieldTypeBuilder extends BuilderBase<Shield.ShieldType<? extends IShield>> {
    private Shield.ShieldFactory<?> factory = UnitShield::new;

    public ShieldTypeBuilder(ResourceLocation id) {
        super(id);
    }

    public ShieldTypeBuilder shieldFactory(Shield.ShieldFactory<?> supplier) {
        factory = supplier;
        return this;
    }


    public void createSimpleUnitShield() {
        factory = UnitShield::new;
    }

    public void createSimpleGroupShield() {
        factory = () -> new GroupShield<>(UnitShield::new);
    }

    public void createTimeLimitedUnitShield(int time) {
        factory = () -> new TimeLimitedUnitShield(time);
    }

    public void createTimeLimitedGroupShield(int time) {
        factory = () -> new GroupShield<>(() -> new TimeLimitedUnitShield(time));
    }

    @Override
    public Shield.ShieldType<?> createObject() {
        return new Shield.ShieldType<>(factory);
    }
}
