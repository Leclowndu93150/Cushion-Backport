package com.leclowndu93150.cushionbackport.forge;

import com.leclowndu93150.cushionbackport.Cushionbackport;
import com.leclowndu93150.cushionbackport.entity.Cushion;
import com.leclowndu93150.cushionbackport.item.CushionItem;
import com.leclowndu93150.cushionbackport.registry.CBEntities;
import com.leclowndu93150.cushionbackport.registry.CBItems;
import com.leclowndu93150.cushionbackport.registry.CBSounds;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CBRegistryForge {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cushionbackport.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Cushionbackport.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Cushionbackport.MOD_ID);

    public static final CreativeModeTab TAB = new CreativeModeTab(Cushionbackport.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(CBItems.cushion(DyeColor.RED));
        }
    };

    public static void init(IEventBus modBus) {
        RegistryObject<EntityType<Cushion>> cushion = ENTITIES.register("cushion", () ->
            EntityType.Builder.<Cushion>of(Cushion::new, MobCategory.MISC)
                .sized(1.0F, 0.25F)
                .clientTrackingRange(10)
                .updateInterval(Integer.MAX_VALUE)
                .build("cushion"));
        CBEntities.CUSHION = cushion::get;

        for (DyeColor color : CBItems.VANILLA_COLORS) {
            RegistryObject<CushionItem> item = ITEMS.register(color.getName() + "_cushion", () -> new CushionItem(new Item.Properties().tab(TAB), color));
            CBItems.CUSHIONS.put(color, item::get);
        }

        CBSounds.CUSHION_BREAK = registerSound("entity.cushion.break");
        CBSounds.CUSHION_PLACE = registerSound("entity.cushion.place");
        CBSounds.CUSHION_SIT = registerSound("entity.cushion.sit");
        CBSounds.CUSHION_GET_UP = registerSound("entity.cushion.get_up");

        ITEMS.register(modBus);
        ENTITIES.register(modBus);
        SOUNDS.register(modBus);
    }

    private static Supplier<SoundEvent> registerSound(String path) {
        RegistryObject<SoundEvent> obj = SOUNDS.register(path.replace('.', '_'), () -> new SoundEvent(new ResourceLocation(Cushionbackport.MOD_ID, path)));
        return obj::get;
    }
}
