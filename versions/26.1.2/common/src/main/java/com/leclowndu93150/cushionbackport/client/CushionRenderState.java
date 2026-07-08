package com.leclowndu93150.cushionbackport.client;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;

public class CushionRenderState extends EntityRenderState {
    public Direction direction = Direction.NORTH;
    public Identifier texture = Identifier.fromNamespaceAndPath(Cushionbackport.MOD_ID, "textures/entity/cushion/white_cushion.png");
}
