package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.serialization.Codec
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType

public object CobblemonProcessorTypes {
   public final val RANDOM_POOLED_STATES: StructureProcessorType<RandomizedStructureMappedBlockStatePairProcessor> =
      INSTANCE.register("random_pooled_states", RandomizedStructureMappedBlockStatePairProcessor.Companion.getCODEC())
      public final val lists: CobblemonStructureProcessorLists = CobblemonStructureProcessorLists.INSTANCE
   public final val registry: Registry<StructureProcessorType<*>> = BuiltInRegistries.f_256897_

   public fun <T : StructureProcessor> register(id: String, codec: Codec<Any>): StructureProcessorType<Any> {
      val var10000: Any = Registry.m_122965_(registry, MiscUtilsKt.cobblemonResource(id), CobblemonProcessorTypes::register$lambda$0);
      return var10000 as StructureProcessorType<T>;
   }

   public fun touch() {
   }

   @JvmStatic
   fun `register$lambda$0`(`$codec`: Codec): Codec {
      return `$codec`;
   }
}
