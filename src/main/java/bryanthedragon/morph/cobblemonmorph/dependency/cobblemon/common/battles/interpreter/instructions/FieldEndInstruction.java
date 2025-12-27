package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.Locale
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class FieldEndInstruction(message: BattleMessage) : InterpreterInstruction {
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
            val var10000: Effect = this.this$0.getMessage().effectAt(0);
            if (var10000 != null) {
               val lang: MutableComponent = LocalizationUtilsKt.battleLang("fieldend.${var10000.getId()}");
               val var4: PokemonBattle = this.$battle;
               var4.broadcastChatMessage(lang as Component);
               val var5: java.lang.String = StringsKt.substringAfterLast$default(var10000.getRawData(), " ", null, 2, null).toUpperCase(Locale.ROOT);
               this.$battle.getContextManager().remove(var10000.getId(), BattleContext.Type.valueOf(var5));
            }
         }
      }) as () -> Unit);
   }
}
