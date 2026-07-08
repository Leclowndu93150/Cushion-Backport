package com.leclowndu93150.cushionbackport.registry;

import com.leclowndu93150.cushionbackport.item.CushionItem;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CBItems {
    public static final DyeColor[] VANILLA_COLORS = {
        DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
        DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY,
        DyeColor.LIGHT_GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
        DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK
    };

    public static final Map<DyeColor, Supplier<CushionItem>> CUSHIONS = new HashMap<>();

    private static final Map<DyeColor, Block> WOOL_BY_COLOR = new HashMap<>();

    public static CushionItem cushion(DyeColor color) {
        return CUSHIONS.get(color).get();
    }

    public static Block wool(DyeColor color) {
        return WOOL_BY_COLOR.computeIfAbsent(color, CBItems::resolveWool);
    }

    private static Block resolveWool(DyeColor color) {
        String woolName = color.getName() + "_wool";
        for (Block block : BuiltInRegistries.BLOCK) {
            if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals(woolName)) {
                return block;
            }
        }
        return Blocks.WOOL.pick(DyeColor.WHITE);
    }
}
