package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome

public class AllBiomeCondition(minGroveSize: Int, maxGroveSize: Int) : BerrySpawnCondition {
   public final val maxGroveSize: Int
   public final val minGroveSize: Int

   init {
      this.minGroveSize = minGroveSize;
      this.maxGroveSize = maxGroveSize;
   }

   public override fun canSpawn(berry: Berry, biome: Holder<Biome>): Boolean {
      return true;
   }

   public override fun getGroveSize(random: RandomSource): Int {
      return random.m_216332_(this.minGroveSize, this.maxGroveSize);
   }

   public companion object {
      public final val ID: ResourceLocation
   }
}
