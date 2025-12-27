package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.WrappedDialogueText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nDialogueTextAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueTextAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueTextAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,35:1\n1549#2:36\n1620#2,3:37\n*S KotlinDebug\n*F\n+ 1 DialogueTextAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DialogueTextAdapter\n*L\n27#1:36\n27#1:37,3\n*E\n"])
public object DialogueTextAdapter : JsonDeserializer<DialogueText> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialogueText {
      val var10000: DialogueText;
      if (json.isJsonPrimitive()) {
         val var10002: java.lang.String = json.getAsString();
         var10000 = new WrappedDialogueText(TextKt.text(var10002));
      } else if (json.isJsonArray()) {
         val var17: JsonArray = json.getAsJsonArray();
         val `$this$map$iv`: java.lang.Iterable = var17 as java.lang.Iterable;
         val clazz: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var17 as java.lang.Iterable, 10));

         for (Object item$iv$iv : $this$map$iv) {
            clazz.add((`item$iv$iv` as JsonElement).getAsString());
         }

         var10000 = new ExpressionLikeDialogueText(MoLangExtensionsKt.asExpressionLike(clazz as MutableList<java.lang.String>));
      } else {
         val typeId: java.lang.String = json.getAsJsonObject().get("type").getAsString();
         val var18: Class = DialogueText.Companion.getTypes().get(typeId);
         if (var18 == null) {
            throw new JsonParseException("Unknown dialogue text type $typeId");
         }

         val var19: Any = context.deserialize(json, var18);
         var10000 = var19 as DialogueText;
      }

      return var10000;
   }
}
