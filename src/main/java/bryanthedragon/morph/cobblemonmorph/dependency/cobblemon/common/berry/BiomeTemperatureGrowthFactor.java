package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import kotlin.random.Random
import net.minecraft.core.BlockPos
import net.minecraft.predicate.NumberRange.FloatRange
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.state.BlockState

public class BiomeTemperatureGrowthFactor(range: FloatRange, bonusYield: IntRange) : GrowthFactor {
   public final val bonusYield: IntRange
   public final val range: FloatRange

   init {
      this.range = range;
      this.bonusYield = bonusYield;
   }

   public override fun validateArguments() {
      if (this.bonusYield.getFirst() < 0 || this.bonusYield.getLast() < 0) {
         throw new IllegalArgumentException("${ID} bonusYield must be a positive range");
      }
   }

   public override fun isValid(world: LevelReader, state: BlockState, pos: BlockPos): Boolean {
      return this.range.m_154810_((double)(world.m_204166_(pos).m_203334_() as Biome).m_47554_());
   }

   public override fun yield(): Int {
      return RangesKt.random(this.bonusYield, Random.Default as Random);
   }

   public override fun minYield(): Int {
      return this.bonusYield.getFirst();
   }

   public override fun maxYield(): Int {
      return this.bonusYield.getLast();
   }

   public companion object {
      public final val ID: ResourceLocation
   }
}
