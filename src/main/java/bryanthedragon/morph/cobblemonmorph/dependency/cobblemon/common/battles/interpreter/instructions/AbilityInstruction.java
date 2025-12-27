package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.UUID
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public class AbilityInstruction(instructionSet: InstructionSet, message: BattleMessage) : InterpreterInstruction, CauserInstruction {
   public final val instructionSet: InstructionSet
   public final val message: BattleMessage

   init {
      this.instructionSet = instructionSet;
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val var6: Effect = this.message.effectAt(1);
         if (var6 != null) {
            val optionalEffect: Effect = BattleMessage.effect$default(this.message, null, 1, null);
            val optionalPokemon: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.message, battle, null, 2, null);
            var var10002: Effect = optionalEffect;
            if (optionalEffect == null) {
               var10002 = var6;
            }

            ShowdownInterpreter.INSTANCE.broadcastAbility(battle, var10002, var10000);
            battle.dispatch(
               (
                  new Function0<DispatchResult>(var10000, optionalPokemon, battle, this, optionalEffect, var6) {
                     {
                        super(0);
                        this.$pokemon = `$pokemon`;
                        this.$optionalPokemon = `$optionalPokemon`;
                        this.$battle = `$battle`;
                        this.this$0 = `$receiver`;
                        this.$optionalEffect = `$optionalEffect`;
                        this.$effect = `$effect`;
                     }

                     @NotNull
                     public final DispatchResult invoke() {
                        var var17: MutableComponent;
                        label69: {
                           var optionalPokemonName: MutableComponent;
                           label73: {
                              val pokemonName: MutableComponent = this.$pokemon.getName();
                              optionalPokemonName = if (this.$optionalPokemon != null) this.$optionalPokemon.getName() else null;
                              val lang: java.util.Map = ShowdownInterpreter.INSTANCE.getLastCauser();
                              val var10000: UUID = this.$battle.getBattleId();
                              lang.put(var10000, this.this$0.getMessage());
                              val var11: java.lang.String = if (this.$optionalEffect != null) this.$optionalEffect.getId() else null;
                              if (var11 != null) {
                                 switch (var11.hashCode()) {
                                    case -857043995:
                                       if (var11.equals("powerofalchemy")) {
                                          break label73;
                                       }
                                       break;
                                    case -808719889:
                                       if (var11.equals("receiver")) {
                                          break label73;
                                       }
                                       break;
                                    case 110620997:
                                       if (var11.equals("trace")) {
                                          var17 = if (optionalPokemonName != null)
                                             LocalizationUtilsKt.battleLang("ability.trace", pokemonName, optionalPokemonName, this.$effect.getTypelessData())
                                             else
                                             null;
                                          break label69;
                                       }
                                    default:
                                 }
                              }

                              label58: {
                                 label57: {
                                    val var12: java.lang.String = this.$effect.getId();
                                    switch (var12.hashCode()) {
                                       case -991786635:
                                          if (var12.equals("airlock")) {
                                             break label57;
                                          }
                                          break;
                                       case -891888173:
                                          if (var12.equals("sturdy")) {
                                             break label58;
                                          }
                                          break;
                                       case -425372569:
                                          if (var12.equals("cloudnine")) {
                                             break label57;
                                          }
                                          break;
                                       case -282335599:
                                          if (var12.equals("unnerve")) {
                                             break label58;
                                          }
                                          break;
                                       case 152824269:
                                          if (var12.equals("anticipation")) {
                                             break label58;
                                          }
                                       default:
                                    }

                                    var17 = null;
                                    break label69;
                                 }

                                 var17 = LocalizationUtilsKt.battleLang("ability.airlock");
                                 break label69;
                              }

                              var17 = LocalizationUtilsKt.battleLang("ability.${this.$effect.getId()}", pokemonName);
                              break label69;
                           }

                           var17 = if (optionalPokemonName != null)
                              LocalizationUtilsKt.battleLang("ability.receiver", optionalPokemonName, this.$effect.getTypelessData())
                              else
                              null;
                        }

                        this.$battle.getMinorBattleActions().put(this.$pokemon.getUuid(), this.this$0.getMessage());
                        if (var17 != null) {
                           this.$battle.broadcastChatMessage(var17 as Component);
                           return new WaitDispatch(1.0F);
                        } else {
                           return DispatchResultKt.getGO();
                        }
                     }
                  }
               ) as () -> DispatchResult
            );
         }
      }
   }
}
