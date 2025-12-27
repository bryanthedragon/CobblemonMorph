package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CriticalCaptureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.PokedexProgressCaptureMultiplierProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.BurnStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.FrozenStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.ParalysisStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonBadlyStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.SleepStatus
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.math.MathKt
import kotlin.random.Random
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

@SourceDebugExtension(["SMAP\nCobblemonCaptureCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonCaptureCalculator.kt\ncom/cobblemon/mod/common/pokeball/catching/calculators/CobblemonCaptureCalculator\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,112:1\n288#2:113\n1747#2,3:114\n289#2:117\n1#3:118\n*S KotlinDebug\n*F\n+ 1 CobblemonCaptureCalculator.kt\ncom/cobblemon/mod/common/pokeball/catching/calculators/CobblemonCaptureCalculator\n*L\n105#1:113\n106#1:114,3\n105#1:117\n*E\n"])
public object CobblemonCaptureCalculator : CaptureCalculator, CriticalCaptureProvider, PokedexProgressCaptureMultiplierProvider {
   public override fun id(): String {
      return "cobblemon";
   }

   public override fun processCapture(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity): CaptureContext {
      val pokeBall: PokeBall = pokeBallEntity.getPokeBall();
      val pokemon: Pokemon = target.getPokemon();
      if (pokeBall.getCatchRateModifier().isGuaranteed()) {
         return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
      } else {
         val inBattleModifier: Float = if (target.getBattleId() != null) 1.0F else 0.5F;
         val catchRate: Float = this.getCatchRate(thrower, pokeBallEntity, target, (float)pokemon.getForm().getCatchRate());
         val validModifier: Boolean = pokeBall.getCatchRateModifier().isValid(thrower, pokemon);
         val var10000: PersistentStatusContainer = pokemon.getStatus();
         val bonusLevel: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         var var23: Float = (pokeBall.getCatchRateModifier()
                  .behavior(thrower, pokemon)
                  .getMutator()
                  .invoke(
                     (3.0F * (float)pokemon.getHp() - 2.0F * (float)pokemon.getCurrentHealth()) * 1.0F * catchRate * inBattleModifier,
                     if (validModifier) pokeBall.getCatchRateModifier().value(thrower, pokemon) else 1.0F
                  ) as java.lang.Number)
               .floatValue()
            / (3.0F * pokemon.getHp())
            * (
               (
                     if (bonusLevel is SleepStatus || bonusLevel is FrozenStatus)
                        2.5F
                        else
                        (
                           if (bonusLevel is ParalysisStatus || bonusLevel is BurnStatus || bonusLevel is PoisonStatus || bonusLevel is PoisonBadlyStatus)
                              1.5F
                              else
                              1.0F
                        )
                  )
                  * (if (pokemon.getLevel() < 13) Math.max((36 - 2 * pokemon.getLevel()) / 10, 1) else 1)
            );
         if (thrower is ServerPlayer) {
            val critical: Int = this.findHighestThrowerLevel(thrower as ServerPlayer, pokemon);
            if (critical != null && critical < pokemon.getLevel()) {
               var23 *= Math.max(
                  0.1F, Math.min(1.0F, 1.0F - (float)((pokemon.getLevel() - critical) / (Cobblemon.INSTANCE.getConfig().getMaxPokemonLevel() / 2)))
               );
            }
         }

         val var24: Boolean = thrower is ServerPlayer && this.shouldHaveCriticalCapture(thrower as ServerPlayer, var23);
         val var25: Int = MathKt.roundToInt(65536.0F / (float)Math.pow((double)(255.0F / var23), (double)0.1875F));
         var shakes: Int = 0;
         val var17: Byte = 4;

         for (int var18 = 0; var18 < var17; var18++) {
            if (Random.Default.nextInt(65537) < var25) {
               shakes++;
            }

            if (var18 == 0 && var24) {
               return new CaptureContext(1, shakes == 1, true);
            }
         }

         return new CaptureContext(shakes, shakes == 4, false);
      }
   }

