package com.chen1335.shieldSystem.kubejs.kubeEvents;

import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.events.RegisterShieldPriorityEvent;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import dev.latvian.mods.kubejs.event.KubeEvent;

import java.util.List;

public class RegisterShieldPriorityEventJS implements KubeEvent {
    private final RegisterShieldPriorityEvent event;

    public RegisterShieldPriorityEventJS(RegisterShieldPriorityEvent event) {
        this.event = event;
    }

    public List<Shield.ShieldType<? extends IShield>> getShieldsPriority() {
        return event.getShieldsPriority();
    }

    public void addShieldType(Shield.ShieldType<? extends IShield> shieldType) {
        event.addShieldType(shieldType);
    }
}
