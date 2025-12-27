package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class ClearAllBoostInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(1.5F, (new Function0<Unit>(battle) {
         {
            super(0);
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               val var10000: BattlePokemon = (`element$iv` as ActiveBattlePokemon).getBattlePokemon();
               if (var10000 != null) {
                  val var8: ContextManager = var10000.getContextManager();
                  if (var8 != null) {
                     var8.clear(BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                  }
               }
            }

            val var9: PokemonBattle = this.$battle;
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("clearallboost");
            var9.broadcastChatMessage(var10001 as Component);
         }
      }) as () -> Unit);
   }
}
