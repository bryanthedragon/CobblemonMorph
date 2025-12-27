package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nEggGroupAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EggGroupAdapter.kt\ncom/cobblemon/mod/common/util/adapters/EggGroupAdapter\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,40:1\n1282#2,2:41\n*S KotlinDebug\n*F\n+ 1 EggGroupAdapter.kt\ncom/cobblemon/mod/common/util/adapters/EggGroupAdapter\n*L\n27#1:41,2\n*E\n"])
public object EggGroupAdapter : JsonDeserializer<EggGroup>, JsonSerializer<EggGroup> {
   private final val eggGroups: Array<EggGroup>

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): EggGroup {
      val rawID: java.lang.String = json.getAsString();
      val `$this$firstOrNull$iv`: Array<Any> = eggGroups;
      var var7: Int = 0;
      val var8: Int = eggGroups.length;

      var var10000: Any;
      while (true) {
         if (var7 >= var8) {
            var10000 = null;
            break;
         }

         val `element$iv`: Any = `$this$firstOrNull$iv`[var7];
         if (StringsKt.equals(((EggGroup)`$this$firstOrNull$iv`[var7]).name(), rawID, true)) {
            var10000 = `element$iv`;
            break;
         }

         var7++;
      }

      if (var10000 == null) {
         throw new IllegalStateException("Failed to resolve egg group from: $rawID");
      } else {
         return (EggGroup)var10000;
      }
   }

   public open fun serialize(src: EggGroup, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      val var10002: java.lang.String = src.name().toLowerCase(Locale.ROOT);
      return (
         new JsonPrimitive(
            CollectionsKt.joinToString$default(
               StringsKt.split$default(var10002, new java.lang.String[]{"_"}, false, 0, 6, null),
               " ",
               null,
               null,
               0,
               null,
               <unrepresentable>.INSTANCE,
               30,
               null
            )
         )
      ) as JsonElement;
   }
}
