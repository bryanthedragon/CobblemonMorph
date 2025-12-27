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

public class SingleMoveInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(1.5F, (new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 != null) {
               val pokemonName: MutableComponent = var10000.getName();
               val var6: Effect = this.this$0.getMessage().effectAt(1);
               if (var6 != null) {
                  val var7: java.lang.String = var6.getId();
                  if (var7 != null) {
                     val lang: MutableComponent = LocalizationUtilsKt.battleLang("singlemove.$var7", pokemonName);
                     val var9: PokemonBattle = this.$battle;
                     var9.broadcastChatMessage(lang as Component);
                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                     return;
                  }
               }
            }
         }
      }) as () -> Unit);
   }
}
