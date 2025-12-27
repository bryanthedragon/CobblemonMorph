package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional

import com.google.gson.JsonElement
import java.util.Optional
import kotlin.jvm.functions.Function1
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public open class RegistryLikeTagCondition<T>(tag: TagKey<Any>) : RegistryLikeCondition<T> {
   public final val tag: TagKey<Any>

   init {
      this.tag = tag;
   }

   public override fun fits(t: Any, registry: Registry<Any>): Boolean {
      val var3: Any = registry.m_7854_(t).flatMap(RegistryLikeTagCondition::fits$lambda$0).map(RegistryLikeTagCondition::fits$lambda$1).orElse(false);
      return var3 as java.lang.Boolean;
   }

   @JvmStatic
   fun `fits$lambda$0`(`$tmp0`: Function1, p0: Any): Optional {
      return `$tmp0`.invoke(p0) as Optional<? extends Object>;
   }

   @JvmStatic
   fun `fits$lambda$1`(`$tmp0`: Function1, p0: Any): java.lang.Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   public companion object {
      public const val PREFIX: String

      public fun <T> resolver(registryKey: ResourceKey<Registry<Any>>, constructor: (TagKey<Any>) -> RegistryLikeTagCondition<Any>): (JsonElement) -> RegistryLikeTagCondition<
               Any
            >? {
         return (new Function1<JsonElement, RegistryLikeTagCondition<T>>(constructor, registryKey) {
            {
               super(1);
               this.$constructor = `$constructor`;
               this.$registryKey = `$registryKey`;
            }

            @Nullable
            public final RegistryLikeTagCondition<T> invoke(@NotNull JsonElement it) {
               var var10000: java.lang.String = it.getAsString();
               var10000 = var10000.substring(0, 1);
               val var11: RegistryLikeTagCondition;
               if (var10000 == "#") {
                  var var10002: java.lang.String = it.getAsString();
                  var10002 = var10002.substring(1);
                  val var6: ResourceLocation = new ResourceLocation(var10002);
                  val var10: Function1 = this.$constructor;
                  val var10001: TagKey = TagKey.m_203882_(this.$registryKey, var6);
                  var11 = var10.invoke(var10001) as RegistryLikeTagCondition;
               } else {
                  var11 = null;
               }

               return var11;
            }
         }) as (JsonElement?) -> RegistryLikeTagCondition<T>;
      }
   }
}
