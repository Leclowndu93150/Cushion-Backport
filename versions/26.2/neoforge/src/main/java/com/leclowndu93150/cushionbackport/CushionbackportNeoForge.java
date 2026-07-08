package com.leclowndu93150.cushionbackport;

import com.leclowndu93150.cushionbackport.neoforge.CBRegistryNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("cushionbackport")
public class CushionbackportNeoForge {

    public CushionbackportNeoForge(IEventBus modBus) {
        CBRegistryNeoForge.init(modBus);
        Cushionbackport.init();
    }
}
