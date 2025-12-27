package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import kotlin.jvm.functions.Function0
import org.jetbrains.annotations.NotNull

public class UpkeepInstruction : InterpreterInstruction {
   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatch((new Function0<DispatchResult>(battle) {
         {
            super(0);
            this.$battle = `$battle`;
         }

         @NotNull
         public final DispatchResult invoke() {
            val `$this$forEach$iv`: java.lang.Iterable;
            for (Object element$iv : $this$forEach$iv) {
               (`element$iv` as BattleActor).upkeep();
            }

            return DispatchResultKt.getGO();
         }
      }) as () -> DispatchResult);
   }
}
