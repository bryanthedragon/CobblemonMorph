package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonEmptyHeldItemManager
import net.minecraft.network.chat.Component

public interface HeldItemManager {
   public abstract fun showdownId(pokemon: BattlePokemon): String? {
   }

   public abstract fun nameOf(showdownId: String): Component {
   }

   public abstract fun handleStartInstruction(pokemon: BattlePokemon, battle: PokemonBattle, battleMessage: BattleMessage) {
   }

   public abstract fun handleEndInstruction(pokemon: BattlePokemon, battle: PokemonBattle, battleMessage: BattleMessage) {
   }

   public abstract fun give(pokemon: BattlePokemon, showdownId: String) {
   }

   public abstract fun take(pokemon: BattlePokemon, showdownId: String) {
   }

   public open fun shouldConsumeItem(pokemon: BattlePokemon, battle: PokemonBattle, showdownId: String): Boolean {
   }

   public companion object {
      public final val EMPTY: HeldItemManager = CobblemonEmptyHeldItemManager.INSTANCE as HeldItemManager
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun shouldConsumeItem(`$this`: HeldItemManager, pokemon: BattlePokemon, battle: PokemonBattle, showdownId: java.lang.String): Boolean {
         return false;
      }
   }
}
