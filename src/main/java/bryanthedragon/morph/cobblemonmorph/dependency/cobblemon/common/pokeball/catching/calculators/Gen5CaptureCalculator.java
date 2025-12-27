package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CriticalCaptureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.PokedexProgressCaptureMultiplierProvider
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

public object Gen5CaptureCalculator : CaptureCalculator, CriticalCaptureProvider, PokedexProgressCaptureMultiplierProvider {
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
      return "generation_5";
   }

   public override fun processCapture(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity): CaptureContext {
      val pokeBall: PokeBall = pokeBallEntity.getPokeBall();
      val pokemon: Pokemon = target.getPokemon();
      if (pokeBall.getCatchRateModifier().isGuaranteed()) {
         return new CaptureContext(3, true, false);
      } else {
         val darkGrass: Int = if (thrower is ServerPlayer) MathKt.roundToInt(this.caughtMultiplierFor(thrower as ServerPlayer)) else 1;
         val catchRate: Float = this.getCatchRate(thrower, pokeBallEntity, target, (float)pokemon.getForm().getCatchRate());
         val validModifier: Boolean = pokeBall.getCatchRateModifier().isValid(thrower, pokemon);
         val var10000: PersistentStatusContainer = pokemon.getStatus();
         val rate: PersistentStatus = if (var10000 != null) var10000.getStatus() else null;
         val bonusStatus: Float = if (rate is SleepStatus || rate is FrozenStatus)
            2.5F
            else
            (if (rate is ParalysisStatus || rate is BurnStatus || rate is PoisonStatus || rate is PoisonBadlyStatus) 1.5F else 1.0F);
         val var23: Float;
         val var24: Float;
         if (apricornPokeballs.contains(pokeBall)) {
            var23 = if (validModifier) pokeBall.getCatchRateModifier().modifyCatchRate(catchRate, thrower, pokemon) else 1.0F;
            var24 = 1.0F;
         } else {
            var23 = catchRate;
            var24 = if (validModifier) pokeBall.getCatchRateModifier().value(thrower, pokemon) else 1.0F;
         }

         val modifiedCatchRate: Float = (pokeBall.getCatchRateModifier()
                  .behavior(thrower, pokemon)
                  .getMutator()
                  .invoke((3.0F * (float)pokemon.getHp() - 2.0F * (float)pokemon.getCurrentHealth()) * (float)darkGrass * var23, var24) as java.lang.Number)
               .floatValue()
            / (3.0F * pokemon.getHp())
            * bonusStatus;
         val critical: Boolean = thrower is ServerPlayer && this.shouldHaveCriticalCapture(thrower as ServerPlayer, modifiedCatchRate);
         if (modifiedCatchRate >= 1044480.0F) {
            return CaptureContext.Companion.successful(critical);
         } else {
            val shakeProbability: Int = MathKt.roundToInt(
               65336.0F / (float)MathKt.roundToInt(Math.sqrt(Math.sqrt((double)MathKt.roundToInt(1044480.0F / modifiedCatchRate))))
            );
            var shakes: Int = 0;
            val var16: Byte = 3;

            for (int var17 = 0; var17 < var16; var17++) {
               var failed: Boolean = true;
               if (Random.Default.nextInt(65537) < shakeProbability) {
                  shakes++;
                  failed = false;
               }

               if (critical && var17 == 0) {
                  return new CaptureContext(1, !failed, true);
               }

               if (var17 == 0 && failed && !critical) {
                  return new CaptureContext(0, false, false);
               }

               if (var17 == 1 && failed && !critical) {
                  return new CaptureContext(1, false, false);
               }

               if (var17 == 2 && failed && !critical) {
                  return new CaptureContext(3, false, false);
               }
            }

            return new CaptureContext(shakes, true, false);
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
