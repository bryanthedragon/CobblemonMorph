package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public class StartInstruction(message: BattleMessage) : InterpreterInstruction {
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
                  val var10000: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                  if (var10000 == null) {
                     return DispatchResultKt.getGO();
                  } else {
                     val var14: Effect = this.this$0.getMessage().effectAt(1);
                     if (var14 != null) {
                        val var15: java.lang.String = var14.getId();
                        if (var15 != null) {
                           var optionalEffect: Effect;
                           var optionalPokemonName: MutableComponent;
                           label97: {
                              optionalEffect = BattleMessage.effect$default(this.this$0.getMessage(), null, 1, null);
                              val optionalPokemon: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(
                                 this.this$0.getMessage(), this.$battle, null, 2, null
                              );
                              optionalPokemonName = if (optionalPokemon != null) optionalPokemon.getName() else null;
                              val var16: Effect = this.this$0.getMessage().effectAt(2);
                              if (var16 != null) {
                                 var17 = var16.getTypelessData();
                                 if (var17 != null) {
                                    break label97;
                                 }
                              }

                              var17 = Component.m_237113_("UNKOWN");
                           }

                           if (!StringsKt.contains$default(var15, "perish", false, 2, null)) {
                              var10000.getContextManager()
                                 .add(ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.VOLATILE, this.$battle));
                           }

                           this.$battle.getMinorBattleActions().put(var10000.getUuid(), this.this$0.getMessage());
                           if (!this.this$0.getMessage().hasOptionalArgument("silent")) {
                              var var23: MutableComponent;
                              if ((if (optionalEffect != null) optionalEffect.getId() else null) == "reflecttype" && optionalPokemonName != null) {
                                 var23 = LocalizationUtilsKt.battleLang("start.reflecttype", var10000.getName(), optionalPokemonName);
                              } else {
                                 label113: {
                                    label83: {
                                       switch (var15.hashCode()) {
                                          case -793000954:
                                             if (var15.equals("confusion")) {
                                                return DispatchResultKt.getGO();
                                             }
                                             break;
                                          case -678735345:
                                             if (var15.equals("perish0")) {
                                                break label83;
                                             }
                                             break;
                                          case -678735344:
                                             if (var15.equals("perish1")) {
                                                break label83;
                                             }
                                             break;
                                          case -678735343:
                                             if (var15.equals("perish2")) {
                                                break label83;
                                             }
                                             break;
                                          case -678735342:
                                             if (var15.equals("perish3")) {
                                                return DispatchResultKt.getGO();
                                             }
                                             break;
                                          case 95027346:
                                             if (var15.equals("curse")) {
                                                val var9: Array<Any> = new Object[2];
                                                val var10003: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(
                                                   this.this$0.getMessage(), this.$battle, null, 2, null
                                                );
                                                var9[0] = var10003.getName();
                                                var9[1] = var10000.getName();
                                                var23 = LocalizationUtilsKt.battleLang("start.curse", var9);
                                                break label113;
                                             }
                                             break;
                                          case 681421801:
                                             if (var15.equals("stockpile1")) {
                                                break label83;
                                             }
                                             break;
                                          case 681421802:
                                             if (var15.equals("stockpile2")) {
                                                break label83;
                                             }
                                             break;
                                          case 681421803:
                                             if (var15.equals("stockpile3")) {
                                                break label83;
                                             }
                                             break;
                                          case 2124767068:
                                             if (var15.equals("dynamax")) {
                                                label69: {
                                                   val var19: Effect = this.this$0.getMessage().effectAt(2);
                                                   if (var19 != null) {
                                                      var20 = var19.getId();
                                                      if (var20 != null) {
                                                         break label69;
                                                      }
                                                   }

                                                   var20 = var15;
                                                }

                                                val var22: MutableComponent = LocalizationUtilsKt.battleLang("start.$var20", var10000.getName());
                                                var23 = TextKt.yellow(var22);
                                                break label113;
                                             }
                                          default:
                                       }

                                       val var25: java.lang.String = "start.$var15";
                                       val var13: Array<Any> = new Object[]{var10000.getName(), null};
                                       var13[1] = var17;
                                       var23 = LocalizationUtilsKt.battleLang(var25, var13);
                                       break label113;
                                    }

                                    var23 = LocalizationUtilsKt.battleLang(
                                       "start.${StringsKt.dropLast(var15, 1)}", var10000.getName(), CharsKt.digitToInt(StringsKt.last(var15))
                                    );
                                 }
                              }

                              val var26: PokemonBattle = this.$battle;
                              var26.broadcastChatMessage(var23 as Component);
                           }

                           return new WaitDispatch(1.0F);
                        }
                     }

                     return DispatchResultKt.getGO();
                  }
               }
            }
         ) as () -> DispatchResult
      );
   }
}
