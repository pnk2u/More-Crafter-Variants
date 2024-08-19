package de.pnku.mcrv.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

import static de.pnku.mcrv.init.McrvBlockInit.more_crafters;

public class MoreCrafterVariantLootTableGenerator extends FabricBlockLootTableProvider {
    public MoreCrafterVariantLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        for (Block crafterBlock : more_crafters) {
            dropSelf(crafterBlock);
        }
    }
}
