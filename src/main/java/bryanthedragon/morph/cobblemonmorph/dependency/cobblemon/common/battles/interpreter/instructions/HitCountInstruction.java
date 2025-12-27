package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class HitCountInstruction(message: BattleMessage) : InterpreterInstruction {
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
                  val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 != null) {
                     val var5: java.lang.String = this.this$0.getMessage().argumentAt(1);
                     if (var5 != null) {
                        val var6: Int = StringsKt.toIntOrNull(var5);
                        if (var6 != null) {
                           val hitCount: Int = var6;
                           val var7: MutableComponent = if (hitCount == 1)
                              LocalizationUtilsKt.battleLang("hit_count_singular")
                              else
                              LocalizationUtilsKt.battleLang("hit_count", hitCount);
                           this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                           val var8: PokemonBattle = this.$battle;
                           var8.broadcastChatMessage(var7 as Component);
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
