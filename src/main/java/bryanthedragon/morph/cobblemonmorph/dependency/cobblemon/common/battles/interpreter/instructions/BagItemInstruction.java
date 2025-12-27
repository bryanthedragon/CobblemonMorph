package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class BagItemInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchGo((new Function0<Unit>(this, battle) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
         }

         public final void invoke() {
            val var10000: BattlePokemon = this.this$0.getMessage().pokemonByUuid(0, this.$battle);
            val var6: java.lang.String = this.this$0.getMessage().argumentAt(1);
            val ownerName: MutableComponent = var10000.getActor().getName();
            val itemName: MutableComponent = MiscUtilsKt.asTranslated(var6);
            val var7: PokemonBattle = this.$battle;
            val var5: Array<Any> = new Object[]{ownerName, null, null};
            var5[1] = itemName;
            var5[2] = var10000.getName();
            val var10001: MutableComponent = LocalizationUtilsKt.battleLang("bagitem.use", var5);
            var7.broadcastChatMessage(var10001 as Component);
         }
      }) as () -> Unit);
   }
}
