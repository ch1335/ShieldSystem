package com.chen1335.shieldSystem.events;


import com.chen1335.shieldSystem.API.shieldAPI.IShield;
import com.chen1335.shieldSystem.shieldSystem.Shield;
import net.neoforged.bus.api.Event;

import java.util.List;

public class RegisterShieldPriorityEvent extends Event {

    private final List<Shield.ShieldType<? extends IShield>> shieldsPriority;

    public RegisterShieldPriorityEvent(List<Shield.ShieldType<? extends IShield>> shieldsPriority) {
        this.shieldsPriority = shieldsPriority;
    }

    public List<Shield.ShieldType<? extends IShield>> getShieldsPriority() {
        return shieldsPriority;
    }

    public void addShieldType(Shield.ShieldType<? extends IShield> shieldType) {
        shieldsPriority.add(shieldType);
    }

}
