package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.Expression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ListExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.SingleExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nExpressionLikeAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExpressionLikeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ExpressionLikeAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,39:1\n1360#2:40\n1446#2,5:41\n*S KotlinDebug\n*F\n+ 1 ExpressionLikeAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ExpressionLikeAdapter\n*L\n33#1:40\n33#1:41,5\n*E\n"])
public object ExpressionLikeAdapter : JsonDeserializer<ExpressionLike> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): ExpressionLike {
      val var10000: ExpressionLike;
      if (json.isJsonPrimitive()) {
         val var10002: java.lang.String = json.getAsString();
         val var18: Expression = MoLangExtensionsKt.asExpression(var10002);
         var10000 = new SingleExpression(var18);
      } else {
         if (!json.isJsonArray()) {
            throw new IllegalArgumentException("Invalid expression JSON: $json");
         }

         val var15: JsonArray = json.getAsJsonArray();
         val `$this$flatMap$iv`: java.lang.Iterable = var15 as java.lang.Iterable;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$flatMap$iv) {
            val var16: java.lang.String = (`element$iv$iv` as JsonElement).getAsString();
            val var17: java.util.List = MoLangExtensionsKt.asExpressions(var16);
            CollectionsKt.addAll(`destination$iv$iv`, var17);
         }

         var10000 = new ListExpression(`destination$iv$iv` as MutableList<Expression>);
      }

      return var10000;
   }
}
