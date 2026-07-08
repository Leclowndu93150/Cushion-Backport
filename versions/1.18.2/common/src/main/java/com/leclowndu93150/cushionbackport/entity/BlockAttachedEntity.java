package com.leclowndu93150.cushionbackport.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public abstract class BlockAttachedEntity extends Entity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private int checkInterval;
    protected BlockPos pos;

    protected BlockAttachedEntity(EntityType<? extends BlockAttachedEntity> type, Level level) {
        super(type, level);
    }

    protected BlockAttachedEntity(EntityType<? extends BlockAttachedEntity> type, Level level, BlockPos pos) {
        this(type, level);
        this.pos = pos;
    }

    protected abstract void recalculateBoundingBox();

    @Override
    public void tick() {
        if (!this.level.isClientSide) {
            this.checkOutOfWorld();
            if (this.checkInterval++ == 100) {
                this.checkInterval = 0;
                if (!this.isRemoved() && !this.survives()) {
                    this.discard();
                    this.dropItem(null);
                }
            }
        }
    }

    public abstract boolean survives();

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean skipAttackInteraction(Entity source) {
        if (source instanceof Player player) {
            return !this.level.mayInteract(player, this.pos) ? true : this.hurt(DamageSource.playerAttack(player), 0.0F);
        } else {
            return false;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.isRemoved() && !this.level.isClientSide) {
                this.kill();
                this.markHurt();
                this.dropItem(source.getEntity());
            }

            return true;
        }
    }

    @Override
    public void move(MoverType type, Vec3 delta) {
        if (!this.level.isClientSide && !this.isRemoved() && delta.lengthSqr() > 0.0) {
            this.kill();
            this.dropItem(null);
        }
    }

    @Override
    public void push(double xa, double ya, double za) {
        if (!this.level.isClientSide && !this.isRemoved() && xa * xa + ya * ya + za * za > 0.0) {
            this.kill();
            this.dropItem(null);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        BlockPos blockPos = this.getPos();
        tag.putInt("TileX", blockPos.getX());
        tag.putInt("TileY", blockPos.getY());
        tag.putInt("TileZ", blockPos.getZ());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        BlockPos blockPos = new BlockPos(tag.getInt("TileX"), tag.getInt("TileY"), tag.getInt("TileZ"));
        if (!blockPos.closerThan(this.blockPosition(), 16.0)) {
            LOGGER.error("Block-attached entity at invalid position: {}", blockPos);
        } else {
            this.pos = blockPos;
        }
    }

    public abstract void dropItem(Entity causedBy);

    @Override
    protected boolean repositionEntityAfterLoad() {
        return false;
    }

    @Override
    public void setPos(double x, double y, double z) {
        this.pos = new BlockPos(x, y, z);
        this.recalculateBoundingBox();
        this.hasImpulse = true;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightningBolt) {
    }

    @Override
    public void refreshDimensions() {
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
