package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.ExpressionSpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSpawnDetailSelectorAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetailSelectorAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnDetailSelectorAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1#2:33\n*E\n"])
public object SpawnDetailSelectorAdapter : JsonDeserializer<SpawnDetailSelector> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawnDetailSelector {
      val var10: SpawnDetailSelector;
      if (json.isJsonPrimitive()) {
         val var10000: java.lang.String = json.getAsString();
         val expression: Expression = MoLangExtensionsKt.asExpression(var10000);
         val typex: ExpressionSpawnDetailSelector = new ExpressionSpawnDetailSelector();
         typex.setExpression(expression);
         var10 = typex;
      } else {
         val typex: java.lang.String = (json as JsonObject).get("type").getAsString();
         val var11: Class = SpawnDetailSelector.Companion.getTypes().get(typex);
         if (var11 == null) {
            throw new IllegalArgumentException("Unknown spawn detail selector type: $typex");
         }

         val var8: Any = ctx.deserialize(json, var11);
         var10 = var8 as SpawnDetailSelector;
      }

      return var10;
   }
}
