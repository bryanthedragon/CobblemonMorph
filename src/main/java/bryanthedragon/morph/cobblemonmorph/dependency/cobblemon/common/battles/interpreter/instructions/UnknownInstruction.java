package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component

public class UnknownInstruction(battleMessage: BattleMessage) : InterpreterInstruction {
   public final val battleMessage: BattleMessage

   init {
      this.battleMessage = battleMessage;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchGo((new Function0<Unit>(battle, this) {
         {
            super(0);
            this.$battle = `$battle`;
            this.this$0 = `$receiver`;
         }

         public final void invoke() {
            this.$battle.broadcastChatMessage(TextKt.red(this.this$0.getBattleMessage().getRawMessage()) as Component);
         }
      }) as () -> Unit);
   }
}
