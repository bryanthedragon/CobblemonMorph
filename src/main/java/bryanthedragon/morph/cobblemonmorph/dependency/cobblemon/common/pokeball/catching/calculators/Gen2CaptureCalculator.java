package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators

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

public class Gen2CaptureCalculator(bugsFixed: Boolean) : CaptureCalculator {
   public final val bugsFixed: Boolean

   init {
      this.bugsFixed = bugsFixed;
   }

   public override fun id(): String {
      return "generation_2${if (this.bugsFixed) "_fixed" else ""}";
   }

   public override fun processCapture(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity): CaptureContext {
      val pokeBall: PokeBall = pokeBallEntity.getPokeBall();
      val pokemon: Pokemon = target.getPokemon();
      if (pokeBall.getCatchRateModifier().isGuaranteed()) {
         return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
      } else {
         val catchRate: Float = this.getCatchRate(thrower, pokeBallEntity, target, (float)pokemon.getForm().getCatchRate());
         val modifiedRate: Float = if (pokeBall.getCatchRateModifier().isValid(thrower, pokemon))
            pokeBall.getCatchRateModifier().modifyCatchRate(catchRate, thrower, pokemon)
            else
            catchRate;
         val var10000: PersistentStatusContainer = pokemon.getStatus();
         val status: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         val bonusStatus: Int = if (status !is SleepStatus && status !is FrozenStatus)
            (if (!this.bugsFixed || status !is ParalysisStatus && status !is BurnStatus && status !is PoisonStatus && status !is PoisonBadlyStatus) 1 else 5)
            else
            10;
         val modifiedCatchRate: Int = MathKt.roundToInt(
            RangesKt.coerceAtMost(
               Math.max(
                  (3.0F * (float)pokemon.getHp() - 2.0F * (float)pokemon.getCurrentHealth()) * modifiedRate / (3.0F * (float)pokemon.getHp())
                     + (float)bonusStatus,
                  1.0F
               ),
               255.0F
            )
         );
         if (Random.Default.nextInt(256) <= modifiedCatchRate) {
            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
         } else {
            val shakeProbability: Int = if (modifiedCatchRate <= 1)
               63
               else
               (
                  if (modifiedCatchRate == 2)
                     75
                     else
                     (
                        if (modifiedCatchRate == 3)
                           84
                           else
                           (
                              if (modifiedCatchRate == 4)
                                 90
                                 else
                                 (
                                    if (modifiedCatchRate == 5)
                                       95
                                       else
                                       (
                                          if (modifiedCatchRate <= 7)
                                             103
                                             else
                                             (
                                                if (modifiedCatchRate <= 10)
                                                   113
                                                   else
                                                   (
                                                      if (modifiedCatchRate <= 15)
                                                         126
                                                         else
                                                         (
                                                            if (modifiedCatchRate <= 20)
                                                               134
                                                               else
                                                               (
                                                                  if (modifiedCatchRate <= 30)
                                                                     149
                                                                     else
                                                                     (
                                                                        if (modifiedCatchRate <= 40)
                                                                           160
                                                                           else
                                                                           (
                                                                              if (modifiedCatchRate <= 50)
                                                                                 169
                                                                                 else
                                                                                 (
                                                                                    if (modifiedCatchRate <= 60)
                                                                                       177
                                                                                       else
                                                                                       (
                                                                                          if (modifiedCatchRate <= 80)
                                                                                             191
                                                                                             else
                                                                                             (
                                                                                                if (modifiedCatchRate <= 100)
                                                                                                   201
                                                                                                   else
                                                                                                   (
                                                                                                      if (modifiedCatchRate <= 120)
                                                                                                         211
                                                                                                         else
                                                                                                         (
                                                                                                            if (modifiedCatchRate <= 140)
                                                                                                               200
                                                                                                               else
                                                                                                               (
                                                                                                                  if (modifiedCatchRate <= 160)
                                                                                                                     227
                                                                                                                     else
                                                                                                                     (
                                                                                                                        if (modifiedCatchRate <= 180)
                                                                                                                           234
                                                                                                                           else
                                                                                                                           (
                                                                                                                              if (modifiedCatchRate <= 200)
                                                                                                                                 240
                                                                                                                                 else
                                                                                                                                 (
                                                                                                                                    if (modifiedCatchRate
                                                                                                                                          <= 220)
                                                                                                                                       246
                                                                                                                                       else
                                                                                                                                       (
                                                                                                                                          if (modifiedCatchRate
                                                                                                                                                <= 240)
                                                                                                                                             251
                                                                                                                                             else
                                                                                                                                             (
                                                                                                                                                if (modifiedCatchRate
                                                                                                                                                      <= 254)
                                                                                                                                                   253
                                                                                                                                                   else
                                                                                                                                                   255
                                                                                                                                             )
                                                                                                                                       )
                                                                                                                                 )
                                                                                                                           )
                                                                                                                     )
                                                                                                               )
                                                                                                         )
                                                                                                   )
                                                                                             )
                                                                                       )
                                                                                 )
                                                                           )
                                                                     )
                                                               )
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               );
            var shakes: Int = 0;
            val var13: Byte = 3;

            for (int var14 = 0; var14 < var13; var14++) {
               if (Random.Default.nextInt(256) >= shakeProbability) {
                  return new CaptureContext(shakes, false, false);
               }

               shakes++;
            }

            return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
         }
      }
   }

   override fun getCatchRate(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
      return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
   }
}
