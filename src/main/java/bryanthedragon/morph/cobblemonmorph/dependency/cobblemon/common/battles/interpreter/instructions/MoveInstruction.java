package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.TargetsProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.UUID
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

public class MoveInstruction(instructionSet: InstructionSet, message: BattleMessage) : InterpreterInstruction, CauserInstruction {
   public final val actionEffect: ActionEffectTimeline?
   public final val effect: Effect
   public final var future: CompletableFuture<Unit>
   public final var holds: MutableSet<String>
   public final val instructionSet: InstructionSet
   public final val message: BattleMessage
   public final val move: MoveTemplate
   public final var targetPokemon: BattlePokemon?
   public final lateinit var userPokemon: BattlePokemon

   init {
      this.instructionSet = instructionSet;
      this.message = message;
      var var10001: Effect = this.message.effectAt(1);
      if (var10001 == null) {
         var10001 = Effect.Companion.pure("", "");
      }

      this.effect = var10001;
      this.move = Moves.INSTANCE.getByNameOrDummy(this.effect.getId());
      this.actionEffect = this.move.getActionEffect();
      this.future = CompletableFuture.completedFuture(Unit.INSTANCE);
      this.holds = new LinkedHashSet<>();
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10001: BattlePokemon = this.message.battlePokemon(0, battle);
      this.setUserPokemon(var10001);
      this.targetPokemon = this.message.battlePokemon(2, battle);
      val targetPokemon: BattlePokemon = this.targetPokemon;
      val optionalEffect: Effect = BattleMessage.effect$default(this.message, null, 1, null);
      ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, optionalEffect, this.getUserPokemon());
      battle.dispatch(
         (
            new Function0<DispatchResult>(this) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final DispatchResult invoke() {
                  return new UntilDispatch(
                     (
                        new Function0<java.lang.Boolean>(this.this$0) {
                           {
                              super(0);
                              this.this$0 = `$receiver`;
                           }

                           @NotNull
                           public final java.lang.Boolean invoke() {
                              val `$this$iv`: InstructionSet = this.this$0.getInstructionSet();
                              val `comparedTo$iv`: InterpreterInstruction = this.this$0;
                              val `$this$lastOrNull$iv$iv`: java.lang.Iterable = `$this$iv`.getInstructions()
                                 .subList(0, `$this$iv`.getInstructions().indexOf(`comparedTo$iv`));
                              val `element$iv$iv`: java.util.Collection = new ArrayList();

                              for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
                                 if (`element$iv$iv$iv` is MoveInstruction) {
                                    `element$iv$iv`.add(`element$iv$iv$iv`);
                                 }
                              }

                              val `iterator$iv$iv`: java.util.ListIterator = (`element$iv$iv` as java.util.List)
                                 .listIterator((`element$iv$iv` as java.util.List).size());

                              var var10000: Any;
                              while (true) {
                                 if (`iterator$iv$iv`.hasPrevious()) {
                                    val var16: Any = `iterator$iv$iv`.previous();
                                    if (false) {
                                       continue;
                                    }

                                    var10000 = (MoveInstruction)var16;
                                    break;
                                 }

                                 var10000 = null;
                                 break;
                              }

                              var10000 = var10000;
                              if (var10000 != null) {
                                 val var18: CompletableFuture = var10000.getFuture();
                                 if (var18 != null) {
                                    return var18.isDone();
                                 }
                              }

                              return true;
                           }
                        }
                     ) as () -> java.lang.Boolean
                  );
               }
            }
         ) as () -> DispatchResult
      );
      battle.dispatch(
         (
            new Function0<DispatchResult>(this, battle, optionalEffect, targetPokemon) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
                  this.$optionalEffect = `$optionalEffect`;
                  this.$targetPokemon = `$targetPokemon`;
               }

               @NotNull
               public final DispatchResult invoke() {
                  val pokemonName: MutableComponent = this.this$0.getUserPokemon().getName();
                  val lang: java.util.Map = ShowdownInterpreter.INSTANCE.getLastCauser();
                  val var10000: UUID = this.$battle.getBattleId();
                  lang.put(var10000, this.this$0.getMessage());
                  val var24: Pokemon = this.this$0.getUserPokemon().getEffectedPokemon();
                  val var26: MoveInstruction = this.this$0;
                  if (UseMoveEvolutionProgress.Companion.supports(var24, this.this$0.getMove())) {
                     val subsequentInstructions: UseMoveEvolutionProgress = var24.getEvolutionProxy()
                        .current()
                        .progressFirstOrCreate((new Function1<EvolutionProgress<?>, java.lang.Boolean>(var26) {
                           {
                              super(1);
                              this.this$0 = `$receiver`;
                           }

                           @NotNull
                           public final java.lang.Boolean invoke(@NotNull EvolutionProgress<?> it) {
                              return it is UseMoveEvolutionProgress && (it as UseMoveEvolutionProgress).currentProgress().getMove() == this.this$0.getMove();
                           }
                        }) as (EvolutionProgress<?>?) -> java.lang.Boolean, <unrepresentable>.INSTANCE);
                     subsequentInstructions.updateProgress(
                        new UseMoveEvolutionProgress.Progress(var26.getMove(), subsequentInstructions.currentProgress().getAmount() + 1)
                     );
                  }

                  val var70: MutableComponent = if ((if (this.$optionalEffect != null) this.$optionalEffect.getId() else null) == "magicbounce")
                     LocalizationUtilsKt.battleLang("ability.magicbounce", pokemonName, this.this$0.getMove().getDisplayName())
                     else
                     (
                        if (!(this.this$0.getMove().getName() == "struggle")
                              && this.$targetPokemon != null
                              && !(this.$targetPokemon == this.this$0.getUserPokemon()))
                           LocalizationUtilsKt.battleLang("used_move_on", pokemonName, this.this$0.getMove().getDisplayName(), this.$targetPokemon.getName())
                           else
                           LocalizationUtilsKt.battleLang("used_move", pokemonName, this.this$0.getMove().getDisplayName())
                     );
                  val var71: PokemonBattle = this.$battle;
                  var71.broadcastChatMessage(var70 as Component);
                  this.$battle.getMajorBattleActions().put(this.this$0.getUserPokemon().getUuid(), this.this$0.getMessage());
                  val var30: java.util.List = CollectionsKt.mutableListOf(new Object[]{this.$battle});
                  val var72: PokemonEntity = this.this$0.getUserPokemon().getEffectedPokemon().getEntity();
                  if (var72 != null) {
                     var30.add(new UsersProvider(var72));
                  }

                  if (this.$targetPokemon != null) {
                     val var73: Pokemon = this.$targetPokemon.getEffectedPokemon();
                     if (var73 != null) {
                        val var74: PokemonEntity = var73.getEntity();
                        if (var74 != null) {
                           var30.add(new TargetsProvider(var74));
                        }
                     }
                  }

                  val var33: MoLangRuntime = new MoLangRuntime();
                  val var35: PokemonBattle = this.$battle;
                  val var75: MoLangFunctions = MoLangFunctions.INSTANCE;
                  val var10002: MoLangFunctions = MoLangFunctions.INSTANCE;
                  val var10003: MoLangEnvironment = var33.getEnvironment();
                  var75.addStandardFunctions(var35.addQueryFunctions(MoLangFunctions.getQueryStruct$default(var10002, var10003, null, 1, null)));
                  if (this.this$0.getActionEffect() == null) {
                     return DispatchResultKt.getGO();
                  } else {
                     val var34: ActionEffectContext = new ActionEffectContext(this.this$0.getActionEffect(), null, var30, var33, false, false, null, 114, null);
                     val var37: java.util.List = this.this$0.getInstructionSet().findInstructionsCausedBy(this.this$0);
                     var var42: java.lang.Iterable = var37;
                     var var54: java.util.Collection = new ArrayList();

                     for (Object element$iv$iv : $this$filterIsInstance$iv) {
                        if (`$this$forEach$iv$iv$iv` is MissInstruction) {
                           var54.add(`$this$forEach$iv$iv$iv`);
                        }
                     }

                     var42 = var54 as java.util.List;
                     var54 = new ArrayList();

                     for (Object element$iv$iv$iv : $this$filterIsInstance$iv) {
                        val var76: BattlePokemon = (`element$iv$iv$iv` as MissInstruction).getTarget();
                        if (var76 != null) {
                           var54.add(var76);
                        }
                     }

                     val var39: java.util.List = var54 as java.util.List;
                     val var77: MoLangFunctions = MoLangFunctions.INSTANCE;
                     var var10001: MoLangEnvironment = var33.getEnvironment();
                     MoLangFunctions.getQueryStruct$default(var77, var10001, null, 1, null).addFunction("missed", <unrepresentable>::invoke$lambda$7);
                     var `$this$filterIsInstance$ivx`: java.lang.Iterable = var37;
                     var `destination$iv$ivx`: java.util.Collection = new ArrayList();

                     for (Object element$iv$ivx : $this$filterIsInstance$ivx) {
                        if (`element$iv$ivx` is DamageInstruction) {
                           `destination$iv$ivx`.add(`element$iv$ivx`);
                        }
                     }

                     `$this$filterIsInstance$ivx` = `destination$iv$ivx` as java.util.List;
                     `destination$iv$ivx` = new ArrayList();

                     for (Object element$iv$iv$ivx : $this$filterIsInstance$ivx) {
                        val var78: BattlePokemon = (`element$iv$iv$ivx` as DamageInstruction).getExpectedTarget();
                        if (var78 != null) {
                           `destination$iv$ivx`.add(var78);
                        }
                     }

                     val var44: java.util.List = `destination$iv$ivx` as java.util.List;
                     val var79: MoLangFunctions = MoLangFunctions.INSTANCE;
                     var10001 = var33.getEnvironment();
                     MoLangFunctions.getQueryStruct$default(var79, var10001, null, 1, null).addFunction("hurt", <unrepresentable>::invoke$lambda$10);
                     val var80: MoLangFunctions = MoLangFunctions.INSTANCE;
                     var10001 = var33.getEnvironment();
                     MoLangFunctions.getQueryStruct$default(var80, var10001, null, 1, null).addFunction("move", <unrepresentable>::invoke$lambda$11);
                     val var81: MoLangFunctions = MoLangFunctions.INSTANCE;
                     var10001 = var33.getEnvironment();
                     MoLangFunctions.getQueryStruct$default(var81, var10001, null, 1, null).addFunction("instruction_id", <unrepresentable>::invoke$lambda$12);
                     this.this$0.setFuture(this.this$0.getActionEffect().run(var34));
                     this.this$0.setHolds(var34.getHolds());
                     this.this$0.getFuture().thenApply(<unrepresentable>::invoke$lambda$13);
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

               private static final Object invoke$lambda$7(java.util.List $missedTargets, MoParams params) {
                  if (params.getParams().size() == 0) {
                     return new DoubleValue(!`$missedTargets`.isEmpty());
                  } else {
                     val entityUUID: java.lang.String = params.getString(0);
                     val `$this$any$iv`: java.lang.Iterable = `$missedTargets`;
                     var var10: Boolean;
                     if (`$missedTargets` is java.util.Collection && (`$missedTargets` as java.util.Collection).isEmpty()) {
                        var10 = false;
                     } else {
                        label46: {
                           for (Object element$iv : $this$any$iv) {
                              val var10000: PokemonEntity = (`element$iv` as BattlePokemon).getEntity();
                              if ((if (var10000 != null) var10000.m_20149_() else null) == entityUUID) {
                                 var10 = true;
                                 break label46;
                              }
                           }

                           var10 = false;
                        }
                     }

                     return new DoubleValue(var10);
                  }
               }

               private static final Object invoke$lambda$10(java.util.List $hurtTargets, MoParams params) {
                  if (params.getParams().size() == 0) {
                     return new DoubleValue(!`$hurtTargets`.isEmpty());
                  } else {
                     val entityUUID: java.lang.String = params.getString(0);
                     val `$this$any$iv`: java.lang.Iterable = `$hurtTargets`;
                     var var10: Boolean;
                     if (`$hurtTargets` is java.util.Collection && (`$hurtTargets` as java.util.Collection).isEmpty()) {
                        var10 = false;
                     } else {
                        label46: {
                           for (Object element$iv : $this$any$iv) {
                              val var10000: PokemonEntity = (`element$iv` as BattlePokemon).getEntity();
                              if ((if (var10000 != null) var10000.m_20149_() else null) == entityUUID) {
                                 var10 = true;
                                 break label46;
                              }
                           }

                           var10 = false;
                        }
                     }

                     return new DoubleValue(var10);
                  }
               }

               private static final Object invoke$lambda$11(MoveInstruction this$0, MoParams it) {
                  return `this$0`.getMove().getStruct();
               }

               private static final Object invoke$lambda$12(MoParams it) {
                  return new StringValue(MiscUtilsKt.cobblemonResource("move").toString());
               }

               private static final Unit invoke$lambda$13(Function1 $tmp0, Object p0) {
                  return `$tmp0`.invoke(p0) as Unit;
               }
            }
         ) as () -> DispatchResult
      );
   }
}
