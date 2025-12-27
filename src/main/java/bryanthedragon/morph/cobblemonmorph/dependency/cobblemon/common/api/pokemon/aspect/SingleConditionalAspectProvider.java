package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull

public interface SingleConditionalAspectProvider : AspectProvider {
   public val aspect: String

   public abstract fun meetsCondition(pokemon: Pokemon): Boolean {
   }

   public abstract fun meetsCondition(pokemonProperties: PokemonProperties): Boolean {
   }

   public override fun provide(properties: PokemonProperties): Set<String> {
   }

   public override fun provide(pokemon: Pokemon): Set<String> {
   }

   public companion object {
      public fun getForFeature(name: String): SingleConditionalAspectProvider {
         return new SingleConditionalAspectProvider(name) {
            @NotNull
            private final java.lang.String aspect;

            {
               this.$name = `$name`;
               this.aspect = `$name`;
            }

            @NotNull
            @Override
            public java.lang.String getAspect() {
               return this.aspect;
            }

            @Override
            public boolean meetsCondition(@NotNull Pokemon pokemon) {
               val var10000: FlagSpeciesFeature = pokemon.getFeature(this.$name);
               return var10000 != null && var10000.getEnabled();
            }

            @Override
            public boolean meetsCondition(@NotNull PokemonProperties pokemonProperties) {
               var `$this$any$iv`: java.lang.Iterable = pokemonProperties.getCustomProperties();
               val `destination$iv$iv`: java.util.Collection = new ArrayList();

               for (Object element$iv$iv : $this$any$iv) {
                  if (var8 is FlagSpeciesFeature) {
                     `destination$iv$iv`.add(var8);
                  }
               }

               `$this$any$iv` = `destination$iv$iv` as java.util.List;
               val var10: java.lang.String = this.$name;
               var var10000: Boolean;
               if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
                  var10000 = false;
               } else {
                  val var11: java.util.Iterator = `$this$any$iv`.iterator();

                  while (true) {
                     if (!var11.hasNext()) {
                        var10000 = false;
                        break;
                     }

                     val var13: FlagSpeciesFeature = var11.next() as FlagSpeciesFeature;
                     if (var13.getName() == var10 && var13.getEnabled()) {
                        var10000 = true;
                        break;
                     }
                  }
               }

               return var10000;
            }

            @NotNull
            @Override
            public java.util.Set<java.lang.String> provide(@NotNull PokemonProperties properties) {
               return SingleConditionalAspectProvider.DefaultImpls.provide(this, properties);
            }

            @NotNull
            @Override
            public java.util.Set<java.lang.String> provide(@NotNull Pokemon pokemon) {
               return SingleConditionalAspectProvider.DefaultImpls.provide(this, pokemon);
            }

            @NotNull
            @Override
            public AspectProvider register() {
               return SingleConditionalAspectProvider.DefaultImpls.register(this);
            }
         };
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun provide(`$this`: SingleConditionalAspectProvider, properties: PokemonProperties): MutableSet<java.lang.String> {
         return if (`$this`.meetsCondition(properties)) SetsKt.setOf(`$this`.getAspect()) else SetsKt.emptySet();
      }

      @JvmStatic
      fun provide(`$this`: SingleConditionalAspectProvider, pokemon: Pokemon): MutableSet<java.lang.String> {
         return if (`$this`.meetsCondition(pokemon)) SetsKt.setOf(`$this`.getAspect()) else SetsKt.emptySet();
      }

      @JvmStatic
      fun register(`$this`: SingleConditionalAspectProvider): AspectProvider {
         return AspectProvider.DefaultImpls.register(`$this`);
      }
   }
}
