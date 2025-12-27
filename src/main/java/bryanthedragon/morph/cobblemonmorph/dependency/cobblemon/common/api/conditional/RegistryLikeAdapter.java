package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

public interface RegistryLikeAdapter<B> : JsonDeserializer<RegistryLikeCondition<B>> {
   public val registryLikeConditions: MutableList<(JsonElement) -> RegistryLikeCondition<Any>?>

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): RegistryLikeCondition<Any> {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nRegistryLikeAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RegistryLikeAdapter.kt\ncom/cobblemon/mod/common/api/conditional/RegistryLikeAdapter$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,31:1\n1#2:32\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun <B> deserialize(`$this`: RegistryLikeAdapter<B>, json: JsonElement, type: Type, ctx: JsonDeserializationContext): RegistryLikeCondition<B> {
         val var4: java.util.Iterator = `$this`.getRegistryLikeConditions().iterator();

         var var10000: RegistryLikeCondition;
         while (true) {
            if (var4.hasNext()) {
               val var7: RegistryLikeCondition = (var4.next() as Function1).invoke(json) as RegistryLikeCondition;
               if (var7 == null) {
                  continue;
               }

               var10000 = var7;
               break;
            }

            var10000 = null;
            break;
         }

         if (var10000 == null) {
            throw new IllegalArgumentException("Unable to deserialize $json");
         } else {
            return var10000;
         }
      }
   }
}
