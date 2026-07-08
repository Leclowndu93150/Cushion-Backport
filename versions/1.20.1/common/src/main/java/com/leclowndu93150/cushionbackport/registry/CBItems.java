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

    private static final Map<DyeColor, Block> WOOL_BY_COLOR = new EnumMap<>(DyeColor.class);

    static {
        WOOL_BY_COLOR.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        WOOL_BY_COLOR.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        WOOL_BY_COLOR.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        WOOL_BY_COLOR.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        WOOL_BY_COLOR.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        WOOL_BY_COLOR.put(DyeColor.LIME, Blocks.LIME_WOOL);
        WOOL_BY_COLOR.put(DyeColor.PINK, Blocks.PINK_WOOL);
        WOOL_BY_COLOR.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        WOOL_BY_COLOR.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        WOOL_BY_COLOR.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        WOOL_BY_COLOR.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        WOOL_BY_COLOR.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        WOOL_BY_COLOR.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        WOOL_BY_COLOR.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        WOOL_BY_COLOR.put(DyeColor.RED, Blocks.RED_WOOL);
        WOOL_BY_COLOR.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    }

    public static CushionItem cushion(DyeColor color) {
        return CUSHIONS.get(color).get();
    }

    public static Block wool(DyeColor color) {
        return WOOL_BY_COLOR.get(color);
    }
}
