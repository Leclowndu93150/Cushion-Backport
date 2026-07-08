package com.leclowndu93150.cushionbackport.registry;

import com.leclowndu93150.cushionbackport.item.CushionItem;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CBItems {
    public static final Map<DyeColor, Supplier<CushionItem>> CUSHIONS = new EnumMap<>(DyeColor.class);

    public static CushionItem cushion(DyeColor color) {
        return CUSHIONS.get(color).get();
    }

    public static Block wool(DyeColor color) {
        return Blocks.WOOL.pick(color);
    }
}
