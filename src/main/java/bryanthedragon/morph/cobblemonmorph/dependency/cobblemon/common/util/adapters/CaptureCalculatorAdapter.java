package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculators
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.catching.calculators.CobblemonCaptureCalculator
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.Locale

public object CaptureCalculatorAdapter : JsonDeserializer<CaptureCalculator>, JsonSerializer<CaptureCalculator> {
   public open fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): CaptureCalculator {
      var var10000: java.lang.String = element.getAsString();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val captureCalculator: CaptureCalculator = CaptureCalculators.INSTANCE.fromId(var10000);
      if (captureCalculator == null) {
         Cobblemon.INSTANCE
            .getLOGGER()
            .error("Failed to load CaptureCalculator from the ID {} defaulting to the {}", var10000, (CobblemonCaptureCalculator::class).getSimpleName());
         return CobblemonCaptureCalculator.INSTANCE;
      } else {
         return captureCalculator;
      }
   }

   public open fun serialize(calculator: CaptureCalculator, type: Type, context: JsonSerializationContext): JsonElement {
      val var10002: java.lang.String = calculator.id().toLowerCase(Locale.ROOT);
      return (new JsonPrimitive(var10002)) as JsonElement;
   }
}
