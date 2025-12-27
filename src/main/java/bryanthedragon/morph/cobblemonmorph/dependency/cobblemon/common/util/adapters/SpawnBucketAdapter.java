package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nSpawnBucketAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnBucketAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawnBucketAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,34:1\n1#2:35\n*E\n"])
public object SpawnBucketAdapter : JsonDeserializer<SpawnBucket>, JsonSerializer<SpawnBucket> {
   public open fun serialize(bucket: SpawnBucket, type: Type, ctx: JsonSerializationContext): JsonPrimitive {
      return new JsonPrimitive(bucket.getName());
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawnBucket {
      val var5: java.util.Iterator = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets().iterator();

      var var10000: Any;
      while (true) {
         if (var5.hasNext()) {
            val var6: Any = var5.next();
            if (!((var6 as SpawnBucket).getName() == json.getAsString())) {
               continue;
            }

            var10000 = (SpawnBucket)var6;
            break;
         }

         var10000 = null;
         break;
      }

      var10000 = var10000;
      if (var10000 == null) {
         throw new IllegalStateException("Spawn referred to invalid spawn bucket: ${json.getAsString()}. Is it missing from the config?");
      } else {
         return var10000;
      }
   }
}
