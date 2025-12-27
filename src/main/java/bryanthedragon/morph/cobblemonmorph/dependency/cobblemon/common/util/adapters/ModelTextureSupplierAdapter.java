package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.AnimatedModelTextureSupplier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ModelTextureSupplier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.StaticModelTextureSupplier
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nModelTextureSupplierAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModelTextureSupplierAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ModelTextureSupplierAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1549#2:48\n1620#2,3:49\n*S KotlinDebug\n*F\n+ 1 ModelTextureSupplierAdapter.kt\ncom/cobblemon/mod/common/util/adapters/ModelTextureSupplierAdapter\n*L\n36#1:48\n36#1:49,3\n*E\n"])
public object ModelTextureSupplierAdapter : JsonDeserializer<ModelTextureSupplier> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): ModelTextureSupplier {
      if (json.isJsonPrimitive()) {
         return new StaticModelTextureSupplier(new ResourceLocation(json.getAsString()));
      } else if (json.isJsonObject()) {
         val jsonObject: JsonObject = json as JsonObject;
         var var10000: JsonElement = (json as JsonObject).get("loop");
         val loop: Boolean = var10000 == null || var10000.getAsBoolean();
         var10000 = jsonObject.get("fps");
         val fps: Float = if (var10000 != null) var10000.getAsFloat() else 1.0F;
         var10000 = jsonObject.get("frames");
         if (var10000 != null) {
            val var20: JsonArray = var10000.getAsJsonArray();
            if (var20 != null) {
               val `$this$map$iv`: java.lang.Iterable = var20 as java.lang.Iterable;
               val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20 as java.lang.Iterable, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  `destination$iv$iv`.add(new ResourceLocation((`item$iv$iv` as JsonElement).getAsString()));
               }

               return new AnimatedModelTextureSupplier(loop, fps, `destination$iv$iv` as MutableList<ResourceLocation>);
            }
         }

         throw new IllegalArgumentException("Animated textures require a 'frames' value.");
      } else {
         throw new IllegalArgumentException(
            "Invalid JSON provided for model texture, it was of type ${(json.getClass()::class).getSimpleName()} instead of a String or Object."
         );
      }
   }
}
