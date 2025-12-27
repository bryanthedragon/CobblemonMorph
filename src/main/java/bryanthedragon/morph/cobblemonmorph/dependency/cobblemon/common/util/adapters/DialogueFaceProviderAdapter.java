package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueFaceProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nDialogueFaceProviderAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueFaceProviderAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueFaceProviderAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1549#2:33\n1620#2,3:34\n*S KotlinDebug\n*F\n+ 1 DialogueFaceProviderAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueFaceProviderAdapter\n*L\n28#1:33\n28#1:34,3\n*E\n"])
public object DialogueFaceProviderAdapter : JsonDeserializer<DialogueFaceProvider> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialogueFaceProvider {
      val var10000: DialogueFaceProvider;
      if (json is JsonPrimitive) {
         val var10002: java.lang.String = (json as JsonPrimitive).getAsString();
         var10000 = new ExpressionLikeDialogueFaceProvider(MoLangExtensionsKt.asExpressionLike(var10002));
      } else if (json is JsonArray) {
         val var16: JsonArray = (json as JsonArray).getAsJsonArray();
         val `$this$map$iv`: java.lang.Iterable = var16 as java.lang.Iterable;
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var16 as java.lang.Iterable, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add((`item$iv$iv` as JsonElement).getAsString());
         }

         var10000 = new ExpressionLikeDialogueFaceProvider(MoLangExtensionsKt.asExpressionLike(`destination$iv$iv` as MutableList<java.lang.String>));
      } else {
         val var17: Any = context.deserialize(json, ArtificialDialogueFaceProvider::class.java);
         var10000 = var17 as DialogueFaceProvider;
      }

      return var10000;
   }
}
