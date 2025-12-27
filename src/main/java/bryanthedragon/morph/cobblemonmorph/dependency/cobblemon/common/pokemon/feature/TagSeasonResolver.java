package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.SeasonResolver
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBiomeTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.world.level.LevelAccessor

public object TagSeasonResolver : SeasonResolver {
   public override operator fun invoke(world: LevelAccessor, pos: BlockPos): CobblemonSeason? {
      val biome: Holder = world.m_204166_(pos);
      return if (biome.m_203656_(CobblemonBiomeTags.IS_WINTER))
         CobblemonSeason.WINTER
         else
         (
            if (biome.m_203656_(CobblemonBiomeTags.IS_SPRING))
               CobblemonSeason.SPRING
               else
               (
                  if (biome.m_203656_(CobblemonBiomeTags.IS_AUTUMN))
                     CobblemonSeason.AUTUMN
                     else
                     (if (biome.m_203656_(CobblemonBiomeTags.IS_SUMMER)) CobblemonSeason.SUMMER else null)
               )
         );
   }
}
