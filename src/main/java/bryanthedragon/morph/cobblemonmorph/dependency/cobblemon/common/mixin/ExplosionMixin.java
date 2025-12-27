package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PreEmptsExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public class ExplosionMixin {
   @Redirect(
      method = "affectWorld",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
         ordinal = 0
      )
   )
   public BlockState cobblemon$whenExploded(Level world, BlockPos pos) {
      BlockState blockState = world.m_8055_(pos);
      if (blockState.m_60734_() instanceof PreEmptsExplosion preExplosionBlock) {
         preExplosionBlock.whenExploded(world, blockState, pos);
      }

      return blockState;
   }
}
