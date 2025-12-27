package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils

@SourceDebugExtension(["SMAP\nPossibleHeldItemAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PossibleHeldItemAdapter.kt\ncom/cobblemon/mod/common/util/adapters/PossibleHeldItemAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,46:1\n1#2:47\n*E\n"])
public object PossibleHeldItemAdapter : JsonDeserializer<PossibleHeldItem> {
   public open fun deserialize(json: JsonElement, tp: Type, ctx: JsonDeserializationContext): PossibleHeldItem {
      if (json.isJsonPrimitive()) {
         val var10: java.lang.String = json.getAsString();
         return new PossibleHeldItem(var10, null, 100.0);
      } else {
         var var12: CompoundTag;
         label20: {
            val var10000: JsonElement = (json as JsonObject).get("nbt");
            if (var10000 != null) {
               val var11: java.lang.String = var10000.getAsString();
               if (var11 != null) {
                  var12 = NbtUtils.m_178024_(var11);
                  break label20;
               }
            }

            var12 = null;
         }

         val item: java.lang.String = (json as JsonObject).get("item").getAsString();
         val var13: JsonElement = (json as JsonObject).get("percentage");
         val percentage: Double = if (var13 != null) var13.getAsDouble() else 100.0;
         return new PossibleHeldItem(item, var12, percentage);
      }
   }
}
