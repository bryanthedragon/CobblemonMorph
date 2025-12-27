package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StairBlock.class)
public interface StairsBlockInvoker {
   @Invoker("<init>")
   static StairBlock cobblemon$create(BlockState baseBlockState, Properties settings) {
      throw new UnsupportedOperationException();
   }
}
