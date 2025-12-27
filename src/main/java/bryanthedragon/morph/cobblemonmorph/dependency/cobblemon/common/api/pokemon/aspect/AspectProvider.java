package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;

public interface AspectProvider {
   public abstract fun provide(pokemon: Pokemon): Set<String> {
   }

   public abstract fun provide(properties: PokemonProperties): Set<String> {
   }

   public open fun register(): AspectProvider {
   }

   public companion object {
      public final val providers: MutableList<AspectProvider> = (new ArrayList()) as java.util.List

      public fun register(provider: AspectProvider): AspectProvider {
         providers.add(provider);
         return provider;
      }

      public fun unregister(provider: AspectProvider) {
         providers.remove(provider);
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun register(`$this`: AspectProvider): AspectProvider {
         return AspectProvider.Companion.register(`$this`);
      }
   }
}
