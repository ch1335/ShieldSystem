package com.chen1335.shieldSystem.kubejs;

import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.UnitShield;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class KubeUnitShield<T> extends UnitShield {
    private final T kubeObject;

    private final BiConsumer<KubeUnitShield<T>, LivingEntity> tick;

    private final BiConsumer<KubeUnitShield<T>, CompoundTag> save;

    private final BiConsumer<KubeUnitShield<T>, CompoundTag> load;

    public KubeUnitShield(float initAmount, T kubeObject,
                          BiConsumer<KubeUnitShield<T>, LivingEntity> tickFunc,
                          BiConsumer<KubeUnitShield<T>, CompoundTag> save,
                          BiConsumer<KubeUnitShield<T>, CompoundTag> load
    ) {
        super(initAmount);
        this.kubeObject = kubeObject;
        this.tick = tickFunc;
        this.save = save;
        this.load = load;
    }

    public T getKubeObject() {
        return kubeObject;
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        super.tick(livingEntity);
        tick.accept(this, livingEntity);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag old = super.serializeNBT(provider);
        save.accept(this, old);
        return old;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider, nbt);
        load.accept(this, nbt);
    }

    public static class Builder<T> {
        private final Function<Float, T> kubeObjectSupplier;
        private BiConsumer<KubeUnitShield<T>, LivingEntity> tick = (n, living) -> {
        };
        private BiConsumer<KubeUnitShield<T>, CompoundTag> save = (n, nbt) -> {
        };

        private BiConsumer<KubeUnitShield<T>, CompoundTag> load = (n, nbt) -> {
        };

        public Builder(Function<Float, T> kubeObjectSupplier) {
            this.kubeObjectSupplier = kubeObjectSupplier;
        }

        public static <T> Builder<T> of(Function<Float, T> kubeObjectSupplier) {
            return new Builder<>(kubeObjectSupplier);
        }

        public Builder<T> tick(BiConsumer<KubeUnitShield<T>, LivingEntity> tickFunc) {
            tick = tickFunc;
            return this;
        }

        public Builder<T> save(BiConsumer<KubeUnitShield<T>, CompoundTag> save) {
            this.save = save;
            return this;
        }

        public Builder<T> load(BiConsumer<KubeUnitShield<T>, CompoundTag> load) {
            this.load = load;
            return this;
        }

        public Shield.UnitShieldFactory<?> build() {
            return (initAmount) -> new KubeUnitShield<T>(initAmount,kubeObjectSupplier.apply(initAmount), tick, save, load);
        }
    }
}
