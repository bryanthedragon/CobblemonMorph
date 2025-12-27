package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles
import net.minecraft.predicate.NumberRange.FloatRange

public object FloatNumberRangeAdapter : JsonDeserializer<Doubles>, JsonSerializer<Doubles> {
   public open fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): FloatRange {
      val var10000: Doubles = Doubles.m_154791_(element);
      return var10000;
   }

   public open fun serialize(range: FloatRange, type: Type, context: JsonSerializationContext): JsonElement {
      val var10000: JsonElement = range.m_55328_();
      return var10000;
   }
}
