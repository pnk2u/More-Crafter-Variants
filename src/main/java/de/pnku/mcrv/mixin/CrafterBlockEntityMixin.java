package de.pnku.mcrv.mixin;

import de.pnku.mcrv.block.MoreCrafterBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.CrafterBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrafterBlockEntity.class)
public class CrafterBlockEntityMixin {
    @Inject(method = "getDefaultName", at = @At("HEAD"), cancellable = true)
    private void injectedGetDefaultName(CallbackInfoReturnable<Component> cir) {
        if (((CrafterBlockEntity) (Object) this).getBlockState().getBlock() instanceof MoreCrafterBlock moreCrafterBlock) {
            cir.setReturnValue(Component.translatable("container.quad-lolmcrv." + moreCrafterBlock.crafterWoodType + "_crafter"));
        }
    }
}
