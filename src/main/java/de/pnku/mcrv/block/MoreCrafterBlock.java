package de.pnku.mcrv.block;

import net.minecraft.core.FrontAndTop;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;

import static de.pnku.mcrv.MoreCrafterVariants.asId;

public class MoreCrafterBlock extends CrafterBlock {
    public final String crafterWoodType;
    public MoreCrafterBlock(MapColor colour, String crafterWoodType) {
        super(Properties.ofFullCopy(Blocks.CRAFTER).mapColor(colour).setId(ResourceKey.create(Registries.BLOCK, asId(crafterWoodType + "_crafter"))));
        this.crafterWoodType = crafterWoodType;
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.ORIENTATION, FrontAndTop.NORTH_UP).setValue(BlockStateProperties.TRIGGERED, false).setValue(BlockStateProperties.CRAFTING, false));

    }

    public MoreCrafterBlock(MapColor colour, SoundType sound, String crafterWoodType) {
        super(Properties.ofFullCopy(Blocks.CRAFTER).mapColor(colour).setId(ResourceKey.create(Registries.BLOCK, asId(crafterWoodType + "_crafter"))).sound(sound));
        this.crafterWoodType = crafterWoodType;
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.ORIENTATION, FrontAndTop.NORTH_UP).setValue(BlockStateProperties.TRIGGERED, false).setValue(BlockStateProperties.CRAFTING, false));
    }
}