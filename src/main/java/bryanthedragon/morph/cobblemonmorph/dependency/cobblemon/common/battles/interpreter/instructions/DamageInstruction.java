package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DamageTakenEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.LinkedHashSet
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.BooleanRef
import kotlin.jvm.internal.Ref.ObjectRef
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nDamageInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/DamageInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,215:1\n1#2:216\n*E\n"])
public class DamageInstruction(instructionSet: InstructionSet, actor: BattleActor, publicMessage: BattleMessage, privateMessage: BattleMessage) :
   ActionEffectInstruction {
   public final val actor: BattleActor
   public final val expectedTarget: BattlePokemon?
   public open var future: CompletableFuture<*>
   public open var holds: MutableSet<String>
   public open val id: ResourceLocation
   public final val instructionSet: InstructionSet
   public final val privateMessage: BattleMessage
   public final val publicMessage: BattleMessage

   init {
      this.instructionSet = instructionSet;
      this.actor = actor;
      this.publicMessage = publicMessage;
      this.privateMessage = privateMessage;
      this.expectedTarget = this.publicMessage.battlePokemon(0, this.actor.getBattle());
      val var10001: CompletableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
      this.future = var10001;
      this.holds = new LinkedHashSet<>();
      this.id = MiscUtilsKt.cobblemonResource("damage");
   }

   public override fun preActionEffect(battle: PokemonBattle) {
      val var10000: BattlePokemon = this.publicMessage.battlePokemon(0, this.actor.getBattle());
      if (var10000 != null) {
         val var11: java.lang.String = this.privateMessage.optionalArgument("from");
         val recoiling: Boolean = var11 != null && StringsKt.equals(var11, "recoil", true);
         val lastCauser: CauserInstruction = this.instructionSet.getMostRecentCauser(this);
         if (recoiling) {
            this.doRecoilEvoChecks(var10000);
            if (lastCauser is MoveInstruction) {
               battle.dispatch((new Function0<DispatchResult>(lastCauser) {
                  {
                     super(0);
                     this.$lastCauser = `$lastCauser`;
                  }

                  @NotNull
                  public final DispatchResult invoke() {
                     return new UntilDispatch((new Function0<java.lang.Boolean>(this.$lastCauser) {
                        {
                           super(0);
                           this.$lastCauser = `$lastCauser`;
                        }

                        @NotNull
                        public final java.lang.Boolean invoke() {
                           return !(this.$lastCauser as MoveInstruction).getHolds().contains("recoil");
                        }
                     }) as () -> java.lang.Boolean);
                  }
               }) as () -> DispatchResult);
            }
         }

         val var12: java.lang.String = this.privateMessage.argumentAt(1);
         if (var12 != null) {
            val var14: java.util.List = StringsKt.split$default(var12, new java.lang.String[]{" "}, false, 0, 6, null);
            if (var14 != null && var14.get(0) as java.lang.String != null) {
               val effect: Effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
               val source: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.privateMessage, battle, null, 2, null);
               if (source != null) {
                  ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, effect, source);
               }

               return;
            }
         }
      }
   }

   private fun doRecoilEvoChecks(battlePokemon: BattlePokemon) {
      val pokemon: Pokemon = battlePokemon.getEffectedPokemon();
      if (RecoilEvolutionProgress.Companion.supports(pokemon)) {
         var var10000: java.lang.String = this.privateMessage.argumentAt(1);
         if (var10000 == null) {
            throw new UnsupportedOperationException("Cant get recoil string");
         }

         val var8: MatchResult = Regex.find$default(new Regex("([0-9]+).*"), var10000, 0, 2, null);
         if (var8 == null) {
            throw new UnsupportedOperationException("Cant get recoil string");
         }

         val var9: MatchGroupCollection = var8.getGroups();
         if (var9 == null) {
            throw new UnsupportedOperationException("Cant get recoil string");
         }

         val var10: MatchGroup = var9.get(1);
         if (var10 == null) {
            throw new UnsupportedOperationException("Cant get recoil string");
         }

         var10000 = var10.getValue();
         if (var10000 == null) {
            throw new UnsupportedOperationException("Cant get recoil string");
         }

         val var12: Int = StringsKt.toIntOrNull(var10000);
         if (var12 == null) {
            throw new UnsupportedOperationException("Cant get recoil string");
         }

         val difference: Int = pokemon.getCurrentHealth() - var12;
         if (difference > 0) {
            val progress: RecoilEvolutionProgress = pokemon.getEvolutionProxy()
               .current()
               .progressFirstOrCreate(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
            progress.updateProgress(new RecoilEvolutionProgress.Progress(progress.currentProgress().getRecoil() + difference));
         }
      }
   }

   public override fun runActionEffect(battle: PokemonBattle, runtime: MoLangRuntime) {
      val effect: Effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
      val var10000: BattlePokemon = this.publicMessage.battlePokemon(0, this.actor.getBattle());
      if (var10000 != null) {
         var status: ObjectRef;
         var var10: Status;
         label15: {
            status = new ObjectRef();
            var9 = status;
            if (effect != null) {
               val var10001: java.lang.String = effect.getId();
               if (var10001 != null) {
                  var10 = Statuses.INSTANCE.getStatus(var10001);
                  var9 = status;
                  break label15;
               }
            }

            var10 = null;
         }

         var9.element = var10;
         battle.dispatch((new Function0<DispatchResult>(this, battle, status, var10000, runtime) {
            {
               super(0);
               this.this$0 = `$receiver`;
               this.$battle = `$battle`;
               this.$status = `$status`;
               this.$battlePokemon = `$battlePokemon`;
               this.$runtime = `$runtime`;
            }

            @NotNull
            public final DispatchResult invoke() {
               val var10000: BattlePokemon = this.this$0.getPrivateMessage().battlePokemon(0, this.$battle);
               if (var10000 == null) {
                  return DispatchResultKt.getGO();
               } else {
                  if (this.$status.element is PoisonStatus) {
                     var var16: Status;
                     label28: {
                        var11 = this.$status;
                        val var10001: PersistentStatusContainer = var10000.getEffectedPokemon().getStatus();
                        if (var10001 != null) {
                           val var15: PersistentStatus = var10001.getStatus();
                           if (var15 != null) {
                              var16 = var15;
                              break label28;
                           }
                        }

                        var16 = this.$status.element as Status;
                     }

                     var11.element = var16;
                  }

                  val var12: Status = this.$status.element as Status;
                  if (this.$status.element as Status != null) {
                     val var13: ActionEffectTimeline = var12.getActionEffect();
                     if (var13 != null) {
                        val providers: java.util.List = CollectionsKt.mutableListOf(new Object[]{this.$battle});
                        val var14: PokemonEntity = this.$battlePokemon.getEffectedPokemon().getEntity();
                        if (var14 != null) {
                           providers.add(new UsersProvider(var14));
                        }

                        val var8: ActionEffectContext = new ActionEffectContext(var13, null, providers, this.$runtime, false, false, null, 114, null);
                        this.this$0.setFuture(var13.run(var8));
                        this.this$0.setHolds(var8.getHolds());
                        this.this$0.getFuture().thenApply(<unrepresentable>::invoke$lambda$1);
                        return DispatchResultKt.getGO();
                     }
                  }

                  return DispatchResultKt.getGO();
               }
            }

            private static final Unit invoke$lambda$1(DamageInstruction this$0, Object it) {
               `this$0`.getHolds().clear();
               return Unit.INSTANCE;
            }
         }) as () -> DispatchResult);
      }
   }

   public override fun postActionEffect(battle: PokemonBattle) {
      var var10000: java.lang.String = this.privateMessage.argumentAt(1);
      if (var10000 != null) {
         val var10: java.util.List = StringsKt.split$default(var10000, new java.lang.String[]{" "}, false, 0, 6, null);
         if (var10 != null) {
            var10000 = var10.get(0) as java.lang.String;
            if (var10000 != null) {
               val var12: BattlePokemon = this.publicMessage.battlePokemon(0, this.actor.getBattle());
               if (var12 == null) {
                  return;
               }

               val battlePokemon: BattlePokemon = var12;
               val causedFaint: BooleanRef = new BooleanRef();
               causedFaint.element = var10000 == "0";
               val var8: Effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
               val source: BattlePokemon = BattleMessage.battlePokemonFromOptional$default(this.privateMessage, battle, null, 2, null);
               val lastCauser: CauserInstruction = this.instructionSet.getMostRecentCauser(this);
               battle.dispatch(
                  (
                     new Function0<DispatchResult>(battlePokemon, causedFaint, var10000, var8, source, battle, this, lastCauser) {
                        {
                           super(0);
                           this.$battlePokemon = `$battlePokemon`;
                           this.$causedFaint = `$causedFaint`;
                           this.$newHealth = `$newHealth`;
                           this.$effect = `$effect`;
                           this.$source = `$source`;
                           this.$battle = `$battle`;
                           this.this$0 = `$receiver`;
                           this.$lastCauser = `$lastCauser`;
                        }

                        @NotNull
                        public final DispatchResult invoke() {
                           val pokemonName: MutableComponent = this.$battlePokemon.getName();
                           val pokemonEntity: PokemonEntity = this.$battlePokemon.getEntity();
                           if (!this.$causedFaint.element && pokemonEntity != null) {
                              val newHealthRatio: PlayPoseableAnimationPacket = new PlayPoseableAnimationPacket(
                                 pokemonEntity.m_19879_(), SetsKt.setOf("recoil"), SetsKt.emptySet()
                              );
                              val remainingHealth: Double = pokemonEntity.m_20185_();
                              val difference: Double = pokemonEntity.m_20186_();
                              val var8: Double = pokemonEntity.m_20189_();
                              val var10: ResourceKey = pokemonEntity.m_9236_().m_46472_();
                              val var10000: NetworkPacket = newHealthRatio;
                              NetworkPacket.DefaultImpls.sendToPlayersAround$default(var10000, remainingHealth, difference, var8, 50.0, var10, null, 32, null);
                           }

                           if (pokemonEntity != null) {
                              val var12: RunPosableMoLangPacket = new RunPosableMoLangPacket(
                                 pokemonEntity.m_19879_(), SetsKt.setOf("q.particle('cobblemon:hit', 'target')")
                              );
                              val var15: Double = pokemonEntity.m_20185_();
                              val var19: Double = pokemonEntity.m_20186_();
                              val var30: Double = pokemonEntity.m_20189_();
                              val var33: ResourceKey = pokemonEntity.m_9236_().m_46472_();
                              val var35: NetworkPacket = var12;
                              NetworkPacket.DefaultImpls.sendToPlayersAround$default(var35, var15, var19, var30, 50.0, var33, null, 32, null);
                           }

                           val var16: Int = Integer.parseInt(
                              StringsKt.split$default(this.$newHealth, new java.lang.String[]{"/"}, false, 0, 6, null).get(0) as java.lang.String
                           );
                           if (this.$effect != null) {
                              var var37: MutableComponent;
                              label102: {
                                 label101: {
                                    label110: {
                                       label111: {
                                          val var20: java.lang.String = this.$effect.getId();
                                          switch (var20.hashCode()) {
                                             case -1177412584:
                                                if (var20.equals("stickybarb")) {
                                                   break label101;
                                                }
                                                break;
                                             case -1152368589:
                                                if (var20.equals("chloroblast")) {
                                                   break label110;
                                                }
                                                break;
                                             case -254646412:
                                                if (var20.equals("jumpkick")) {
                                                   var37 = LocalizationUtilsKt.battleLang("damage.highjumpkick", pokemonName);
                                                   break label102;
                                                }
                                                break;
                                             case 97822:
                                                if (var20.equals("brn")) {
                                                   break label111;
                                                }
                                                break;
                                             case 111307:
                                                if (var20.equals("psn")) {
                                                   break label111;
                                                }
                                                break;
                                             case 115037:
                                                if (var20.equals("tox")) {
                                                   break label111;
                                                }
                                                break;
                                             case 1019985636:
                                                if (var20.equals("aftermath")) {
                                                   var37 = LocalizationUtilsKt.battleLang("damage.generic", pokemonName);
                                                   break label102;
                                                }
                                                break;
                                             case 1041565690:
                                                if (var20.equals("steelbeam")) {
                                                   break label110;
                                                }
                                                break;
                                             case 1328235077:
                                                if (var20.equals("blacksludge")) {
                                                   break label101;
                                                }
                                             default:
                                          }

                                          var var27: Array<Any>;
                                          var var10003: MutableComponent;
                                          label85: {
                                             var42 = "damage.${this.$effect.getId()}";
                                             var27 = new Object[]{pokemonName, null};
                                             if (this.$source != null) {
                                                var10003 = this.$source.getName();
                                                if (var10003 != null) {
                                                   break label85;
                                                }
                                             }

                                             var10003 = Component.m_237113_("UNKOWN");
                                          }

                                          var27[1] = var10003;
                                          var37 = LocalizationUtilsKt.battleLang(var42, var27);
                                          break label102;
                                       }

                                       label80: {
                                          val var38: Status = Statuses.INSTANCE.getStatus(this.$effect.getId());
                                          if (var38 != null) {
                                             val var39: ResourceLocation = var38.getName();
                                             if (var39 != null) {
                                                var40 = var39.m_135815_();
                                                break label80;
                                             }
                                          }

                                          var40 = null;
                                       }

                                       if (var40 == null) {
                                          return DispatchResultKt.getGO();
                                       }

                                       var37 = LocalizationUtilsKt.lang("status.$var40.hurt", pokemonName);
                                       break label102;
                                    }

                                    var37 = LocalizationUtilsKt.battleLang("damage.mindblown", pokemonName);
                                    break label102;
                                 }

                                 var37 = LocalizationUtilsKt.battleLang("damage.item", pokemonName, this.$effect.getTypelessData());
                              }

                              val var43: PokemonBattle = this.$battle;
                              var43.broadcastChatMessage(TextKt.red(var37) as Component);
                           }

                           val var14: Float;
                           if (this.$causedFaint.element) {
                              var14 = 0.0F;
                              this.$battle.dispatch((new Function0<DispatchResult>(this.$battlePokemon) {
                                 {
                                    super(0);
                                    this.$battlePokemon = `$battlePokemon`;
                                 }

                                 @NotNull
                                 public final DispatchResult invoke() {
                                    this.$battlePokemon.getEffectedPokemon().setCurrentHealth(0);
                                    this.$battlePokemon.sendUpdate();
                                    return DispatchResultKt.getGO();
                                 }
                              }) as () -> DispatchResult);
                              this.$causedFaint.element = true;
                           } else {
                              val var18: Int = Integer.parseInt(
                                 StringsKt.split$default(this.$newHealth, new java.lang.String[]{"/"}, false, 0, 6, null).get(1) as java.lang.String
                              );
                              val var22: Int = var18 - var16;
                              var14 = (float)var16 / var18;
                              this.$battle
                                 .dispatchToFront(
                                    (
                                       new Function0<DispatchResult>(this.$battlePokemon, var16, var22) {
                                          {
                                             super(0);
                                             this.$battlePokemon = `$battlePokemon`;
                                             this.$remainingHealth = `$remainingHealth`;
                                             this.$difference = `$difference`;
                                          }

                                          @NotNull
                                          public final DispatchResult invoke() {
                                             this.$battlePokemon.getEffectedPokemon().setCurrentHealth(this.$remainingHealth);
                                             if (this.$difference > 0) {
                                                val var1: Pokemon = this.$battlePokemon.getEffectedPokemon();
                                                val var2: Int = this.$difference;
                                                if (DamageTakenEvolutionProgress.Companion.supports(var1)) {
                                                   val progress: DamageTakenEvolutionProgress = var1.getEvolutionProxy()
                                                      .current()
                                                      .progressFirstOrCreate(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE);
                                                   progress.updateProgress(
                                                      new DamageTakenEvolutionProgress.Progress(progress.currentProgress().getAmount() + var2)
                                                   );
                                                }
                                             }

                                             this.$battlePokemon.sendUpdate();
                                             return DispatchResultKt.getGO();
                                          }
                                       }
                                    ) as () -> DispatchResult
                                 );
                           }

                           val var45: Pair = this.this$0.getPrivateMessage().pnxAndUuid(0);
                           if (var45 != null) {
                              val var29: PokemonBattle = this.$battle;
                              val var32: DamageInstruction = this.this$0;
                              val pnx: java.lang.String = var45.component1() as java.lang.String;
                              PokemonBattle.sendSidedUpdate$default(
                                 var29,
                                 var32.getActor(),
                                 new BattleHealthChangePacket(pnx, (float)var16, null, 4, null),
                                 new BattleHealthChangePacket(pnx, var14, null, 4, null),
                                 false,
                                 8,
                                 null
                              );
                           }

                           this.$battle.getMinorBattleActions().put(this.$battlePokemon.getUuid(), this.this$0.getPrivateMessage());
                           return if (this.$lastCauser is MoveInstruction
                                 && (this.$lastCauser as MoveInstruction).getActionEffect() != null
                                 && !this.$causedFaint.element)
                              new UntilDispatch((new Function0<java.lang.Boolean>(this.$lastCauser) {
                                 {
                                    super(0);
                                    this.$lastCauser = `$lastCauser`;
                                 }

                                 @NotNull
                                 public final java.lang.Boolean invoke() {
                                    return (this.$lastCauser as MoveInstruction).getFuture().isDone();
                                 }
                              }) as () -> java.lang.Boolean)
                              else
                              (if (this.$causedFaint.element) DispatchResultKt.getGO() else new UntilDispatch((new Function0<java.lang.Boolean>(this.this$0) {
                                 {
                                    super(0);
                                    this.this$0 = `$receiver`;
                                 }

                                 @NotNull
                                 public final java.lang.Boolean invoke() {
                                    return !this.this$0.getHolds().contains("effects");
                                 }
                              }) as () -> java.lang.Boolean));
                        }
                     }
                  ) as () -> DispatchResult
               );
               return;
            }
         }
      }
   }

   override fun invoke(battle: PokemonBattle) {
      ActionEffectInstruction.DefaultImpls.invoke(this, battle);
   }

   override fun addMolangQueries(runtime: MoLangRuntime) {
      ActionEffectInstruction.DefaultImpls.addMolangQueries(this, runtime);
   }
}
