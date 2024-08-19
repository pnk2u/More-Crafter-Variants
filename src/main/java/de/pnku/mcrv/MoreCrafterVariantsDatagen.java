package de.pnku.mcrv;

import de.pnku.mcrv.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoreCrafterVariantsDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(MoreCrafterVariantRecipeGenerator::new);
        pack.addProvider(MoreCrafterVariantLootTableGenerator::new);
        pack.addProvider(MoreCrafterVariantLangGenerator::new);
    }
}
