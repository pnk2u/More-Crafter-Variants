package de.pnku.mcrv.datagen;

import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.block.MoreCrafterVariantBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

import static de.pnku.mcrv.init.McrvBlockInit.more_crafters;

public class MoreCrafterVariantRecipeGenerator extends FabricRecipeProvider {
    public MoreCrafterVariantRecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        for (Block crafterBlock : more_crafters) {
            String planksWood = ((MoreCrafterVariantBlock) crafterBlock).crafterWoodType;
            Item crafterTableIngredient = ((MoreCrafterVariantBlock) crafterBlock).getTableItem(planksWood);
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, crafterBlock)
                    .group("crafter")
                    .unlockedBy("has_table", has(crafterTableIngredient))
                    .pattern("III")
                    .pattern("I#I")
                    .pattern("RDR")
                    .define('I', Items.IRON_INGOT)
                    .define('#', crafterTableIngredient)
                    .define('R', Items.REDSTONE)
                    .define('D', Items.DROPPER)
                    .save(recipeOutput, MoreCrafterVariants.asId(planksWood + "_crafter"));
        }
    }
}
