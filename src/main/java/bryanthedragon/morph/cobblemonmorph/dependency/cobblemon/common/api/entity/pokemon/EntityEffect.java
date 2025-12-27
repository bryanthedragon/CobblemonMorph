package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.TransformEffect
import java.util.LinkedHashMap
import java.util.concurrent.CompletableFuture
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.reflect.KClass
import net.minecraft.nbt.CompoundTag

public interface EntityEffect {
   public abstract fun start(entity: PokemonEntity): CompletableFuture<PokemonEntity>? {
   }

   public abstract fun end(entity: PokemonEntity): CompletableFuture<PokemonEntity>? {
   }

   public abstract fun saveToNbt(): CompoundTag {
   }

   public abstract fun loadFromNBT(nbt: CompoundTag) {
   }

   @SourceDebugExtension(["SMAP\nEntityEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityEffect.kt\ncom/cobblemon/mod/common/api/entity/pokemon/EntityEffect$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,98:1\n1#2:99\n*E\n"])
   public companion object {
      private final val defaults: MutableMap<String, () -> EntityEffect> = (new LinkedHashMap()) as java.util.Map
      private final val effects: MutableMap<String, KClass<out EntityEffect>> = (new LinkedHashMap()) as java.util.Map

      public fun <T : EntityEffect> register(id: String, type: KClass<Any>, default: () -> Any) {
         effects.put(id, type);
         defaults.put(id, default);
      }

      public fun createDefault(id: String): EntityEffect? {
         val var10000: Function0 = defaults.get(id);
         return if (var10000 != null) var10000.invoke() as EntityEffect else null;
      }

      public fun loadFromNbt(nbt: CompoundTag): EntityEffect? {
         if (nbt.m_128441_("EntityEffectID")) {
            val id: java.lang.String = nbt.m_128461_("EntityEffectID");
            var var10000: EntityEffect = this.createDefault(id);
            if (var10000 != null) {
               var10000.loadFromNBT(nbt);
               var10000 = var10000;
            } else {
               var10000 = null;
            }

            return var10000;
         } else {
            return null;
         }
      }

      @JvmStatic
      fun {
         $$INSTANCE.register(IllusionEffect.Companion.getID(), IllusionEffect::class, <unrepresentable>.INSTANCE);
         $$INSTANCE.register(TransformEffect.Companion.getID(), TransformEffect::class, <unrepresentable>.INSTANCE);
      }
   }
}
