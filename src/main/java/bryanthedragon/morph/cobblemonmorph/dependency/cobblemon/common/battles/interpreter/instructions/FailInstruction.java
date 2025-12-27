package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class FailInstruction(message: BattleMessage) : InterpreterInstruction {
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
                     label72: {
                        val pokemonName: MutableComponent = var10000.getName();
                        val var19: Effect = this.this$0.getMessage().effectAt(1);
                        val effectID: java.lang.String = if (var19 != null) var19.getId() else null;
                        val cause: Effect = this.this$0.getMessage().effect("from");
                        val of: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                        label52:
                        if (effectID != null) {
                           label59: {
                              switch (effectID.hashCode()) {
                                 case -1599618703:
                                    if (effectID.equals("doubleshock")) {
                                       break label52;
                                    }
                                    break;
                                 case -1377752918:
                                    if (effectID.equals("burnup")) {
                                       break label52;
                                    }
                                    break;
                                 case -641723388:
                                    if (effectID.equals("shedtail")) {
                                       var20 = LocalizationUtilsKt.battleLang("fail.substitute", pokemonName);
                                       break label72;
                                    }
                                    break;
                                 case -537625199:
                                    if (effectID.equals("corrosivegas")) {
                                       var20 = LocalizationUtilsKt.battleLang("fail.healblock", pokemonName);
                                       break label72;
                                    }
                                    break;
                                 case -492715048:
                                    if (effectID.equals("aurawheel")) {
                                       break label59;
                                    }
                                    break;
                                 case -293122902:
                                    if (effectID.equals("unboost")) {
                                       val var13: java.lang.String = this.this$0.getMessage().argumentAt(2);
                                       val stat: Component = if (var13 != null) Stats.Companion.getStat(var13).getDisplayName() else null;
                                       var20 = if (stat != null)
                                          LocalizationUtilsKt.battleLang("fail.$effectID.single", pokemonName, stat)
                                          else
                                          LocalizationUtilsKt.battleLang("fail.$effectID", pokemonName);
                                       break label72;
                                    }
                                    break;
                                 case 702307440:
                                    if (effectID.equals("hyperspacefury")) {
                                       break label59;
                                    }
                                    break;
                                 case 2124767068:
                                    if (effectID.equals("dynamax")) {
                                       var20 = LocalizationUtilsKt.battleLang("fail.grassknot", pokemonName);
                                       break label72;
                                    }
                                 default:
                              }

                              var20 = LocalizationUtilsKt.battleLang("fail.$effectID", pokemonName);
                              break label72;
                           }

                           var20 = LocalizationUtilsKt.battleLang("fail.darkvoid", pokemonName);
                           break label72;
                        }

                        var20 = LocalizationUtilsKt.battleLang("fail");
                     }

                     val var25: PokemonBattle = this.$battle;
                     var25.broadcastChatMessage(TextKt.red(var20) as Component);
                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                  }
               }
            }
         ) as () -> Unit
      );
   }
}
