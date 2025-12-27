package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.LastBattleCriticalHitsEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class CritInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchGo(
         (
            new Function0<Unit>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  var var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 != null) {
                     val var8: PokemonBattle = this.$battle;
                     val var10001: MutableComponent = LocalizationUtilsKt.battleLang("crit");
                     var8.broadcastChatMessage(TextKt.yellow(var10001) as Component);
                     val var9: BattleMessage = ShowdownInterpreter.INSTANCE.getLastCauser().get(this.$battle.getBattleId());
                     if (var9 != null) {
                        var10000 = var9.battlePokemon(0, this.$battle);
                        if (var10000 != null) {
                           if (LastBattleCriticalHitsEvolutionProgress.Companion.supports(var10000.getEffectedPokemon())) {
                              val progress: LastBattleCriticalHitsEvolutionProgress = var10000.getEffectedPokemon()
                                 .getEvolutionProxy()
                                 .current()
                                 .progressFirstOrCreate(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
                              progress.updateProgress(new LastBattleCriticalHitsEvolutionProgress.Progress(progress.currentProgress().getAmount() + 1));
                           }
                        }
                     }

                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                  }
               }
            }
         ) as () -> Unit
      );
   }
}
