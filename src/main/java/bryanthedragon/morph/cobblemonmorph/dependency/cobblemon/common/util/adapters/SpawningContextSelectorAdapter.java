package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ConditionalSpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSpawningContextSelectorAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningContextSelectorAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawningContextSelectorAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"])
public object SpawningContextSelectorAdapter : JsonDeserializer<SpawningContextSelector> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawningContextSelector {
      val var10: SpawningContextSelector;
      if (json.isJsonPrimitive()) {
         val var10000: java.lang.String = json.getAsString();
         val expression: Expression = MoLangExtensionsKt.asExpression(var10000);
         val typex: ExpressionSpawningContextSelector = new ExpressionSpawningContextSelector();
         typex.setExpression(expression);
         var10 = typex;
      } else {
         val var11: JsonElement = (json as JsonObject).get("type");
         val var12: java.lang.String = if (var11 != null) var11.getAsString() else null;
         if (var12 == null) {
            val var14: Any = ctx.deserialize(json, ConditionalSpawningContextSelector::class.java);
            return var14 as SpawningContextSelector;
         }

         val var13: Class = SpawningContextSelector.Companion.getTypes().get(var12);
         if (var13 == null) {
            throw new IllegalArgumentException("Unknown spawn detail selector type: $var12");
         }

         val var8: Any = ctx.deserialize(json, var13);
         var10 = var8 as SpawningContextSelector;
      }

      return var10;
   }
}
