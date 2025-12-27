package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class ZPowerInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val battlePokemon: BattlePokemon = var10000;
         PokemonBattle.dispatchWaiting$default(battle, 0.0F, (new Function0<Unit>(battlePokemon, battle, this) {
            {
               super(0);
               this.$battlePokemon = `$battlePokemon`;
               this.$battle = `$battle`;
               this.this$0 = `$receiver`;
            }

            public final void invoke() {
               val pokemonName: MutableComponent = this.$battlePokemon.getName();
               val var10000: PokemonBattle = this.$battle;
               val var10001: MutableComponent = LocalizationUtilsKt.battleLang("zpower", pokemonName);
               var10000.broadcastChatMessage(TextKt.yellow(var10001) as Component);
               this.$battle.getMinorBattleActions().put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
            }
         }) as Function0, 1, null);
      }
   }
}
