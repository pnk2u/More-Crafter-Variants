package de.pnku.mcrv.block;

import de.pnku.mcrv.init.McrvBlockInit;
import io.github.lieonlion.lolmct.init.MctItemInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeCache;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;

public class MoreCrafterVariantBlock extends CrafterBlock {
    public final String crafterWoodType;
    public static final BooleanProperty CRAFTING;
    public static final BooleanProperty TRIGGERED;
    private static final EnumProperty<FrontAndTop> ORIENTATION;
    private static final RecipeCache RECIPE_CACHE;

    public MoreCrafterVariantBlock(MapColor colour, String crafterWoodType) {
        super(Properties.ofFullCopy(Blocks.CRAFTER).mapColor(colour));
        this.crafterWoodType = crafterWoodType;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(ORIENTATION, FrontAndTop.NORTH_UP)).setValue(TRIGGERED, false)).setValue(CRAFTING, false));

    }

    public MoreCrafterVariantBlock(MapColor colour, SoundType sound, String crafterWoodType) {
        super(Properties.ofFullCopy(Blocks.CRAFTER).mapColor(colour).sound(sound));
        this.crafterWoodType = crafterWoodType;
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(ORIENTATION, FrontAndTop.NORTH_UP)).setValue(TRIGGERED, false)).setValue(CRAFTING, false));
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, McrvBlockInit.MORE_CRAFTER_VARIANT_BLOCK_ENTITY, MoreCrafterVariantBlockEntity::serverTick);
    }

    private void setBlockEntityTriggered(@Nullable BlockEntity blockEntity, boolean triggered) {
        if (blockEntity instanceof MoreCrafterVariantBlockEntity moreCrafterVariantBlockEntity) {
            moreCrafterVariantBlockEntity.setTriggered(triggered);
        }

    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        MoreCrafterVariantBlockEntity moreCrafterVariantBlockEntity = new MoreCrafterVariantBlockEntity(pos, state);
        moreCrafterVariantBlockEntity.setTriggered(state.hasProperty(TRIGGERED) && (Boolean)state.getValue(TRIGGERED));
        return moreCrafterVariantBlockEntity;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MoreCrafterVariantBlockEntity) {
                player.openMenu((MoreCrafterVariantBlockEntity)blockEntity);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof MoreCrafterVariantBlockEntity moreCrafterVariantBlockEntity) {
            return moreCrafterVariantBlockEntity.getRedstoneSignal();
        } else {
            return 0;
        }
    }

    @Override
    protected void dispenseFrom(BlockState state, ServerLevel level, BlockPos pos) {
        BlockEntity var5 = level.getBlockEntity(pos);
        if (var5 instanceof MoreCrafterVariantBlockEntity moreCrafterVariantBlockEntity) {
            CraftingInput craftingInput = moreCrafterVariantBlockEntity.asCraftInput();
            Optional optional = getPotentialResults(level, craftingInput);
            if (optional.isEmpty()) {
                level.levelEvent(1050, pos, 0);
            } else {
                RecipeHolder<CraftingRecipe> recipeHolder = (RecipeHolder)optional.get();
                ItemStack itemStack = ((CraftingRecipe)recipeHolder.value()).assemble(craftingInput, level.registryAccess());
                if (itemStack.isEmpty()) {
                    level.levelEvent(1050, pos, 0);
                } else {
                    moreCrafterVariantBlockEntity.setCraftingTicksRemaining(6);
                    level.setBlock(pos, (BlockState)state.setValue(CRAFTING, true), 2);
                    itemStack.onCraftedBySystem(level);
                    this.dispenseItem(level, pos, moreCrafterVariantBlockEntity, itemStack, state, recipeHolder);
                    Iterator var9 = ((CraftingRecipe)recipeHolder.value()).getRemainingItems(craftingInput).iterator();

                    while(var9.hasNext()) {
                        ItemStack itemStack2 = (ItemStack)var9.next();
                        if (!itemStack2.isEmpty()) {
                            this.dispenseItem(level, pos, moreCrafterVariantBlockEntity, itemStack2, state, recipeHolder);
                        }
                    }

                    moreCrafterVariantBlockEntity.getItems().forEach((itemStackx) -> {
                        if (!itemStackx.isEmpty()) {
                            itemStackx.shrink(1);
                        }
                    });
                    moreCrafterVariantBlockEntity.setChanged();
                }
            }
        }
    }

    private void dispenseItem(ServerLevel level, BlockPos pos, MoreCrafterVariantBlockEntity crafter, ItemStack stack, BlockState state, RecipeHolder<CraftingRecipe> recipe) {
        Direction direction = ((FrontAndTop)state.getValue(ORIENTATION)).front();
        Container container = HopperBlockEntity.getContainerAt(level, pos.relative(direction));
        ItemStack itemStack = stack.copy();
        if (container != null && (container instanceof MoreCrafterVariantBlockEntity || stack.getCount() > container.getMaxStackSize(stack))) {
            while(!itemStack.isEmpty()) {
                ItemStack itemStack2 = itemStack.copyWithCount(1);
                ItemStack itemStack3 = HopperBlockEntity.addItem(crafter, container, itemStack2, direction.getOpposite());
                if (!itemStack3.isEmpty()) {
                    break;
                }

                itemStack.shrink(1);
            }
        } else if (container != null) {
            while(!itemStack.isEmpty()) {
                int i = itemStack.getCount();
                itemStack = HopperBlockEntity.addItem(crafter, container, itemStack, direction.getOpposite());
                if (i == itemStack.getCount()) {
                    break;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            Vec3 vec3 = Vec3.atCenterOf(pos);
            Vec3 vec32 = vec3.relative(direction, 0.7);
            DefaultDispenseItemBehavior.spawnItem(level, itemStack, 6, direction, vec32);
            Iterator var12 = level.getEntitiesOfClass(ServerPlayer.class, AABB.ofSize(vec3, 17.0, 17.0, 17.0)).iterator();

            while(var12.hasNext()) {
                ServerPlayer serverPlayer = (ServerPlayer)var12.next();
                CriteriaTriggers.CRAFTER_RECIPE_CRAFTED.trigger(serverPlayer, recipe.id(), crafter.getItems());
            }

            level.levelEvent(1049, pos, 0);
            level.levelEvent(2010, pos, direction.get3DDataValue());
        }

    }

    public Item getTableItem(String planksWood) {
        switch (planksWood) {
            case "acacia" -> {
                return MctItemInit.ACACIA_TABLE_I;
            }
            case "bamboo" -> {
                return MctItemInit.BAMBOO_TABLE_I;
            }
            case "birch" -> {
                return MctItemInit.BIRCH_TABLE_I;
            }
            case "cherry" -> {
                return MctItemInit.CHERRY_TABLE_I;
            }
            case "crimson" -> {
                return MctItemInit.CRIMSON_TABLE_I;
            }
            case "dark_oak" -> {
                return MctItemInit.DARK_OAK_TABLE_I;
            }
            case "oak" -> {
                return Items.CRAFTING_TABLE;
            }
            case "jungle" -> {
                return MctItemInit.JUNGLE_TABLE_I;
            }
            case "mangrove" -> {
                return MctItemInit.MANGROVE_TABLE_I;
            }
            case "spruce" -> {
                return MctItemInit.SPRUCE_TABLE_I;
            }
            case "warped" -> {
                return MctItemInit.WARPED_TABLE_I;
            }

        }
        return null;
    }

    static {
        CRAFTING = BlockStateProperties.CRAFTING;
        TRIGGERED = BlockStateProperties.TRIGGERED;
        ORIENTATION = BlockStateProperties.ORIENTATION;
        RECIPE_CACHE = new RecipeCache(10);
    }
}