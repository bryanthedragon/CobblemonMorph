package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class SwapBoostInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(2.0F, (new Function0<Unit>(this, battle) {
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
                  val targetPokemonName: MutableComponent = var10000.getName();
                  val var12: Effect = BattleMessage.effect$default(this.this$0.getMessage(), null, 1, null);
                  if (var12 != null) {
                     val var13: java.lang.String = var12.getId();
                     if (var13 != null) {
                        label29: {
                           label28: {
                              switch (var13.hashCode()) {
                                 case -185707080:
                                    if (var13.equals("guardswap")) {
                                       break label28;
                                    }
                                    break;
                                 case 201420505:
                                    if (var13.equals("heartswap")) {
                                       break label28;
                                    }
                                    break;
                                 case 846106648:
                                    if (var13.equals("powerswap")) {
                                       break label28;
                                    }
                                 default:
                              }

                              var14 = LocalizationUtilsKt.battleLang("swapboost.generic", pokemonName, targetPokemonName);
                              break label29;
                           }

                           var14 = LocalizationUtilsKt.battleLang("swapboost.$var13", pokemonName);
                        }

                        val var16: PokemonBattle = this.$battle;
                        var16.broadcastChatMessage(var14 as Component);
                        var10000.getContextManager().swap(var10000.getContextManager(), BattleContext.Type.BOOST);
                        var10000.getContextManager().swap(var10000.getContextManager(), BattleContext.Type.UNBOOST);
                        this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                        return;
                     }
                  }
               }
            }
         }
      }) as () -> Unit);
   }
}
