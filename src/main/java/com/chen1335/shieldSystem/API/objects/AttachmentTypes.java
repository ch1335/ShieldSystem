package com.chen1335.shieldSystem.API.objects;

import com.chen1335.shieldSystem.ShieldSystem;
import com.chen1335.shieldSystem.attachmentTypes.EntityShield;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ShieldSystem.MODID);


    public static final Supplier<AttachmentType<EntityShield>> ENTITY_SHIELD = ATTACHMENT_TYPES.register(
            "entity_shield", () -> AttachmentType.serializable(EntityShield::new).build()
    );
}
