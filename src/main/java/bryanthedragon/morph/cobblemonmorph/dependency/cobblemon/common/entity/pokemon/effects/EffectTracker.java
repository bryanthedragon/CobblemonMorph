package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.EntityEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.MocKEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.concurrent.CompletableFuture
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag

@SourceDebugExtension(["SMAP\nEffectTracker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EffectTracker.kt\ncom/cobblemon/mod/common/entity/pokemon/effects/EffectTracker\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,54:1\n1#2:55\n*E\n"])
public class EffectTracker(entity: PokemonEntity) {
   public final val entity: PokemonEntity
   public final var mockEffect: MocKEffect?
   public final var progress: CompletableFuture<PokemonEntity>?

   init {
      this.entity = entity;
   }

   public fun wipe(): CompletableFuture<PokemonEntity>? {
      return if (this.mockEffect != null) this.mockEffect.end(this.entity) else null;
   }

   public fun forceWipe() {
      this.mockEffect = null;
   }

   public fun saveToNbt(): CompoundTag {
      val nbt: CompoundTag = new CompoundTag();
      if (this.mockEffect != null) {
         nbt.m_128365_("EntityEffectMock", this.mockEffect.saveToNbt() as Tag);
      }

      return nbt;
   }

   public fun loadFromNBT(nbt: CompoundTag) {
      if (nbt.m_128441_("EntityEffectMock")) {
         var var11: MocKEffect;
         var var10000: EffectTracker;
         label19: {
            val mockTag: CompoundTag = nbt.m_128469_("EntityEffectMock");
            var10000 = this;
            val var10001: EntityEffect.Companion = EntityEffect.Companion;
            val var9: EntityEffect = var10001.loadFromNbt(mockTag);
            if (var9 != null) {
               val var7: Boolean = var9 is MocKEffect;
               var10000 = this;
               val var10: EntityEffect = if (var7) var9 else null;
               if ((if (var7) var9 else null) != null) {
                  var11 = var10 as MocKEffect;
                  var10000 = this;
                  break label19;
               }
            }

            var11 = null;
         }

         var10000.mockEffect = var11;
      }
   }
}
