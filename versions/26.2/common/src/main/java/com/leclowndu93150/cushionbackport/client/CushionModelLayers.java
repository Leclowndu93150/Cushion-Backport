package com.leclowndu93150.cushionbackport.client;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class CushionModelLayers {
    public static final ModelLayerLocation CUSHION = new ModelLayerLocation(
        Identifier.fromNamespaceAndPath(Cushionbackport.MOD_ID, "cushion"), "main"
    );
}
