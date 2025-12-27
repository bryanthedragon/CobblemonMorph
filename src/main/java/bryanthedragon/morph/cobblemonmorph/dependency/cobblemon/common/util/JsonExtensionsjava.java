@file:SourceDebugExtension(["SMAP\nJsonExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonExtensions.kt\ncom/cobblemon/mod/common/util/JsonExtensionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,73:1\n1855#2,2:74\n1855#2,2:76\n1855#2,2:78\n1855#2,2:80\n1109#3,2:82\n*S KotlinDebug\n*F\n+ 1 JsonExtensions.kt\ncom/cobblemon/mod/common/util/JsonExtensionsKt\n*L\n23#1:74,2\n32#1:76,2\n41#1:78,2\n50#1:80,2\n62#1:82,2\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps
import java.util.NoSuchElementException
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.NbtOps
import net.minecraft.nbt.Tag

@JvmName(name = "toJsonArrayString")
public fun Collection<String>.toJsonArray(): JsonArray {
   val array: JsonArray = new JsonArray();
   if (`$this$toJsonArray`.isEmpty()) {
      return array;
   } else {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         array.add(`element$iv` as java.lang.String);
      }

      return array;
   }
}

@JvmName(name = "toJsonArrayBoolean")
public fun Collection<Boolean>.toJsonArray(): JsonArray {
   val array: JsonArray = new JsonArray();
   if (`$this$toJsonArray`.isEmpty()) {
      return array;
   } else {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         array.add(`element$iv` as java.lang.Boolean);
      }

      return array;
   }
}

@JvmName(name = "toJsonArrayNumber")
public fun Collection<Number>.toJsonArray(): JsonArray {
   val array: JsonArray = new JsonArray();
   if (`$this$toJsonArray`.isEmpty()) {
      return array;
   } else {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         array.add(`element$iv` as java.lang.Number);
      }

      return array;
   }
}

@JvmName(name = "toJsonArrayJsonElement")
public fun Collection<JsonElement>.toJsonArray(): JsonArray {
   val array: JsonArray = new JsonArray();
   if (`$this$toJsonArray`.isEmpty()) {
      return array;
   } else {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         array.add(`element$iv` as JsonElement);
      }

      return array;
   }
}

public fun JsonObject.isEmpty(): Boolean {
   return `$this$isEmpty`.size() <= 0;
}

public fun JsonObject.isNotEmpty(): Boolean {
   return `$this$isNotEmpty`.size() > 0;
}

public fun JsonElement.asNbt(): Tag {
   val var10000: Any = JsonOps.INSTANCE.convertTo(NbtOps.f_128958_ as DynamicOps, `$this$asNbt`);
   return var10000 as Tag;
}

public fun <T : Enum<Any>> Array<Any>.getFromJSON(element: JsonElement, name: String): Any {
   val type: java.lang.String = (element as JsonObject).get(name).getAsString();

   for (Object element$iv : $this$getFromJSON) {
      if (StringsKt.equals(type, `element$iv`.name(), true)) {
         return (T)`element$iv`;
      }
   }

   throw new NoSuchElementException("Array contains no element matching the predicate.");
}

public fun JsonObject.getFirst(vararg names: String): JsonElement? {
   for (java.lang.String name : names) {
      val element: JsonElement = `$this$getFirst`.get(name);
      if (element != null) {
         return element;
      }
   }

   return null;
}
