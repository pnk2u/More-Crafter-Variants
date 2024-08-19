package de.pnku.mcrv.datagen;

import de.pnku.mcrv.block.MoreCrafterVariantBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.apache.commons.text.WordUtils;

import java.util.concurrent.CompletableFuture;

import static de.pnku.mcrv.init.McrvBlockInit.more_crafters;

public class MoreCrafterVariantLangGenerator extends FabricLanguageProvider{
    public MoreCrafterVariantLangGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (Block crafterBlock : more_crafters) {
            String crafterName = WordUtils.capitalizeFully(((MoreCrafterVariantBlock) crafterBlock).crafterWoodType + " Crafter");
            translationBuilder.add(crafterBlock, crafterName);
        }
    }
}
