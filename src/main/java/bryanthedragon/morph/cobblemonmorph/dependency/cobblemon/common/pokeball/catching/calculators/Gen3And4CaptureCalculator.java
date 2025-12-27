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

public object Gen3And4CaptureCalculator : CaptureCalculator {
   private final val apricornPokeballs: Set<PokeBall> =
      SetsKt.setOf(
         new PokeBall[]{
            PokeBalls.INSTANCE.getHEAVY_BALL(),
            PokeBalls.INSTANCE.getLURE_BALL(),
            PokeBalls.INSTANCE.getFRIEND_BALL(),
            PokeBalls.INSTANCE.getLOVE_BALL(),
            PokeBalls.INSTANCE.getLEVEL_BALL(),
            PokeBalls.INSTANCE.getFAST_BALL(),
            PokeBalls.INSTANCE.getMOON_BALL()
         }
      )

   public override fun id(): String {
      return "generation_3_and_4";
   }

   public override fun processCapture(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity): CaptureContext {
      val pokeBall: PokeBall = pokeBallEntity.getPokeBall();
      val pokemon: Pokemon = target.getPokemon();
      if (pokeBall.getCatchRateModifier().isGuaranteed()) {
         return CaptureContext.Companion.successful$default(CaptureContext.Companion, false, 1, null);
      } else {
         val catchRate: Float = this.getCatchRate(thrower, pokeBallEntity, target, (float)pokemon.getForm().getCatchRate());
         val validModifier: Boolean = pokeBall.getCatchRateModifier().isValid(thrower, pokemon);
         val var10000: PersistentStatusContainer = pokemon.getStatus();
         val rate: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         val bonusStatus: Float = if (rate is SleepStatus || rate is FrozenStatus)
            2.0F
            else
            (if (rate is ParalysisStatus || rate is BurnStatus || rate is PoisonStatus || rate is PoisonBadlyStatus) 1.5F else 1.0F);
         val var20: Float;
         val var21: Float;
         if (apricornPokeballs.contains(pokeBall)) {
            var20 = if (validModifier) pokeBall.getCatchRateModifier().modifyCatchRate(catchRate, thrower, pokemon) else 1.0F;
            var21 = 1.0F;
         } else {
            var20 = catchRate;
            var21 = if (validModifier) pokeBall.getCatchRateModifier().value(thrower, pokemon) else 1.0F;
         }

         val shakeProbability: Int = MathKt.roundToInt(
            1048560.0F
               / (float)MathKt.roundToInt(
                  Math.sqrt(
                     Math.sqrt(
                        (double)MathKt.roundToInt(
                           1.671168E7F
                              / (
                                 (pokeBall.getCatchRateModifier()
                                          .behavior(thrower, pokemon)
                                          .getMutator()
                                          .invoke((3.0F * (float)pokemon.getHp() - 2.0F * (float)pokemon.getCurrentHealth()) * var20, var21) as java.lang.Number)
                                       .floatValue()
                                    / (3.0F * (float)pokemon.getHp())
                                    * bonusStatus
                              )
                        )
                     )
                  )
               )
         );
         var shakes: Int = 0;
         val var14: Byte = 4;

         for (int var15 = 0; var15 < var14; var15++) {
            if (Random.Default.nextInt(65537) < shakeProbability) {
               shakes++;
            }
         }

         return new CaptureContext(shakes, shakes == 4, false);
      }
   }

   override fun getCatchRate(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
      return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
   }
}
