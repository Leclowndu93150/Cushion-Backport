package com.leclowndu93150.cushionbackport;

import com.leclowndu93150.cushionbackport.forge.CBRegistryForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("cushionbackport")
public class CushionbackportForge {

    public CushionbackportForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        CBRegistryForge.init(modBus);
        Cushionbackport.init();
    }
}
