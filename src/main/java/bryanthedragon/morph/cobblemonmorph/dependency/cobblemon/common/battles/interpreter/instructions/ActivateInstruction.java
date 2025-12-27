package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.UUID
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

public class ActivateInstruction(instructionSet: InstructionSet, message: BattleMessage) : ActionEffectInstruction, CauserInstruction {
   public open var future: CompletableFuture<*>
   public open var holds: MutableSet<String>
   public open val id: ResourceLocation
   public final val instructionSet: InstructionSet
   public final val message: BattleMessage

   init {
      this.instructionSet = instructionSet;
      this.message = message;
      val var10001: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
      this.future = var10001;
      this.holds = new LinkedHashSet<>();
      this.id = MiscUtilsKt.cobblemonResource("activate");
   }

   public override fun preActionEffect(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.message.battlePokemon(0, battle);
      if (var10000 != null) {
         val var4: Effect = this.message.effectAt(1);
         if (var4 != null) {
            ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, var4, var10000);
            battle.dispatch((new Function0<DispatchResult>(battle, this, var10000) {
               {
                  super(0);
                  this.$battle = `$battle`;
                  this.this$0 = `$receiver`;
                  this.$pokemon = `$pokemon`;
               }

               @NotNull
               public final DispatchResult invoke() {
                  val var1: java.util.Map = ShowdownInterpreter.INSTANCE.getLastCauser();
                  val var10000: UUID = this.$battle.getBattleId();
                  var1.put(var10000, this.this$0.getMessage());
                  this.$battle.getMinorBattleActions().put(this.$pokemon.getUuid(), this.this$0.getMessage());
                  return DispatchResultKt.getGO();
               }
            }) as () -> DispatchResult);
         }
      }
   }

   public override fun runActionEffect(battle: PokemonBattle, runtime: MoLangRuntime) {
      battle.dispatch((new Function0<DispatchResult>(this, battle, runtime) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$battle = `$battle`;
            this.$runtime = `$runtime`;
         }

         @NotNull
         public final DispatchResult invoke() {
            val var10000: Effect = this.this$0.getMessage().effectAt(1);
            if (var10000 == null) {
               return DispatchResultKt.getGO();
            } else {
               val status: Status = Statuses.INSTANCE.getStatus(var10000.getId());
               if (status != null) {
                  val var13: ActionEffectTimeline = status.getActionEffect();
                  if (var13 != null) {
                     val providers: java.util.List = CollectionsKt.mutableListOf(new Object[]{this.$battle});
                     val var14: BattlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                     if (var14 == null) {
                        return DispatchResultKt.getGO();
                     }

                     val var15: PokemonEntity = var14.getEffectedPokemon().getEntity();
                     if (var15 != null) {
                        providers.add(new UsersProvider(var15));
                     }

                     val context: ActionEffectContext = new ActionEffectContext(var13, null, providers, this.$runtime, false, false, null, 114, null);
                     this.this$0.setFuture(var13.run(context));
                     this.this$0.setHolds(context.getHolds());
                     this.this$0.getFuture().thenApply(<unrepresentable>::invoke$lambda$1);
                     return DispatchResultKt.getGO();
                  }
               }

               return DispatchResultKt.getGO();
            }
         }

         private static final Unit invoke$lambda$1(ActivateInstruction this$0, Object it) {
            `this$0`.getHolds().clear();
            return Unit.INSTANCE;
         }
      }) as () -> DispatchResult);
   }

   public override fun postActionEffect(battle: PokemonBattle) {
      battle.dispatch((new Function0<DispatchResult>(this, battle) {
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
               label127: {
                  val var36: Effect = this.this$0.getMessage().effectAt(2);
                  if (var36 != null) {
                     var37 = var36.getTypelessData();
                     if (var37 != null) {
                        break label127;
                     }
                  }

                  var37 = Component.m_237113_("UNKNOWN");
               }

               val var38: Effect = this.this$0.getMessage().effectAt(1);
               if (var38 == null) {
                  return DispatchResultKt.getGO();
               } else {
                  var pokemonName: MutableComponent;
                  label121: {
                     pokemonName = var10000.getName();
                     var10000 = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                     if (var10000 != null) {
                        var40 = var10000.getName();
                        if (var40 != null) {
                           break label121;
                        }
                     }

                     var40 = Component.m_237113_("UNKNOWN");
                  }

                  label115: {
                     label114: {
                        label113: {
                           label112: {
                              label111: {
                                 val var7: java.lang.String = var38.getId();
                                 switch (var7.hashCode()) {
                                    case -2020594310:
                                       if (var7.equals("eeriespell")) {
                                          break label114;
                                       }
                                       break;
                                    case -2016783856:
                                       if (var7.equals("magnitude")) {
                                          var var28: Array<Any>;
                                          var var47: Int;
                                          label87: {
                                             var28 = new Object[1];
                                             val var10003: java.lang.String = this.this$0.getMessage().argumentAt(2);
                                             if (var10003 != null) {
                                                val var46: Int = StringsKt.toIntOrNull(var10003);
                                                if (var46 != null) {
                                                   var47 = var46;
                                                   break label87;
                                                }
                                             }

                                             var47 = 1;
                                          }

                                          var28[0] = var47;
                                          var41 = LocalizationUtilsKt.battleLang("activate.magnitude", var28);
                                          break label115;
                                       }
                                       break;
                                    case -1557412405:
                                       if (var7.equals("shadowforce")) {
                                          break label111;
                                       }
                                       break;
                                    case -1538027662:
                                       if (var7.equals("toxicdebris")) {
                                          return DispatchResultKt.getGO();
                                       }
                                       break;
                                    case -1408213035:
                                       if (var7.equals("destinybond")) {
                                          var var24: java.lang.Iterable = this.$battle.getActivePokemon();
                                          val `$i$f$forEach`: java.util.Collection = new ArrayList();

                                          for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                                             var10000 = (`element$iv$iv$iv` as ActiveBattlePokemon).getBattlePokemon();
                                             val var43: UUID = if (var10000 != null) var10000.getUuid() else null;
                                             if (var43 != null) {
                                                `$i$f$forEach`.add(var43);
                                             }
                                          }

                                          var24 = `$i$f$forEach` as java.util.List;
                                          val var31: PokemonBattle = this.$battle;
                                          val `$this$mapNotNullTo$iv$iv`: ActivateInstruction = this.this$0;

                                          for (Object element$iv : $this$mapNotNull$iv) {
                                             var31.getMinorBattleActions().put(`element$iv` as UUID, `$this$mapNotNullTo$iv$iv`.getMessage());
                                          }

                                          var41 = LocalizationUtilsKt.battleLang("activate.destinybond", pokemonName);
                                          break label115;
                                       }
                                       break;
                                    case -641743567:
                                       if (var7.equals("shedskin")) {
                                          return DispatchResultKt.getGO();
                                       }
                                       break;
                                    case -309012785:
                                       if (var7.equals("protect")) {
                                          break label112;
                                       }
                                       break;
                                    case 109646109:
                                       if (var7.equals("spite")) {
                                          break label114;
                                       }
                                       break;
                                    case 415512513:
                                       if (var7.equals("maxguard")) {
                                          break label112;
                                       }
                                       break;
                                    case 702307440:
                                       if (var7.equals("hyperspacefury")) {
                                          break label111;
                                       }
                                       break;
                                    case 702361050:
                                       if (var7.equals("hyperspacehole")) {
                                          break label111;
                                       }
                                       break;
                                    case 1629040397:
                                       if (var7.equals("focusband")) {
                                          break label113;
                                       }
                                       break;
                                    case 1629547003:
                                       if (var7.equals("focussash")) {
                                          break label113;
                                       }
                                    default:
                                 }

                                 val var44: java.lang.String = "activate.${var38.getId()}";
                                 val var29: Array<Any> = new Object[]{pokemonName, null, null};
                                 var29[1] = var40;
                                 var29[2] = var37;
                                 var41 = LocalizationUtilsKt.battleLang(var44, var29);
                                 break label115;
                              }

                              var41 = LocalizationUtilsKt.battleLang("activate.phantomforce", pokemonName);
                              break label115;
                           }

                           var41 = LocalizationUtilsKt.battleLang("activate.protect", pokemonName);
                           break label115;
                        }

                        var41 = LocalizationUtilsKt.battleLang("activate.focusband", pokemonName, var38.getTypelessData());
                        break label115;
                     }

                     val var30: Array<Any> = new Object[]{pokemonName, null, null};
                     var30[1] = var37;
                     val var48: java.lang.String = this.this$0.getMessage().argumentAt(3);
                     var30[2] = var48;
                     var41 = LocalizationUtilsKt.battleLang("activate.spite", var30);
                  }

                  val var45: PokemonBattle = this.$battle;
                  var45.broadcastChatMessage(var41 as Component);
                  return new UntilDispatch((new Function0<java.lang.Boolean>(this.this$0) {
                     {
                        super(0);
                        this.this$0 = `$receiver`;
                     }

                     @NotNull
                     public final java.lang.Boolean invoke() {
                        return !this.this$0.getHolds().contains("effects");
                     }
                  }) as () -> java.lang.Boolean);
               }
            }
         }
      }) as () -> DispatchResult);
   }

   override fun invoke(battle: PokemonBattle) {
      ActionEffectInstruction.DefaultImpls.invoke(this, battle);
   }

   override fun addMolangQueries(runtime: MoLangRuntime) {
      ActionEffectInstruction.DefaultImpls.addMolangQueries(this, runtime);
   }
}
