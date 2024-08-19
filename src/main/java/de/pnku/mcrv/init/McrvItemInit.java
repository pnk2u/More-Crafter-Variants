package de.pnku.mcrv.init;

import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.block.MoreCrafterVariantBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.Registry;

public class McrvItemInit {
    public static final BlockItem BIRCH_CRAFTER_I = new BlockItem(McrvBlockInit.BIRCH_CRAFTER, new Item.Properties());
    public static final BlockItem DARK_OAK_CRAFTER_I = new BlockItem(McrvBlockInit.DARK_OAK_CRAFTER, new Item.Properties());
    public static final BlockItem SPRUCE_CRAFTER_I = new BlockItem(McrvBlockInit.SPRUCE_CRAFTER, new Item.Properties());
    public static final BlockItem JUNGLE_CRAFTER_I = new BlockItem(McrvBlockInit.JUNGLE_CRAFTER, new Item.Properties());
    public static final BlockItem ACACIA_CRAFTER_I = new BlockItem(McrvBlockInit.ACACIA_CRAFTER, new Item.Properties());
    public static final BlockItem MANGROVE_CRAFTER_I = new BlockItem(McrvBlockInit.MANGROVE_CRAFTER, new Item.Properties());
    public static final BlockItem CHERRY_CRAFTER_I = new BlockItem(McrvBlockInit.CHERRY_CRAFTER, new Item.Properties());
    public static final BlockItem BAMBOO_CRAFTER_I = new BlockItem(McrvBlockInit.BAMBOO_CRAFTER, new Item.Properties());
    public static final BlockItem CRIMSON_CRAFTER_I = new BlockItem(McrvBlockInit.CRIMSON_CRAFTER, new Item.Properties());
    public static final BlockItem WARPED_CRAFTER_I = new BlockItem(McrvBlockInit.WARPED_CRAFTER, new Item.Properties());


    public static void registerCrafterItems() {
        registerCrafterItem(BIRCH_CRAFTER_I, Items.CRAFTER);
        registerCrafterItem(DARK_OAK_CRAFTER_I, BIRCH_CRAFTER_I);
        registerCrafterItem(SPRUCE_CRAFTER_I, DARK_OAK_CRAFTER_I);
        registerCrafterItem(JUNGLE_CRAFTER_I, SPRUCE_CRAFTER_I);
        registerCrafterItem(ACACIA_CRAFTER_I, JUNGLE_CRAFTER_I);
        registerCrafterItem(MANGROVE_CRAFTER_I, ACACIA_CRAFTER_I);
        registerCrafterItem(CHERRY_CRAFTER_I, MANGROVE_CRAFTER_I);
        registerCrafterItem(BAMBOO_CRAFTER_I, CHERRY_CRAFTER_I);
        registerCrafterItem(CRIMSON_CRAFTER_I, BAMBOO_CRAFTER_I);
        registerCrafterItem(WARPED_CRAFTER_I, CRIMSON_CRAFTER_I);
    }

    private static void registerCrafterItem(BlockItem crafter, Item crafterAfter) {
        Registry.register(BuiltInRegistries.ITEM, MoreCrafterVariants.asId(((MoreCrafterVariantBlock) crafter.getBlock()).crafterWoodType + "_crafter"), crafter);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.addAfter(crafterAfter, crafter));
    }
}