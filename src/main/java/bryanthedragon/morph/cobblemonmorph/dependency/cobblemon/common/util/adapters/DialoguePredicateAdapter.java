package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialoguePredicate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nDialoguePredicateAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePredicateAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialoguePredicateAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1549#2:34\n1620#2,3:35\n*S KotlinDebug\n*F\n+ 1 DialoguePredicateAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialoguePredicateAdapter\n*L\n29#1:34\n29#1:35,3\n*E\n"])
public object DialoguePredicateAdapter : JsonDeserializer<DialoguePredicate> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialoguePredicate {
      val var18: DialoguePredicate;
      if (json is JsonObject) {
         val `$i$f$map`: java.lang.String = (json as JsonObject).get("type").getAsString();
         val var10000: Class = DialoguePredicate.Companion.getTypes().get(`$i$f$map`);
         if (var10000 == null) {
            throw new IllegalArgumentException("Unknown dialogue predicate type $`$i$f$map`");
         }

         val `$this$map$iv`: Any = context.deserialize(json, var10000);
         var18 = `$this$map$iv` as DialoguePredicate;
      } else if (json is JsonArray) {
         val var19: java.util.List = (json as JsonArray).asList();
         val var16: java.lang.Iterable = var19;
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add((`item$iv$iv` as JsonElement).getAsString());
         }

         var18 = new ExpressionLikeDialoguePredicate(MoLangExtensionsKt.asExpressionLike(`destination$iv$iv` as MutableList<java.lang.String>));
      } else {
         val var10002: java.lang.String = json.getAsString();
         var18 = new ExpressionLikeDialoguePredicate(MoLangExtensionsKt.asExpressionLike(var10002));
      }

      return var18;
   }
}
