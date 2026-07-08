package com.leclowndu93150.cushionbackport.forge;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import com.leclowndu93150.cushionbackport.client.CushionModel;
import com.leclowndu93150.cushionbackport.client.CushionModelLayers;
import com.leclowndu93150.cushionbackport.client.CushionRenderer;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = Cushionbackport.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class CBClientForge {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CushionModelLayers.CUSHION, CushionModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(CBEntities.CUSHION.get(), CushionRenderer::new);
    }
}