   private fun findHighestThrowerLevel(player: ServerPlayer, pokemon: Pokemon): Int? {
      val var10000: PokemonEntity = pokemon.getEntity();
      if (var10000 == null) {
         return null;
      } else {
         val var26: UUID = var10000.getBattleId();
         if (var26 == null) {
            return null;
         } else {
            val var27: PokemonBattle = BattleRegistry.INSTANCE.getBattle(var26);
            if (var27 == null) {
               return null;
            } else {
               val it: java.util.Iterator = var27.getActors().iterator();

               while (true) {
                  if (!it.hasNext()) {
                     var33 = null;
                     break;
                  }

                  var var10: Any;
                  label102: {
                     var10 = it.next();
                     val actor: BattleActor = var10 as BattleActor;
                     if (var10 as BattleActor is PlayerBattleActor && player.m_20148_() == (var10 as BattleActor).getUuid()) {
                        val `$this$any$iv`: java.lang.Iterable = actor.getActivePokemon();
                        var var31: Boolean;
                        if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
                           var31 = false;
                        } else {
                           val var15: java.util.Iterator = `$this$any$iv`.iterator();

                           while (true) {
                              if (!var15.hasNext()) {
                                 var31 = false;
                                 break;
                              }

                              label94: {
                                 val var28: BattlePokemon = (var15.next() as ActiveBattlePokemon).getBattlePokemon();
                                 if (var28 != null) {
                                    val var29: Pokemon = var28.getEffectedPokemon();
                                    if (var29 != null) {
                                       var30 = var29.getUuid();
                                       break label94;
                                    }
                                 }

                                 var30 = null;
                              }

                              if (var30 == pokemon.getUuid()) {
                                 var31 = true;
                                 break;
                              }
                           }
                        }

                        if (var31) {
                           var32 = true;
                           break label102;
                        }
                     }

                     var32 = false;
                  }

                  if (var32) {
                     var33 = var10;
                     break;
                  }
               }

               val var34: BattleActor = var33 as BattleActor;
               if (var33 as BattleActor == null) {
                  return null;
               } else {
                  val var19: java.util.Iterator = var34.getSide().getOppositeSide().getActivePokemon().iterator();
                  val var35: java.lang.Comparable;
                  if (!var19.hasNext()) {
                     var35 = null;
                  } else {
                     label73: {
                        val var36: BattlePokemon = (var19.next() as ActiveBattlePokemon).getBattlePokemon();
                        if (var36 != null) {
                           val var37: Pokemon = var36.getEffectedPokemon();
                           if (var37 != null) {
                              var38 = var37.getLevel();
                              break label73;
                           }
                        }

                        var38 = 1;
                     }

                     var var21: java.lang.Comparable = var38;

                     while (var19.hasNext()) {
                        label62: {
                           val var39: BattlePokemon = (var19.next() as ActiveBattlePokemon).getBattlePokemon();
                           if (var39 != null) {
                              val var40: Pokemon = var39.getEffectedPokemon();
                              if (var40 != null) {
                                 var41 = var40.getLevel();
                                 break label62;
                              }
                           }

                           var41 = 1;
                        }

                        val var24: java.lang.Comparable = var41;
                        if (var21.compareTo(var24) < 0) {
                           var21 = var24;
                        }
                     }

                     var35 = var21;
                  }

                  return var35 as Int;
               }
            }
         }
      }
   }

   override fun getCatchRate(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
      return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
   }

   override fun shouldHaveCriticalCapture(player: ServerPlayer, modifiedCatchRate: Float): Boolean {
      return CriticalCaptureProvider.DefaultImpls.shouldHaveCriticalCapture(this, player, modifiedCatchRate);
   }

   override fun caughtMultiplierFor(player: ServerPlayer): Float {
      return PokedexProgressCaptureMultiplierProvider.DefaultImpls.caughtMultiplierFor(this, player);
   }
}
