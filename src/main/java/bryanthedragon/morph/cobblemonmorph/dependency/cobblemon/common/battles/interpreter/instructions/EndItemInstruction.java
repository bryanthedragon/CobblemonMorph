package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import kotlin.jvm.functions.Function0

public class EndItemInstruction(message: BattleMessage) : InterpreterInstruction {
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
            val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
            if (var10000 != null) {
               val var3: Effect = this.this$0.getMessage().effectAt(1);
               if (var3 != null) {
                  var10000.getHeldItemManager().handleEndInstruction(var10000, this.$battle, this.this$0.getMessage());
                  this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                  var10000.getContextManager().remove(var3.getId(), BattleContext.Type.ITEM);
                  if (this.this$0.getMessage().hasOptionalArgument("eat")) {
                     val var4: PokemonEntity = var10000.getEntity();
                     if (var4 != null) {
                        var4.m_5496_(CobblemonSounds.BERRY_EAT, 1.0F, 1.0F);
                     }
                  }
               }
            }
         }
      }) as () -> Unit);
   }
}
