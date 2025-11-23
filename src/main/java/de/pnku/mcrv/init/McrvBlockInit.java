package de.pnku.mcrv.init;

import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.block.MoreCrafterBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;

public class McrvBlockInit {
    public static final Block BIRCH_CRAFTER = new MoreCrafterBlock(MapColor.SAND, "birch");
    public static final Block DARK_OAK_CRAFTER = new MoreCrafterBlock(MapColor.COLOR_BROWN, "dark_oak");
    public static final Block SPRUCE_CRAFTER = new MoreCrafterBlock(MapColor.PODZOL, "spruce");
    public static final Block JUNGLE_CRAFTER = new MoreCrafterBlock(MapColor.DIRT, "jungle");
    public static final Block ACACIA_CRAFTER = new MoreCrafterBlock(MapColor.COLOR_ORANGE, "acacia");
    public static final Block MANGROVE_CRAFTER = new MoreCrafterBlock(MapColor.COLOR_RED, "mangrove");
    public static final Block CHERRY_CRAFTER = new MoreCrafterBlock(MapColor.TERRACOTTA_WHITE, SoundType.CHERRY_WOOD, "cherry");
    public static final Block BAMBOO_CRAFTER = new MoreCrafterBlock(MapColor.COLOR_YELLOW, SoundType.BAMBOO_WOOD, "bamboo");
    public static final Block CRIMSON_CRAFTER = new MoreCrafterBlock(MapColor.CRIMSON_STEM, SoundType.NETHER_WOOD, "crimson");
    public static final Block WARPED_CRAFTER = new MoreCrafterBlock(MapColor.WARPED_STEM, SoundType.NETHER_WOOD, "warped");
    public static final List<Block> more_crafters = new ArrayList<>();

    public static void registerCrafterBlocks() {
        registerCrafterBlock(BIRCH_CRAFTER);
        registerCrafterBlock(SPRUCE_CRAFTER);
        registerCrafterBlock(JUNGLE_CRAFTER);
        registerCrafterBlock(ACACIA_CRAFTER);
        registerCrafterBlock(DARK_OAK_CRAFTER);
        registerCrafterBlock(MANGROVE_CRAFTER);
        registerCrafterBlock(CHERRY_CRAFTER);
        registerCrafterBlock(BAMBOO_CRAFTER);
        registerCrafterBlock(CRIMSON_CRAFTER);
        registerCrafterBlock(WARPED_CRAFTER);
    }

    private static void registerCrafterBlock(Block crafterBlock) {
        Registry.register(BuiltInRegistries.BLOCK, MoreCrafterVariants.asId(((MoreCrafterBlock) crafterBlock).crafterWoodType + "_crafter"), crafterBlock);
        more_crafters.add(crafterBlock);
        BlockEntityType.CRAFTER.addSupportedBlock(crafterBlock);
    }
}
