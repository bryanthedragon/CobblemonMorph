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

public class MissInstruction(battle: PokemonBattle, message: BattleMessage) : InterpreterInstruction {
   public final val battle: PokemonBattle
   public final val message: BattleMessage
   public final val target: BattlePokemon?

   init {
      this.battle = battle;
      this.message = message;
      this.target = this.message.battlePokemon(1, this.battle);
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchGo((new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 != null) {
               val var2: PokemonBattle = this.$battle;
               val var10001: MutableComponent = LocalizationUtilsKt.battleLang("missed");
               var2.broadcastChatMessage(TextKt.red(var10001) as Component);
               this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
            }
         }
      }) as () -> Unit);
   }
}
