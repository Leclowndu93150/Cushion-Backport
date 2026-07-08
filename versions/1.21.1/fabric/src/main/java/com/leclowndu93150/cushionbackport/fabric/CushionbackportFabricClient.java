package com.leclowndu93150.cushionbackport.fabric;

import com.leclowndu93150.cushionbackport.client.CushionModel;
import com.leclowndu93150.cushionbackport.client.CushionModelLayers;
import com.leclowndu93150.cushionbackport.client.CushionRenderer;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CushionbackportFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(CushionModelLayers.CUSHION, CushionModel::createBodyLayer);
        EntityRendererRegistry.register(CBEntities.CUSHION.get(), CushionRenderer::new);
    }
}
