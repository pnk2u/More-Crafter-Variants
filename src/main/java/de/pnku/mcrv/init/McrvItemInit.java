package de.pnku.mcrv.init;

import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.block.MoreCrafterBlock;
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
    public static final BlockItem CRIMSON_CRAFTER_I = new BlockItem(McrvBlockInit.CRIMSON_CRAFTER, new Item.Properties().fireResistant());
    public static final BlockItem WARPED_CRAFTER_I = new BlockItem(McrvBlockInit.WARPED_CRAFTER, new Item.Properties().fireResistant());


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
        registerCrafterItem(BIRCH_CRAFTER_I);
    }

    private static void registerCrafterItem(BlockItem crafter) {
        Registry.register(BuiltInRegistries.ITEM, MoreCrafterVariants.asId(((MoreCrafterBlock) crafter.getBlock()).crafterWoodType + "_crafter"), crafter);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> entries.addAfter(Items.CRAFTER, crafter));
    }
}