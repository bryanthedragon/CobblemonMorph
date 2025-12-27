package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai.SleepDepth
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block

public class RestBehaviour {
   public final val biomes: MutableList<RegistryLikeCondition<Biome>>
   public final val blocks: MutableList<RegistryLikeCondition<Block>>
   public final val canSleep: Boolean
   public final val depth: SleepDepth
   public final val light: IntRange
   public final val sleepChance: Float
   public final val times: TimeRange
   public final val willSleepOnBed: Boolean
}
