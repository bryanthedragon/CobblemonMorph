package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer

public fun interface PokemonPropertyExtractor {
   public abstract operator fun invoke(pokemon: Pokemon, properties: PokemonProperties) {
   }

   public companion object {
      public final val ABILITY: PokemonPropertyExtractor
      public final val ALL: MutableList<PokemonPropertyExtractor>
      public final val ASPECTS: PokemonPropertyExtractor
      public final val EVS: PokemonPropertyExtractor
      public final val FORM: PokemonPropertyExtractor
      public final val FRIENDSHIP: PokemonPropertyExtractor
      public final val GENDER: PokemonPropertyExtractor
      public final val ILLUSION: MutableList<PokemonPropertyExtractor>
      public final val IVS: PokemonPropertyExtractor
      public final val LEVEL: PokemonPropertyExtractor
      public final val NATURE: PokemonPropertyExtractor
      public final val NICKNAME: PokemonPropertyExtractor
      public final val POKEBALL: PokemonPropertyExtractor
      public final val SHINY: PokemonPropertyExtractor
      public final val SPECIES: PokemonPropertyExtractor
      public final val STATUS: PokemonPropertyExtractor
      public final val TRANSFORM: MutableList<PokemonPropertyExtractor>

      public fun add(extractor: PokemonPropertyExtractor): PokemonPropertyExtractor {
         PokemonPropertyExtractor.ALL.add(extractor);
         return extractor;
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun `SPECIES$lambda$0`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setSpecies(pokemon.getSpecies().getResourceIdentifier().toString());
      }

      @JvmStatic
      fun `FORM$lambda$1`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setForm(pokemon.getForm().formOnlyShowdownId());
      }

      @JvmStatic
      fun `SHINY$lambda$2`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setShiny(pokemon.getShiny());
      }

      @JvmStatic
      fun `ASPECTS$lambda$3`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setAspects(pokemon.getAspects());
      }

      @JvmStatic
      fun `LEVEL$lambda$4`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setLevel(pokemon.getLevel());
      }

      @JvmStatic
      fun `GENDER$lambda$5`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setGender(pokemon.getGender());
      }

      @JvmStatic
      fun `FRIENDSHIP$lambda$6`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setFriendship(pokemon.getFriendship());
      }

      @JvmStatic
      fun `POKEBALL$lambda$7`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setPokeball(pokemon.getCaughtBall().getName().toString());
      }

      @JvmStatic
      fun `NATURE$lambda$8`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setNature(pokemon.getNature().getName().toString());
      }

      @JvmStatic
      fun `ABILITY$lambda$9`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setAbility(pokemon.getAbility().getName());
      }

      @JvmStatic
      fun `NICKNAME$lambda$10`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setNickname(pokemon.getNickname());
      }

      @JvmStatic
      fun `STATUS$lambda$11`(pokemon: Pokemon, properties: PokemonProperties) {
         var var3: java.lang.String;
         label12: {
            val var10001: PersistentStatusContainer = pokemon.getStatus();
            if (var10001 != null) {
               val var2: PersistentStatus = var10001.getStatus();
               if (var2 != null) {
                  var3 = var2.getShowdownName();
                  break label12;
               }
            }

            var3 = null;
         }

         properties.setStatus(var3);
      }

      @JvmStatic
      fun `IVS$lambda$12`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setIvs(pokemon.getIvs());
      }

      @JvmStatic
      fun `EVS$lambda$13`(pokemon: Pokemon, properties: PokemonProperties) {
         properties.setEvs(pokemon.getEvs());
      }
   }
}
