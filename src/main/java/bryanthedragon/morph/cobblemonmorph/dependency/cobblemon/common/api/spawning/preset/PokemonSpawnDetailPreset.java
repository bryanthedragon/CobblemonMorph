package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;

public class PokemonSpawnDetailPreset : SpawnDetailPreset {
   public final var levelRange: IntRange?
   public final var pokemon: PokemonProperties?

   public override fun apply(spawnDetail: SpawnDetail) {
      super.apply(spawnDetail);
      if (spawnDetail is PokemonSpawnDetail) {
         val pokemon: PokemonProperties = this.pokemon;
         if (this.pokemon != null) {
            (spawnDetail as PokemonSpawnDetail)
               .setPokemon(
                  PokemonProperties.Companion.parse$default(
                     PokemonProperties.Companion,
                     "${(spawnDetail as PokemonSpawnDetail).getPokemon().getOriginalString()} ${pokemon.getOriginalString()}",
                     null,
                     null,
                     6,
                     null
                  )
               );
         }

         if (this.levelRange != null) {
            (spawnDetail as PokemonSpawnDetail).setLevelRange(this.levelRange);
         }
      }
   }

   public companion object {
      public const val NAME: String
   }
}
