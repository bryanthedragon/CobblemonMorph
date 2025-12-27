package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import com.google.gson.Gson

public class StarterConfig {
   public final var allowStarterOnJoin: Boolean = true
   public final var promptStarterOnceOnly: Boolean = true
   public final var starters: MutableList<StarterCategory> =
      CollectionsKt.mutableListOf(
         new StarterCategory[]{
            new StarterCategory(
               "Kanto",
               "cobblemon.starterselection.category.kanto",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Bulbasaur level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Charmander level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Squirtle level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Johto",
               "cobblemon.starterselection.category.johto",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Chikorita level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Cyndaquil level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Totodile level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Hoenn",
               "cobblemon.starterselection.category.hoenn",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Treecko level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Torchic level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Mudkip level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Sinnoh",
               "cobblemon.starterselection.category.sinnoh",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Turtwig level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Chimchar level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Piplup level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Unova",
               "cobblemon.starterselection.category.unova",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Snivy level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Tepig level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Oshawott level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Kalos",
               "cobblemon.starterselection.category.kalos",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Chespin level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Fennekin level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Froakie level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Alola",
               "cobblemon.starterselection.category.alola",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Rowlet level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Litten level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Popplio level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Galar",
               "cobblemon.starterselection.category.galar",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Grookey level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Scorbunny level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Sobble level=10", null, null, 6, null)
                  }
               )
            ),
            new StarterCategory(
               "Hisui Bias",
               "cobblemon.starterselection.category.hisui_bias",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(
                        PokemonProperties.Companion, "Rowlet region_bias=hisui level=10 pokeball=ancient_poke_ball", null, null, 6, null
                     ),
                     PokemonProperties.Companion.parse$default(
                        PokemonProperties.Companion, "Cyndaquil region_bias=hisui level=10 pokeball=ancient_poke_ball", null, null, 6, null
                     ),
                     PokemonProperties.Companion.parse$default(
                        PokemonProperties.Companion, "Oshawott region_bias=hisui level=10 pokeball=ancient_poke_ball", null, null, 6, null
                     )
                  }
               )
            ),
            new StarterCategory(
               "Paldea",
               "cobblemon.starterselection.category.paldea",
               CollectionsKt.mutableListOf(
                  new PokemonProperties[]{
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Sprigatito level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Fuecoco level=10", null, null, 6, null),
                     PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Quaxly level=10", null, null, 6, null)
                  }
               )
            )
         }
      )

   public companion object {
      public final val GSON: Gson
   }
}
