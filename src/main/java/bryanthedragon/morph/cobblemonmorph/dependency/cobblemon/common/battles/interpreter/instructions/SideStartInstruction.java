package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.Locale
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

public class SideStartInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchWaiting(
         2.0F,
         (
            new Function0<Unit>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  var var10000: java.lang.String = this.this$0.getMessage().argumentAt(0);
                  val side: BattleSide = if (var10000 != null && var10000.charAt(1) == '1') this.$battle.getSide1() else this.$battle.getSide2();
                  val var15: Effect = this.this$0.getMessage().effectAt(1);
                  if (var15 != null) {
                     val effect: Effect = var15;

                     val bucket: java.lang.Iterable;
                     for (Object element$iv : bucket) {
                        val it: BattleSide = `element$iv` as BattleSide;
                        val subject: MutableComponent = if (`element$iv` as BattleSide == side)
                           LocalizationUtilsKt.battleLang("side_subject.ally")
                           else
                           LocalizationUtilsKt.battleLang("side_subject.opponent");
                        var10000 = "sidestart.${effect.getId()}";
                        val var10: Array<Any> = new Object[1];
                        var10[0] = subject;
                        val lang: MutableComponent = LocalizationUtilsKt.battleLang(var10000, var10);
                        it.broadcastChatMessage(lang as Component);
                     }

                     label62: {
                        label61: {
                           label60: {
                              var10000 = StringsKt.substringAfterLast$default(effect.getRawData(), " ", null, 2, null).toLowerCase(Locale.ROOT);
                              switch (var10000.hashCode()) {
                                 case -907689876:
                                    if (var10000.equals("screen")) {
                                       break label61;
                                    }
                                    break;
                                 case -895946451:
                                    if (var10000.equals("spikes")) {
                                       break label60;
                                    }
                                    break;
                                 case -694469544:
                                    if (var10000.equals("tailwind")) {
                                       var18 = BattleContext.Type.TAILWIND;
                                       break label62;
                                    }
                                    break;
                                 case 117588:
                                    if (var10000.equals("web")) {
                                       break label60;
                                    }
                                    break;
                                 case 3506021:
                                    if (var10000.equals("rock")) {
                                       break label60;
                                    }
                                    break;
                                 case 3615762:
                                    if (var10000.equals("veil")) {
                                       break label61;
                                    }
                                    break;
                                 case 1085265597:
                                    if (var10000.equals("reflect")) {
                                       break label61;
                                    }
                                 default:
                              }

                              var18 = BattleContext.Type.MISC;
                              break label62;
                           }

                           var18 = BattleContext.Type.HAZARD;
                           break label62;
                        }

                        var18 = BattleContext.Type.SCREEN;
                     }

                     side.getContextManager().add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), var18, this.$battle));
                  }
               }
            }
         ) as () -> Unit
      );
   }
}
