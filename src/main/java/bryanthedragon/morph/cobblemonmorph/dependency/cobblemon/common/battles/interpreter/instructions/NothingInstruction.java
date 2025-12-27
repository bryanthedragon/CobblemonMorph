package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class NothingInstruction : InterpreterInstruction {
   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchGo((new Function0<Unit>(battle) {
         {
            super(0);
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: PokemonBattle = this.$battle;
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("nothing");
            var10000.broadcastChatMessage(var10001 as Component);
         }
      }) as () -> Unit);
   }
}
