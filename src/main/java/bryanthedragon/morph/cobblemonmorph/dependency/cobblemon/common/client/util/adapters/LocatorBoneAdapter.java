package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.LocatorBone
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nLocatorBoneAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocatorBoneAdapter.kt\ncom/cobblemon/mod/common/client/util/adapters/LocatorBoneAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n1549#2:44\n1620#2,3:45\n1549#2:48\n1620#2,3:49\n*S KotlinDebug\n*F\n+ 1 LocatorBoneAdapter.kt\ncom/cobblemon/mod/common/client/util/adapters/LocatorBoneAdapter\n*L\n35#1:44\n35#1:45,3\n36#1:48\n36#1:49,3\n*E\n"])
public object LocatorBoneAdapter : JsonDeserializer<LocatorBone> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, ctx: JsonDeserializationContext): LocatorBone {
      val var17: java.util.List;
      val var18: java.util.List;
      if (json is JsonArray) {
         var17 = CollectionsKt.listOf(
            new java.lang.Float[]{(json as JsonArray).get(0).getAsFloat(), (json as JsonArray).get(1).getAsFloat(), (json as JsonArray).get(2).getAsFloat()}
         );
         var18 = CollectionsKt.listOf(new java.lang.Float[]{0.0F, 0.0F, 0.0F});
      } else {
         var var31: java.util.List;
         label42: {
            val var10000: JsonElement = (json as JsonObject).get("offset");
            if (var10000 != null) {
               val var30: JsonArray = var10000.getAsJsonArray();
               if (var30 != null) {
                  val var20: java.lang.Iterable = var30 as java.lang.Iterable;
                  val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var30 as java.lang.Iterable, 10));

                  for (Object item$iv$iv : $this$map$iv) {
                     `destination$iv$iv`.add((`item$iv$iv` as JsonElement).getAsFloat());
                  }

                  var31 = `destination$iv$iv` as java.util.List;
                  break label42;
               }
            }

            var31 = CollectionsKt.listOf(new java.lang.Float[]{0.0F, 0.0F, 0.0F});
         }

         label31: {
            var17 = var31;
            val var32: JsonElement = (json as JsonObject).get("rotation");
            if (var32 != null) {
               val var33: JsonArray = var32.getAsJsonArray();
               if (var33 != null) {
                  val var22: java.lang.Iterable = var33 as java.lang.Iterable;
                  val var24: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var33 as java.lang.Iterable, 10));

                  for (Object item$iv$iv : $this$map$iv) {
                     var24.add((var27 as JsonElement).getAsFloat());
                  }

                  var31 = var24 as java.util.List;
                  break label31;
               }
            }

            var31 = CollectionsKt.listOf(new java.lang.Float[]{0.0F, 0.0F, 0.0F});
         }

         var18 = var31;
      }

      return new LocatorBone(var17, var18);
   }
}
