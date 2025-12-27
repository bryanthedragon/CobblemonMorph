package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.CobblemonSeason

import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelAccessor

public fun interface SeasonResolver {
   public abstract operator fun invoke(world: LevelAccessor, pos: BlockPos): CobblemonSeason? {
   }
}
