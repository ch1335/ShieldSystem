package com.chen1335.shieldSystem.network;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.API.objects.Registries;
import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import com.chen1335.shieldSystem.shieldSystem.ShieldInstanceHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ShieldPack(Shield.ShieldType<? extends IShield> shieldType, float amount) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ShieldPack> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ShieldSystem.MODID, "shield_pack"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShieldPack> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.SHIELD_TYPE_KEY),
            ShieldPack::shieldType,
            ByteBufCodecs.FLOAT,
            ShieldPack::amount,
            ShieldPack::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handler(IPayloadContext context) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ShieldInstanceHolder<?> instanceHolder = player.getData(AttachmentTypes.ENTITY_SHIELD).getShieldInstance(shieldType);
        if (instanceHolder != null) {
            instanceHolder.setRenderAmount(amount);
        }
    }
}
