package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.BattleDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSwitchPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt
import java.util.ArrayList;
import java.util.UUID
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSwitchInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwitchInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction\n+ 2 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet$getNextInstruction$1\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,156:1\n42#2,6:157\n800#3,11:163\n288#3:174\n289#3:176\n42#4:175\n4098#5,11:177\n1#6:188\n*S KotlinDebug\n*F\n+ 1 SwitchInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction\n*L\n43#1:157,6\n43#1:163,11\n43#1:174\n43#1:176\n43#1:175\n52#1:177,11\n*E\n"])
public class SwitchInstruction(instructionSet: InstructionSet, battleActor: BattleActor, publicMessage: BattleMessage, privateMessage: BattleMessage) :
   InterpreterInstruction {
   public final val battleActor: BattleActor
   public final val instructionSet: InstructionSet
   public final val privateMessage: BattleMessage
   public final val publicMessage: BattleMessage

   init {
      this.instructionSet = instructionSet;
      this.battleActor = battleActor;
      this.publicMessage = publicMessage;
      this.privateMessage = privateMessage;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: Pair = this.publicMessage.pnxAndUuid(0);
      if (var10000 != null) {
         val pnx: java.lang.String = var10000.component1() as java.lang.String;
         val var3: Pair = battle.getActorAndActiveSlotFromPNX(pnx);
         val actor: BattleActor = var3.component1() as BattleActor;
         val activePokemon: ActiveBattlePokemon = var3.component2() as ActiveBattlePokemon;
         val entity: LivingEntity = if (actor is EntityBackedBattleActor) (actor as EntityBackedBattleActor).getEntity() else null;
         val pokemon: InstructionSet = this.instructionSet;
         val it: Int = this.instructionSet.getInstructions().indexOf(this);
         var var45: Any;
         if (CollectionsKt.last(pokemon.getInstructions()) == this) {
            var45 = null;
         } else {
            val var13: java.lang.Iterable = pokemon.getInstructions().subList(it + 1, pokemon.getInstructions().size());
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
               if (offset is TransformInstruction) {
                  `destination$iv$iv`.add(offset);
               }
            }

            val `$this$filterIsInstanceTo$iv$iv`: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

            while (true) {
               if (!`$this$filterIsInstanceTo$iv$iv`.hasNext()) {
                  var45 = null;
                  break;
               }

               val var35: Any = `$this$filterIsInstanceTo$iv$iv`.next();
               if (true) {
                  var45 = (BattlePokemon)var35;
                  break;
               }
            }
         }

         val imposter: Boolean = (if (var45 as TransformInstruction != null) (var45 as TransformInstruction).getExpectedTarget() else null) != null;
         val illusion: BattlePokemon = this.publicMessage.battlePokemonFromOptional(battle, "is");
         var45 = this.publicMessage.battlePokemon(0, battle);
         if (var45 != null) {
            if (!battle.getStarted()) {
               activePokemon.setBattlePokemon(var45);
               activePokemon.setIllusion(illusion);
               val pokemonEntity: PokemonEntity = var45.getEntity();
               if (pokemonEntity == null && entity != null) {
                  var var28: Array<Any> = this.battleActor.getSide().getOppositeSide().getActors();
                  val var36: java.util.Collection = new ArrayList();

                  for (Object element$iv$iv : $this$filterIsInstance$iv) {
                     if (idealPos is EntityBackedBattleActor) {
                        var36.add(idealPos);
                     }
                  }

                  label70: {
                     val var47: EntityBackedBattleActor = CollectionsKt.firstOrNull(var36 as java.util.List) as EntityBackedBattleActor;
                     if (var47 != null) {
                        val var48: LivingEntity = var47.getEntity();
                        if (var48 != null) {
                           val var49: Vec3 = var48.m_20182_();
                           if (var49 != null) {
                              val var44: Vec3 = entity.m_20182_().m_82549_(var49.m_82546_(entity.m_20182_()).m_82490_(0.33));
                              var50 = var44;
                              if (var44 != null) {
                                 break label70;
                              }
                           }
                        }
                     }

                     var50 = entity.m_20182_();
                  }

                  actor.setStillSendingOutCount(actor.getStillSendingOutCount() + 1);
                  val var25: Pokemon = var45.getEffectedPokemon();
                  var28 = battle.getBattleId();
                  val var51: Level = entity.m_9236_();
                  val var32: ServerLevel = var51 as ServerLevel;
                  val var34: IllusionEffect = if (illusion != null) new IllusionEffect(illusion.getEffectedPokemon()) else null;
                  Pokemon.sendOutWithAnimation$default(var25, entity, var32, var50, var28, false, var34, null, 64, null)
                     .thenApply(SwitchInstruction::invoke$lambda$2);
               } else if (pokemonEntity != null) {
                  if (illusion != null) {
                     new IllusionEffect(illusion.getEffectedPokemon()).start(pokemonEntity);
                  }
               }
            } else {
               battle.dispatchInsert(
                  (
                     new Function0<java.lang.Iterable<? extends BattleDispatch>>(var45, activePokemon, battle, this, entity, actor, pnx, illusion, imposter) {
                        {
                           super(0);
                           this.$pokemon = `$pokemon`;
                           this.$activePokemon = `$activePokemon`;
                           this.$battle = `$battle`;
                           this.this$0 = `$receiver`;
                           this.$entity = `$entity`;
                           this.$actor = `$actor`;
                           this.$pnx = `$pnx`;
                           this.$illusion = `$illusion`;
                           this.$imposter = `$imposter`;
                        }

                        @NotNull
                        public final java.lang.Iterable<BattleDispatch> invoke() {
                           this.$pokemon.sendUpdate();
                           if (this.$activePokemon.getBattlePokemon() == this.$pokemon) {
                              return SetsKt.emptySet();
                           } else {
                              val var10000: BattlePokemon = this.$activePokemon.getBattlePokemon();
                              if (var10000 != null) {
                                 val var2: SwitchInstruction = this.this$0;
                                 val var3: BattlePokemon = this.$pokemon;
                                 val var4: PokemonBattle = this.$battle;
                                 val var9: Effect = BattleMessage.effect$default(this.this$0.getPublicMessage(), null, 1, null);
                                 if ((if (var9 != null) var9.getId() else null) == "batonpass") {
                                    var10000.getContextManager().swap(var3.getContextManager(), BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                                 }

                                 var10000.getContextManager().clear(BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                                 var4.getMajorBattleActions().put(var10000.getUuid(), var2.getPublicMessage());
                              }

                              this.$battle.getMajorBattleActions().put(this.$pokemon.getUuid(), this.this$0.getPublicMessage());
                              return SetsKt.setOf(<unrepresentable>::invoke$lambda$1);
                           }
                        }

                        private static final DispatchResult invoke$lambda$1(
                           LivingEntity $entity,
                           PokemonBattle $battle,
                           BattleActor $actor,
                           java.lang.String $pnx,
                           ActiveBattlePokemon $activePokemon,
                           BattlePokemon $pokemon,
                           BattlePokemon $illusion,
                           boolean $imposter,
                           PokemonBattle it
                        ) {
                           return if (`$entity` != null)
                              SwitchInstruction.Companion
                                 .createEntitySwitch(`$battle`, `$actor`, `$entity`, `$pnx`, `$activePokemon`, `$pokemon`, `$illusion`, `$imposter`)
                              else
                              SwitchInstruction.Companion.createNonEntitySwitch(`$battle`, `$actor`, `$pnx`, `$activePokemon`, `$pokemon`, `$illusion`);
                        }
                     }
                  ) as () -> MutableIterable<BattleDispatch>
               );
            }
         }
      }
   }

   @JvmStatic
   fun `invoke$lambda$2`(`$tmp0`: Function1, p0: Any): Int {
      return `$tmp0`.invoke(p0) as Int;
   }

   @SourceDebugExtension(["SMAP\nSwitchInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwitchInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,156:1\n1#2:157\n*E\n"])
   public companion object {
      public fun createEntitySwitch(
         battle: PokemonBattle,
         actor: BattleActor,
         entity: LivingEntity,
         pnx: String,
         activePokemon: ActiveBattlePokemon,
         newPokemon: BattlePokemon,
         illusion: BattlePokemon? = null,
         imposter: Boolean = false
      ): DispatchResult {
         var sendOutFuture: CompletableFuture;
         var doCry: Boolean;
         var var12: CompletableFuture;
         label19: {
            val var10000: BattlePokemon = activePokemon.getBattlePokemon();
            val pokemonEntity: PokemonEntity = if (var10000 != null) var10000.getEntity() else null;
            sendOutFuture = new CompletableFuture();
            doCry = illusion == null && !imposter;
            if (pokemonEntity != null) {
               var12 = pokemonEntity.recallWithAnimation();
               if (var12 != null) {
                  break label19;
               }
            }

            var12 = CompletableFuture.completedFuture(Unit.INSTANCE);
         }

         var12.thenApply(SwitchInstruction.Companion::createEntitySwitch$lambda$3);
         return new UntilDispatch((new Function0<java.lang.Boolean>(sendOutFuture) {
            {
               super(0);
               this.$sendOutFuture = `$sendOutFuture`;
            }

            @NotNull
            public final java.lang.Boolean invoke() {
               return this.$sendOutFuture.isDone();
            }
         }) as () -> java.lang.Boolean);
      }

      public fun createNonEntitySwitch(
         battle: PokemonBattle,
         actor: BattleActor,
         pnx: String,
         activePokemon: ActiveBattlePokemon,
         newPokemon: BattlePokemon,
         illusion: BattlePokemon? = null
      ): DispatchResult {
         CollectionUtilsKt.swap(actor.getPokemonList(), actor.getActivePokemon().indexOf(activePokemon), actor.getPokemonList().indexOf(newPokemon));
         activePokemon.setBattlePokemon(newPokemon);
         activePokemon.setIllusion(illusion);
         PokemonBattle.sendSidedUpdate$default(
            battle,
            actor,
            new BattleSwitchPokemonPacket(pnx, newPokemon, true, illusion),
            new BattleSwitchPokemonPacket(pnx, newPokemon, false, illusion),
            false,
            8,
            null
         );
         return new WaitDispatch(1.5F);
      }

      @JvmStatic
      fun `createEntitySwitch$lambda$3$lambda$2`(`$tmp0`: Function1, p0: Any) {
         `$tmp0`.invoke(p0);
      }

      @JvmStatic
      fun `createEntitySwitch$lambda$3`(
         `$actor`: BattleActor,
         `$activePokemon`: ActiveBattlePokemon,
         `$newPokemon`: BattlePokemon,
         `$illusion`: BattlePokemon,
         `$battle`: PokemonBattle,
         `$pnx`: java.lang.String,
         `$doCry`: Boolean,
         `$sendOutFuture`: CompletableFuture,
         `$entity`: LivingEntity,
         it: Any
      ): Any {
         CollectionUtilsKt.swap(
            `$actor`.getPokemonList(), `$actor`.getActivePokemon().indexOf(`$activePokemon`), `$actor`.getPokemonList().indexOf(`$newPokemon`)
         );
         `$activePokemon`.setBattlePokemon(`$newPokemon`);
         `$activePokemon`.setIllusion(`$illusion`);
         PokemonBattle.sendSidedUpdate$default(
            `$battle`,
            `$actor`,
            new BattleSwitchPokemonPacket(`$pnx`, `$newPokemon`, true, `$illusion`),
            new BattleSwitchPokemonPacket(`$pnx`, `$newPokemon`, false, `$illusion`),
            false,
            8,
            null
         );
         val var20: Any;
         if (`$newPokemon`.getEntity() != null) {
            if (`$illusion` != null) {
               var20 = new IllusionEffect(`$illusion`.getEffectedPokemon());
               val var10001: PokemonEntity = `$newPokemon`.getEntity();
               var20.start(var10001);
            }

            if (`$doCry`) {
               val var19: PokemonEntity = `$newPokemon`.getEntity();
               if (var19 != null) {
                  var19.cry();
               }
            }

            var20 = `$sendOutFuture`.complete(Unit.INSTANCE);
         } else {
            var lastPosition: Pair;
            label39: {
               lastPosition = `$activePokemon`.getPosition();
               if (lastPosition != null) {
                  var21 = lastPosition.getFirst() as ServerLevel;
                  if (var21 != null) {
                     break label39;
                  }
               }

               val var22: Level = `$entity`.m_9236_();
               var21 = var22 as ServerLevel;
            }

            label34: {
               if (lastPosition != null) {
                  var23 = lastPosition.getSecond() as Vec3;
                  if (var23 != null) {
                     break label34;
                  }
               }

               var23 = `$entity`.m_20182_();
            }

            val var13: Pokemon = `$newPokemon`.getEffectedPokemon();
            val var14: UUID = `$battle`.getBattleId();
            val var15: IllusionEffect = if (`$illusion` != null) new IllusionEffect(`$illusion`.getEffectedPokemon()) else null;
            var20 = Pokemon.sendOutWithAnimation$default(var13, `$entity`, var21, var23, var14, `$doCry`, var15, null, 64, null)
               .thenAccept(SwitchInstruction.Companion::createEntitySwitch$lambda$3$lambda$2);
         }

         return var20;
      }
   }
}
