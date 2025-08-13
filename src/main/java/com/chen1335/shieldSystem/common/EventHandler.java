package com.chen1335.shieldSystem.common;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.API.objects.Registries;
import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.network.ShieldPack;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;

public class EventHandler {
    @EventBusSubscriber(modid = ShieldSystem.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class Game {
        @SubscribeEvent
        public static void EntityTickEvent$Post(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity living && !living.level().isClientSide()) {
                living.getData(AttachmentTypes.ENTITY_SHIELD).tick(living);
            }
        }

        @SubscribeEvent
        public static void LivingDamageEvent$Pre(LivingDamageEvent.Pre event) {
            Shield.handleShieldAbsorb(event);
        }

        @SubscribeEvent
        public static void onPLayerLoginIn(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getData(AttachmentTypes.ENTITY_SHIELD).sycAllShields(player);
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getData(AttachmentTypes.ENTITY_SHIELD).sycAllShields(player);
            }
        }
    }

    @EventBusSubscriber(modid = ShieldSystem.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod {
        @SubscribeEvent
        public static void RegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent event) {
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playBidirectional(ShieldPack.TYPE, ShieldPack.STREAM_CODEC, ShieldPack::handler);
        }

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            Shield.setPriority();
        }

        @SubscribeEvent
        public static void registerRegistries(NewRegistryEvent event) {
            event.register(Registries.SHIELD_TYPE);
        }

        @SubscribeEvent
        public static void registerGuiLayers(RegisterGuiLayersEvent event) {

        }
    }
}
