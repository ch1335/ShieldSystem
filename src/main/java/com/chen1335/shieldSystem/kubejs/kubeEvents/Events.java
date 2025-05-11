package com.chen1335.shieldSystem.kubejs.kubeEvents;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface Events {
    EventGroup GROUP = EventGroup.of("ShieldSystemEvents");

    EventHandler REGISTER_SHIELD_PRIORITY_EVENT = GROUP.startup("RegisterShieldPriority", () -> RegisterShieldPriorityEventJS.class);
}
