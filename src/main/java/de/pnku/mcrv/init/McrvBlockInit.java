package de.pnku.mcrv.init;

import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.block.MoreCrafterVariantBlock;
import de.pnku.mcrv.block.MoreCrafterVariantBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;

public class McrvBlockInit {
    public static final MoreCrafterVariantBlock BIRCH_CRAFTER = new MoreCrafterVariantBlock(MapColor.SAND, "birch");
    public static final MoreCrafterVariantBlock DARK_OAK_CRAFTER = new MoreCrafterVariantBlock(MapColor.COLOR_BROWN, "dark_oak");
    public static final MoreCrafterVariantBlock SPRUCE_CRAFTER = new MoreCrafterVariantBlock(MapColor.PODZOL, "spruce");
    public static final MoreCrafterVariantBlock JUNGLE_CRAFTER = new MoreCrafterVariantBlock(MapColor.DIRT, "jungle");
    public static final MoreCrafterVariantBlock ACACIA_CRAFTER = new MoreCrafterVariantBlock(MapColor.COLOR_ORANGE, "acacia");
    public static final MoreCrafterVariantBlock MANGROVE_CRAFTER = new MoreCrafterVariantBlock(MapColor.COLOR_RED, "mangrove");
    public static final MoreCrafterVariantBlock CHERRY_CRAFTER = new MoreCrafterVariantBlock(MapColor.TERRACOTTA_WHITE, SoundType.CHERRY_WOOD, "cherry");
    public static final MoreCrafterVariantBlock BAMBOO_CRAFTER = new MoreCrafterVariantBlock(MapColor.COLOR_YELLOW, SoundType.BAMBOO_WOOD, "bamboo");
    public static final MoreCrafterVariantBlock CRIMSON_CRAFTER = new MoreCrafterVariantBlock(MapColor.CRIMSON_STEM, SoundType.NETHER_WOOD, "crimson");
    public static final MoreCrafterVariantBlock WARPED_CRAFTER = new MoreCrafterVariantBlock(MapColor.WARPED_STEM, SoundType.NETHER_WOOD, "warped");

    public static BlockEntityType<MoreCrafterVariantBlockEntity> MORE_CRAFTER_VARIANT_BLOCK_ENTITY;
    public static final List<Block> more_crafters = new ArrayList<>();

    public static void registerCrafterBlocks() {
        registerCrafterBlock(BIRCH_CRAFTER);
        registerCrafterBlock(DARK_OAK_CRAFTER);
        registerCrafterBlock(SPRUCE_CRAFTER);
        registerCrafterBlock(JUNGLE_CRAFTER);
        registerCrafterBlock(ACACIA_CRAFTER);
        registerCrafterBlock(MANGROVE_CRAFTER);
        registerCrafterBlock(CHERRY_CRAFTER);
        registerCrafterBlock(BAMBOO_CRAFTER);
        registerCrafterBlock(CRIMSON_CRAFTER);
        registerCrafterBlock(WARPED_CRAFTER);

        MORE_CRAFTER_VARIANT_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MoreCrafterVariants.asId("more_crafter_variant"), BlockEntityType.Builder.of(MoreCrafterVariantBlockEntity::new, McrvBlockInit.more_crafters.toArray(Block[]::new)).build());
    }

    private static void registerCrafterBlock(MoreCrafterVariantBlock crafter) {
        Registry.register(BuiltInRegistries.BLOCK, MoreCrafterVariants.asId(((MoreCrafterVariantBlock) crafter).crafterWoodType + "_crafter"), crafter);
        more_crafters.add(crafter);
    }
}
