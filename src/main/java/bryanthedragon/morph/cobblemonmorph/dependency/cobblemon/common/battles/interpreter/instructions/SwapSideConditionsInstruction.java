package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import java.util.ArrayList;
import kotlin.jvm.functions.Function0

public class SwapSideConditionsInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchGo(
         (
            new Function0<Unit>(battle) {
               {
                  super(0);
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  val sides: java.util.List = new ArrayList();

                  val `$this$forEach$iv`: java.lang.Iterable;
                  for (Object element$iv : $this$forEach$iv) {
                     val side: BattleSide = `element$iv` as BattleSide;
                     if (!sides.contains(`element$iv` as BattleSide)) {
                        side.getContextManager()
                           .swap(side.getOppositeSide().getContextManager(), BattleContext.Type.TAILWIND, BattleContext.Type.SCREEN, BattleContext.Type.HAZARD);
                     }

                     sides.add(side);
                  }
               }
            }
         ) as () -> Unit
      );
   }
}
