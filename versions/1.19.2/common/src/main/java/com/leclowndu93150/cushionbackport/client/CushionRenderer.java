package com.leclowndu93150.cushionbackport.client;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import com.leclowndu93150.cushionbackport.entity.Cushion;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.EnumMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class CushionRenderer extends EntityRenderer<Cushion> {
    private static final EnumMap<DyeColor, ResourceLocation> TEXTURES_BY_COLOR = Util.make(new EnumMap<>(DyeColor.class), textures -> {
        for (DyeColor color : DyeColor.values()) {
            textures.put(color, new ResourceLocation(Cushionbackport.MOD_ID, "textures/entity/cushion/" + color.getName() + "_cushion.png"));
        }
    });
    private final CushionModel model;

    public CushionRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CushionModel(context.bakeLayer(CushionModelLayers.CUSHION));
    }

    @Override
    public void render(Cushion cushion, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(Direction.fromYRot(cushion.getYRot()).toYRot()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        poseStack.translate(0.0, -0.25, 0.0);
        ResourceLocation texture = TEXTURES_BY_COLOR.get(cushion.getColor());
        this.model.renderToBuffer(
            poseStack,
            bufferSource.getBuffer(this.model.renderType(texture)),
            packedLight,
            OverlayTexture.NO_OVERLAY,
            1.0F,
            1.0F,
            1.0F,
            1.0F
        );
        poseStack.popPose();
        super.render(cushion, yaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Cushion cushion) {
        return TEXTURES_BY_COLOR.get(cushion.getColor());
    }
}
