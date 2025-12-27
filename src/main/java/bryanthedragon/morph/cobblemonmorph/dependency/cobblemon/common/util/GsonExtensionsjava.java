package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.Reader
import kotlin.jvm.internal.Intrinsics

@JvmSynthetic
public inline fun <reified T> Gson.fromJson(reader: Reader): Any {
   Intrinsics.reifiedOperationMarker(4, "T");
   return (T)`$this$fromJson`.fromJson(reader, Object::class.java);
}

@JvmSynthetic
public inline fun <reified T> Gson.fromJson(element: JsonElement): Any {
   Intrinsics.reifiedOperationMarker(4, "T");
   return (T)`$this$fromJson`.fromJson(element, Object::class.java);
}

@JvmSynthetic
public inline fun <reified T> Gson.fromJson(string: String): Any {
   Intrinsics.reifiedOperationMarker(4, "T");
   return (T)`$this$fromJson`.fromJson(string, Object::class.java);
}

public fun JsonObject.singularToPluralList(rootName: String, pluralName: String = "$rootNames"): JsonObject {
   if (`$this$singularToPluralList`.has(rootName)) {
      if (!`$this$singularToPluralList`.has(pluralName)) {
         `$this$singularToPluralList`.add(pluralName, (new JsonArray()) as JsonElement);
      }

      `$this$singularToPluralList`.get(pluralName).getAsJsonArray().add(`$this$singularToPluralList`.get(rootName));
      `$this$singularToPluralList`.remove(rootName);
   }

   return `$this$singularToPluralList`;
}

@JvmSynthetic
fun `singularToPluralList$default`(var0: JsonObject, var1: java.lang.String, var2: java.lang.String, var3: Int, var4: Any): JsonObject {
   if ((var3 and 2) != 0) {
      var2 = "$var1s";
   }

   return singularToPluralList(var0, var1, var2);
}

public fun JsonElement.normalizeToArray(): JsonArray {
   if (`$this$normalizeToArray` is JsonArray) {
      return `$this$normalizeToArray` as JsonArray;
   } else {
      val array: JsonArray = new JsonArray();
      array.add(`$this$normalizeToArray`);
      return array;
   }
}
