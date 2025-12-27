package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.StringSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Locale
import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelAccessor

public object SeasonFeatureHandler {
   public fun updateSeason(pokemon: Pokemon, world: LevelAccessor, pos: BlockPos) {
      this.updateSeason(pokemon, Cobblemon.INSTANCE.getSeasonResolver().invoke(world, pos));
   }

   public fun updateSeason(pokemon: Pokemon, season: CobblemonSeason?) {
      val var10000: StringSpeciesFeature = pokemon.getFeature("season");
      if (var10000 != null) {
         var currentSeason: java.lang.String;
         label21: {
            currentSeason = var10000.getValue();
            if (season != null) {
               val var6: java.lang.String = season.name();
               if (var6 != null) {
                  var7 = var6.toLowerCase(Locale.ROOT);
                  break label21;
               }
            }

            var7 = null;
         }

         if (!(currentSeason == var7) && var7 != null) {
            var10000.setValue(var7);
            pokemon.updateAspects();
            pokemon.markFeatureDirty(var10000);
         }
      }
   }
}
