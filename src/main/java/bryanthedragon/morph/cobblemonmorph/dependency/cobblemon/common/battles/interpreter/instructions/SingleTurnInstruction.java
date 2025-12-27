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

public class SingleTurnInstruction(message: BattleMessage) : InterpreterInstruction {
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
            var var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 != null) {
               var pokemonName: MutableComponent;
               label20: {
                  pokemonName = var10000.getName();
                  var10000 = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                  if (var10000 != null) {
                     var8 = var10000.getName();
                     if (var8 != null) {
                        break label20;
                     }
                  }

                  var8 = Component.m_237113_("UNKOWN");
               }

               val var9: Effect = this.this$0.getMessage().effectAt(1);
               if (var9 != null) {
                  val var10: java.lang.String = var9.getId();
                  if (var10 != null) {
                     val var11: java.lang.String = "singleturn.$var10";
                     val var6: Array<Any> = new Object[]{pokemonName, null};
                     var6[1] = var8;
                     val lang: MutableComponent = LocalizationUtilsKt.battleLang(var11, var6);
                     val var12: PokemonBattle = this.$battle;
                     var12.broadcastChatMessage(lang as Component);
                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                     return;
                  }
               }
            }
         }
      }) as () -> Unit);
   }
}
