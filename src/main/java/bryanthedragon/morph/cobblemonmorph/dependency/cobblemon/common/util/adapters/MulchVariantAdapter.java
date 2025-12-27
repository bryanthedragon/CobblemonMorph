package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nMulchVariantAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MulchVariantAdapter.kt\ncom/cobblemon/mod/common/util/adapters/MulchVariantAdapter\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,25:1\n3792#2:26\n4307#2,2:27\n*S KotlinDebug\n*F\n+ 1 MulchVariantAdapter.kt\ncom/cobblemon/mod/common/util/adapters/MulchVariantAdapter\n*L\n22#1:26\n22#1:27,2\n*E\n"])
public object MulchVariantAdapter : JsonDeserializer<MulchVariant> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): MulchVariant {
      var var10000: java.lang.String = json.getAsString();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val mulchName: java.lang.String = var10000;
      val `$this$filter$iv`: Array<Any> = MulchVariant.values();
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         var10000 = ((MulchVariant)`element$iv$iv`).name().toLowerCase(Locale.ROOT);
         if (var10000 == mulchName) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      return CollectionsKt.first(`destination$iv$iv` as java.util.List) as MulchVariant;
   }
}
