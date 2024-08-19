package de.pnku.mcrv.mixin;

import de.pnku.mcrv.block.MoreCrafterVariantBlockEntity;
import net.minecraft.network.protocol.game.ServerboundContainerSlotStateChangedPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CrafterMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "handleContainerSlotStateChanged", at = @At("TAIL"))
    public void injectedHandleContainerSlotStateChanged(ServerboundContainerSlotStateChangedPacket packet, CallbackInfo ci) {
        Container container = ((CrafterMenu) this.player.containerMenu).getContainer();
        if (container instanceof MoreCrafterVariantBlockEntity) {
            MoreCrafterVariantBlockEntity mrcvBlockEntity = (MoreCrafterVariantBlockEntity) container;
            if (!this.player.isSpectator() && packet.containerId() == this.player.containerMenu.containerId) {
                mrcvBlockEntity.setSlotState(packet.slotId(), packet.newState());
            }

        }
    }
}
