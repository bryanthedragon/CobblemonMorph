package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import com.google.gson.JsonArray
import java.util.UUID

public interface ShowdownService {
   public abstract fun openConnection() {
   }

   public abstract fun closeConnection() {
   }

   public abstract fun startBattle(battle: PokemonBattle, messages: Array<String>) {
   }

   public abstract fun send(battleId: UUID, messages: Array<String>) {
   }

   public abstract fun getAbilityIds(): JsonArray {
   }

   public abstract fun getMoves(): JsonArray {
   }

   public abstract fun getItemIds(): JsonArray {
   }

   public abstract fun registerSpecies() {
   }

   public abstract fun registerBagItems() {
   }

   public open fun indicateSpeciesInitialized() {
   }

   public companion object {
      public final val service: ShowdownService by LazyKt.lazy(<unrepresentable>.INSTANCE)
         public final get() {
            return service$delegate.getValue() as ShowdownService;
         }

   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun indicateSpeciesInitialized(`$this`: ShowdownService) {
      }
   }
}
