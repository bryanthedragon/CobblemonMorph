package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import kotlin.text.MatchResult.Destructured

public object IntRangeAdapter : JsonSerializer<IntRange>, JsonDeserializer<IntRange> {
   private final val PATTERN: Regex = new Regex("(-?\\d+)-?(-?\\d+)?")

   public open fun serialize(range: IntRange, type: Type, ctx: JsonSerializationContext): JsonElement {
      return if (range.getFirst() == range.getLast())
         (new JsonPrimitive(range.getFirst())) as JsonElement
         else
         (new JsonPrimitive("${range.getFirst()}-${range.getLast()}")) as JsonElement;
   }

   public open fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): IntRange {
      val var10000: Regex = PATTERN;
      val var10001: java.lang.String = json.getAsString();
      val var7: MatchResult = Regex.find$default(var10000, var10001, 0, 2, null);
      val var4: Destructured = var7.getDestructured();
      val start: java.lang.String = var4.getMatch().getGroupValues().get(1) as java.lang.String;
      val end: java.lang.String = var4.getMatch().getGroupValues().get(2) as java.lang.String;
      return if (end.length() == 0)
         new IntRange(Integer.parseInt(start), Integer.parseInt(start))
         else
         new IntRange(Integer.parseInt(start), Integer.parseInt(end));
   }
}
