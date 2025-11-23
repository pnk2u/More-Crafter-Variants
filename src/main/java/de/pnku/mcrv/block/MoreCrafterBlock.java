package de.pnku.mcrv.block;

import net.minecraft.core.FrontAndTop;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;

public class MoreCrafterBlock extends CrafterBlock {
    public final String crafterWoodType;
    public MoreCrafterBlock(MapColor colour, String crafterWoodType) {
        super(Properties.ofFullCopy(Blocks.CRAFTER).mapColor(colour));
        this.crafterWoodType = crafterWoodType;
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.ORIENTATION, FrontAndTop.NORTH_UP).setValue(BlockStateProperties.TRIGGERED, false).setValue(BlockStateProperties.CRAFTING, false));

    }

    public MoreCrafterBlock(MapColor colour, SoundType sound, String crafterWoodType) {
        super(Properties.ofFullCopy(Blocks.CRAFTER).mapColor(colour).sound(sound));
        this.crafterWoodType = crafterWoodType;
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.ORIENTATION, FrontAndTop.NORTH_UP).setValue(BlockStateProperties.TRIGGERED, false).setValue(BlockStateProperties.CRAFTING, false));
    }
}