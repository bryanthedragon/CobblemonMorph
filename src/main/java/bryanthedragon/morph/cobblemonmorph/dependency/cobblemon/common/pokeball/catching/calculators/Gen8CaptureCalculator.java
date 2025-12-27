package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CriticalCaptureProvider
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
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

public object Gen8CaptureCalculator : CaptureCalculator, CriticalCaptureProvider {
   public override fun id(): String {
      return "generation_8";
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
         val bonusLevel: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         val modifiedCatchRate: Float = (pokeBall.getCatchRateModifier()
                  .behavior(thrower, pokemon)
                  .getMutator()
                  .invoke(
                     (3.0F * (float)pokemon.getHp() - 2.0F * (float)pokemon.getCurrentHealth()) * catchRate,
                     if (validModifier) pokeBall.getCatchRateModifier().value(thrower, pokemon) else 1.0F
                  ) as java.lang.Number)
               .floatValue()
            / (3.0F * pokemon.getHp())
            * (
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
            * (if (pokemon.getLevel() < 20) Math.max((30 - pokemon.getLevel()) / 10, 1) else 1);
         val critical: Boolean = thrower is ServerPlayer && this.shouldHaveCriticalCapture(thrower as ServerPlayer, modifiedCatchRate);
         val shakeProbability: Int = MathKt.roundToInt(65536.0F / (float)Math.pow((double)(255.0F / modifiedCatchRate), (double)0.1875F));
         var shakes: Int = 0;
         val var15: Byte = 4;

         for (int var16 = 0; var16 < var15; var16++) {
            if (Random.Default.nextInt(65537) < shakeProbability) {
               shakes++;
            }

            if (var16 == 0 && critical) {
               return new CaptureContext(1, shakes == 1, true);
            }
         }

         return new CaptureContext(shakes, shakes == 4, false);
      }
   }

   override fun getCatchRate(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
      return CaptureCalculator.DefaultImpls.getCatchRate(this, thrower, pokeBallEntity, target, catchRate);
   }

   override fun shouldHaveCriticalCapture(player: ServerPlayer, modifiedCatchRate: Float): Boolean {
      return CriticalCaptureProvider.DefaultImpls.shouldHaveCriticalCapture(this, player, modifiedCatchRate);
   }
}
