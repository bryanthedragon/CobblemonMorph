package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import kotlin.jvm.functions.Function0
import org.jetbrains.annotations.NotNull

public class PpUpdateInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatch(
         (
            new Function0<DispatchResult>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               @NotNull
               public final DispatchResult invoke() {
                  var var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 == null) {
                     return DispatchResultKt.getGO();
                  } else {
                     val pokemon: BattlePokemon = var10000;
                     val var21: java.lang.String = this.this$0.getMessage().argumentAt(1);
                     if (var21 != null && StringsKt.split$default(var21, new java.lang.String[]{", "}, false, 0, 6, null) != null) {
                        val `$this$forEach$iv`: java.lang.Iterable;
                        for (Object element$iv : $this$forEach$iv) {
                           val moveIdAndPp: java.util.List = StringsKt.split$default(
                              `element$iv` as java.lang.String, new java.lang.String[]{": "}, false, 0, 6, null
                           );
                           val var20: java.lang.String = moveIdAndPp.get(0) as java.lang.String;
                           val movePp: java.lang.String = moveIdAndPp.get(1) as java.lang.String;
                           val var14: java.util.Iterator = pokemon.getEffectedPokemon().getMoveSet().iterator();

                           while (true) {
                              if (!var14.hasNext()) {
                                 var10000 = null;
                                 break;
                              }

                              val `element$ivx`: Any = var14.next();
                              if (StringsKt.equals((`element$ivx` as Move).getName(), var20, true)) {
                                 var10000 = (BattlePokemon)`element$ivx`;
                                 break;
                              }
                           }

                           val var26: Move = var10000 as Move;
                           if (var10000 as Move == null) {
                              return DispatchResultKt.getGO();
                           }

                           var26.setCurrentPp(Integer.parseInt(movePp));
                        }

                        return DispatchResultKt.getGO();
                     } else {
                        return DispatchResultKt.getGO();
                     }
                  }
               }
            }
         ) as () -> DispatchResult
      );
   }
}
