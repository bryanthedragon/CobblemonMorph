package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class SetBoostInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(
         1.5F,
         (
            new Function0<Unit>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 != null) {
                     val pokemonName: MutableComponent = var10000.getName();
                     val var7: Effect = BattleMessage.effect$default(this.this$0.getMessage(), null, 1, null);
                     if (var7 != null) {
                        val var8: java.lang.String = var7.getId();
                        if (var8 != null) {
                           val lang: MutableComponent = LocalizationUtilsKt.battleLang("setboost.$var8", pokemonName);
                           val var10: PokemonBattle = this.$battle;
                           var10.broadcastChatMessage(lang as Component);
                           var10000.getContextManager()
                              .add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.BOOST, this.$battle));
                           this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                           return;
                        }
                     }
                  }
               }
            }
         ) as () -> Unit
      );
   }
}
