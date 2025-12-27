package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SeafloorSpawningContext;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState

public object SeafloorSpawningContextCalculator : FlooredSpawningContextCalculator<SeafloorSpawningContext> {
   public open val baseCondition: (BlockState) -> Boolean = SpawningContextCalculator.Companion.isSolidCondition()
   public open val name: String = "seafloor"
   public open val surroundingCondition: (BlockState) -> Boolean = SpawningContextCalculator.Companion.isWaterCondition()

   public open fun calculate(input: AreaSpawningInput): SeafloorSpawningContext {
      val var2: SpawnCause = input.getCause();
      val var3: ServerLevel = input.getWorld();
      val var4: BlockPos = input.getPosition().m_7949_();
      val var5: Int = AreaSpawningContextCalculator.DefaultImpls.getLight$default(this, input, 0, 2, null);
      val var6: Int = AreaSpawningContextCalculator.DefaultImpls.getSkyLight$default(this, input, 0, 2, null);
      val var7: Boolean = this.getCanSeeSky(input);
      val var8: java.util.List = input.getSpawner().copyInfluences();
      val var9: Int = AreaSpawningContextCalculator.DefaultImpls.getHeight$default(
         this, input, this.getSurroundingCondition(), Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace(), 0, 1, 0, 40, null
      );
      val var10: WorldSlice = input.getSlice();
      val var11: java.util.List = AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks$default(this, input, 0, 0, 6, null);
      return new SeafloorSpawningContext(var2, var3, var4, var5, var6, var7, var8, var9, var11, var10);
   }

   override fun fits(input: AreaSpawningInput): Boolean {
      return FlooredSpawningContextCalculator.DefaultImpls.fits(this, input);
   }

   override fun getDepth(input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int): Int {
      return FlooredSpawningContextCalculator.DefaultImpls.getDepth(this, input, condition, maximum);
   }

   override fun getHeight(input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int, offsetX: Int, offsetY: Int, offsetZ: Int): Int {
      return FlooredSpawningContextCalculator.DefaultImpls.getHeight(this, input, condition, maximum, offsetX, offsetY, offsetZ);
   }

   override fun getHorizontalSpace(
      input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int, offsetX: Int, offsetY: Int, offsetZ: Int
   ): Int {
      return FlooredSpawningContextCalculator.DefaultImpls.getHorizontalSpace(this, input, condition, maximum, offsetX, offsetY, offsetZ);
   }

   override fun getLight(input: AreaSpawningInput, elseLight: Int): Int {
      return FlooredSpawningContextCalculator.DefaultImpls.getLight(this, input, elseLight);
   }

   override fun getSkyLight(input: AreaSpawningInput, elseLight: Int): Int {
      return FlooredSpawningContextCalculator.DefaultImpls.getSkyLight(this, input, elseLight);
   }

   override fun getCanSeeSky(input: AreaSpawningInput): Boolean {
      return FlooredSpawningContextCalculator.DefaultImpls.getCanSeeSky(this, input);
   }

   override fun getSkySpaceAbove(input: AreaSpawningInput): Int {
      return FlooredSpawningContextCalculator.DefaultImpls.getSkySpaceAbove(this, input);
   }

   override fun getNearbyBlocks(input: AreaSpawningInput, horizontalRadius: Int, verticalRadius: Int): MutableList<BlockState> {
      return FlooredSpawningContextCalculator.DefaultImpls.getNearbyBlocks(this, input, horizontalRadius, verticalRadius);
   }
}
