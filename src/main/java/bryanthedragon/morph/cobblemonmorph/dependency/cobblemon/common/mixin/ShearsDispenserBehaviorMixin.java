package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ShearableBlock;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsDispenseItemBehavior.class)
public abstract class ShearsDispenserBehaviorMixin {
   @Inject(method = "tryShearBlock", at = @At("HEAD"), cancellable = true)
   private static void cobblemon$tryApricornHarvest(ServerLevel world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
      BlockState state = world.m_8055_(pos);
      if (state.m_60734_() instanceof ShearableBlock shearableBlock) {
         cir.setReturnValue(shearableBlock.attemptShear(world, state, pos, () -> Unit.INSTANCE));
      }
   }
}
