package com.leclowndu93150.cushionbackport.neoforge;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import com.leclowndu93150.cushionbackport.entity.Cushion;
import com.leclowndu93150.cushionbackport.item.CushionItem;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import com.leclowndu93150.cushionbackport.registry.CBItems;
import com.leclowndu93150.cushionbackport.registry.CBSounds;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CBRegistryNeoForge {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Cushionbackport.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Cushionbackport.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Cushionbackport.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Cushionbackport.MOD_ID);

    public static void init(IEventBus modBus) {
        ResourceKey<EntityType<?>> cushionKey = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(Cushionbackport.MOD_ID, "cushion"));
        CBEntities.CUSHION = ENTITIES.register(
            "cushion",
            () -> EntityType.Builder.<Cushion>of(Cushion::new, MobCategory.MISC)
                .sized(1.0F, 0.25F)
                .clientTrackingRange(10)
                .updateInterval(Integer.MAX_VALUE)
                .build(cushionKey)
        );

        for (DyeColor color : CBItems.VANILLA_COLORS) {
            DeferredItem<CushionItem> holder = ITEMS.registerItem(
                color.getName() + "_cushion",
                properties -> new CushionItem(properties, color)
            );
            CBItems.CUSHIONS.put(color, holder::get);
        }

        CBSounds.CUSHION_BREAK = registerSound("entity.cushion.break");
        CBSounds.CUSHION_PLACE = registerSound("entity.cushion.place");
        CBSounds.CUSHION_SIT = registerSound("entity.cushion.sit");
        CBSounds.CUSHION_GET_UP = registerSound("entity.cushion.get_up");

        TABS.register("cushionbackport", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.cushionbackport"))
            .icon(() -> new ItemStack(CBItems.cushion(DyeColor.RED)))
            .displayItems((params, output) -> {
                for (DyeColor color : CBItems.VANILLA_COLORS) {
                    output.accept(CBItems.cushion(color));
                }
            })
            .build());

        ITEMS.register(modBus);
        ENTITIES.register(modBus);
        SOUNDS.register(modBus);
        TABS.register(modBus);
    }

    private static Supplier<SoundEvent> registerSound(String path) {
        DeferredHolder<SoundEvent, SoundEvent> holder = SOUNDS.register(
            path.replace('.', '_'),
            () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Cushionbackport.MOD_ID, path))
        );
        return holder::get;
    }
}
