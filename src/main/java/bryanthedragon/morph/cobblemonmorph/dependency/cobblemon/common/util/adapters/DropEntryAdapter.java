package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nDropEntryAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DropEntryAdapter.kt\ncom/cobblemon/mod/common/util/adapters/DropEntryAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"])
public object DropEntryAdapter : JsonDeserializer<DropEntry> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): DropEntry {
      var var10: Class;
      label18: {
         val var5: JsonElement = (json as JsonObject).get("type");
         if (var5 != null) {
            val var6: java.lang.String = var5.getAsString();
            if (var6 != null) {
               var10 = DropEntry.Companion.getByName(var6);
               if (var10 == null) {
                  throw new IllegalArgumentException("Unrecognized drop entry type: $var6");
               }

               if (var10 != null) {
                  var10 = var10;
                  break label18;
               }
            }
         }

         var10 = DropEntry.Companion.getDefaultType();
      }

      var10 = (Class)ctx.deserialize(json, var10);
      return var10 as DropEntry;
   }
}
