package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball.PokemonCatchRateEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.LivingEntity

public interface CaptureCalculator {
   public abstract fun id(): String {
   }

   public abstract fun processCapture(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity): CaptureContext {
   }

   public open fun getCatchRate(thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nCaptureCalculator.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CaptureCalculator.kt\ncom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator$DefaultImpls\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,60:1\n14#2,5:61\n19#2:69\n13579#3:66\n13580#3:68\n14#4:67\n*S KotlinDebug\n*F\n+ 1 CaptureCalculator.kt\ncom/cobblemon/mod/common/api/pokeball/catching/calculators/CaptureCalculator$DefaultImpls\n*L\n56#1:61,5\n56#1:69\n56#1:66\n56#1:68\n56#1:67\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun getCatchRate(`$this`: CaptureCalculator, thrower: LivingEntity, pokeBallEntity: EmptyPokeBallEntity, target: PokemonEntity, catchRate: Float): Float {
         val event: PokemonCatchRateEvent = new PokemonCatchRateEvent(thrower, pokeBallEntity, target, catchRate);
         val `$this$iv`: EventObservable = CobblemonEvents.POKEMON_CATCH_RATE;
         val `events$iv`: Array<PokemonCatchRateEvent> = new PokemonCatchRateEvent[]{event};
         `$this$iv`.emit(Arrays.copyOf(`events$iv`, `events$iv`.length));

         for (Object element$iv$iv : events$iv) {
            ;
         }

         return event.getCatchRate();
      }
   }
}
