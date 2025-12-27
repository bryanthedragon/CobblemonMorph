package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.reflect.TypeToken
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nRenderContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RenderContext.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext\n+ 2 RenderContext.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Companion\n*L\n1#1,159:1\n155#2:160\n155#2:161\n155#2:162\n155#2:163\n155#2:164\n155#2:165\n*S KotlinDebug\n*F\n+ 1 RenderContext.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext\n*L\n108#1:160\n113#1:161\n118#1:162\n123#1:163\n128#1:164\n133#1:165\n*E\n"])
public class RenderContext {
   private final val context: MutableMap<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<*>, Any?> =
      (new LinkedHashMap()) as java.util.Map

   public fun <T : Any> request(key: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Any>): Any? {
      return (T)this.context.get(key);
   }

   public fun <T : Any> requires(key: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Any>): Any {
      val var10000: Any = this.request(key);
      if (var10000 == null) {
         throw new NullPointerException("Required value not found in context for key: $key");
      } else {
         return (T)var10000;
      }
   }

   public fun <T : Any> put(key: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Any>, value: Any?) {
      this.context.put(key, value);
   }

   public fun pop() {
      this.context.clear();
   }

   public fun <T : Any> pop(key: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Any>) {
      this.context.remove(key);
   }

   @JvmStatic
   fun {
      var `this_$iv`: RenderContext.Companion = Companion;
      var `id$iv`: ResourceLocation = MiscUtilsKt.asResource("entity");
      var var10002: TypeToken = TypeToken.get(Entity.class);
      ENTITY = `this_$iv`.key(`id$iv`, var10002);
      `this_$iv` = Companion;
      `id$iv` = MiscUtilsKt.asResource("texture");
      var10002 = TypeToken.get(ResourceLocation.class);
      TEXTURE = `this_$iv`.key(`id$iv`, var10002);
      `this_$iv` = Companion;
      `id$iv` = MiscUtilsKt.asResource("scale");
      var10002 = TypeToken.get(java.lang.Float.class);
      SCALE = `this_$iv`.key(`id$iv`, var10002);
      `this_$iv` = Companion;
      `id$iv` = MiscUtilsKt.asResource("species");
      var10002 = TypeToken.get(ResourceLocation.class);
      SPECIES = `this_$iv`.key(`id$iv`, var10002);
      `this_$iv` = Companion;
      `id$iv` = MiscUtilsKt.asResource("species");
      var10002 = TypeToken.get(java.util.Set.class);
      ASPECTS = `this_$iv`.key(`id$iv`, var10002);
      `this_$iv` = Companion;
      `id$iv` = MiscUtilsKt.asResource("state");
      var10002 = TypeToken.get(RenderContext.RenderState.class);
      RENDER_STATE = `this_$iv`.key(`id$iv`, var10002);
   }

   public companion object {
      public final val ASPECTS: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Set<String>>
      public final val ENTITY: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Entity>
      public final val RENDER_STATE: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<
         bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.RenderState
      >
      public final val SCALE: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<Float>
      public final val SPECIES: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<ResourceLocation>
      public final val TEXTURE: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<ResourceLocation>

      public fun <T : Any> key(id: ResourceLocation, token: TypeToken<Any>): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<
            Any
         > {
         return new RenderContext.Key(id, token);
      }
   }

   public data Key<T>(key: ResourceLocation, token: TypeToken<Any>) {
      public final val key: ResourceLocation
      public final val token: TypeToken<Any>

      init {
         this.key = key;
         this.token = token;
      }

      public operator fun component1(): ResourceLocation {
         return this.key;
      }

      public operator fun component2(): TypeToken<Any> {
         return this.token;
      }

      public fun copy(key: ResourceLocation = this.key, token: TypeToken<Any> = this.token): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext.Key<
            Any
         > {
         return new RenderContext.Key<>(key, token);
      }

      public override fun toString(): String {
         return "Key(key=${this.key}, token=${this.token})";
      }

      public override fun hashCode(): Int {
         return this.key.hashCode() * 31 + this.token.hashCode();
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is RenderContext.Key) {
            return false;
         } else {
            val var2: RenderContext.Key = other as RenderContext.Key;
            if (!(this.key == (other as RenderContext.Key).key)) {
               return false;
            } else {
               return this.token == var2.token;
            }
         }
      }
   }

   public enum RenderState(isGuiBased: Boolean) {
      WORLD(false),
      PORTRAIT(true),
      PROFILE(true)
      public final val isGuiBased: Boolean

      init {
         this.isGuiBased = isGuiBased;
      }
   }
}
