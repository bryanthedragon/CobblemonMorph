package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSingleToPluralAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SingleToPluralAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SingleToPluralAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1549#2:33\n1620#2,3:34\n*S KotlinDebug\n*F\n+ 1 SingleToPluralAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SingleToPluralAdapter\n*L\n27#1:33\n27#1:34,3\n*E\n"])
public class SingleToPluralAdapter<T, C extends java.lang.Iterable<? extends T>>(clazz: Class<Any>, converter: (List<Any>) -> Any) : JsonDeserializer<C> {
   public final val clazz: Class<Any>
   public final val converter: (List<Any>) -> Any

   init {
      this.clazz = clazz;
      this.converter = converter;
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Any {
      val var15: java.util.List;
      if (json.isJsonArray()) {
         val var10000: JsonArray = json.getAsJsonArray();
         val `$this$map$iv`: java.lang.Iterable = var10000 as java.lang.Iterable;
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10000 as java.lang.Iterable, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add(ctx.deserialize(`item$iv$iv` as JsonElement, this.clazz));
         }

         var15 = `destination$iv$iv` as java.util.List;
      } else {
         var15 = CollectionsKt.listOf(ctx.deserialize(json, this.clazz));
      }

      return (C)this.converter.invoke(var15);
   }
}
