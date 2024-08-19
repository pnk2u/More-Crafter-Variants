package de.pnku.mcrv.block;

import com.google.common.annotations.VisibleForTesting;
import de.pnku.mcrv.MoreCrafterVariants;
import de.pnku.mcrv.init.McrvBlockInit;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class MoreCrafterVariantBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider, CraftingContainer {
    public static final int CONTAINER_WIDTH = 3;
    public static final int CONTAINER_HEIGHT = 3;
    public static final int SLOT_DISABLED = 1;
    public static final int SLOT_ENABLED = 0;
    public static final int CONTAINER_SIZE = 9;
    public static final int DATA_TRIGGERED = 9;
    public static final int NUM_DATA = 10;
    private NonNullList<ItemStack> items;
    private int craftingTicksRemaining;
    protected final ContainerData mcrvContainerData;

    public MoreCrafterVariantBlockEntity(BlockPos pos, BlockState state) {
        super(McrvBlockInit.MORE_CRAFTER_VARIANT_BLOCK_ENTITY, pos, state);
        this.items = NonNullList.withSize(9, ItemStack.EMPTY);
        this.craftingTicksRemaining = 0;
        this.mcrvContainerData = new SimpleContainerData(10) {
            private final int[] slotStates = new int[9];
            private int triggered = 0;

            @Override
            public int get(int index) {
                return index == 9 ? this.triggered : this.slotStates[index];
            }

            @Override
            public void set(int index, int value) {
                if (index == 9) {
                    this.triggered = value;
                } else {
                    this.slotStates[index] = value;
                }

            }

            @Override
            public int getCount() {
                return 10;
            }
        };
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container." + MoreCrafterVariants.MODID + "." + getBlock().crafterWoodType + "_crafter");
    }

    protected MoreCrafterVariantBlock getBlock() {
        return (MoreCrafterVariantBlock) getBlockState().getBlock();
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new CrafterMenu(containerId, inventory, MoreCrafterVariantBlockEntity.this, MoreCrafterVariantBlockEntity.this.mcrvContainerData);
    }

    public void setSlotState(int slot, boolean state) {
        if (MoreCrafterVariantBlockEntity.this.slotCanBeDisabled(slot)) {
            MoreCrafterVariantBlockEntity.this.mcrvContainerData.set(slot, state ? 0 : 1);
            MoreCrafterVariantBlockEntity.this.setChanged();
        }
    }

    public boolean isSlotDisabled(int slot) {
        if (slot >= 0 && slot < 9) {
            return MoreCrafterVariantBlockEntity.this.mcrvContainerData.get(slot) == 1;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (MoreCrafterVariantBlockEntity.this.mcrvContainerData.get(slot) == 1) {
            return false;
        } else {
            ItemStack itemStack = (ItemStack)this.items.get(slot);
            int i = itemStack.getCount();
            if (i >= itemStack.getMaxStackSize()) {
                return false;
            } else if (itemStack.isEmpty()) {
                return true;
            } else {
                return !this.smallerStackExist(i, itemStack, slot);
            }
        }
    }

    private boolean smallerStackExist(int currentSize, ItemStack stack, int slot) {
        for(int i = slot + 1; i < 9; ++i) {
            if (!MoreCrafterVariantBlockEntity.this.isSlotDisabled(i)) {
                ItemStack itemStack = MoreCrafterVariantBlockEntity.this.getItem(i);
                if (itemStack.isEmpty() || itemStack.getCount() < currentSize && ItemStack.isSameItemSameComponents(itemStack, stack)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        MoreCrafterVariantBlockEntity.this.craftingTicksRemaining = tag.getInt("variant_crafting_ticks_remaining");
        MoreCrafterVariantBlockEntity.this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!MoreCrafterVariantBlockEntity.this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items, registries);
        }

        int[] is = tag.getIntArray("variant_disabled_slots");

        for(int i = 0; i < 9; ++i) {
            this.mcrvContainerData.set(i, 0);
        }

        int[] var8 = is;
        int var5 = is.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            int j = var8[var6];
            if (MoreCrafterVariantBlockEntity.this.slotCanBeDisabled(j)) {
                MoreCrafterVariantBlockEntity.this.mcrvContainerData.set(j, 1);
            }
        }

        MoreCrafterVariantBlockEntity.this.mcrvContainerData.set(9, tag.getInt("variant_triggered"));
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("variant_crafting_ticks_remaining", MoreCrafterVariantBlockEntity.this.craftingTicksRemaining);
        if (!MoreCrafterVariantBlockEntity.this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, MoreCrafterVariantBlockEntity.this.items, registries);
        }
        MoreCrafterVariantBlockEntity.this.addDisabledSlots(tag);
        MoreCrafterVariantBlockEntity.this.addTriggered(tag);
    }

    private void addTriggered(CompoundTag tag) {
        tag.putInt("variant_triggered", MoreCrafterVariantBlockEntity.this.mcrvContainerData.get(9));
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        Iterator<ItemStack> var1 = MoreCrafterVariantBlockEntity.this.items.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return (ItemStack)MoreCrafterVariantBlockEntity.this.items.get(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (MoreCrafterVariantBlockEntity.this.isSlotDisabled(slot)) {
            MoreCrafterVariantBlockEntity.this.setSlotState(slot, true);
        }

        super.setItem(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(MoreCrafterVariantBlockEntity.this, player);
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return MoreCrafterVariantBlockEntity.this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        MoreCrafterVariantBlockEntity.this.items = items;
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public void fillStackedContents(StackedContents contents) {

        for (ItemStack itemStack : MoreCrafterVariantBlockEntity.this.items) {
            contents.accountSimpleStack(itemStack);
        }

    }

    public void addDisabledSlots(CompoundTag tag) {
        IntList intList = new IntArrayList();

        for(int index = 0; index < 9; ++index) {
            if (MoreCrafterVariantBlockEntity.this.isSlotDisabled(index)) {
                intList.add(index);
            }
        }

        tag.putIntArray("variant_disabled_slots", intList);
    }

    public void setTriggered(boolean triggered) {
        MoreCrafterVariantBlockEntity.this.mcrvContainerData.set(9, triggered ? 1 : 0);
    }

    @VisibleForTesting
    public boolean isTriggered() {
        return MoreCrafterVariantBlockEntity.this.mcrvContainerData.get(9) == 1;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MoreCrafterVariantBlockEntity crafter) {
        int i = crafter.craftingTicksRemaining - 1;
        if (i >= 0) {
            crafter.craftingTicksRemaining = i;
            if (i == 0) {
                level.setBlock(pos, (BlockState)state.setValue(MoreCrafterVariantBlock.CRAFTING, false), 3);
            }

        }
    }

    public void setCraftingTicksRemaining(int craftingTicksRemaining) {
        MoreCrafterVariantBlockEntity.this.craftingTicksRemaining = craftingTicksRemaining;
    }

    public int getRedstoneSignal() {
        int i = 0;

        for(int j = 0; j < MoreCrafterVariantBlockEntity.this.getContainerSize(); ++j) {
            ItemStack itemStack = MoreCrafterVariantBlockEntity.this.getItem(j);
            if (!itemStack.isEmpty() || MoreCrafterVariantBlockEntity.this.isSlotDisabled(j)) {
                ++i;
            }
        }

        return i;
    }

    public boolean slotCanBeDisabled(int slot) {
        return slot > -1 && slot < 9 && ((ItemStack)MoreCrafterVariantBlockEntity.this.items.get(slot)).isEmpty();
    }
}
