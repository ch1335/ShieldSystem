package com.chen1335.shieldSystem;

import com.chen1335.shieldSystem.API.objects.AttachmentTypes;
import com.chen1335.shieldSystem.API.objects.ShieldTypes;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ShieldSystem.MODID)
public class ShieldSystem {
    public static final String MODID = "shield_system";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ShieldSystem(IEventBus modEventBus, ModContainer modContainer) {
        AttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        ShieldTypes.SHIELD_TYPE_DEFERRED_REGISTER.register(modEventBus);

    }
}
