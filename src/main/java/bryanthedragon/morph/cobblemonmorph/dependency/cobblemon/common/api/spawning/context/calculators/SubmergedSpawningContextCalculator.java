package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext;

import kotlin.jvm.functions.Function1;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class SubmergedSpawningContextCalculator : AreaSpawningContextCalculator<SubmergedSpawningContext> {
   public final val fluidConditions: MutableList<(BlockState) -> Boolean> = CollectionsKt.mutableListOf(new Function1[]{SpawningContextCalculator.Companion.isWaterCondition(), SpawningContextCalculator.Companion.isLavaCondition()})
      public open val name: String = "submerged"

   public override fun fits(input: AreaSpawningInput): Boolean {
      val condition: Function1 = this.getFluidCondition(input);
      if (condition != null) {
         var var10001: WorldSlice = input.getSlice();
         var var10002: BlockPos = input.getPosition().m_7495_();
         if (condition.invoke(WorldSlice.getBlockState$default(var10001, var10002, null, 2, null)) as java.lang.Boolean) {
            var10001 = input.getSlice();
            var10002 = input.getPosition().m_7494_();
            if (condition.invoke(WorldSlice.getBlockState$default(var10001, var10002, null, 2, null)) as java.lang.Boolean) {
               return true;
            }
         }
      }

      return false;
   }

   public fun getFluidCondition(input: AreaSpawningInput): ((BlockState) -> Boolean)? {
      val var4: java.util.Iterator = fluidConditions.iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if (!(`element$iv` as Function1).invoke(WorldSlice.getBlockState$default(input.getSlice(), input.getPosition(), null, 2, null)) as java.lang.Boolean
               )
             {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as (BlockState?) -> java.lang.Boolean;
   }

   public open fun calculate(input: AreaSpawningInput): SubmergedSpawningContext {
      val var10000: Function1 = this.getFluidCondition(input);
      val var3: SpawnCause = input.getCause();
      val var4: ServerLevel = input.getWorld();
      val var5: BlockPos = input.getPosition().m_7949_();
      val var6: Int = AreaSpawningContextCalculator.DefaultImpls.getLight$default(this, input, 0, 2, null);
      val var7: Int = AreaSpawningContextCalculator.DefaultImpls.getSkyLight$default(this, input, 0, 2, null);
      val var8: Boolean = this.getCanSeeSky(input);
      val var9: java.util.List = input.getSpawner().copyInfluences();
      val var10: Int = AreaSpawningContextCalculator.DefaultImpls.getHeight$default(
         this, input, var10000, Mth.m_14167_((float)Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace() / 2.0F), 0, 0, 0, 56, null
      );
      val var11: Int = this.getDepth(input, var10000, Mth.m_14167_((float)Cobblemon.INSTANCE.getConfig().getMaxVerticalSpace() / 2.0F));
      val var12: WorldSlice = input.getSlice();
      val var13: java.util.List = AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks$default(this, input, 0, 0, 6, null);
      return new SubmergedSpawningContext(var3, var4, var5, var6, var7, var8, var9, var10, var11, var13, var12);
   }

   override fun getDepth(input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int): Int {
      return AreaSpawningContextCalculator.DefaultImpls.getDepth(this, input, condition, maximum);
   }

   override fun getHeight(input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int, offsetX: Int, offsetY: Int, offsetZ: Int): Int {
      return AreaSpawningContextCalculator.DefaultImpls.getHeight(this, input, condition, maximum, offsetX, offsetY, offsetZ);
   }

   override fun getHorizontalSpace(
      input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int, offsetX: Int, offsetY: Int, offsetZ: Int
   ): Int {
      return AreaSpawningContextCalculator.DefaultImpls.getHorizontalSpace(this, input, condition, maximum, offsetX, offsetY, offsetZ);
   }

   override fun getLight(input: AreaSpawningInput, elseLight: Int): Int {
      return AreaSpawningContextCalculator.DefaultImpls.getLight(this, input, elseLight);
   }

   override fun getSkyLight(input: AreaSpawningInput, elseLight: Int): Int {
      return AreaSpawningContextCalculator.DefaultImpls.getSkyLight(this, input, elseLight);
   }

   override fun getCanSeeSky(input: AreaSpawningInput): Boolean {
      return AreaSpawningContextCalculator.DefaultImpls.getCanSeeSky(this, input);
   }

   override fun getSkySpaceAbove(input: AreaSpawningInput): Int {
      return AreaSpawningContextCalculator.DefaultImpls.getSkySpaceAbove(this, input);
   }

   override fun getNearbyBlocks(input: AreaSpawningInput, horizontalRadius: Int, verticalRadius: Int): MutableList<BlockState> {
      return AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks(this, input, horizontalRadius, verticalRadius);
   }
}
