package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMadeInvalidChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class ErrorInstruction(battleActor: BattleActor, message: BattleMessage) : InterpreterInstruction {
   public final val battleActor: BattleActor
   public final val message: BattleMessage

   init {
      this.battleActor = battleActor;
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.log("Error Instruction");
      battle.dispatchGo((new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var2: java.lang.String = this.this$0.getMessage().getRawMessage();
            val var3: Component;
            if (var2 == "|error|[Unavailable choice] Can't switch: The active Pokémon is trapped") {
               val var10000: MutableComponent = LocalizationUtilsKt.battleLang("error.pokemon_is_trapped");
               var3 = TextKt.red(var10000) as Component;
            } else {
               if (var2 == "|error|[Invalid choice] Can't choose for Team Preview: You're not in a Team Preview phase") {
                  return;
               }

               var3 = this.$battle.createUnimplemented$common(this.this$0.getMessage());
            }

            this.this$0.getBattleActor().sendMessage(var3);
            this.this$0.getBattleActor().setMustChoose(true);
            this.this$0.getBattleActor().sendUpdate(new BattleMadeInvalidChoicePacket());
         }
      }) as () -> Unit);
   }
}
