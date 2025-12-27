package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class PrepareInstruction(message: BattleMessage) : InterpreterInstruction {
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
                     val var9: Effect = this.this$0.getMessage().effectAt(1);
                     if (var9 != null) {
                        val var10: java.lang.String = var9.getId();
                        if (var10 != null) {
                           val var11: MutableComponent = if (var10 == "shadowforce")
                              LocalizationUtilsKt.battleLang("prepare.phantomforce", pokemonName)
                              else
                              (
                                 if (var10 == "solarblade")
                                    LocalizationUtilsKt.battleLang("prepare.solarbeam", pokemonName)
                                    else
                                    LocalizationUtilsKt.battleLang("prepare.$var10", pokemonName)
                              );
                           val var13: PokemonBattle = this.$battle;
                           var13.broadcastChatMessage(var11 as Component);
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
