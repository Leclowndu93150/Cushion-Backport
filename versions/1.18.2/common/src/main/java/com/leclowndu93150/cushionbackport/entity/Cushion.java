package com.leclowndu93150.cushionbackport.entity;

import com.leclowndu93150.cushionbackport.CushionConfig;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import com.leclowndu93150.cushionbackport.registry.CBItems;
import com.leclowndu93150.cushionbackport.registry.CBSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Cushion extends BlockAttachedEntity {
    private static final DyeColor DEFAULT_COLOR = DyeColor.WHITE;
    private static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(Cushion.class, EntityDataSerializers.INT);

    public Cushion(EntityType<? extends Cushion> type, Level level) {
        super(type, level);
    }

    public Cushion(Level level, BlockPos pos) {
        super(CBEntities.CUSHION.get(), level, pos);
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLOR));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(DATA_COLOR, color.getId());
    }

    @Override
    public void dropItem(Entity causedBy) {
        this.playSound(CBSounds.CUSHION_BREAK.get(), 1.0F, 1.0F);
        this.showBreakingParticles();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            if (!(causedBy instanceof Player player && player.getAbilities().instabuild)) {
                this.spawnAtLocation(CBItems.cushion(this.getColor()));
            }
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (player.isSecondaryUseActive() && held.getItem() instanceof DyeItem dyeItem) {
            DyeColor dyeColor = dyeItem.getDyeColor();
            if (dyeColor != this.getColor()) {
                if (!this.level.isClientSide()) {
                    this.setColor(dyeColor);
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        held.shrink(1);
                    }
                }
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            }
            return InteractionResult.PASS;
        }

        if (CushionConfig.get().preventCushionToCushionMovement && player.getVehicle() instanceof Cushion) {
            return InteractionResult.PASS;
        }

        if (!player.isSecondaryUseActive() && !this.isVehicle() && (this.level.isClientSide() || player.startRiding(this))) {
            if (!this.level.isClientSide()) {
                this.playSound(CBSounds.CUSHION_SIT.get(), 1.0F, 1.0F);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.level.isClientSide() && this.getRemovalReason() == null) {
            this.playSound(CBSounds.CUSHION_GET_UP.get(), 1.0F, 1.0F);
        }
    }

    @Override
    public void onPassengerTurned(Entity passenger) {
        passenger.setYBodyRot(passenger.getYRot());
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.0;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(CBItems.cushion(this.getColor()));
    }

    @Override
    public void setPos(double x, double y, double z) {
        this.setPosRaw(x, y, z);
        super.setPos(x, y, z);
    }

    private void showBreakingParticles() {
        if (this.level instanceof ServerLevel level) {
            level.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, CBItems.wool(this.getColor()).defaultBlockState()),
                this.getX(),
                this.getY(0.6666666666666666),
                this.getZ(),
                10,
                this.getBbWidth() / 4.0F,
                this.getBbHeight() / 4.0F,
                this.getBbWidth() / 4.0F,
                0.05
            );
        }
    }

    public static boolean wouldSurviveAt(Level level, AABB boundingBox) {
        AABB anchorBox = new AABB(
            boundingBox.minX, boundingBox.minY - 0.015625, boundingBox.minZ, Math.nextDown(boundingBox.maxX), boundingBox.minY, Math.nextDown(boundingBox.maxZ)
        );

        for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(anchorBox.minX), Mth.floor(anchorBox.minY), Mth.floor(anchorBox.minZ), Mth.floor(anchorBox.maxX), Mth.floor(anchorBox.maxY), Mth.floor(anchorBox.maxZ))) {
            BlockState blockState = level.getBlockState(blockPos);
            VoxelShape shape = blockState.getShape(level, blockPos);
            if (!shape.isEmpty() && shape.bounds().move(blockPos).intersects(anchorBox)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean survives() {
        Level level = this.level;
        AABB boundingBox = this.getBoundingBox();
        if (!wouldSurviveAt(level, boundingBox)) {
            return false;
        }

        AABB inner = boundingBox.deflate(1.0E-7);
        for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(inner.minX), Mth.floor(inner.minY), Mth.floor(inner.minZ), Mth.floor(inner.maxX), Mth.floor(inner.maxY), Mth.floor(inner.maxZ))) {
            if (!level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void recalculateBoundingBox() {
        this.setBoundingBox(this.makeBoundingBox());
    }

    @Override
    protected AABB makeBoundingBox() {
        return new AABB(
            this.getX() - 0.5, this.getY(), this.getZ() - 0.5,
            this.getX() + 0.5, this.getY() + 0.25, this.getZ() + 0.5
        );
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_COLOR, DEFAULT_COLOR.getId());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("color", this.getColor().getName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setColor(DyeColor.byName(tag.getString("color"), DEFAULT_COLOR));
    }
}
