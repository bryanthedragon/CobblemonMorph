package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections.LazySet
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.reflect.KClass

@SourceDebugExtension(["SMAP\nLazySetAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LazySetAdapter.kt\ncom/cobblemon/mod/common/util/adapters/LazySetAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,40:1\n1855#2,2:41\n*S KotlinDebug\n*F\n+ 1 LazySetAdapter.kt\ncom/cobblemon/mod/common/util/adapters/LazySetAdapter\n*L\n37#1:41,2\n*E\n"])
public class LazySetAdapter<T>(type: KClass<Any>) : JsonDeserializer<LazySet<T>>, JsonSerializer<LazySet<T>> {
   private final val type: KClass<Any>

   init {
      this.type = type;
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LazySet<Any> {
      val var10002: KClass = this.type;
      val var10003: JsonArray = json.getAsJsonArray();
      return new LazySet<>(var10002, var10003);
   }

   public open fun serialize(src: LazySet<Any>, typeOfSrc: Type, context: JsonSerializationContext): JsonArray {
      val var4: JsonArray = new JsonArray();
      val `$this$serialize_u24lambda_u241`: JsonArray = var4;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         `$this$serialize_u24lambda_u241`.add(context.serialize(`element$iv`, JvmClassMappingKt.getJavaClass(this.type)));
      }

      return var4;
   }
}
