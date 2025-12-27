package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class FieldActivateInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(2.5F, (new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: Effect = this.this$0.getMessage().effectAt(0);
            if (var10000 != null) {
               val var11: java.lang.String = var10000.getId();
               if (var11 != null) {
                  val lang: MutableComponent = LocalizationUtilsKt.battleLang("fieldactivate.$var11");
                  val var12: PokemonBattle = this.$battle;
                  var12.broadcastChatMessage(TextKt.red(lang) as Component);
                  val `$this$forEach$iv`: java.lang.Iterable = this.$battle.getActivePokemon();
                  val var4: FieldActivateInstruction = this.this$0;
                  val var5: PokemonBattle = this.$battle;

                  for (Object element$iv : $this$forEach$iv) {
                     val var13: BattlePokemon = (`element$iv` as ActiveBattlePokemon).getBattlePokemon();
                     if (var13 != null) {
                        val var14: ContextManager = var13.getContextManager();
                        if (var14 != null) {
                           var14.addUnique(ShowdownInterpreter.INSTANCE.getContextFromAction(var4.getMessage(), BattleContext.Type.VOLATILE, var5));
                        }
                     }
                  }

                  return;
               }
            }
         }
      }) as () -> Unit);
   }
}
