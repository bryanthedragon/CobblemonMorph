package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class CopyBoostInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      PokemonBattle.dispatchWaiting$default(battle, 0.0F, (new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            var var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 != null) {
               val pokemonName: MutableComponent = var10000.getName();
               var10000 = this.this$0.getMessage().battlePokemon(1, this.$battle);
               if (var10000 != null) {
                  val lang: MutableComponent = LocalizationUtilsKt.battleLang("copyboost.generic", pokemonName, var10000.getName());
                  val var9: PokemonBattle = this.$battle;
                  var9.broadcastChatMessage(lang as Component);
                  var10000.getContextManager().copy(var10000.getContextManager(), BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                  this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
               }
            }
         }
      }) as Function0, 1, null);
   }
}
