package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BasicContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.Arrays
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class InvertBoostInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      PokemonBattle.dispatchWaiting$default(
         battle,
         0.0F,
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
                     val name: MutableComponent = var10000.getName();
                     val var31: PokemonBattle = this.$battle;
                     val var10001: MutableComponent = LocalizationUtilsKt.battleLang("invertboost", name);
                     var31.broadcastChatMessage(var10001 as Component);
                     val context: BattleContext = ShowdownInterpreter.INSTANCE
                        .getContextFromAction(this.this$0.getMessage(), BattleContext.Type.BOOST, this.$battle);
                     val var32: java.util.Collection = var10000.getContextManager().get(BattleContext.Type.BOOST);
                     val var33: Array<BasicContext>;
                     if (var32 != null) {
                        val it: java.lang.Iterable = var32;
                        val `thisCollection$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var32, 10));

                        for (Object item$iv$iv : $this$map$iv) {
                           `thisCollection$iv`.add(
                              new BasicContext((`item$iv$iv` as BattleContext).getId(), context.getTurn(), BattleContext.Type.UNBOOST, context.getOrigin())
                           );
                        }

                        var33 = (`thisCollection$iv` as java.util.List).toArray(new BasicContext[0]);
                     } else {
                        var33 = null;
                     }

                     val var34: java.util.Collection = var10000.getContextManager().get(BattleContext.Type.UNBOOST);
                     val var35: Array<BasicContext>;
                     if (var34 != null) {
                        val var21: java.lang.Iterable = var34;
                        val var26: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var34, 10));

                        for (Object item$iv$iv : $this$map$iv) {
                           var26.add(new BasicContext((var29 as BattleContext).getId(), context.getTurn(), BattleContext.Type.BOOST, context.getOrigin()));
                        }

                        var35 = (var26 as java.util.List).toArray(new BasicContext[0]);
                     } else {
                        var35 = null;
                     }

                     var10000.getContextManager().clear(BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                     if (var35 != null) {
                        var10000.getContextManager().add(Arrays.copyOf(var35, var35.length));
                     }

                     if (var33 != null) {
                        var10000.getContextManager().add(Arrays.copyOf(var33, var33.length));
                     }

                     this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                  }
               }
            }
         ) as Function0,
         1,
         null
      );
   }
}
