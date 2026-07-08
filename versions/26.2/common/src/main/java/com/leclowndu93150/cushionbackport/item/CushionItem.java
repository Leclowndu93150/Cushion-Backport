package com.leclowndu93150.cushionbackport.item;

import com.leclowndu93150.cushionbackport.entity.Cushion;
import com.leclowndu93150.cushionbackport.registry.CBSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CushionItem extends Item {
    private final DyeColor color;

    public CushionItem(Item.Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Direction clickedFace = context.getClickedFace();
        if (clickedFace != Direction.UP) {
            return InteractionResult.FAIL;
        }

        Level level = context.getLevel();
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        BlockPos blockPos = placeContext.getClickedPos();
        Vec3 entityPos = new Vec3(blockPos.getX() + 0.5, context.getClickLocation().y, blockPos.getZ() + 0.5);
        AABB spawnAABB = new AABB(
            entityPos.x - 0.5, entityPos.y, entityPos.z - 0.5,
            entityPos.x + 0.5, entityPos.y + 0.25, entityPos.z + 0.5
        );
        if (!Cushion.wouldSurviveAt(level, spawnAABB)) {
            return InteractionResult.FAIL;
        }

        ItemStack itemStack = context.getItemInHand();
        if (level instanceof ServerLevel serverLevel) {
            if (!serverLevel.getEntitiesOfClass(Cushion.class, spawnAABB).isEmpty()) {
                return InteractionResult.FAIL;
            }

            Cushion cushion = new Cushion(serverLevel, blockPos);
            float yRot = Direction.fromYRot(placeContext.getRotation()).toYRot();
            cushion.snapTo(entityPos, yRot, 0.0F);
            cushion.setColor(this.color);
            if (!cushion.survives()) {
                return InteractionResult.FAIL;
            }

            serverLevel.addFreshEntity(cushion);
            level.playSound(null, cushion.getX(), cushion.getY(), cushion.getZ(), CBSounds.CUSHION_PLACE.get(), SoundSource.BLOCKS, 0.75F, 0.8F);
            cushion.gameEvent(GameEvent.ENTITY_PLACE);
            itemStack.consume(1, placeContext.getPlayer());
        }

        return InteractionResult.SUCCESS;
    }
}
