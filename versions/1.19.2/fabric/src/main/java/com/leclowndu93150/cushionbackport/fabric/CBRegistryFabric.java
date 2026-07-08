package com.leclowndu93150.cushionbackport.fabric;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import com.leclowndu93150.cushionbackport.entity.Cushion;
import com.leclowndu93150.cushionbackport.item.CushionItem;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import com.leclowndu93150.cushionbackport.registry.CBItems;
import com.leclowndu93150.cushionbackport.registry.CBSounds;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CBRegistryFabric {

    public static void init() {
        EntityType<Cushion> cushionType = buildCushionType(Cushion::new);
        Registry.register(Registry.ENTITY_TYPE, id("cushion"), cushionType);
        CBEntities.CUSHION = () -> cushionType;

        CreativeModeTab tab = FabricItemGroupBuilder.create(id("cushionbackport"))
            .icon(() -> new ItemStack(CBItems.cushion(DyeColor.RED)))
            .build();

        for (DyeColor color : CBItems.VANILLA_COLORS) {
            CushionItem item = new CushionItem(new Item.Properties().tab(tab), color);
            Registry.register(Registry.ITEM, id(color.getName() + "_cushion"), item);
            CBItems.CUSHIONS.put(color, () -> item);
        }

        CBSounds.CUSHION_BREAK = registerSound("entity.cushion.break");
        CBSounds.CUSHION_PLACE = registerSound("entity.cushion.place");
        CBSounds.CUSHION_SIT = registerSound("entity.cushion.sit");
        CBSounds.CUSHION_GET_UP = registerSound("entity.cushion.get_up");
    }

    private static EntityType<Cushion> buildCushionType(EntityType.EntityFactory<Cushion> factory) {
        return EntityType.Builder.of(factory, MobCategory.MISC)
            .sized(1.0F, 0.25F)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .build("cushion");
    }

    private static Supplier<SoundEvent> registerSound(String path) {
        ResourceLocation loc = id(path);
        SoundEvent event = new SoundEvent(loc);
        Registry.register(Registry.SOUND_EVENT, loc, event);
        return () -> event;
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(Cushionbackport.MOD_ID, path);
    }
}
