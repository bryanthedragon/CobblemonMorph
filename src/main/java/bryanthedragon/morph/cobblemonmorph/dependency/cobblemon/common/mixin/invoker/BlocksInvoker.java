package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker;

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksInvoker {
   @Invoker("createLogBlock")
   static RotatedPillarBlock createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
      throw new UnsupportedOperationException();
   }

   @Invoker("createLeavesBlock")
   static LeavesBlock createLeavesBlock(SoundType soundGroup) {
      throw new UnsupportedOperationException();
   }

   @Invoker("createWoodenButtonBlock")
   static ButtonBlock createWoodenButtonBlock(BlockSetType blockSetType, FeatureFlag... requiredFeatures) {
      throw new UnsupportedOperationException();
   }

   @Invoker("createFlowerPotBlock")
   static FlowerPotBlock createFlowerPotBlock(Block flower, FeatureFlag... requiredFeatures) {
      throw new UnsupportedOperationException();
   }
}
