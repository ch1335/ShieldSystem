package com.chen1335.shieldSystem.kubejs;

import com.chen1335.shieldSystem.events.RegisterShieldPriorityEvent;
import com.chen1335.shieldSystem.kubejs.kubeEvents.Events;
import com.chen1335.shieldSystem.kubejs.kubeEvents.RegisterShieldPriorityEventJS;

public class KubeCommon {
    public static void setPriority(RegisterShieldPriorityEvent event){
        Events.REGISTER_SHIELD_PRIORITY_EVENT.post(new RegisterShieldPriorityEventJS(event));
    }
}
