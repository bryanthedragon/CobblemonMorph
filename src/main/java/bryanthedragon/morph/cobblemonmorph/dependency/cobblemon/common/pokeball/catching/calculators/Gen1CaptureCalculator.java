package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator
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
import kotlin.math.MathKt
import kotlin.random.Random
import net.minecraft.world.entity.LivingEntity

public object Gen1CaptureCalculator : CaptureCalculator {
   private const val FRZ_SLEEP_THRESHOLD: Int = 25
   private const val PARA_BRN_PSN_THRESHOLD: Int = 12

   public override fun id(): String {
      return "generation_1";
   }

   public override fun processCapture(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity): CaptureContext {
      val pokeBall: PokeBall = pokeBallEntity.getPokeBall();
      val pokemon: Pokemon = target.getPokemon();
      if (pokeBall.getCatchRateModifier().isGuaranteed()) {
         return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
      } else {
         val nBound: Int = if (pokeBall == PokeBalls.INSTANCE.getPOKE_BALL()) 255 else (if (pokeBall == PokeBalls.INSTANCE.getGREAT_BALL()) 200 else 150);
         val n: Int = Random.Default.nextInt(nBound + 1);
         val var10000: PersistentStatusContainer = pokemon.getStatus();
         val status: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         if ((status is FrozenStatus || status is SleepStatus) && n < 25) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
         } else if ((status is ParalysisStatus || status is BurnStatus || status is PoisonStatus || status is PoisonBadlyStatus) && n < 12) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
         } else if (n - 0 > pokemon.getForm().getCatchRate()) {
            return new CaptureContext(0, false, false);
         } else {
            val m: Int = Random.Default.nextInt(256);
            val f: Int = MathKt.roundToInt(
               RangesKt.coerceIn(
                  (float)pokemon.getHp()
                     * 255.0F
                     * 4.0F
                     / ((float)pokemon.getCurrentHealth() * (if (pokeBall == PokeBalls.INSTANCE.getGREAT_BALL()) 8.0F else 12.0F)),
                  1.0F,
                  255.0F
               )
            );
            return if (f >= m)
               CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null)
               else
               new CaptureContext(
                  this.calculateShakes(pokemon, this.getCatchRate(thrower, pokeBallEntity, target, (float)pokemon.getForm().getCatchRate()), nBound, f),
                  false,
                  false
               );
         }
      }
   }

   private fun calculateShakes(pokemon: Pokemon, catchRate: Float, ballValue: Int, f: Int): Int {
      val d: Float = catchRate * 100.0F / ballValue;
      if (catchRate * 100.0F / ballValue >= 256.0F) {
         return 3;
      } else {
         val var10000: PersistentStatusContainer = pokemon.getStatus();
         val x: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         return if (d * f / 255
                  + (
                     if (x is FrozenStatus || x is SleepStatus)
                        10
                        else
                        (if (x is ParalysisStatus || x is BurnStatus || x is PoisonStatus || x is PoisonBadlyStatus) 5 else 0)
                  )
               < 10.0F)
            0
            else
            (
               if (d * f / 255
                        + (
                           if (x is FrozenStatus || x is SleepStatus)
                              10
                              else
                              (if (x is ParalysisStatus || x is BurnStatus || x is PoisonStatus || x is PoisonBadlyStatus) 5 else 0)
                        )
                     < 30.0F)
                  1
                  else
                  (
                     if (d * f / 255
                              + (
                                 if (x is FrozenStatus || x is SleepStatus)
                                    10
                                    else
                                    (if (x is ParalysisStatus || x is BurnStatus || x is PoisonStatus || x is PoisonBadlyStatus) 5 else 0)
                              )
                           < 70.0F)
                        2
                        else
                        3
                  )
            );
      }
   }

   override fun getCatchRate(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
      return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
   }
}
