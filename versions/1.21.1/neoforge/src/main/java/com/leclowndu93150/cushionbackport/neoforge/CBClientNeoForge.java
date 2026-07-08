package com.leclowndu93150.cushionbackport.neoforge;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import com.leclowndu93150.cushionbackport.client.CushionModel;
import com.leclowndu93150.cushionbackport.client.CushionModelLayers;
import com.leclowndu93150.cushionbackport.client.CushionRenderer;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Cushionbackport.MOD_ID, value = Dist.CLIENT)
public final class CBClientNeoForge {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CushionModelLayers.CUSHION, CushionModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CBEntities.CUSHION.get(), CushionRenderer::new);
    }
}
