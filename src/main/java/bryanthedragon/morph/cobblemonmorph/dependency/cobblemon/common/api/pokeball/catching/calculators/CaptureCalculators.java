package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.DebugCaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen1CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen2CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen3And4CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen5CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen6CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen7CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen8CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.Gen9CaptureCalculator
import java.util.LinkedHashMap
import java.util.Locale

public object CaptureCalculators {
   private final val calculators: LinkedHashMap<String, CaptureCalculator> = new LinkedHashMap()

   public fun register(calculator: CaptureCalculator) {
      val var10000: java.lang.String = calculator.id().toLowerCase(Locale.ROOT);
      val existing: CaptureCalculator = calculators.put(var10000, calculator);
      if (existing != null) {
         Cobblemon.INSTANCE
            .getLOGGER()
            .debug(
               "The capture calculator {} with ID {} was replaced by {}",
               (existing.getClass()::class).getQualifiedName(),
               var10000,
               (calculator.getClass()::class).getQualifiedName()
            );
      }
   }

   public fun fromId(id: String): CaptureCalculator? {
      val var10000: LinkedHashMap = calculators;
      val var10001: java.lang.String = id.toLowerCase(Locale.ROOT);
      return var10000.get(var10001) as CaptureCalculator;
   }

   internal fun registerDefaults() {
      this.register(Gen1CaptureCalculator.INSTANCE);
      this.register(new Gen2CaptureCalculator(false));
      this.register(new Gen2CaptureCalculator(true));
      this.register(Gen3And4CaptureCalculator.INSTANCE);
      this.register(Gen5CaptureCalculator.INSTANCE);
      this.register(Gen6CaptureCalculator.INSTANCE);
      this.register(Gen7CaptureCalculator.INSTANCE);
      this.register(Gen8CaptureCalculator.INSTANCE);
      this.register(Gen9CaptureCalculator.INSTANCE);
      this.register(CobblemonCaptureCalculator.INSTANCE);
      this.register(DebugCaptureCalculator.INSTANCE);
   }
}
