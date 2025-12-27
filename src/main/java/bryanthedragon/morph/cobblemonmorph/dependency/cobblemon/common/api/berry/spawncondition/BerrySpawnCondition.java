package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import net.minecraft.core.Holder
import net.minecraft.util.RandomSource
import net.minecraft.world.level.biome.Biome

public interface BerrySpawnCondition {
   public abstract fun getGroveSize(random: RandomSource): Int {
   }

   public abstract fun canSpawn(berry: Berry, biome: Holder<Biome>): Boolean {
   }
}
