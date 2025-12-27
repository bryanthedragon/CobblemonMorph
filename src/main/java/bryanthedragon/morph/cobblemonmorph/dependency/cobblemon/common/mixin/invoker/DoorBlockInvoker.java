package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DoorBlock.class)
public interface DoorBlockInvoker {
   @Invoker("<init>")
   static DoorBlock cobblemon$create(Properties settings, BlockSetType blockSetType) {
      throw new UnsupportedOperationException();
   }
}
