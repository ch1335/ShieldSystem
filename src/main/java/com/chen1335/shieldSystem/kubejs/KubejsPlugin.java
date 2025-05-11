package com.chen1335.shieldSystem.kubejs;

import com.chen1335.shieldSystem.API.objects.Registries;
import com.chen1335.shieldSystem.API.shieldAPI.ShieldAPI;
import com.chen1335.shieldSystem.events.RegisterShieldPriorityEvent;
import com.chen1335.shieldSystem.kubejs.builders.ShieldTypeBuilder;
import com.chen1335.shieldSystem.kubejs.kubeEvents.Events;
import com.chen1335.shieldSystem.shieldSystem.GroupShield;
import com.chen1335.shieldSystem.shieldSystem.UnitShield;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class KubejsPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(Events.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("ShieldAPI", ShieldAPI.class);
        bindings.add("UnitShieldBuilder", KubeUnitShield.Builder.class);
        bindings.add("UnitShield", UnitShield.class);
        bindings.add("GroupShield", GroupShield.class);
        bindings.add("KubeUnitShield", KubeUnitShield.class);
        bindings.add("RegisterShieldPriorityEvent", RegisterShieldPriorityEvent.class);
    }

    @Override
    public void initStartup() {
        KubeJSPlugin.super.initStartup();
    }

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.SHIELD_TYPE_KEY, reg -> {
            reg.addDefault(ShieldTypeBuilder.class, ShieldTypeBuilder::new);
        });
    }
}
