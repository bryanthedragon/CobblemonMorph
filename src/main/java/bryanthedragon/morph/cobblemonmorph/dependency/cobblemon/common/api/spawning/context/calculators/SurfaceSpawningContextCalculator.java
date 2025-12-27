package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SurfaceSpawningContext;

import kotlin.jvm.functions.Function1;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class SurfaceSpawningContextCalculator extends FlooredSpawningContextCalculator<SurfaceSpawningContext> {
   public Function1<BlockState, Boolean> baseCondition = new Function1<BlockState, Boolean>() { };
   public String name = "surface";
   public Function1<BlockState, Boolean> surroundingCondition = SpawningContextCalculator.isAirCondition();

   public SurfaceSpawningContext calculate(AreaSpawningInput input) {
      val var2: SpawnCause = input.getCause();
      val var3: ServerLevel = input.getWorld();
      val var4: BlockPos = input.getPosition().m_7949_();
      val var5: Int = AreaSpawningContextCalculator.DefaultImpls.getLight$default(this, input, 0, 2, null);
      val var6: Int = AreaSpawningContextCalculator.DefaultImpls.getSkyLight$default(this, input, 0, 2, null);
      val var7: Boolean = this.getCanSeeSky(input);
      val var8: java.util.List = input.getSpawner().copyInfluences();
      val var9: Int = AreaSpawningContextCalculator.DefaultImpls.getHeight$default(this, input, this.getSurroundingCondition(), Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace() / 2, 0, 1, 0, 40, null);
      val var10: Int = this.getDepth(input, this.getBaseCondition(), Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace() / 2);
      val var11: WorldSlice = input.getSlice();
      val var12: java.util.List = AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks$default(this, input, 0, 0, 6, null);
      return new SurfaceSpawningContext(var2, var3, var4, var5, var6, var7, var8, var9, var10, var12, var11);
   }

   @Override
   public Boolean fits(AreaSpawningInput input) {
      return FlooredSpawningContextCalculator.DefaultImpls.fits(this, input);
   }

   @Override
   public int getDepth(AreaSpawningInput input, Function1<BlockState, Boolean> condition, int maximum) {
      return FlooredSpawningContextCalculator.DefaultImpls.getDepth(this, input, condition, maximum);
   }

   @Override
   public int getHeight(AreaSpawningInput input, Function1<BlockState, Boolean> condition, int maximum, int offsetX, int offsetY, int offsetZ) {
      return FlooredSpawningContextCalculator.DefaultImpls.getHeight(this, input, condition, maximum, offsetX, offsetY, offsetZ);
   }

   @Override
   public int getHorizontalSpace(AreaSpawningInput input, Function1<BlockState, Boolean> condition, int maximum, int offsetX, int offsetY, int offsetZ) {
      return FlooredSpawningContextCalculator.DefaultImpls.getHorizontalSpace(this, input, condition, maximum, offsetX, offsetY, offsetZ);
   }

   @Override
   public int getLight(AreaSpawningInput input, int elseLight) {
      return FlooredSpawningContextCalculator.DefaultImpls.getLight(this, input, elseLight);
   }

   @Override
   public int getSkyLight(AreaSpawningInput input, int elseLight) {
      return FlooredSpawningContextCalculator.DefaultImpls.getSkyLight(this, input, elseLight);
   }

   @Override
   public Boolean getCanSeeSky(AreaSpawningInput input) {
      return FlooredSpawningContextCalculator.DefaultImpls.getCanSeeSky(this, input);
   }

   @Override
   public int getSkySpaceAbove(AreaSpawningInput input) {
      return FlooredSpawningContextCalculator.DefaultImpls.getSkySpaceAbove(this, input);
   }

   @Override
   public java.util.List<BlockState> getNearbyBlocks(AreaSpawningInput input, int horizontalRadius, int verticalRadius) {
      return FlooredSpawningContextCalculator.getNearbyBlocks(this, input, horizontalRadius, verticalRadius);
   }
}
