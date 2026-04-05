package de.pnku.mcrv.init;

import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.block.MoreCrafterBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public class McrvItemInit {
    public static final BlockItem BIRCH_CRAFTER_I = itemFromBlock(McrvBlockInit.BIRCH_CRAFTER);
    public static final BlockItem DARK_OAK_CRAFTER_I = itemFromBlock(McrvBlockInit.DARK_OAK_CRAFTER);
    public static final BlockItem PALE_OAK_CRAFTER_I = itemFromBlock(McrvBlockInit.PALE_OAK_CRAFTER);
    public static final BlockItem SPRUCE_CRAFTER_I = itemFromBlock(McrvBlockInit.SPRUCE_CRAFTER);
    public static final BlockItem JUNGLE_CRAFTER_I = itemFromBlock(McrvBlockInit.JUNGLE_CRAFTER);
    public static final BlockItem ACACIA_CRAFTER_I = itemFromBlock(McrvBlockInit.ACACIA_CRAFTER);
    public static final BlockItem MANGROVE_CRAFTER_I = itemFromBlock(McrvBlockInit.MANGROVE_CRAFTER);
    public static final BlockItem CHERRY_CRAFTER_I = itemFromBlock(McrvBlockInit.CHERRY_CRAFTER);
    public static final BlockItem BAMBOO_CRAFTER_I = itemFromBlock(McrvBlockInit.BAMBOO_CRAFTER);
    public static final BlockItem CRIMSON_CRAFTER_I = itemFromBlock(McrvBlockInit.CRIMSON_CRAFTER);
    public static final BlockItem WARPED_CRAFTER_I = itemFromBlock(McrvBlockInit.WARPED_CRAFTER);

    public static BlockItem itemFromBlock(Block block) {
        return new BlockItem(block, setProperties(block));
    }

    public static Item.Properties setProperties(Block block) {
        Item.Properties properties = new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM,BuiltInRegistries.BLOCK.getKey(block))).useBlockDescriptionPrefix();
        if (((MoreCrafterBlock) block).crafterWoodType.equals("crimson") || ((MoreCrafterBlock) block).crafterWoodType.equals("warped")) {
            properties.fireResistant();
        }
        return properties;
    }

    public static void registerCrafterItems() {
        registerCrafterItem(WARPED_CRAFTER_I);
        registerCrafterItem(CRIMSON_CRAFTER_I);
        registerCrafterItem(BAMBOO_CRAFTER_I);
        registerCrafterItem(CHERRY_CRAFTER_I);
        registerCrafterItem(MANGROVE_CRAFTER_I);
        registerCrafterItem(ACACIA_CRAFTER_I);
        registerCrafterItem(JUNGLE_CRAFTER_I);
        registerCrafterItem(SPRUCE_CRAFTER_I);
        registerCrafterItem(DARK_OAK_CRAFTER_I);
        registerCrafterItem(PALE_OAK_CRAFTER_I);
        registerCrafterItem(BIRCH_CRAFTER_I);
    }

    private static void registerCrafterItem(BlockItem crafter) {
        Registry.register(BuiltInRegistries.ITEM, MoreCrafterVariants.asId(((MoreCrafterBlock) crafter.getBlock()).crafterWoodType + "_crafter"), crafter);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.insertAfter(Items.CRAFTER, crafter));
    }
}