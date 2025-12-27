package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nItemInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/ItemInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"])
public class ItemInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val source: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.message, battle, null, 2, null);
      if (source != null) {
         ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, BattleMessage.effect$default(this.message, null, 1, null), source);
      }

      battle.dispatchGo(
         (
            new Function0<Unit>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 != null) {
                     var10000.getHeldItemManager().handleStartInstruction(var10000, this.$battle, this.this$0.getMessage());
                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                     var10000.getContextManager()
                        .add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.ITEM, this.$battle));
                  }
               }
            }
         ) as () -> Unit
      );
   }
}
