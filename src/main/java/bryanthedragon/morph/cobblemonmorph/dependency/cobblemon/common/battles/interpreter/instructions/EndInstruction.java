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

public class EndInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      PokemonBattle.dispatchWaiting$default(
         battle,
         0.0F,
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
                     val var7: Effect = this.this$0.getMessage().effectAt(1);
                     if (var7 != null) {
                        val var8: java.lang.String = var7.getId();
                        if (var8 != null) {
                           if (!this.this$0.getMessage().hasOptionalArgument("silent")) {
                              val var9: MutableComponent = if (var8 == "yawn")
                                 LocalizationUtilsKt.lang("status.sleep.apply", pokemonName)
                                 else
                                 LocalizationUtilsKt.battleLang("end.$var8", pokemonName);
                              val var11: PokemonBattle = this.$battle;
                              var11.broadcastChatMessage(var9 as Component);
                           }

                           var10000.getContextManager().remove(var8, BattleContext.Type.VOLATILE);
                           this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                           return;
                        }
                     }
                  }
               }
            }
         ) as Function0,
         1,
         null
      );
   }
}
