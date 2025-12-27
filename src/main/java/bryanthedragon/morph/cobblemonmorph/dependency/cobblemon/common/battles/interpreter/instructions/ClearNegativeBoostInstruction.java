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

public class ClearNegativeBoostInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val battlePokemon: BattlePokemon = var10000;
         battle.dispatchWaiting(
            1.5F,
            (
               new Function0<Unit>(battlePokemon, this, battle) {
                  {
                     super(0);
                     this.$battlePokemon = `$battlePokemon`;
                     this.this$0 = `$receiver`;
                     this.$battle = `$battle`;
                  }

                  public final void invoke() {
                     val pokemonName: MutableComponent = this.$battlePokemon.getName();
                     val var10000: MutableComponent = if (this.this$0.getMessage().hasOptionalArgument("zeffect"))
                        LocalizationUtilsKt.battleLang("clearallnegativeboost.zeffect", pokemonName)
                        else
                        LocalizationUtilsKt.battleLang("clearallnegativeboost", pokemonName);
                     if (!this.this$0.getMessage().hasOptionalArgument("silent")) {
                        val var6: PokemonBattle = this.$battle;
                        var6.broadcastChatMessage(var10000 as Component);
                     }

                     this.$battlePokemon.getContextManager().clear(BattleContext.Type.UNBOOST);
                     this.$battle.getMinorBattleActions().put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
                  }
               }
            ) as () -> Unit
         );
      }
   }
}
