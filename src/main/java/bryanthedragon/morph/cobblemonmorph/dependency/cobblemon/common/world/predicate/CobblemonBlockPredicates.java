package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.serialization.Codec
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType

public object CobblemonBlockPredicates {
   public final val ALTITUDE: BlockPredicateType<AltitudePredicate> = INSTANCE.register("altitude", AltitudePredicate.Companion.getCODEC())
   public final val BIOME: BlockPredicateType<BiomePredicate> = INSTANCE.register("biome", BiomePredicate.Companion.getCODEC())

   public fun <P : BlockPredicate?> register(id: String, codec: Codec<Any>): BlockPredicateType<Any> {
      val var10000: Any = Registry.m_122965_(BuiltInRegistries.f_256906_, MiscUtilsKt.cobblemonResource(id), CobblemonBlockPredicates::register$lambda$0);
      return var10000 as BlockPredicateType<P>;
   }

   public fun touch() {
   }

   @JvmStatic
   fun `register$lambda$0`(`$codec`: Codec): Codec {
      return `$codec`;
   }
}
