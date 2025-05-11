package com.chen1335.shieldSystem.kubejs;

import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.UnitShield;
import dev.latvian.mods.rhino.NativeObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Supplier;

public class KubeUnitShield extends UnitShield {
    private final NativeObject kubeObject;

    private final TriConsumer<KubeUnitShield, NativeObject, LivingEntity> tick;

    private final TriConsumer<KubeUnitShield, NativeObject, CompoundTag> save;

    private final TriConsumer<KubeUnitShield, NativeObject, CompoundTag> load;

    public KubeUnitShield(NativeObject kubeObject,
                          TriConsumer<KubeUnitShield, NativeObject, LivingEntity> tickFunc,
                          TriConsumer<KubeUnitShield, NativeObject, CompoundTag> save,
                          TriConsumer<KubeUnitShield, NativeObject, CompoundTag> load
    ) {
        this.kubeObject = kubeObject;
        this.tick = tickFunc;
        this.save = save;
        this.load = load;
    }

    public NativeObject getKubeObject() {
        return kubeObject;
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        super.tick(livingEntity);
        tick.accept(this, kubeObject, livingEntity);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag old = super.serializeNBT(provider);
        save.accept(this, kubeObject, old);
        return old;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
        load.accept(this, kubeObject, nbt);
    }

    public static class Builder<T extends NativeObject> {
        private final Supplier<T> kubeObjectSupplier;
        private TriConsumer<KubeUnitShield, NativeObject, LivingEntity> tick = (n, t, living) -> {
        };
        private TriConsumer<KubeUnitShield, NativeObject, CompoundTag> save = (n, t, nbt) -> {
        };

        private TriConsumer<KubeUnitShield, NativeObject, CompoundTag> load = (n, t, nbt) -> {
        };

        public Builder(Supplier<T> kubeObjectSupplier) {
            this.kubeObjectSupplier = kubeObjectSupplier;
        }

        public static <T extends NativeObject> Builder<T> of(Supplier<T> kubeObjectSupplier) {
            return new Builder<>(kubeObjectSupplier);
        }

        public Builder<T> tick(TriConsumer<KubeUnitShield, NativeObject, LivingEntity> tickFunc) {
            tick = tickFunc;
            return this;
        }

        public Builder<T> save(TriConsumer<KubeUnitShield, NativeObject, CompoundTag> save) {
            this.save = save;
            return this;
        }

        public Builder<T> load(TriConsumer<KubeUnitShield, NativeObject, CompoundTag> load) {
            this.load = load;
            return this;
        }

        public Shield.ShieldFactory<?> build() {
            return () -> new KubeUnitShield(kubeObjectSupplier.get(), tick, save, load);
        }
    }
}
