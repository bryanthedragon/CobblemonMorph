package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.Locale
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class DetailsChangeInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val var5: java.lang.String = this.message.argumentAt(1);
         if (var5 != null) {
            val var7: java.util.List = StringsKt.split$default(var5, new char[]{','}, false, 0, 6, null);
            if (var7 != null) {
               val var8: java.lang.String = var7.get(0) as java.lang.String;
               if (var8 != null) {
                  val var9: java.lang.String = StringsKt.substringAfter$default(var8, '-', null, 2, null);
                  if (var9 != null) {
                     val var10: java.lang.String = var9.toLowerCase(Locale.ROOT);
                     if (var10 != null) {
                        val formName: java.lang.String = var10;
                        PokemonBattle.dispatchWaiting$default(battle, 0.0F, (new Function0<Unit>(var10000, battle, formName, this) {
                           {
                              super(0);
                              this.$battlePokemon = `$battlePokemon`;
                              this.$battle = `$battle`;
                              this.$formName = `$formName`;
                              this.this$0 = `$receiver`;
                           }

                           public final void invoke() {
                              val pokemonName: MutableComponent = this.$battlePokemon.getName();
                              val var10000: PokemonBattle = this.$battle;
                              val var3: MutableComponent = LocalizationUtilsKt.battleLang("detailschange.${this.$formName}", pokemonName);
                              var10000.broadcastChatMessage(var3 as Component);
                              this.$battle.getMajorBattleActions().put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
                           }
                        }) as Function0, 1, null);
                        return;
                     }
                  }
               }
            }
         }
      }
   }
}
