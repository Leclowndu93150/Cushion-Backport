package com.leclowndu93150.cushionbackport;

import com.leclowndu93150.cushionbackport.fabric.CBRegistryFabric;
import net.fabricmc.api.ModInitializer;

public class CushionbackportFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CBRegistryFabric.init();
        Cushionbackport.init();
    }
}
